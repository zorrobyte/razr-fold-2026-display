package androidx.compose.ui.input.pointer.util;

import androidx.compose.ui.input.pointer.PointerId;
import java.util.Arrays;

/* JADX INFO: compiled from: PointerIdArray.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PointerIdArray {
    private long[] internalArray = new long[2];
    private int size;

    private final long[] resizeStorage(int i) {
        long[] jArr = this.internalArray;
        long[] jArrCopyOf = Arrays.copyOf(jArr, Math.max(i, jArr.length * 2));
        jArrCopyOf.getClass();
        this.internalArray = jArrCopyOf;
        return jArrCopyOf;
    }

    public final boolean add(long j) {
        if (contains(j)) {
            return false;
        }
        set(this.size, j);
        return true;
    }

    public final void clear() {
        this.size = 0;
    }

    public final boolean contains(long j) {
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.internalArray[i2] == j) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: get-_I2yYro, reason: not valid java name */
    public final long m525get_I2yYro(int i) {
        return PointerId.m489constructorimpl(this.internalArray[i]);
    }

    public final int getSize() {
        return this.size;
    }

    public final boolean isEmpty() {
        return this.size == 0;
    }

    public final boolean remove(long j) {
        int i = this.size;
        int i2 = 0;
        while (i2 < i) {
            if (j == this.internalArray[i2]) {
                int i3 = this.size - 1;
                while (i2 < i3) {
                    long[] jArr = this.internalArray;
                    int i4 = i2 + 1;
                    jArr[i2] = jArr[i4];
                    i2 = i4;
                }
                this.size--;
                return true;
            }
            i2++;
        }
        return false;
    }

    public final boolean removeAt(int i) {
        int i2 = this.size;
        if (i >= i2) {
            return false;
        }
        int i3 = i2 - 1;
        while (i < i3) {
            long[] jArr = this.internalArray;
            int i4 = i + 1;
            jArr[i] = jArr[i4];
            i = i4;
        }
        this.size--;
        return true;
    }

    public final void set(int i, long j) {
        long[] jArrResizeStorage = this.internalArray;
        if (i >= jArrResizeStorage.length) {
            jArrResizeStorage = resizeStorage(i + 1);
        }
        jArrResizeStorage[i] = j;
        if (i >= this.size) {
            this.size = i + 1;
        }
    }
}
