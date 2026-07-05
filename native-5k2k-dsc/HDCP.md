# HDCP on the external display — diagnosis + prepared fix (force HDCP 1.4)

> Status (2026-07): **root-caused and a byte-validated patch is ready, but NOT yet
> applied or verified** — applying it needs a `vendor_dlkm` reflash (USB fastboot),
> and the USB-C port is occupied by the DP cable. See §4.

## 1. Symptom
With the LG 45GX950A connected, HDCP never authenticates — it loops forever:
```
[drm:dp_hdcp2p2_start_auth]
hdcp2p2_rcvd_msg failed :13
[sde-hdcp-2x] sde_hdcp_2x_msg_recvd: failed to process sink's response to AKE_SEND_CERT (13)
[drm:dp_display_hdcp_cb_work] HDCP_VERSION_2P2: HDCP_STATE_AUTH_FAIL     (repeats ~2/s)
```
`/sys/kernel/debug/drm_dp/hdcp` → `HDCP_VERSION_2P2: HDCP_STATE_AUTH_FAIL`, source `caps: 2`.
Protected video (Netflix/DRM) won't play on the external display; the desktop itself is unaffected.

## 2. Root cause
The source (SM8845) supports HDCP 2.2 and the LG **advertises** 2.2, so the driver always picks 2.2.
HDCP 2.2 auth starts with AKE: the source sends `AKE_Init`, waits ~110 ms, then reads the sink's
`AKE_Send_Cert` (534 bytes) from the HDCP DPCD region. On this LG-over-DP-alt link that read fails
(`hdcp2p2_rcvd_msg failed :13`) — the sink's certificate response is not returned correctly. The driver
**never falls back to HDCP 1.4**; it just retries 2.2 forever. This is a sink/link interop bug in the
LG's HDCP 2.2 DP implementation — not fixable from the phone at the 2.2 layer.

Runtime knobs that do **not** help: `hdcp_wait_sink_sync=1`, `hdcp_checking=2`, re-probe. There is no
userspace/debugfs way to force the HDCP version.

## 3. The fix — force HDCP 1.4 (skip 2.2)
HDCP 1.4 uses a different, simpler DPCD flow (0x68xxx, no certificate exchange) that may authenticate
where 2.2 fails. In `msm_drm.ko`, `dp_hdcp2p2_supported()` is the gate: it reads the sink RxCaps at
**DPCD 0x6921D** and returns true iff the 2.2-capable bit is set. Force it to return **false** and the
DP HDCP core skips 2.2 and uses the HDCP 1.4 module (`hdcp1_*`).

**Patch** (`scripts/apply-hdcp14-patch.py`): overwrite the function prologue with `mov w0,wzr ; ret`.
- Located by a unique 12-byte anchor (the 0x6921D DPCD read), function start = anchor − 0x74.
- `paciasp ; sub sp,sp,#0x40` (`3f2303d5 ff0301d1`) → `mov w0,wzr ; ret` (`e0031f2a c0035fd6`).
- **Validated** against the running (DSC-patched) ko: single anchor at `.ko` offset `0x4c408`,
  prologue matched, DSC patch (`0x953a4`) untouched. `7ed8b938… → ac1ab8c8…`. Disassembly confirms
  `dp_hdcp2p2_supported` now returns immediately.
- **Reversible** and DP-path-only (won't affect boot / internal panels).

## 4. Why it isn't applied yet (blocker)
`msm_drm.ko` loads in first-stage (before Magisk), so the only way to swap it is to rebuild
`vendor_dlkm` and **`fastboot flash vendor_dlkm`** — which needs **USB fastboot**. With the DP cable in
the USB-C port there is no USB-data connection (`getprop sys.usb.state` = `adb` over Wi-Fi only), so the
device can't enter a flashable fastboot state remotely. This must be done with the phone on USB.

## 5. Apply + test (when the phone is on USB)
```sh
# 1. patch the running DSC ko with the HDCP-1.4 force
python3 scripts/apply-hdcp14-patch.py \
    ../newer-software-W3WBS36.36-48-5-1/msm_drm.ko.patched  msm_drm.ko.dsc+hdcp14
# 2. rebuild vendor_dlkm on-device with this ko (same flow as README §4)
adb push msm_drm.ko.dsc+hdcp14 /data/local/tmp/msm_drm.ko.patched
adb shell su -c 'cd /data/local/tmp && sh rebuild-vendor-dlkm.sh'   # -> vendor_dlkm_new.img
adb pull /data/local/tmp/vendor_dlkm_new.img
# 3. flash (verity already disabled) — fastbootd, needs USB
adb reboot fastboot && fastboot flash vendor_dlkm vendor_dlkm_new.img && fastboot reboot
# 4. TEST: plug the monitor, then
adb shell su -c 'settings put global hdcp_checking 2; cat /sys/kernel/debug/drm_dp/hdcp'
#    success = HDCP_VERSION_1X: HDCP_STATE_AUTHENTICATED (or AUTH_SUCCESS)
adb shell su -c 'dmesg | grep -iE "hdcp|auth" | tail'
```
**Revert** (if 1.4 also fails or anything misbehaves): reflash the DSC-only `vendor_dlkm`
(rebuild from `../newer-software-W3WBS36.36-48-5-1/msm_drm.ko.patched` unmodified) or stock.

## 6. Open uncertainty
Forcing 1.4 is **unverified** — it will only help if this LG's HDCP **1.4** authenticates over the same
link where 2.2 fails. If 1.4 also fails, HDCP simply can't work with this monitor/cable and the
practical option is the repo's original quiet-it approach (`settings put global hdcp_checking 0`), which
disables HDCP (protected video won't play on the external display, desktop stays clean). Note also:
HDCP 1.4 caps some services (e.g. Netflix limits resolution without 2.2).
