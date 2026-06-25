package com.motorola.plugin.core.extension;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class ConfigurationControllerImpl_Factory implements Factory {
    private final Provider contextProvider;

    public ConfigurationControllerImpl_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static ConfigurationControllerImpl_Factory create(Provider provider) {
        return new ConfigurationControllerImpl_Factory(provider);
    }

    public static ConfigurationControllerImpl newInstance(Context context) {
        return new ConfigurationControllerImpl(context);
    }

    @Override // javax.inject.Provider
    public ConfigurationControllerImpl get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
