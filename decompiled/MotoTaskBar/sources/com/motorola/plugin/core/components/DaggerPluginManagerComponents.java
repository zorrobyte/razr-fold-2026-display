package com.motorola.plugin.core.components;

import android.content.Context;
import com.motorola.plugin.core.components.PluginManagerComponents;
import com.motorola.plugin.core.components.impls.ConfigurationListenerChainedDispatcherImpl_Factory;
import com.motorola.plugin.core.components.impls.DummyPluginManager_Factory;
import com.motorola.plugin.core.components.impls.PackageEventMonitorImpl_Factory;
import com.motorola.plugin.core.components.impls.PluginEnablerImpl_Factory;
import com.motorola.plugin.core.components.impls.PluginEventImpl_Factory;
import com.motorola.plugin.core.components.impls.PluginInfoManagerImpl_Factory;
import com.motorola.plugin.core.components.impls.PluginListenerDispatcherImpl_Factory;
import com.motorola.plugin.core.components.impls.PluginManagerImpl_Factory;
import com.motorola.plugin.core.components.impls.PluginProviderInfoProviderImpl_Factory;
import com.motorola.plugin.core.components.impls.PluginSubscriberImpl_Factory;
import com.motorola.plugin.core.components.impls.PluginWhitelistPolicyImpl_Factory;
import com.motorola.plugin.core.container.PluginInstanceContainerImpl_Factory;
import com.motorola.plugin.core.discovery.ThirdPartyAppPluginDiscovery_Factory;
import com.motorola.plugin.core.extension.ConfigurationControllerImpl_Factory;
import com.motorola.plugin.core.extension.ExtensionControllerImpl_Factory;
import com.motorola.plugin.core.misc.DeviceState_Factory;
import com.motorola.plugin.core.misc.PluginExceptionHandler_Factory;
import com.motorola.plugin.core.provider.InstalledPluginProvider_Factory;
import com.motorola.plugin.core.provider.RemotePluginProviderFactory_Factory;
import com.motorola.plugin.core.remote.RemotePluginManagerStub_Factory;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class DaggerPluginManagerComponents {

    final class Factory implements PluginManagerComponents.Factory {
        private Factory() {
        }

        @Override // com.motorola.plugin.core.components.PluginManagerComponents.Factory
        public PluginManagerComponents create(Context context) {
            context.getClass();
            return new PluginManagerComponentsImpl(context);
        }
    }

    final class PluginManagerComponentsImpl implements PluginManagerComponents {
        private Provider bindConfigurationControllerProvider;
        private Provider bindConfigurationListenerDispatcherProvider;
        private Provider bindExtensionControllerProvider;
        private Provider bindMockPluginManagerProvider;
        private Provider bindPackageEventMonitorProvider;
        private Provider bindPluginEnablerProvider;
        private Provider bindPluginEventProvider;
        private Provider bindPluginInfoManagerProvider;
        private Provider bindPluginListenerDispatcherProvider;
        private Provider bindPluginManagerProvider;
        private Provider bindPluginProviderInfoProvider;
        private Provider bindPluginSubscriberProvider;
        private Provider bindPluginWhiteListPolicyProvider;
        private Provider bindRemotePluginManagerProvider;
        private Provider configurationControllerImplProvider;
        private Provider contextProvider;
        private Provider deviceStateProvider;
        private Provider dummyPluginManagerProvider;
        private Provider extensionControllerImplProvider;
        private Provider installedPluginProvider;
        private Provider packageEventMonitorImplProvider;
        private Provider pluginEnablerImplProvider;
        private Provider pluginEventImplProvider;
        private Provider pluginExceptionHandlerProvider;
        private Provider pluginInfoManagerImplProvider;
        private Provider pluginInstanceContainerImplProvider;
        private final PluginManagerComponentsImpl pluginManagerComponentsImpl;
        private Provider pluginManagerImplProvider;
        private Provider pluginProviderInfoProviderImplProvider;
        private Provider pluginSubscriberImplProvider;
        private Provider pluginWhitelistPolicyImplProvider;
        private Provider provideApplicationContextProvider;
        private Provider provideDisplayContextProvider;
        private Provider providePluginDiscoveryProvider;
        private Provider thirdPartyAppPluginDiscoveryProvider;

        private PluginManagerComponentsImpl(Context context) {
            this.pluginManagerComponentsImpl = this;
            initialize(context);
        }

        private void initialize(Context context) {
            dagger.internal.Factory factoryCreate = InstanceFactory.create(context);
            this.contextProvider = factoryCreate;
            PluginManagerComponentModule_ProvideApplicationContextFactory pluginManagerComponentModule_ProvideApplicationContextFactoryCreate = PluginManagerComponentModule_ProvideApplicationContextFactory.create(factoryCreate);
            this.provideApplicationContextProvider = pluginManagerComponentModule_ProvideApplicationContextFactoryCreate;
            PluginWhitelistPolicyImpl_Factory pluginWhitelistPolicyImpl_FactoryCreate = PluginWhitelistPolicyImpl_Factory.create(pluginManagerComponentModule_ProvideApplicationContextFactoryCreate);
            this.pluginWhitelistPolicyImplProvider = pluginWhitelistPolicyImpl_FactoryCreate;
            this.bindPluginWhiteListPolicyProvider = DoubleCheck.provider((Provider) pluginWhitelistPolicyImpl_FactoryCreate);
            PluginEnablerImpl_Factory pluginEnablerImpl_FactoryCreate = PluginEnablerImpl_Factory.create(this.provideApplicationContextProvider);
            this.pluginEnablerImplProvider = pluginEnablerImpl_FactoryCreate;
            this.bindPluginEnablerProvider = DoubleCheck.provider((Provider) pluginEnablerImpl_FactoryCreate);
            PluginEventImpl_Factory pluginEventImpl_FactoryCreate = PluginEventImpl_Factory.create(this.provideApplicationContextProvider);
            this.pluginEventImplProvider = pluginEventImpl_FactoryCreate;
            this.bindPluginEventProvider = DoubleCheck.provider((Provider) pluginEventImpl_FactoryCreate);
            Provider provider = DoubleCheck.provider((Provider) PluginManagerComponentModule_ProvideDisplayContextFactory.create(this.contextProvider));
            this.provideDisplayContextProvider = provider;
            ConfigurationControllerImpl_Factory configurationControllerImpl_FactoryCreate = ConfigurationControllerImpl_Factory.create(provider);
            this.configurationControllerImplProvider = configurationControllerImpl_FactoryCreate;
            Provider provider2 = DoubleCheck.provider((Provider) configurationControllerImpl_FactoryCreate);
            this.bindConfigurationControllerProvider = provider2;
            InstalledPluginProvider_Factory installedPluginProvider_FactoryCreate = InstalledPluginProvider_Factory.create(this.provideDisplayContextProvider, this.bindPluginWhiteListPolicyProvider, provider2, this.bindPluginEnablerProvider, this.bindPluginEventProvider);
            this.installedPluginProvider = installedPluginProvider_FactoryCreate;
            PluginInfoManagerImpl_Factory pluginInfoManagerImpl_FactoryCreate = PluginInfoManagerImpl_Factory.create(installedPluginProvider_FactoryCreate, RemotePluginProviderFactory_Factory.create());
            this.pluginInfoManagerImplProvider = pluginInfoManagerImpl_FactoryCreate;
            Provider provider3 = DoubleCheck.provider((Provider) pluginInfoManagerImpl_FactoryCreate);
            this.bindPluginInfoManagerProvider = provider3;
            ThirdPartyAppPluginDiscovery_Factory thirdPartyAppPluginDiscovery_FactoryCreate = ThirdPartyAppPluginDiscovery_Factory.create(this.provideApplicationContextProvider, provider3);
            this.thirdPartyAppPluginDiscoveryProvider = thirdPartyAppPluginDiscovery_FactoryCreate;
            this.providePluginDiscoveryProvider = DoubleCheck.provider((Provider) PluginManagerComponentModule_ProvidePluginDiscoveryFactory.create(thirdPartyAppPluginDiscovery_FactoryCreate, PluginManagerComponentModule_ProvideLocalAppPluginDiscoveryFactory.create()));
            Provider provider4 = DoubleCheck.provider((Provider) RemotePluginManagerStub_Factory.create());
            this.bindRemotePluginManagerProvider = provider4;
            PackageEventMonitorImpl_Factory packageEventMonitorImpl_FactoryCreate = PackageEventMonitorImpl_Factory.create(this.provideApplicationContextProvider, provider4, PluginManagerComponentModule_BindDisposableContainerFactory.create());
            this.packageEventMonitorImplProvider = packageEventMonitorImpl_FactoryCreate;
            this.bindPackageEventMonitorProvider = DoubleCheck.provider((Provider) packageEventMonitorImpl_FactoryCreate);
            PluginProviderInfoProviderImpl_Factory pluginProviderInfoProviderImpl_FactoryCreate = PluginProviderInfoProviderImpl_Factory.create(this.bindPluginInfoManagerProvider);
            this.pluginProviderInfoProviderImplProvider = pluginProviderInfoProviderImpl_FactoryCreate;
            this.bindPluginProviderInfoProvider = DoubleCheck.provider((Provider) pluginProviderInfoProviderImpl_FactoryCreate);
            DelegateFactory delegateFactory = new DelegateFactory();
            this.bindPluginManagerProvider = delegateFactory;
            ExtensionControllerImpl_Factory extensionControllerImpl_FactoryCreate = ExtensionControllerImpl_Factory.create(this.provideDisplayContextProvider, delegateFactory, this.bindConfigurationControllerProvider);
            this.extensionControllerImplProvider = extensionControllerImpl_FactoryCreate;
            this.bindExtensionControllerProvider = DoubleCheck.provider((Provider) extensionControllerImpl_FactoryCreate);
            this.bindPluginListenerDispatcherProvider = DoubleCheck.provider((Provider) PluginListenerDispatcherImpl_Factory.create());
            Provider provider5 = DoubleCheck.provider((Provider) DeviceState_Factory.create(this.provideApplicationContextProvider));
            this.deviceStateProvider = provider5;
            this.pluginInstanceContainerImplProvider = PluginInstanceContainerImpl_Factory.create(this.provideDisplayContextProvider, this.bindPluginEventProvider, this.bindPluginEnablerProvider, this.bindPluginWhiteListPolicyProvider, this.bindPluginListenerDispatcherProvider, provider5);
            PluginSubscriberImpl_Factory pluginSubscriberImpl_FactoryCreate = PluginSubscriberImpl_Factory.create(this.provideDisplayContextProvider, this.bindPluginEnablerProvider, this.bindPluginEventProvider, PluginManagerComponentModule_BindDisposableContainerFactory.create(), this.providePluginDiscoveryProvider, this.bindPluginInfoManagerProvider, this.pluginInstanceContainerImplProvider);
            this.pluginSubscriberImplProvider = pluginSubscriberImpl_FactoryCreate;
            this.bindPluginSubscriberProvider = DoubleCheck.provider((Provider) pluginSubscriberImpl_FactoryCreate);
            this.bindConfigurationListenerDispatcherProvider = DoubleCheck.provider((Provider) ConfigurationListenerChainedDispatcherImpl_Factory.create());
            PluginExceptionHandler_Factory pluginExceptionHandler_FactoryCreate = PluginExceptionHandler_Factory.create(this.bindPluginEventProvider, this.bindPluginSubscriberProvider);
            this.pluginExceptionHandlerProvider = pluginExceptionHandler_FactoryCreate;
            PluginManagerImpl_Factory pluginManagerImpl_FactoryCreate = PluginManagerImpl_Factory.create(this.provideApplicationContextProvider, this.bindPluginWhiteListPolicyProvider, this.bindPluginEnablerProvider, this.bindPluginEventProvider, this.providePluginDiscoveryProvider, this.bindPackageEventMonitorProvider, this.bindPluginInfoManagerProvider, this.bindPluginProviderInfoProvider, this.bindExtensionControllerProvider, this.bindPluginListenerDispatcherProvider, this.bindPluginSubscriberProvider, this.bindConfigurationControllerProvider, this.bindConfigurationListenerDispatcherProvider, pluginExceptionHandler_FactoryCreate, this.deviceStateProvider, PluginManagerComponentModule_BindDisposableContainerFactory.create());
            this.pluginManagerImplProvider = pluginManagerImpl_FactoryCreate;
            DelegateFactory.setDelegate(this.bindPluginManagerProvider, DoubleCheck.provider((Provider) pluginManagerImpl_FactoryCreate));
            DummyPluginManager_Factory dummyPluginManager_FactoryCreate = DummyPluginManager_Factory.create(this.contextProvider);
            this.dummyPluginManagerProvider = dummyPluginManager_FactoryCreate;
            this.bindMockPluginManagerProvider = DoubleCheck.provider((Provider) dummyPluginManager_FactoryCreate);
        }

        @Override // com.motorola.plugin.core.components.PluginManagerComponents
        public PluginManager mockManager() {
            return (PluginManager) this.bindMockPluginManagerProvider.get();
        }

        @Override // com.motorola.plugin.core.components.PluginManagerComponents
        public PluginManager newPluginManager() {
            return (PluginManager) this.bindPluginManagerProvider.get();
        }
    }

    private DaggerPluginManagerComponents() {
    }

    public static PluginManagerComponents.Factory factory() {
        return new Factory();
    }
}
