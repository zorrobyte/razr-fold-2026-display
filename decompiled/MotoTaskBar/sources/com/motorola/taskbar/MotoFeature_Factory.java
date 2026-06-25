package com.motorola.taskbar;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class MotoFeature_Factory implements Factory {
    private final Provider contextProvider;

    public MotoFeature_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static MotoFeature_Factory create(Provider provider) {
        return new MotoFeature_Factory(provider);
    }

    public static MotoFeature newInstance(Context context) {
        return new MotoFeature(context);
    }

    @Override // javax.inject.Provider
    public MotoFeature get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
