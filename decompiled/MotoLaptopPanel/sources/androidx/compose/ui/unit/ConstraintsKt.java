package androidx.compose.ui.unit;

/* JADX INFO: compiled from: Constraints.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ConstraintsKt {
    public static final long Constraints(int i, int i2, int i3, int i4) {
        if (!((i3 >= 0) & (i2 >= i) & (i4 >= i3) & (i >= 0))) {
            InlineClassHelperKt.throwIllegalArgumentException("maxWidth must be >= than minWidth,\nmaxHeight must be >= than minHeight,\nminWidth and minHeight must be >= 0");
        }
        return createConstraints(i, i2, i3, i4);
    }

    public static /* synthetic */ long Constraints$default(int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = 0;
        }
        if ((i5 & 2) != 0) {
            i2 = Integer.MAX_VALUE;
        }
        if ((i5 & 4) != 0) {
            i3 = 0;
        }
        if ((i5 & 8) != 0) {
            i4 = Integer.MAX_VALUE;
        }
        return Constraints(i, i2, i3, i4);
    }

    public static final int bitsNeedForSizeUnchecked(int i) {
        if (i < 8191) {
            return 13;
        }
        if (i < 32767) {
            return 15;
        }
        if (i < 65535) {
            return 16;
        }
        return i < 262143 ? 18 : 255;
    }

    /* JADX INFO: renamed from: constrain-4WqzIAM, reason: not valid java name */
    public static final long m1869constrain4WqzIAM(long j, long j2) {
        int i = (int) (j2 >> 32);
        int iM1862getMinWidthimpl = Constraints.m1862getMinWidthimpl(j);
        int iM1860getMaxWidthimpl = Constraints.m1860getMaxWidthimpl(j);
        if (i < iM1862getMinWidthimpl) {
            i = iM1862getMinWidthimpl;
        }
        if (i <= iM1860getMaxWidthimpl) {
            iM1860getMaxWidthimpl = i;
        }
        int i2 = (int) (j2 & 4294967295L);
        int iM1861getMinHeightimpl = Constraints.m1861getMinHeightimpl(j);
        int iM1859getMaxHeightimpl = Constraints.m1859getMaxHeightimpl(j);
        if (i2 < iM1861getMinHeightimpl) {
            i2 = iM1861getMinHeightimpl;
        }
        if (i2 <= iM1859getMaxHeightimpl) {
            iM1859getMaxHeightimpl = i2;
        }
        return IntSize.m1919constructorimpl((((long) iM1860getMaxWidthimpl) << 32) | (((long) iM1859getMaxHeightimpl) & 4294967295L));
    }

    /* JADX INFO: renamed from: constrain-N9IONVI, reason: not valid java name */
    public static final long m1870constrainN9IONVI(long j, long j2) {
        int iM1862getMinWidthimpl = Constraints.m1862getMinWidthimpl(j);
        int iM1860getMaxWidthimpl = Constraints.m1860getMaxWidthimpl(j);
        int iM1861getMinHeightimpl = Constraints.m1861getMinHeightimpl(j);
        int iM1859getMaxHeightimpl = Constraints.m1859getMaxHeightimpl(j);
        int iM1862getMinWidthimpl2 = Constraints.m1862getMinWidthimpl(j2);
        if (iM1862getMinWidthimpl2 < iM1862getMinWidthimpl) {
            iM1862getMinWidthimpl2 = iM1862getMinWidthimpl;
        }
        if (iM1862getMinWidthimpl2 > iM1860getMaxWidthimpl) {
            iM1862getMinWidthimpl2 = iM1860getMaxWidthimpl;
        }
        int iM1860getMaxWidthimpl2 = Constraints.m1860getMaxWidthimpl(j2);
        if (iM1860getMaxWidthimpl2 >= iM1862getMinWidthimpl) {
            iM1862getMinWidthimpl = iM1860getMaxWidthimpl2;
        }
        if (iM1862getMinWidthimpl <= iM1860getMaxWidthimpl) {
            iM1860getMaxWidthimpl = iM1862getMinWidthimpl;
        }
        int iM1861getMinHeightimpl2 = Constraints.m1861getMinHeightimpl(j2);
        if (iM1861getMinHeightimpl2 < iM1861getMinHeightimpl) {
            iM1861getMinHeightimpl2 = iM1861getMinHeightimpl;
        }
        if (iM1861getMinHeightimpl2 > iM1859getMaxHeightimpl) {
            iM1861getMinHeightimpl2 = iM1859getMaxHeightimpl;
        }
        int iM1859getMaxHeightimpl2 = Constraints.m1859getMaxHeightimpl(j2);
        if (iM1859getMaxHeightimpl2 >= iM1861getMinHeightimpl) {
            iM1861getMinHeightimpl = iM1859getMaxHeightimpl2;
        }
        if (iM1861getMinHeightimpl <= iM1859getMaxHeightimpl) {
            iM1859getMaxHeightimpl = iM1861getMinHeightimpl;
        }
        return Constraints(iM1862getMinWidthimpl2, iM1860getMaxWidthimpl, iM1861getMinHeightimpl2, iM1859getMaxHeightimpl);
    }

    /* JADX INFO: renamed from: constrainHeight-K40F9xA, reason: not valid java name */
    public static final int m1871constrainHeightK40F9xA(long j, int i) {
        int iM1861getMinHeightimpl = Constraints.m1861getMinHeightimpl(j);
        int iM1859getMaxHeightimpl = Constraints.m1859getMaxHeightimpl(j);
        if (i < iM1861getMinHeightimpl) {
            i = iM1861getMinHeightimpl;
        }
        return i > iM1859getMaxHeightimpl ? iM1859getMaxHeightimpl : i;
    }

    /* JADX INFO: renamed from: constrainWidth-K40F9xA, reason: not valid java name */
    public static final int m1872constrainWidthK40F9xA(long j, int i) {
        int iM1862getMinWidthimpl = Constraints.m1862getMinWidthimpl(j);
        int iM1860getMaxWidthimpl = Constraints.m1860getMaxWidthimpl(j);
        if (i < iM1862getMinWidthimpl) {
            i = iM1862getMinWidthimpl;
        }
        return i > iM1860getMaxWidthimpl ? iM1860getMaxWidthimpl : i;
    }

    public static final long createConstraints(int i, int i2, int i3, int i4) {
        int i5 = i4 == Integer.MAX_VALUE ? i3 : i4;
        int iBitsNeedForSizeUnchecked = bitsNeedForSizeUnchecked(i5);
        int i6 = i2 == Integer.MAX_VALUE ? i : i2;
        int iBitsNeedForSizeUnchecked2 = bitsNeedForSizeUnchecked(i6);
        if (iBitsNeedForSizeUnchecked + iBitsNeedForSizeUnchecked2 > 31) {
            throwInvalidConstraintException(i6, i5);
        }
        int i7 = i2 + 1;
        int i8 = i4 + 1;
        int i9 = iBitsNeedForSizeUnchecked2 - 13;
        return Constraints.m1850constructorimpl((((long) (i7 & (~(i7 >> 31)))) << 33) | ((long) ((i9 >> 1) + (i9 & 1))) | (((long) i) << 2) | (((long) i3) << (iBitsNeedForSizeUnchecked2 + 2)) | (((long) (i8 & (~(i8 >> 31)))) << (iBitsNeedForSizeUnchecked2 + 33)));
    }

    /* JADX INFO: renamed from: offset-NN6Ew-U, reason: not valid java name */
    public static final long m1873offsetNN6EwU(long j, int i, int i2) {
        int iM1862getMinWidthimpl = Constraints.m1862getMinWidthimpl(j) + i;
        if (iM1862getMinWidthimpl < 0) {
            iM1862getMinWidthimpl = 0;
        }
        int iM1860getMaxWidthimpl = Constraints.m1860getMaxWidthimpl(j);
        if (iM1860getMaxWidthimpl != Integer.MAX_VALUE && (iM1860getMaxWidthimpl = iM1860getMaxWidthimpl + i) < 0) {
            iM1860getMaxWidthimpl = 0;
        }
        int iM1861getMinHeightimpl = Constraints.m1861getMinHeightimpl(j) + i2;
        if (iM1861getMinHeightimpl < 0) {
            iM1861getMinHeightimpl = 0;
        }
        int iM1859getMaxHeightimpl = Constraints.m1859getMaxHeightimpl(j);
        return Constraints(iM1862getMinWidthimpl, iM1860getMaxWidthimpl, iM1861getMinHeightimpl, (iM1859getMaxHeightimpl == Integer.MAX_VALUE || (iM1859getMaxHeightimpl = iM1859getMaxHeightimpl + i2) >= 0) ? iM1859getMaxHeightimpl : 0);
    }

    /* JADX INFO: renamed from: offset-NN6Ew-U$default, reason: not valid java name */
    public static /* synthetic */ long m1874offsetNN6EwU$default(long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return m1873offsetNN6EwU(j, i, i2);
    }

    public static final void throwInvalidConstraintException(int i, int i2) {
        throw new IllegalArgumentException("Can't represent a width of " + i + " and height of " + i2 + " in Constraints");
    }

    public static final Void throwInvalidConstraintsSizeException(int i) {
        throw new IllegalArgumentException("Can't represent a size of " + i + " in Constraints");
    }
}
