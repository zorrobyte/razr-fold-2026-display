package com.motorola.plugin.core.components;

import com.motorola.plugin.core.discovery.LocalAppPluginDiscovery;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerComponentModule_Companion_ProvideLocalAppPluginDiscoveryFactory implements Factory {

    final class InstanceHolder {
        private static final PluginManagerComponentModule_Companion_ProvideLocalAppPluginDiscoveryFactory INSTANCE = new PluginManagerComponentModule_Companion_ProvideLocalAppPluginDiscoveryFactory();

        private InstanceHolder() {
        }
    }

    public static PluginManagerComponentModule_Companion_ProvideLocalAppPluginDiscoveryFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static LocalAppPluginDiscovery provideLocalAppPluginDiscovery() {
        LocalAppPluginDiscovery localAppPluginDiscoveryProvideLocalAppPluginDiscovery = PluginManagerComponentModule.Companion.provideLocalAppPluginDiscovery();
        localAppPluginDiscoveryProvideLocalAppPluginDiscovery.getClass();
        return localAppPluginDiscoveryProvideLocalAppPluginDiscovery;
    }

    @Override // javax.inject.Provider
    public LocalAppPluginDiscovery get() {
        return provideLocalAppPluginDiscovery();
    }
}
