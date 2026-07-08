#!/system/bin/sh
# rebuild-vendor-dlkm.sh — run ON-DEVICE as root (su).
# Rebuilds the vendor_dlkm EROFS with the patched msm_drm.ko, preserving SELinux
# labels (native on Android), correct compression (lz4hc), and partition fit.
#
# Inputs in /data/local/tmp:
#   vendor_dlkm_b.img      (stock partition dump)
#   msm_drm.ko.patched     (from apply-patch.py)
# Output:
#   vendor_dlkm_new.img    (flash this to vendor_dlkm)
#
# Requires: /system/bin/{fsck.erofs,mkfs.erofs,dump.erofs} (present on this device).

set -e
cd /data/local/tmp

UUID=9e483606-ec3c-5687-9f51-3a0f140e5aec   # match the stock FS UUID (dump.erofs -s shows it)
PART_SIZE=28672000                          # vendor_dlkm partition size in bytes (must fit)

echo ">> extracting stock EROFS"
rm -rf vdlkm; mkdir vdlkm
fsck.erofs --extract=vdlkm vendor_dlkm_b.img

echo ">> swapping in patched msm_drm.ko"
cp msm_drm.ko.patched vdlkm/lib/modules/msm_drm.ko

echo ">> restoring SELinux labels (vendor_file everywhere, vendor_configs_file under /etc)"
chcon -hR u:object_r:vendor_file:s0 vdlkm
chcon -hR u:object_r:vendor_configs_file:s0 vdlkm/etc

echo ">> building EROFS (lz4hc to fit the partition)"
rm -f vendor_dlkm_new.img
mkfs.erofs -zlz4hc --all-root -U "$UUID" vendor_dlkm_new.img vdlkm

SZ=$(stat -c %s vendor_dlkm_new.img)
echo ">> size: $SZ (partition $PART_SIZE)"
[ "$SZ" -le "$PART_SIZE" ] || { echo "!! TOO BIG — abort"; exit 1; }

echo ">> verifying patched module + labels via loop-mount (NOT extract — extract relabels to /data ctx)"
rm -rf m; mkdir m
mount -t erofs -o ro,loop vendor_dlkm_new.img m
echo "   msm_drm.ko label : $(ls -Z m/lib/modules/msm_drm.ko | awk '{print $1}')"
echo "   msm_drm.ko sha   : $(sha256sum m/lib/modules/msm_drm.ko | cut -c1-16)"
echo "   build.prop label : $(ls -Z m/etc/build.prop | awk '{print $1}')"
umount m; rm -rf m

echo ">> DONE. Pull vendor_dlkm_new.img and flash it (see README §5)."
