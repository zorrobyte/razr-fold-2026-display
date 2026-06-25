package androidx.compose.ui.geometry;

/* JADX INFO: compiled from: Size.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SizeKt {
    /* JADX INFO: renamed from: getCenter-uvyYCjk, reason: not valid java name */
    public static final long m212getCenteruvyYCjk(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) / 2.0f;
        return Offset.m182constructorimpl((((long) Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L)) / 2.0f)) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32));
    }

    /* JADX INFO: renamed from: toRect-uvyYCjk, reason: not valid java name */
    public static final Rect m213toRectuvyYCjk(long j) {
        return RectKt.m200Recttz77jQw(Offset.Companion.m195getZeroF1C5BW0(), j);
    }
}
