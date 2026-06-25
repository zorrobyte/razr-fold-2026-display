package kotlin.text;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Indent.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class StringsKt__IndentKt extends StringsKt__AppendableKt {
    private static final Function1 getIndentFunction$StringsKt__IndentKt(final String str) {
        return str.length() == 0 ? new Function1() { // from class: kotlin.text.StringsKt__IndentKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StringsKt__IndentKt.getIndentFunction$lambda$8$StringsKt__IndentKt((String) obj);
            }
        } : new Function1() { // from class: kotlin.text.StringsKt__IndentKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return StringsKt__IndentKt.getIndentFunction$lambda$9$StringsKt__IndentKt(str, (String) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getIndentFunction$lambda$8$StringsKt__IndentKt(String str) {
        str.getClass();
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getIndentFunction$lambda$9$StringsKt__IndentKt(String str, String str2) {
        str2.getClass();
        return str + str2;
    }

    private static final int indentWidth$StringsKt__IndentKt(String str) {
        int length = str.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                i = -1;
                break;
            }
            if (!CharsKt__CharJVMKt.isWhitespace(str.charAt(i))) {
                break;
            }
            i++;
        }
        return i == -1 ? str.length() : i;
    }

    public static final String replaceIndent(String str, String str2) {
        String str3;
        str.getClass();
        str2.getClass();
        List listLines = StringsKt__StringsKt.lines(str);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listLines) {
            if (!StringsKt__StringsKt.isBlank((String) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            arrayList2.add(Integer.valueOf(indentWidth$StringsKt__IndentKt((String) obj2)));
        }
        Integer num = (Integer) CollectionsKt.minOrNull(arrayList2);
        int iIntValue = num != null ? num.intValue() : 0;
        int length = str.length() + (str2.length() * listLines.size());
        Function1 indentFunction$StringsKt__IndentKt = getIndentFunction$StringsKt__IndentKt(str2);
        int lastIndex = CollectionsKt.getLastIndex(listLines);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : listLines) {
            int i3 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str4 = (String) obj3;
            if ((i == 0 || i == lastIndex) && StringsKt__StringsKt.isBlank(str4)) {
                str4 = null;
            } else {
                String strDrop = StringsKt___StringsKt.drop(str4, iIntValue);
                if (strDrop != null && (str3 = (String) indentFunction$StringsKt__IndentKt.invoke(strDrop)) != null) {
                    str4 = str3;
                }
            }
            if (str4 != null) {
                arrayList3.add(str4);
            }
            i = i3;
        }
        return ((StringBuilder) CollectionsKt___CollectionsKt.joinTo(arrayList3, new StringBuilder(length), (124 & 2) != 0 ? ", " : "\n", (124 & 4) != 0 ? "" : null, (124 & 8) == 0 ? null : "", (124 & 16) != 0 ? -1 : 0, (124 & 32) != 0 ? "..." : null, (124 & 64) != 0 ? null : null)).toString();
    }

    public static final String replaceIndentByMargin(String str, String str2, String str3) {
        String str4;
        String str5;
        str.getClass();
        str2.getClass();
        str3.getClass();
        if (StringsKt__StringsKt.isBlank(str3)) {
            throw new IllegalArgumentException("marginPrefix must be non-blank string.");
        }
        List listLines = StringsKt__StringsKt.lines(str);
        int length = str.length() + (str2.length() * listLines.size());
        Function1 indentFunction$StringsKt__IndentKt = getIndentFunction$StringsKt__IndentKt(str2);
        int lastIndex = CollectionsKt.getLastIndex(listLines);
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : listLines) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str6 = (String) obj;
            String strSubstring = null;
            if ((i == 0 || i == lastIndex) && StringsKt__StringsKt.isBlank(str6)) {
                str4 = str3;
                str6 = null;
            } else {
                int length2 = str6.length();
                int i3 = 0;
                while (true) {
                    if (i3 >= length2) {
                        i3 = -1;
                        break;
                    }
                    if (!CharsKt__CharJVMKt.isWhitespace(str6.charAt(i3))) {
                        break;
                    }
                    i3++;
                }
                if (i3 == -1) {
                    str4 = str3;
                } else {
                    int i4 = i3;
                    str4 = str3;
                    if (StringsKt__StringsJVMKt.startsWith$default(str6, str4, i4, false, 4, null)) {
                        int length3 = str4.length() + i4;
                        str6.getClass();
                        strSubstring = str6.substring(length3);
                        strSubstring.getClass();
                    }
                }
                if (strSubstring != null && (str5 = (String) indentFunction$StringsKt__IndentKt.invoke(strSubstring)) != null) {
                    str6 = str5;
                }
            }
            if (str6 != null) {
                arrayList.add(str6);
            }
            i = i2;
            str3 = str4;
        }
        return ((StringBuilder) CollectionsKt___CollectionsKt.joinTo(arrayList, new StringBuilder(length), (124 & 2) != 0 ? ", " : "\n", (124 & 4) != 0 ? "" : null, (124 & 8) == 0 ? null : "", (124 & 16) != 0 ? -1 : 0, (124 & 32) != 0 ? "..." : null, (124 & 64) != 0 ? null : null)).toString();
    }

    public static String trimIndent(String str) {
        str.getClass();
        return replaceIndent(str, "");
    }

    public static final String trimMargin(String str, String str2) {
        str.getClass();
        str2.getClass();
        return replaceIndentByMargin(str, "", str2);
    }

    public static /* synthetic */ String trimMargin$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "|";
        }
        return trimMargin(str, str2);
    }
}
