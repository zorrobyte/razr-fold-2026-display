#!/usr/bin/env python3
"""
vbmeta-disable-verity.py — set the AVB "disable verity/verification" flags in a
vbmeta image, so you can `fastboot flash vbmeta` it PLAIN (no --disable-* flags).

Why this exists
---------------
On this device + platform-tools 37.0.0, the normal one-liner

    fastboot --disable-verity --disable-verification flash vbmeta vbmeta.img

fails with:

    fastboot: error: Failed to find AVB_MAGIC at offset: 0

...even though vbmeta.img has a perfectly valid `AVB0` header at offset 0
(verify: `xxd vbmeta.img | head -1` -> `4156 4230 ...` = "AVB0"). The failure
is in fastboot's *host-side* AVB header patcher, and it happens in BOTH
fastbootd (is-userspace: yes) and the primary bootloader. See
../native-5k2k-dsc/README.md §9 ("AVB_MAGIC" gotcha).

The fix is to do what --disable-verity would have done ourselves: OR the two
disable bits into the vbmeta header `flags` field, then flash the result with a
plain `fastboot flash vbmeta`. On an UNLOCKED bootloader the now-broken vbmeta
signature is not enforced, so it flashes and boots fine.

AVB vbmeta header (avb_vbmeta_image.h): `flags` is a big-endian uint32 at byte
offset 120 (0x78). Bits:
    AVB_VBMETA_IMAGE_FLAGS_HASHTREE_DISABLED     = 0x1  (== --disable-verity)
    AVB_VBMETA_IMAGE_FLAGS_VERIFICATION_DISABLED = 0x2  (== --disable-verification)
Setting VERIFICATION_DISABLED on the TOP-LEVEL vbmeta disables the entire AVB
chain (vbmeta_system, vendor_dlkm hashtree, etc.), per the AVB spec.

Usage:
    python3 vbmeta-disable-verity.py stock_vbmeta.img vbmeta_verity_off.img
    fastboot flash vbmeta vbmeta_verity_off.img     # PLAIN, no --disable-* flags
"""
import sys, struct, hashlib

AVB_MAGIC = b"AVB0"
FLAGS_OFFSET = 120                       # 0x78, big-endian u32
HASHTREE_DISABLED = 0x1
VERIFICATION_DISABLED = 0x2


def main():
    if len(sys.argv) != 3:
        print(__doc__)
        sys.exit(1)
    src, dst = sys.argv[1], sys.argv[2]
    data = bytearray(open(src, "rb").read())

    if data[:4] != AVB_MAGIC:
        print(f"ERROR: {src} does not start with AVB_MAGIC ('AVB0'); "
              f"got {bytes(data[:4])!r}. Not a vbmeta image.")
        sys.exit(2)

    cur = struct.unpack_from(">I", data, FLAGS_OFFSET)[0]
    new = cur | HASHTREE_DISABLED | VERIFICATION_DISABLED
    struct.pack_into(">I", data, FLAGS_OFFSET, new)
    open(dst, "wb").write(data)

    print(f"{src}: flags 0x{cur:08x} -> 0x{new:08x} "
          f"(hashtree+verification disabled)")
    print(f"  wrote {dst} ({len(data)} bytes)")
    print(f"  sha256: {hashlib.sha256(bytes(data)).hexdigest()}")
    print(f"  now:  fastboot flash vbmeta {dst}   (PLAIN — do NOT pass --disable-* flags)")


if __name__ == "__main__":
    main()
