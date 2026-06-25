package com.android.systemui.statusbar.notification.collection;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class TargetSdkResolver_Factory implements Factory {
    private final Provider contextProvider;

    public TargetSdkResolver_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static TargetSdkResolver_Factory create(Provider provider) {
        return new TargetSdkResolver_Factory(provider);
    }

    public static TargetSdkResolver newInstance(Context context) {
        return new TargetSdkResolver(context);
    }

    @Override // javax.inject.Provider
    public TargetSdkResolver get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
