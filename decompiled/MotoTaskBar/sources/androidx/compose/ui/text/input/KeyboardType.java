package androidx.compose.ui.text.input;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: KeyboardType.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class KeyboardType {
    public static final Companion Companion = new Companion(null);
    private static final int Unspecified = m860constructorimpl(0);
    private static final int Text = m860constructorimpl(1);
    private static final int Ascii = m860constructorimpl(2);
    private static final int Number = m860constructorimpl(3);
    private static final int Phone = m860constructorimpl(4);
    private static final int Uri = m860constructorimpl(5);
    private static final int Email = m860constructorimpl(6);
    private static final int Password = m860constructorimpl(7);
    private static final int NumberPassword = m860constructorimpl(8);
    private static final int Decimal = m860constructorimpl(9);

    /* JADX INFO: compiled from: KeyboardType.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getAscii-PjHm6EE, reason: not valid java name */
        public final int m864getAsciiPjHm6EE() {
            return KeyboardType.Ascii;
        }

        /* JADX INFO: renamed from: getDecimal-PjHm6EE, reason: not valid java name */
        public final int m865getDecimalPjHm6EE() {
            return KeyboardType.Decimal;
        }

        /* JADX INFO: renamed from: getEmail-PjHm6EE, reason: not valid java name */
        public final int m866getEmailPjHm6EE() {
            return KeyboardType.Email;
        }

        /* JADX INFO: renamed from: getNumber-PjHm6EE, reason: not valid java name */
        public final int m867getNumberPjHm6EE() {
            return KeyboardType.Number;
        }

        /* JADX INFO: renamed from: getNumberPassword-PjHm6EE, reason: not valid java name */
        public final int m868getNumberPasswordPjHm6EE() {
            return KeyboardType.NumberPassword;
        }

        /* JADX INFO: renamed from: getPassword-PjHm6EE, reason: not valid java name */
        public final int m869getPasswordPjHm6EE() {
            return KeyboardType.Password;
        }

        /* JADX INFO: renamed from: getPhone-PjHm6EE, reason: not valid java name */
        public final int m870getPhonePjHm6EE() {
            return KeyboardType.Phone;
        }

        /* JADX INFO: renamed from: getText-PjHm6EE, reason: not valid java name */
        public final int m871getTextPjHm6EE() {
            return KeyboardType.Text;
        }

        /* JADX INFO: renamed from: getUri-PjHm6EE, reason: not valid java name */
        public final int m872getUriPjHm6EE() {
            return KeyboardType.Uri;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m860constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m861equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m862hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m863toStringimpl(int i) {
        return m861equalsimpl0(i, Unspecified) ? "Unspecified" : m861equalsimpl0(i, Text) ? "Text" : m861equalsimpl0(i, Ascii) ? "Ascii" : m861equalsimpl0(i, Number) ? "Number" : m861equalsimpl0(i, Phone) ? "Phone" : m861equalsimpl0(i, Uri) ? "Uri" : m861equalsimpl0(i, Email) ? "Email" : m861equalsimpl0(i, Password) ? "Password" : m861equalsimpl0(i, NumberPassword) ? "NumberPassword" : m861equalsimpl0(i, Decimal) ? "Decimal" : "Invalid";
    }
}
