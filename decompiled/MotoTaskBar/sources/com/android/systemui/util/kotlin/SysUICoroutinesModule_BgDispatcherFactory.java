package com.android.systemui.util.kotlin;

import dagger.internal.Factory;
import kotlinx.coroutines.CoroutineDispatcher;

/* JADX INFO: loaded from: classes.dex */
public final class SysUICoroutinesModule_BgDispatcherFactory implements Factory {
    private final SysUICoroutinesModule module;

    public SysUICoroutinesModule_BgDispatcherFactory(SysUICoroutinesModule sysUICoroutinesModule) {
        this.module = sysUICoroutinesModule;
    }

    public static CoroutineDispatcher bgDispatcher(SysUICoroutinesModule sysUICoroutinesModule) {
        CoroutineDispatcher coroutineDispatcherBgDispatcher = sysUICoroutinesModule.bgDispatcher();
        coroutineDispatcherBgDispatcher.getClass();
        return coroutineDispatcherBgDispatcher;
    }

    public static SysUICoroutinesModule_BgDispatcherFactory create(SysUICoroutinesModule sysUICoroutinesModule) {
        return new SysUICoroutinesModule_BgDispatcherFactory(sysUICoroutinesModule);
    }

    @Override // javax.inject.Provider
    public CoroutineDispatcher get() {
        return bgDispatcher(this.module);
    }
}
