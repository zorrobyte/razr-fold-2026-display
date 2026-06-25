package com.android.systemui.util;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public abstract class Assert {
    private static final Looper sMainLooper = Looper.getMainLooper();
    private static Looper sTestLooper = null;

    public static void isMainThread() {
        Looper looper = sMainLooper;
        if (looper.isCurrentThread()) {
            return;
        }
        Looper looper2 = sTestLooper;
        if (looper2 == null || !looper2.isCurrentThread()) {
            throw new IllegalStateException("should be called from the main thread. sMainLooper.threadName=" + looper.getThread().getName() + " Thread.currentThread()=" + Thread.currentThread().getName());
        }
    }

    public static void isNotMainThread() {
        if (sMainLooper.isCurrentThread()) {
            Looper looper = sTestLooper;
            if (looper == null || looper.isCurrentThread()) {
                throw new IllegalStateException("should not be called from the main thread.");
            }
        }
    }

    public static void setTestableLooper(Looper looper) {
        sTestLooper = looper;
    }
}
