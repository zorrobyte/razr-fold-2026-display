package androidx.compose.foundation.text.modifiers;

import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.foundation.text.modifiers.MinLinesConstrainer;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.MultiParagraphIntrinsics;
import androidx.compose.ui.text.Paragraph;
import androidx.compose.ui.text.ParagraphIntrinsics;
import androidx.compose.ui.text.ParagraphIntrinsicsKt;
import androidx.compose.ui.text.ParagraphKt;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.TextStyleKt;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ParagraphLayoutCache.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ParagraphLayoutCache {
    private int cachedIntrinsicHeight;
    private int cachedIntrinsicHeightInputWidth;
    private Density density;
    private boolean didOverflow;
    private FontFamily.Resolver fontFamilyResolver;
    private LayoutDirection intrinsicsLayoutDirection;
    private long lastDensity;
    private long layoutSize;
    private MinLinesConstrainer mMinLinesConstrainer;
    private int maxLines;
    private int minLines;
    private int overflow;
    private Paragraph paragraph;
    private ParagraphIntrinsics paragraphIntrinsics;
    private long prevConstraints;
    private boolean softWrap;
    private TextStyle style;
    private String text;

    private ParagraphLayoutCache(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3) {
        this.text = str;
        this.style = textStyle;
        this.fontFamilyResolver = resolver;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        this.lastDensity = InlineDensity.Companion.m204getUnspecifiedL26CHvs();
        long j = 0;
        this.layoutSize = IntSize.m1919constructorimpl((j & 4294967295L) | (j << 32));
        this.prevConstraints = Constraints.Companion.m1868fixedJhjzzOo(0, 0);
        this.cachedIntrinsicHeightInputWidth = -1;
        this.cachedIntrinsicHeight = -1;
    }

    public /* synthetic */ ParagraphLayoutCache(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, textStyle, resolver, i, z, i2, i3);
    }

    private final void markDirty() {
        this.paragraph = null;
        this.paragraphIntrinsics = null;
        this.intrinsicsLayoutDirection = null;
        this.cachedIntrinsicHeightInputWidth = -1;
        this.cachedIntrinsicHeight = -1;
        this.prevConstraints = Constraints.Companion.m1868fixedJhjzzOo(0, 0);
        long j = 0;
        this.layoutSize = IntSize.m1919constructorimpl((j & 4294967295L) | (j << 32));
        this.didOverflow = false;
    }

    /* JADX INFO: renamed from: newLayoutWillBeDifferent-K40F9xA, reason: not valid java name */
    private final boolean m217newLayoutWillBeDifferentK40F9xA(long j, LayoutDirection layoutDirection) {
        ParagraphIntrinsics paragraphIntrinsics;
        Paragraph paragraph = this.paragraph;
        if (paragraph == null || (paragraphIntrinsics = this.paragraphIntrinsics) == null || paragraphIntrinsics.getHasStaleResolvedFonts() || layoutDirection != this.intrinsicsLayoutDirection) {
            return true;
        }
        if (Constraints.m1854equalsimpl0(j, this.prevConstraints)) {
            return false;
        }
        return Constraints.m1860getMaxWidthimpl(j) != Constraints.m1860getMaxWidthimpl(this.prevConstraints) || ((float) Constraints.m1859getMaxHeightimpl(j)) < paragraph.getHeight() || paragraph.getDidExceedMaxLines();
    }

    private final ParagraphIntrinsics setLayoutDirection(LayoutDirection layoutDirection) {
        ParagraphIntrinsics ParagraphIntrinsics = this.paragraphIntrinsics;
        if (ParagraphIntrinsics == null || layoutDirection != this.intrinsicsLayoutDirection || ParagraphIntrinsics.getHasStaleResolvedFonts()) {
            this.intrinsicsLayoutDirection = layoutDirection;
            String str = this.text;
            TextStyle textStyleResolveDefaults = TextStyleKt.resolveDefaults(this.style, layoutDirection);
            List listEmptyList = CollectionsKt.emptyList();
            Density density = this.density;
            density.getClass();
            ParagraphIntrinsics = ParagraphIntrinsicsKt.ParagraphIntrinsics(str, textStyleResolveDefaults, listEmptyList, density, this.fontFamilyResolver, CollectionsKt.emptyList());
        }
        this.paragraphIntrinsics = ParagraphIntrinsics;
        return ParagraphIntrinsics;
    }

    /* JADX INFO: renamed from: useMinLinesConstrainer-euUD3Qg, reason: not valid java name */
    private final long m218useMinLinesConstrainereuUD3Qg(long j, LayoutDirection layoutDirection, TextStyle textStyle) {
        MinLinesConstrainer.Companion companion = MinLinesConstrainer.Companion;
        MinLinesConstrainer minLinesConstrainer = this.mMinLinesConstrainer;
        Density density = this.density;
        density.getClass();
        MinLinesConstrainer minLinesConstrainerFrom = companion.from(minLinesConstrainer, layoutDirection, textStyle, density, this.fontFamilyResolver);
        this.mMinLinesConstrainer = minLinesConstrainerFrom;
        return minLinesConstrainerFrom.m209coerceMinLinesOh53vG4$foundation_release(j, this.minLines);
    }

    /* JADX INFO: renamed from: useMinLinesConstrainer-euUD3Qg$default, reason: not valid java name */
    static /* synthetic */ long m219useMinLinesConstrainereuUD3Qg$default(ParagraphLayoutCache paragraphLayoutCache, long j, LayoutDirection layoutDirection, TextStyle textStyle, int i, Object obj) {
        if ((i & 4) != 0) {
            textStyle = paragraphLayoutCache.style;
        }
        return paragraphLayoutCache.m218useMinLinesConstrainereuUD3Qg(j, layoutDirection, textStyle);
    }

    public final Density getDensity$foundation_release() {
        return this.density;
    }

    public final boolean getDidOverflow$foundation_release() {
        return this.didOverflow;
    }

    /* JADX INFO: renamed from: getLayoutSize-YbymL2g$foundation_release, reason: not valid java name */
    public final long m220getLayoutSizeYbymL2g$foundation_release() {
        return this.layoutSize;
    }

    public final Unit getObserveFontChanges$foundation_release() {
        ParagraphIntrinsics paragraphIntrinsics = this.paragraphIntrinsics;
        if (paragraphIntrinsics != null) {
            paragraphIntrinsics.getHasStaleResolvedFonts();
        }
        return Unit.INSTANCE;
    }

    public final Paragraph getParagraph$foundation_release() {
        return this.paragraph;
    }

    /* JADX INFO: renamed from: layoutText-K40F9xA$foundation_release, reason: not valid java name */
    public final Paragraph m221layoutTextK40F9xA$foundation_release(long j, LayoutDirection layoutDirection) {
        ParagraphIntrinsics layoutDirection2 = setLayoutDirection(layoutDirection);
        return ParagraphKt.m1545ParagraphczeNHc(layoutDirection2, LayoutUtilsKt.m205finalConstraintstfFHcEY(j, this.softWrap, this.overflow, layoutDirection2.getMaxIntrinsicWidth()), LayoutUtilsKt.m206finalMaxLinesxdlQI24(this.softWrap, this.overflow, this.maxLines), this.overflow);
    }

    /* JADX INFO: renamed from: layoutWithConstraints-K40F9xA, reason: not valid java name */
    public final boolean m222layoutWithConstraintsK40F9xA(long j, LayoutDirection layoutDirection) {
        ParagraphLayoutCache paragraphLayoutCache;
        LayoutDirection layoutDirection2;
        boolean z = true;
        if (this.minLines > 1) {
            paragraphLayoutCache = this;
            layoutDirection2 = layoutDirection;
            j = m219useMinLinesConstrainereuUD3Qg$default(paragraphLayoutCache, j, layoutDirection2, null, 4, null);
        } else {
            paragraphLayoutCache = this;
            layoutDirection2 = layoutDirection;
        }
        boolean z2 = false;
        if (paragraphLayoutCache.m217newLayoutWillBeDifferentK40F9xA(j, layoutDirection2)) {
            Paragraph paragraphM221layoutTextK40F9xA$foundation_release = paragraphLayoutCache.m221layoutTextK40F9xA$foundation_release(j, layoutDirection2);
            paragraphLayoutCache.prevConstraints = j;
            paragraphLayoutCache.layoutSize = ConstraintsKt.m1869constrain4WqzIAM(j, IntSize.m1919constructorimpl((((long) TextDelegateKt.ceilToIntPx(paragraphM221layoutTextK40F9xA$foundation_release.getHeight())) & 4294967295L) | (((long) TextDelegateKt.ceilToIntPx(paragraphM221layoutTextK40F9xA$foundation_release.getWidth())) << 32)));
            if (!TextOverflow.m1841equalsimpl0(paragraphLayoutCache.overflow, TextOverflow.Companion.m1848getVisiblegIe3tQ8()) && (((int) (r10 >> 32)) < paragraphM221layoutTextK40F9xA$foundation_release.getWidth() || ((int) (r10 & 4294967295L)) < paragraphM221layoutTextK40F9xA$foundation_release.getHeight())) {
                z2 = true;
            }
            paragraphLayoutCache.didOverflow = z2;
            paragraphLayoutCache.paragraph = paragraphM221layoutTextK40F9xA$foundation_release;
            return true;
        }
        if (!Constraints.m1854equalsimpl0(j, paragraphLayoutCache.prevConstraints)) {
            Paragraph paragraph = paragraphLayoutCache.paragraph;
            paragraph.getClass();
            paragraphLayoutCache.layoutSize = ConstraintsKt.m1869constrain4WqzIAM(j, IntSize.m1919constructorimpl((((long) TextDelegateKt.ceilToIntPx(paragraph.getHeight())) & 4294967295L) | (((long) TextDelegateKt.ceilToIntPx(Math.min(paragraph.getMaxIntrinsicWidth(), paragraph.getWidth()))) << 32)));
            if (TextOverflow.m1841equalsimpl0(paragraphLayoutCache.overflow, TextOverflow.Companion.m1848getVisiblegIe3tQ8()) || (((int) (r5 >> 32)) >= paragraph.getWidth() && ((int) (4294967295L & r5)) >= paragraph.getHeight())) {
                z = false;
            }
            paragraphLayoutCache.didOverflow = z;
            paragraphLayoutCache.prevConstraints = j;
        }
        return false;
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

    public final TextLayoutResult slowCreateTextLayoutResultOrNull(TextStyle textStyle) {
        Density density;
        LayoutDirection layoutDirection = this.intrinsicsLayoutDirection;
        if (layoutDirection == null || (density = this.density) == null) {
            return null;
        }
        AnnotatedString annotatedString = new AnnotatedString(this.text, null, 2, null);
        if (this.paragraph == null || this.paragraphIntrinsics == null) {
            return null;
        }
        long jM1850constructorimpl = Constraints.m1850constructorimpl(this.prevConstraints & (-8589934589L));
        return new TextLayoutResult(new TextLayoutInput(annotatedString, textStyle, CollectionsKt.emptyList(), this.maxLines, this.softWrap, this.overflow, density, layoutDirection, this.fontFamilyResolver, jM1850constructorimpl, (DefaultConstructorMarker) null), new MultiParagraph(new MultiParagraphIntrinsics(annotatedString, textStyle, CollectionsKt.emptyList(), density, this.fontFamilyResolver), jM1850constructorimpl, this.maxLines, this.overflow, null), this.layoutSize, null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ParagraphLayoutCache(paragraph=");
        sb.append(this.paragraph != null ? "<paragraph>" : "null");
        sb.append(", lastDensity=");
        sb.append((Object) InlineDensity.m203toStringimpl(this.lastDensity));
        sb.append(')');
        return sb.toString();
    }

    /* JADX INFO: renamed from: update-L6sJoHM, reason: not valid java name */
    public final void m223updateL6sJoHM(String str, TextStyle textStyle, FontFamily.Resolver resolver, int i, boolean z, int i2, int i3) {
        this.text = str;
        this.style = textStyle;
        this.fontFamilyResolver = resolver;
        this.overflow = i;
        this.softWrap = z;
        this.maxLines = i2;
        this.minLines = i3;
        markDirty();
    }
}
