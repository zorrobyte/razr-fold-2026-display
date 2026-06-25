package com.android.systemui.util.kotlin;

import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* JADX INFO: loaded from: classes.dex */
public final class SysUICoroutinesModule_BgCoroutineContextFactory implements Factory {
    private final Provider bgCoroutineDispatcherProvider;
    private final SysUICoroutinesModule module;
    private final Provider tracingCoroutineContextProvider;

    public SysUICoroutinesModule_BgCoroutineContextFactory(SysUICoroutinesModule sysUICoroutinesModule, Provider provider, Provider provider2) {
        this.module = sysUICoroutinesModule;
        this.tracingCoroutineContextProvider = provider;
        this.bgCoroutineDispatcherProvider = provider2;
    }

    public static CoroutineContext bgCoroutineContext(SysUICoroutinesModule sysUICoroutinesModule, CoroutineContext coroutineContext, CoroutineDispatcher coroutineDispatcher) {
        CoroutineContext coroutineContextBgCoroutineContext = sysUICoroutinesModule.bgCoroutineContext(coroutineContext, coroutineDispatcher);
        coroutineContextBgCoroutineContext.getClass();
        return coroutineContextBgCoroutineContext;
    }

    public static SysUICoroutinesModule_BgCoroutineContextFactory create(SysUICoroutinesModule sysUICoroutinesModule, Provider provider, Provider provider2) {
        return new SysUICoroutinesModule_BgCoroutineContextFactory(sysUICoroutinesModule, provider, provider2);
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return bgCoroutineContext(this.module, (CoroutineContext) this.tracingCoroutineContextProvider.get(), (CoroutineDispatcher) this.bgCoroutineDispatcherProvider.get());
    }
}
