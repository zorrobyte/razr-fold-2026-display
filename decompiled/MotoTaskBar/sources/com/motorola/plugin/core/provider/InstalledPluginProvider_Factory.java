package com.motorola.plugin.core.provider;

import android.content.Context;
import com.motorola.plugin.core.components.PluginEnabler;
import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginWhitelistPolicyExt;
import com.motorola.plugin.core.extension.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class InstalledPluginProvider_Factory implements Factory {
    private final Provider configurationControllerProvider;
    private final Provider contextProvider;
    private final Provider pluginEnablerProvider;
    private final Provider pluginEventProvider;
    private final Provider pluginWhitelistPolicyProvider;

    public InstalledPluginProvider_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.pluginWhitelistPolicyProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.pluginEnablerProvider = provider4;
        this.pluginEventProvider = provider5;
    }

    public static InstalledPluginProvider_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new InstalledPluginProvider_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static InstalledPluginProvider newInstance(Context context, PluginWhitelistPolicyExt pluginWhitelistPolicyExt, ConfigurationController configurationController, PluginEnabler pluginEnabler, PluginEvent pluginEvent) {
        return new InstalledPluginProvider(context, pluginWhitelistPolicyExt, configurationController, pluginEnabler, pluginEvent);
    }

    @Override // javax.inject.Provider
    public InstalledPluginProvider get() {
        return newInstance((Context) this.contextProvider.get(), (PluginWhitelistPolicyExt) this.pluginWhitelistPolicyProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (PluginEnabler) this.pluginEnablerProvider.get(), (PluginEvent) this.pluginEventProvider.get());
    }
}
