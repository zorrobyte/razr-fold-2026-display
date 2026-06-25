package com.motorola.plugin.core.components;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerComponentModule_Companion_ProvideDisplayContextFactory implements Factory {
    private final Provider oProvider;

    public PluginManagerComponentModule_Companion_ProvideDisplayContextFactory(Provider provider) {
        this.oProvider = provider;
    }

    public static PluginManagerComponentModule_Companion_ProvideDisplayContextFactory create(Provider provider) {
        return new PluginManagerComponentModule_Companion_ProvideDisplayContextFactory(provider);
    }

    public static Context provideDisplayContext(Context context) {
        Context contextProvideDisplayContext = PluginManagerComponentModule.Companion.provideDisplayContext(context);
        contextProvideDisplayContext.getClass();
        return contextProvideDisplayContext;
    }

    @Override // javax.inject.Provider
    public Context get() {
        return provideDisplayContext((Context) this.oProvider.get());
    }
}
