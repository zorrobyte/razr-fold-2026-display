package androidx.compose.ui.unit;

/* JADX INFO: compiled from: TextUnit.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TextUnitKt {
    /* JADX INFO: renamed from: TextUnit-anM5pPY, reason: not valid java name */
    public static final long m1028TextUnitanM5pPY(float f, long j) {
        return pack(j, f);
    }

    public static final long getSp(int i) {
        return pack(4294967296L, i);
    }

    public static final long pack(long j, float f) {
        return TextUnit.m1018constructorimpl(j | (((long) Float.floatToRawIntBits(f)) & 4294967295L));
    }
}
