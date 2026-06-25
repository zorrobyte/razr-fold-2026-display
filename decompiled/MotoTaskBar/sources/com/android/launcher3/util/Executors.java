package com.android.launcher3.util;

import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public abstract class Executors {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    public static final LooperExecutor MAIN_EXECUTOR;
    private static final int MAXIMUM_POOL_SIZE;
    public static final LooperExecutor MODEL_EXECUTOR;
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;
    public static final LooperExecutor UI_HELPER_EXECUTOR;

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        CPU_COUNT = iAvailableProcessors;
        int i = iAvailableProcessors + 1;
        CORE_POOL_SIZE = i;
        int i2 = (iAvailableProcessors * 2) + 1;
        MAXIMUM_POOL_SIZE = i2;
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(i, i2, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        MAIN_EXECUTOR = new LooperExecutor(Looper.getMainLooper());
        UI_HELPER_EXECUTOR = new LooperExecutor(createAndStartNewForegroundLooper("UiThreadHelper"));
        MODEL_EXECUTOR = new LooperExecutor(createAndStartNewLooper("launcher-loader"));
    }

    public static Looper createAndStartNewForegroundLooper(String str) {
        return createAndStartNewLooper(str, -2);
    }

    public static Looper createAndStartNewLooper(String str) {
        return createAndStartNewLooper(str, 0);
    }

    public static Looper createAndStartNewLooper(String str, int i) {
        HandlerThread handlerThread = new HandlerThread(str, i);
        handlerThread.start();
        return handlerThread.getLooper();
    }
}
