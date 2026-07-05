#!/system/bin/sh
# Persist adb-over-TCP on 5555 across reboots.
#
# Why: on the Razr Fold 2026 the single USB-C port is used by the USB-C->DP
# cable when driving the external display, so USB adb is unavailable. This keeps
# a wireless adb transport up automatically after every boot:
#     adb connect <phone-wlan-ip>:5555
#
# Runs as a Magisk late_start service (root, after boot).

# wait for boot + network to settle
i=0
while [ "$(getprop sys.boot_completed)" != "1" ] && [ $i -lt 60 ]; do
  sleep 2; i=$((i+1))
done
sleep 3

# tell adbd to listen on TCP and bounce it so the transport binds
resetprop service.adb.tcp.port 5555 2>/dev/null || setprop service.adb.tcp.port 5555
setprop persist.adb.tcp.port 5555
stop adbd
start adbd
