package com.android.systemui.util.concurrency;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideMainExecutorFactory implements Factory {
    private final Provider contextProvider;

    public ConcurrencyModule_ProvideMainExecutorFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static ConcurrencyModule_ProvideMainExecutorFactory create(Provider provider) {
        return new ConcurrencyModule_ProvideMainExecutorFactory(provider);
    }

    public static Executor provideMainExecutor(Context context) {
        Executor executorProvideMainExecutor = ConcurrencyModule.provideMainExecutor(context);
        executorProvideMainExecutor.getClass();
        return executorProvideMainExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideMainExecutor((Context) this.contextProvider.get());
    }
}
