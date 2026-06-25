package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TextAlign.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextAlign {
    private final int value;
    public static final Companion Companion = new Companion(null);
    private static final int Left = m1799constructorimpl(1);
    private static final int Right = m1799constructorimpl(2);
    private static final int Center = m1799constructorimpl(3);
    private static final int Justify = m1799constructorimpl(4);
    private static final int Start = m1799constructorimpl(5);
    private static final int End = m1799constructorimpl(6);
    private static final int Unspecified = m1799constructorimpl(Integer.MIN_VALUE);

    /* JADX INFO: compiled from: TextAlign.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getCenter-e0LSkKk, reason: not valid java name */
        public final int m1805getCentere0LSkKk() {
            return TextAlign.Center;
        }

        /* JADX INFO: renamed from: getEnd-e0LSkKk, reason: not valid java name */
        public final int m1806getEnde0LSkKk() {
            return TextAlign.End;
        }

        /* JADX INFO: renamed from: getJustify-e0LSkKk, reason: not valid java name */
        public final int m1807getJustifye0LSkKk() {
            return TextAlign.Justify;
        }

        /* JADX INFO: renamed from: getLeft-e0LSkKk, reason: not valid java name */
        public final int m1808getLefte0LSkKk() {
            return TextAlign.Left;
        }

        /* JADX INFO: renamed from: getRight-e0LSkKk, reason: not valid java name */
        public final int m1809getRighte0LSkKk() {
            return TextAlign.Right;
        }

        /* JADX INFO: renamed from: getStart-e0LSkKk, reason: not valid java name */
        public final int m1810getStarte0LSkKk() {
            return TextAlign.Start;
        }

        /* JADX INFO: renamed from: getUnspecified-e0LSkKk, reason: not valid java name */
        public final int m1811getUnspecifiede0LSkKk() {
            return TextAlign.Unspecified;
        }
    }

    private /* synthetic */ TextAlign(int i) {
        this.value = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextAlign m1798boximpl(int i) {
        return new TextAlign(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m1799constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1800equalsimpl(int i, Object obj) {
        return (obj instanceof TextAlign) && i == ((TextAlign) obj).m1804unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1801equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1802hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1803toStringimpl(int i) {
        return m1801equalsimpl0(i, Left) ? "Left" : m1801equalsimpl0(i, Right) ? "Right" : m1801equalsimpl0(i, Center) ? "Center" : m1801equalsimpl0(i, Justify) ? "Justify" : m1801equalsimpl0(i, Start) ? "Start" : m1801equalsimpl0(i, End) ? "End" : m1801equalsimpl0(i, Unspecified) ? "Unspecified" : "Invalid";
    }

    public boolean equals(Object obj) {
        return m1800equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m1802hashCodeimpl(this.value);
    }

    public String toString() {
        return m1803toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m1804unboximpl() {
        return this.value;
    }
}
