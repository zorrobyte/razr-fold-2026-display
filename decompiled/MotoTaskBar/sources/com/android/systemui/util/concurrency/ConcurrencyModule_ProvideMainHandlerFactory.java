package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideMainHandlerFactory implements Factory {
    private final Provider mainLooperProvider;

    public ConcurrencyModule_ProvideMainHandlerFactory(Provider provider) {
        this.mainLooperProvider = provider;
    }

    public static ConcurrencyModule_ProvideMainHandlerFactory create(Provider provider) {
        return new ConcurrencyModule_ProvideMainHandlerFactory(provider);
    }

    public static Handler provideMainHandler(Looper looper) {
        Handler handlerProvideMainHandler = ConcurrencyModule.provideMainHandler(looper);
        handlerProvideMainHandler.getClass();
        return handlerProvideMainHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideMainHandler((Looper) this.mainLooperProvider.get());
    }
}
