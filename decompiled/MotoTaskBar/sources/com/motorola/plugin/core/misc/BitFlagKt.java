package com.motorola.plugin.core.misc;

import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: BitFlag.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BitFlagKt {
    public static final void add(BitFlag bitFlag, Function0 function0) {
        bitFlag.getClass();
        function0.getClass();
        bitFlag.add(((Number) function0.mo2224invoke()).intValue());
    }

    public static final boolean contains(BitFlag bitFlag, int i) {
        bitFlag.getClass();
        return bitFlag.has(i);
    }

    public static final boolean contains(BitFlag bitFlag, BitFlag bitFlag2) {
        bitFlag.getClass();
        bitFlag2.getClass();
        return bitFlag.has(bitFlag2.getMyFlags());
    }

    public static final void del(BitFlag bitFlag, Function0 function0) {
        bitFlag.getClass();
        function0.getClass();
        bitFlag.del(((Number) function0.mo2224invoke()).intValue());
    }

    public static final void minusAssign(BitFlag bitFlag, int i) {
        bitFlag.getClass();
        bitFlag.del(i);
    }

    public static final void minusAssign(BitFlag bitFlag, BitFlag bitFlag2) {
        bitFlag.getClass();
        bitFlag2.getClass();
        bitFlag.del(bitFlag2.getMyFlags());
    }

    public static final void plusAssign(BitFlag bitFlag, int i) {
        bitFlag.getClass();
        bitFlag.add(i);
    }

    public static final void plusAssign(BitFlag bitFlag, BitFlag bitFlag2) {
        bitFlag.getClass();
        bitFlag2.getClass();
        bitFlag.add(bitFlag2.getMyFlags());
    }
}
