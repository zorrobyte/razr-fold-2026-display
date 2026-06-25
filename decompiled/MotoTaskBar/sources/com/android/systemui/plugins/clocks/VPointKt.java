package com.android.systemui.plugins.clocks;

import kotlin.ULong;

/* JADX INFO: compiled from: VPoint.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VPointKt {
    private static final long X_MASK = -4294967296L;
    private static final long Y_MASK = 4294967295L;

    /* JADX INFO: Access modifiers changed from: private */
    public static final long pack(int i, int i2) {
        return ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(i2)) & Y_MASK) | ULong.m2715constructorimpl(ULong.m2715constructorimpl(ULong.m2715constructorimpl(i) << 32) & X_MASK));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: unpackX-VKZWuLQ, reason: not valid java name */
    public static final int m1516unpackXVKZWuLQ(long j) {
        return (int) ULong.m2715constructorimpl(ULong.m2715constructorimpl(j & X_MASK) >>> 32);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: unpackY-VKZWuLQ, reason: not valid java name */
    public static final int m1517unpackYVKZWuLQ(long j) {
        return (int) ULong.m2715constructorimpl(ULong.m2715constructorimpl(j & Y_MASK));
    }
}
