package com.motorola.plugin.core.components.impls;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class DummyPluginManager_Factory implements Factory {
    private final Provider cProvider;

    public DummyPluginManager_Factory(Provider provider) {
        this.cProvider = provider;
    }

    public static DummyPluginManager_Factory create(Provider provider) {
        return new DummyPluginManager_Factory(provider);
    }

    public static DummyPluginManager newInstance(Context context) {
        return new DummyPluginManager(context);
    }

    @Override // javax.inject.Provider
    public DummyPluginManager get() {
        return newInstance((Context) this.cProvider.get());
    }
}
