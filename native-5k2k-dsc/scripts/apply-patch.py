#!/usr/bin/env python3
"""
apply-patch.py — force dp_display->max_dsc_count = 4 in a stock msm_drm.ko
(Razr Fold 2026 / blanc / SM8845 "Eliza"), unlocking external-display DSC.

Patches the single instruction in dp_drm_bridge_init() that copies the
max_dsc_count argument (w3) into w19 (later stored to display->max_dsc_count):

    mov  w19, w3   (f3 03 03 2a)   ->   movz w19, #4   (93 00 80 52)

The 8-byte pair `mov w19,w3; mov w20,w2` appears ~23x in the module, so we
locate the function by its UNIQUE 16-byte prologue and patch only that site.

Usage:  python3 apply-patch.py msm_drm.ko.stock msm_drm.ko.patched
"""
import sys, hashlib

# dp_drm_bridge_init prologue: mov w19,w3 ; mov w20,w2 ; mov x23,x1 ; mov x21,x0
ANCHOR = bytes.fromhex("f303032a" + "f403022a" + "f70301aa" + "f50300aa")
STOCK_INSTR   = bytes.fromhex("f303032a")   # mov  w19, w3
PATCH_INSTR   = bytes.fromhex("93008052")   # movz w19, #4

def main():
    if len(sys.argv) != 3:
        print(__doc__); sys.exit(1)
    src, dst = sys.argv[1], sys.argv[2]
    data = bytearray(open(src, "rb").read())

    hits = []
    start = 0
    while True:
        i = data.find(ANCHOR, start)
        if i < 0:
            break
        hits.append(i)
        start = i + 1

    if len(hits) != 1:
        print(f"ERROR: expected exactly 1 unique anchor, found {len(hits)}: "
              f"{[hex(h) for h in hits]}")
        print("This build differs — re-derive with llvm-objdump "
              "(--disassemble-symbols=dp_drm_bridge_init) and update ANCHOR.")
        sys.exit(2)

    off = hits[0]
    assert data[off:off+4] == STOCK_INSTR, "anchor/instruction mismatch"
    print(f"dp_drm_bridge_init mov w19,w3 at .ko offset 0x{off:x}")
    data[off:off+4] = PATCH_INSTR
    open(dst, "wb").write(data)

    changed = sum(1 for a, b in zip(open(src,"rb").read(), data) if a != b)
    print(f"patched {dst}: {changed} byte(s) changed (expect 4)")
    print(f"  patched insn @0x{off:x}: {data[off:off+4].hex()} (movz w19,#4)")
    print(f"  stock   sha256: {hashlib.sha256(open(src,'rb').read()).hexdigest()}")
    print(f"  patched sha256: {hashlib.sha256(bytes(data)).hexdigest()}")

if __name__ == "__main__":
    main()
