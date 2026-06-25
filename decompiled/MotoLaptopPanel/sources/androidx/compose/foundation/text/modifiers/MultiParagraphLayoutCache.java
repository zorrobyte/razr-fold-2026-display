package androidx.compose.foundation.text.modifiers;

import androidx.compose.foundation.text.TextAutoSize;
import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.foundation.text.modifiers.MinLinesConstrainer;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.MultiParagraphIntrinsics;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.TextStyleKt;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: MultiParagraphLayoutCache.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MultiParagraphLayoutCache {
    private TextAutoSizeLayoutScopeImpl _textAutoSizeLayoutScope;
    private int cachedIntrinsicHeight;
    private int cachedIntrinsicHeightInputWidth;
    private Density density;
    private FontFamily.Resolver fontFamilyResolver;
    private LayoutDirection intrinsicsLayoutDirection;
    private long lastDensity;
    private TextLayoutResult layoutCache;
    private MinLinesConstrainer mMinLinesConstrainer;
    private int maxLines;
    private int minLines;
    private int overflow;
    private MultiParagraphIntrinsics paragraphIntrinsics;
    private List placeholders;
    private boolean softWrap;
    private TextStyle style;
    private AnnotatedString text;

    /* JADX INFO: compiled from: MultiParagraphLayoutCache.kt */
    abstract class TextAutoSizeLayoutScopeImpl implements Density {
    }

    private MultiParagraphLayoutCache(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, List list, TextAutoSize textAutoSize) {
        this.text = annotatedString;
        this.fontFamilyResolver = resolver;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        this.placeholders = list;
        this.lastDensity = InlineDensity.Companion.m204getUnspecifiedL26CHvs();
        this.style = textStyle;
        this.cachedIntrinsicHeightInputWidth = -1;
        this.cachedIntrinsicHeight = -1;
    }

    public /* synthetic */ MultiParagraphLayoutCache(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, List list, TextAutoSize textAutoSize, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotatedString, textStyle, resolver, i, z, i2, i3, list, textAutoSize);
    }

    /* JADX INFO: renamed from: layoutText-R2G3SPE, reason: not valid java name */
    private final MultiParagraph m210layoutTextR2G3SPE(long j, LayoutDirection layoutDirection, int i) {
        MultiParagraphIntrinsics layoutDirection2 = setLayoutDirection(layoutDirection);
        return new MultiParagraph(layoutDirection2, LayoutUtilsKt.m205finalConstraintstfFHcEY(j, this.softWrap, i, layoutDirection2.getMaxIntrinsicWidth()), LayoutUtilsKt.m206finalMaxLinesxdlQI24(this.softWrap, i, this.maxLines), i, null);
    }

    /* JADX INFO: renamed from: layoutText-R2G3SPE$default, reason: not valid java name */
    static /* synthetic */ MultiParagraph m211layoutTextR2G3SPE$default(MultiParagraphLayoutCache multiParagraphLayoutCache, long j, LayoutDirection layoutDirection, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = multiParagraphLayoutCache.overflow;
        }
        return multiParagraphLayoutCache.m210layoutTextR2G3SPE(j, layoutDirection, i);
    }

    private final void markDirty() {
        this.paragraphIntrinsics = null;
        this.layoutCache = null;
        this.cachedIntrinsicHeight = -1;
        this.cachedIntrinsicHeightInputWidth = -1;
        this._textAutoSizeLayoutScope = null;
    }

    private final void markStyleAffectedDirty() {
        this.paragraphIntrinsics = null;
        this.layoutCache = null;
        this.cachedIntrinsicHeight = -1;
        this.cachedIntrinsicHeightInputWidth = -1;
    }

    /* JADX INFO: renamed from: newLayoutWillBeDifferent-VKLhPVY, reason: not valid java name */
    private final boolean m212newLayoutWillBeDifferentVKLhPVY(TextLayoutResult textLayoutResult, long j, LayoutDirection layoutDirection) {
        if (textLayoutResult == null || textLayoutResult.getMultiParagraph().getIntrinsics().getHasStaleResolvedFonts() || layoutDirection != textLayoutResult.getLayoutInput().getLayoutDirection()) {
            return true;
        }
        if (Constraints.m1854equalsimpl0(j, textLayoutResult.getLayoutInput().m1583getConstraintsmsEJaDk())) {
            return false;
        }
        return Constraints.m1860getMaxWidthimpl(j) != Constraints.m1860getMaxWidthimpl(textLayoutResult.getLayoutInput().m1583getConstraintsmsEJaDk()) || ((float) Constraints.m1859getMaxHeightimpl(j)) < textLayoutResult.getMultiParagraph().getHeight() || textLayoutResult.getMultiParagraph().getDidExceedMaxLines();
    }

    private final MultiParagraphIntrinsics setLayoutDirection(LayoutDirection layoutDirection) {
        MultiParagraphIntrinsics multiParagraphIntrinsics = this.paragraphIntrinsics;
        if (multiParagraphIntrinsics == null || layoutDirection != this.intrinsicsLayoutDirection || multiParagraphIntrinsics.getHasStaleResolvedFonts()) {
            this.intrinsicsLayoutDirection = layoutDirection;
            AnnotatedString annotatedString = this.text;
            TextStyle textStyleResolveDefaults = TextStyleKt.resolveDefaults(this.style, layoutDirection);
            Density density = this.density;
            density.getClass();
            FontFamily.Resolver resolver = this.fontFamilyResolver;
            List listEmptyList = this.placeholders;
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            multiParagraphIntrinsics = new MultiParagraphIntrinsics(annotatedString, textStyleResolveDefaults, listEmptyList, density, resolver);
        }
        this.paragraphIntrinsics = multiParagraphIntrinsics;
        return multiParagraphIntrinsics;
    }

    private final void setStyle(TextStyle textStyle) {
        boolean zHasSameLayoutAffectingAttributes = textStyle.hasSameLayoutAffectingAttributes(this.style);
        this.style = textStyle;
        if (zHasSameLayoutAffectingAttributes) {
            return;
        }
        markStyleAffectedDirty();
    }

    /* JADX INFO: renamed from: textLayoutResult-VKLhPVY, reason: not valid java name */
    private final TextLayoutResult m213textLayoutResultVKLhPVY(LayoutDirection layoutDirection, long j, MultiParagraph multiParagraph) {
        float fMin = Math.min(multiParagraph.getIntrinsics().getMaxIntrinsicWidth(), multiParagraph.getWidth());
        AnnotatedString annotatedString = this.text;
        TextStyle textStyle = this.style;
        List listEmptyList = this.placeholders;
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        int i = this.maxLines;
        boolean z = this.softWrap;
        int i2 = this.overflow;
        Density density = this.density;
        density.getClass();
        return new TextLayoutResult(new TextLayoutInput(annotatedString, textStyle, listEmptyList, i, z, i2, density, layoutDirection, this.fontFamilyResolver, j, (DefaultConstructorMarker) null), multiParagraph, ConstraintsKt.m1869constrain4WqzIAM(j, IntSize.m1919constructorimpl((((long) TextDelegateKt.ceilToIntPx(multiParagraph.getHeight())) & 4294967295L) | (((long) TextDelegateKt.ceilToIntPx(fMin)) << 32))), null);
    }

    /* JADX INFO: renamed from: useMinLinesConstrainer-Oh53vG4, reason: not valid java name */
    private final long m214useMinLinesConstrainerOh53vG4(long j, LayoutDirection layoutDirection) {
        MinLinesConstrainer.Companion companion = MinLinesConstrainer.Companion;
        MinLinesConstrainer minLinesConstrainer = this.mMinLinesConstrainer;
        TextStyle textStyle = this.style;
        Density density = this.density;
        density.getClass();
        MinLinesConstrainer minLinesConstrainerFrom = companion.from(minLinesConstrainer, layoutDirection, textStyle, density, this.fontFamilyResolver);
        this.mMinLinesConstrainer = minLinesConstrainerFrom;
        return minLinesConstrainerFrom.m209coerceMinLinesOh53vG4$foundation_release(j, this.minLines);
    }

    public final Density getDensity$foundation_release() {
        return this.density;
    }

    public final TextLayoutResult getLayoutOrNull() {
        return this.layoutCache;
    }

    public final TextLayoutResult getTextLayoutResult() {
        TextLayoutResult textLayoutResult = this.layoutCache;
        if (textLayoutResult != null) {
            return textLayoutResult;
        }
        throw new IllegalStateException("You must call layoutWithConstraints first");
    }

    /* JADX INFO: renamed from: layoutWithConstraints-K40F9xA, reason: not valid java name */
    public final boolean m215layoutWithConstraintsK40F9xA(long j, LayoutDirection layoutDirection) {
        if (this.minLines > 1) {
            j = m214useMinLinesConstrainerOh53vG4(j, layoutDirection);
        }
        long j2 = j;
        if (m212newLayoutWillBeDifferentVKLhPVY(this.layoutCache, j2, layoutDirection)) {
            this.layoutCache = m213textLayoutResultVKLhPVY(layoutDirection, j2, m211layoutTextR2G3SPE$default(this, j2, layoutDirection, 0, 4, null));
            return true;
        }
        TextLayoutResult textLayoutResult = this.layoutCache;
        textLayoutResult.getClass();
        if (Constraints.m1854equalsimpl0(j2, textLayoutResult.getLayoutInput().m1583getConstraintsmsEJaDk())) {
            return false;
        }
        TextLayoutResult textLayoutResult2 = this.layoutCache;
        textLayoutResult2.getClass();
        this.layoutCache = m213textLayoutResultVKLhPVY(layoutDirection, j2, textLayoutResult2.getMultiParagraph());
        return true;
    }

    public final void setDensity$foundation_release(Density density) {
        Density density2 = this.density;
        long jM199constructorimpl = density != null ? InlineDensity.m199constructorimpl(density) : InlineDensity.Companion.m204getUnspecifiedL26CHvs();
        if (density2 == null) {
            this.density = density;
            this.lastDensity = jM199constructorimpl;
        } else if (density == null || !InlineDensity.m200equalsimpl0(this.lastDensity, jM199constructorimpl)) {
            this.density = density;
            this.lastDensity = jM199constructorimpl;
            markDirty();
        }
    }

    /* JADX INFO: renamed from: update-J2qo7bo, reason: not valid java name */
    public final void m216updateJ2qo7bo(AnnotatedString annotatedString, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, List list, TextAutoSize textAutoSize) {
        this.text = annotatedString;
        setStyle(textStyle);
        this.fontFamilyResolver = resolver;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        this.placeholders = list;
        markDirty();
    }
}
