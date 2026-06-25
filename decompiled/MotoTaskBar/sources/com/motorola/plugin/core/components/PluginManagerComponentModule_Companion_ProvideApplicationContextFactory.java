package com.motorola.plugin.core.components;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerComponentModule_Companion_ProvideApplicationContextFactory implements Factory {
    private final Provider oProvider;

    public PluginManagerComponentModule_Companion_ProvideApplicationContextFactory(Provider provider) {
        this.oProvider = provider;
    }

    public static PluginManagerComponentModule_Companion_ProvideApplicationContextFactory create(Provider provider) {
        return new PluginManagerComponentModule_Companion_ProvideApplicationContextFactory(provider);
    }

    public static Context provideApplicationContext(Context context) {
        Context contextProvideApplicationContext = PluginManagerComponentModule.Companion.provideApplicationContext(context);
        contextProvideApplicationContext.getClass();
        return contextProvideApplicationContext;
    }

    @Override // javax.inject.Provider
    public Context get() {
        return provideApplicationContext((Context) this.oProvider.get());
    }
}
