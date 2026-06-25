package com.motorola.plugin.core.discovery;

import android.content.Context;
import com.motorola.plugin.core.components.PluginInfoManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class ThirdPartyAppPluginDiscovery_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider pluginInfoManagerProvider;

    public ThirdPartyAppPluginDiscovery_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.pluginInfoManagerProvider = provider2;
    }

    public static ThirdPartyAppPluginDiscovery_Factory create(Provider provider, Provider provider2) {
        return new ThirdPartyAppPluginDiscovery_Factory(provider, provider2);
    }

    public static ThirdPartyAppPluginDiscovery newInstance(Context context, PluginInfoManager pluginInfoManager) {
        return new ThirdPartyAppPluginDiscovery(context, pluginInfoManager);
    }

    @Override // javax.inject.Provider
    public ThirdPartyAppPluginDiscovery get() {
        return newInstance((Context) this.contextProvider.get(), (PluginInfoManager) this.pluginInfoManagerProvider.get());
    }
}
