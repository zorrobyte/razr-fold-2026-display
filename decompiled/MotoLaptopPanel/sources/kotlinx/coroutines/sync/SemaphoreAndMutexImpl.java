package kotlinx.coroutines.sync;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.selects.SelectInstance;

/* JADX INFO: compiled from: Semaphore.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SemaphoreAndMutexImpl {
    private final AtomicInt _availablePermits;
    private final AtomicLong deqIdx = AtomicFU.atomic(0L);
    private final AtomicLong enqIdx = AtomicFU.atomic(0L);
    private final AtomicRef head;
    private final Function3 onCancellationRelease;
    private final int permits;
    private final AtomicRef tail;

    public SemaphoreAndMutexImpl(int i, int i2) {
        this.permits = i;
        if (i <= 0) {
            throw new IllegalArgumentException(("Semaphore should have at least 1 permit, but had " + i).toString());
        }
        if (i2 < 0 || i2 > i) {
            throw new IllegalArgumentException(("The number of acquired permits should be in 0.." + i).toString());
        }
        SemaphoreSegment semaphoreSegment = new SemaphoreSegment(0L, null, 2);
        this.head = AtomicFU.atomic(semaphoreSegment);
        this.tail = AtomicFU.atomic(semaphoreSegment);
        this._availablePermits = AtomicFU.atomic(i - i2);
        this.onCancellationRelease = new Function3() { // from class: kotlinx.coroutines.sync.SemaphoreAndMutexImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return SemaphoreAndMutexImpl.onCancellationRelease$lambda$2(this.f$0, (Throwable) obj, (Unit) obj2, (CoroutineContext) obj3);
            }
        };
    }

    private final boolean addAcquireToQueue(Waiter waiter) {
        Object objFindSegmentInternal;
        SemaphoreSegment semaphoreSegment = (SemaphoreSegment) this.tail.getValue();
        long andIncrement = this.enqIdx.getAndIncrement();
        SemaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1 semaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1 = SemaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1.INSTANCE;
        AtomicRef atomicRef = this.tail;
        long j = andIncrement / ((long) SemaphoreKt.SEGMENT_SIZE);
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(semaphoreSegment, j, semaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1);
            if (!SegmentOrClosed.m2242isClosedimpl(objFindSegmentInternal)) {
                Segment segmentM2241getSegmentimpl = SegmentOrClosed.m2241getSegmentimpl(objFindSegmentInternal);
                while (true) {
                    Segment segment = (Segment) atomicRef.getValue();
                    if (segment.id >= segmentM2241getSegmentimpl.id) {
                        break loop0;
                    }
                    if (!segmentM2241getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        break;
                    }
                    if (atomicRef.compareAndSet(segment, segmentM2241getSegmentimpl)) {
                        if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segment.remove();
                        }
                    } else if (segmentM2241getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        segmentM2241getSegmentimpl.remove();
                    }
                }
            } else {
                break;
            }
        }
        SemaphoreSegment semaphoreSegment2 = (SemaphoreSegment) SegmentOrClosed.m2241getSegmentimpl(objFindSegmentInternal);
        int i = (int) (andIncrement % ((long) SemaphoreKt.SEGMENT_SIZE));
        if (semaphoreSegment2.getAcquirers().get(i).compareAndSet(null, waiter)) {
            waiter.invokeOnCancellation(semaphoreSegment2, i);
            return true;
        }
        if (!semaphoreSegment2.getAcquirers().get(i).compareAndSet(SemaphoreKt.PERMIT, SemaphoreKt.TAKEN)) {
            return false;
        }
        if (waiter instanceof CancellableContinuation) {
            waiter.getClass();
            ((CancellableContinuation) waiter).resume(Unit.INSTANCE, this.onCancellationRelease);
        } else {
            if (!(waiter instanceof SelectInstance)) {
                throw new IllegalStateException(("unexpected: " + waiter).toString());
            }
            ((SelectInstance) waiter).selectInRegistrationPhase(Unit.INSTANCE);
        }
        return true;
    }

    private final void coerceAvailablePermitsAtMaximum() {
        int value;
        int i;
        do {
            value = this._availablePermits.getValue();
            i = this.permits;
            if (value <= i) {
                return;
            }
        } while (!this._availablePermits.compareAndSet(value, i));
    }

    private final int decPermits() {
        int andDecrement;
        do {
            andDecrement = this._availablePermits.getAndDecrement();
        } while (andDecrement > this.permits);
        return andDecrement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCancellationRelease$lambda$2(SemaphoreAndMutexImpl semaphoreAndMutexImpl, Throwable th, Unit unit, CoroutineContext coroutineContext) {
        th.getClass();
        unit.getClass();
        coroutineContext.getClass();
        semaphoreAndMutexImpl.release();
        return Unit.INSTANCE;
    }

    private final boolean tryResumeAcquire(Object obj) {
        if (!(obj instanceof CancellableContinuation)) {
            if (obj instanceof SelectInstance) {
                return ((SelectInstance) obj).trySelect(this, Unit.INSTANCE);
            }
            throw new IllegalStateException(("unexpected: " + obj).toString());
        }
        obj.getClass();
        CancellableContinuation cancellableContinuation = (CancellableContinuation) obj;
        Object objTryResume = cancellableContinuation.tryResume(Unit.INSTANCE, null, this.onCancellationRelease);
        if (objTryResume == null) {
            return false;
        }
        cancellableContinuation.completeResume(objTryResume);
        return true;
    }

    private final boolean tryResumeNextFromQueue() {
        Object objFindSegmentInternal;
        SemaphoreSegment semaphoreSegment = (SemaphoreSegment) this.head.getValue();
        long andIncrement = this.deqIdx.getAndIncrement();
        long j = andIncrement / ((long) SemaphoreKt.SEGMENT_SIZE);
        SemaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1 semaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1 = SemaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1.INSTANCE;
        AtomicRef atomicRef = this.head;
        loop0: while (true) {
            objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(semaphoreSegment, j, semaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1);
            if (SegmentOrClosed.m2242isClosedimpl(objFindSegmentInternal)) {
                break;
            }
            Segment segmentM2241getSegmentimpl = SegmentOrClosed.m2241getSegmentimpl(objFindSegmentInternal);
            while (true) {
                Segment segment = (Segment) atomicRef.getValue();
                if (segment.id >= segmentM2241getSegmentimpl.id) {
                    break loop0;
                }
                if (!segmentM2241getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    break;
                }
                if (atomicRef.compareAndSet(segment, segmentM2241getSegmentimpl)) {
                    if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                        segment.remove();
                    }
                } else if (segmentM2241getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                    segmentM2241getSegmentimpl.remove();
                }
            }
        }
        SemaphoreSegment semaphoreSegment2 = (SemaphoreSegment) SegmentOrClosed.m2241getSegmentimpl(objFindSegmentInternal);
        semaphoreSegment2.cleanPrev();
        if (semaphoreSegment2.id > j) {
            return false;
        }
        int i = (int) (andIncrement % ((long) SemaphoreKt.SEGMENT_SIZE));
        Object andSet = semaphoreSegment2.getAcquirers().get(i).getAndSet(SemaphoreKt.PERMIT);
        if (andSet != null) {
            if (andSet == SemaphoreKt.CANCELLED) {
                return false;
            }
            return tryResumeAcquire(andSet);
        }
        int i2 = SemaphoreKt.MAX_SPIN_CYCLES;
        for (int i3 = 0; i3 < i2; i3++) {
            if (semaphoreSegment2.getAcquirers().get(i).getValue() == SemaphoreKt.TAKEN) {
                return true;
            }
        }
        return !semaphoreSegment2.getAcquirers().get(i).compareAndSet(SemaphoreKt.PERMIT, SemaphoreKt.BROKEN);
    }

    protected final void acquire(CancellableContinuation cancellableContinuation) {
        cancellableContinuation.getClass();
        while (decPermits() <= 0) {
            if (addAcquireToQueue((Waiter) cancellableContinuation)) {
                return;
            }
        }
        cancellableContinuation.resume(Unit.INSTANCE, this.onCancellationRelease);
    }

    public final int getAvailablePermits() {
        return Math.max(this._availablePermits.getValue(), 0);
    }

    public final void release() {
        do {
            int andIncrement = this._availablePermits.getAndIncrement();
            if (andIncrement >= this.permits) {
                coerceAvailablePermitsAtMaximum();
                throw new IllegalStateException(("The number of released permits cannot be greater than " + this.permits).toString());
            }
            if (andIncrement >= 0) {
                return;
            }
        } while (!tryResumeNextFromQueue());
    }

    public final boolean tryAcquire() {
        while (true) {
            int value = this._availablePermits.getValue();
            if (value > this.permits) {
                coerceAvailablePermitsAtMaximum();
            } else {
                if (value <= 0) {
                    return false;
                }
                if (this._availablePermits.compareAndSet(value, value - 1)) {
                    return true;
                }
            }
        }
    }
}
