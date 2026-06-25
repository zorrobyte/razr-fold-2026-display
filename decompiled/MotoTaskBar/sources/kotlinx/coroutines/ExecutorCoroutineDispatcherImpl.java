package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.coroutines.CoroutineContext;

/* JADX INFO: compiled from: Executors.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ExecutorCoroutineDispatcherImpl extends ExecutorCoroutineDispatcher implements Delay {
    private final Executor executor;

    public ExecutorCoroutineDispatcherImpl(Executor executor) {
        executor.getClass();
        this.executor = executor;
        if (getExecutor() instanceof ScheduledThreadPoolExecutor) {
            ((ScheduledThreadPoolExecutor) getExecutor()).setRemoveOnCancelPolicy(true);
        }
    }

    private final void cancelJobOnRejection(CoroutineContext coroutineContext, RejectedExecutionException rejectedExecutionException) {
        JobKt.cancel(coroutineContext, ExceptionsKt.CancellationException("The task was rejected", rejectedExecutionException));
    }

    private final ScheduledFuture scheduleBlock(ScheduledExecutorService scheduledExecutorService, Runnable runnable, CoroutineContext coroutineContext, long j) {
        try {
            return scheduledExecutorService.schedule(runnable, j, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            this.cancelJobOnRejection(coroutineContext, e);
            return null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Executor executor = getExecutor();
        ExecutorService executorService = executor instanceof ExecutorService ? (ExecutorService) executor : null;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        coroutineContext.getClass();
        runnable.getClass();
        try {
            Executor executor = getExecutor();
            AbstractTimeSourceKt.access$getTimeSource$p();
            executor.execute(runnable);
        } catch (RejectedExecutionException e) {
            AbstractTimeSourceKt.access$getTimeSource$p();
            cancelJobOnRejection(coroutineContext, e);
            Dispatchers.getIO().dispatch(coroutineContext, runnable);
        }
    }

    public boolean equals(Object obj) {
        return (obj instanceof ExecutorCoroutineDispatcherImpl) && ((ExecutorCoroutineDispatcherImpl) obj).getExecutor() == getExecutor();
    }

    public Executor getExecutor() {
        return this.executor;
    }

    public int hashCode() {
        return System.identityHashCode(getExecutor());
    }

    @Override // kotlinx.coroutines.Delay
    public DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext) {
        long j2;
        Runnable runnable2;
        CoroutineContext coroutineContext2;
        runnable.getClass();
        coroutineContext.getClass();
        Executor executor = getExecutor();
        ScheduledFuture scheduledFutureScheduleBlock = null;
        ScheduledExecutorService scheduledExecutorService = executor instanceof ScheduledExecutorService ? (ScheduledExecutorService) executor : null;
        if (scheduledExecutorService != null) {
            j2 = j;
            runnable2 = runnable;
            coroutineContext2 = coroutineContext;
            scheduledFutureScheduleBlock = scheduleBlock(scheduledExecutorService, runnable2, coroutineContext2, j2);
        } else {
            j2 = j;
            runnable2 = runnable;
            coroutineContext2 = coroutineContext;
        }
        return scheduledFutureScheduleBlock != null ? new DisposableFutureHandle(scheduledFutureScheduleBlock) : DefaultExecutor.INSTANCE.invokeOnTimeout(j2, runnable2, coroutineContext2);
    }

    @Override // kotlinx.coroutines.Delay
    public void scheduleResumeAfterDelay(long j, CancellableContinuation cancellableContinuation) {
        long j2;
        cancellableContinuation.getClass();
        Executor executor = getExecutor();
        ScheduledFuture scheduledFutureScheduleBlock = null;
        ScheduledExecutorService scheduledExecutorService = executor instanceof ScheduledExecutorService ? (ScheduledExecutorService) executor : null;
        if (scheduledExecutorService != null) {
            j2 = j;
            scheduledFutureScheduleBlock = scheduleBlock(scheduledExecutorService, new ResumeUndispatchedRunnable(this, cancellableContinuation), cancellableContinuation.getContext(), j2);
        } else {
            j2 = j;
        }
        if (scheduledFutureScheduleBlock != null) {
            CancellableContinuationKt.invokeOnCancellation(cancellableContinuation, new CancelFutureOnCancel(scheduledFutureScheduleBlock));
        } else {
            DefaultExecutor.INSTANCE.scheduleResumeAfterDelay(j2, cancellableContinuation);
        }
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public String toString() {
        return getExecutor().toString();
    }
}
