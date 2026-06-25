package androidx.compose.ui.platform;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: AccessibilityIterators.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AccessibilityIterators$ParagraphTextSegmentIterator extends AccessibilityIterators$AbstractTextSegmentIterator {
    public static final Companion Companion = new Companion(null);
    private static AccessibilityIterators$ParagraphTextSegmentIterator instance;

    /* JADX INFO: compiled from: AccessibilityIterators.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AccessibilityIterators$ParagraphTextSegmentIterator getInstance() {
            if (AccessibilityIterators$ParagraphTextSegmentIterator.instance == null) {
                AccessibilityIterators$ParagraphTextSegmentIterator.instance = new AccessibilityIterators$ParagraphTextSegmentIterator(null);
            }
            AccessibilityIterators$ParagraphTextSegmentIterator accessibilityIterators$ParagraphTextSegmentIterator = AccessibilityIterators$ParagraphTextSegmentIterator.instance;
            accessibilityIterators$ParagraphTextSegmentIterator.getClass();
            return accessibilityIterators$ParagraphTextSegmentIterator;
        }
    }

    private AccessibilityIterators$ParagraphTextSegmentIterator() {
    }

    public /* synthetic */ AccessibilityIterators$ParagraphTextSegmentIterator(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private final boolean isEndBoundary(int i) {
        if (i <= 0 || getText().charAt(i - 1) == '\n') {
            return false;
        }
        return i == getText().length() || getText().charAt(i) == '\n';
    }

    private final boolean isStartBoundary(int i) {
        if (getText().charAt(i) != '\n') {
            return i == 0 || getText().charAt(i - 1) == '\n';
        }
        return false;
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public int[] following(int i) {
        int length = getText().length();
        if (length <= 0 || i >= length) {
            return null;
        }
        if (i < 0) {
            i = 0;
        }
        while (i < length && getText().charAt(i) == '\n' && !isStartBoundary(i)) {
            i++;
        }
        if (i >= length) {
            return null;
        }
        int i2 = i + 1;
        while (i2 < length && !isEndBoundary(i2)) {
            i2++;
        }
        return getRange(i, i2);
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public int[] preceding(int i) {
        int length = getText().length();
        if (length <= 0 || i <= 0) {
            return null;
        }
        if (i > length) {
            i = length;
        }
        while (i > 0 && getText().charAt(i - 1) == '\n' && !isEndBoundary(i)) {
            i--;
        }
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        while (i2 > 0 && !isStartBoundary(i2)) {
            i2--;
        }
        return getRange(i2, i);
    }
}
