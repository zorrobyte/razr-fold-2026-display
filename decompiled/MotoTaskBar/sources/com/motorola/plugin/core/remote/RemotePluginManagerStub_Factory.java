package com.motorola.plugin.core.remote;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes2.dex */
public final class RemotePluginManagerStub_Factory implements Factory {

    final class InstanceHolder {
        private static final RemotePluginManagerStub_Factory INSTANCE = new RemotePluginManagerStub_Factory();

        private InstanceHolder() {
        }
    }

    public static RemotePluginManagerStub_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static RemotePluginManagerStub newInstance() {
        return new RemotePluginManagerStub();
    }

    @Override // javax.inject.Provider
    public RemotePluginManagerStub get() {
        return newInstance();
    }
}
