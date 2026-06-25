package com.google.android.setupcompat.internal;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class ExecutorProvider {
    public static final ExecutorProvider setupCompatServiceInvoker = new ExecutorProvider(createSizeBoundedExecutor("SetupCompatServiceInvoker", 50));
    private final Executor executor;
    private Executor injectedExecutor;

    public static /* synthetic */ Thread $r8$lambda$zP8axJi3ZkzCUEV6qbLyclcTy20(String str, Runnable runnable) {
        return new Thread(runnable, str);
    }

    private ExecutorProvider(Executor executor) {
        this.executor = executor;
    }

    public static ExecutorService createSizeBoundedExecutor(final String str, int i) {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue(i), new ThreadFactory() { // from class: com.google.android.setupcompat.internal.ExecutorProvider$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return ExecutorProvider.$r8$lambda$zP8axJi3ZkzCUEV6qbLyclcTy20(str, runnable);
            }
        });
    }

    public static void resetExecutors() {
        setupCompatServiceInvoker.injectedExecutor = null;
    }

    public Executor get() {
        Executor executor = this.injectedExecutor;
        return executor != null ? executor : this.executor;
    }

    public void injectExecutor(Executor executor) {
        this.injectedExecutor = executor;
    }
}
