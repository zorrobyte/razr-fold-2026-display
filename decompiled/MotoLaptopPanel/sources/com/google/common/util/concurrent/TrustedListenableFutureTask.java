package com.google.common.util.concurrent;

import com.google.common.util.concurrent.FluentFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

/* JADX INFO: loaded from: classes.dex */
class TrustedListenableFutureTask extends FluentFuture.TrustedFuture implements RunnableFuture {
    private volatile InterruptibleTask task;

    final class TrustedFutureInterruptibleTask extends InterruptibleTask {
        private final Callable callable;

        TrustedFutureInterruptibleTask(Callable callable) {
            callable.getClass();
            this.callable = callable;
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        void afterRanInterruptiblyFailure(Throwable th) {
            TrustedListenableFutureTask.this.setException(th);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        void afterRanInterruptiblySuccess(Object obj) {
            TrustedListenableFutureTask.this.set(obj);
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        final boolean isDone() {
            return TrustedListenableFutureTask.this.isDone();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        Object runInterruptibly() {
            return this.callable.call();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        String toPendingString() {
            return this.callable.toString();
        }
    }

    TrustedListenableFutureTask(Callable callable) {
        this.task = new TrustedFutureInterruptibleTask(callable);
    }

    static TrustedListenableFutureTask create(Runnable runnable, Object obj) {
        return new TrustedListenableFutureTask(Executors.callable(runnable, obj));
    }

    static TrustedListenableFutureTask create(Callable callable) {
        return new TrustedListenableFutureTask(callable);
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected void afterDone() {
        InterruptibleTask interruptibleTask;
        super.afterDone();
        if (wasInterrupted() && (interruptibleTask = this.task) != null) {
            interruptibleTask.interruptTask();
        }
        this.task = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    protected String pendingToString() {
        InterruptibleTask interruptibleTask = this.task;
        if (interruptibleTask == null) {
            return super.pendingToString();
        }
        return "task=[" + interruptibleTask + "]";
    }

    @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
    public void run() {
        InterruptibleTask interruptibleTask = this.task;
        if (interruptibleTask != null) {
            interruptibleTask.run();
        }
        this.task = null;
    }
}
