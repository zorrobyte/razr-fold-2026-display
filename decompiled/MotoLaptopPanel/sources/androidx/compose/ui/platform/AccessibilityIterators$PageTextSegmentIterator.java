package androidx.compose.ui.platform;

import android.graphics.Rect;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.style.ResolvedTextDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: AccessibilityIterators.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AccessibilityIterators$PageTextSegmentIterator extends AccessibilityIterators$AbstractTextSegmentIterator {
    private static AccessibilityIterators$PageTextSegmentIterator pageInstance;
    private TextLayoutResult layoutResult;
    private SemanticsNode node;
    private Rect tempRect;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final ResolvedTextDirection DirectionStart = ResolvedTextDirection.Rtl;
    private static final ResolvedTextDirection DirectionEnd = ResolvedTextDirection.Ltr;

    /* JADX INFO: compiled from: AccessibilityIterators.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AccessibilityIterators$PageTextSegmentIterator getInstance() {
            if (AccessibilityIterators$PageTextSegmentIterator.pageInstance == null) {
                AccessibilityIterators$PageTextSegmentIterator.pageInstance = new AccessibilityIterators$PageTextSegmentIterator(null);
            }
            AccessibilityIterators$PageTextSegmentIterator accessibilityIterators$PageTextSegmentIterator = AccessibilityIterators$PageTextSegmentIterator.pageInstance;
            accessibilityIterators$PageTextSegmentIterator.getClass();
            return accessibilityIterators$PageTextSegmentIterator;
        }
    }

    private AccessibilityIterators$PageTextSegmentIterator() {
        this.tempRect = new Rect();
    }

    public /* synthetic */ AccessibilityIterators$PageTextSegmentIterator(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private final int getLineEdgeIndex(int i, ResolvedTextDirection resolvedTextDirection) {
        TextLayoutResult textLayoutResult = this.layoutResult;
        TextLayoutResult textLayoutResult2 = null;
        if (textLayoutResult == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
            textLayoutResult = null;
        }
        int lineStart = textLayoutResult.getLineStart(i);
        TextLayoutResult textLayoutResult3 = this.layoutResult;
        if (textLayoutResult3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
            textLayoutResult3 = null;
        }
        if (resolvedTextDirection != textLayoutResult3.getParagraphDirection(lineStart)) {
            TextLayoutResult textLayoutResult4 = this.layoutResult;
            if (textLayoutResult4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
            } else {
                textLayoutResult2 = textLayoutResult4;
            }
            return textLayoutResult2.getLineStart(i);
        }
        TextLayoutResult textLayoutResult5 = this.layoutResult;
        if (textLayoutResult5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
            textLayoutResult5 = null;
        }
        return TextLayoutResult.getLineEnd$default(textLayoutResult5, i, false, 2, null) - 1;
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public int[] following(int i) {
        int lineCount;
        TextLayoutResult textLayoutResult = null;
        if (getText().length() <= 0 || i >= getText().length()) {
            return null;
        }
        try {
            SemanticsNode semanticsNode = this.node;
            if (semanticsNode == null) {
                Intrinsics.throwUninitializedPropertyAccessException("node");
                semanticsNode = null;
            }
            androidx.compose.ui.geometry.Rect boundsInRoot = semanticsNode.getBoundsInRoot();
            int iRound = Math.round(boundsInRoot.getBottom() - boundsInRoot.getTop());
            int iCoerceAtLeast = RangesKt.coerceAtLeast(0, i);
            TextLayoutResult textLayoutResult2 = this.layoutResult;
            if (textLayoutResult2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
                textLayoutResult2 = null;
            }
            int lineForOffset = textLayoutResult2.getLineForOffset(iCoerceAtLeast);
            TextLayoutResult textLayoutResult3 = this.layoutResult;
            if (textLayoutResult3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
                textLayoutResult3 = null;
            }
            float lineTop = textLayoutResult3.getLineTop(lineForOffset) + iRound;
            TextLayoutResult textLayoutResult4 = this.layoutResult;
            if (textLayoutResult4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
                textLayoutResult4 = null;
            }
            TextLayoutResult textLayoutResult5 = this.layoutResult;
            if (textLayoutResult5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
                textLayoutResult5 = null;
            }
            if (lineTop < textLayoutResult4.getLineTop(textLayoutResult5.getLineCount() - 1)) {
                TextLayoutResult textLayoutResult6 = this.layoutResult;
                if (textLayoutResult6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
                } else {
                    textLayoutResult = textLayoutResult6;
                }
                lineCount = textLayoutResult.getLineForVerticalPosition(lineTop);
            } else {
                TextLayoutResult textLayoutResult7 = this.layoutResult;
                if (textLayoutResult7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
                } else {
                    textLayoutResult = textLayoutResult7;
                }
                lineCount = textLayoutResult.getLineCount();
            }
            return getRange(iCoerceAtLeast, getLineEdgeIndex(lineCount - 1, DirectionEnd) + 1);
        } catch (IllegalStateException unused) {
            return null;
        }
    }

    public final void initialize(String str, TextLayoutResult textLayoutResult, SemanticsNode semanticsNode) {
        setText(str);
        this.layoutResult = textLayoutResult;
        this.node = semanticsNode;
    }

    @Override // androidx.compose.ui.platform.AccessibilityIterators$TextSegmentIterator
    public int[] preceding(int i) {
        int lineForVerticalPosition;
        TextLayoutResult textLayoutResult = null;
        if (getText().length() <= 0 || i <= 0) {
            return null;
        }
        try {
            SemanticsNode semanticsNode = this.node;
            if (semanticsNode == null) {
                Intrinsics.throwUninitializedPropertyAccessException("node");
                semanticsNode = null;
            }
            androidx.compose.ui.geometry.Rect boundsInRoot = semanticsNode.getBoundsInRoot();
            int iRound = Math.round(boundsInRoot.getBottom() - boundsInRoot.getTop());
            int iCoerceAtMost = RangesKt.coerceAtMost(getText().length(), i);
            TextLayoutResult textLayoutResult2 = this.layoutResult;
            if (textLayoutResult2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
                textLayoutResult2 = null;
            }
            int lineForOffset = textLayoutResult2.getLineForOffset(iCoerceAtMost);
            TextLayoutResult textLayoutResult3 = this.layoutResult;
            if (textLayoutResult3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
                textLayoutResult3 = null;
            }
            float lineTop = textLayoutResult3.getLineTop(lineForOffset) - iRound;
            if (lineTop > 0.0f) {
                TextLayoutResult textLayoutResult4 = this.layoutResult;
                if (textLayoutResult4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("layoutResult");
                } else {
                    textLayoutResult = textLayoutResult4;
                }
                lineForVerticalPosition = textLayoutResult.getLineForVerticalPosition(lineTop);
            } else {
                lineForVerticalPosition = 0;
            }
            if (iCoerceAtMost == getText().length() && lineForVerticalPosition < lineForOffset) {
                lineForVerticalPosition++;
            }
            return getRange(getLineEdgeIndex(lineForVerticalPosition, DirectionStart), iCoerceAtMost);
        } catch (IllegalStateException unused) {
            return null;
        }
    }
}
