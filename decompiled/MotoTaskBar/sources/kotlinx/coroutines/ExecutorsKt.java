package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: Executors.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class ExecutorsKt {
    public static final CoroutineDispatcher from(Executor executor) {
        executor.getClass();
        return new ExecutorCoroutineDispatcherImpl(executor);
    }

    public static final ExecutorCoroutineDispatcher from(ExecutorService executorService) {
        executorService.getClass();
        return new ExecutorCoroutineDispatcherImpl(executorService);
    }
}
