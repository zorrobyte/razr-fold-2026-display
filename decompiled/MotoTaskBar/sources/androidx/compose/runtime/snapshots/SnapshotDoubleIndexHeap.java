package androidx.compose.runtime.snapshots;

import kotlin.collections.ArraysKt;

/* JADX INFO: compiled from: SnapshotDoubleIndexHeap.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SnapshotDoubleIndexHeap {
    private int firstFreeHandle;
    private int[] handles;
    private int size;
    private long[] values = SnapshotId_jvmKt.snapshotIdArrayWithCapacity(16);
    private int[] index = new int[16];

    public SnapshotDoubleIndexHeap() {
        int[] iArr = new int[16];
        int i = 0;
        while (i < 16) {
            int i2 = i + 1;
            iArr[i] = i2;
            i = i2;
        }
        this.handles = iArr;
    }

    private final int allocateHandle() {
        int length = this.handles.length;
        if (this.firstFreeHandle >= length) {
            int i = length * 2;
            int[] iArr = new int[i];
            int i2 = 0;
            while (i2 < i) {
                int i3 = i2 + 1;
                iArr[i2] = i3;
                i2 = i3;
            }
            ArraysKt.copyInto$default(this.handles, iArr, 0, 0, 0, 14, (Object) null);
            this.handles = iArr;
        }
        int i4 = this.firstFreeHandle;
        this.firstFreeHandle = this.handles[i4];
        return i4;
    }

    private final void ensure(int i) {
        int length = this.values.length;
        if (i <= length) {
            return;
        }
        int i2 = length * 2;
        long[] jArrSnapshotIdArrayWithCapacity = SnapshotId_jvmKt.snapshotIdArrayWithCapacity(i2);
        int[] iArr = new int[i2];
        ArraysKt.copyInto$default(this.values, jArrSnapshotIdArrayWithCapacity, 0, 0, 0, 12, (Object) null);
        ArraysKt.copyInto$default(this.index, iArr, 0, 0, 0, 14, (Object) null);
        this.values = jArrSnapshotIdArrayWithCapacity;
        this.index = iArr;
    }

    private final void freeHandle(int i) {
        this.handles[i] = this.firstFreeHandle;
        this.firstFreeHandle = i;
    }

    private final void shiftDown(int i) {
        long[] jArr = this.values;
        int i2 = this.size >> 1;
        while (i < i2) {
            int i3 = (i + 1) << 1;
            int i4 = i3 - 1;
            if (i3 < this.size) {
                long j = jArr[i3];
                if (j < jArr[i4]) {
                    if (j >= jArr[i]) {
                        return;
                    }
                    swap(i3, i);
                    i = i3;
                }
            }
            if (jArr[i4] >= jArr[i]) {
                return;
            }
            swap(i4, i);
            i = i4;
        }
    }

    private final void shiftUp(int i) {
        long[] jArr = this.values;
        long j = jArr[i];
        while (i > 0) {
            int i2 = ((i + 1) >> 1) - 1;
            if (jArr[i2] <= j) {
                return;
            }
            swap(i2, i);
            i = i2;
        }
    }

    private final void swap(int i, int i2) {
        long[] jArr = this.values;
        int[] iArr = this.index;
        int[] iArr2 = this.handles;
        long j = jArr[i];
        jArr[i] = jArr[i2];
        jArr[i2] = j;
        int i3 = iArr[i];
        int i4 = iArr[i2];
        iArr[i] = i4;
        iArr[i2] = i3;
        iArr2[i4] = i;
        iArr2[i3] = i2;
    }

    public final int add(long j) {
        ensure(this.size + 1);
        int i = this.size;
        this.size = i + 1;
        int iAllocateHandle = allocateHandle();
        this.values[i] = j;
        this.index[i] = iAllocateHandle;
        this.handles[iAllocateHandle] = i;
        shiftUp(i);
        return iAllocateHandle;
    }

    public final long lowestOrDefault(long j) {
        return this.size > 0 ? this.values[0] : j;
    }

    public final void remove(int i) {
        int i2 = this.handles[i];
        swap(i2, this.size - 1);
        this.size--;
        shiftUp(i2);
        shiftDown(i2);
        freeHandle(i);
    }
}
