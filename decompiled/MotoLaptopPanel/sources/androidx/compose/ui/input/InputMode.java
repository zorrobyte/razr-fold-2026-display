package androidx.compose.ui.input;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: InputModeManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class InputMode {
    private final int value;
    public static final Companion Companion = new Companion(null);
    private static final int Touch = m1154constructorimpl(1);
    private static final int Keyboard = m1154constructorimpl(2);

    /* JADX INFO: compiled from: InputModeManager.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getKeyboard-aOaMEAU, reason: not valid java name */
        public final int m1160getKeyboardaOaMEAU() {
            return InputMode.Keyboard;
        }

        /* JADX INFO: renamed from: getTouch-aOaMEAU, reason: not valid java name */
        public final int m1161getTouchaOaMEAU() {
            return InputMode.Touch;
        }
    }

    private /* synthetic */ InputMode(int i) {
        this.value = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ InputMode m1153boximpl(int i) {
        return new InputMode(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m1154constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m1155equalsimpl(int i, Object obj) {
        return (obj instanceof InputMode) && i == ((InputMode) obj).m1159unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1156equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1157hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1158toStringimpl(int i) {
        return m1156equalsimpl0(i, Touch) ? "Touch" : m1156equalsimpl0(i, Keyboard) ? "Keyboard" : "Error";
    }

    public boolean equals(Object obj) {
        return m1155equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m1157hashCodeimpl(this.value);
    }

    public String toString() {
        return m1158toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m1159unboximpl() {
        return this.value;
    }
}
