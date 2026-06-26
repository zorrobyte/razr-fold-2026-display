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

Build: `javac` → `d8` → `aapt2 link` → zip `classes.dex` → `zipalign` → `apksigner` (see parent
README for tool paths). Prebuilt: `5K-Display-Control.apk`.
