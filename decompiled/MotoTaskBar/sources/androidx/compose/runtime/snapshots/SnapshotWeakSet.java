package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.internal.System_jvmKt;
import androidx.compose.runtime.internal.WeakReference;
import kotlin.collections.ArraysKt;

/* JADX INFO: compiled from: SnapshotWeakSet.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SnapshotWeakSet {
    private int size;
    private int[] hashes = new int[16];
    private WeakReference[] values = new WeakReference[16];

    private final int find(Object obj, int i) {
        int i2 = this.size - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.hashes[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else {
                if (i5 <= i) {
                    WeakReference weakReference = this.values[i4];
                    return obj == (weakReference != null ? weakReference.get() : null) ? i4 : findExactIndex(i4, obj, i);
                }
                i2 = i4 - 1;
            }
        }
        return -(i3 + 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001d, code lost:
    
        r4 = r4 + 1;
        r0 = r3.size;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0021, code lost:
    
        if (r4 >= r0) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0027, code lost:
    
        if (r3.hashes[r4] == r6) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x002c, code lost:
    
        return -(r4 + 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x002d, code lost:
    
        r2 = r3.values[r4];
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0031, code lost:
    
        if (r2 == null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0033, code lost:
    
        r2 = r2.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0038, code lost:
    
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0039, code lost:
    
        if (r2 != r5) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003b, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x003c, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0044, code lost:
    
        return -(r3.size + 1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int findExactIndex(int r4, java.lang.Object r5, int r6) {
        /*
            r3 = this;
            int r0 = r4 + (-1)
        L2:
            r1 = 0
            r2 = -1
            if (r2 >= r0) goto L1d
            int[] r2 = r3.hashes
            r2 = r2[r0]
            if (r2 == r6) goto Ld
            goto L1d
        Ld:
            androidx.compose.runtime.internal.WeakReference[] r2 = r3.values
            r2 = r2[r0]
            if (r2 == 0) goto L17
            java.lang.Object r1 = r2.get()
        L17:
            if (r1 != r5) goto L1a
            return r0
        L1a:
            int r0 = r0 + (-1)
            goto L2
        L1d:
            int r4 = r4 + 1
            int r0 = r3.size
        L21:
            if (r4 >= r0) goto L3f
            int[] r2 = r3.hashes
            r2 = r2[r4]
            if (r2 == r6) goto L2d
            int r4 = r4 + 1
            int r3 = -r4
            return r3
        L2d:
            androidx.compose.runtime.internal.WeakReference[] r2 = r3.values
            r2 = r2[r4]
            if (r2 == 0) goto L38
            java.lang.Object r2 = r2.get()
            goto L39
        L38:
            r2 = r1
        L39:
            if (r2 != r5) goto L3c
            return r4
        L3c:
            int r4 = r4 + 1
            goto L21
        L3f:
            int r3 = r3.size
            int r3 = r3 + 1
            int r3 = -r3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.snapshots.SnapshotWeakSet.findExactIndex(int, java.lang.Object, int):int");
    }

    public final boolean add(Object obj) {
        int iFind;
        int i = this.size;
        int iIdentityHashCode = System_jvmKt.identityHashCode(obj);
        if (i > 0) {
            iFind = find(obj, iIdentityHashCode);
            if (iFind >= 0) {
                return false;
            }
        } else {
            iFind = -1;
        }
        int i2 = -(iFind + 1);
        WeakReference[] weakReferenceArr = this.values;
        int length = weakReferenceArr.length;
        if (i == length) {
            int i3 = length * 2;
            WeakReference[] weakReferenceArr2 = new WeakReference[i3];
            int[] iArr = new int[i3];
            int i4 = i2 + 1;
            System.arraycopy(weakReferenceArr, i2, weakReferenceArr2, i4, i - i2);
            System.arraycopy(this.values, 0, weakReferenceArr2, 0, i2);
            ArraysKt.copyInto(this.hashes, iArr, i4, i2, i);
            ArraysKt.copyInto$default(this.hashes, iArr, 0, 0, i2, 6, (Object) null);
            this.values = weakReferenceArr2;
            this.hashes = iArr;
        } else {
            int i5 = i2 + 1;
            System.arraycopy(weakReferenceArr, i2, weakReferenceArr, i5, i - i2);
            int[] iArr2 = this.hashes;
            ArraysKt.copyInto(iArr2, iArr2, i5, i2, i);
        }
        this.values[i2] = new WeakReference(obj);
        this.hashes[i2] = iIdentityHashCode;
        this.size++;
        return true;
    }

    public final int[] getHashes$runtime_release() {
        return this.hashes;
    }

    public final int getSize$runtime_release() {
        return this.size;
    }

    public final WeakReference[] getValues$runtime_release() {
        return this.values;
    }

    public final void setSize$runtime_release(int i) {
        this.size = i;
    }
}
