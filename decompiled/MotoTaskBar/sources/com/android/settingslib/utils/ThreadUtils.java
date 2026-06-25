package com.android.settingslib.utils;

import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public abstract class ThreadUtils {
    private static volatile Thread sMainThread;

    public static boolean isMainThread() {
        if (sMainThread == null) {
            sMainThread = Looper.getMainLooper().getThread();
        }
        return Thread.currentThread() == sMainThread;
    }
}
