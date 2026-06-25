package kotlinx.coroutines.scheduling;

import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicRef;

/* JADX INFO: compiled from: WorkQueue.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WorkQueue {
    private final AtomicReferenceArray buffer = new AtomicReferenceArray(128);
    private final AtomicRef lastScheduledTask = AtomicFU.atomic((Object) null);
    private final AtomicInt producerIndex = AtomicFU.atomic(0);
    private final AtomicInt consumerIndex = AtomicFU.atomic(0);
    private final AtomicInt blockingTasksInBuffer = AtomicFU.atomic(0);

    private final Task addLast(Task task) {
        if (getBufferSize() == 127) {
            return task;
        }
        if (task.taskContext) {
            this.blockingTasksInBuffer.incrementAndGet();
        }
        int value = this.producerIndex.getValue() & 127;
        while (this.buffer.get(value) != null) {
            Thread.yield();
        }
        this.buffer.lazySet(value, task);
        this.producerIndex.incrementAndGet();
        return null;
    }

    private final void decrementIfBlocking(Task task) {
        if (task == null || !task.taskContext) {
            return;
        }
        this.blockingTasksInBuffer.decrementAndGet();
    }

    private final int getBufferSize() {
        return this.producerIndex.getValue() - this.consumerIndex.getValue();
    }

    private final Task pollBuffer() {
        Task task;
        while (true) {
            int value = this.consumerIndex.getValue();
            if (value - this.producerIndex.getValue() == 0) {
                return null;
            }
            int i = value & 127;
            if (this.consumerIndex.compareAndSet(value, value + 1) && (task = (Task) this.buffer.getAndSet(i, null)) != null) {
                decrementIfBlocking(task);
                return task;
            }
        }
    }

    private final boolean pollTo(GlobalQueue globalQueue) {
        Task taskPollBuffer = pollBuffer();
        if (taskPollBuffer == null) {
            return false;
        }
        globalQueue.addLast(taskPollBuffer);
        return true;
    }

    private final Task pollWithExclusiveMode(boolean z) {
        Task task;
        do {
            task = (Task) this.lastScheduledTask.getValue();
            if (task == null || task.taskContext != z) {
                int value = this.consumerIndex.getValue();
                int value2 = this.producerIndex.getValue();
                while (value != value2) {
                    if (z && this.blockingTasksInBuffer.getValue() == 0) {
                        return null;
                    }
                    value2--;
                    Task taskTryExtractFromTheMiddle = tryExtractFromTheMiddle(value2, z);
                    if (taskTryExtractFromTheMiddle != null) {
                        return taskTryExtractFromTheMiddle;
                    }
                }
                return null;
            }
        } while (!this.lastScheduledTask.compareAndSet(task, null));
        return task;
    }

    private final Task stealWithExclusiveMode(int i) {
        int value = this.consumerIndex.getValue();
        int value2 = this.producerIndex.getValue();
        boolean z = i == 1;
        while (value != value2) {
            if (z && this.blockingTasksInBuffer.getValue() == 0) {
                return null;
            }
            int i2 = value + 1;
            Task taskTryExtractFromTheMiddle = tryExtractFromTheMiddle(value, z);
            if (taskTryExtractFromTheMiddle != null) {
                return taskTryExtractFromTheMiddle;
            }
            value = i2;
        }
        return null;
    }

    private final Task tryExtractFromTheMiddle(int i, boolean z) {
        int i2 = i & 127;
        Task task = (Task) this.buffer.get(i2);
        if (task == null || task.taskContext != z || !this.buffer.compareAndSet(i2, task, null)) {
            return null;
        }
        if (z) {
            this.blockingTasksInBuffer.decrementAndGet();
        }
        return task;
    }

    private final long tryStealLastScheduled(int i, Ref$ObjectRef ref$ObjectRef) {
        Task task;
        do {
            task = (Task) this.lastScheduledTask.getValue();
            if (task == null) {
                return -2L;
            }
            if (((task.taskContext ? 1 : 2) & i) == 0) {
                return -2L;
            }
            long jNanoTime = TasksKt.schedulerTimeSource.nanoTime() - task.submissionTime;
            long j = TasksKt.WORK_STEALING_TIME_RESOLUTION_NS;
            if (jNanoTime < j) {
                return j - jNanoTime;
            }
        } while (!this.lastScheduledTask.compareAndSet(task, null));
        ref$ObjectRef.element = task;
        return -1L;
    }

    public final Task add(Task task, boolean z) {
        task.getClass();
        if (z) {
            return addLast(task);
        }
        Task task2 = (Task) this.lastScheduledTask.getAndSet(task);
        if (task2 == null) {
            return null;
        }
        return addLast(task2);
    }

    public final int getSize$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        Object value = this.lastScheduledTask.getValue();
        int bufferSize = getBufferSize();
        return value != null ? bufferSize + 1 : bufferSize;
    }

    public final void offloadAllWorkTo(GlobalQueue globalQueue) {
        globalQueue.getClass();
        Task task = (Task) this.lastScheduledTask.getAndSet(null);
        if (task != null) {
            globalQueue.addLast(task);
        }
        while (pollTo(globalQueue)) {
        }
    }

    public final Task poll() {
        Task task = (Task) this.lastScheduledTask.getAndSet(null);
        return task == null ? pollBuffer() : task;
    }

    public final Task pollBlocking() {
        return pollWithExclusiveMode(true);
    }

    public final long trySteal(int i, Ref$ObjectRef ref$ObjectRef) {
        ref$ObjectRef.getClass();
        Task taskPollBuffer = i == 3 ? pollBuffer() : stealWithExclusiveMode(i);
        if (taskPollBuffer == null) {
            return tryStealLastScheduled(i, ref$ObjectRef);
        }
        ref$ObjectRef.element = taskPollBuffer;
        return -1L;
    }
}
