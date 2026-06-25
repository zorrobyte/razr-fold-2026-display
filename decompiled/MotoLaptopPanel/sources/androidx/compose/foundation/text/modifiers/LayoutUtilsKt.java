package androidx.compose.foundation.text.modifiers;

import androidx.compose.foundation.text.TextDelegateKt;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Constraints;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: LayoutUtils.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LayoutUtilsKt {
    /* JADX INFO: renamed from: finalConstraints-tfFHcEY, reason: not valid java name */
    public static final long m205finalConstraintstfFHcEY(long j, boolean z, int i, float f) {
        return Constraints.Companion.m1867fitPrioritizingWidthZbe2FdA(0, m207finalMaxWidthtfFHcEY(j, z, i, f), 0, Constraints.m1859getMaxHeightimpl(j));
    }

    /* JADX INFO: renamed from: finalMaxLines-xdlQI24, reason: not valid java name */
    public static final int m206finalMaxLinesxdlQI24(boolean z, int i, int i2) {
        if (z || !m208isEllipsisMW5ApA(i)) {
            return RangesKt.coerceAtLeast(i2, 1);
        }
        return 1;
    }

    /* JADX INFO: renamed from: finalMaxWidth-tfFHcEY, reason: not valid java name */
    public static final int m207finalMaxWidthtfFHcEY(long j, boolean z, int i, float f) {
        int iM1860getMaxWidthimpl = ((z || m208isEllipsisMW5ApA(i)) && Constraints.m1856getHasBoundedWidthimpl(j)) ? Constraints.m1860getMaxWidthimpl(j) : Integer.MAX_VALUE;
        return Constraints.m1862getMinWidthimpl(j) == iM1860getMaxWidthimpl ? iM1860getMaxWidthimpl : RangesKt.coerceIn(TextDelegateKt.ceilToIntPx(f), Constraints.m1862getMinWidthimpl(j), iM1860getMaxWidthimpl);
    }

    /* JADX INFO: renamed from: isEllipsis-MW5-ApA, reason: not valid java name */
    public static final boolean m208isEllipsisMW5ApA(int i) {
        TextOverflow.Companion companion = TextOverflow.Companion;
        return TextOverflow.m1841equalsimpl0(i, companion.m1845getEllipsisgIe3tQ8()) || TextOverflow.m1841equalsimpl0(i, companion.m1847getStartEllipsisgIe3tQ8()) || TextOverflow.m1841equalsimpl0(i, companion.m1846getMiddleEllipsisgIe3tQ8());
    }
}
