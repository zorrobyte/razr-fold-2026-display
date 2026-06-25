package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideMainDelayableExecutorFactory implements Factory {
    private final Provider looperProvider;

    public ConcurrencyModule_ProvideMainDelayableExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static ConcurrencyModule_ProvideMainDelayableExecutorFactory create(Provider provider) {
        return new ConcurrencyModule_ProvideMainDelayableExecutorFactory(provider);
    }

    public static DelayableExecutor provideMainDelayableExecutor(Looper looper) {
        DelayableExecutor delayableExecutorProvideMainDelayableExecutor = ConcurrencyModule.provideMainDelayableExecutor(looper);
        delayableExecutorProvideMainDelayableExecutor.getClass();
        return delayableExecutorProvideMainDelayableExecutor;
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideMainDelayableExecutor((Looper) this.looperProvider.get());
    }
}
