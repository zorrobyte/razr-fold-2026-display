package com.motorola.plugin.core.components.impls;

import android.content.Context;
import com.motorola.plugin.core.components.PluginEnabler;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.discovery.PluginDiscovery;
import com.motorola.plugin.core.misc.DisposableContainer;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginSubscriberImpl_Factory implements Factory {
    private final Provider mContextProvider;
    private final Provider mDisposableProvider;
    private final Provider mPluginDiscoveryProvider;
    private final Provider mPluginEnablerProvider;
    private final Provider mPluginEventProvider;
    private final Provider mPluginInfoManagerProvider;
    private final Provider mPluginInstanceContainerProvider;

    public PluginSubscriberImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.mContextProvider = provider;
        this.mPluginEnablerProvider = provider2;
        this.mPluginEventProvider = provider3;
        this.mDisposableProvider = provider4;
        this.mPluginDiscoveryProvider = provider5;
        this.mPluginInfoManagerProvider = provider6;
        this.mPluginInstanceContainerProvider = provider7;
    }

    public static PluginSubscriberImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new PluginSubscriberImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static PluginSubscriberImpl newInstance(Context context, PluginEnabler pluginEnabler, PluginEvent pluginEvent, DisposableContainer disposableContainer, PluginDiscovery pluginDiscovery, Lazy lazy, Provider provider) {
        return new PluginSubscriberImpl(context, pluginEnabler, pluginEvent, disposableContainer, pluginDiscovery, lazy, provider);
    }

    @Override // javax.inject.Provider
    public PluginSubscriberImpl get() {
        return newInstance((Context) this.mContextProvider.get(), (PluginEnabler) this.mPluginEnablerProvider.get(), (PluginEvent) this.mPluginEventProvider.get(), (DisposableContainer) this.mDisposableProvider.get(), (PluginDiscovery) this.mPluginDiscoveryProvider.get(), DoubleCheck.lazy(this.mPluginInfoManagerProvider), this.mPluginInstanceContainerProvider);
    }
}
