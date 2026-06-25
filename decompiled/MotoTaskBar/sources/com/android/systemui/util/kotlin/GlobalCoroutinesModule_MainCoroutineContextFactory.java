package com.android.systemui.util.kotlin;

import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;

/* JADX INFO: loaded from: classes.dex */
public final class GlobalCoroutinesModule_MainCoroutineContextFactory implements Factory {
    private final GlobalCoroutinesModule module;
    private final Provider tracingCoroutineContextProvider;

    public GlobalCoroutinesModule_MainCoroutineContextFactory(GlobalCoroutinesModule globalCoroutinesModule, Provider provider) {
        this.module = globalCoroutinesModule;
        this.tracingCoroutineContextProvider = provider;
    }

    public static GlobalCoroutinesModule_MainCoroutineContextFactory create(GlobalCoroutinesModule globalCoroutinesModule, Provider provider) {
        return new GlobalCoroutinesModule_MainCoroutineContextFactory(globalCoroutinesModule, provider);
    }

    public static CoroutineContext mainCoroutineContext(GlobalCoroutinesModule globalCoroutinesModule, CoroutineContext coroutineContext) {
        CoroutineContext coroutineContextMainCoroutineContext = globalCoroutinesModule.mainCoroutineContext(coroutineContext);
        coroutineContextMainCoroutineContext.getClass();
        return coroutineContextMainCoroutineContext;
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return mainCoroutineContext(this.module, (CoroutineContext) this.tracingCoroutineContextProvider.get());
    }
}
