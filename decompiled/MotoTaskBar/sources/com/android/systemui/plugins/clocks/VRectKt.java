package com.android.systemui.plugins.clocks;

import kotlin.ULong;

/* JADX INFO: compiled from: VRect.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VRectKt {
    private static final long BOTTOM_MASK = 65535;
    private static final long LEFT_MASK = -281474976710656L;
    private static final long RIGHT_MASK = 4294901760L;
    private static final long TOP_MASK = 281470681743360L;

    /* JADX INFO: Access modifiers changed from: private */
    public static final long pack(short s, short s2, short s3, short s4) {
        return ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(s2) << 32) & TOP_MASK) | ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(s) << 48) & LEFT_MASK)) | ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(s3) << 16) & RIGHT_MASK)) | ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(s4)) & BOTTOM_MASK));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: unpackBottom-VKZWuLQ, reason: not valid java name */
    public static final short m1572unpackBottomVKZWuLQ(long j) {
        return (short) ULong.m2715constructorimpl(ULong.m2715constructorimpl(j & BOTTOM_MASK));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: unpackLeft-VKZWuLQ, reason: not valid java name */
    public static final short m1573unpackLeftVKZWuLQ(long j) {
        return (short) ULong.m2715constructorimpl(ULong.m2715constructorimpl(j & LEFT_MASK) >>> 48);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: unpackRight-VKZWuLQ, reason: not valid java name */
    public static final short m1574unpackRightVKZWuLQ(long j) {
        return (short) ULong.m2715constructorimpl(ULong.m2715constructorimpl(j & RIGHT_MASK) >>> 16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: unpackTop-VKZWuLQ, reason: not valid java name */
    public static final short m1575unpackTopVKZWuLQ(long j) {
        return (short) ULong.m2715constructorimpl(ULong.m2715constructorimpl(j & TOP_MASK) >>> 32);
    }
}
