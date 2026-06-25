package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideBgHandlerFactory implements Factory {
    private final Provider bgLooperProvider;

    public ConcurrencyModule_ProvideBgHandlerFactory(Provider provider) {
        this.bgLooperProvider = provider;
    }

    public static ConcurrencyModule_ProvideBgHandlerFactory create(Provider provider) {
        return new ConcurrencyModule_ProvideBgHandlerFactory(provider);
    }

    public static Handler provideBgHandler(Looper looper) {
        Handler handlerProvideBgHandler = ConcurrencyModule.provideBgHandler(looper);
        handlerProvideBgHandler.getClass();
        return handlerProvideBgHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideBgHandler((Looper) this.bgLooperProvider.get());
    }
}
