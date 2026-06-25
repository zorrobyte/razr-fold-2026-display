package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideBackgroundExecutorFactory implements Factory {
    private final Provider looperProvider;

    public ConcurrencyModule_ProvideBackgroundExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static ConcurrencyModule_ProvideBackgroundExecutorFactory create(Provider provider) {
        return new ConcurrencyModule_ProvideBackgroundExecutorFactory(provider);
    }

    public static Executor provideBackgroundExecutor(Looper looper) {
        Executor executorProvideBackgroundExecutor = ConcurrencyModule.provideBackgroundExecutor(looper);
        executorProvideBackgroundExecutor.getClass();
        return executorProvideBackgroundExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideBackgroundExecutor((Looper) this.looperProvider.get());
    }
}
