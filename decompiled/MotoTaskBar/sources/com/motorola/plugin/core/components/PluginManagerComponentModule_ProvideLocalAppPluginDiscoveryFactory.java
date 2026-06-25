package com.motorola.plugin.core.components;

import com.motorola.plugin.core.discovery.LocalAppPluginDiscovery;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerComponentModule_ProvideLocalAppPluginDiscoveryFactory implements Factory {

    final class InstanceHolder {
        private static final PluginManagerComponentModule_ProvideLocalAppPluginDiscoveryFactory INSTANCE = new PluginManagerComponentModule_ProvideLocalAppPluginDiscoveryFactory();

        private InstanceHolder() {
        }
    }

    public static PluginManagerComponentModule_ProvideLocalAppPluginDiscoveryFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static LocalAppPluginDiscovery provideLocalAppPluginDiscovery() {
        LocalAppPluginDiscovery localAppPluginDiscoveryProvideLocalAppPluginDiscovery = PluginManagerComponentModule.provideLocalAppPluginDiscovery();
        localAppPluginDiscoveryProvideLocalAppPluginDiscovery.getClass();
        return localAppPluginDiscoveryProvideLocalAppPluginDiscovery;
    }

    @Override // javax.inject.Provider
    public LocalAppPluginDiscovery get() {
        return provideLocalAppPluginDiscovery();
    }
}
