package com.zorrobyte.dispctl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.view.Display;
import java.io.*;
import java.util.*;

// 5K Display Control — root app to force the external DisplayPort mode via the
// QTI mode_override (drm_dp/edid_modes). Reads whatever monitor is plugged in,
// lists its modes (incl. the DSC-enabled native ones once msm_drm.ko is patched),
// you tap one, it writes mode_override; replug the monitor to apply.
public class MainActivity extends Activity {
    LinearLayout list;
    TextView status, scaleLabel;
    SeekBar scaleBar;
    int extDisplayId = -1;       // logical id of the external display (for `wm density -d`)
    static final int DPI_MIN = 96, DPI_MAX = 360;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        ScrollView sv = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(44, 64, 44, 44);
        root.setBackgroundColor(Color.parseColor("#0e1216"));
        sv.addView(root);

        TextView title = new TextView(this);
        title.setText("5K Display Control");
        title.setTextColor(Color.WHITE); title.setTextSize(26);
        root.addView(title);

        status = new TextView(this);
        status.setTextColor(Color.parseColor("#9fd0ff")); status.setTextSize(14);
        status.setPadding(0, 16, 0, 22);
        root.addView(status);

        list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        root.addView(list);

        // ---- UI scale (display density) ----
        TextView scaleHdr = new TextView(this);
        scaleHdr.setText("UI scale — external display");
        scaleHdr.setTextColor(Color.WHITE); scaleHdr.setTextSize(18);
        scaleHdr.setPadding(0, 30, 0, 4);
        root.addView(scaleHdr);

        scaleLabel = new TextView(this);
        scaleLabel.setTextColor(Color.parseColor("#9fd0ff")); scaleLabel.setTextSize(14);
        scaleLabel.setPadding(0, 0, 0, 6);
        root.addView(scaleLabel);

        scaleBar = new SeekBar(this);
        scaleBar.setMax(DPI_MAX - DPI_MIN);
        scaleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar s, int p, boolean fromUser) {
                if (scaleLabel != null) scaleLabel.setText(scaleText(DPI_MIN + p));
            }
            public void onStartTrackingTouch(SeekBar s) {}
            public void onStopTrackingTouch(SeekBar s) {        // apply on release
                int dpi = DPI_MIN + s.getProgress();
                if (extDisplayId < 0) { toast("No external display found — tap Refresh"); return; }
                su("wm density " + dpi + " -d " + extDisplayId);
                toast("UI scale set to " + dpi + " dpi");
            }
        });
        root.addView(scaleBar);

        LinearLayout scaleRow = new LinearLayout(this);
        scaleRow.setOrientation(LinearLayout.HORIZONTAL);
        Button smaller = btn("Smaller –", "#2a3138");
        Button bigger  = btn("Larger +",  "#2a3138");
        Button resetD  = btn("Reset",     "#3a2a2a");
        LinearLayout.LayoutParams half = new LinearLayout.LayoutParams(0,
            LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        half.setMargins(0, 8, 8, 8);
        smaller.setLayoutParams(half); bigger.setLayoutParams(half); resetD.setLayoutParams(half);
        smaller.setOnClickListener(v -> nudge(-20));
        bigger.setOnClickListener(v -> nudge(+20));
        resetD.setOnClickListener(v -> {
            if (extDisplayId < 0) { toast("No external display"); return; }
            su("wm density reset -d " + extDisplayId);
            toast("UI scale reset to default");
            reload();
        });
        scaleRow.addView(smaller); scaleRow.addView(bigger); scaleRow.addView(resetD);
        root.addView(scaleRow);

        Button refresh = btn("⟳  Refresh / re-read monitor", "#2a3138");
        refresh.setOnClickListener(v -> reload());
        root.addView(refresh);

        Button clear = btn("Clear override (back to Auto / stock)", "#3a2a2a");
        clear.setOnClickListener(v -> {
            su("echo \"0 0 0 0\" > /sys/kernel/debug/drm_dp/edid_modes");
            toast("Cleared — replug the monitor to revert to default");
        });
        root.addView(clear);

        setContentView(sv);
        reload();
    }

    Button btn(String t, String color) {
        Button b = new Button(this);
        b.setText(t); b.setAllCaps(false); b.setTextColor(Color.WHITE);
        b.setBackgroundColor(Color.parseColor(color));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 8, 0, 8); b.setLayoutParams(lp);
        return b;
    }

    String scaleText(int dpi) {
        // 160 dpi = 1.0x baseline (mdpi). Show dpi + relative scale.
        int pct = Math.round(dpi * 100f / 160f);
        return "Density " + dpi + " dpi  (~" + pct + "% — drag right = larger)";
    }

    // Find the external display's logical id (matches `wm density -d <id>`).
    int findExternalDisplay() {
        try {
            DisplayManager dm = (DisplayManager) getSystemService(DISPLAY_SERVICE);
            for (Display d : dm.getDisplays()) {
                if (d.getDisplayId() == Display.DEFAULT_DISPLAY) continue;
                String n = d.getName() == null ? "" : d.getName().toLowerCase();
                if (n.contains("hdmi") || n.contains("dp") || n.contains("ultragear")
                        || n.contains("odyssey") || n.contains("screen"))
                    return d.getDisplayId();
            }
            // fallback: first non-default display
            for (Display d : dm.getDisplays())
                if (d.getDisplayId() != Display.DEFAULT_DISPLAY) return d.getDisplayId();
        } catch (Exception e) { }
        return -1;
    }

    // Read the current density for a display from `wm density -d <id>`.
    int readDensity(int id) {
        String o = su("wm density -d " + id);
        int phys = DPI_MIN, over = -1;
        for (String ln : o.split("\n")) {
            try {
                if (ln.contains("Override density:")) over = Integer.parseInt(ln.replaceAll("\\D+", ""));
                else if (ln.contains("Physical density:")) phys = Integer.parseInt(ln.replaceAll("\\D+", ""));
            } catch (Exception e) { }
        }
        return over > 0 ? over : phys;
    }

    void nudge(int delta) {
        if (extDisplayId < 0) { toast("No external display found — tap Refresh"); return; }
        int dpi = Math.max(DPI_MIN, Math.min(DPI_MAX, DPI_MIN + scaleBar.getProgress() + delta));
        scaleBar.setProgress(dpi - DPI_MIN);
        su("wm density " + dpi + " -d " + extDisplayId);
        toast("UI scale set to " + dpi + " dpi");
    }

    void reload() {
        new Thread(() -> {
            String dp = su("mount -t debugfs none /sys/kernel/debug 2>/dev/null; "
                + "cat /sys/kernel/debug/drm_dp/dp_debug");
            String modes = su("cat /sys/kernel/debug/drm_dp/edid_modes");
            String st = su("cat /sys/class/drm/card0-DP-1/status").trim();
            String cur = "unknown";
            for (String ln : dp.split("\n")) if (ln.contains("resolution=")) cur = ln.trim();
            final String header = "Current: " + cur + "   [" + st + "]";
            final List<String[]> ms = parseModes(modes);
            extDisplayId = findExternalDisplay();
            final int dens = extDisplayId >= 0 ? readDensity(extDisplayId) : DPI_MIN;
            runOnUiThread(() -> {
                int clamped = Math.max(DPI_MIN, Math.min(DPI_MAX, dens));
                scaleBar.setProgress(clamped - DPI_MIN);
                scaleLabel.setText(extDisplayId >= 0
                    ? scaleText(dens) + "   [display " + extDisplayId + "]"
                    : "No external display detected");
            });
            runOnUiThread(() -> {
                status.setText(header + "\nTap a mode, then UNPLUG + REPLUG the monitor to apply it.");
                list.removeAllViews();
                if (ms.isEmpty()) {
                    TextView t = new TextView(this);
                    t.setText("No modes found. Make sure root is granted, a monitor is connected, "
                        + "then tap Refresh.");
                    t.setTextColor(Color.parseColor("#ffb0b0")); t.setPadding(0, 12, 0, 12);
                    list.addView(t);
                }
                for (String[] m : ms) {
                    Button b = btn(m[0], "#1d6fb8");
                    final String arg = m[1]; final String lbl = m[0];
                    b.setOnClickListener(v -> {
                        su("echo \"" + arg + "\" > /sys/kernel/debug/drm_dp/edid_modes");
                        toast("Set " + lbl + "\nNow UNPLUG + REPLUG the monitor.");
                    });
                    list.addView(b);
                }
            });
        }).start();
    }

    // edid_modes lines: "WxH vrefresh aspect htotal vtotal clock flags"
    List<String[]> parseModes(String modes) {
        LinkedHashMap<String, String[]> uniq = new LinkedHashMap<>();
        if (modes != null) for (String ln : modes.split("\n")) {
            String[] f = ln.trim().split("\\s+");
            if (f.length < 3) continue;
            String[] wh = f[0].split("x");
            if (wh.length != 2) continue;
            try {
                int w = Integer.parseInt(wh[0]), h = Integer.parseInt(wh[1]);
                int hz = Integer.parseInt(f[1]), aspect = Integer.parseInt(f[2]);
                String key = w + "x" + h + "@" + hz;
                if (uniq.containsKey(key)) continue;
                String label = w + " × " + h + "   @ " + hz + " Hz";
                if ((long) w * h >= 5120L * 2160) label += "    ★ 5K2K (DSC)";
                else if ((long) w * h >= 3840L * 2160) label += "    4K";
                else if ((long) w * h >= 3440L * 1440) label += "    UW";
                uniq.put(key, new String[]{label, w + " " + h + " " + hz + " " + aspect,
                    String.valueOf((long) w * h * hz)});
            } catch (Exception e) { }
        }
        List<String[]> out = new ArrayList<>(uniq.values());
        Collections.sort(out, (a, c) -> Long.compare(Long.parseLong(c[2]), Long.parseLong(a[2])));
        return out;
    }

    String su(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder sb = new StringBuilder(); String l;
            while ((l = r.readLine()) != null) sb.append(l).append("\n");
            p.waitFor();
            return sb.toString();
        } catch (Exception e) { return ""; }
    }

    void toast(String s) { runOnUiThread(() -> Toast.makeText(this, s, Toast.LENGTH_LONG).show()); }
}
