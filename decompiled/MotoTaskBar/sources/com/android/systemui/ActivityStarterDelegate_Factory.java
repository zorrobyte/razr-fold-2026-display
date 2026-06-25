package com.android.systemui;

import android.content.Context;
import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityStarterDelegate_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider mainLooperProvider;

    public ActivityStarterDelegate_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.mainLooperProvider = provider2;
    }

    public static ActivityStarterDelegate_Factory create(Provider provider, Provider provider2) {
        return new ActivityStarterDelegate_Factory(provider, provider2);
    }

    public static ActivityStarterDelegate newInstance(Context context, Looper looper) {
        return new ActivityStarterDelegate(context, looper);
    }

    @Override // javax.inject.Provider
    public ActivityStarterDelegate get() {
        return newInstance((Context) this.contextProvider.get(), (Looper) this.mainLooperProvider.get());
    }
}
