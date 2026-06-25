package com.android.systemui.util.concurrency;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory implements Factory {
    private final Provider execProvider;

    public ConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory(Provider provider) {
        this.execProvider = provider;
    }

    public static ConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory create(Provider provider) {
        return new ConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory(provider);
    }

    public static RepeatableExecutor provideBackgroundRepeatableExecutor(DelayableExecutor delayableExecutor) {
        RepeatableExecutor repeatableExecutorProvideBackgroundRepeatableExecutor = ConcurrencyModule.provideBackgroundRepeatableExecutor(delayableExecutor);
        repeatableExecutorProvideBackgroundRepeatableExecutor.getClass();
        return repeatableExecutorProvideBackgroundRepeatableExecutor;
    }

    @Override // javax.inject.Provider
    public RepeatableExecutor get() {
        return provideBackgroundRepeatableExecutor((DelayableExecutor) this.execProvider.get());
    }
}
