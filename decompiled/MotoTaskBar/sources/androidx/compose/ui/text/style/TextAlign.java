package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TextAlign.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextAlign {
    private final int value;
    public static final Companion Companion = new Companion(null);
    private static final int Left = m947constructorimpl(1);
    private static final int Right = m947constructorimpl(2);
    private static final int Center = m947constructorimpl(3);
    private static final int Justify = m947constructorimpl(4);
    private static final int Start = m947constructorimpl(5);
    private static final int End = m947constructorimpl(6);
    private static final int Unspecified = m947constructorimpl(Integer.MIN_VALUE);

    /* JADX INFO: compiled from: TextAlign.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private /* synthetic */ TextAlign(int i) {
        this.value = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextAlign m946boximpl(int i) {
        return new TextAlign(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m947constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m948equalsimpl(int i, Object obj) {
        return (obj instanceof TextAlign) && i == ((TextAlign) obj).m952unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m949equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m950hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m951toStringimpl(int i) {
        return m949equalsimpl0(i, Left) ? "Left" : m949equalsimpl0(i, Right) ? "Right" : m949equalsimpl0(i, Center) ? "Center" : m949equalsimpl0(i, Justify) ? "Justify" : m949equalsimpl0(i, Start) ? "Start" : m949equalsimpl0(i, End) ? "End" : m949equalsimpl0(i, Unspecified) ? "Unspecified" : "Invalid";
    }

    public boolean equals(Object obj) {
        return m948equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m950hashCodeimpl(this.value);
    }

    public String toString() {
        return m951toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m952unboximpl() {
        return this.value;
    }
}
