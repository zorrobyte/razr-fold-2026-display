package com.motorola.plugin.core.components;

import com.motorola.plugin.core.misc.DisposableContainer;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerComponentModule_Companion_BindDisposableContainerFactory implements Factory {

    final class InstanceHolder {
        private static final PluginManagerComponentModule_Companion_BindDisposableContainerFactory INSTANCE = new PluginManagerComponentModule_Companion_BindDisposableContainerFactory();

        private InstanceHolder() {
        }
    }

    public static DisposableContainer bindDisposableContainer() {
        DisposableContainer disposableContainerBindDisposableContainer = PluginManagerComponentModule.Companion.bindDisposableContainer();
        disposableContainerBindDisposableContainer.getClass();
        return disposableContainerBindDisposableContainer;
    }

    public static PluginManagerComponentModule_Companion_BindDisposableContainerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    @Override // javax.inject.Provider
    public DisposableContainer get() {
        return bindDisposableContainer();
    }
}
