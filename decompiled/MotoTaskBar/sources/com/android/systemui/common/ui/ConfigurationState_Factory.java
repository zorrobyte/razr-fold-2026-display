package com.android.systemui.common.ui;

import android.content.Context;
import android.view.LayoutInflater;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ConfigurationState_Factory implements Factory {
    private final Provider configurationControllerProvider;
    private final Provider contextProvider;
    private final Provider layoutInflaterProvider;

    public ConfigurationState_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.configurationControllerProvider = provider;
        this.contextProvider = provider2;
        this.layoutInflaterProvider = provider3;
    }

    public static ConfigurationState_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ConfigurationState_Factory(provider, provider2, provider3);
    }

    public static ConfigurationState newInstance(ConfigurationController configurationController, Context context, LayoutInflater layoutInflater) {
        return new ConfigurationState(configurationController, context, layoutInflater);
    }

    @Override // javax.inject.Provider
    public ConfigurationState get() {
        return newInstance((ConfigurationController) this.configurationControllerProvider.get(), (Context) this.contextProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get());
    }
}
