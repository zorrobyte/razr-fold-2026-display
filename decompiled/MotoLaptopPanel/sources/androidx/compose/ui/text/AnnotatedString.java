package androidx.compose.ui.text;

import androidx.collection.IntListKt;
import androidx.collection.MutableIntList;
import androidx.compose.runtime.saveable.Saver;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AnnotatedString.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AnnotatedString implements CharSequence {
    public static final Companion Companion = new Companion(null);
    private static final Saver Saver = SaversKt.getAnnotatedStringSaver();
    private final List annotations;
    private final List paragraphStylesOrNull;
    private final List spanStylesOrNull;
    private final String text;

    /* JADX INFO: compiled from: AnnotatedString.kt */
    public interface Annotation {
    }

    /* JADX INFO: compiled from: AnnotatedString.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: AnnotatedString.kt */
    public final class Range {
        private final int end;
        private final Object item;
        private final int start;
        private final String tag;

        public Range(Object obj, int i, int i2) {
            this(obj, i, i2, "");
        }

        public Range(Object obj, int i, int i2, String str) {
            this.item = obj;
            this.start = i;
            this.end = i2;
            this.tag = str;
            if (i <= i2) {
                return;
            }
            InlineClassHelperKt.throwIllegalArgumentException("Reversed range is not supported");
        }

        public static /* synthetic */ Range copy$default(Range range, Object obj, int i, int i2, String str, int i3, Object obj2) {
            if ((i3 & 1) != 0) {
                obj = range.item;
            }
            if ((i3 & 2) != 0) {
                i = range.start;
            }
            if ((i3 & 4) != 0) {
                i2 = range.end;
            }
            if ((i3 & 8) != 0) {
                str = range.tag;
            }
            return range.copy(obj, i, i2, str);
        }

        public final Object component1() {
            return this.item;
        }

        public final int component2() {
            return this.start;
        }

        public final int component3() {
            return this.end;
        }

        public final Range copy(Object obj, int i, int i2, String str) {
            return new Range(obj, i, i2, str);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Range)) {
                return false;
            }
            Range range = (Range) obj;
            return Intrinsics.areEqual(this.item, range.item) && this.start == range.start && this.end == range.end && Intrinsics.areEqual(this.tag, range.tag);
        }

        public final int getEnd() {
            return this.end;
        }

        public final Object getItem() {
            return this.item;
        }

        public final int getStart() {
            return this.start;
        }

        public final String getTag() {
            return this.tag;
        }

        public int hashCode() {
            Object obj = this.item;
            return ((((((obj == null ? 0 : obj.hashCode()) * 31) + Integer.hashCode(this.start)) * 31) + Integer.hashCode(this.end)) * 31) + this.tag.hashCode();
        }

        public String toString() {
            return "Range(item=" + this.item + ", start=" + this.start + ", end=" + this.end + ", tag=" + this.tag + ')';
        }
    }

    public AnnotatedString(String str, List list) {
        this(list.isEmpty() ? null : list, str);
    }

    public /* synthetic */ AnnotatedString(String str, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? CollectionsKt.emptyList() : list);
    }

    public AnnotatedString(List list, String str) {
        ArrayList arrayList;
        ArrayList arrayList2;
        this.annotations = list;
        this.text = str;
        if (list != null) {
            int size = list.size();
            arrayList = null;
            arrayList2 = null;
            for (int i = 0; i < size; i++) {
                Range range = (Range) list.get(i);
                if (range.getItem() instanceof SpanStyle) {
                    arrayList = arrayList == null ? new ArrayList() : arrayList;
                    arrayList.add(range);
                } else if (range.getItem() instanceof ParagraphStyle) {
                    arrayList2 = arrayList2 == null ? new ArrayList() : arrayList2;
                    arrayList2.add(range);
                }
            }
        } else {
            arrayList = null;
            arrayList2 = null;
        }
        this.spanStylesOrNull = arrayList;
        this.paragraphStylesOrNull = arrayList2;
        List listSortedWith = arrayList2 != null ? CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: androidx.compose.ui.text.AnnotatedString$special$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt.compareValues(Integer.valueOf(((AnnotatedString.Range) obj).getStart()), Integer.valueOf(((AnnotatedString.Range) obj2).getStart()));
            }
        }) : null;
        if (listSortedWith == null || listSortedWith.isEmpty()) {
            return;
        }
        MutableIntList mutableIntListMutableIntListOf = IntListKt.mutableIntListOf(((Range) CollectionsKt.first(listSortedWith)).getEnd());
        int size2 = listSortedWith.size();
        for (int i2 = 1; i2 < size2; i2++) {
            Range range2 = (Range) listSortedWith.get(i2);
            while (true) {
                if (mutableIntListMutableIntListOf._size == 0) {
                    break;
                }
                int iLast = mutableIntListMutableIntListOf.last();
                if (range2.getStart() >= iLast) {
                    mutableIntListMutableIntListOf.removeAt(mutableIntListMutableIntListOf._size - 1);
                } else if (!(range2.getEnd() <= iLast)) {
                    InlineClassHelperKt.throwIllegalArgumentException("Paragraph overlap not allowed, end " + range2.getEnd() + " should be less than or equal to " + iLast);
                }
            }
            mutableIntListMutableIntListOf.add(range2.getEnd());
        }
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ char charAt(int i) {
        return get(i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AnnotatedString)) {
            return false;
        }
        AnnotatedString annotatedString = (AnnotatedString) obj;
        return Intrinsics.areEqual(this.text, annotatedString.text) && Intrinsics.areEqual(this.annotations, annotatedString.annotations);
    }

    public char get(int i) {
        return this.text.charAt(i);
    }

    public final List getAnnotations$ui_text_release() {
        return this.annotations;
    }

    public int getLength() {
        return this.text.length();
    }

    public final List getLinkAnnotations(int i, int i2) {
        List listEmptyList;
        List list = this.annotations;
        if (list != null) {
            listEmptyList = new ArrayList(list.size());
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                Object obj = list.get(i3);
                Range range = (Range) obj;
                if ((range.getItem() instanceof LinkAnnotation) && AnnotatedStringKt.intersect(i, i2, range.getStart(), range.getEnd())) {
                    listEmptyList.add(obj);
                }
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        listEmptyList.getClass();
        return listEmptyList;
    }

    public final List getParagraphStylesOrNull$ui_text_release() {
        return this.paragraphStylesOrNull;
    }

    public final List getSpanStylesOrNull$ui_text_release() {
        return this.spanStylesOrNull;
    }

    public final String getText() {
        return this.text;
    }

    public final List getTtsAnnotations(int i, int i2) {
        List listEmptyList;
        List list = this.annotations;
        if (list != null) {
            listEmptyList = new ArrayList(list.size());
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                Object obj = list.get(i3);
                Range range = (Range) obj;
                if ((range.getItem() instanceof TtsAnnotation) && AnnotatedStringKt.intersect(i, i2, range.getStart(), range.getEnd())) {
                    listEmptyList.add(obj);
                }
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        listEmptyList.getClass();
        return listEmptyList;
    }

    public final List getUrlAnnotations(int i, int i2) {
        List listEmptyList;
        List list = this.annotations;
        if (list != null) {
            listEmptyList = new ArrayList(list.size());
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                Object obj = list.get(i3);
                Range range = (Range) obj;
                if ((range.getItem() instanceof UrlAnnotation) && AnnotatedStringKt.intersect(i, i2, range.getStart(), range.getEnd())) {
                    listEmptyList.add(obj);
                }
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        listEmptyList.getClass();
        return listEmptyList;
    }

    public final boolean hasEqualAnnotations(AnnotatedString annotatedString) {
        return Intrinsics.areEqual(this.annotations, annotatedString.annotations);
    }

    public final boolean hasLinkAnnotations(int i, int i2) {
        List list = this.annotations;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                Range range = (Range) list.get(i3);
                if ((range.getItem() instanceof LinkAnnotation) && AnnotatedStringKt.intersect(i, i2, range.getStart(), range.getEnd())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = this.text.hashCode() * 31;
        List list = this.annotations;
        return iHashCode + (list != null ? list.hashCode() : 0);
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ int length() {
        return getLength();
    }

    @Override // java.lang.CharSequence
    public AnnotatedString subSequence(int i, int i2) {
        if (!(i <= i2)) {
            InlineClassHelperKt.throwIllegalArgumentException("start (" + i + ") should be less or equal to end (" + i2 + ')');
        }
        if (i == 0 && i2 == this.text.length()) {
            return this;
        }
        String strSubstring = this.text.substring(i, i2);
        strSubstring.getClass();
        return new AnnotatedString(AnnotatedStringKt.filterRanges(this.annotations, i, i2), strSubstring);
    }

    /* JADX INFO: renamed from: subSequence-5zc-tL8, reason: not valid java name */
    public final AnnotatedString m1526subSequence5zctL8(long j) {
        return subSequence(TextRange.m1595getMinimpl(j), TextRange.m1594getMaximpl(j));
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return this.text;
    }
}
