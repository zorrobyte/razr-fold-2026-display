package androidx.compose.ui.text;

import androidx.compose.ui.text.internal.InlineClassHelperKt;

/* JADX INFO: compiled from: TextRange.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TextRangeKt {
    public static final long TextRange(int i) {
        return TextRange(i, i);
    }

    public static final long TextRange(int i, int i2) {
        return TextRange.m808constructorimpl(packWithCheck(i, i2));
    }

    /* JADX INFO: renamed from: coerceIn-8ffj60Q, reason: not valid java name */
    public static final long m820coerceIn8ffj60Q(long j, int i, int i2) {
        int iM815getStartimpl = TextRange.m815getStartimpl(j);
        if (iM815getStartimpl < i) {
            iM815getStartimpl = i;
        }
        if (iM815getStartimpl > i2) {
            iM815getStartimpl = i2;
        }
        int iM812getEndimpl = TextRange.m812getEndimpl(j);
        if (iM812getEndimpl >= i) {
            i = iM812getEndimpl;
        }
        if (i <= i2) {
            i2 = i;
        }
        return (iM815getStartimpl == TextRange.m815getStartimpl(j) && i2 == TextRange.m812getEndimpl(j)) ? j : TextRange(iM815getStartimpl, i2);
    }

    private static final long packWithCheck(int i, int i2) {
        if (!(i >= 0 && i2 >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("start and end cannot be negative. [start: " + i + ", end: " + i2 + ']');
        }
        return (((long) i2) & 4294967295L) | (((long) i) << 32);
    }
}
