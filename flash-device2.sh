#!/usr/bin/env bash
#
# flash-device2.sh — clone the native-5K2K-DSC unlock onto a SECOND Razr Fold 2026.
#
#   Brings a fresh `blanc` to: rooted (Magisk) + DSC-patched vendor_dlkm +
#   v4 services.jar framework patch + the 5K Display Control app + the display
#   settings. After this, native 5120×2160@100 + DSC works exactly like device #1.
#
# ── HARD REQUIREMENTS (read CLONE.md first) ──────────────────────────────────
#   • Target is a Motorola Razr Fold 2026, codename `blanc`.
#   • Target is on build  W3WBS36.36-48-5-1  (same as the patched artifacts).
#       Different build → flash that build's factory image FIRST, or the patched
#       vendor_dlkm / services.jar will NOT match (verity hash + dex offsets).
#   • Bootloader is UNLOCKED on the target (this script does not unlock it).
#   • `fastboot` and `adb` are on PATH (platform-tools).
#   • This is per-system only. It NEVER touches persist/modem/IMEI/userdata.
#
# Usage:   ./flash-device2.sh            # interactive, with confirmations
#          ./flash-device2.sh --force    # skip the build-match guard (advanced)
#          ./flash-device2.sh --dry-run  # print every command, flash nothing
#
set -euo pipefail
cd "$(dirname "$0")"

EXPECT_DEVICE="blanc"
EXPECT_BUILD="W3WBS36.36-48-5-1"

VENDOR_DLKM="native-5k2k-dsc/artifacts/vendor_dlkm_patched.img"
INIT_BOOT="firmware-boot/magisk_patched.img"     # stock init_boot + Magisk 30.7
VBMETA="firmware-boot/vbmeta.img"                # stock; verity disabled at flash
VBMETA_SYS="firmware-boot/vbmeta_system.img"     # stock; verity disabled at flash
MAGISK_APK="tools/Magisk-v30.7.apk"
SVC_MODULE="framework-patch/services_desktop5k_v4.zip"
APP_APK="native-5k2k-dsc/app/5K-Display-Control.apk"
SUMS="CLONE-SHA256SUMS.txt"

FORCE=0; DRY=0
for a in "$@"; do
  case "$a" in
    --force) FORCE=1;;
    --dry-run) DRY=1;;
    *) echo "unknown arg: $a"; exit 2;;
  esac
done

c(){ printf '\033[1;36m%s\033[0m\n' "$*"; }      # cyan banner
ok(){ printf '\033[1;32m%s\033[0m\n' "$*"; }     # green
warn(){ printf '\033[1;33m%s\033[0m\n' "$*"; }   # yellow
die(){ printf '\033[1;31mERROR: %s\033[0m\n' "$*" >&2; exit 1; }
run(){ if [ "$DRY" = 1 ]; then echo "  + $*"; else echo "  + $*"; "$@"; fi; }
pause(){ printf '\033[1;35m%s\033[0m ' "$*"; read -r _ < /dev/tty; }

# ── 0. preflight ─────────────────────────────────────────────────────────────
command -v fastboot >/dev/null || die "fastboot not on PATH"
command -v adb >/dev/null || die "adb not on PATH"
c "==> Verifying clone-kit artifacts against $SUMS"
if command -v shasum >/dev/null; then HASH="shasum -a256"; else HASH="sha256sum"; fi
for f in "$VENDOR_DLKM" "$INIT_BOOT" "$VBMETA" "$VBMETA_SYS" "$MAGISK_APK" "$SVC_MODULE" "$APP_APK"; do
  [ -f "$f" ] || die "missing artifact: $f"
done
# verify the ones listed in the manifest (process-substitution so `die` aborts the script)
while read -r want path; do
  [ -z "$want" ] && continue
  got=$($HASH "$path" | awk '{print $1}')
  [ "$got" = "$want" ] && ok "  ok  $path" || die "checksum MISMATCH: $path"
done < <(grep -v '^#' "$SUMS")

cat <<EOF

$(c "================ Razr Fold 2026 — clone native 5K2K-DSC to device #2 ================")
This will, on the connected target device:
  1. (fastboot)  flash stock vbmeta + vbmeta_system  WITH verity/verification DISABLED
  2. (fastboot)  flash init_boot = Magisk-patched (root)
  3. (fastbootd) flash vendor_dlkm = DSC-patched (max_dsc_count)
  4. (booted)    install Magisk app + v4 services.jar module + 5K Display Control app
  5. (booted)    apply display settings, reboot
It does NOT touch persist / modem / IMEI / userdata. Bootloader must already be unlocked.
$([ "$DRY" = 1 ] && warn '*** DRY RUN — nothing will actually be flashed ***')
EOF
pause "Type-and-enter to continue (Ctrl-C to abort):"

# ── 1. identity guard (device booted with adb) ───────────────────────────────
c "==> Checking target identity over adb (skip if device not booted)"
if adb get-state >/dev/null 2>&1; then
  dev=$(adb shell getprop ro.product.device 2>/dev/null | tr -d '\r')
  bld=$(adb shell getprop ro.build.display.id 2>/dev/null | tr -d '\r')
  echo "  device=$dev  build=$bld"
  [ "$dev" = "$EXPECT_DEVICE" ] || { [ "$FORCE" = 1 ] && warn "device != $EXPECT_DEVICE (forced)" || die "target is '$dev', expected '$EXPECT_DEVICE' (use --force to override)"; }
  if [ "$bld" != "$EXPECT_BUILD" ]; then
    [ "$FORCE" = 1 ] && warn "build '$bld' != '$EXPECT_BUILD' (forced — patches may not match!)" \
      || die "build is '$bld', patched artifacts are for '$EXPECT_BUILD'. Flash that factory image first, or --force."
  fi
  c "==> Rebooting target to bootloader"
  run adb reboot bootloader
else
  warn "  No adb device. Put the target in BOOTLOADER (fastboot) mode manually."
fi
pause "Press ENTER when the device is in BOOTLOADER (fastboot) mode:"

# ── 2. bootloader: vbmeta (verity off) + init_boot ───────────────────────────
fastboot getvar unlocked 2>&1 | grep -qi 'unlocked: *yes' || {
  [ "$FORCE" = 1 ] && warn "bootloader not reported unlocked (forced)" || die "bootloader is LOCKED — unlock first (this wipes the device)."
}
c "==> Flashing vbmeta + vbmeta_system with verity/verification DISABLED"
run fastboot --disable-verity --disable-verification flash vbmeta "$VBMETA"
run fastboot --disable-verity --disable-verification flash vbmeta_system "$VBMETA_SYS"
c "==> Flashing Magisk-patched init_boot (root)"
run fastboot flash init_boot "$INIT_BOOT"
c "==> Entering userspace fastboot (fastbootd) for the dynamic partition"
run fastboot reboot fastboot
pause "Press ENTER when the device shows the fastbootd screen:"

# ── 3. fastbootd: vendor_dlkm (DSC patch) ────────────────────────────────────
c "==> Flashing DSC-patched vendor_dlkm"
run fastboot flash vendor_dlkm "$VENDOR_DLKM"
c "==> Rebooting to system (first boot may take a few minutes)"
run fastboot reboot
pause "Press ENTER once Android has booted AND USB debugging is authorized:"

# ── 4. booted: Magisk app + framework module + app + settings ────────────────
run adb wait-for-device
c "==> Installing Magisk manager app"
run adb install -r "$MAGISK_APK" || warn "Magisk app install failed — install $MAGISK_APK by hand"
warn ">>> If Magisk prompts on-device, GRANT root to 'shell' (adb) now."
pause "Press ENTER once shell has root (open Magisk → it should show installed):"

c "==> Installing v4 services.jar framework module"
run adb push "$SVC_MODULE" /data/local/tmp/svc5k.zip
run adb shell su -c "magisk --install-module /data/local/tmp/svc5k.zip" || \
  warn "module install failed — in Magisk app: Modules → Install from storage → $SVC_MODULE"

c "==> Installing 5K Display Control app"
run adb install -r "$APP_APK" || warn "app install failed — install $APP_APK by hand"

c "==> Applying display settings"
run adb shell su -c "settings put secure match_content_frame_rate 2; settings put global hdcp_checking 0"

c "==> Rebooting to apply the framework module"
run adb reboot

# ── 5. done ──────────────────────────────────────────────────────────────────
cat <<EOF

$(ok "================ DONE — device #2 cloned ================")
After it boots:
  • Plug in the monitor (direct USB-C→DP cable for 4-lane / native 5120).
  • Open "5K Display Control": tap the mode you want (★ 5K2K-DSC), then replug.
  • Drag the UI-scale slider (~170–200 dpi) for comfortable size.

Recovery (revert this device): flash stock back —
  fastboot reboot fastboot
  fastboot flash vendor_dlkm native-5k2k-dsc/backup/vendor_dlkm_b.stock.img
  fastboot reboot bootloader
  fastboot flash vbmeta firmware-boot/vbmeta.img         # no disable flags
  fastboot flash vbmeta_system firmware-boot/vbmeta_system.img
  fastboot flash init_boot firmware-boot/init_boot.img   # stock (un-roots)
EOF
