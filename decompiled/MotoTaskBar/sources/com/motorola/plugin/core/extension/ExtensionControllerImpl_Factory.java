package com.motorola.plugin.core.extension;

import android.content.Context;
import com.motorola.plugin.core.components.PluginManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class ExtensionControllerImpl_Factory implements Factory {
    private final Provider mConfigurationControllerProvider;
    private final Provider mDefaultContextProvider;
    private final Provider mPluginManagerProvider;

    public ExtensionControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.mDefaultContextProvider = provider;
        this.mPluginManagerProvider = provider2;
        this.mConfigurationControllerProvider = provider3;
    }

    public static ExtensionControllerImpl_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ExtensionControllerImpl_Factory(provider, provider2, provider3);
    }

    public static ExtensionControllerImpl newInstance(Context context, PluginManager pluginManager, ConfigurationController configurationController) {
        return new ExtensionControllerImpl(context, pluginManager, configurationController);
    }

    @Override // javax.inject.Provider
    public ExtensionControllerImpl get() {
        return newInstance((Context) this.mDefaultContextProvider.get(), (PluginManager) this.mPluginManagerProvider.get(), (ConfigurationController) this.mConfigurationControllerProvider.get());
    }
}
