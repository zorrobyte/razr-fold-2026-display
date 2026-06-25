package androidx.compose.ui.platform;

import java.text.BreakIterator;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AccessibilityIterators.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AccessibilityIterators$WordTextSegmentIterator extends AccessibilityIterators$AbstractTextSegmentIterator {
    private static AccessibilityIterators$WordTextSegmentIterator instance;
    private BreakIterator impl;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: AccessibilityIterators.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AccessibilityIterators$WordTextSegmentIterator getInstance(Locale locale) {
            if (AccessibilityIterators$WordTextSegmentIterator.instance == null) {
                AccessibilityIterators$WordTextSegmentIterator.instance = new AccessibilityIterators$WordTextSegmentIterator(locale, null);
            }
            AccessibilityIterators$WordTextSegmentIterator accessibilityIterators$WordTextSegmentIterator = AccessibilityIterators$WordTextSegmentIterator.instance;
            accessibilityIterators$WordTextSegmentIterator.getClass();
            return accessibilityIterators$WordTextSegmentIterator;
        }
    }

    private AccessibilityIterators$WordTextSegmentIterator(Locale locale) {
        onLocaleChanged(locale);
    }

    public /* synthetic */ AccessibilityIterators$WordTextSegmentIterator(Locale locale, DefaultConstructorMarker defaultConstructorMarker) {
        this(locale);
    }

    private final boolean isEndBoundary(int i) {
        if (i <= 0 || !isLetterOrDigit(i - 1)) {
            return false;
        }
        return i == getText().length() || !isLetterOrDigit(i);
    }

    private final boolean isLetterOrDigit(int i) {
        if (i < 0 || i >= getText().length()) {
            return false;
        }
        return Character.isLetterOrDigit(getText().codePointAt(i));
    }

    private final boolean isStartBoundary(int i) {
        if (isLetterOrDigit(i)) {
            return i == 0 || !isLetterOrDigit(i - 1);
        }
        return false;
    }

    private final void onLocaleChanged(Locale locale) {
        this.impl = BreakIterator.getWordInstance(locale);
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public int[] following(int i) {
        if (getText().length() <= 0 || i >= getText().length()) {
            return null;
        }
        if (i < 0) {
            i = 0;
        }
        while (!isLetterOrDigit(i) && !isStartBoundary(i)) {
            BreakIterator breakIterator = this.impl;
            if (breakIterator == null) {
                Intrinsics.throwUninitializedPropertyAccessException("impl");
                breakIterator = null;
            }
            i = breakIterator.following(i);
            if (i == -1) {
                return null;
            }
        }
        BreakIterator breakIterator2 = this.impl;
        if (breakIterator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("impl");
            breakIterator2 = null;
        }
        int iFollowing = breakIterator2.following(i);
        if (iFollowing == -1 || !isEndBoundary(iFollowing)) {
            return null;
        }
        return getRange(i, iFollowing);
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$AbstractTextSegmentIterator
    public void initialize(String str) {
        super.initialize(str);
        BreakIterator breakIterator = this.impl;
        if (breakIterator == null) {
            Intrinsics.throwUninitializedPropertyAccessException("impl");
            breakIterator = null;
        }
        breakIterator.setText(str);
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
        while (i > 0 && !isLetterOrDigit(i - 1) && !isEndBoundary(i)) {
            BreakIterator breakIterator = this.impl;
            if (breakIterator == null) {
                Intrinsics.throwUninitializedPropertyAccessException("impl");
                breakIterator = null;
            }
            i = breakIterator.preceding(i);
            if (i == -1) {
                return null;
            }
        }
        BreakIterator breakIterator2 = this.impl;
        if (breakIterator2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("impl");
            breakIterator2 = null;
        }
        int iPreceding = breakIterator2.preceding(i);
        if (iPreceding == -1 || !isStartBoundary(iPreceding)) {
            return null;
        }
        return getRange(iPreceding, i);
    }
}
