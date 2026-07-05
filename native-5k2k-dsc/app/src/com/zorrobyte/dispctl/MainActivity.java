package com.zorrobyte.dispctl;

import android.app.Activity;
import android.os.Bundle;
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

// 5K Display Control (v2) — root app for the Razr Fold 2026 external-display unlock.
//
// LIVE mode switching, NO physical replug: writes the QTI mode_override (edid_modes)
// then cycles the DP hpd node 0->1, which re-probes + re-runs DSC and the framework
// re-adds the display at the new timing. See ../LIVE-MODE-SWITCHING.md.
//
// Modes are bandwidth-gated to the CURRENT trained link: 5120@100 needs a 4-lane
// direct USB-C->DP cable; a 2-lane dock caps at 5120@60. Every mode that can't fit
// the current link's budget (DSC floor 18bpp) is flagged, and after applying we
// verify the real resolution and report honestly.
public class MainActivity extends Activity {

    // palette
    static final int BG=0xFF0E1216, CARD=0xFF161C22, CARD2=0xFF1B232B, ACCENT=0xFF3B9EFF,
        ACCENT_HI=0xFF1D6FB8, GOOD=0xFF39D98A, WARN=0xFFFFB454, BAD=0xFFFF6B6B,
        TEXT=0xFFECF2F8, SUBTLE=0xFF9FB2C4, DIM=0xFF66727E;

    // DP link constants (SM8845 = DP1.4 / HBR3 8.1 Gbps/lane, DSC floor 18bpp)
    static final long HBR3_LANE_BPS = 8_100_000_000L;
    static final double ENC_8B10B = 0.8;      // 8b/10b payload efficiency
    static final int DSC_FLOOR_BPP = 18;      // driver's min compressed bpp

    static final String DBG="/sys/kernel/debug/drm_dp/";
    static final int DPI_MIN=96, DPI_MAX=360;

    LinearLayout list;
    TextView stConn, stMode, stLink, stDsc, stHdcp, scaleLabel;
    SeekBar scaleBar;
    int extDisplayId=-1;
    int curLanes=2;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
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
        list=new LinearLayout(this); list.setOrientation(LinearLayout.VERTICAL); root.addView(list);

        LinearLayout ctl=new LinearLayout(this); ctl.setOrientation(LinearLayout.HORIZONTAL);
        Button re=btn("↻  Re-probe link",CARD2,false,true);
        Button cl=btn("Clear override",CARD2,false,true);
        re.setOnClickListener(v->reprobeCurrent());
        cl.setOnClickListener(v->clearOverride());
        ctl.addView(re); ctl.addView(cl); root.addView(ctl);

        root.addView(section("UI scale — external display"));
        scaleLabel=new TextView(this); scaleLabel.setTextColor(SUBTLE); scaleLabel.setTextSize(13);
        scaleLabel.setPadding(0,0,0,dp(4)); root.addView(scaleLabel);
        scaleBar=new SeekBar(this); scaleBar.setMax(DPI_MAX-DPI_MIN);
        scaleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            public void onProgressChanged(SeekBar s,int p,boolean u){ if(scaleLabel!=null) scaleLabel.setText(scaleText(DPI_MIN+p)); }
            public void onStartTrackingTouch(SeekBar s){}
            public void onStopTrackingTouch(SeekBar s){
                if(extDisplayId<0){toast("No external display — tap Re-probe");return;}
                int dpi=DPI_MIN+s.getProgress(); su("wm density "+dpi+" -d "+extDisplayId); toast("UI scale → "+dpi+" dpi");
            }
        });
        root.addView(scaleBar);
        LinearLayout sr=new LinearLayout(this); sr.setOrientation(LinearLayout.HORIZONTAL);
        Button sm=btn("Smaller –",CARD2,false,true), bg=btn("Larger +",CARD2,false,true), rs=btn("Reset",CARD2,false,true);
        sm.setOnClickListener(v->nudge(-20)); bg.setOnClickListener(v->nudge(20));
        rs.setOnClickListener(v->{ if(extDisplayId<0){toast("No external display");return;} su("wm density reset -d "+extDisplayId); toast("UI scale reset"); reload();});
        sr.addView(sm); sr.addView(bg); sr.addView(rs); root.addView(sr);

        setContentView(sv);
        reload();
    }

    // ---------- state ----------
    String rd(String node){ return su("cat "+DBG+node+" 2>/dev/null"); }

    void reload() {
        new Thread(() -> {
            su("mount -t debugfs none /sys/kernel/debug 2>/dev/null");
            String dp=rd("dp_debug");
            String hdcp=rd("hdcp");
            String status=su("cat /sys/class/drm/card0-DP-1/status").trim();

            String curRes="—"; int lanes=2; long linkRate=0; int bpp=0;
            for(String ln:dp.split("\n")){
                String t=ln.trim();
                if(t.startsWith("resolution=")) curRes=t.substring(11);
                else if(t.startsWith("num_lanes=")) lanes=parseI(t,2);
                else if(t.startsWith("link_rate=")) linkRate=parseL(t,0);
                else if(t.startsWith("bpp=")) bpp=parseI(t,0);
            }
            curLanes=lanes;
            final boolean connected="connected".equals(status);
            final String fRes=curRes; final int fLanes=lanes; final long fRate=linkRate; final int fBpp=bpp;

            // build candidate mode list from the framework + hero 5120 modes
            final List<int[]> modes=collectModes();
            extDisplayId=findExternal();
            final int dens=extDisplayId>=0?readDensity(extDisplayId):DPI_MIN;
            final String dscTxt=dscStatus(fLanes,fRes);
            final String hdcpTxt=hdcpStatus(hdcp);

            runOnUiThread(() -> {
                stConn.setText(connected?("Connected  ·  "+fLanes+" lane"+(fLanes==1?"":"s")):"No external display");
                stConn.setTextColor(connected?GOOD:SUBTLE);
                stMode.setText(connected?fRes:"—"); stMode.setTextColor(TEXT);
                stLink.setText(connected?linkText(fRate,fLanes):"—"); stLink.setTextColor(SUBTLE);
                stDsc.setText(dscTxt); stDsc.setTextColor(dscTxt.startsWith("engaged")?GOOD:SUBTLE);
                stHdcp.setText(hdcpTxt); stHdcp.setTextColor(hdcpTxt.startsWith("auth")?GOOD:(hdcpTxt.equals("—")?SUBTLE:WARN));

                int cl=Math.max(DPI_MIN,Math.min(DPI_MAX,dens));
                scaleBar.setProgress(cl-DPI_MIN);
                scaleLabel.setText(extDisplayId>=0?scaleText(dens):"No external display");

                list.removeAllViews();
                if(!connected||modes.isEmpty()){
                    TextView t=new TextView(this);
                    t.setText(connected?"No modes reported — tap Re-probe.":
                        "Connect a monitor (direct USB-C→DP cable for 4-lane / native 5120), then Re-probe.");
                    t.setTextColor(BAD); t.setPadding(0,dp(8),0,dp(8)); list.addView(t);
                }
                long budget=linkBudget(fLanes);
                for(int[] m:modes){
                    final int w=m[0],h=m[1],hz=m[2];
                    long need=(long)w*h*hz*DSC_FLOOR_BPP;
                    boolean fits=need<=budget;
                    boolean is5k=(long)w*h>=5120L*2160;
                    boolean active=sameMode(fRes,w,h,hz);
                    String badge=is5k?"  ★ 5K2K":((long)w*h>=3840L*2160?"  4K":((long)w*h>=3440L*1440?"  UW":""));
                    String label=(active?"●  ":"")+w+" × "+h+"  @ "+hz+" Hz"+badge;
                    if(!fits) label+="\n    ⚠ needs 4-lane cable ("+gbps(need)+" > "+gbps(budget)+" on "+fLanes+" lanes)";
                    Button bb=btn(label, is5k?ACCENT_HI:CARD, is5k, false);
                    if(!fits) bb.setTextColor(DIM);
                    bb.setOnClickListener(v->applyMode(w,h,hz,fits));
                    list.addView(bb);
                }
            });
        }).start();
    }

    // Fully dynamic per-monitor mode list — nothing hardcoded. Two dynamic sources
    // merged: (1) the monitor's raw EDID (DTDs + DisplayID detailed timings — this is
    // where the ultrawide's 5120@60/100/165 live, and it survives the mode_override
    // collapse because raw EDID is always complete); (2) the framework's
    // getSupportedModes() (adds CEA/standard modes when not collapsed).
    List<int[]> collectModes() {
        LinkedHashMap<String,int[]> u=new LinkedHashMap<>();
        for(int[] m:parseEdidModes()) u.put(m[0]+"x"+m[1]+"@"+m[2],m);
        try {
            DisplayManager dm=(DisplayManager)getSystemService(DISPLAY_SERVICE);
            Display ext=pickExternal(dm);
            if(ext!=null) for(Display.Mode m:ext.getSupportedModes()){
                int w=m.getPhysicalWidth(),h=m.getPhysicalHeight(),hz=Math.round(m.getRefreshRate());
                if(w>=640&&h>=480) u.put(w+"x"+h+"@"+hz,new int[]{w,h,hz});
            }
        } catch(Exception e){}
        List<int[]> out=new ArrayList<>(u.values());
        Collections.sort(out,(a,c)->Long.compare((long)c[0]*c[1]*c[2],(long)a[0]*a[1]*a[2]));
        return out;
    }

    // Parse the connected monitor's raw EDID for real timings (base + CEA DTDs and
    // DisplayID Type I/VII detailed timings). Returns {w,h,hz} for each.
    List<int[]> parseEdidModes() {
        List<int[]> out=new ArrayList<>();
        try {
            String hex=su("cat /sys/class/drm/card0-DP-1/edid 2>/dev/null | od -An -tx1 | tr -dc '0-9a-f'").trim();
            int n=hex.length()/2; if(n<128) return out;
            int[] b=new int[n];
            for(int i=0;i<n;i++) b[i]=Integer.parseInt(hex.substring(i*2,i*2+2),16);
            int blocks=n/128;
            for(int off:new int[]{54,72,90,108}) addDtd(out,b,0,off);      // base block DTDs
            for(int blk=1;blk<blocks;blk++){
                int base=blk*128, tag=b[base];
                if(tag==0x02){                                            // CEA-861: DTDs after byte[2]
                    int d=b[base+2];
                    if(d>=4){ int off=d; while(off+18<=127 && (b[base+off]|b[base+off+1])!=0){ addDtd(out,b,base,off); off+=18; } }
                }
                if(tag==0x70||tag==0x20){                                 // DisplayID: detailed-timing data blocks
                    int p=5;
                    while(p+3<128){ int dt=b[base+p], dl=b[base+p+2];
                        if(dt==0&&dl==0) break;
                        if(dt==0x03||dt==0x22){ int pp=base+p+3; for(int i=0;i+20<=dl;i+=20) addDid(out,b,pp+i); }
                        p+=3+dl; }
                }
            }
        } catch(Exception e){}
        return out;
    }
    void addDtd(List<int[]> out,int[] b,int base,int off){
        int d=base+off; int pclk=(b[d+1]<<8|b[d])*10; if(pclk==0) return;      // kHz
        int ha=b[d+2]|((b[d+4]&0xF0)<<4), hb=b[d+3]|((b[d+4]&0x0F)<<8);
        int va=b[d+5]|((b[d+7]&0xF0)<<4), vb=b[d+6]|((b[d+7]&0x0F)<<8);
        int ht=ha+hb, vt=va+vb; if(ht<=0||vt<=0) return;
        addMode(out,ha,va,(int)Math.round(pclk*1000.0/(ht*vt)));
    }
    void addDid(List<int[]> out,int[] b,int d){
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
    // LIVE apply: mode_override + hpd 0->1 re-probe (no skip_uevent, no physical replug).
    void applyMode(int w,int h,int hz,boolean fits) {
        if(extDisplayId<0){toast("No external display");return;}
        new Thread(() -> {
            toast("Applying "+w+"×"+h+"@"+hz+" …");
            su("mount -t debugfs none /sys/kernel/debug 2>/dev/null; "
                + "echo \""+w+" "+h+" "+hz+" 0\" > "+DBG+"edid_modes");
            su("echo 0 > "+DBG+"hpd"); sleep(1300);
            su("echo 1 > "+DBG+"hpd"); sleep(3800);
            String got=curResolution();
            if(sameMode(got,w,h,hz)) toast("✓ "+w+"×"+h+"@"+hz+" — live, no replug");
            else if(!fits) toast("Rejected (got "+got+"). This mode needs a 4-lane direct USB-C→DP cable.");
            else toast("Rejected (got "+got+"). Timing not accepted on this link — try Re-probe or a direct cable.");
            reload();
        }).start();
    }

    void reprobeCurrent() {
        if(extDisplayId<0){toast("No external display");return;}
        new Thread(() -> { su("echo 0 > "+DBG+"hpd"); sleep(1300); su("echo 1 > "+DBG+"hpd"); sleep(3500);
            toast("Re-probed"); reload(); }).start();
    }
    void clearOverride() {
        new Thread(() -> { su("echo \"0 0 0 0\" > "+DBG+"edid_modes"); su("echo 0 > "+DBG+"hpd"); sleep(1200);
            su("echo 1 > "+DBG+"hpd"); sleep(3500); toast("Cleared — back to monitor default"); reload(); }).start();
    }

    // ---------- readers ----------
    String curResolution(){ for(String ln:rd("dp_debug").split("\n")){ String t=ln.trim();
        if(t.startsWith("resolution=")) return t.substring(11);} return "—"; }

    // DSC state for the active mode: estimate the compressed bpp the driver targets
    // (highest quality that fits the current link) and the resulting ratio from 30bpp source.
    String dscStatus(int lanes,String res){
        int[] whr=parseRes(res); if(whr==null) return "—";
        long px=(long)whr[0]*whr[1]*whr[2];
        long budget=linkBudget(lanes);
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

    long linkBudget(int lanes){ return (long)(lanes*HBR3_LANE_BPS*ENC_8B10B); }
    String gbps(long bps){ return String.format(Locale.US,"%.1f Gbps",bps/1e9); }
    String linkText(long rateKhz,int lanes){
        String r=rateKhz>=810000?"HBR3":rateKhz>=540000?"HBR2":rateKhz>=270000?"HBR":"?";
        return r+" ×"+lanes+"  ("+gbps(linkBudget(lanes))+" usable)"; }

    int readDensity(int id){ String o=su("wm density -d "+id); int phys=DPI_MIN,over=-1;
        for(String ln:o.split("\n")){ try{
            if(ln.contains("Override density:")) over=Integer.parseInt(ln.replaceAll("\\D+",""));
            else if(ln.contains("Physical density:")) phys=Integer.parseInt(ln.replaceAll("\\D+",""));}catch(Exception e){}}
        return over>0?over:phys; }
    void nudge(int d){ if(extDisplayId<0){toast("No external display");return;}
        int dpi=Math.max(DPI_MIN,Math.min(DPI_MAX,DPI_MIN+scaleBar.getProgress()+d));
        scaleBar.setProgress(dpi-DPI_MIN); su("wm density "+dpi+" -d "+extDisplayId); toast("UI scale → "+dpi+" dpi"); }

    Display pickExternal(DisplayManager dm){
        try{ Display[] pres=dm.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            if(pres!=null&&pres.length>0) return pres[0]; }catch(Exception e){}
        for(Display d:dm.getDisplays()){ if(d.getDisplayId()==Display.DEFAULT_DISPLAY) continue;
            String n=d.getName()==null?"":d.getName().toLowerCase();
            if(n.contains("hdmi")||n.contains("dp")||n.contains("screen")||n.contains("ultra")||n.contains("odyssey")) return d; }
        for(Display d:dm.getDisplays()) if(d.getDisplayId()!=Display.DEFAULT_DISPLAY) return d;
        return null;
    }
    int findExternal(){ try{ Display d=pickExternal((DisplayManager)getSystemService(DISPLAY_SERVICE));
        return d==null?-1:d.getDisplayId(); }catch(Exception e){ return -1; } }

    // ---------- parse helpers ----------
    int parseI(String kv,int def){ try{ return Integer.parseInt(kv.replaceAll(".*=","").replaceAll("[^0-9].*","").trim()); }catch(Exception e){ return def; } }
    long parseL(String kv,long def){ try{ return Long.parseLong(kv.replaceAll(".*=","").replaceAll("[^0-9].*","").trim()); }catch(Exception e){ return def; } }
    // "5120x2160@60Hz" -> {5120,2160,60}
    int[] parseRes(String r){ try{ String s=r.replace("Hz","").trim(); String[] a=s.split("[x@]");
        return new int[]{Integer.parseInt(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[2])}; }catch(Exception e){ return null; } }
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
