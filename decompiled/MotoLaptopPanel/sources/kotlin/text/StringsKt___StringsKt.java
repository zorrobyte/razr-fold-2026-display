package kotlin.text;

import java.util.NoSuchElementException;
import kotlin.ranges.RangesKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: _Strings.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class StringsKt___StringsKt extends StringsKt___StringsJvmKt {
    public static final String drop(String str, int i) {
        str.getClass();
        if (i >= 0) {
            String strSubstring = str.substring(RangesKt.coerceAtMost(i, str.length()));
            strSubstring.getClass();
            return strSubstring;
        }
        throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
    }

    public static char last(CharSequence charSequence) {
        charSequence.getClass();
        if (charSequence.length() != 0) {
            return charSequence.charAt(StringsKt__StringsKt.getLastIndex(charSequence));
        }
        throw new NoSuchElementException("Char sequence is empty.");
    }

    public static String take(String str, int i) {
        str.getClass();
        if (i >= 0) {
            String strSubstring = str.substring(0, RangesKt.coerceAtMost(i, str.length()));
            strSubstring.getClass();
            return strSubstring;
        }
        throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
    }
}
