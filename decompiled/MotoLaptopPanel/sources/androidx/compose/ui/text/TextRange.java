package androidx.compose.ui.text;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TextRange.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextRange {
    public static final Companion Companion = new Companion(null);
    private static final long Zero = TextRangeKt.TextRange(0);
    private final long packedValue;

    /* JADX INFO: compiled from: TextRange.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getZero-d9O1mEE, reason: not valid java name */
        public final long m1600getZerod9O1mEE() {
            return TextRange.Zero;
        }
    }

    private /* synthetic */ TextRange(long j) {
        this.packedValue = j;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextRange m1588boximpl(long j) {
        return new TextRange(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1589constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1590equalsimpl(long j, Object obj) {
        return (obj instanceof TextRange) && j == ((TextRange) obj).m1599unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1591equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: getCollapsed-impl, reason: not valid java name */
    public static final boolean m1592getCollapsedimpl(long j) {
        return m1596getStartimpl(j) == m1593getEndimpl(j);
    }

    /* JADX INFO: renamed from: getEnd-impl, reason: not valid java name */
    public static final int m1593getEndimpl(long j) {
        return (int) (j & 4294967295L);
    }

    /* JADX INFO: renamed from: getMax-impl, reason: not valid java name */
    public static final int m1594getMaximpl(long j) {
        return Math.max(m1596getStartimpl(j), m1593getEndimpl(j));
    }

    /* JADX INFO: renamed from: getMin-impl, reason: not valid java name */
    public static final int m1595getMinimpl(long j) {
        return Math.min(m1596getStartimpl(j), m1593getEndimpl(j));
    }

    /* JADX INFO: renamed from: getStart-impl, reason: not valid java name */
    public static final int m1596getStartimpl(long j) {
        return (int) (j >> 32);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1597hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1598toStringimpl(long j) {
        return "TextRange(" + m1596getStartimpl(j) + ", " + m1593getEndimpl(j) + ')';
    }

    public boolean equals(Object obj) {
        return m1590equalsimpl(this.packedValue, obj);
    }

    public int hashCode() {
        return m1597hashCodeimpl(this.packedValue);
    }

    public String toString() {
        return m1598toStringimpl(this.packedValue);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ long m1599unboximpl() {
        return this.packedValue;
    }
}
