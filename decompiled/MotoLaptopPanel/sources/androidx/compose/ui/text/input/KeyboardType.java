package androidx.compose.ui.text.input;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: KeyboardType.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class KeyboardType {
    public static final Companion Companion = new Companion(null);
    private static final int Unspecified = m1679constructorimpl(0);
    private static final int Text = m1679constructorimpl(1);
    private static final int Ascii = m1679constructorimpl(2);
    private static final int Number = m1679constructorimpl(3);
    private static final int Phone = m1679constructorimpl(4);
    private static final int Uri = m1679constructorimpl(5);
    private static final int Email = m1679constructorimpl(6);
    private static final int Password = m1679constructorimpl(7);
    private static final int NumberPassword = m1679constructorimpl(8);
    private static final int Decimal = m1679constructorimpl(9);

    /* JADX INFO: compiled from: KeyboardType.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getAscii-PjHm6EE, reason: not valid java name */
        public final int m1683getAsciiPjHm6EE() {
            return KeyboardType.Ascii;
        }

        /* JADX INFO: renamed from: getDecimal-PjHm6EE, reason: not valid java name */
        public final int m1684getDecimalPjHm6EE() {
            return KeyboardType.Decimal;
        }

        /* JADX INFO: renamed from: getEmail-PjHm6EE, reason: not valid java name */
        public final int m1685getEmailPjHm6EE() {
            return KeyboardType.Email;
        }

        /* JADX INFO: renamed from: getNumber-PjHm6EE, reason: not valid java name */
        public final int m1686getNumberPjHm6EE() {
            return KeyboardType.Number;
        }

        /* JADX INFO: renamed from: getNumberPassword-PjHm6EE, reason: not valid java name */
        public final int m1687getNumberPasswordPjHm6EE() {
            return KeyboardType.NumberPassword;
        }

        /* JADX INFO: renamed from: getPassword-PjHm6EE, reason: not valid java name */
        public final int m1688getPasswordPjHm6EE() {
            return KeyboardType.Password;
        }

        /* JADX INFO: renamed from: getPhone-PjHm6EE, reason: not valid java name */
        public final int m1689getPhonePjHm6EE() {
            return KeyboardType.Phone;
        }

        /* JADX INFO: renamed from: getText-PjHm6EE, reason: not valid java name */
        public final int m1690getTextPjHm6EE() {
            return KeyboardType.Text;
        }

        /* JADX INFO: renamed from: getUri-PjHm6EE, reason: not valid java name */
        public final int m1691getUriPjHm6EE() {
            return KeyboardType.Uri;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m1679constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1680equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1681hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1682toStringimpl(int i) {
        return m1680equalsimpl0(i, Unspecified) ? "Unspecified" : m1680equalsimpl0(i, Text) ? "Text" : m1680equalsimpl0(i, Ascii) ? "Ascii" : m1680equalsimpl0(i, Number) ? "Number" : m1680equalsimpl0(i, Phone) ? "Phone" : m1680equalsimpl0(i, Uri) ? "Uri" : m1680equalsimpl0(i, Email) ? "Email" : m1680equalsimpl0(i, Password) ? "Password" : m1680equalsimpl0(i, NumberPassword) ? "NumberPassword" : m1680equalsimpl0(i, Decimal) ? "Decimal" : "Invalid";
    }
}
