package androidx.compose.ui.graphics;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FilterQuality.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FilterQuality {
    public static final Companion Companion = new Companion(null);
    private static final int None = m909constructorimpl(0);
    private static final int Low = m909constructorimpl(1);
    private static final int Medium = m909constructorimpl(2);
    private static final int High = m909constructorimpl(3);

    /* JADX INFO: compiled from: FilterQuality.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getLow-f-v9h1I, reason: not valid java name */
        public final int m913getLowfv9h1I() {
            return FilterQuality.Low;
        }

        /* JADX INFO: renamed from: getNone-f-v9h1I, reason: not valid java name */
        public final int m914getNonefv9h1I() {
            return FilterQuality.None;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m909constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m910equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m911hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m912toStringimpl(int i) {
        return m910equalsimpl0(i, None) ? "None" : m910equalsimpl0(i, Low) ? "Low" : m910equalsimpl0(i, Medium) ? "Medium" : m910equalsimpl0(i, High) ? "High" : "Unknown";
    }
}
