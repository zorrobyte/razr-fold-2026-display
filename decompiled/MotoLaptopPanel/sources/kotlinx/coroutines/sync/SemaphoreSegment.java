package kotlinx.coroutines.sync;

import kotlin.coroutines.CoroutineContext;
import kotlinx.atomicfu.AtomicArray;
import kotlinx.atomicfu.AtomicFU_commonKt;
import kotlinx.coroutines.internal.Segment;

/* JADX INFO: compiled from: Semaphore.kt */
/* JADX INFO: loaded from: classes.dex */
final class SemaphoreSegment extends Segment {
    private final AtomicArray acquirers;

    public SemaphoreSegment(long j, SemaphoreSegment semaphoreSegment, int i) {
        super(j, semaphoreSegment, i);
        this.acquirers = AtomicFU_commonKt.atomicArrayOfNulls(SemaphoreKt.SEGMENT_SIZE);
    }

    public final AtomicArray getAcquirers() {
        return this.acquirers;
    }

    @Override // kotlinx.coroutines.internal.Segment
    public int getNumberOfSlots() {
        return SemaphoreKt.SEGMENT_SIZE;
    }

    @Override // kotlinx.coroutines.internal.Segment
    public void onCancellation(int i, Throwable th, CoroutineContext coroutineContext) {
        coroutineContext.getClass();
        getAcquirers().get(i).setValue(SemaphoreKt.CANCELLED);
        onSlotCleaned();
    }

    public String toString() {
        return "SemaphoreSegment[id=" + this.id + ", hashCode=" + hashCode() + "]";
    }
}
