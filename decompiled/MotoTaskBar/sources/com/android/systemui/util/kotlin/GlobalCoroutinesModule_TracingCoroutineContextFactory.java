package com.android.systemui.util.kotlin;

import dagger.internal.Factory;
import kotlin.coroutines.CoroutineContext;

/* JADX INFO: loaded from: classes.dex */
public final class GlobalCoroutinesModule_TracingCoroutineContextFactory implements Factory {
    private final GlobalCoroutinesModule module;

    public GlobalCoroutinesModule_TracingCoroutineContextFactory(GlobalCoroutinesModule globalCoroutinesModule) {
        this.module = globalCoroutinesModule;
    }

    public static GlobalCoroutinesModule_TracingCoroutineContextFactory create(GlobalCoroutinesModule globalCoroutinesModule) {
        return new GlobalCoroutinesModule_TracingCoroutineContextFactory(globalCoroutinesModule);
    }

    public static CoroutineContext tracingCoroutineContext(GlobalCoroutinesModule globalCoroutinesModule) {
        CoroutineContext coroutineContextTracingCoroutineContext = globalCoroutinesModule.tracingCoroutineContext();
        coroutineContextTracingCoroutineContext.getClass();
        return coroutineContextTracingCoroutineContext;
    }

    @Override // javax.inject.Provider
    public CoroutineContext get() {
        return tracingCoroutineContext(this.module);
    }
}
