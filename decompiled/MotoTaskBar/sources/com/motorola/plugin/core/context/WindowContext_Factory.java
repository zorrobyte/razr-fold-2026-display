package com.motorola.plugin.core.context;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class WindowContext_Factory implements Factory {
    private final Provider baseProvider;

    public WindowContext_Factory(Provider provider) {
        this.baseProvider = provider;
    }

    public static WindowContext_Factory create(Provider provider) {
        return new WindowContext_Factory(provider);
    }

    public static WindowContext newInstance(Context context) {
        return new WindowContext(context);
    }

    @Override // javax.inject.Provider
    public WindowContext get() {
        return newInstance((Context) this.baseProvider.get());
    }
}
