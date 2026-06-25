package com.android.systemui.util.kotlin;

import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes.dex */
public final class GlobalCoroutinesModule_ApplicationScopeFactory implements Factory {
    private final Provider dispatcherContextProvider;
    private final GlobalCoroutinesModule module;

    public GlobalCoroutinesModule_ApplicationScopeFactory(GlobalCoroutinesModule globalCoroutinesModule, Provider provider) {
        this.module = globalCoroutinesModule;
        this.dispatcherContextProvider = provider;
    }

    public static CoroutineScope applicationScope(GlobalCoroutinesModule globalCoroutinesModule, CoroutineContext coroutineContext) {
        CoroutineScope coroutineScopeApplicationScope = globalCoroutinesModule.applicationScope(coroutineContext);
        coroutineScopeApplicationScope.getClass();
        return coroutineScopeApplicationScope;
    }

    public static GlobalCoroutinesModule_ApplicationScopeFactory create(GlobalCoroutinesModule globalCoroutinesModule, Provider provider) {
        return new GlobalCoroutinesModule_ApplicationScopeFactory(globalCoroutinesModule, provider);
    }

    @Override // javax.inject.Provider
    public CoroutineScope get() {
        return applicationScope(this.module, (CoroutineContext) this.dispatcherContextProvider.get());
    }
}
