package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Dp.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DpSize {
    public static final Companion Companion = new Companion(null);
    private static final long Zero = m1895constructorimpl(0);
    private static final long Unspecified = m1895constructorimpl(9205357640488583168L);

    /* JADX INFO: compiled from: Dp.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getZero-MYxV2XQ, reason: not valid java name */
        public final long m1900getZeroMYxV2XQ() {
            return DpSize.Zero;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1895constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: copy-DwJknco, reason: not valid java name */
    public static final long m1896copyDwJknco(long j, float f, float f2) {
        return m1895constructorimpl((((long) Float.floatToRawIntBits(f)) << 32) | (((long) Float.floatToRawIntBits(f2)) & 4294967295L));
    }

    /* JADX INFO: renamed from: copy-DwJknco$default, reason: not valid java name */
    public static /* synthetic */ long m1897copyDwJknco$default(long j, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = m1899getWidthD9Ej5fM(j);
        }
        if ((i & 2) != 0) {
            f2 = m1898getHeightD9Ej5fM(j);
        }
        return m1896copyDwJknco(j, f, f2);
    }

    /* JADX INFO: renamed from: getHeight-D9Ej5fM, reason: not valid java name */
    public static final float m1898getHeightD9Ej5fM(long j) {
        return Dp.m1877constructorimpl(Float.intBitsToFloat((int) (j & 4294967295L)));
    }

    /* JADX INFO: renamed from: getWidth-D9Ej5fM, reason: not valid java name */
    public static final float m1899getWidthD9Ej5fM(long j) {
        return Dp.m1877constructorimpl(Float.intBitsToFloat((int) (j >> 32)));
    }
}
