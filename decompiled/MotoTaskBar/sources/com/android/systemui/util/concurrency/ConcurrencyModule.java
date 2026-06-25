package com.android.systemui.util.concurrency;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public abstract class ConcurrencyModule {
    private static final Long BG_SLOW_DISPATCH_THRESHOLD = 1000L;
    private static final Long BG_SLOW_DELIVERY_THRESHOLD = 1000L;
    private static final Long LONG_SLOW_DISPATCH_THRESHOLD = 2500L;
    private static final Long LONG_SLOW_DELIVERY_THRESHOLD = 2500L;
    private static final Long BROADCAST_SLOW_DISPATCH_THRESHOLD = 1000L;
    private static final Long BROADCAST_SLOW_DELIVERY_THRESHOLD = 1000L;
    private static final Long NOTIFICATION_INFLATION_SLOW_DISPATCH_THRESHOLD = 1000L;
    private static final Long NOTIFICATION_INFLATION_SLOW_DELIVERY_THRESHOLD = 1000L;

    public static DelayableExecutor provideBackgroundDelayableExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public static Executor provideBackgroundExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public static RepeatableExecutor provideBackgroundRepeatableExecutor(DelayableExecutor delayableExecutor) {
        return new RepeatableExecutorImpl(delayableExecutor);
    }

    public static Handler provideBgHandler(Looper looper) {
        return new Handler(looper);
    }

    public static Looper provideBgLooper() {
        HandlerThread handlerThread = new HandlerThread("SysUiBg", 10);
        handlerThread.start();
        return handlerThread.getLooper();
    }

    public static Handler provideHandler() {
        return new Handler();
    }

    public static DelayableExecutor provideMainDelayableExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public static Executor provideMainExecutor(Context context) {
        return context.getMainExecutor();
    }

    public static Handler provideMainHandler(Looper looper) {
        return new Handler(looper);
    }

    public static Looper provideMainLooper() {
        return Looper.getMainLooper();
    }

    public static Executor provideNotifInflationExecutor(Looper looper) {
        return new ExecutorImpl(looper);
    }

    public static Looper provideNotifInflationLooper(Looper looper) {
        HandlerThread handlerThread = new HandlerThread("NotifInflation", 10);
        handlerThread.start();
        Looper looper2 = handlerThread.getLooper();
        looper2.setSlowLogThresholdMs(NOTIFICATION_INFLATION_SLOW_DISPATCH_THRESHOLD.longValue(), NOTIFICATION_INFLATION_SLOW_DELIVERY_THRESHOLD.longValue());
        return looper2;
    }

    public static Executor provideUiBackgroundExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
