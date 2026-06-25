package com.android.systemui;

import android.content.Context;
import com.android.systemui.SystemUIFactory;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class SystemUIFactory_ContextHolder_ProvideContextFactory implements Factory {
    private final SystemUIFactory.ContextHolder module;

    public SystemUIFactory_ContextHolder_ProvideContextFactory(SystemUIFactory.ContextHolder contextHolder) {
        this.module = contextHolder;
    }

    public static SystemUIFactory_ContextHolder_ProvideContextFactory create(SystemUIFactory.ContextHolder contextHolder) {
        return new SystemUIFactory_ContextHolder_ProvideContextFactory(contextHolder);
    }

    public static Context provideContext(SystemUIFactory.ContextHolder contextHolder) {
        Context contextProvideContext = contextHolder.provideContext();
        contextProvideContext.getClass();
        return contextProvideContext;
    }

    @Override // javax.inject.Provider
    public Context get() {
        return provideContext(this.module);
    }
}
