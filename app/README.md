# 5K Display Control (root app)

Tap-to-set the external DisplayPort mode — no adb needed. Reads whatever monitor is plugged in,
lists its modes (including the DSC-enabled native ones, once `msm_drm.ko` is patched per the parent
README), you tap one, it applies **live** — it writes the QTI `mode_override`
(`/sys/kernel/debug/drm_dp/edid_modes`) and cycles the DP `hpd` node, so the mode re-trains with
**no physical replug**. Works on any monitor (LG 5K2K, Samsung 3440, etc.) — it just reads the
connected display's mode list.

- Requires **root** (Magisk grants on first use, or pre-grant the policy).
- Requires the **`max_dsc_count` patch** flashed (parent README) for the 5K2K-DSC modes to appear;
  works for uncompressed modes without it.
- "Clear override" reverts to the monitor's default **and forgets** its remembered mode.

## Auto-select on plug (NEW)

The app is the **only** thing that sets a resolution — there is no background service arming modes.
So it sets one for you automatically:

- **On a fresh connection** it applies the **highest res/refresh that fits the trained link**
  (native 5120×2160@100 with DSC on a 4-lane cable; the best that fits on a 2-lane dock).
- **Per-monitor memory:** it identifies each monitor by its **EDID**, so once you tap a mode by
  hand, it remembers *that* choice for *that* monitor and re-applies it on every future plug.
- **Never fights you:** a manual tap marks the monitor handled for the session — the app won't
  auto-override it. Unplug/replug is what re-triggers auto-select.
- Toggle it off with the **"Auto-set best mode on plug"** switch (state is remembered).
- Because it's the app (not a boot service), auto-select runs while the app is open. Plug a monitor
  with the app closed and it comes up at the monitor's EDID default until you open the app.

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
