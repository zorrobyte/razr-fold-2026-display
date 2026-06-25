package com.motorola.plugin.core.components.impls;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginEventImpl_Factory implements Factory {
    private final Provider appContextProvider;

    public PluginEventImpl_Factory(Provider provider) {
        this.appContextProvider = provider;
    }

    public static PluginEventImpl_Factory create(Provider provider) {
        return new PluginEventImpl_Factory(provider);
    }

    public static PluginEventImpl newInstance(Context context) {
        return new PluginEventImpl(context);
    }

    @Override // javax.inject.Provider
    public PluginEventImpl get() {
        return newInstance((Context) this.appContextProvider.get());
    }
}
