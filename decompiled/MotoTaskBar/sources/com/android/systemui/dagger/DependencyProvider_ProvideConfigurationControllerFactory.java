package com.android.systemui.dagger;

import android.content.Context;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class DependencyProvider_ProvideConfigurationControllerFactory implements Factory {
    private final Provider contextProvider;
    private final DependencyProvider module;

    public DependencyProvider_ProvideConfigurationControllerFactory(DependencyProvider dependencyProvider, Provider provider) {
        this.module = dependencyProvider;
        this.contextProvider = provider;
    }

    public static DependencyProvider_ProvideConfigurationControllerFactory create(DependencyProvider dependencyProvider, Provider provider) {
        return new DependencyProvider_ProvideConfigurationControllerFactory(dependencyProvider, provider);
    }

    public static ConfigurationController provideConfigurationController(DependencyProvider dependencyProvider, Context context) {
        ConfigurationController configurationControllerProvideConfigurationController = dependencyProvider.provideConfigurationController(context);
        configurationControllerProvideConfigurationController.getClass();
        return configurationControllerProvideConfigurationController;
    }

    @Override // javax.inject.Provider
    public ConfigurationController get() {
        return provideConfigurationController(this.module, (Context) this.contextProvider.get());
    }
}
