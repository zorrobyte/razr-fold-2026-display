# Kernel / DSC Feasibility Report (background agent, 2026-06-25)

Goal: enable DSC + higher modes over external DP on Razr Fold 2026 (blanc, SM8845 / SD 8 Gen 5).

## Key findings
1. **Kernel source NOT published** for blanc. Moto GPL portal: github.com/MotorolaMobilityLLC/kernel-msm.
   An open, unfulfilled GPL request matching our build exists: **issue #836 "W3WB36.36-48-5"** (2026-05-28).
   No SM8845/"canoe" sibling source exists either. Expect ~3–9+ months post-launch.
2. **GKI 2.0 / android16-6.12.** Display driver = loadable vendor module (`msm_drm.ko` etc.) in
   `vendor_dlkm`. Rebuildable against GKI+KMI *if* source existed.
3. **DSC gate = devicetree.** `qcom,dsc-feature-enable` + `qcom,max-dp-dsc-blks` +
   `qcom,max-dp-dsc-input-width-pixs` on the DP node. Driver path `dp_panel_get_supported_bpp()` /
   `mode_valid` DROPS modes that don't fit the *uncompressed* budget when DSC isn't applied — exactly our
   symptom (5K2K@120 / 4K@120 dropped).
4. **No-source route: edit dtbo, reflash.** If the blocker is a DT property, add/modify it and
   `fastboot flash dtbo` — no kernel source needed.

## Verdict
**Feasible NOW via devicetree (dtbo) edit; full kernel rebuild only feasible once source releases.**

## What we found on-device (extends the agent's hypothesis)
DSC is ALREADY enabled (`qcom,dsc-feature-enable` present). The real artificial caps on
`/soc/qcom,dp_display@9ad2000`:
- **`qcom,max-pclk-frequency-khz = 675000` (675 MHz)** — pixel-clock cap, exactly enough for 5K2K@60.
  This is what drops 5K2K@120 / 4K@120.
- **`qcom,dp-hbr3-disable`** (present) — forces HBR2 instead of HBR3.
- `qcom,dp-downgrade` present.

Plan: edit dtbo → raise max-pclk (→ ~1.6 GHz) + remove hbr3-disable → repack → `fastboot flash dtbo_b`.
Recovery: stock dtbo backed up (`dtbo-mod/dtbo.stock.img`) + in the 15 GB firmware zip.

Sources: kernel-msm repo + issues, AOSP GKI/DLKM docs, sde-dp.txt DT binding, techpack dp_panel.c, LWN DSC.
