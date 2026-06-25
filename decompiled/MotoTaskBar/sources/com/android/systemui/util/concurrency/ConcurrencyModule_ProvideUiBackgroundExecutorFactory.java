package com.android.systemui.util.concurrency;

import dagger.internal.Factory;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideUiBackgroundExecutorFactory implements Factory {

    abstract class InstanceHolder {
        static final ConcurrencyModule_ProvideUiBackgroundExecutorFactory INSTANCE = new ConcurrencyModule_ProvideUiBackgroundExecutorFactory();
    }

    public static ConcurrencyModule_ProvideUiBackgroundExecutorFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Executor provideUiBackgroundExecutor() {
        Executor executorProvideUiBackgroundExecutor = ConcurrencyModule.provideUiBackgroundExecutor();
        executorProvideUiBackgroundExecutor.getClass();
        return executorProvideUiBackgroundExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideUiBackgroundExecutor();
    }
}
