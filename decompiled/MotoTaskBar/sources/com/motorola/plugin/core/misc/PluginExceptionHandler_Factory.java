package com.motorola.plugin.core.misc;

import com.motorola.plugin.core.components.PluginEvent;
import com.motorola.plugin.core.components.PluginSubscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginExceptionHandler_Factory implements dagger.internal.Factory {
    private final javax.inject.Provider mPluginEventProvider;
    private final javax.inject.Provider mPluginSubscriberProvider;

    public PluginExceptionHandler_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.mPluginEventProvider = provider;
        this.mPluginSubscriberProvider = provider2;
    }

    public static PluginExceptionHandler_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new PluginExceptionHandler_Factory(provider, provider2);
    }

    public static PluginExceptionHandler newInstance(PluginEvent pluginEvent, PluginSubscriber pluginSubscriber) {
        return new PluginExceptionHandler(pluginEvent, pluginSubscriber);
    }

    @Override // javax.inject.Provider
    public PluginExceptionHandler get() {
        return newInstance((PluginEvent) this.mPluginEventProvider.get(), (PluginSubscriber) this.mPluginSubscriberProvider.get());
    }
}
