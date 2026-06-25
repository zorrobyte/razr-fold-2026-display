package com.android.systemui.util.concurrency;

import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class ThreadFactoryImpl implements ThreadFactory {
    ThreadFactoryImpl() {
    }

    public DelayableExecutor buildDelayableExecutorOnLooper(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public DelayableExecutor buildDelayableExecutorOnNewThread(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return buildDelayableExecutorOnLooper(handlerThread.getLooper());
    }

    @Override // com.android.systemui.util.concurrency.ThreadFactory
    public Executor buildExecutorOnNewThread(String str) {
        return buildDelayableExecutorOnNewThread(str);
    }
}
