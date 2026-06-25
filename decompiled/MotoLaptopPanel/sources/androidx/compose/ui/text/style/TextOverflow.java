package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TextOverflow.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TextOverflow {
    public static final Companion Companion = new Companion(null);
    private static final int Clip = m1840constructorimpl(1);
    private static final int Ellipsis = m1840constructorimpl(2);
    private static final int Visible = m1840constructorimpl(3);
    private static final int StartEllipsis = m1840constructorimpl(4);
    private static final int MiddleEllipsis = m1840constructorimpl(5);

    /* JADX INFO: compiled from: TextOverflow.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getClip-gIe3tQ8, reason: not valid java name */
        public final int m1844getClipgIe3tQ8() {
            return TextOverflow.Clip;
        }

        /* JADX INFO: renamed from: getEllipsis-gIe3tQ8, reason: not valid java name */
        public final int m1845getEllipsisgIe3tQ8() {
            return TextOverflow.Ellipsis;
        }

        /* JADX INFO: renamed from: getMiddleEllipsis-gIe3tQ8, reason: not valid java name */
        public final int m1846getMiddleEllipsisgIe3tQ8() {
            return TextOverflow.MiddleEllipsis;
        }

        /* JADX INFO: renamed from: getStartEllipsis-gIe3tQ8, reason: not valid java name */
        public final int m1847getStartEllipsisgIe3tQ8() {
            return TextOverflow.StartEllipsis;
        }

        /* JADX INFO: renamed from: getVisible-gIe3tQ8, reason: not valid java name */
        public final int m1848getVisiblegIe3tQ8() {
            return TextOverflow.Visible;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m1840constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1841equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1842hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1843toStringimpl(int i) {
        return m1841equalsimpl0(i, Clip) ? "Clip" : m1841equalsimpl0(i, Ellipsis) ? "Ellipsis" : m1841equalsimpl0(i, MiddleEllipsis) ? "MiddleEllipsis" : m1841equalsimpl0(i, Visible) ? "Visible" : m1841equalsimpl0(i, StartEllipsis) ? "StartEllipsis" : "Invalid";
    }
}
