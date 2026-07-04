# Newer software — build `W3WBS36.36-48-5-1` (Verizon `blanc_gu`)

The full native-5K2K + DSC stack **confirmed working on the newer OTA build
`W3WBS36.36-48-5-1`** (the main guide was written on `W3WBS36V.36-48-ST4.6-5`). All layers reproduce;
this folder has the artifacts **rebuilt for this build** so you don't hit a version mismatch.

> This device also runs a **from-source GKI kernel with audio + Lindroid container support** — see
> [`zorrobyte/razr-fold-2026-kernel-build`](https://github.com/zorrobyte/razr-fold-2026-kernel-build).
> The DSC patch here is applied on top of that kernel + the stock `vendor_dlkm`.

## What changed vs the ST4.6 guide
Nothing conceptually — same three layers, same mechanisms. The only build-specific pieces are the two
binaries (they must match *this* build), both regenerated here and **verified on-device (no bootloops,
`system_server` healthy)**:

| Layer | Build-specific? | This build |
|---|---|---|
| **3. DSC** — `msm_drm.ko` `max_dsc_count=4` | offset can move | **same offset `0x953a4`** — `apply-patch.py` re-derives it via the unique 16-byte anchor. `msm_drm.ko.patched` here. |
| **1a. Cap** — `isExternalDisplayLimitModeEnabled()→false` | yes (services.jar) | rebuilt for 48-5-1 (below) |
| **1b. ReadyFor rebuild** — `supportedReadyForModes` | yes (services.jar) | rebuilt for 48-5-1 (below) |
| Settings, mode-select app, cable | no | unchanged (`../native-5k2k-dsc/`) |

## Artifacts
- **`msm_drm.ko.patched`** — DSC-patched vendor module for this build (stock sha
  `03c53b47…` → patched `7ed8b938…`, 4 bytes @ `0x953a4`). Rebuild `vendor_dlkm` on-device per
  [`../native-5k2k-dsc/README.md`](../native-5k2k-dsc/README.md) §4–5.
- **`magisk-dispcap/`** — a Magisk module (patched `services.jar` = **layer 1a + 1b**, `customize.sh`
  invalidates the oat + fs-verity meta). Zip it and `magisk --install-module`, or copy to
  `/data/adb/modules/services_dispcap/` (run `customize.sh`'s `mknod`s manually) + reboot.
- **`kernel-config-lindroid.txt`** — the running kernel config (for reference).

## How the `services.jar` patch was built for this build (fully reproducible)
Both layer-1 patches live in `classes.dex` (dex version **039**), applied with `baksmali`/`smali`
`2.5.2` (`--api 28` → dex 039) and R4 compiled with `d8`:

1. Pull `/system/framework/services.jar`; `baksmali d --api 28 classes.dex`.
2. **1a:** in `com/android/server/display/feature/DisplayManagerFlags.smali`, replace
   `isExternalDisplayLimitModeEnabled()Z`'s body with `const/4 p0, 0x0 ; return p0`.
3. **1b:** compile [`../lsposed-module/src/com/dispunlock/R4.java`](../lsposed-module/src/com/dispunlock/R4.java)
   with `javac` + `d8 --min-api 28` (pure reflection — no android.jar needed), `baksmali` it, drop
   `com/dispunlock/R4*.smali` into the tree, and inject a call at the tail of
   `LocalDisplayAdapter$LocalDisplayDevice.getDisplayDeviceInfoLocked()` — right at `:cond_386`, where
   `p0` still holds `this`, before it's overwritten with `mInfo`:
   ```smali
   :cond_386
   invoke-static {p0}, Lcom/dispunlock/R4;->fix(Ljava/lang/Object;)V
   iget-object p0, p0, Lcom/android/server/display/LocalDisplayAdapter$LocalDisplayDevice;->mInfo:Lcom/android/server/display/DisplayDeviceInfo;
   return-object p0
   ```
   (R4 swallows all throwables, so it can never crash `system_server`.)
4. `smali a --api 28` → new `classes.dex`; replace it in the jar **stored/uncompressed** (`zip -0`);
   ship via the Magisk module, which deletes the stale `services.{odex,vdex,art}` + all `.fsv_meta`
   + `.prof` so ART reloads the patched dex.

## Recovery
If `system_server` bootloops: reboot to Magisk **safe mode** (or `adb shell su -c 'touch
/data/adb/modules/services_dispcap/disable'`) and reboot. For DSC: re-flash the stock `vendor_dlkm`.
