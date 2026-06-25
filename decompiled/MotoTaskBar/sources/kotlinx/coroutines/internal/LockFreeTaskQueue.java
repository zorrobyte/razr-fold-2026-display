package kotlinx.coroutines.internal;

import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;

/* JADX INFO: compiled from: LockFreeTaskQueue.kt */
/* JADX INFO: loaded from: classes2.dex */
public class LockFreeTaskQueue {
    private final AtomicRef _cur;

    public LockFreeTaskQueue(boolean z) {
        this._cur = AtomicFU.atomic(new LockFreeTaskQueueCore(8, z));
    }

    public final boolean addLast(Object obj) {
        obj.getClass();
        AtomicRef atomicRef = this._cur;
        while (true) {
            LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.getValue();
            int iAddLast = lockFreeTaskQueueCore.addLast(obj);
            if (iAddLast == 0) {
                return true;
            }
            if (iAddLast == 1) {
                this._cur.compareAndSet(lockFreeTaskQueueCore, lockFreeTaskQueueCore.next());
            } else if (iAddLast == 2) {
                return false;
            }
        }
    }

    public final void close() {
        AtomicRef atomicRef = this._cur;
        while (true) {
            LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.getValue();
            if (lockFreeTaskQueueCore.close()) {
                return;
            } else {
                this._cur.compareAndSet(lockFreeTaskQueueCore, lockFreeTaskQueueCore.next());
            }
        }
    }

    public final int getSize() {
        return ((LockFreeTaskQueueCore) this._cur.getValue()).getSize();
    }

    public final Object removeFirstOrNull() {
        AtomicRef atomicRef = this._cur;
        while (true) {
            LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.getValue();
            Object objRemoveFirstOrNull = lockFreeTaskQueueCore.removeFirstOrNull();
            if (objRemoveFirstOrNull != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                return objRemoveFirstOrNull;
            }
            this._cur.compareAndSet(lockFreeTaskQueueCore, lockFreeTaskQueueCore.next());
        }
    }
}
