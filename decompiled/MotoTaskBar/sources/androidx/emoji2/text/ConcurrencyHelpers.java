package androidx.emoji2.text;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
abstract class ConcurrencyHelpers {

    abstract class Handler28Impl {
        public static Handler createAsync(Looper looper) {
            return Handler.createAsync(looper);
        }
    }

    public static /* synthetic */ Thread $r8$lambda$hDWNjMgTS47ccxPkL8ebwFGVHg4(String str, Runnable runnable) {
        Thread thread = new Thread(runnable, str);
        thread.setPriority(10);
        return thread;
    }

    static Executor convertHandlerToExecutor(Handler handler) {
        handler.getClass();
        return new ConcurrencyHelpers$$ExternalSyntheticLambda0(handler);
    }

    static ThreadPoolExecutor createBackgroundPriorityExecutor(final String str) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new ThreadFactory() { // from class: androidx.emoji2.text.ConcurrencyHelpers$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return ConcurrencyHelpers.$r8$lambda$hDWNjMgTS47ccxPkL8ebwFGVHg4(str, runnable);
            }
        });
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    static Handler mainHandlerAsync() {
        return Handler28Impl.createAsync(Looper.getMainLooper());
    }

    static Executor mainThreadExecutor() {
        return convertHandlerToExecutor(mainHandlerAsync());
    }
}
