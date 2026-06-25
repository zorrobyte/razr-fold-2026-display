# dtbo DSC/pclk unlock
- `dtbo.stock.img` — original dtbo partition dump (FULL, for recovery)
- `dtbo.patched.img` — modified dtbo image (flash to dtbo_<slot>)
- `dts/` — decompiled+edited overlays (fragment@175 / &sde_dp)

Edit (all 3 board DTBs, fragment@175 __overlay__ target=&sde_dp):
- REMOVED `qcom,dp-hbr3-disable;`  (was forcing HBR2)
- REMOVED `qcom,dp-downgrade;`
- ADDED  `qcom,max-pclk-frequency-khz = <1600000>;`  (overrides base 675000 = 675MHz -> 1.6GHz)

Flash: `dd if=dtbo.patched.img of=/dev/block/by-name/dtbo_b` (root) then reboot.
Revert: `dd if=dtbo.stock.img of=/dev/block/by-name/dtbo_b` then reboot.
