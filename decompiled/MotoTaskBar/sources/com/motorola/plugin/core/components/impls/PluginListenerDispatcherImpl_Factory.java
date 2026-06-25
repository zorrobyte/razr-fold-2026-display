package com.motorola.plugin.core.components.impls;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginListenerDispatcherImpl_Factory implements Factory {

    final class InstanceHolder {
        private static final PluginListenerDispatcherImpl_Factory INSTANCE = new PluginListenerDispatcherImpl_Factory();

        private InstanceHolder() {
        }
    }

    public static PluginListenerDispatcherImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static PluginListenerDispatcherImpl newInstance() {
        return new PluginListenerDispatcherImpl();
    }

    @Override // javax.inject.Provider
    public PluginListenerDispatcherImpl get() {
        return newInstance();
    }
}
