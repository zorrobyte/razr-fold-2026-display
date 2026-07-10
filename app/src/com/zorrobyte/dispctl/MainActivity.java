package com.zorrobyte.dispctl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.hardware.display.DisplayManager;
import android.view.Display;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

// 5K Display Control (v3) — root app for the Razr Fold 2026 external-display unlock.
//
// LIVE mode switching, NO physical replug: writes the QTI mode_override (edid_modes)
// then cycles the DP hpd node 0->1, which re-probes + re-runs DSC and the framework
// re-adds the display at the new timing. See ../LIVE-MODE-SWITCHING.md.
//
// Design principles:
//   * Nothing hardcoded for a specific monitor. Modes come from the sink's raw EDID
//     and the framework's getSupportedModes(); the DP connector and DRM card are
//     discovered at runtime; bandwidth is derived from the LIVE trained link.
//   * Reactive. Registers a DisplayManager.DisplayListener + refreshes onResume, so
//     plugging, unplugging, and mode changes update the UI on their own — no relaunch.
//   * Honest. After applying a mode we poll the link until it settles, then report the
//     resolution actually achieved (a 2-lane dock can't fit 5120@100; we say so).
//   * Automatic. On a fresh connection the app picks a mode on its own: the one it
//     remembers you last chose for THIS monitor (keyed by EDID), else the highest
//     res/refresh that fits the trained link. It never overrides a mode you tapped by
//     hand this session, and the whole behaviour is toggleable. No background service
//     sets resolution — the app is the only mode-setter (the Magisk module only quiets
//     HDCP). See onCreate()/reloadWork() for the auto-select path.
public class MainActivity extends Activity {

    // palette
    static final int BG=0xFF0E1216, CARD=0xFF161C22, CARD2=0xFF1B232B, ACCENT=0xFF3B9EFF,
        ACCENT_HI=0xFF1D6FB8, GOOD=0xFF39D98A, WARN=0xFFFFB454, BAD=0xFFFF6B6B,
        TEXT=0xFFECF2F8, SUBTLE=0xFF9FB2C4, DIM=0xFF66727E;

    // DP link constants (8b/10b payload efficiency, driver's min compressed bpp)
    static final double ENC_8B10B = 0.8;
    static final int DSC_FLOOR_BPP = 18;

    static final String DBG="/sys/kernel/debug/drm_dp/";
    static final int DPI_MIN=96, DPI_MAX=360;

    LinearLayout list;
    TextView stConn, stMode, stLink, stDsc, stHdcp, scaleLabel, footer;
    SeekBar scaleBar;

    // live state (written on the worker, read on UI thread)
    volatile int extDisplayId=-1;
    volatile int curLanes=2;
    volatile long curRateKhz=0;
    volatile String connectorDir=null;   // e.g. /sys/class/drm/card0-DP-1 (discovered)
    volatile boolean rootOk=true;
    volatile boolean present=false;       // last known external-display presence

    // auto-select state
    SharedPreferences prefs;              // per-monitor remembered mode + auto toggle
    volatile boolean autoEnabled=true;    // "auto-set on plug" master switch (persisted)
    volatile String curMonitorId=null;    // identity of the connected monitor (EDID-derived)
    volatile String autoHandledId=null;   // monitor already auto/manually handled this connection
    Switch autoSwitch;

    final AtomicBoolean applying=new AtomicBoolean(false);
    ExecutorService worker;               // serialises all su/state work
    Handler ui;                           // main-looper handler (debounce)
    DisplayManager dm;
    DisplayManager.DisplayListener displayListener;
    final Runnable reloadRunnable=this::reload;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        worker=Executors.newSingleThreadExecutor();
        ui=new Handler(Looper.getMainLooper());
        dm=(DisplayManager)getSystemService(DISPLAY_SERVICE);
        prefs=getSharedPreferences("dispctl", MODE_PRIVATE);
        autoEnabled=prefs.getBoolean("auto_enabled", true);

        ScrollView sv=new ScrollView(this); sv.setBackgroundColor(BG);
        LinearLayout root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20),dp(28),dp(20),dp(28));
        sv.addView(root);

        TextView title=new TextView(this);
        title.setText("5K Display Control"); title.setTextColor(TEXT); title.setTextSize(26);
        title.setTypeface(title.getTypeface(),Typeface.BOLD); root.addView(title);
        TextView sub=new TextView(this);
        sub.setText("Razr Fold 2026 · live switching, no replug");
        sub.setTextColor(SUBTLE); sub.setTextSize(13); sub.setPadding(0,dp(2),0,dp(16)); root.addView(sub);

        LinearLayout card=card();
        stConn=kv(card,"Link","—");
        stMode=kv(card,"Active mode","—");
        stLink=kv(card,"DP link","—");
        stDsc =kv(card,"DSC","—");
        stHdcp=kv(card,"HDCP","—");
        root.addView(card);

        root.addView(section("Resolution & refresh"));
        TextView hint=new TextView(this);
        hint.setText("Tap a mode — applied live over the current link (no replug).");
        hint.setTextColor(SUBTLE); hint.setTextSize(12); hint.setPadding(0,0,0,dp(8)); root.addView(hint);

        // Auto-select toggle: on a fresh plug, apply the remembered (else highest fitting) mode.
        autoSwitch=new Switch(this);
        autoSwitch.setText("Auto-set best mode on plug  (remembers per monitor)");
        autoSwitch.setTextColor(TEXT); autoSwitch.setTextSize(13);
        autoSwitch.setChecked(autoEnabled);
        autoSwitch.setPadding(0,0,0,dp(6));
        autoSwitch.setOnCheckedChangeListener((sw,c)->{
            autoEnabled=c; prefs.edit().putBoolean("auto_enabled",c).apply();
            toast(c?"Auto-select on — best mode applied on each plug":"Auto-select off — pick modes manually");
            if(c){ autoHandledId=null; reload(); }   // re-evaluate current monitor now
        });
        root.addView(autoSwitch);

        list=new LinearLayout(this); list.setOrientation(LinearLayout.VERTICAL); root.addView(list);

        LinearLayout ctl=new LinearLayout(this); ctl.setOrientation(LinearLayout.HORIZONTAL);
        Button rf=btn("↻  Refresh",CARD2,false,true);
        Button re=btn("Re-probe link",CARD2,false,true);
        Button cl=btn("Clear override",CARD2,false,true);
        rf.setOnClickListener(v->{ toast("Refreshing…"); reload(); });
        re.setOnClickListener(v->reprobeCurrent());
        cl.setOnClickListener(v->clearOverride());
        ctl.addView(rf); ctl.addView(re); ctl.addView(cl); root.addView(ctl);

        root.addView(section("UI scale — external display"));
        scaleLabel=new TextView(this); scaleLabel.setTextColor(SUBTLE); scaleLabel.setTextSize(13);
        scaleLabel.setPadding(0,0,0,dp(4)); root.addView(scaleLabel);
        scaleBar=new SeekBar(this); scaleBar.setMax(DPI_MAX-DPI_MIN);
        scaleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            public void onProgressChanged(SeekBar s,int p,boolean u){ if(scaleLabel!=null) scaleLabel.setText(scaleText(DPI_MIN+p)); }
            public void onStartTrackingTouch(SeekBar s){}
            public void onStopTrackingTouch(SeekBar s){
                if(extDisplayId<0){toast("No external display — connect a monitor first");return;}
                int dpi=DPI_MIN+s.getProgress(); su("wm density "+dpi+" -d "+extDisplayId); toast("UI scale → "+dpi+" dpi");
            }
        });
        root.addView(scaleBar);
        LinearLayout sr=new LinearLayout(this); sr.setOrientation(LinearLayout.HORIZONTAL);
        Button sm=btn("Smaller –",CARD2,false,true), bg=btn("Larger +",CARD2,false,true), rs=btn("Reset",CARD2,false,true);
        sm.setOnClickListener(v->nudge(-20)); bg.setOnClickListener(v->nudge(20));
        rs.setOnClickListener(v->{ if(extDisplayId<0){toast("No external display");return;} su("wm density reset -d "+extDisplayId); toast("UI scale reset"); reload();});
        sr.addView(sm); sr.addView(bg); sr.addView(rs); root.addView(sr);

        footer=new TextView(this); footer.setTextColor(DIM); footer.setTextSize(11);
        footer.setPadding(dp(2),dp(18),0,0); root.addView(footer);

        setContentView(sv);

        // React to hotplug + mode changes without a relaunch.
        displayListener=new DisplayManager.DisplayListener(){
            public void onDisplayAdded(int id){ scheduleReload(); }
            public void onDisplayRemoved(int id){ scheduleReload(); }
            public void onDisplayChanged(int id){ scheduleReload(); }
        };
    }

    @Override protected void onResume(){
        super.onResume();
        try{ dm.registerDisplayListener(displayListener, ui); }catch(Exception e){}
        reload();
    }
    @Override protected void onPause(){
        super.onPause();
        try{ dm.unregisterDisplayListener(displayListener); }catch(Exception e){}
        ui.removeCallbacks(reloadRunnable);
    }
    @Override protected void onDestroy(){
        ui.removeCallbacks(reloadRunnable);
        if(worker!=null) worker.shutdownNow();
        super.onDestroy();
    }

    // Debounced refresh: coalesce bursts of hotplug callbacks into one reload.
    void scheduleReload(){ ui.removeCallbacks(reloadRunnable); ui.postDelayed(reloadRunnable, 350); }

    // All background work goes through here so a submit after the activity is torn
    // down (worker shut down) can never throw RejectedExecutionException.
    void submit(Runnable r){
        ExecutorService w=worker;
        if(w==null||w.isShutdown()||w.isTerminated()) return;
        try{ w.submit(r); }catch(java.util.concurrent.RejectedExecutionException ignore){}
    }

    // ---------- state ----------
    String rd(String node){ return su("cat "+DBG+node+" 2>/dev/null"); }

    void reload() {
        submit(() -> {
            try { reloadWork(); } catch(Throwable t){ /* never let a refresh crash the app */ }
        });
    }

    void reloadWork() {
        // root sanity — one cheap probe. If su is unavailable everything else is moot.
        String uid=su("id -u").trim();
        rootOk = uid.equals("0");

        su("mount -t debugfs none /sys/kernel/debug 2>/dev/null");
        String conn=findConnector();               // discovered, not hardcoded
        connectorDir=conn;

        String status = conn!=null ? su("cat "+conn+"/status").trim() : "";
        boolean sysfsConnected="connected".equals(status);

        String dp=rd("dp_debug");
        String hdcp=rd("hdcp");

        String curRes="—"; int lanes=0; long linkRate=0; int bpp=0;
        for(String ln:dp.split("\n")){
            String t=ln.trim();
            if(t.startsWith("resolution=")) curRes=t.substring(11);
            else if(t.startsWith("num_lanes=")) lanes=parseI(t,0);
            else if(t.startsWith("link_rate=")) linkRate=parseL(t,0);
            else if(t.startsWith("bpp=")) bpp=parseI(t,0);
        }

        // Framework's view (authoritative for the display id + density target).
        int fwId=findExternal();
        boolean fwHasExternal = fwId>=0;

        // Unified presence: believe EITHER path. During our own hpd cycle the sysfs
        // node blips 'disconnected' for a moment — the framework (or the applying
        // guard) keeps us from flashing an empty screen.
        boolean present = sysfsConnected || fwHasExternal;
        this.present=present;

        extDisplayId=fwId;
        curLanes = lanes>0?lanes:curLanes;
        curRateKhz = linkRate>0?linkRate:curRateKhz;

        final List<int[]> modes = present?collectModes():new ArrayList<>();

        // ---- auto-select on a fresh connection --------------------------------
        // Identify the monitor by its EDID. When a not-yet-handled monitor is present
        // and auto is on, pick the mode to apply — the one remembered for THIS monitor,
        // else the highest res/refresh that fits the trained link — and apply it once.
        // A manual tap marks the monitor handled (see applyMode), so we never fight the
        // user; unplugging resets, so the next plug re-applies the remembered mode.
        String monId = present ? readMonitorId() : null;
        curMonitorId = monId;
        if(!present){
            autoHandledId=null;                              // reset so replug re-triggers
        } else if(autoEnabled && !applying.get() && monId!=null && !monId.equals(autoHandledId)){
            long budget = linkBudget(curLanes,curRateKhz);
            int[] tgt = chooseAuto(modes, budget, monId);
            autoHandledId = monId;                           // mark handled (prevents re-fire loop)
            if(tgt!=null && !sameMode(curRes,tgt[0],tgt[1],tgt[2])){
                boolean tfits = budget<=0 || (long)tgt[0]*tgt[1]*tgt[2]*DSC_FLOOR_BPP<=budget;
                applyMode(tgt[0],tgt[1],tgt[2],tfits,true);  // auto=true (enqueued on worker)
            }
        }

        final boolean fPresent=present, fApplying=applying.get(), fRoot=rootOk;
        final String fRes = present?(isRes(curRes)?curRes:"—"):"—";
        final int fLanes=curLanes; final long fRate=curRateKhz;
        final int dens = fwId>=0?readDensity(fwId):DPI_MIN;
        final String dscTxt = present?dscStatus(fLanes,fRate,fRes):"—";
        final String hdcpTxt = hdcpStatus(hdcp);
        final String connName = conn!=null?conn.substring(conn.lastIndexOf('/')+1):"—";

        runOnUiThread(() -> render(fPresent,fApplying,fRoot,fRes,fLanes,fRate,modes,dens,dscTxt,hdcpTxt,connName));
    }

    void render(boolean present, boolean isApplying, boolean root, String res, int lanes, long rate,
                List<int[]> modes, int dens, String dscTxt, String hdcpTxt, String connName){
        if(!root){
            stConn.setText("No root — grant su to this app"); stConn.setTextColor(BAD);
            stMode.setText("—"); stLink.setText("—"); stDsc.setText("—"); stHdcp.setText("—");
            list.removeAllViews();
            TextView t=new TextView(this);
            t.setText("This app needs root (Magisk). Open Magisk → grant superuser to 5K Display Control, then Refresh.");
            t.setTextColor(BAD); t.setPadding(0,dp(8),0,dp(8)); list.addView(t);
            scaleLabel.setText("—"); footer.setText(""); return;
        }

        if(!present && isApplying){
            stConn.setText("Applying…"); stConn.setTextColor(WARN);
            footer.setText("Re-training link — hold on.");
            return;   // keep the mode list on-screen; don't flash "no display"
        }

        stConn.setText(present?("Connected  ·  "+lanes+" lane"+(lanes==1?"":"s")):"No external display");
        stConn.setTextColor(present?GOOD:SUBTLE);
        stMode.setText(present?res:"—"); stMode.setTextColor(TEXT);
        stLink.setText(present?linkText(rate,lanes):"—"); stLink.setTextColor(SUBTLE);
        stDsc.setText(dscTxt); stDsc.setTextColor(dscTxt.startsWith("engaged")?GOOD:SUBTLE);
        stHdcp.setText(hdcpTxt); stHdcp.setTextColor(hdcpTxt.startsWith("auth")?GOOD:(hdcpTxt.equals("—")?SUBTLE:WARN));

        boolean canScale=extDisplayId>=0;
        int cl=Math.max(DPI_MIN,Math.min(DPI_MAX,dens));
        scaleBar.setProgress(cl-DPI_MIN); scaleBar.setEnabled(canScale);
        scaleLabel.setText(canScale?scaleText(dens):"Connect a monitor to adjust scale");

        list.removeAllViews();
        if(!present || modes.isEmpty()){
            TextView t=new TextView(this);
            t.setText(present?"No modes reported yet — tap Re-probe.":
                "Connect a monitor (direct USB-C→DP cable for 4-lane / native 5120), then it appears here automatically.");
            t.setTextColor(present?WARN:BAD); t.setPadding(0,dp(8),0,dp(8)); list.addView(t);
        }
        long budget=linkBudget(lanes,rate);
        for(int[] m:modes){
            final int w=m[0],h=m[1],hz=m[2];
            long need=(long)w*h*hz*DSC_FLOOR_BPP;
            boolean fits = budget<=0 || need<=budget;
            boolean is5k=(long)w*h>=5120L*2160;
            boolean active=sameMode(res,w,h,hz);
            String badge=is5k?"  ★ 5K2K":((long)w*h>=3840L*2160?"  4K":((long)w*h>=3440L*1440?"  UW":""));
            String label=(active?"●  ":"")+w+" × "+h+"  @ "+hz+" Hz"+badge;
            if(!fits) label+="\n    ⚠ needs a wider link ("+gbps(need)+" > "+gbps(budget)+" on "+lanes+" lane"+(lanes==1?"":"s")+")";
            Button bb=btn(label, is5k?ACCENT_HI:CARD, is5k, false);
            if(!fits) bb.setTextColor(DIM);
            bb.setOnClickListener(v->applyMode(w,h,hz,fits));
            list.addView(bb);
        }
        footer.setText("connector "+connName+(rate>0?("  ·  "+linkText(rate,lanes)):"")+
            (extDisplayId>=0?("  ·  display id "+extDisplayId):""));
    }

    // Discover the active DP/HDMI connector under /sys/class/drm — never assume
    // card0-DP-1. Prefer a connected one; fall back to the first DP connector.
    String findConnector(){
        String o=su("for d in /sys/class/drm/card*-DP-* /sys/class/drm/card*-HDMI-*; do "
                   +"[ -e \"$d/status\" ] && echo \"$d $(cat $d/status)\"; done");
        String firstDp=null, first=null;
        for(String ln:o.split("\n")){
            String t=ln.trim(); if(t.isEmpty()) continue;
            int sp=t.lastIndexOf(' '); if(sp<0) continue;
            String dir=t.substring(0,sp), st=t.substring(sp+1);
            if("connected".equals(st)) return dir;
            if(first==null) first=dir;
            if(firstDp==null && dir.contains("-DP-")) firstDp=dir;
        }
        return firstDp!=null?firstDp:first;
    }

    // Fully dynamic per-monitor mode list — nothing hardcoded. Two dynamic sources
    // merged: (1) the monitor's raw EDID (DTDs + DisplayID detailed timings — where
    // the ultrawide's 5120@60/100 live, and which survives the mode_override collapse
    // because raw EDID is always complete); (2) the framework's getSupportedModes().
    List<int[]> collectModes() {
        LinkedHashMap<String,int[]> u=new LinkedHashMap<>();
        for(int[] m:parseEdidModes()) u.put(key(m),m);
        try {
            Display ext=pickExternal(dm);
            if(ext!=null) for(Display.Mode m:ext.getSupportedModes()){
                int w=m.getPhysicalWidth(),h=m.getPhysicalHeight(),hz=Math.round(m.getRefreshRate());
                if(w>=640&&h>=480) u.putIfAbsent(w+"x"+h+"@"+hz,new int[]{w,h,hz});
            }
        } catch(Exception e){}
        List<int[]> out=new ArrayList<>(u.values());
        Collections.sort(out,(a,c)->Long.compare((long)c[0]*c[1]*c[2],(long)a[0]*a[1]*a[2]));
        return out;
    }
    String key(int[] m){ return m[0]+"x"+m[1]+"@"+m[2]; }

    // Parse the connected monitor's raw EDID for real timings (base + CEA DTDs and
    // DisplayID Type I/VII detailed timings). Uses the discovered connector.
    List<int[]> parseEdidModes() {
        List<int[]> out=new ArrayList<>();
        String conn=connectorDir; if(conn==null) return out;
        try {
            String hex=su("cat "+conn+"/edid 2>/dev/null | od -An -tx1 | tr -dc '0-9a-f'").trim();
            int n=hex.length()/2; if(n<128) return out;
            int[] b=new int[n];
            for(int i=0;i<n;i++) b[i]=Integer.parseInt(hex.substring(i*2,i*2+2),16);
            int blocks=n/128;
            for(int off:new int[]{54,72,90,108}) addDtd(out,b,0,off);      // base block DTDs
            for(int blk=1;blk<blocks;blk++){
                int base=blk*128; if(base+2>=n) break; int tag=b[base];
                if(tag==0x02){                                            // CEA-861: DTDs after byte[2]
                    int d=b[base+2];
                    if(d>=4){ int off=d; while(off+18<=127 && base+off+1<n && (b[base+off]|b[base+off+1])!=0){ addDtd(out,b,base,off); off+=18; } }
                }
                if(tag==0x70||tag==0x20){                                 // DisplayID: detailed-timing data blocks
                    int p=5;
                    while(p+3<128 && base+p+2<n){ int dt=b[base+p], dl=b[base+p+2];
                        if(dt==0&&dl==0) break;
                        if(dt==0x03||dt==0x22){ int pp=base+p+3; for(int i=0;i+20<=dl && pp+i+15<n;i+=20) addDid(out,b,pp+i); }
                        p+=3+dl; }
                }
            }
        } catch(Exception e){}
        return out;
    }
    void addDtd(List<int[]> out,int[] b,int base,int off){
        int d=base+off; if(d+7>=b.length) return; int pclk=(b[d+1]<<8|b[d])*10; if(pclk==0) return; // kHz
        int ha=b[d+2]|((b[d+4]&0xF0)<<4), hb=b[d+3]|((b[d+4]&0x0F)<<8);
        int va=b[d+5]|((b[d+7]&0xF0)<<4), vb=b[d+6]|((b[d+7]&0x0F)<<8);
        int ht=ha+hb, vt=va+vb; if(ht<=0||vt<=0) return;
        addMode(out,ha,va,(int)Math.round(pclk*1000.0/(ht*vt)));
    }
    void addDid(List<int[]> out,int[] b,int d){
        if(d+15>=b.length) return;
        int pclk=(((b[d+2]<<16)|(b[d+1]<<8)|b[d])+1)*10;                       // kHz (10kHz units, stored-1)
        int ha=(b[d+5]<<8|b[d+4])+1, hb=(b[d+7]<<8|b[d+6])+1;
        int va=(b[d+13]<<8|b[d+12])+1, vb=(b[d+15]<<8|b[d+14])+1;
        int ht=ha+hb, vt=va+vb; if(ht<=1||vt<=1) return;
        addMode(out,ha,va,(int)Math.round(pclk*1000.0/(ht*vt)));
    }
    void addMode(List<int[]> out,int w,int h,int hz){
        if(w>=640&&h>=480&&hz>=24&&hz<=360) out.add(new int[]{w,h,hz});
    }

    // ---------- actions ----------
    // LIVE apply: mode_override + hpd 0->1 re-probe (no skip_uevent, no physical replug),
    // then poll the link until it settles and report what actually trained.
    void applyMode(int w,int h,int hz,boolean fits){ applyMode(w,h,hz,fits,false); }

    // auto=false: user tapped a mode -> remember it for this monitor, mark handled.
    // auto=true : app picked it on a fresh plug -> don't overwrite the remembered choice.
    void applyMode(int w,int h,int hz,boolean fits,boolean auto) {
        if(!present){ if(!auto) toast("No external display"); return; }
        final String mid=curMonitorId;
        autoHandledId=mid;   // either path handles this monitor -> stop auto from re-firing
        final String tag=auto?"Auto · ":"";
        if(!fits && !auto) toast("Trying "+w+"×"+h+"@"+hz+" — likely too wide for this link; will restore if it fails.");
        submit(() -> {
            applying.set(true);
            runOnUiThread(()->{ stConn.setText(tag+"Applying "+w+"×"+h+"@"+hz+"…"); stConn.setTextColor(WARN); });
            try {
                su("mount -t debugfs none /sys/kernel/debug 2>/dev/null; "
                    + "echo \""+w+" "+h+" "+hz+" 0\" > "+DBG+"edid_modes");
                su("echo 0 > "+DBG+"hpd"); sleep(1300);
                su("echo 1 > "+DBG+"hpd");
                boolean back=waitConnected(12000);           // poll instead of a fixed sleep
                String got=back?curResolution():"—";
                if(back && sameMode(got,w,h,hz)){
                    if(!auto && mid!=null)                    // remember the user's choice per monitor
                        prefs.edit().putString("mode_"+mid, w+"x"+h+"@"+hz).apply();
                    toast(tag+"✓ "+w+"×"+h+"@"+hz+" — live, no replug");
                } else {
                    // The requested timing didn't train (over-budget mode, or the sink
                    // refused it) — never leave the panel blank. Restore the monitor's
                    // own default so it always comes back to a usable picture.
                    String restored=recoverDefault();
                    if(!fits)      toast(tag+"Wouldn't fit this link (needs a 4-lane USB-C→DP cable). Restored "+restored+".");
                    else if(!back) toast(tag+"Didn't re-train in time. Restored "+restored+".");
                    else           toast(tag+"Rejected (got "+got+"). Restored "+restored+".");
                }
            } catch(Throwable t){
                recoverDefault();
            } finally {
                applying.set(false);
                reload();
            }
        });
    }

    // Identify the connected monitor from its EDID (manufacturer/product/serial + base
    // timings all live in the 128-byte base block), so we can remember a per-monitor
    // choice. Stable across replugs; null when no EDID is readable.
    String readMonitorId(){
        String conn=connectorDir; if(conn==null) return null;
        try{
            String hex=su("cat "+conn+"/edid 2>/dev/null | od -An -tx1 | tr -dc '0-9a-f'").trim();
            if(hex.length()<32) return null;                          // no / stub EDID
            String base=hex.substring(0, Math.min(hex.length(),256)); // base block
            return Integer.toHexString(base.hashCode());
        }catch(Exception e){ return null; }
    }

    // Pick the mode to auto-apply: the one remembered for this monitor (if it still
    // advertises it), else the highest res/refresh that fits the trained link. `modes`
    // is pre-sorted largest-first, so the first fitting entry is the best available.
    int[] chooseAuto(List<int[]> modes,long budget,String monId){
        if(modes==null||modes.isEmpty()) return null;
        if(monId!=null){
            int[] r=parseKey(prefs.getString("mode_"+monId,null));
            if(r!=null) for(int[] m:modes) if(m[0]==r[0]&&m[1]==r[1]&&m[2]==r[2]) return r;
        }
        for(int[] m:modes){
            long need=(long)m[0]*m[1]*m[2]*DSC_FLOOR_BPP;
            if(budget<=0 || need<=budget) return m;
        }
        return modes.get(0);                                          // nothing fits: try the top
    }
    // "5120x2160@100" -> {5120,2160,100}
    int[] parseKey(String k){ if(k==null) return null; try{ String[] a=k.split("[x@]");
        return new int[]{Integer.parseInt(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[2])}; }catch(Exception e){ return null; } }

    // Clear any mode override and re-probe → the sink comes up at its native default,
    // which is always trainable on the current link. Returns the resolution reached.
    String recoverDefault(){
        su("mount -t debugfs none /sys/kernel/debug 2>/dev/null; echo \"0 0 0 0\" > "+DBG+"edid_modes");
        su("echo 0 > "+DBG+"hpd"); sleep(1400);
        su("echo 1 > "+DBG+"hpd");
        boolean back=waitConnected(10000);
        return back?curResolution():"no signal";
    }

    void reprobeCurrent() {
        if(!present){toast("No external display");return;}
        submit(() -> {
            applying.set(true);
            try { su("echo 0 > "+DBG+"hpd"); sleep(1300); su("echo 1 > "+DBG+"hpd");
                  boolean back=waitConnected(10000); toast(back?"Re-probed — "+curResolution():"Re-probed — no signal"); }
            finally { applying.set(false); reload(); }
        });
    }
    void clearOverride() {
        final String mid=curMonitorId;
        submit(() -> {
            applying.set(true);
            try {
                if(mid!=null) prefs.edit().remove("mode_"+mid).apply();  // forget the remembered choice
                autoHandledId=mid;                                       // stay at default until replug
                String r=recoverDefault(); toast("Cleared — monitor default ("+r+"), forgot remembered mode");
            }
            finally { applying.set(false); reload(); }
        });
    }

    // Poll the DP status + a parseable resolution until the sink re-trains, or timeout.
    boolean waitConnected(long timeoutMs){
        long waited=0;
        while(waited<timeoutMs){
            String st=su(statusPath()).trim();
            if("connected".equals(st) && isRes(curResolution())) return true;
            sleep(500); waited+=500;
        }
        return "connected".equals(su(statusPath()).trim());
    }
    String statusPath(){ String c=connectorDir; return "cat "+(c!=null?c:"/sys/class/drm/card0-DP-1")+"/status 2>/dev/null"; }

    // ---------- readers ----------
    String curResolution(){ for(String ln:rd("dp_debug").split("\n")){ String t=ln.trim();
        if(t.startsWith("resolution=")) return t.substring(11);} return "—"; }
    boolean isRes(String r){ return parseRes(r)!=null; }

    // DSC state for the active mode: estimate the compressed bpp the driver targets
    // (highest quality that fits the current link) and the resulting ratio from 30bpp source.
    String dscStatus(int lanes,long rateKhz,String res){
        int[] whr=parseRes(res); if(whr==null) return "—";
        long px=(long)whr[0]*whr[1]*whr[2];
        long budget=linkBudget(lanes,rateKhz); if(budget<=0) return "—";
        double uncompressed=px*30.0;
        if(uncompressed<=budget) return "off · "+gbps((long)uncompressed)+" fits uncompressed";
        double fitBpp=(double)budget/px;                 // max compressed bpp that fits
        if(fitBpp<DSC_FLOOR_BPP) fitBpp=DSC_FLOOR_BPP;    // driver floor
        double ratio=30.0/fitBpp;
        return String.format(Locale.US,"engaged · ~%.0f bpp · %.1f:1  (%s→%s)",
            fitBpp,ratio,gbps((long)uncompressed),gbps(budget));
    }
    String hdcpStatus(String s){ if(s==null)return"—"; String l=s.toLowerCase();
        if(l.contains("authenticated")||l.contains("state_auth_success")) return "authenticated";
        if(l.contains("auth_fail")) return "auth failed"; if(l.contains("2p2")||l.contains("1x")) return "negotiating"; return "—"; }

    // Per-lane data rate derived from the LIVE trained link rate (DP standard rates),
    // not an assumed HBR3 ceiling — so the budget is right on HBR2/HBR/RBR links too.
    long laneBps(long rateKhz){
        if(rateKhz>=810000) return 8_100_000_000L;   // HBR3
        if(rateKhz>=540000) return 5_400_000_000L;   // HBR2
        if(rateKhz>=270000) return 2_700_000_000L;   // HBR
        if(rateKhz>=162000) return 1_620_000_000L;   // RBR
        return 0;                                     // unknown → no budget claim
    }
    long linkBudget(int lanes,long rateKhz){ if(lanes<=0) return 0; return (long)(lanes*laneBps(rateKhz)*ENC_8B10B); }
    String gbps(long bps){ return String.format(Locale.US,"%.1f Gbps",bps/1e9); }
    String linkText(long rateKhz,int lanes){
        String r=rateKhz>=810000?"HBR3":rateKhz>=540000?"HBR2":rateKhz>=270000?"HBR":rateKhz>=162000?"RBR":"?";
        long budget=linkBudget(lanes,rateKhz);
        return r+" ×"+lanes+(budget>0?("  ("+gbps(budget)+" usable)"):""); }

    int readDensity(int id){ String o=su("wm density -d "+id); int phys=DPI_MIN,over=-1;
        for(String ln:o.split("\n")){ try{
            if(ln.contains("Override density:")) over=Integer.parseInt(ln.replaceAll("\\D+",""));
            else if(ln.contains("Physical density:")) phys=Integer.parseInt(ln.replaceAll("\\D+",""));}catch(Exception e){}}
        return over>0?over:phys; }
    void nudge(int d){ if(extDisplayId<0){toast("No external display");return;}
        int dpi=Math.max(DPI_MIN,Math.min(DPI_MAX,DPI_MIN+scaleBar.getProgress()+d));
        scaleBar.setProgress(dpi-DPI_MIN); su("wm density "+dpi+" -d "+extDisplayId); toast("UI scale → "+dpi+" dpi"); }

    // Generic external-display pick — no brand names. Presentation category is the
    // authoritative "is external + suitable" signal; fall back to a non-default
    // display that carries FLAG_PRESENTATION (excludes the fold's internal panels).
    Display pickExternal(DisplayManager dm){
        try{ Display[] pres=dm.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            if(pres!=null&&pres.length>0) return pres[0]; }catch(Exception e){}
        try{ for(Display d:dm.getDisplays()){
            if(d.getDisplayId()==Display.DEFAULT_DISPLAY) continue;
            if((d.getFlags()&Display.FLAG_PRESENTATION)!=0) return d; } }catch(Exception e){}
        return null;
    }
    int findExternal(){ try{ Display d=pickExternal(dm); return d==null?-1:d.getDisplayId(); }catch(Exception e){ return -1; } }

    // ---------- parse helpers ----------
    int parseI(String kv,int def){ try{ return Integer.parseInt(kv.replaceAll(".*=","").replaceAll("[^0-9].*","").trim()); }catch(Exception e){ return def; } }
    long parseL(String kv,long def){ try{ return Long.parseLong(kv.replaceAll(".*=","").replaceAll("[^0-9].*","").trim()); }catch(Exception e){ return def; } }
    // "5120x2160@60Hz" -> {5120,2160,60}
    int[] parseRes(String r){ try{ if(r==null)return null; String s=r.replace("Hz","").trim(); String[] a=s.split("[x@]");
        if(a.length<3) return null;
        return new int[]{Integer.parseInt(a[0].trim()),Integer.parseInt(a[1].trim()),Integer.parseInt(a[2].trim())}; }catch(Exception e){ return null; } }
    boolean sameMode(String res,int w,int h,int hz){ int[] a=parseRes(res); return a!=null&&a[0]==w&&a[1]==h&&a[2]==hz; }

    // ---------- ui helpers ----------
    int dp(int v){ return Math.round(v*getResources().getDisplayMetrics().density); }
    LinearLayout card(){ LinearLayout c=new LinearLayout(this); c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(dp(16),dp(14),dp(16),dp(14)); GradientDrawable g=new GradientDrawable();
        g.setColor(CARD); g.setCornerRadius(dp(14)); c.setBackground(g);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,0,dp(6)); c.setLayoutParams(lp); return c; }
    TextView kv(LinearLayout p,String k,String v){ LinearLayout row=new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL); row.setPadding(0,dp(3),0,dp(3));
        TextView kk=new TextView(this); kk.setText(k); kk.setTextColor(SUBTLE); kk.setTextSize(13); kk.setWidth(dp(96));
        TextView vv=new TextView(this); vv.setText(v); vv.setTextColor(TEXT); vv.setTextSize(14);
        vv.setTypeface(vv.getTypeface(),Typeface.BOLD);
        row.addView(kk); row.addView(vv); p.addView(row); return vv; }
    TextView section(String s){ TextView t=new TextView(this); t.setText(s.toUpperCase(Locale.US));
        t.setTextColor(ACCENT); t.setTextSize(12); t.setTypeface(t.getTypeface(),Typeface.BOLD);
        t.setLetterSpacing(0.08f); t.setPadding(dp(2),dp(20),0,dp(8)); return t; }
    Button btn(String t,int color,boolean emph,boolean half){
        Button b=new Button(this); b.setText(t); b.setAllCaps(false); b.setTextColor(TEXT); b.setTextSize(14);
        if(emph) b.setTypeface(b.getTypeface(),Typeface.BOLD);
        GradientDrawable g=new GradientDrawable(); g.setColor(color); g.setCornerRadius(dp(12));
        if(emph) g.setStroke(dp(1),ACCENT); b.setBackground(g);
        b.setPadding(dp(16),dp(13),dp(16),dp(13)); b.setGravity(Gravity.CENTER_VERTICAL|Gravity.START);
        LinearLayout.LayoutParams lp = half
            ? new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1f)
            : new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(dp(3),dp(4),dp(3),dp(4)); b.setLayoutParams(lp); return b; }

    String scaleText(int dpi){ int pct=Math.round(dpi*100f/160f); return "Density "+dpi+" dpi  (~"+pct+"% — drag right = larger)"; }
    void sleep(long ms){ try{ Thread.sleep(ms); }catch(InterruptedException e){} }
    String su(String cmd){ try{ Process p=Runtime.getRuntime().exec(new String[]{"su","-c",cmd});
        BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream())); StringBuilder sb=new StringBuilder(); String l;
        while((l=r.readLine())!=null) sb.append(l).append("\n"); p.waitFor(); return sb.toString(); }catch(Exception e){ return ""; } }
    void toast(String s){ runOnUiThread(()->Toast.makeText(this,s,Toast.LENGTH_LONG).show()); }
}
