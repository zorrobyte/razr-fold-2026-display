package androidx.compose.ui.node;

/* JADX INFO: compiled from: HitTestResult.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class HitTestResultKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final long DistanceAndFlags(float f, boolean z, boolean z2) {
        return DistanceAndFlags.m564constructorimpl((((z ? 1L : 0L) | (z2 ? 2L : 0L)) & 4294967295L) | (((long) Float.floatToRawIntBits(f)) << 32));
    }

    static /* synthetic */ long DistanceAndFlags$default(float f, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = false;
        }
        return DistanceAndFlags(f, z, z2);
    }
}
