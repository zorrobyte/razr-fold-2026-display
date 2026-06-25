package androidx.compose.ui.text.font;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FontSynthesis.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FontSynthesis {
    private final int value;
    public static final Companion Companion = new Companion(null);
    private static final int None = m1636constructorimpl(0);
    private static final int Weight = m1636constructorimpl(1);
    private static final int Style = m1636constructorimpl(2);
    private static final int All = m1636constructorimpl(65535);

    /* JADX INFO: compiled from: FontSynthesis.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getAll-GVVA2EU, reason: not valid java name */
        public final int m1642getAllGVVA2EU() {
            return FontSynthesis.All;
        }
    }

    private /* synthetic */ FontSynthesis(int i) {
        this.value = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ FontSynthesis m1635boximpl(int i) {
        return new FontSynthesis(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m1636constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1637equalsimpl(int i, Object obj) {
        return (obj instanceof FontSynthesis) && i == ((FontSynthesis) obj).m1641unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1638equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1639hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1640toStringimpl(int i) {
        return m1638equalsimpl0(i, None) ? "None" : m1638equalsimpl0(i, Weight) ? "Weight" : m1638equalsimpl0(i, Style) ? "Style" : m1638equalsimpl0(i, All) ? "All" : "Invalid";
    }

    public boolean equals(Object obj) {
        return m1637equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m1639hashCodeimpl(this.value);
    }

    public String toString() {
        return m1640toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m1641unboximpl() {
        return this.value;
    }
}
