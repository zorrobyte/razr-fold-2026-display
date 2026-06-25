package com.google.common.util.concurrent;

/* JADX INFO: loaded from: classes.dex */
abstract class Platform {
    static void restoreInterruptIfIsInterruptedException(Throwable th) {
        th.getClass();
        if (th instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
    }
}
