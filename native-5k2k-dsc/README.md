# Native 5120×2160 (5K2K) + DSC on the wired external display — Razr Fold 2026

**Achieved: native 5120×2160 @ 100 Hz, 10‑bit, DSC, sharp AND smooth** on the wired DisplayPort
external display — and confirmed end‑to‑end (kernel modeset + DSC reservation + the monitor's own
OSD + a responsive desktop). This folder is the **complete, replicate‑from‑a‑wiped‑device** guide.

- Device: **Motorola Razr Fold 2026**, codename **`blanc`** (product `blanc_g`)
- SoC: **Snapdragon 8 Gen 5 = SM8845**, QTI DPU codename **"Eliza"**
- Build used: `motorola/blanc_g/blanc:16/W3WBS36V.36-48-ST4.6-5`, kernel `6.12.38-android16-5-…-4k` (GKI)
- Monitor used: **LG 45GX950A** (5120×2160 / 21:9 / up to 165 Hz)

> ⚠️ Read **§9 Gotchas** before you start. Two of them (the DRM `debug` mask and the DRM `force`
> node) will waste hours or wedge your display if you don't know about them.

---

## 0. TL;DR — what the fix actually is

The external DisplayPort path was handed a **boot‑static DSC budget of zero**
(`dp_display->max_dsc_count = 0`) because `SDE_DP_DSC_RESERVATION_SWITCH` is off for Eliza. So
`DP_PANEL_CAPS_DSC` was never set → every wide mode validated `dsc:0` → only uncompressed timings the
monitor rejects → no native 5K2K.

**The fix = one instruction** in the loadable vendor module `msm_drm.ko`: force `max_dsc_count = 4`.
Then DSC engages, the monitor's real 5120×2160@100 (1196 MHz, DSC) timing validates, and the SDE RM
reserves the 2 DSC blocks it needs.

Because `msm_drm.ko` lives in a **dm‑verity'd, VABC, LZ4‑EROFS `vendor_dlkm`** and loads in
**first‑stage init**, you can't Magisk‑overlay it — you must rebuild the EROFS, disable AVB verity,
and flash the partition. All of that is below.

---

## 1. Prerequisites

- **Unlocked bootloader** (this device's `ro.boot.flash.locked=1`/`verifiedbootstate=green` are
  **spoofed by the PlayIntegrityFix Magisk module** — the real state is unlocked).
- **Root** (Magisk).
- **A full `blanc` factory image** for recovery (mandatory safety net — see §8).
- macOS/Linux with `adb` + `fastboot` (platform‑tools), and Android NDK `llvm-objdump`/`llvm-nm` if
  you want to re‑derive the patch offset. The device has `/system/bin/mkfs.erofs` (we build on‑device).
- The stock `msm_drm.ko` from **your** build (do NOT reuse the one here blindly — see §2.1).

---

## 2. The patch (binary, value‑only)

`dp_drm_bridge_init()` in `msm_drm.ko` copies its 4th arg (`max_dsc_count`, register `w3`) into `w19`,
then stores `w19` to `display->max_dsc_count` (`stp w20, w19, [x21, #0x30]`). We replace the copy with
a constant.

| | bytes (little‑endian) | instruction |
|---|---|---|
| stock | `f3 03 03 2a` | `mov w19, w3` |
| patched | `93 00 80 52` | `movz w19, #4` |

Effect: `dp_display->max_dsc_count = 4` regardless of the computed `0`. (The SoC actually has 4 free
DSC blocks; `avail_dsc=4`.) Only `.text` changes → `__versions`/vermagic/symbols untouched → the
module loads identically. Stock `msm_drm.ko` ships **unsigned** and is **not** on
`protected_module_names_list`, so the patched module loads fine.

### 2.1 Find the right offset on YOUR build (don't trust a fixed number)
The 8‑byte pair `mov w19,w3; mov w20,w2` appears ~23× in the module — matching it blind will patch the
**wrong** function. Use the **unique 16‑byte anchor** from `dp_drm_bridge_init`'s prologue:

```
f303032a f403022a f70301aa f50300aa     # mov w19,w3 ; mov w20,w2 ; mov x23,x1 ; mov x21,x0
```

`scripts/apply-patch.py` does exactly this (finds the unique anchor, patches the first 4 bytes). On
our build the anchor was at `.ko` file offset **`0x953a4`** (`.text` `sh_offset 0x4b000` + vaddr
`0x4a3a4`), but **always re‑derive it** — a different build moves it.

```sh
adb shell su -c 'cp /vendor_dlkm/lib/modules/msm_drm.ko /data/local/tmp/'
adb pull /data/local/tmp/msm_drm.ko msm_drm.ko.stock
python3 scripts/apply-patch.py msm_drm.ko.stock msm_drm.ko.patched
```

---

## 3. Back up everything FIRST (recovery depends on this)

Active slot here was `_b` — adjust to yours (`getprop ro.boot.slot_suffix`).

```sh
mkdir -p backup && cd backup
for p in vendor_dlkm_b vbmeta_b vbmeta_system_b init_boot_b; do
  adb shell su -c "dd if=/dev/block/by-name/$p of=/data/local/tmp/$p.img 2>/dev/null"
  adb pull /data/local/tmp/$p.img .
done
# vendor_dlkm is a VABC snapshot device — dump the live content via dm-6 (the by-name dm node):
adb shell su -c 'dd if=$(readlink -f /dev/block/by-name/vendor_dlkm_b) of=/data/local/tmp/vendor_dlkm_b.img bs=1M 2>/dev/null'
adb pull /data/local/tmp/vendor_dlkm_b.img .
cd ..
```

Keep these + the factory image somewhere safe. They are your only fast recovery.

---

## 4. Rebuild `vendor_dlkm` with the patched module (ON‑DEVICE, for SELinux)

`vendor_dlkm` is **LZ4‑EROFS** (incompatible feature `lz4_0padding`), blocksize 4096, UUID
`9e483606-ec3c-5687-9f51-3a0f140e5aec`, all files `root:root`. The `.ko` is **compressed** in the FS,
so you cannot in‑place patch the partition image — you must rebuild it. **Build on‑device** (the phone
has `/system/bin/mkfs.erofs` and native SELinux); building on macOS loses `security.selinux` xattrs.

```sh
adb push msm_drm.ko.patched /data/local/tmp/
adb push backup/vendor_dlkm_b.img /data/local/tmp/      # or use the copy already there
adb push scripts/rebuild-vendor-dlkm.sh /data/local/tmp/
adb shell su -c 'sh /data/local/tmp/rebuild-vendor-dlkm.sh'
adb pull /data/local/tmp/vendor_dlkm_new.img .
```

`rebuild-vendor-dlkm.sh` does: `fsck.erofs --extract` the stock image → swap in the patched `.ko` →
`chcon` the tree to the real labels (`vendor_file:s0` everywhere, `vendor_configs_file:s0` for `/etc`)
→ `mkfs.erofs -zlz4hc --all-root -U <uuid>`. **`-zlz4hc` is required** — plain `-zlz4` produces a
~33 MB image that overflows the 28,672,000‑byte partition; `lz4hc` fits (~28 MB).

**Verify the rebuilt image before flashing** (the script also does this — loop‑mount, *not* extract,
because extracting to `/data` relabels the output to `shell_data_file`):
```sh
adb shell su -c 'cd /data/local/tmp; mkdir -p m; mount -t erofs -o ro,loop vendor_dlkm_new.img m; \
  ls -Z m/lib/modules/msm_drm.ko; sha256sum m/lib/modules/msm_drm.ko; umount m'
# expect: u:object_r:vendor_file:s0   and the patched sha
```

---

## 5. Disable AVB verity + flash (fastbootd)

`vendor_dlkm` is dm‑verity‑protected, so a modified partition fails verity unless you disable it.
Bootloader is unlocked, so this just works. Reboot to **fastbootd** (userspace fastboot — required for
the logical `vendor_dlkm`):

```sh
adb reboot fastboot          # NOT 'bootloader' — must be fastbootd for logical partitions
fastboot devices             # confirm 'fastboot' (userspace)

# disable verity by re-flashing the stock vbmeta with the disable flags:
fastboot --disable-verity --disable-verification flash vbmeta        backup/vbmeta_b.img
fastboot --disable-verity --disable-verification flash vbmeta_system backup/vbmeta_system_b.img

# flash the patched partition (fastbootd resizes + handles the VABC snapshot automatically):
fastboot flash vendor_dlkm vendor_dlkm_new.img

fastboot reboot
```

### Verify the patch took (briefly!)
```sh
adb shell su -c 'sha256sum /vendor_dlkm/lib/modules/msm_drm.ko'   # == patched sha
# Read the DSC budget — TURN THE DEBUG MASK BACK OFF IMMEDIATELY AFTER (see §9):
adb shell su -c 'mount -t debugfs none /sys/kernel/debug 2>/dev/null; echo 0x07 > /sys/module/drm/parameters/debug'
# plug the monitor in, then:
adb shell su -c 'dmesg | grep convert_to_dp_mode | tail'   # expect: ... max:4 ... caps:0x1   (was max:0 caps:0x0)
adb shell su -c 'echo 0 > /sys/module/drm/parameters/debug' # ‼️ MUST turn off — it cripples the pipeline
```

---

## 6. Select native 5K2K (the patch enables DSC; this picks the mode)

The patch makes the **5120×2160@100** DisplayID timing valid. Force it with the QTI `mode_override`,
then **physically replug** the monitor (mode_override applies on the next probe):

```sh
adb shell su -c 'mount -t debugfs none /sys/kernel/debug 2>/dev/null; \
  echo "5120 2160 100 0" > /sys/kernel/debug/drm_dp/edid_modes'
# now UNPLUG + REPLUG the monitor
```

- Use **@100** (1196 MHz — the monitor's real DisplayID timing, drives DSC). The **@60** entry is a
  704 MHz reduced‑blanking timing the **panel rejects** (black screen) — don't use it.
- Clear with `echo "0 0 0 0" > /sys/kernel/debug/drm_dp/edid_modes`.
- `mode_override` is **volatile** (debugfs) — it resets on reboot. Persistence: §7.

**Expected proof** (`dmesg`, info‑level — no debug mask needed):
```
dp_panel_resolution_info: 5120(120|8|32|0)x2160(52|45|8|1)@100fps 30bpp 1196340Khz 20LR 4Ln
dp_display_stream_enable: ... tot_dsc_blks_in_use=2
```
Clean load at 5120@100+DSC: `composer-service ≈ 14%`, `crtc_commit ≈ 10%` (S‑state), **~85% idle.**
Sharp **and** smooth. (See `PROOF.md`.)

---

## 7. Persistence (auto‑apply on dock)

The patch is permanent (it's in `vendor_dlkm`). Only the **mode selection** resets on reboot. Install
the Magisk module in `magisk-dsc-5k/` — its `service.sh` sets `edid_modes = 5120 2160 100` at boot so
the next dock comes up native. (Docked‑at‑boot may need one replug, because the DP probes in
first‑stage before the service runs; dock‑after‑boot is automatic.) Also sets
`settings put global hdcp_checking 0` (see §9 HDCP).

```sh
adb push magisk-dsc-5k /data/local/tmp/
adb shell su -c 'cp -r /data/local/tmp/magisk-dsc-5k /data/adb/modules/dsc_5k && \
  chmod 0755 /data/adb/modules/dsc_5k/*.sh'
# reboot; dock; enjoy
```

A cleaner alternative if it works on your build: with DSC now valid, Moto's desktop resolution picker
may list 5120 — selecting it sets a framework‑native preferred mode that persists without debugfs.

---

## 8. Recovery (do this if it bootloops or the display dies)

Bootloader unlocked + your backups + factory image = always recoverable.

```sh
# revert the partition + re-enable verity (flash stock vbmeta WITHOUT the disable flags):
fastboot flash vendor_dlkm backup/vendor_dlkm_b.img
fastboot flash vbmeta        backup/vbmeta_b.img
fastboot flash vbmeta_system backup/vbmeta_system_b.img
fastboot reboot
# if anything is worse: flash the full blanc factory image.
```
A patched‑module failure usually does **not** panic the kernel (ADB stays up) — worst case you remove
`/data/adb/modules/*` or re‑flash. Internal displays are unaffected by this patch (DP‑path only).

---

## 9. Gotchas / hard‑won lessons (READ THIS)

- **🔴 NEVER leave the DRM debug mask on.** `echo 0x07 > /sys/module/drm/parameters/debug` logs every
  plane/commit *per frame* — it **cripples the whole display pipeline** (composer 14% → 192%, desktop
  becomes laggy) **and poisons every CPU measurement.** We chased a phantom "platform performance
  ceiling" for an hour that was entirely this. Use it for a 1‑second read, then `echo 0` immediately.
- **🔴 NEVER write the DRM `force` node** (`/sys/kernel/debug/dri/0/DP-1/force`). It only accepts
  `on`/`off`, **latches**, and has no working "return to auto" value → wedges the connector
  disconnected until reboot. (`detect`/`unspecified`/`reset` are silently ignored.)
- **🔴 NEVER cycle the QTI `hpd` node** (`drm_dp/hpd`) to apply changes — repeated sim‑HPD wedges the
  Android framework display state to `OFF` (black, won't repaint). **Use a physical replug** to apply
  `mode_override`/EDID changes.
- **`mode_override` only selects an already‑valid enumerated mode** and matches by W/H/Hz/aspect only.
  Forcing 5120 picks **@100** (good) — the **@60** 704 MHz timing the monitor rejects.
- **Magisk file‑overlay of `msm_drm.ko` does NOT work** — it loads in first‑stage, before any Magisk
  hook. You must flash the partition.
- **Rebuild EROFS on‑device**, not on macOS (`mkfs.erofs` on macOS lacks SELinux; you'd lose labels).
  Verify labels by **loop‑mount**, never by extracting to `/data` (that relabels to `shell_data_file`).
- **HDCP:** the LG fails HDCP 2.2 auth over DP‑alt (`AKE_SEND_CERT failed`), leaving a retry loop. Not
  the main perf issue, but quiet it with `settings put global hdcp_checking 0`. (A `.ko` patch of the
  HDCP path — `dp_display_check_source_hdcp_caps`/`hdcp_start` — is the clean kill if you care; not
  required for a working desktop. Disabling HDCP breaks protected video playback on that display.)
- **Performance reality (corrected):** native 5120‑DSC is **smooth** (~14% composer, 85% idle). It's
  fine as a daily desktop. The cover panel waking adds composer load (~2×) — minor.

---

## 10. Why it was broken (the mechanism, for the curious)
See `../FINDINGS.md` and `mechanism.md` for the full diagnosis. Short version of the call chain:
`dp_panel_read_sink_caps: fec_en=1, dsc_en=1` (sink + driver support DSC) → but
`dp_display_convert_to_dp_mode` gate `free_dsc_blks >= required_dsc_blks` fails because
`max_dsc_count` is computed once at boot as `catalog_dsc − DSI_panel_dsc` with the reservation switch
**off**, leaving DP with **0**. Validate then prunes the DSC timing. The patch forces the budget so
the gate passes, after which the SDE RM (`_sde_rm_reserve_dsc`) reserves the DSC pair at commit.

## Files in this folder
- `scripts/apply-patch.py` — re‑derive the unique anchor + apply the 4‑byte patch to a stock `.ko`.
- `scripts/rebuild-vendor-dlkm.sh` — on‑device EROFS extract → swap `.ko` → relabel → `mkfs.erofs`.
- `scripts/vendor_dlkm_file_contexts` — SELinux file_contexts reference for the rebuild.
- `magisk-dsc-5k/` — persistence module (sets `edid_modes` at boot + quiets HDCP).
- `PROOF.md` — captured kernel logs + load numbers proving the working result.
