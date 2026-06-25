package com.motorola.plugin.core.components.impls;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginWhitelistPolicyImpl_Factory implements Factory {
    private final Provider contextProvider;

    public PluginWhitelistPolicyImpl_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PluginWhitelistPolicyImpl_Factory create(Provider provider) {
        return new PluginWhitelistPolicyImpl_Factory(provider);
    }

    public static PluginWhitelistPolicyImpl newInstance(Context context) {
        return new PluginWhitelistPolicyImpl(context);
    }

    @Override // javax.inject.Provider
    public PluginWhitelistPolicyImpl get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
