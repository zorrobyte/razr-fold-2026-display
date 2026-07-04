#!/sbin/sh
# Installs the patched services.jar (layer 1a + 1b) and invalidates the stale dexopt +
# fs-verity metadata so ART loads the patched dex directly from the jar. Build 48-5-1.
ui_print "- Display-cap + ReadyFor mode-rebuild (services.jar, build 48-5-1)"
set_perm "$MODPATH/system/framework/services.jar" 0 0 0644 u:object_r:system_file:s0

ui_print "- Invalidating stale services oat + fs-verity meta"
OAT="$MODPATH/system/framework/oat/arm64"
mkdir -p "$OAT"
for f in services.odex services.vdex services.art \
         services.odex.fsv_meta services.vdex.fsv_meta services.art.fsv_meta; do
  mknod "$OAT/$f" c 0 0
done
mknod "$MODPATH/system/framework/services.jar.fsv_meta" c 0 0 2>/dev/null
mknod "$MODPATH/system/framework/services.jar.prof" c 0 0 2>/dev/null
mknod "$MODPATH/system/framework/services.jar.prof.fsv_meta" c 0 0 2>/dev/null

ui_print "- Done. Reboot to apply. (Recovery: disable the module via Magisk safe-mode.)"
