package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.NotCompleted;

/* JADX INFO: compiled from: ConcurrentLinkedList.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class Segment extends ConcurrentLinkedListNode implements NotCompleted {
    private final AtomicInt cleanedAndPointers;
    public final long id;

    public Segment(long j, Segment segment, int i) {
        super(segment);
        this.id = j;
        this.cleanedAndPointers = AtomicFU.atomic(i << 16);
    }

    public final boolean decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this.cleanedAndPointers.addAndGet(-65536) == getNumberOfSlots() && !isTail();
    }

    public abstract int getNumberOfSlots();

    @Override // kotlinx.coroutines.internal.ConcurrentLinkedListNode
    public boolean isRemoved() {
        return this.cleanedAndPointers.getValue() == getNumberOfSlots() && !isTail();
    }

    public abstract void onCancellation(int i, Throwable th, CoroutineContext coroutineContext);

    public final void onSlotCleaned() {
        if (this.cleanedAndPointers.incrementAndGet() == getNumberOfSlots()) {
            remove();
        }
    }

    public final boolean tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        int value;
        AtomicInt atomicInt = this.cleanedAndPointers;
        do {
            value = atomicInt.getValue();
            if (value == getNumberOfSlots() && !isTail()) {
                return false;
            }
        } while (!atomicInt.compareAndSet(value, 65536 + value));
        return true;
    }
}
