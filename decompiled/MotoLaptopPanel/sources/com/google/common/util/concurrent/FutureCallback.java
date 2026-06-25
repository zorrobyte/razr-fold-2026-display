package com.google.common.util.concurrent;

/* JADX INFO: loaded from: classes.dex */
public interface FutureCallback {
    void onFailure(Throwable th);

    void onSuccess(Object obj);
}
