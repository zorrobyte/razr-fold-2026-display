package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.ranges.RangesKt;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;

/* JADX INFO: compiled from: EventLoop.common.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class EventLoopImplBase extends EventLoopImplPlatform implements Delay {
    private final AtomicRef _queue = AtomicFU.atomic((Object) null);
    private final AtomicRef _delayed = AtomicFU.atomic((Object) null);
    private final AtomicBoolean _isCompleted = AtomicFU.atomic(false);

    /* JADX INFO: compiled from: EventLoop.common.kt */
    final class DelayedResumeTask extends DelayedTask {
        private final CancellableContinuation cont;
        final /* synthetic */ EventLoopImplBase this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DelayedResumeTask(EventLoopImplBase eventLoopImplBase, long j, CancellableContinuation cancellableContinuation) {
            super(j);
            cancellableContinuation.getClass();
            this.this$0 = eventLoopImplBase;
            this.cont = cancellableContinuation;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.cont.resumeUndispatched(this.this$0, Unit.INSTANCE);
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        public String toString() {
            return super.toString() + this.cont;
        }
    }

    /* JADX INFO: compiled from: EventLoop.common.kt */
    final class DelayedRunnableTask extends DelayedTask {
        private final Runnable block;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DelayedRunnableTask(long j, Runnable runnable) {
            super(j);
            runnable.getClass();
            this.block = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.block.run();
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        public String toString() {
            return super.toString() + this.block;
        }
    }

    /* JADX INFO: compiled from: EventLoop.common.kt */
    public abstract class DelayedTask implements Runnable, Comparable, DisposableHandle, ThreadSafeHeapNode {
        private volatile Object _heap;
        private int index = -1;
        public long nanoTime;

        public DelayedTask(long j) {
            this.nanoTime = j;
        }

        @Override // java.lang.Comparable
        public int compareTo(DelayedTask delayedTask) {
            delayedTask.getClass();
            long j = this.nanoTime - delayedTask.nanoTime;
            if (j > 0) {
                return 1;
            }
            return j < 0 ? -1 : 0;
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public final void dispose() {
            synchronized (this) {
                try {
                    Object obj = this._heap;
                    if (obj == EventLoop_commonKt.DISPOSED_TASK) {
                        return;
                    }
                    DelayedTaskQueue delayedTaskQueue = obj instanceof DelayedTaskQueue ? (DelayedTaskQueue) obj : null;
                    if (delayedTaskQueue != null) {
                        delayedTaskQueue.remove(this);
                    }
                    this._heap = EventLoop_commonKt.DISPOSED_TASK;
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public ThreadSafeHeap getHeap() {
            Object obj = this._heap;
            if (obj instanceof ThreadSafeHeap) {
                return (ThreadSafeHeap) obj;
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public int getIndex() {
            return this.index;
        }

        public final int scheduleTask(long j, DelayedTaskQueue delayedTaskQueue, EventLoopImplBase eventLoopImplBase) {
            delayedTaskQueue.getClass();
            eventLoopImplBase.getClass();
            synchronized (this) {
                if (this._heap == EventLoop_commonKt.DISPOSED_TASK) {
                    return 2;
                }
                synchronized (delayedTaskQueue) {
                    try {
                        DelayedTask delayedTask = (DelayedTask) delayedTaskQueue.firstImpl();
                        if (eventLoopImplBase.isCompleted()) {
                            return 1;
                        }
                        if (delayedTask == null) {
                            delayedTaskQueue.timeNow = j;
                        } else {
                            long j2 = delayedTask.nanoTime;
                            if (j2 - j < 0) {
                                j = j2;
                            }
                            if (j - delayedTaskQueue.timeNow > 0) {
                                delayedTaskQueue.timeNow = j;
                            }
                        }
                        long j3 = this.nanoTime;
                        long j4 = delayedTaskQueue.timeNow;
                        if (j3 - j4 < 0) {
                            this.nanoTime = j4;
                        }
                        delayedTaskQueue.addImpl(this);
                        return 0;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public void setHeap(ThreadSafeHeap threadSafeHeap) {
            if (this._heap == EventLoop_commonKt.DISPOSED_TASK) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            this._heap = threadSafeHeap;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public void setIndex(int i) {
            this.index = i;
        }

        public final boolean timeToExecute(long j) {
            return j - this.nanoTime >= 0;
        }

        public String toString() {
            return "Delayed[nanos=" + this.nanoTime + "]";
        }
    }

    /* JADX INFO: compiled from: EventLoop.common.kt */
    public final class DelayedTaskQueue extends ThreadSafeHeap {
        public long timeNow;

        public DelayedTaskQueue(long j) {
            this.timeNow = j;
        }
    }

    private final void closeQueue() {
        AtomicRef atomicRef = this._queue;
        while (true) {
            Object value = atomicRef.getValue();
            if (value == null) {
                if (this._queue.compareAndSet(null, EventLoop_commonKt.CLOSED_EMPTY)) {
                    return;
                }
            } else if (value instanceof LockFreeTaskQueueCore) {
                ((LockFreeTaskQueueCore) value).close();
                return;
            } else {
                if (value == EventLoop_commonKt.CLOSED_EMPTY) {
                    return;
                }
                LockFreeTaskQueueCore lockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore.addLast((Runnable) value);
                if (this._queue.compareAndSet(value, lockFreeTaskQueueCore)) {
                    return;
                }
            }
        }
    }

    private final Runnable dequeue() {
        AtomicRef atomicRef = this._queue;
        while (true) {
            Object value = atomicRef.getValue();
            if (value == null) {
                return null;
            }
            if (value instanceof LockFreeTaskQueueCore) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) value;
                Object objRemoveFirstOrNull = lockFreeTaskQueueCore.removeFirstOrNull();
                if (objRemoveFirstOrNull != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                    return (Runnable) objRemoveFirstOrNull;
                }
                this._queue.compareAndSet(value, lockFreeTaskQueueCore.next());
            } else {
                if (value == EventLoop_commonKt.CLOSED_EMPTY) {
                    return null;
                }
                if (this._queue.compareAndSet(value, null)) {
                    return (Runnable) value;
                }
            }
        }
    }

    private final void enqueueDelayedTasks() {
        ThreadSafeHeapNode threadSafeHeapNodeRemoveAtImpl;
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.getValue();
        if (delayedTaskQueue == null || delayedTaskQueue.isEmpty()) {
            return;
        }
        AbstractTimeSourceKt.access$getTimeSource$p();
        long jNanoTime = System.nanoTime();
        do {
            synchronized (delayedTaskQueue) {
                try {
                    ThreadSafeHeapNode threadSafeHeapNodeFirstImpl = delayedTaskQueue.firstImpl();
                    if (threadSafeHeapNodeFirstImpl != null) {
                        DelayedTask delayedTask = (DelayedTask) threadSafeHeapNodeFirstImpl;
                        threadSafeHeapNodeRemoveAtImpl = delayedTask.timeToExecute(jNanoTime) ? enqueueImpl(delayedTask) : false ? delayedTaskQueue.removeAtImpl(0) : null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } while (((DelayedTask) threadSafeHeapNodeRemoveAtImpl) != null);
    }

    private final boolean enqueueImpl(Runnable runnable) {
        AtomicRef atomicRef = this._queue;
        while (true) {
            Object value = atomicRef.getValue();
            if (isCompleted()) {
                return false;
            }
            if (value == null) {
                if (this._queue.compareAndSet(null, runnable)) {
                    return true;
                }
            } else if (value instanceof LockFreeTaskQueueCore) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) value;
                int iAddLast = lockFreeTaskQueueCore.addLast(runnable);
                if (iAddLast == 0) {
                    return true;
                }
                if (iAddLast == 1) {
                    this._queue.compareAndSet(value, lockFreeTaskQueueCore.next());
                } else if (iAddLast == 2) {
                    return false;
                }
            } else {
                if (value == EventLoop_commonKt.CLOSED_EMPTY) {
                    return false;
                }
                LockFreeTaskQueueCore lockFreeTaskQueueCore2 = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore2.addLast((Runnable) value);
                lockFreeTaskQueueCore2.addLast(runnable);
                if (this._queue.compareAndSet(value, lockFreeTaskQueueCore2)) {
                    return true;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCompleted() {
        return this._isCompleted.getValue();
    }

    private final void rescheduleAllDelayed() {
        DelayedTask delayedTask;
        AbstractTimeSourceKt.access$getTimeSource$p();
        long jNanoTime = System.nanoTime();
        while (true) {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.getValue();
            if (delayedTaskQueue == null || (delayedTask = (DelayedTask) delayedTaskQueue.removeFirstOrNull()) == null) {
                return;
            } else {
                reschedule(jNanoTime, delayedTask);
            }
        }
    }

    private final int scheduleImpl(long j, DelayedTask delayedTask) {
        if (isCompleted()) {
            return 1;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.getValue();
        if (delayedTaskQueue == null) {
            this._delayed.compareAndSet(null, new DelayedTaskQueue(j));
            Object value = this._delayed.getValue();
            value.getClass();
            delayedTaskQueue = (DelayedTaskQueue) value;
        }
        return delayedTask.scheduleTask(j, delayedTaskQueue, this);
    }

    private final void setCompleted(boolean z) {
        this._isCompleted.setValue(z);
    }

    private final boolean shouldUnpark(DelayedTask delayedTask) {
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.getValue();
        return (delayedTaskQueue != null ? (DelayedTask) delayedTaskQueue.peek() : null) == delayedTask;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        coroutineContext.getClass();
        runnable.getClass();
        enqueue(runnable);
    }

    public void enqueue(Runnable runnable) {
        runnable.getClass();
        enqueueDelayedTasks();
        if (enqueueImpl(runnable)) {
            unpark();
        } else {
            DefaultExecutor.INSTANCE.enqueue(runnable);
        }
    }

    @Override // kotlinx.coroutines.EventLoop
    protected long getNextTime() {
        DelayedTask delayedTask;
        if (super.getNextTime() == 0) {
            return 0L;
        }
        Object value = this._queue.getValue();
        if (value != null) {
            if (!(value instanceof LockFreeTaskQueueCore)) {
                return value == EventLoop_commonKt.CLOSED_EMPTY ? Long.MAX_VALUE : 0L;
            }
            if (!((LockFreeTaskQueueCore) value).isEmpty()) {
                return 0L;
            }
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.getValue();
        if (delayedTaskQueue == null || (delayedTask = (DelayedTask) delayedTaskQueue.peek()) == null) {
            return Long.MAX_VALUE;
        }
        long j = delayedTask.nanoTime;
        AbstractTimeSourceKt.access$getTimeSource$p();
        return RangesKt.coerceAtLeast(j - System.nanoTime(), 0L);
    }

    public DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext) {
        return Delay.DefaultImpls.invokeOnTimeout(this, j, runnable, coroutineContext);
    }

    protected boolean isEmpty() {
        if (!isUnconfinedQueueEmpty()) {
            return false;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.getValue();
        if (delayedTaskQueue != null && !delayedTaskQueue.isEmpty()) {
            return false;
        }
        Object value = this._queue.getValue();
        if (value == null) {
            return true;
        }
        return value instanceof LockFreeTaskQueueCore ? ((LockFreeTaskQueueCore) value).isEmpty() : value == EventLoop_commonKt.CLOSED_EMPTY;
    }

    @Override // kotlinx.coroutines.EventLoop
    public long processNextEvent() {
        if (processUnconfinedEvent()) {
            return 0L;
        }
        enqueueDelayedTasks();
        Runnable runnableDequeue = dequeue();
        if (runnableDequeue == null) {
            return getNextTime();
        }
        runnableDequeue.run();
        return 0L;
    }

    protected final void resetAll() {
        this._queue.setValue(null);
        this._delayed.setValue(null);
    }

    public final void schedule(long j, DelayedTask delayedTask) {
        delayedTask.getClass();
        int iScheduleImpl = scheduleImpl(j, delayedTask);
        if (iScheduleImpl == 0) {
            if (shouldUnpark(delayedTask)) {
                unpark();
            }
        } else if (iScheduleImpl == 1) {
            reschedule(j, delayedTask);
        } else if (iScheduleImpl != 2) {
            throw new IllegalStateException("unexpected result");
        }
    }

    protected final DisposableHandle scheduleInvokeOnTimeout(long j, Runnable runnable) {
        runnable.getClass();
        long jDelayToNanos = EventLoop_commonKt.delayToNanos(j);
        if (jDelayToNanos >= 4611686018427387903L) {
            return NonDisposableHandle.INSTANCE;
        }
        AbstractTimeSourceKt.access$getTimeSource$p();
        long jNanoTime = System.nanoTime();
        DelayedRunnableTask delayedRunnableTask = new DelayedRunnableTask(jDelayToNanos + jNanoTime, runnable);
        schedule(jNanoTime, delayedRunnableTask);
        return delayedRunnableTask;
    }

    @Override // kotlinx.coroutines.Delay
    public void scheduleResumeAfterDelay(long j, CancellableContinuation cancellableContinuation) {
        cancellableContinuation.getClass();
        long jDelayToNanos = EventLoop_commonKt.delayToNanos(j);
        if (jDelayToNanos < 4611686018427387903L) {
            AbstractTimeSourceKt.access$getTimeSource$p();
            long jNanoTime = System.nanoTime();
            DelayedResumeTask delayedResumeTask = new DelayedResumeTask(this, jDelayToNanos + jNanoTime, cancellableContinuation);
            schedule(jNanoTime, delayedResumeTask);
            CancellableContinuationKt.disposeOnCancellation(cancellableContinuation, delayedResumeTask);
        }
    }

    @Override // kotlinx.coroutines.EventLoop
    public void shutdown() {
        ThreadLocalEventLoop.INSTANCE.resetEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        setCompleted(true);
        closeQueue();
        while (processNextEvent() <= 0) {
        }
        rescheduleAllDelayed();
    }
}
