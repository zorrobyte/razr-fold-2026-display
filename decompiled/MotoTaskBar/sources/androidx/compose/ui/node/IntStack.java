package androidx.compose.ui.node;

import androidx.compose.ui.internal.InlineClassHelperKt;
import java.util.Arrays;

/* JADX INFO: compiled from: MyersDiff.kt */
/* JADX INFO: loaded from: classes.dex */
final class IntStack {
    private int lastIndex;
    private int[] stack;

    public IntStack(int i) {
        this.stack = new int[i];
    }

    private final boolean compareDiagonal(int i, int i2) {
        int[] iArr = this.stack;
        int i3 = iArr[i];
        int i4 = iArr[i2];
        return i3 < i4 || (i3 == i4 && iArr[i + 1] <= iArr[i2 + 1]);
    }

    private final int partition(int i, int i2, int i3) {
        int i4 = i - i3;
        while (i < i2) {
            if (compareDiagonal(i, i2)) {
                i4 += i3;
                swapDiagonal(i4, i);
            }
            i += i3;
        }
        int i5 = i4 + i3;
        swapDiagonal(i5, i2);
        return i5;
    }

    private final void quickSort(int i, int i2, int i3) {
        if (i < i2) {
            int iPartition = partition(i, i2, i3);
            quickSort(i, iPartition - i3, i3);
            quickSort(iPartition + i3, i2, i3);
        }
    }

    private final int[] resizeStack(int[] iArr) {
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length * 2);
        iArrCopyOf.getClass();
        this.stack = iArrCopyOf;
        return iArrCopyOf;
    }

    private final void swapDiagonal(int i, int i2) {
        int[] iArr = this.stack;
        MyersDiffKt.swap(iArr, i, i2);
        MyersDiffKt.swap(iArr, i + 1, i2 + 1);
        MyersDiffKt.swap(iArr, i + 2, i2 + 2);
    }

    public final int get(int i) {
        return this.stack[i];
    }

    public final int getSize() {
        return this.lastIndex;
    }

    public final boolean isNotEmpty() {
        return this.lastIndex != 0;
    }

    public final int pop() {
        int[] iArr = this.stack;
        int i = this.lastIndex - 1;
        this.lastIndex = i;
        return iArr[i];
    }

    public final void pushDiagonal(int i, int i2, int i3) {
        int i4 = this.lastIndex;
        int[] iArrResizeStack = this.stack;
        int i5 = i4 + 3;
        if (i5 >= iArrResizeStack.length) {
            iArrResizeStack = resizeStack(iArrResizeStack);
        }
        iArrResizeStack[i4] = i + i3;
        iArrResizeStack[i4 + 1] = i2 + i3;
        iArrResizeStack[i4 + 2] = i3;
        this.lastIndex = i5;
    }

    public final void pushRange(int i, int i2, int i3, int i4) {
        int i5 = this.lastIndex;
        int[] iArrResizeStack = this.stack;
        int i6 = i5 + 4;
        if (i6 >= iArrResizeStack.length) {
            iArrResizeStack = resizeStack(iArrResizeStack);
        }
        iArrResizeStack[i5] = i;
        iArrResizeStack[i5 + 1] = i2;
        iArrResizeStack[i5 + 2] = i3;
        iArrResizeStack[i5 + 3] = i4;
        this.lastIndex = i6;
    }

    public final void sortDiagonals() {
        int i = this.lastIndex;
        if (!(i % 3 == 0)) {
            InlineClassHelperKt.throwIllegalStateException("Array size not a multiple of 3");
        }
        if (i > 3) {
            quickSort(0, i - 3, 3);
        }
    }
}
