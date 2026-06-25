package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideBackgroundDelayableExecutorFactory implements Factory {
    private final Provider looperProvider;

    public ConcurrencyModule_ProvideBackgroundDelayableExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static ConcurrencyModule_ProvideBackgroundDelayableExecutorFactory create(Provider provider) {
        return new ConcurrencyModule_ProvideBackgroundDelayableExecutorFactory(provider);
    }

    public static DelayableExecutor provideBackgroundDelayableExecutor(Looper looper) {
        DelayableExecutor delayableExecutorProvideBackgroundDelayableExecutor = ConcurrencyModule.provideBackgroundDelayableExecutor(looper);
        delayableExecutorProvideBackgroundDelayableExecutor.getClass();
        return delayableExecutorProvideBackgroundDelayableExecutor;
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideBackgroundDelayableExecutor((Looper) this.looperProvider.get());
    }
}
