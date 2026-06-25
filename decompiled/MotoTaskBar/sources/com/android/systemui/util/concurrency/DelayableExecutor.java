package com.android.systemui.util.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public interface DelayableExecutor extends Executor {
    default Runnable executeDelayed(Runnable runnable, long j) {
        return executeDelayed(runnable, j, TimeUnit.MILLISECONDS);
    }

    Runnable executeDelayed(Runnable runnable, long j, TimeUnit timeUnit);
}
