package com.motorola.plugin.core.components.impls;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginEnablerImpl_Factory implements Factory {
    private final Provider contextProvider;

    public PluginEnablerImpl_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PluginEnablerImpl_Factory create(Provider provider) {
        return new PluginEnablerImpl_Factory(provider);
    }

    public static PluginEnablerImpl newInstance(Context context) {
        return new PluginEnablerImpl(context);
    }

    @Override // javax.inject.Provider
    public PluginEnablerImpl get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
