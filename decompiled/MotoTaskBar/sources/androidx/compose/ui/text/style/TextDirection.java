package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TextDirection.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextDirection {
    private final int value;
    public static final Companion Companion = new Companion(null);
    private static final int Ltr = m954constructorimpl(1);
    private static final int Rtl = m954constructorimpl(2);
    private static final int Content = m954constructorimpl(3);
    private static final int ContentOrLtr = m954constructorimpl(4);
    private static final int ContentOrRtl = m954constructorimpl(5);
    private static final int Unspecified = m954constructorimpl(Integer.MIN_VALUE);

    /* JADX INFO: compiled from: TextDirection.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private /* synthetic */ TextDirection(int i) {
        this.value = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextDirection m953boximpl(int i) {
        return new TextDirection(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m954constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m955equalsimpl(int i, Object obj) {
        return (obj instanceof TextDirection) && i == ((TextDirection) obj).m959unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m956equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m957hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m958toStringimpl(int i) {
        return m956equalsimpl0(i, Ltr) ? "Ltr" : m956equalsimpl0(i, Rtl) ? "Rtl" : m956equalsimpl0(i, Content) ? "Content" : m956equalsimpl0(i, ContentOrLtr) ? "ContentOrLtr" : m956equalsimpl0(i, ContentOrRtl) ? "ContentOrRtl" : m956equalsimpl0(i, Unspecified) ? "Unspecified" : "Invalid";
    }

    public boolean equals(Object obj) {
        return m955equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m957hashCodeimpl(this.value);
    }

    public String toString() {
        return m958toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m959unboximpl() {
        return this.value;
    }
}
