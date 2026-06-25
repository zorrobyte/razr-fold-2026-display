package androidx.compose.ui.unit;

/* JADX INFO: compiled from: TextUnit.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TextUnitKt {
    /* JADX INFO: renamed from: TextUnit-anM5pPY, reason: not valid java name */
    public static final long m1942TextUnitanM5pPY(float f, long j) {
        return pack(j, f);
    }

    /* JADX INFO: renamed from: checkArithmetic--R2X_6o, reason: not valid java name */
    public static final void m1943checkArithmeticR2X_6o(long j) {
        if (TextUnit.m1935getRawTypeimpl(j) == 0) {
            InlineClassHelperKt.throwIllegalArgumentException("Cannot perform operation for Unspecified type.");
        }
    }

    public static final long getSp(double d) {
        return pack(4294967296L, (float) d);
    }

    public static final long getSp(float f) {
        return pack(4294967296L, f);
    }

    public static final long getSp(int i) {
        return pack(4294967296L, i);
    }

    public static final long pack(long j, float f) {
        return TextUnit.m1932constructorimpl(j | (((long) Float.floatToRawIntBits(f)) & 4294967295L));
    }
}
