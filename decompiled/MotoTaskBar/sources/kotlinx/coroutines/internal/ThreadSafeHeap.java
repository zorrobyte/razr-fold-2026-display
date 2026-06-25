package kotlinx.coroutines.internal;

import java.util.Arrays;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;

/* JADX INFO: compiled from: ThreadSafeHeap.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class ThreadSafeHeap {
    private final AtomicInt _size = AtomicFU.atomic(0);
    private ThreadSafeHeapNode[] a;

    private final ThreadSafeHeapNode[] realloc() {
        ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
        if (threadSafeHeapNodeArr == null) {
            ThreadSafeHeapNode[] threadSafeHeapNodeArr2 = new ThreadSafeHeapNode[4];
            this.a = threadSafeHeapNodeArr2;
            return threadSafeHeapNodeArr2;
        }
        if (getSize() < threadSafeHeapNodeArr.length) {
            return threadSafeHeapNodeArr;
        }
        Object[] objArrCopyOf = Arrays.copyOf(threadSafeHeapNodeArr, getSize() * 2);
        objArrCopyOf.getClass();
        ThreadSafeHeapNode[] threadSafeHeapNodeArr3 = (ThreadSafeHeapNode[]) objArrCopyOf;
        this.a = threadSafeHeapNodeArr3;
        return threadSafeHeapNodeArr3;
    }

    private final void setSize(int i) {
        this._size.setValue(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void siftDownFrom(int r6) {
        /*
            r5 = this;
        L0:
            int r0 = r6 * 2
            int r1 = r0 + 1
            int r2 = r5.getSize()
            if (r1 < r2) goto Lb
            goto L3e
        Lb:
            kotlinx.coroutines.internal.ThreadSafeHeapNode[] r2 = r5.a
            r2.getClass()
            int r0 = r0 + 2
            int r3 = r5.getSize()
            if (r0 >= r3) goto L2b
            r3 = r2[r0]
            r3.getClass()
            java.lang.Comparable r3 = (java.lang.Comparable) r3
            r4 = r2[r1]
            r4.getClass()
            int r3 = r3.compareTo(r4)
            if (r3 >= 0) goto L2b
            goto L2c
        L2b:
            r0 = r1
        L2c:
            r1 = r2[r6]
            r1.getClass()
            java.lang.Comparable r1 = (java.lang.Comparable) r1
            r2 = r2[r0]
            r2.getClass()
            int r1 = r1.compareTo(r2)
            if (r1 > 0) goto L3f
        L3e:
            return
        L3f:
            r5.swap(r6, r0)
            r6 = r0
            goto L0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ThreadSafeHeap.siftDownFrom(int):void");
    }

    private final void siftUpFrom(int i) {
        while (i > 0) {
            ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
            threadSafeHeapNodeArr.getClass();
            int i2 = (i - 1) / 2;
            ThreadSafeHeapNode threadSafeHeapNode = threadSafeHeapNodeArr[i2];
            threadSafeHeapNode.getClass();
            ThreadSafeHeapNode threadSafeHeapNode2 = threadSafeHeapNodeArr[i];
            threadSafeHeapNode2.getClass();
            if (((Comparable) threadSafeHeapNode).compareTo(threadSafeHeapNode2) <= 0) {
                return;
            }
            swap(i, i2);
            i = i2;
        }
    }

    private final void swap(int i, int i2) {
        ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
        threadSafeHeapNodeArr.getClass();
        ThreadSafeHeapNode threadSafeHeapNode = threadSafeHeapNodeArr[i2];
        threadSafeHeapNode.getClass();
        ThreadSafeHeapNode threadSafeHeapNode2 = threadSafeHeapNodeArr[i];
        threadSafeHeapNode2.getClass();
        threadSafeHeapNodeArr[i] = threadSafeHeapNode;
        threadSafeHeapNodeArr[i2] = threadSafeHeapNode2;
        threadSafeHeapNode.setIndex(i);
        threadSafeHeapNode2.setIndex(i2);
    }

    public final void addImpl(ThreadSafeHeapNode threadSafeHeapNode) {
        threadSafeHeapNode.getClass();
        threadSafeHeapNode.setHeap(this);
        ThreadSafeHeapNode[] threadSafeHeapNodeArrRealloc = realloc();
        int size = getSize();
        setSize(size + 1);
        threadSafeHeapNodeArrRealloc[size] = threadSafeHeapNode;
        threadSafeHeapNode.setIndex(size);
        siftUpFrom(size);
    }

    public final ThreadSafeHeapNode firstImpl() {
        ThreadSafeHeapNode[] threadSafeHeapNodeArr = this.a;
        if (threadSafeHeapNodeArr != null) {
            return threadSafeHeapNodeArr[0];
        }
        return null;
    }

    public final int getSize() {
        return this._size.getValue();
    }

    public final boolean isEmpty() {
        return getSize() == 0;
    }

    public final ThreadSafeHeapNode peek() {
        ThreadSafeHeapNode threadSafeHeapNodeFirstImpl;
        synchronized (this) {
            threadSafeHeapNodeFirstImpl = firstImpl();
        }
        return threadSafeHeapNodeFirstImpl;
    }

    public final boolean remove(ThreadSafeHeapNode threadSafeHeapNode) {
        boolean z;
        threadSafeHeapNode.getClass();
        synchronized (this) {
            if (threadSafeHeapNode.getHeap() == null) {
                z = false;
            } else {
                removeAtImpl(threadSafeHeapNode.getIndex());
                z = true;
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final kotlinx.coroutines.internal.ThreadSafeHeapNode removeAtImpl(int r6) {
        /*
            r5 = this;
            kotlinx.coroutines.internal.ThreadSafeHeapNode[] r0 = r5.a
            r0.getClass()
            int r1 = r5.getSize()
            r2 = -1
            int r1 = r1 + r2
            r5.setSize(r1)
            int r1 = r5.getSize()
            if (r6 >= r1) goto L3d
            int r1 = r5.getSize()
            r5.swap(r6, r1)
            int r1 = r6 + (-1)
            int r1 = r1 / 2
            if (r6 <= 0) goto L3a
            r3 = r0[r6]
            r3.getClass()
            java.lang.Comparable r3 = (java.lang.Comparable) r3
            r4 = r0[r1]
            r4.getClass()
            int r3 = r3.compareTo(r4)
            if (r3 >= 0) goto L3a
            r5.swap(r6, r1)
            r5.siftUpFrom(r1)
            goto L3d
        L3a:
            r5.siftDownFrom(r6)
        L3d:
            int r6 = r5.getSize()
            r6 = r0[r6]
            r6.getClass()
            r1 = 0
            r6.setHeap(r1)
            r6.setIndex(r2)
            int r5 = r5.getSize()
            r0[r5] = r1
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ThreadSafeHeap.removeAtImpl(int):kotlinx.coroutines.internal.ThreadSafeHeapNode");
    }

    public final ThreadSafeHeapNode removeFirstOrNull() {
        ThreadSafeHeapNode threadSafeHeapNodeRemoveAtImpl;
        synchronized (this) {
            threadSafeHeapNodeRemoveAtImpl = getSize() > 0 ? removeAtImpl(0) : null;
        }
        return threadSafeHeapNodeRemoveAtImpl;
    }
}
