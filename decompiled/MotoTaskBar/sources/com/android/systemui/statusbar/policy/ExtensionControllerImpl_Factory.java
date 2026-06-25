package com.android.systemui.statusbar.policy;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ExtensionControllerImpl_Factory implements Factory {
    private final Provider configurationControllerProvider;
    private final Provider contextProvider;

    public ExtensionControllerImpl_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.configurationControllerProvider = provider2;
    }

    public static ExtensionControllerImpl_Factory create(Provider provider, Provider provider2) {
        return new ExtensionControllerImpl_Factory(provider, provider2);
    }

    public static ExtensionControllerImpl newInstance(Context context, ConfigurationController configurationController) {
        return new ExtensionControllerImpl(context, configurationController);
    }

    @Override // javax.inject.Provider
    public ExtensionControllerImpl get() {
        return newInstance((Context) this.contextProvider.get(), (ConfigurationController) this.configurationControllerProvider.get());
    }
}
