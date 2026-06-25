package com.android.systemui.dagger;

import android.os.Handler;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class DependencyProvider_ProvideTimeTickHandlerFactory implements Factory {
    private final DependencyProvider module;

    public DependencyProvider_ProvideTimeTickHandlerFactory(DependencyProvider dependencyProvider) {
        this.module = dependencyProvider;
    }

    public static DependencyProvider_ProvideTimeTickHandlerFactory create(DependencyProvider dependencyProvider) {
        return new DependencyProvider_ProvideTimeTickHandlerFactory(dependencyProvider);
    }

    public static Handler provideTimeTickHandler(DependencyProvider dependencyProvider) {
        Handler handlerProvideTimeTickHandler = dependencyProvider.provideTimeTickHandler();
        handlerProvideTimeTickHandler.getClass();
        return handlerProvideTimeTickHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideTimeTickHandler(this.module);
    }
}
