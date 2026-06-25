# LSPosed on the Razr Fold 2026 (Android 16) — WORKING

The stock LSPosed and the **Vector release build (3021)** could not load modules on this
device: the daemon (`lspd`) failed to read the module APK from its mount namespace
(`Failed to open APK /data/app/.../base.apk: I/O error`). See `lspd_module_load_failure.log`.

## Fix: Vector canary `v2.0-3043`

The JingMatrix LSPosed fork **canary build 3043** (`vector-v2.0-3043-canary.zip`) fixes the
mount-namespace bug. Verified on this device:

```
I/VectorNative  System server process detected. Marking for injection.
I/VectorNative  Injected Vector framework into system_server.   <-- 0 open-APK errors
```

Requirements: Magisk root + **Zygisk enabled** (`magisk --sqlite "..."` → `zygisk=1`).
Install the zip with `magisk --install-module vector-v2.0-3043-canary.zip`, reboot.

## CLI (new in the canary — no hidden UI needed)

The canary ships a scriptable daemon CLI at `/data/adb/lspd/cli`:

```sh
/data/adb/lspd/cli status                       # framework + enabled-module count
/data/adb/lspd/cli modules ls                   # list installed modules
/data/adb/lspd/cli modules enable <pkg>         # enable a module
/data/adb/lspd/cli scope set <pkg> android/0    # scope to System Framework (pkg "android", user 0)
/data/adb/lspd/cli scope ls <pkg>
```

## DispUnlock — the display-cap module (`DispUnlock.apk`)

Replaces the services.jar patch. Hooks, in the `android` (system_server) process:

```
com.android.server.display.feature.DisplayManagerFlags.isExternalDisplayLimitModeEnabled()  -> false
```

Enable + scope to `android/0`, reboot. **Verified to remove the external-display resolution
cap on its own** (services.jar patch disabled): the LG 45GX950A then exposes
`5120×2160@60`, `3840×2160@60`, `3440×1440@100` over a direct USB-C→DP cable.

This is the maintainable route — survives system OTAs (just re-enable the module) with no need
to re-patch and re-whiteout `services.jar` after every update. The `../framework-patch/`
services.jar route remains as a fallback for non-LSPosed setups.
