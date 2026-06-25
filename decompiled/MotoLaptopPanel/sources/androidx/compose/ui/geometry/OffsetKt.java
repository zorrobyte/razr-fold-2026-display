package androidx.compose.ui.geometry;

/* JADX INFO: compiled from: Offset.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class OffsetKt {
    public static final long Offset(float f, float f2) {
        return Offset.m752constructorimpl((((long) Float.floatToRawIntBits(f2)) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
    }
}
