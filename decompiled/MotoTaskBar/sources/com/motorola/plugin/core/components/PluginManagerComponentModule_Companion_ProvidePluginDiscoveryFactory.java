package com.motorola.plugin.core.components;

import com.motorola.plugin.core.discovery.LocalAppPluginDiscovery;
import com.motorola.plugin.core.discovery.PluginDiscovery;
import com.motorola.plugin.core.discovery.ThirdPartyAppPluginDiscovery;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerComponentModule_Companion_ProvidePluginDiscoveryFactory implements Factory {
    private final Provider o1Provider;
    private final Provider oProvider;

    public PluginManagerComponentModule_Companion_ProvidePluginDiscoveryFactory(Provider provider, Provider provider2) {
        this.oProvider = provider;
        this.o1Provider = provider2;
    }

    public static PluginManagerComponentModule_Companion_ProvidePluginDiscoveryFactory create(Provider provider, Provider provider2) {
        return new PluginManagerComponentModule_Companion_ProvidePluginDiscoveryFactory(provider, provider2);
    }

    public static PluginDiscovery providePluginDiscovery(ThirdPartyAppPluginDiscovery thirdPartyAppPluginDiscovery, LocalAppPluginDiscovery localAppPluginDiscovery) {
        PluginDiscovery pluginDiscoveryProvidePluginDiscovery = PluginManagerComponentModule.Companion.providePluginDiscovery(thirdPartyAppPluginDiscovery, localAppPluginDiscovery);
        pluginDiscoveryProvidePluginDiscovery.getClass();
        return pluginDiscoveryProvidePluginDiscovery;
    }

    @Override // javax.inject.Provider
    public PluginDiscovery get() {
        return providePluginDiscovery((ThirdPartyAppPluginDiscovery) this.oProvider.get(), (LocalAppPluginDiscovery) this.o1Provider.get());
    }
}
