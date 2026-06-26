# 5K Display Control (root app)

Tap-to-set the external DisplayPort mode — no adb needed. Reads whatever monitor is plugged in,
lists its modes (including the DSC-enabled native ones, once `msm_drm.ko` is patched per the parent
README), you tap one, it writes the QTI `mode_override` (`/sys/kernel/debug/drm_dp/edid_modes`).
**Unplug + replug the monitor to apply** (the kernel only re-evaluates the override on a hotplug).
Works on any monitor (LG 5K2K, Samsung 3440, etc.) — it just reads the connected display's mode list.

- Requires **root** (Magisk grants on first use, or pre-grant the policy).
- Requires the **`max_dsc_count` patch** flashed (parent README) for the 5K2K-DSC modes to appear;
  works for uncompressed modes without it.
- "Clear override" reverts to Auto/stock (then replug).

## UI scale slider (NEW)

At native 5120×2160 the external display defaults to **~138 dpi** (the internal panel runs 420), so
everything is tiny. The app has a **UI-scale slider** that adjusts the external display's density
**live — no replug, applies instantly**:

- It auto-detects the external display's logical id via `DisplayManager` (here: display **19**) and
  runs `wm density <dpi> -d <id>` under root.
- Drag right = larger (range 96–360 dpi), or use the **Smaller − / Larger +** buttons for ±20 dpi steps.
- **Reset** restores the display's stock density (`wm density reset -d <id>`).
- The change is per-display, so it doesn't touch your phone's internal-screen scaling.
- Try **~170–200 dpi** as a comfortable 5K2K desktop scale.

CLI equivalent: `wm density 180 -d 19` (find the id in `dumpsys display | grep -A2 EXTERNAL`).

## Build

`./build.sh` — `aapt2 link` → `javac` → `d8` → zip `classes.dex` → `zipalign` → `apksigner`
(debug keystore `android`/`android`; build-tools 37.0.0, android-36 jar). Prebuilt:
`5K-Display-Control.apk`.
