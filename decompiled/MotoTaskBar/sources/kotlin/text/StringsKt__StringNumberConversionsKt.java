package kotlin.text;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: StringNumberConversions.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class StringsKt__StringNumberConversionsKt extends StringsKt__StringNumberConversionsJVMKt {
    public static Integer toIntOrNull(String str) {
        str.getClass();
        return toIntOrNull(str, 10);
    }

    public static final Integer toIntOrNull(String str, int i) {
        boolean z;
        int i2;
        int i3;
        str.getClass();
        CharsKt__CharJVMKt.checkRadix(i);
        int length = str.length();
        if (length == 0) {
            return null;
        }
        int i4 = 0;
        char cCharAt = str.charAt(0);
        int i5 = -2147483647;
        if (Intrinsics.compare(cCharAt, 48) < 0) {
            i2 = 1;
            if (length == 1) {
                return null;
            }
            if (cCharAt == '+') {
                z = false;
            } else {
                if (cCharAt != '-') {
                    return null;
                }
                i5 = Integer.MIN_VALUE;
                z = true;
            }
        } else {
            z = false;
            i2 = 0;
        }
        int i6 = -59652323;
        while (i2 < length) {
            int iDigitOf = CharsKt__CharJVMKt.digitOf(str.charAt(i2), i);
            if (iDigitOf < 0) {
                return null;
            }
            if ((i4 < i6 && (i6 != -59652323 || i4 < (i6 = i5 / i))) || (i3 = i4 * i) < i5 + iDigitOf) {
                return null;
            }
            i4 = i3 - iDigitOf;
            i2++;
        }
        return z ? Integer.valueOf(i4) : Integer.valueOf(-i4);
    }

    public static Long toLongOrNull(String str) {
        str.getClass();
        return toLongOrNull(str, 10);
    }

    public static final Long toLongOrNull(String str, int i) {
        boolean z;
        str.getClass();
        CharsKt__CharJVMKt.checkRadix(i);
        int length = str.length();
        Long l = null;
        if (length == 0) {
            return null;
        }
        int i2 = 0;
        char cCharAt = str.charAt(0);
        long j = -9223372036854775807L;
        if (Intrinsics.compare(cCharAt, 48) < 0) {
            z = true;
            if (length == 1) {
                return null;
            }
            if (cCharAt == '+') {
                z = false;
                i2 = 1;
            } else {
                if (cCharAt != '-') {
                    return null;
                }
                j = Long.MIN_VALUE;
                i2 = 1;
            }
        } else {
            z = false;
        }
        long j2 = 0;
        long j3 = -256204778801521550L;
        while (i2 < length) {
            int iDigitOf = CharsKt__CharJVMKt.digitOf(str.charAt(i2), i);
            if (iDigitOf < 0) {
                return l;
            }
            if (j2 < j3) {
                if (j3 != -256204778801521550L) {
                    return l;
                }
                j3 = j / ((long) i);
                if (j2 < j3) {
                    return l;
                }
            }
            Long l2 = l;
            int i3 = i2;
            long j4 = j2 * ((long) i);
            long j5 = iDigitOf;
            if (j4 < j + j5) {
                return l2;
            }
            j2 = j4 - j5;
            i2 = i3 + 1;
            l = l2;
        }
        return z ? Long.valueOf(j2) : Long.valueOf(-j2);
    }
}
