package androidx.compose.ui.geometry;

/* JADX INFO: compiled from: Rect.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RectKt {
    /* JADX INFO: renamed from: Rect-tz77jQw, reason: not valid java name */
    public static final Rect m200Recttz77jQw(long j, long j2) {
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        return new Rect(Float.intBitsToFloat(i), Float.intBitsToFloat(i2), Float.intBitsToFloat(i) + Float.intBitsToFloat((int) (j2 >> 32)), Float.intBitsToFloat(i2) + Float.intBitsToFloat((int) (j2 & 4294967295L)));
    }
}
