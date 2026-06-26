#!/system/bin/sh
# Native 5K2K mode selection (persistence).
# Requires the max_dsc_count patch already flashed into vendor_dlkm.
#
# Arms the QTI mode_override so the external display comes up at native
# 5120x2160@100 with DSC. Setting edid_modes while the cable is disconnected
# persists in the dp_panel struct, so the NEXT dock applies it automatically.
# (Docked-at-boot: the DP probes in first-stage before this runs, so it may
#  come up at 3440 once — one replug then applies 5120.)

# quiet the HDCP 2.2 auth-retry loop (LG fails HDCP over DP-alt). Persistent setting.
settings put global hdcp_checking 0 2>/dev/null

mount -t debugfs none /sys/kernel/debug 2>/dev/null

# wait for the DP driver's debugfs node, then arm 5120@100
i=0
while [ ! -e /sys/kernel/debug/drm_dp/edid_modes ] && [ $i -lt 60 ]; do
    sleep 2; i=$((i+1))
done

if [ -e /sys/kernel/debug/drm_dp/edid_modes ]; then
    echo "5120 2160 100 0" > /sys/kernel/debug/drm_dp/edid_modes
    log -t DSC_5K "armed mode_override 5120x2160@100 ($?)"
fi

# best-effort: re-arm whenever the cable goes disconnected (so the next plug is native)
( S=/sys/class/drm/card0-DP-1/status
  prev=$(cat $S 2>/dev/null)
  while true; do
      sleep 5
      cur=$(cat $S 2>/dev/null)
      if [ "$cur" = "disconnected" ] && [ "$prev" != "disconnected" ]; then
          echo "5120 2160 100 0" > /sys/kernel/debug/drm_dp/edid_modes 2>/dev/null
      fi
      prev="$cur"
  done ) &
