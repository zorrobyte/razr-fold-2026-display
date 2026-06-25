package com.android.systemui.util.kotlin;

import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes.dex */
public final class JavaAdapter_Factory implements Factory {
    private final Provider scopeProvider;

    public JavaAdapter_Factory(Provider provider) {
        this.scopeProvider = provider;
    }

    public static JavaAdapter_Factory create(Provider provider) {
        return new JavaAdapter_Factory(provider);
    }

    public static JavaAdapter newInstance(CoroutineScope coroutineScope) {
        return new JavaAdapter(coroutineScope);
    }

    @Override // javax.inject.Provider
    public JavaAdapter get() {
        return newInstance((CoroutineScope) this.scopeProvider.get());
    }
}
