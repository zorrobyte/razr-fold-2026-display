package com.motorola.plugin.core.provider;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes2.dex */
public final class RemotePluginProviderFactory_Factory implements Factory {

    final class InstanceHolder {
        private static final RemotePluginProviderFactory_Factory INSTANCE = new RemotePluginProviderFactory_Factory();

        private InstanceHolder() {
        }
    }

    public static RemotePluginProviderFactory_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static RemotePluginProviderFactory newInstance() {
        return new RemotePluginProviderFactory();
    }

    @Override // javax.inject.Provider
    public RemotePluginProviderFactory get() {
        return newInstance();
    }
}
