package com.zorrobyte.dispctl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import java.io.*;
import java.util.*;

// 5K Display Control — root app to force the external DisplayPort mode via the
// QTI mode_override (drm_dp/edid_modes). Reads whatever monitor is plugged in,
// lists its modes (incl. the DSC-enabled native ones once msm_drm.ko is patched),
// you tap one, it writes mode_override; replug the monitor to apply.
public class MainActivity extends Activity {
    LinearLayout list;
    TextView status;

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
