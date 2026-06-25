package com.motorola.plugin.core.components.impls;

import android.content.Context;
import com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher;
import com.motorola.plugin.core.components.PackageEventMonitor;
import com.motorola.plugin.core.components.PluginEnabler;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginInfoManager;
import com.motorola.plugin.core.components.PluginListenerDispatcher;
import com.motorola.plugin.core.components.PluginProviderInfoProvider;
import com.motorola.plugin.core.components.PluginSubscriber;
import com.motorola.plugin.core.components.PluginWhitelistPolicyExt;
import com.motorola.plugin.core.discovery.PluginDiscovery;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.misc.DeviceState;
import com.motorola.plugin.core.misc.DisposableContainer;
import com.motorola.plugin.core.misc.PluginExceptionHandler;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerImpl_Factory implements Factory {
    private final Provider appContextProvider;
    private final Provider mConfigurationControllerProvider;
    private final Provider mConfigurationListenerChainedDispatcherProvider;
    private final Provider mDeviceStateProvider;
    private final Provider mDisposableProvider;
    private final Provider mExtensionControllerProvider;
    private final Provider mPackageEventMonitorProvider;
    private final Provider mPluginDiscoveryProvider;
    private final Provider mPluginEnablerProvider;
    private final Provider mPluginEventProvider;
    private final Provider mPluginExceptionHandlerProvider;
    private final Provider mPluginInfoManagerProvider;
    private final Provider mPluginListenerDispatcherProvider;
    private final Provider mPluginProviderInfoProvider;
    private final Provider mPluginSubscriberProvider;
    private final Provider mPluginWhitelistPolicyProvider;

    public PluginManagerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.appContextProvider = provider;
        this.mPluginWhitelistPolicyProvider = provider2;
        this.mPluginEnablerProvider = provider3;
        this.mPluginEventProvider = provider4;
        this.mPluginDiscoveryProvider = provider5;
        this.mPackageEventMonitorProvider = provider6;
        this.mPluginInfoManagerProvider = provider7;
        this.mPluginProviderInfoProvider = provider8;
        this.mExtensionControllerProvider = provider9;
        this.mPluginListenerDispatcherProvider = provider10;
        this.mPluginSubscriberProvider = provider11;
        this.mConfigurationControllerProvider = provider12;
        this.mConfigurationListenerChainedDispatcherProvider = provider13;
        this.mPluginExceptionHandlerProvider = provider14;
        this.mDeviceStateProvider = provider15;
        this.mDisposableProvider = provider16;
    }

    public static PluginManagerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        return new PluginManagerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16);
    }

    public static PluginManagerImpl newInstance(Context context, PluginWhitelistPolicyExt pluginWhitelistPolicyExt, PluginEnabler pluginEnabler, PluginEvent pluginEvent, PluginDiscovery pluginDiscovery, PackageEventMonitor packageEventMonitor, PluginInfoManager pluginInfoManager, PluginProviderInfoProvider pluginProviderInfoProvider, Lazy lazy, PluginListenerDispatcher pluginListenerDispatcher, PluginSubscriber pluginSubscriber, ConfigurationController configurationController, ConfigurationListenerChainedDispatcher configurationListenerChainedDispatcher, PluginExceptionHandler pluginExceptionHandler, DeviceState deviceState, DisposableContainer disposableContainer) {
        return new PluginManagerImpl(context, pluginWhitelistPolicyExt, pluginEnabler, pluginEvent, pluginDiscovery, packageEventMonitor, pluginInfoManager, pluginProviderInfoProvider, lazy, pluginListenerDispatcher, pluginSubscriber, configurationController, configurationListenerChainedDispatcher, pluginExceptionHandler, deviceState, disposableContainer);
    }

    @Override // javax.inject.Provider
    public PluginManagerImpl get() {
        return newInstance((Context) this.appContextProvider.get(), (PluginWhitelistPolicyExt) this.mPluginWhitelistPolicyProvider.get(), (PluginEnabler) this.mPluginEnablerProvider.get(), (PluginEvent) this.mPluginEventProvider.get(), (PluginDiscovery) this.mPluginDiscoveryProvider.get(), (PackageEventMonitor) this.mPackageEventMonitorProvider.get(), (PluginInfoManager) this.mPluginInfoManagerProvider.get(), (PluginProviderInfoProvider) this.mPluginProviderInfoProvider.get(), DoubleCheck.lazy(this.mExtensionControllerProvider), (PluginListenerDispatcher) this.mPluginListenerDispatcherProvider.get(), (PluginSubscriber) this.mPluginSubscriberProvider.get(), (ConfigurationController) this.mConfigurationControllerProvider.get(), (ConfigurationListenerChainedDispatcher) this.mConfigurationListenerChainedDispatcherProvider.get(), (PluginExceptionHandler) this.mPluginExceptionHandlerProvider.get(), (DeviceState) this.mDeviceStateProvider.get(), (DisposableContainer) this.mDisposableProvider.get());
    }
}
