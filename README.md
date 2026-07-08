# Razr Fold 2026 — Native 5K2K + DSC External Display

Drive a **Motorola Razr Fold 2026** at **native 5120×2160 @ 100 Hz, 10-bit, with DSC** (Display
Stream Compression) over a wired USB-C→DisplayPort ultrawide — resolutions and a compression path
the phone refuses out of the box. Also unlocks 4K@60 and 3440×1440@100.

|  |  |
|---|---|
| **Device** | Motorola Razr Fold 2026 · codename `blanc` (product `blanc_gu`) · Verizon retail |
| **Build**  | Android 16 · **`W3WBS36.36-48-5-1`** (this repo is pinned to this build) |
| **Kernel** | GKI `6.12.38-android16-5-…-4k` — **works on the stock kernel** |
| **SoC / DPU** | Snapdragon 8 Gen 5 = SM8845 · QTI DPU "Eliza" |
| **Needs**  | Unlocked bootloader · Magisk root · a **direct 4-lane USB-C→DP cable** · a 5K2K monitor |

> This is the condensed, single-device guide. The full RE journey, dead-ends, alternate paths
> (LSPosed, dtbo cap-edit), and decompiled Moto APKs were removed — see the `backup/pre-cleanup`
> branch if you need them.

---

## Why it's blocked (3 layers, condensed)

The hardware can do it (sink advertises DSC 1.2 + FEC, link trains HBR3 ×4 ≈ 25.9 Gbps, SoC has 4
free DSC blocks). Three independent software locks stop it:

| Layer | Block | Unlock | Lives in |
|---|---|---|---|
| **1a — framework cap** | AOSP filters any external mode above the internal-panel pixel budget (~5.54 MP) → 5120/4K never appear | `DisplayManagerFlags.isExternalDisplayLimitModeEnabled() → false` | `modules/magisk-dispcap` |
| **1b — Moto mode list** | Moto's ReadyFor list clamps resolution ≤ active mode and pins 60 Hz | rebuild `supportedReadyForModes` from real `mSupportedModes` (the `R4` hook) | `modules/magisk-dispcap` (same `services.jar`) |
| **2 — link** | a USB-C **dock** runs 2-lane DP → bandwidth-starved (caps ≈ 3440@60 / 5120@60) | a **direct 4-lane USB-C→DP cable** | *hardware* |
| **3 — DSC (kernel)** | `dp_display->max_dsc_count` is computed as **0** at boot (`SDE_DP_DSC_RESERVATION_SWITCH` off for Eliza) → DSC never engages | binary-patch `msm_drm.ko`: force `max_dsc_count = 4` (one instruction) | `scripts/apply-patch.py` → rebuilt `vendor_dlkm` |

All of 1 + 2 + 3 are required together for native 5120-with-DSC.

---

## Repo layout

```
scripts/
  apply-patch.py            # derive+apply the 4-byte DSC patch to a stock msm_drm.ko
  rebuild-vendor-dlkm.sh    # ON-DEVICE: rebuild the vendor_dlkm EROFS with the patched .ko
  vbmeta-disable-verity.py  # set AVB disable flags so a modified partition boots
  apply-hdcp14-patch.py     # OPTIONAL: force HDCP 1.4 (quiets LG HDCP-2.2 retry loop)
  vendor_dlkm_file_contexts # SELinux file_contexts reference for the rebuild
modules/
  magisk-dispcap/           # LAYER 1a+1b — patched services.jar (for build 48-5-1)
  magisk-dsc-5k/            # persistence — arms 5120@100 on the external display at boot
app/
  5K-Display-Control.apk    # tap-to-set root app (pick the mode per monitor) + source
prebuilt/
  msm_drm.ko.patched        # reference patched module for 48-5-1 (see step 2 note)
```

---

## Reproduce

### 0. Prereqs — unlock + root
- **Unlock bootloader** (Motorola: enable *OEM unlocking* + *USB debugging*, then
  `fastboot oem get_unlock_data` → motorola.com/bootloader → `fastboot oem unlock <code>`). Wipes data.
- **Root**: extract stock `init_boot.img` from your factory firmware, patch with the Magisk app,
  `fastboot flash init_boot magisk_patched.img`.
- Confirm build is `W3WBS36.36-48-5-1` and note your slot: `adb shell getprop ro.boot.slot_suffix`
  (**examples below use `_a` — substitute yours**).

### 1. Layer 3 — DSC patch → rebuilt `vendor_dlkm`

**Derive the patch from YOUR OWN stock module** (guarantees vermagic matches your kernel — do NOT
blindly reuse `prebuilt/`; see note):

```sh
# pull this device's stock msm_drm.ko
adb shell su -c 'cp /vendor_dlkm/lib/modules/msm_drm.ko /data/local/tmp/'
adb pull /data/local/tmp/msm_drm.ko msm_drm.ko.stock
#   expect sha256: 03c53b47fdf01232bf701e2843a698313830bdf2354abb927b2db64516470e48

python3 scripts/apply-patch.py msm_drm.ko.stock msm_drm.ko.patched
#   finds the unique 16-byte anchor, patches 4 bytes @ 0x953a4 (mov w19,w3 -> movz w19,#4)
#   expect patched sha256: 7ed8b938b302ecceb40036435c273ea912362c38544440245e8d578876488c45
```

**Back up the partitions you'll touch** (recovery net — keep these OUTSIDE the repo). `vendor_dlkm`
is a logical partition under `/dev/block/mapper`:

```sh
mkdir -p backup
for p in vbmeta_a vbmeta_system_a init_boot_a; do
  adb shell su -c "dd if=/dev/block/by-name/$p of=/data/local/tmp/$p.img" && adb pull /data/local/tmp/$p.img backup/
done
adb shell su -c 'dd if=/dev/block/mapper/vendor_dlkm_a of=/data/local/tmp/vendor_dlkm_a.img bs=1M'
adb pull /data/local/tmp/vendor_dlkm_a.img backup/
```

**Rebuild `vendor_dlkm` ON-DEVICE** (needs native SELinux + `mkfs.erofs`; the phone has them):

```sh
adb push msm_drm.ko.patched            /data/local/tmp/
adb push scripts/rebuild-vendor-dlkm.sh /data/local/tmp/rebuild.sh
adb shell su -c 'cp /data/local/tmp/vendor_dlkm_a.img /data/local/tmp/vendor_dlkm_b.img'  # script reads *_b.img
adb shell su -c 'cd /data/local/tmp && sh rebuild.sh'
adb pull /data/local/tmp/vendor_dlkm_new.img
#   expect: size 28061696 (fits 28672000), UUID 9e483606-ec3c-5687-9f51-3a0f140e5aec,
#           msm_drm.ko label u:object_r:vendor_file:s0, sha 7ed8b938...
```

**Disable AVB verity, then flash in fastbootd** (order matters — verity off *before* the modified
partition boots):

```sh
python3 scripts/vbmeta-disable-verity.py backup/vbmeta_a.img vbmeta_verity_off.img  # flags 0x0 -> 0x3

adb reboot fastboot                 # fastbootD (userspace) — required for logical vendor_dlkm
fastboot getvar is-userspace        # -> yes
fastboot flash vbmeta      vbmeta_verity_off.img   # PLAIN — do NOT pass --disable-* flags (see Gotchas)
fastboot flash vendor_dlkm vendor_dlkm_new.img
fastboot reboot

# verify live:
adb shell su -c 'sha256sum /vendor_dlkm/lib/modules/msm_drm.ko'  # == 7ed8b938...
adb shell getprop ro.boot.veritymode                             # empty (was "enforcing")
```

### 2. Layer 1 — framework cap + mode-list (`services.jar`)

```sh
adb push modules/magisk-dispcap /data/local/tmp/            # or zip it first
adb shell su -c 'magisk --install-module /data/local/tmp/magisk-dispcap.zip'   # if zipped
# (module = patched services.jar; customize.sh whiteouts the stale oat/vdex/art + fs-verity meta)
adb reboot
# after boot, no bootloop = success; /system/framework/services.jar is 26660134 B (patched)
```

### 3. Persistence module + control app

```sh
adb shell su -c 'magisk --install-module /data/local/tmp/magisk-dsc-5k.zip'   # arms 5120@100 on dock
adb install app/5K-Display-Control.apk
adb reboot
```

### 4. Select the mode (needs the monitor + 4-lane cable)
Open **5K Display Control**, plug the monitor in with a **direct 4-lane USB-C→DP cable**, tap
**5120×2160@100**, replug once.

CLI equivalent (live, no physical replug — cycle QTI `hpd` 0→1):
```sh
adb shell su -c '
  mount -t debugfs none /sys/kernel/debug 2>/dev/null
  echo "5120 2160 100 0" > /sys/kernel/debug/drm_dp/edid_modes
  echo 0 > /sys/kernel/debug/drm_dp/hpd; sleep 1; echo 1 > /sys/kernel/debug/drm_dp/hpd'
```
Expected proof (`dmesg`, info level):
```
dp_panel_resolution_info: 5120(...)x2160(...)@100fps 30bpp 1196340Khz 20LR 4Ln
dp_display_stream_enable: ... tot_dsc_blks_in_use=2
```

---

## Verified values (build 48-5-1)

| Thing | Value |
|---|---|
| stock `msm_drm.ko` sha256 | `03c53b47fdf01232bf701e2843a698313830bdf2354abb927b2db64516470e48` |
| patched `msm_drm.ko` sha256 | `7ed8b938b302ecceb40036435c273ea912362c38544440245e8d578876488c45` |
| patch offset / instr | `0x953a4` · `f3 03 03 2a` (mov w19,w3) → `93 00 80 52` (movz w19,#4) |
| rebuilt `vendor_dlkm` | 28,061,696 B · EROFS lz4hc · UUID `9e483606-ec3c-5687-9f51-3a0f140e5aec` |
| patched `services.jar` | 26,660,134 B |
| `vbmeta` flags | `0x00000000` → `0x00000003` (hashtree + verification disabled) |

---

## Recovery
Unlocked bootloader + your `backup/` images + the full `blanc` factory image = always recoverable.
```sh
fastboot flash vendor_dlkm  backup/vendor_dlkm_a.img     # stock partition
fastboot flash vbmeta       backup/vbmeta_a.img          # WITHOUT the disable flags -> re-enables verity
fastboot reboot
```
A bad `services.jar` (bootloop) → Magisk safe mode, or
`adb shell su -c 'touch /data/adb/modules/services_dispcap/disable'` + reboot.
The DSC patch is **DP-path only** — internal/fold displays are never touched.

---

## Gotchas (the ones that will bite you)

- **CRLF kills on-device scripts.** Android sh reads `set -e\r` as `set: -: unknown option`. This
  repo's `.gitattributes` forces `eol=lf` on `*.sh/*.py/*.prop` — keep it. If you author on Windows
  another way, `tr -d '\r'` before running.
- **`prebuilt/msm_drm.ko.patched` is a `.text`-only patch** (vermagic/symbols untouched), so it's
  byte-identical to patching your own stock module and loads on the stock kernel. Still, **re-derive
  from your device** (step 1) — a different sub-build can move the offset, and `apply-patch.py`
  re-finds it via the unique anchor.
- **`fastboot --disable-verity flash vbmeta` FAILS** here (`Failed to find AVB_MAGIC at offset: 0`,
  platform-tools 37.0.0, a host-side bug). That's why we pre-patch the flags with
  `vbmeta-disable-verity.py` and flash **plain**.
- **Disable verity BEFORE rebooting with the modified `vendor_dlkm`.** Flashing it while verity is
  `enforcing` and rebooting bounces you back to the bootloader (recoverable, not a brick).
- **`vendor_dlkm` is logical** → flash from **fastbootd** (`adb reboot fastboot`), not the primary
  bootloader. `vbmeta` is physical → either works.
- **Rebuild EROFS on-device**, never on desktop (you'd lose `security.selinux` xattrs). Verify labels
  by loop-mount, not by extracting to `/data` (that relabels to `shell_data_file`).
- **Never leave the DRM debug mask on** (`echo 0x07 > /sys/module/drm/parameters/debug`) — it logs
  every plane/commit per frame and cripples the pipeline. 1-second read, then `echo 0`.
- **5120@100 needs a 4-lane direct cable.** A 2-lane dock caps at 5120@60. sim-HPD keeps the trained
  lane count — only a physical replug renegotiates 2→4 lanes.
- **HDCP:** the LG fails HDCP 2.2 over DP-alt (retry loop). `settings put global hdcp_checking 0`
  quiets it (the `dsc_5k` module does this); `scripts/apply-hdcp14-patch.py` is the clean kill.
  Disabling HDCP breaks protected video on that display.
