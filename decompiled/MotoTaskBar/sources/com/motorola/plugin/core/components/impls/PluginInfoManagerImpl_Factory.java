package com.motorola.plugin.core.components.impls;

import com.motorola.plugin.core.provider.InstalledPluginProvider;
import com.motorola.plugin.core.provider.RemotePluginProviderFactory;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginInfoManagerImpl_Factory implements Factory {
    private final Provider installedApkPluginProvider;
    private final Provider remotePluginProviderFactoryProvider;

    public PluginInfoManagerImpl_Factory(Provider provider, Provider provider2) {
        this.installedApkPluginProvider = provider;
        this.remotePluginProviderFactoryProvider = provider2;
    }

    public static PluginInfoManagerImpl_Factory create(Provider provider, Provider provider2) {
        return new PluginInfoManagerImpl_Factory(provider, provider2);
    }

    public static PluginInfoManagerImpl newInstance(InstalledPluginProvider installedPluginProvider, RemotePluginProviderFactory remotePluginProviderFactory) {
        return new PluginInfoManagerImpl(installedPluginProvider, remotePluginProviderFactory);
    }

    @Override // javax.inject.Provider
    public PluginInfoManagerImpl get() {
        return newInstance((InstalledPluginProvider) this.installedApkPluginProvider.get(), (RemotePluginProviderFactory) this.remotePluginProviderFactoryProvider.get());
    }
}
