package com.google.common.util.concurrent.internal;

/* JADX INFO: loaded from: classes.dex */
public abstract class InternalFutures {
    public static Throwable tryInternalFastPathGetFailure(InternalFutureFailureAccess internalFutureFailureAccess) {
        return internalFutureFailureAccess.tryInternalFastPathGetFailure();
    }
}
