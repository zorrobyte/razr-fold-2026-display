package androidx.compose.ui.unit;

import androidx.compose.ui.geometry.Offset;

/* JADX INFO: compiled from: IntOffset.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class IntOffsetKt {
    /* JADX INFO: renamed from: minus-Nv-tHpc, reason: not valid java name */
    public static final long m1003minusNvtHpc(long j, long j2) {
        return Offset.m182constructorimpl((((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32)) - IntOffset.m997getXimpl(j2))) << 32) | (((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L)) - IntOffset.m998getYimpl(j2))) & 4294967295L));
    }

    /* JADX INFO: renamed from: plus-Nv-tHpc, reason: not valid java name */
    public static final long m1004plusNvtHpc(long j, long j2) {
        return Offset.m182constructorimpl((((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32)) + IntOffset.m997getXimpl(j2))) << 32) | (((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L)) + IntOffset.m998getYimpl(j2))) & 4294967295L));
    }

    /* JADX INFO: renamed from: round-k-4lQ0M, reason: not valid java name */
    public static final long m1005roundk4lQ0M(long j) {
        int iRound = Math.round(Float.intBitsToFloat((int) (j >> 32)));
        return IntOffset.m995constructorimpl((((long) Math.round(Float.intBitsToFloat((int) (j & 4294967295L)))) & 4294967295L) | (((long) iRound) << 32));
    }
}
