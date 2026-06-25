package androidx.compose.ui.node;

/* JADX INFO: compiled from: MyersDiff.kt */
/* JADX INFO: loaded from: classes.dex */
abstract class Snake {
    /* JADX INFO: renamed from: addDiagonalToStack-impl, reason: not valid java name */
    public static final void m1413addDiagonalToStackimpl(int[] iArr, IntStack intStack) {
        int iMin;
        int i = iArr[0];
        int i2 = iArr[1];
        if (m1415getHasAdditionOrRemovalimpl(iArr)) {
            iMin = Math.min(iArr[2] - iArr[0], iArr[3] - iArr[1]);
            i += ((iArr[4] != 0 ? 1 : 0) | (m1416isAdditionimpl(iArr) ? 1 : 0)) ^ 1;
            i2 += ((!m1416isAdditionimpl(iArr) ? 1 : 0) | (iArr[4] != 0 ? 1 : 0)) ^ 1;
        } else {
            iMin = iArr[2] - iArr[0];
        }
        intStack.pushDiagonal(i, i2, iMin);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int[] m1414constructorimpl(int[] iArr) {
        return iArr;
    }

    /* JADX INFO: renamed from: getHasAdditionOrRemoval-impl, reason: not valid java name */
    private static final boolean m1415getHasAdditionOrRemovalimpl(int[] iArr) {
        return iArr[3] - iArr[1] != iArr[2] - iArr[0];
    }

    /* JADX INFO: renamed from: isAddition-impl, reason: not valid java name */
    private static final boolean m1416isAdditionimpl(int[] iArr) {
        return iArr[3] - iArr[1] > iArr[2] - iArr[0];
    }
}
