package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PathFillType.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PathFillType {
    public static final Companion Companion = new Companion(null);
    private static final int NonZero = m964constructorimpl(0);
    private static final int EvenOdd = m964constructorimpl(1);

    /* JADX INFO: compiled from: PathFillType.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getEvenOdd-Rg-k1Os, reason: not valid java name */
        public final int m967getEvenOddRgk1Os() {
            return PathFillType.EvenOdd;
        }

        /* JADX INFO: renamed from: getNonZero-Rg-k1Os, reason: not valid java name */
        public final int m968getNonZeroRgk1Os() {
            return PathFillType.NonZero;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m964constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m965equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m966hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }
}
