package androidx.compose.runtime.snapshots;

import kotlin.collections.ArraysKt;

/* JADX INFO: compiled from: SnapshotId.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SnapshotId_jvmKt {
    public static final int binarySearch(long[] jArr, long j) {
        int length = jArr.length - 1;
        int i = 0;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            long j2 = jArr[i2];
            if (j > j2) {
                i = i2 + 1;
            } else {
                if (j >= j2) {
                    return i2;
                }
                length = i2 - 1;
            }
        }
        return -(i + 1);
    }

    public static final long[] snapshotIdArrayWithCapacity(int i) {
        return new long[i];
    }

    public static final long toSnapshotId(int i) {
        return i;
    }

    public static final long[] withIdInsertedAt(long[] jArr, int i, long j) {
        int length = jArr.length;
        long[] jArr2 = new long[length + 1];
        ArraysKt.copyInto(jArr, jArr2, 0, 0, i);
        ArraysKt.copyInto(jArr, jArr2, i + 1, i, length);
        jArr2[i] = j;
        return jArr2;
    }

    public static final long[] withIdRemovedAt(long[] jArr, int i) {
        int length = jArr.length;
        int i2 = length - 1;
        if (i2 == 0) {
            return null;
        }
        long[] jArr2 = new long[i2];
        if (i > 0) {
            ArraysKt.copyInto(jArr, jArr2, 0, 0, i);
        }
        if (i < i2) {
            ArraysKt.copyInto(jArr, jArr2, i, i + 1, length);
        }
        return jArr2;
    }
}
