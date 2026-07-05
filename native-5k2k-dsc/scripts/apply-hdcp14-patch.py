#!/usr/bin/env python3
"""
apply-hdcp14-patch.py — force HDCP 1.4 (skip the broken HDCP 2.2 path) in a
stock/DSC-patched msm_drm.ko (Razr Fold 2026 / blanc / SM8845 "Eliza").

Why
---
The LG 45GX950A advertises HDCP 2.2 but its AKE_Send_Cert response is unreadable
over this DP-alt link, so auth loops forever and never falls back to 1.4:

    dp_hdcp2p2_start_auth
    hdcp2p2_rcvd_msg failed :13
    [sde-hdcp-2x] failed to process sink's response to AKE_SEND_CERT (13)
    HDCP_VERSION_2P2: HDCP_STATE_AUTH_FAIL   (repeats)

`dp_hdcp2p2_supported()` decides whether the driver uses HDCP 2.2: it reads the
sink RxCaps at DPCD 0x6921D and returns true if the 2.2-capable bit is set.
Forcing it to return false makes the DP HDCP core skip 2.2 and fall back to the
HDCP 1.4 module (hdcp1_*), which uses a different, simpler DPCD flow (0x68xxx)
that may authenticate where 2.2 does not.

The patch overwrites the function prologue with `mov w0, wzr ; ret`:

    paciasp / sub sp,sp,#0x40   ->   mov w0, wzr (0x2A1F03E0) ; ret (0xD65F03C0)

The function is located by a UNIQUE 12-byte anchor — the 0x6921D DPCD read
(`mov w1,#0x921d ; add x2,sp,#0x4 ; movk w1,#0x6,lsl#16`) — so the offset is
re-derived per build; the function start is anchor-0x74.

Reversible: DP-path only. Revert by reflashing the DSC-only (or stock) vendor_dlkm.

Usage:  python3 apply-hdcp14-patch.py msm_drm.ko.in msm_drm.ko.out
"""
import sys, hashlib

# anchor: mov w1,#0x921d ; add x2,sp,#0x4 ; movk w1,#0x6,lsl#16  (LE bytes)
ANCHOR = bytes.fromhex("a1439252" + "e2130091" + "c100a072")
FUNC_FROM_ANCHOR = -0x74                      # dp_hdcp2p2_supported start = anchor - 0x74
PROLOGUE = bytes.fromhex("3f2303d5" + "ff0301d1")   # paciasp ; sub sp,sp,#0x40
PATCH    = bytes.fromhex("e0031f2a" + "c0035fd6")   # mov w0,wzr ; ret


def main():
    if len(sys.argv) != 3:
        print(__doc__); sys.exit(1)
    src, dst = sys.argv[1], sys.argv[2]
    data = bytearray(open(src, "rb").read())

    hits = []
    i = 0
    while True:
        i = data.find(ANCHOR, i)
        if i < 0: break
        hits.append(i); i += 1
    if len(hits) != 1:
        print(f"ERROR: expected exactly 1 anchor, found {len(hits)}: {[hex(h) for h in hits]}")
        print("Re-derive with llvm-objdump --disassemble-symbols=dp_hdcp2p2_supported.")
        sys.exit(2)

    fn = hits[0] + FUNC_FROM_ANCHOR
    if data[fn:fn+8] != PROLOGUE:
        print(f"ERROR: prologue mismatch at 0x{fn:x}: {data[fn:fn+8].hex()} "
              f"(expected {PROLOGUE.hex()}). Build differs — re-verify.")
        sys.exit(3)

    print(f"dp_hdcp2p2_supported @ .ko offset 0x{fn:x}")
    data[fn:fn+8] = PATCH
    open(dst, "wb").write(data)
    print(f"patched {dst}: prologue -> 'mov w0,wzr; ret' (always report HDCP 2.2 unsupported)")
    print(f"  in  sha256: {hashlib.sha256(open(src,'rb').read()).hexdigest()}")
    print(f"  out sha256: {hashlib.sha256(bytes(data)).hexdigest()}")


if __name__ == "__main__":
    main()
