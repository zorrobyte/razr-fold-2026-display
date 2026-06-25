package androidx.compose.ui.unit;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Dp.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DpSize {
    public static final Companion Companion = new Companion(null);
    private static final long Zero = m991constructorimpl(0);
    private static final long Unspecified = m991constructorimpl(9205357640488583168L);

    /* JADX INFO: compiled from: Dp.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getZero-MYxV2XQ, reason: not valid java name */
        public final long m994getZeroMYxV2XQ() {
            return DpSize.Zero;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m991constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: getHeight-D9Ej5fM, reason: not valid java name */
    public static final float m992getHeightD9Ej5fM(long j) {
        return Dp.m989constructorimpl(Float.intBitsToFloat((int) (j & 4294967295L)));
    }

    /* JADX INFO: renamed from: getWidth-D9Ej5fM, reason: not valid java name */
    public static final float m993getWidthD9Ej5fM(long j) {
        return Dp.m989constructorimpl(Float.intBitsToFloat((int) (j >> 32)));
    }
}
