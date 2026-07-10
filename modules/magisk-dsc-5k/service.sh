#!/system/bin/sh
# Quiet the HDCP 2.2 auth-retry loop, and nothing else.
#
# The LG 5K2K panel fails HDCP 2.2 over DP-alt and spins a noisy auth-retry loop;
# clearing hdcp_checking stops it. Resolution / mode selection is handled entirely
# by the 5K Display Control app — never a background service.
#
# settings put can race the settings provider at late_start, so retry until it sticks.
i=0
while [ $i -lt 30 ]; do
    settings put global hdcp_checking 0 2>/dev/null
    [ "$(settings get global hdcp_checking 2>/dev/null)" = "0" ] && break
    sleep 2
    i=$((i + 1))
done
