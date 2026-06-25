package androidx.compose.ui.node;

/* JADX INFO: compiled from: MyersDiff.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MyersDiffKt {
    private static final void applyDiff(IntStack intStack, DiffCallback diffCallback) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < intStack.getSize()) {
            int i4 = i + 2;
            int i5 = intStack.get(i) - intStack.get(i4);
            int i6 = intStack.get(i + 1) - intStack.get(i4);
            int i7 = intStack.get(i4);
            i += 3;
            while (i2 < i5) {
                diffCallback.remove(i3, i2);
                i2++;
            }
            while (i3 < i6) {
                diffCallback.insert(i3);
                i3++;
            }
            while (true) {
                int i8 = i7 - 1;
                if (i7 > 0) {
                    diffCallback.same(i2, i3);
                    i2++;
                    i3++;
                    i7 = i8;
                }
            }
        }
    }

    /* JADX INFO: renamed from: backward-4l5_RBY, reason: not valid java name */
    private static final boolean m1366backward4l5_RBY(int i, int i2, int i3, int i4, DiffCallback diffCallback, int[] iArr, int[] iArr2, int i5, int[] iArr3) {
        int iM1304getimpl;
        int i6;
        int i7;
        int i8 = (i2 - i) - (i4 - i3);
        boolean z = (i8 & 1) == 0;
        int i9 = -i5;
        for (int i10 = i9; i10 <= i5; i10 += 2) {
            if (i10 == i9 || (i10 != i5 && CenteredArray.m1304getimpl(iArr2, i10 + 1) < CenteredArray.m1304getimpl(iArr2, i10 - 1))) {
                iM1304getimpl = CenteredArray.m1304getimpl(iArr2, i10 + 1);
                i6 = iM1304getimpl;
            } else {
                iM1304getimpl = CenteredArray.m1304getimpl(iArr2, i10 - 1);
                i6 = iM1304getimpl - 1;
            }
            int i11 = i4 - ((i2 - i6) - i10);
            int i12 = ((i5 != 0 ? 1 : 0) & (i6 == iM1304getimpl ? 1 : 0)) + i11;
            while (i6 > i && i11 > i3) {
                if (!diffCallback.areItemsTheSame(i6 - 1, i11 - 1)) {
                    break;
                }
                i6--;
                i11--;
            }
            CenteredArray.m1306setimpl(iArr2, i10, i6);
            if (z && (i7 = i8 - i10) >= i9 && i7 <= i5) {
                if (CenteredArray.m1304getimpl(iArr, i7) >= i6) {
                    fillSnake(i6, i11, iM1304getimpl, i12, true, iArr3);
                    return true;
                }
            }
        }
        return false;
    }

    private static final IntStack calculateDiff(int i, int i2, DiffCallback diffCallback) {
        char c = 1;
        int i3 = ((i + i2) + 1) / 2;
        IntStack intStack = new IntStack(i3 * 3);
        IntStack intStack2 = new IntStack(i3 * 4);
        intStack2.pushRange(0, i, 0, i2);
        int i4 = (i3 * 2) + 1;
        int[] iArrM1303constructorimpl = CenteredArray.m1303constructorimpl(new int[i4]);
        int[] iArrM1303constructorimpl2 = CenteredArray.m1303constructorimpl(new int[i4]);
        int[] iArrM1414constructorimpl = Snake.m1414constructorimpl(new int[5]);
        while (intStack2.isNotEmpty()) {
            int iPop = intStack2.pop();
            int iPop2 = intStack2.pop();
            int iPop3 = intStack2.pop();
            int iPop4 = intStack2.pop();
            int[] iArr = iArrM1414constructorimpl;
            if (m1368midPointq5eDKzI(iPop4, iPop3, iPop2, iPop, diffCallback, iArrM1303constructorimpl, iArrM1303constructorimpl2, iArrM1414constructorimpl)) {
                char c2 = c;
                if (Math.min(iArr[2] - iArr[0], iArr[3] - iArr[c]) > 0) {
                    Snake.m1413addDiagonalToStackimpl(iArr, intStack);
                }
                intStack2.pushRange(iPop4, iArr[0], iPop2, iArr[c2]);
                intStack2.pushRange(iArr[2], iPop3, iArr[3], iPop);
                iArrM1414constructorimpl = iArr;
                c = c2;
            } else {
                iArrM1414constructorimpl = iArr;
            }
        }
        intStack.sortDiagonals();
        intStack.pushDiagonal(i, i2, 0);
        return intStack;
    }

    public static final void executeDiff(int i, int i2, DiffCallback diffCallback) {
        applyDiff(calculateDiff(i, i2, diffCallback), diffCallback);
    }

    public static final void fillSnake(int i, int i2, int i3, int i4, boolean z, int[] iArr) {
        if (iArr.length < 5) {
            return;
        }
        iArr[0] = i;
        iArr[1] = i2;
        iArr[2] = i3;
        iArr[3] = i4;
        iArr[4] = z ? 1 : 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: forward-4l5_RBY, reason: not valid java name */
    private static final boolean m1367forward4l5_RBY(int i, int i2, int i3, int i4, DiffCallback diffCallback, int[] iArr, int[] iArr2, int i5, int[] iArr3) {
        int iM1304getimpl;
        int i6;
        boolean z;
        int i7 = (i2 - i) - (i4 - i3);
        boolean z2 = true;
        boolean z3 = (Math.abs(i7) & 1) == 1;
        int i8 = -i5;
        int i9 = i8;
        while (i9 <= i5) {
            if (i9 == i8 || (i9 != i5 && CenteredArray.m1304getimpl(iArr, i9 + 1) > CenteredArray.m1304getimpl(iArr, i9 - 1))) {
                iM1304getimpl = CenteredArray.m1304getimpl(iArr, i9 + 1);
                i6 = iM1304getimpl;
            } else {
                iM1304getimpl = CenteredArray.m1304getimpl(iArr, i9 - 1);
                i6 = iM1304getimpl + 1;
            }
            int i10 = (i3 + (i6 - i)) - i9;
            int i11 = i10 - ((i5 != 0 ? z2 : 0) & (i6 == iM1304getimpl ? z2 : 0));
            while (i6 < i2 && i10 < i4) {
                if (!diffCallback.areItemsTheSame(i6, i10)) {
                    break;
                }
                i6++;
                i10++;
            }
            CenteredArray.m1306setimpl(iArr, i9, i6);
            if (z3) {
                int i12 = i7 - i9;
                z = z2;
                if (i12 >= i8 + 1 && i12 <= i5 - 1) {
                    if (CenteredArray.m1304getimpl(iArr2, i12) <= i6) {
                        fillSnake(iM1304getimpl, i11, i6, i10, false, iArr3);
                        return z;
                    }
                }
                i9 += 2;
                z2 = z;
            } else {
                z = z2;
            }
            i9 += 2;
            z2 = z;
        }
        return false;
    }

    /* JADX INFO: renamed from: midPoint-q5eDKzI, reason: not valid java name */
    private static final boolean m1368midPointq5eDKzI(int i, int i2, int i3, int i4, DiffCallback diffCallback, int[] iArr, int[] iArr2, int[] iArr3) {
        int i5 = i2 - i;
        int i6 = i4 - i3;
        if (i5 >= 1 && i6 >= 1) {
            int i7 = ((i5 + i6) + 1) / 2;
            int[] iArr4 = iArr;
            CenteredArray.m1306setimpl(iArr4, 1, i);
            int[] iArr5 = iArr2;
            CenteredArray.m1306setimpl(iArr5, 1, i2);
            int i8 = 0;
            while (i8 < i7) {
                if (m1367forward4l5_RBY(i, i2, i3, i4, diffCallback, iArr4, iArr5, i8, iArr3) || m1366backward4l5_RBY(i, i2, i3, i4, diffCallback, iArr, iArr2, i8, iArr3)) {
                    return true;
                }
                i8++;
                iArr4 = iArr;
                iArr5 = iArr2;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void swap(int[] iArr, int i, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
    }
}
