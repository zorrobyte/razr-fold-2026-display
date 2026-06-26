# Cloning the native-5K2K-DSC setup to another Razr Fold 2026

> Bring a **second `blanc`** to the exact working state of device #1 — rooted, DSC-patched,
> framework-unlocked, app + settings — in ~10 mostly-scripted minutes. Run **[`flash-device2.sh`](flash-device2.sh)**.

## TL;DR

```bash
# target: a blanc on build W3WBS36.36-48-5-1, bootloader UNLOCKED, USB connected
./flash-device2.sh            # interactive, verifies checksums, confirms each phase
./flash-device2.sh --dry-run  # print every command, flash nothing (read it first!)
```

---

## Why NOT "just dd the whole device"

A literal bit-for-bit clone of every partition is the **wrong tool** for a *second physical phone*:

- **Per-device partitions must not move between units.** `persist` (sensor + display calibration),
  `modemst1` / `modemst2` / `fsg` / `fsc` (your IMEI + radio calibration), Widevine/DRM keys, `frp`,
  `devinfo` are all unique to one handset. Flashing them onto another phone corrupts its IMEI and
  calibration — possibly bricks the radio and breaks DRM/attestation.
- **`userdata` is ~256 GB and encrypted to device #1's hardware keys** — it won't decrypt on device #2.

So you never clone a phone *to* a phone. You clone the **handful of modified system partitions** and let
the target re-initialize its own per-device state. That curated set is the **clone kit** below.

## What actually gets flashed (the clone kit)

| Layer | Artifact | Partition / target | Notes |
|---|---|---|---|
| Root | `firmware-boot/magisk_patched.img` | `init_boot` | stock init_boot + Magisk 30.7 |
| Verity off | `firmware-boot/vbmeta.img` | `vbmeta` | **stock image**, flashed with `--disable-verity --disable-verification` |
| Verity off | `firmware-boot/vbmeta_system.img` | `vbmeta_system` | same |
| DSC (layer 3) | `native-5k2k-dsc/artifacts/vendor_dlkm_patched.img` | `vendor_dlkm` (dynamic) | `msm_drm.ko` `max_dsc_count` patch; flashed from **fastbootd** |
| Framework (layer 1a+1b) | `framework-patch/services_desktop5k_v4.zip` | Magisk module | patched `services.jar` (dex039) + oat-whiteout `customize.sh` |
| App | `native-5k2k-dsc/app/5K-Display-Control.apk` | installed | mode-select + UI-scale slider |
| Manager | `tools/Magisk-v30.7.apk` | installed | |
| Settings | — | `settings` | `match_content_frame_rate=2`, `hdcp_checking=0` |

All checksummed in [`CLONE-SHA256SUMS.txt`](CLONE-SHA256SUMS.txt); the script verifies them before flashing.

> **Note on `vbmeta`:** you flash the *stock* image — the `--disable-verity --disable-verification` flags
> patch the header's flags byte at flash time. There is no "patched vbmeta" file; the flags do the work.

## Hard requirements

1. **Same model + build.** Target must be codename **`blanc`** on build **`W3WBS36.36-48-5-1`**.
   The patched `vendor_dlkm` (dm-verity hash), the `services.jar` (dex method offsets), and the
   `msm_drm.ko` byte offset are **all build-specific**. On a different build, flash that build's
   factory image first (or re-derive the patches per `native-5k2k-dsc/README.md`). The script guards
   this and refuses unless `--force`.
2. **Bootloader unlocked** on the target (per-device — cannot be cloned). The script checks
   `fastboot getvar unlocked`.
3. `adb` + `fastboot` (platform-tools) on PATH.

## What the script does (phases)

0. **Preflight** — verify every artifact's sha256 against the manifest.
1. **Identity guard** — over adb, confirm `ro.product.device=blanc` and the build matches, then
   `adb reboot bootloader`.
2. **Bootloader** — confirm unlocked → flash `vbmeta` + `vbmeta_system` *with verity disabled* →
   flash Magisk-patched `init_boot` → `fastboot reboot fastboot` (into fastbootd).
3. **fastbootd** — flash the DSC-patched `vendor_dlkm` (a dynamic/super partition, only flashable from
   userspace fastboot) → `fastboot reboot`.
4. **Booted** — install Magisk app → **grant root to `shell`** (on-device prompt) → install the v4
   `services.jar` module (`magisk --install-module`) → install the app → apply settings → reboot.
5. **Use** — plug in the monitor, open *5K Display Control*, tap the mode, drag the scale slider.

The script pauses between mode transitions (bootloader → fastbootd → booted) so you can confirm the
device's on-screen state. The one unavoidable manual step is **granting su to `shell`** the first time
Magisk prompts — adb can't auto-approve its own root.

## Recovery (revert device #2 to stock)

```bash
fastboot reboot fastboot
fastboot flash vendor_dlkm native-5k2k-dsc/backup/vendor_dlkm_b.stock.img
fastboot reboot bootloader
fastboot flash vbmeta        firmware-boot/vbmeta.img          # NO disable flags = verity back on
fastboot flash vbmeta_system firmware-boot/vbmeta_system.img
fastboot flash init_boot     firmware-boot/init_boot.img       # stock init_boot = un-root
fastboot reboot
```

Or flash the full `blanc` factory image as the nuclear option. The DSC patch is DP-path-only and never
affects the internal foldable displays.
