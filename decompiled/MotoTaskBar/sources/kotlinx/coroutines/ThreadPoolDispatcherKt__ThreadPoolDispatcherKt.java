package kotlinx.coroutines;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: ThreadPoolDispatcher.kt */
/* JADX INFO: loaded from: classes2.dex */
abstract /* synthetic */ class ThreadPoolDispatcherKt__ThreadPoolDispatcherKt {
    public static final ExecutorCoroutineDispatcher newFixedThreadPoolContext(final int i, final String str) {
        str.getClass();
        if (i >= 1) {
            final AtomicInteger atomicInteger = new AtomicInteger();
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(i, new ThreadFactory() { // from class: kotlinx.coroutines.ThreadPoolDispatcherKt__ThreadPoolDispatcherKt$newFixedThreadPoolContext$executor$1
                @Override // java.util.concurrent.ThreadFactory
                public final Thread newThread(Runnable runnable) {
                    String str2;
                    if (i == 1) {
                        str2 = str;
                    } else {
                        str2 = str + "-" + atomicInteger.incrementAndGet();
                    }
                    Thread thread = new Thread(runnable, str2);
                    thread.setDaemon(true);
                    return thread;
                }
            });
            scheduledExecutorServiceNewScheduledThreadPool.getClass();
            return ExecutorsKt.from((ExecutorService) scheduledExecutorServiceNewScheduledThreadPool);
        }
        throw new IllegalArgumentException(("Expected at least one thread, but " + i + " specified").toString());
    }
}
