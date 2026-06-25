package com.motorola.plugin.core.components;

import com.motorola.plugin.core.misc.DisposableContainer;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerComponentModule_BindDisposableContainerFactory implements Factory {

    final class InstanceHolder {
        private static final PluginManagerComponentModule_BindDisposableContainerFactory INSTANCE = new PluginManagerComponentModule_BindDisposableContainerFactory();

        private InstanceHolder() {
        }
    }

    public static DisposableContainer bindDisposableContainer() {
        DisposableContainer disposableContainerBindDisposableContainer = PluginManagerComponentModule.bindDisposableContainer();
        disposableContainerBindDisposableContainer.getClass();
        return disposableContainerBindDisposableContainer;
    }

    public static PluginManagerComponentModule_BindDisposableContainerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    @Override // javax.inject.Provider
    public DisposableContainer get() {
        return bindDisposableContainer();
    }
}
