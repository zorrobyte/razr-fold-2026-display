# Razr Fold 2026 — external display unlock: native 5K2K + DSC

Driving a **Motorola Razr Fold 2026** (Snapdragon 8 Gen 5 / **SM8845**, "Eliza" DPU; codename
`blanc`) at **native 5120×2160 @ 100 Hz, 10-bit, with DSC** on a wired ultrawide — resolutions and a
compression path the device refuses out of the box. Also does 4K@60 and 3440×1440@100 on a Samsung UW.

This took **two independent unlocks** stacked on top of bootloader-unlock + Magisk root. Both are
required for the full native-5K2K-with-DSC result:

## The stack (what we actually had to do)

| # | Layer | Problem | Fix | Where |
|---|---|---|---|---|
| 1 | **Framework** | AOSP's `enable_mode_limit_for_external_display` governor filters out any external mode >5.54 MP — so 5120×2160 and 4K never even appear. | Patch `DisplayManagerFlags.isExternalDisplayLimitModeEnabled()` → `false`. Delivered as a **`services.jar` patch** (dex 039, smali `--api 28`) and later as an **LSPosed/Vector module**. | `framework-patch/`, `lsposed-module/`, FINDINGS §11/§17/§23 |
| 2 | **Link/cable** | A USB-C **dock** splits lanes (2-lane DP + USB3), halving bandwidth → 3440@60 cap. | **Direct USB-C→DP cable** (4-lane), or a 4-lane dock. | FINDINGS §17 |
| 3 | **Kernel (DSC)** | Qualcomm/Moto left `SDE_DP_DSC_RESERVATION_SWITCH` **off** for Eliza, so the DP path's DSC budget is computed as **0** at boot → DSC never engages → only uncompressed timings (5120@60 max, which the panel won't take at native). **FINDINGS §18 called this "kernel-locked, not forceable."** | **Binary-patch `msm_drm.ko`**: force `dp_display->max_dsc_count = 4` (one instruction). DSC engages, the monitor's real 5120@100 DSC timing validates, SDE reserves the DSC pair. | **`native-5k2k-dsc/`** (this session) |
| + | Settings | Cross-group switch + HDCP retry loop. | `settings put secure match_content_frame_rate 2`; `settings put global hdcp_checking 0`. | `native-5k2k-dsc/` |
| + | Mode select | SF defaults to the EDID-preferred 3440. | QTI `mode_override`: `echo "5120 2160 100 0" > .../drm_dp/edid_modes` + replug. | `native-5k2k-dsc/` |

### The progression
1. **Framework patch (§11/§17)** removed the software cap → the picker offered 5120×2160 / 4K /
   3440@100, and with a direct 4-lane cable they *worked* — but **uncompressed**, so 5K2K topped out at
   **@60** and 5K2K@120 / 4K@120 were dropped.
2. **§18 concluded DSC was kernel-gated and unforceable without kernel source** — that was the
   documented ceiling. §19 explored a dtbo-cap angle as an alternative.
3. **This session broke that ceiling** by reverse-engineering the *actual* DSC blocker
   (`max_dsc_count = 0`, not a hard kernel/PHY limit) and binary-patching the vendor display module
   → **native 5120×2160 @ 100 Hz with DSC, sharp and smooth.** See **`native-5k2k-dsc/`** for the
   complete, replicate-from-scratch writeup, scripts, and binaries; **`native-5k2k-dsc/PROOF.md`** for
   the kernel evidence. This **supersedes FINDINGS §18's "not forceable" conclusion.**

## Where everything lives
- **`native-5k2k-dsc/`** — ⭐ the DSC kernel-module unlock (the final win): README, scripts,
  Magisk persistence module, patched/stock `msm_drm.ko`, ready-to-flash `vendor_dlkm`, recovery backups,
  PROOF.
- **`FINDINGS.md`** — the full investigation log (§1–§23): bootloader unlock, root, desktop mode, the
  resolution-cap root cause, the framework patch, the lane/bandwidth analysis, the refresh-rate
  investigations, the DSC dead-ends, the dtbo-cap path.
- **`framework-patch/`** — the `services.jar` / `DisplayManagerFlags` patch (the framework cap removal).
- **`lsposed-module/`** — same cap removal as an LSPosed/Vector module (`com.dispunlock`).
- **`dtbo-mod/`** — the devicetree-cap angle (`max-pclk`, `hbr3-disable`) + stock dtbo backup.
- **`firmware-boot/`, `decompiled/`, `apks/`, `tools/`** — supporting artifacts.
- **`REDDIT-POST.md`, `DEX-POST.md`** — write-ups.

## Final achieved state
Razr Fold 2026, bootloader-unlocked + Magisk + framework-cap-removed + **`msm_drm.ko` DSC-patched**,
driving an **LG 45GX950A** at **native 5120×2160 @ 100 Hz, 10-bit, DSC** (and 4K@60 / 3440×1440@100)
over a direct USB-C→DP cable — sharp, smooth, and permanent.

> **Hard-won gotcha that cost an hour:** never leave the DRM debug mask
> (`/sys/module/drm/parameters/debug = 0x07`) on — it logs every plane/commit per frame, cripples the
> display pipeline, and poisons CPU measurements. With it off, native 5K2K-DSC is ~14% composer / ~85%
> idle. (Full gotcha list in `native-5k2k-dsc/README.md` §9.)
