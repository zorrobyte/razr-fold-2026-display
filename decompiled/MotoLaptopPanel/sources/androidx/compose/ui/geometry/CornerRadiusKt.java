package androidx.compose.ui.geometry;

/* JADX INFO: compiled from: CornerRadius.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CornerRadiusKt {
    public static final long CornerRadius(float f, float f2) {
        return CornerRadius.m745constructorimpl((((long) Float.floatToRawIntBits(f2)) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
    }
}
