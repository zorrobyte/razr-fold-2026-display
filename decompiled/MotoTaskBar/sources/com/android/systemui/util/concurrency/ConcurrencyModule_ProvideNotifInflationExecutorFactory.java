package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideNotifInflationExecutorFactory implements Factory {
    private final Provider looperProvider;

    public ConcurrencyModule_ProvideNotifInflationExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static ConcurrencyModule_ProvideNotifInflationExecutorFactory create(Provider provider) {
        return new ConcurrencyModule_ProvideNotifInflationExecutorFactory(provider);
    }

    public static Executor provideNotifInflationExecutor(Looper looper) {
        Executor executorProvideNotifInflationExecutor = ConcurrencyModule.provideNotifInflationExecutor(looper);
        executorProvideNotifInflationExecutor.getClass();
        return executorProvideNotifInflationExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideNotifInflationExecutor((Looper) this.looperProvider.get());
    }
}
