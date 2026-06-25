package com.motorola.plugin.core.components.impls;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes2.dex */
public final class ConfigurationListenerChainedDispatcherImpl_Factory implements Factory {

    final class InstanceHolder {
        private static final ConfigurationListenerChainedDispatcherImpl_Factory INSTANCE = new ConfigurationListenerChainedDispatcherImpl_Factory();

        private InstanceHolder() {
        }
    }

    public static ConfigurationListenerChainedDispatcherImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ConfigurationListenerChainedDispatcherImpl newInstance() {
        return new ConfigurationListenerChainedDispatcherImpl();
    }

    @Override // javax.inject.Provider
    public ConfigurationListenerChainedDispatcherImpl get() {
        return newInstance();
    }
}
