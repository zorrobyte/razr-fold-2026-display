package com.android.settingslib.utils;

import android.os.Handler;
import android.os.Looper;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public abstract class ThreadUtils {
    private static volatile ListeningExecutorService sListeningService;
    private static volatile Handler sMainThreadHandler;

    public static synchronized ListeningExecutorService getBackgroundExecutor() {
        try {
            if (sListeningService == null) {
                sListeningService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
            }
        } catch (Throwable th) {
            throw th;
        }
        return sListeningService;
    }

    public static Handler getUiThreadHandler() {
        if (sMainThreadHandler == null) {
            sMainThreadHandler = new Handler(Looper.getMainLooper());
        }
        return sMainThreadHandler;
    }

    public static void postOnMainThread(Runnable runnable) {
        getUiThreadHandler().post(runnable);
    }
}
