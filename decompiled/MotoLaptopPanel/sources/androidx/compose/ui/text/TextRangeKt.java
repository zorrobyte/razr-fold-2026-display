package androidx.compose.ui.text;

import androidx.compose.ui.text.internal.InlineClassHelperKt;

/* JADX INFO: compiled from: TextRange.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TextRangeKt {
    public static final long TextRange(int i) {
        return TextRange(i, i);
    }

    public static final long TextRange(int i, int i2) {
        return TextRange.m1589constructorimpl(packWithCheck(i, i2));
    }

    /* JADX INFO: renamed from: coerceIn-8ffj60Q, reason: not valid java name */
    public static final long m1601coerceIn8ffj60Q(long j, int i, int i2) {
        int iM1596getStartimpl = TextRange.m1596getStartimpl(j);
        if (iM1596getStartimpl < i) {
            iM1596getStartimpl = i;
        }
        if (iM1596getStartimpl > i2) {
            iM1596getStartimpl = i2;
        }
        int iM1593getEndimpl = TextRange.m1593getEndimpl(j);
        if (iM1593getEndimpl >= i) {
            i = iM1593getEndimpl;
        }
        if (i <= i2) {
            i2 = i;
        }
        return (iM1596getStartimpl == TextRange.m1596getStartimpl(j) && i2 == TextRange.m1593getEndimpl(j)) ? j : TextRange(iM1596getStartimpl, i2);
    }

    private static final long packWithCheck(int i, int i2) {
        if (!(i >= 0 && i2 >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("start and end cannot be negative. [start: " + i + ", end: " + i2 + ']');
        }
        return (((long) i2) & 4294967295L) | (((long) i) << 32);
    }
}
