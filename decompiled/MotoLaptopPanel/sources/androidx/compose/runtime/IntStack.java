package androidx.compose.runtime;

import java.util.Arrays;

/* JADX INFO: compiled from: Stack.kt */
/* JADX INFO: loaded from: classes.dex */
public final class IntStack {
    public int[] slots = new int[10];
    public int tos;

    private final int[] resize() {
        int[] iArr = this.slots;
        int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length * 2);
        iArrCopyOf.getClass();
        this.slots = iArrCopyOf;
        return iArrCopyOf;
    }

    public final void clear() {
        this.tos = 0;
    }

    public final int indexOf(int i) {
        int[] iArr = this.slots;
        int iMin = Math.min(iArr.length, this.tos);
        for (int i2 = 0; i2 < iMin; i2++) {
            if (iArr[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    public final int peek() {
        return this.slots[this.tos - 1];
    }

    public final int peek(int i) {
        return this.slots[i];
    }

    public final int peek2() {
        return this.slots[this.tos - 2];
    }

    public final int peekOr(int i) {
        int i2 = this.tos - 1;
        return i2 >= 0 ? this.slots[i2] : i;
    }

    public final int pop() {
        int[] iArr = this.slots;
        int i = this.tos - 1;
        this.tos = i;
        return iArr[i];
    }

    public final void push(int i) {
        int[] iArrResize = this.slots;
        if (this.tos >= iArrResize.length) {
            iArrResize = resize();
        }
        int i2 = this.tos;
        this.tos = i2 + 1;
        iArrResize[i2] = i;
    }
}
