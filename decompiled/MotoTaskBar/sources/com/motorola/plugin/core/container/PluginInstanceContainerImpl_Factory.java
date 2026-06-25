package com.motorola.plugin.core.container;

import android.content.Context;
import com.motorola.plugin.core.components.PluginEnabler;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginListenerDispatcher;
import com.motorola.plugin.core.components.PluginWhitelistPolicyExt;
import com.motorola.plugin.core.misc.DeviceState;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginInstanceContainerImpl_Factory implements Factory {
    private final Provider mContextProvider;
    private final Provider mDeviceStateProvider;
    private final Provider mPluginEnablerProvider;
    private final Provider mPluginEventProvider;
    private final Provider mPluginListenerDispatcherProvider;
    private final Provider mPluginWhitelistPolicyProvider;

    public PluginInstanceContainerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.mContextProvider = provider;
        this.mPluginEventProvider = provider2;
        this.mPluginEnablerProvider = provider3;
        this.mPluginWhitelistPolicyProvider = provider4;
        this.mPluginListenerDispatcherProvider = provider5;
        this.mDeviceStateProvider = provider6;
    }

    public static PluginInstanceContainerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new PluginInstanceContainerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static PluginInstanceContainerImpl newInstance(Context context, PluginEvent pluginEvent, PluginEnabler pluginEnabler, PluginWhitelistPolicyExt pluginWhitelistPolicyExt, PluginListenerDispatcher pluginListenerDispatcher, DeviceState deviceState) {
        return new PluginInstanceContainerImpl(context, pluginEvent, pluginEnabler, pluginWhitelistPolicyExt, pluginListenerDispatcher, deviceState);
    }

    @Override // javax.inject.Provider
    public PluginInstanceContainerImpl get() {
        return newInstance((Context) this.mContextProvider.get(), (PluginEvent) this.mPluginEventProvider.get(), (PluginEnabler) this.mPluginEnablerProvider.get(), (PluginWhitelistPolicyExt) this.mPluginWhitelistPolicyProvider.get(), (PluginListenerDispatcher) this.mPluginListenerDispatcherProvider.get(), (DeviceState) this.mDeviceStateProvider.get());
    }
}
