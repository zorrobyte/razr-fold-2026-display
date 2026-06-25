package com.android.systemui.util.concurrency;

import android.os.Handler;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideHandlerFactory implements Factory {

    abstract class InstanceHolder {
        static final ConcurrencyModule_ProvideHandlerFactory INSTANCE = new ConcurrencyModule_ProvideHandlerFactory();
    }

    public static ConcurrencyModule_ProvideHandlerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Handler provideHandler() {
        Handler handlerProvideHandler = ConcurrencyModule.provideHandler();
        handlerProvideHandler.getClass();
        return handlerProvideHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideHandler();
    }
}
