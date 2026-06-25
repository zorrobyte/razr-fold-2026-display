package com.motorola.plugin.core.components.impls;

import com.motorola.plugin.core.components.PluginInfoManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginProviderInfoProviderImpl_Factory implements Factory {
    private final Provider pluginInfoManagerProvider;

    public PluginProviderInfoProviderImpl_Factory(Provider provider) {
        this.pluginInfoManagerProvider = provider;
    }

    public static PluginProviderInfoProviderImpl_Factory create(Provider provider) {
        return new PluginProviderInfoProviderImpl_Factory(provider);
    }

    public static PluginProviderInfoProviderImpl newInstance(PluginInfoManager pluginInfoManager) {
        return new PluginProviderInfoProviderImpl(pluginInfoManager);
    }

    @Override // javax.inject.Provider
    public PluginProviderInfoProviderImpl get() {
        return newInstance((PluginInfoManager) this.pluginInfoManagerProvider.get());
    }
}
