# Razr Fold 2026 — Full External-Display Unlock (Native 5K2K + DSC)

> Driving a **Motorola Razr Fold 2026** at **native 5120×2160 @ 100 Hz, 10-bit, with DSC** on a wired
> ultrawide — plus 4K@60 and 3440×1440@100 — resolutions, refresh rates, and a compression path the
> phone is hard-coded to refuse out of the box. Sharp, smooth, permanent, and fully reproducible.

|  |  |
|---|---|
| **Device** | Motorola Razr Fold 2026 · codename **`blanc`** (product `blanc_g`) · Verizon |
| **SoC / DPU** | Snapdragon 8 Gen 5 = **SM8845** · QTI DPU codename **"Eliza"** |
| **OS / kernel** | Android 16 · GKI `6.12.38-android16-5-…-4k` · build `W3WBS36V.36-48-ST4.6-5` |
| **Monitors** | **LG 45GX950A** (5120×2160 / 21:9 / 165 Hz) · **Samsung Odyssey G85SD** (3440×1440 / 240 Hz) |
| **Final state** | **5120×2160@100 + DSC**, 4K@60, 3440×1440@100 — direct USB-C→DP, bootloader-unlocked + Magisk |

This was not one fix. It was a **stack of unlocks across three layers** (framework, link, kernel),
each of which independently blocks native 5K2K. This README is the master map; each piece has its own
folder and deep-dive.

---

## TL;DR — the complete unlock stack

| Layer | What blocks it stock | The unlock | Lives in |
|---|---|---|---|
| **0. Access** | Locked bootloader, no root | Unlock bootloader + Magisk (props are PIF-spoofed to look locked/green) | FINDINGS §7–§8 |
| **1a. Framework cap** | AOSP `enable_mode_limit_for_external_display` filters out any external mode > the internal-panel budget (~5.54 MP) → 5120 & 4K never appear | Hook `DisplayManagerFlags.isExternalDisplayLimitModeEnabled()` → `false` | `framework-patch/`, `lsposed-module/` |
| **1b. Mode/refresh list** | Moto's ReadyFor/desktop list (`getDisplayDeviceInfoLocked → getMaxResolution`) **clamps resolution to ≤ active mode and hardcodes 60 Hz** | Hook `getDisplayDeviceInfoLocked` and **rebuild `supportedReadyForModes` from the real `mSupportedModes` — every resolution at its TRUE max refresh** (the `R4` helper) | `lsposed-module/src/com/dispunlock/` (`R4.java`, `Hook-with-mode-rebuild.java`) |
| **2. Link/cable** | A USB-C **dock** runs 2-lane DP (shares lanes with USB3) → bandwidth-starved (3440@60) | **Direct USB-C→DP cable** (4-lane) or a 4-lane "DP-priority" dock | FINDINGS §17, native-5k2k-dsc §lanes |
| **3. DSC (kernel)** | `SDE_DP_DSC_RESERVATION_SWITCH` is **off** for Eliza → DP path's DSC budget = **0** at boot → DSC never engages → only uncompressed timings (5K2K capped @60, which the panel won't take at native) | **Binary-patch `msm_drm.ko`**: force `dp_display->max_dsc_count = 4` (one instruction) → DSC engages, native 5120@100 DSC timing validates, SDE reserves the DSC pair | **`native-5k2k-dsc/`** |
| **+ Mode select** | SF defaults to the EDID-preferred 3440 | 📱 **the "5K Display Control" app** (tap the mode, replug) — or the QTI `mode_override` CLI: `echo "5120 2160 100 0" > /sys/kernel/debug/drm_dp/edid_modes` | `native-5k2k-dsc/app/` |
| **+ Settings** | Cross-group switch off; HDCP 2.2 auth-retry loop | `settings put secure match_content_frame_rate 2`; `settings put global hdcp_checking 0` | `native-5k2k-dsc/` |

**All of layer 1 + 2 + 3 are required together** for native 5120-with-DSC: layer 1 makes the framework
*offer* the big modes with correct refresh, layer 2 gives the bandwidth, layer 3 makes the native
timing actually negotiate compression.

---

## The journey (and the dead ends — so you don't repeat them)

1. **Removed the AOSP software cap** (layer 1a). First via an **LSPosed/Vector module** — which was
   **blocked** on this device by a Vector release bug (`lspd` couldn't read the module APK), then
   **solved** with Vector canary `v2.0-3043` (FINDINGS §10 → §23). Also delivered the same hooks as a
   **`services.jar` patch** (dex **039** — `smali --api 28`; get the dex version wrong and
   `system_server` bootloops, see `razr-services-jar-dex-version`).
2. **Rebuilt Moto's mode/refresh list** (layer 1b) so 3440@100, 5120, 4K appear with correct refresh
   instead of a 60 Hz-clamped, resolution-capped list — the `R4` `supportedReadyForModes` rebuild.
3. **Direct 4-lane cable** (layer 2): exposed the full panel → **5120×2160@60 / 4K@60 / 3440@100**
   appeared and worked (FINDINGS §17) — but **uncompressed**, so 5K2K topped out at 60 Hz.
4. **Declared DSC "kernel-locked, not forceable without source"** (FINDINGS §18) — the documented
   ceiling. Explored a **dtbo cap-edit** angle as an alternative (`max-pclk`, `hbr3-disable`; §19).
5. **Broke that ceiling** (layer 3, FINDINGS §24): RE'd the *actual* DSC blocker — `max_dsc_count = 0`,
   not a hard PHY/kernel limit — and **binary-patched the vendor display module** → native
   **5120×2160 @ 100 Hz with DSC**. See `native-5k2k-dsc/` + `native-5k2k-dsc/PROOF.md`.
6. **Chased a phantom "performance ceiling"** for an hour that turned out to be **my own DRM debug mask
   left on** (`/sys/module/drm/parameters/debug = 0x07` logs every plane/commit per frame, crippling
   the pipeline and poisoning measurements). With it off: native 5K2K-DSC is **smooth** (~14% composer,
   ~85% idle). **Lesson: never leave that mask on.**

Other dead ends documented so nobody re-suffers them: the DRM `force` node (latches → wedges the
connector), sim-HPD cycling (wedges the framework display state to OFF), the 704 MHz reduced-blanking
5120@60 timing (the panel rejects it — use the 1196 MHz @100), and Magisk file-overlay of `msm_drm.ko`
(loads first-stage, too early). Full gotcha list in `native-5k2k-dsc/README.md §9`.

---

## Repository map

| Path | What it is |
|---|---|
| **`native-5k2k-dsc/`** | ⭐ **The DSC kernel-module unlock — the final win.** Full replicate-from-scratch guide, `apply-patch.py`, on-device EROFS rebuild script, Magisk persistence module, **patched + stock `msm_drm.ko`**, **ready-to-flash `vendor_dlkm`**, recovery backups (`vbmeta`, `vendor_dlkm`), and **`PROOF.md`**. |
| **`native-5k2k-dsc/app/`** | 📱 **5K Display Control** — a tap-to-set **root app**: reads whatever monitor is plugged in, lists its modes (incl. native 5K2K-DSC), tap one → it sets the QTI `mode_override` → replug to apply. Plus a **live UI-scale slider** (`wm density -d <ext>`) — fixes the tiny-at-5K2K default (~138 dpi) with no replug. Works on any monitor. Source + prebuilt APK. **No adb needed for daily use.** |
| **`FINDINGS.md`** | The full investigation log, §1–§24: bootloader unlock, root, desktop mode, the resolution-cap root cause, the framework patch, the lane/bandwidth math, refresh-rate investigations, the DSC dead-ends, the dtbo-cap path, and the final DSC unlock (§24). |
| **`framework-patch/`** | The **`services.jar`** framework patch (`DisplayManagerFlags.patched.smali`, `services-dispcap.zip` Magisk module) — layer 1a. |
| **`lsposed-module/`** | The same framework unlock as an **LSPosed/Vector module** (`com.dispunlock`). `Hook.java` = cap-only; **`Hook-with-mode-rebuild.java` + `R4.java` = the full layer-1a+1b** (cap removal **and** `supportedReadyForModes` rebuild). |
| **`dtbo-mod/`** | The **devicetree-cap** angle (`qcom,max-pclk-frequency-khz`, `qcom,dp-hbr3-disable`) + stock dtbo backup — an alternative unlock path (§19). |
| **`firmware-boot/`** | Boot/firmware artifacts. |
| **`decompiled/`, `apks/`, `tools/`** | Decompiled Moto desktop APKs (LaptopPanel/DesktopCore/TaskBar) + tooling used during RE. |
| **`REDDIT-POST.md`, `DEX-POST.md`** | Write-ups / posts. |

---

## Why it was blocked (the mechanism, condensed)

- **Hardware can do it.** Sink advertises DSC (`DPCD 0x60=0x01`, DSC 1.2) + FEC; link is HBR3 ×4
  (`DPCD 0x02=0xc4`, ~25.9 Gbps); the SoC has **4 free DSC blocks** (`avail_dsc=4`). Nothing physical
  blocks native 5K2K — 5120@60 needs DSC; 5120@100 needs DSC; both fit compressed.
- **Layer 1 — Android refuses to offer it.** The external-display governor filters modes above the
  internal-panel pixel budget; Moto's ReadyFor list clamps resolution and pins 60 Hz. → modes don't
  appear / appear wrong.
- **Layer 3 — the driver won't compress.** `dp_panel_read_sink_caps: fec_en=1, dsc_en=1` (capability is
  there), but `dp_display_convert_to_dp_mode`'s gate `free_dsc_blks >= required_dsc_blks` fails because
  `max_dsc_count` is computed once at boot as `catalog_dsc − DSI_panel_dsc` with the reservation switch
  **off** → DP gets **0**. Live proof: `… max:0 … caps:0x0` (stock) → `… max:4 … caps:0x1` (patched).
  After the patch the SDE RM reserves the DSC pair (`_sde_rm_reserve_dsc blk 1,2`) and the panel runs
  native 5120@100 at 30bpp via DSCMERGE dual-pipe (2×2560).

---

## Replicate (high level — full steps in the sub-READMEs)

1. **Unlock + root** (FINDINGS §7–§8).
2. **Layer 1 (framework):** install `framework-patch/` (services.jar) **or** `lsposed-module/`
   (Vector canary ≥3043 + `com.dispunlock`, using `Hook-with-mode-rebuild.java` + `R4.java`).
3. **Layer 2:** use a **direct USB-C→DP cable** (4-lane), not a dock.
4. **Layer 3 (DSC):** follow **`native-5k2k-dsc/README.md`** — patch `msm_drm.ko`
   (`scripts/apply-patch.py`), rebuild `vendor_dlkm` on-device (`scripts/rebuild-vendor-dlkm.sh`),
   disable AVB verity, `fastboot flash vendor_dlkm`, install the persistence module.
5. **Select the mode — easiest way: install the 📱 [`native-5k2k-dsc/app/`](native-5k2k-dsc/app/)
   "5K Display Control" app**, open it, tap the mode you want for the connected monitor, then replug.
   (CLI equivalent: `echo "5120 2160 100 0" > /sys/kernel/debug/drm_dp/edid_modes` + replug; or let the
   `magisk-dsc-5k/` service auto-arm a single saved mode.)

## Recovery
Bootloader unlocked + the backups in `native-5k2k-dsc/backup/` (stock `vendor_dlkm`, `vbmeta`,
`vbmeta_system`) + the full `blanc` factory image. Re-flash stock `vendor_dlkm` and stock `vbmeta`
(without the disable flags) to revert; flash the factory image as last resort. The DSC patch is
DP-path-only — internal displays are never affected.

## Proof
`native-5k2k-dsc/PROOF.md` — `max:0 caps:0x0` → `max:4 caps:0x1`; `resolution=5120x2160@100Hz bpp=30`;
`tot_dsc_blks_in_use=2`; `_sde_rm_reserve_dsc blk 1,2`; `dumpsys: real 5120 x 2160`; monitor OSD
`5120x2160@100`; clean load ~14% composer / ~85% idle.

---

*Bootloader-unlocked + Magisk + framework-cap-removed + Moto-mode-list-rebuilt + 4-lane-cable +
`msm_drm.ko` DSC-patched = a foldable that drives a 5K2K ultrawide at native 100 Hz. A genuine first
for this device.*
