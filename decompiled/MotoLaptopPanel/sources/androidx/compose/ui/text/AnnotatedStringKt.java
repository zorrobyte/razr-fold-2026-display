package androidx.compose.ui.text;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: AnnotatedString.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AnnotatedStringKt {
    private static final AnnotatedString EmptyAnnotatedString = new AnnotatedString("", null, 2, 0 == true ? 1 : 0);

    /* JADX INFO: Access modifiers changed from: private */
    public static final List filterRanges(List list, int i, int i2) {
        if (!(i <= i2)) {
            InlineClassHelperKt.throwIllegalArgumentException("start (" + i + ") should be less than or equal to end (" + i2 + ')');
        }
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            AnnotatedString.Range range = (AnnotatedString.Range) list.get(i3);
            if (intersect(i, i2, range.getStart(), range.getEnd())) {
                arrayList.add(new AnnotatedString.Range(range.getItem(), Math.max(i, range.getStart()) - i, Math.min(i2, range.getEnd()) - i, range.getTag()));
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    private static final List getLocalAnnotations(AnnotatedString annotatedString, int i, int i2, Function1 function1) {
        List annotations$ui_text_release;
        if (i == i2 || (annotations$ui_text_release = annotatedString.getAnnotations$ui_text_release()) == null) {
            return null;
        }
        if (i != 0 || i2 < annotatedString.getText().length()) {
            ArrayList arrayList = new ArrayList(annotations$ui_text_release.size());
            int size = annotations$ui_text_release.size();
            for (int i3 = 0; i3 < size; i3++) {
                AnnotatedString.Range range = (AnnotatedString.Range) annotations$ui_text_release.get(i3);
                if ((function1 != null ? ((Boolean) function1.invoke(range.getItem())).booleanValue() : true) && intersect(i, i2, range.getStart(), range.getEnd())) {
                    arrayList.add(new AnnotatedString.Range((AnnotatedString.Annotation) range.getItem(), RangesKt.coerceIn(range.getStart(), i, i2) - i, RangesKt.coerceIn(range.getEnd(), i, i2) - i, range.getTag()));
                }
            }
            return arrayList;
        }
        if (function1 == null) {
            return annotations$ui_text_release;
        }
        ArrayList arrayList2 = new ArrayList(annotations$ui_text_release.size());
        int size2 = annotations$ui_text_release.size();
        for (int i4 = 0; i4 < size2; i4++) {
            Object obj = annotations$ui_text_release.get(i4);
            if (((Boolean) function1.invoke(((AnnotatedString.Range) obj).getItem())).booleanValue()) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public static final boolean intersect(int i, int i2, int i3, int i4) {
        return ((i < i4) & (i3 < i2)) | (((i == i2) | (i3 == i4)) & (i == i3));
    }

    public static final List normalizedParagraphStyles(AnnotatedString annotatedString, ParagraphStyle paragraphStyle) {
        List listEmptyList;
        List paragraphStylesOrNull$ui_text_release = annotatedString.getParagraphStylesOrNull$ui_text_release();
        if (paragraphStylesOrNull$ui_text_release == null || (listEmptyList = CollectionsKt.sortedWith(paragraphStylesOrNull$ui_text_release, new Comparator() { // from class: androidx.compose.ui.text.AnnotatedStringKt$normalizedParagraphStyles$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt.compareValues(Integer.valueOf(((AnnotatedString.Range) obj).getStart()), Integer.valueOf(((AnnotatedString.Range) obj2).getStart()));
            }
        })) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        ArrayDeque arrayDeque = new ArrayDeque();
        int size = listEmptyList.size();
        int end = 0;
        for (int i = 0; i < size; i++) {
            AnnotatedString.Range range = (AnnotatedString.Range) listEmptyList.get(i);
            AnnotatedString.Range rangeCopy$default = AnnotatedString.Range.copy$default(range, paragraphStyle.merge((ParagraphStyle) range.getItem()), 0, 0, null, 14, null);
            while (end < rangeCopy$default.getStart() && !arrayDeque.isEmpty()) {
                AnnotatedString.Range range2 = (AnnotatedString.Range) arrayDeque.last();
                if (rangeCopy$default.getStart() < range2.getEnd()) {
                    arrayList.add(new AnnotatedString.Range(range2.getItem(), end, rangeCopy$default.getStart()));
                    end = rangeCopy$default.getStart();
                } else {
                    arrayList.add(new AnnotatedString.Range(range2.getItem(), end, range2.getEnd()));
                    end = range2.getEnd();
                    while (!arrayDeque.isEmpty() && end == ((AnnotatedString.Range) arrayDeque.last()).getEnd()) {
                        arrayDeque.removeLast();
                    }
                }
            }
            if (end < rangeCopy$default.getStart()) {
                arrayList.add(new AnnotatedString.Range(paragraphStyle, end, rangeCopy$default.getStart()));
                end = rangeCopy$default.getStart();
            }
            AnnotatedString.Range range3 = (AnnotatedString.Range) arrayDeque.lastOrNull();
            if (range3 == null) {
                arrayDeque.add(new AnnotatedString.Range(rangeCopy$default.getItem(), rangeCopy$default.getStart(), rangeCopy$default.getEnd()));
            } else if (range3.getStart() == rangeCopy$default.getStart() && range3.getEnd() == rangeCopy$default.getEnd()) {
                arrayDeque.removeLast();
                arrayDeque.add(new AnnotatedString.Range(((ParagraphStyle) range3.getItem()).merge((ParagraphStyle) rangeCopy$default.getItem()), rangeCopy$default.getStart(), rangeCopy$default.getEnd()));
            } else if (range3.getStart() == range3.getEnd()) {
                arrayList.add(new AnnotatedString.Range(range3.getItem(), range3.getStart(), range3.getEnd()));
                arrayDeque.removeLast();
                arrayDeque.add(new AnnotatedString.Range(rangeCopy$default.getItem(), rangeCopy$default.getStart(), rangeCopy$default.getEnd()));
            } else {
                if (range3.getEnd() < rangeCopy$default.getEnd()) {
                    throw new IllegalArgumentException();
                }
                arrayDeque.add(new AnnotatedString.Range(((ParagraphStyle) range3.getItem()).merge((ParagraphStyle) rangeCopy$default.getItem()), rangeCopy$default.getStart(), rangeCopy$default.getEnd()));
            }
        }
        while (end <= annotatedString.getText().length() && !arrayDeque.isEmpty()) {
            AnnotatedString.Range range4 = (AnnotatedString.Range) arrayDeque.last();
            arrayList.add(new AnnotatedString.Range(range4.getItem(), end, range4.getEnd()));
            end = range4.getEnd();
            while (!arrayDeque.isEmpty() && end == ((AnnotatedString.Range) arrayDeque.last()).getEnd()) {
                arrayDeque.removeLast();
            }
        }
        if (end < annotatedString.getText().length()) {
            arrayList.add(new AnnotatedString.Range(paragraphStyle, end, annotatedString.getText().length()));
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new AnnotatedString.Range(paragraphStyle, 0, 0));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AnnotatedString substringWithoutParagraphStyles(AnnotatedString annotatedString, int i, int i2) {
        String strSubstring;
        if (i != i2) {
            strSubstring = annotatedString.getText().substring(i, i2);
            strSubstring.getClass();
        } else {
            strSubstring = "";
        }
        List localAnnotations = getLocalAnnotations(annotatedString, i, i2, new Function1() { // from class: androidx.compose.ui.text.AnnotatedStringKt.substringWithoutParagraphStyles.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(AnnotatedString.Annotation annotation) {
                return Boolean.valueOf(!(annotation instanceof ParagraphStyle));
            }
        });
        if (localAnnotations == null) {
            localAnnotations = CollectionsKt.emptyList();
        }
        return new AnnotatedString(strSubstring, localAnnotations);
    }
}
