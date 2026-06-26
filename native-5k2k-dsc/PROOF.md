# Proof — native 5120×2160@100 + DSC, working and smooth

All captured on-device (`blanc`, build `W3WBS36V.36-48-ST4.6-5`) after flashing the patched
`vendor_dlkm` and forcing `mode_override = 5120 2160 100 0`.

## 1. The patch flips the DSC budget (the whole point)
`dp_display_convert_to_dp_mode` budget print (DRM-KMS debug mask on briefly):

```
# BEFORE (stock):
After: in_use:0, max:0,  free:0, req:2, caps:0x0     ← max_dsc_count=0, DP_PANEL_CAPS_DSC never set
get_available_dp_resources: max_dsc:0, avail_dsc:4, dp_avail_dsc:0   ← 4 blocks free, DP clamped to 0

# AFTER (patched, max_dsc_count forced to 4):
After: in_use:2, max:4,  free:4, req:2, caps:0x1     ← caps:0x1 = DP_PANEL_CAPS_DSC SET
```

## 2. The kernel scans out native 5120 with DSC
```
dp_debug: resolution=5120x2160@100Hz   bpp=30   num_lanes=4   link_rate=810000 (HBR3)

dp_panel_resolution_info: 5120(120|8|32|0)x2160(52|45|8|1)@100fps 30bpp 1196340Khz 20LR 4Ln
dp_display_stream_enable:  ... tot_dsc_blks_in_use=2        ← 2 DSC blocks in use

_sde_rm_reserve_dsc: blk id = 1, _dsc_ids[0] = 1            ← SDE RM reserved the DSC pair
_sde_rm_reserve_dsc: blk id = 2, _dsc_ids[1] = 2              (this reservation ALWAYS failed pre-patch)

sde_plane ... 2560x2160+0+0  +  2560x2160+2560+0            ← DSCMERGE dual-pipe = 5120 wide
drm_mode_debug_printmodeline "5120x2160": 100 1196340 5120 5128 5160 5280 2160 2205 2213 2265
```

## 3. The framework + monitor agree
```
dumpsys display: HDMI Screen ... real 5120 x 2160     (LG ULTRAGEAR+)
Monitor OSD: 5120x2160 @ 100 Hz   (confirmed by eye — sharp, 1:1)
```

## 4. It's smooth (clean load, NO debug mask)
At native 5120×2160@100 + DSC, idle/light interaction:

| thread | CPU |
|---|---|
| `vendor.qti.hardware.display.composer-service` | **~14%** |
| `crtc_commit` (S-state, not blocked) | **~10%** |
| `surfaceflinger` | ~14% |
| **system idle** | **~85%** |

> ⚠️ Earlier "composer 192% / crtc_commit 80% D-state / laggy" readings were **entirely caused by
> leaving the DRM debug mask (`/sys/module/drm/parameters/debug = 0x07`) on** — it logs every
> plane/commit per frame, which cripples the pipeline and poisons measurements. With it off, native
> 5K2K is smooth. Always `echo 0 > /sys/module/drm/parameters/debug` after reading.

## Patched module identity
```
stock   msm_drm.ko  sha256 03c53b47fdf01232...
patched msm_drm.ko  sha256 7ed8b938b302ecce...   (4 bytes changed: mov w19,w3 -> movz w19,#4)
```
