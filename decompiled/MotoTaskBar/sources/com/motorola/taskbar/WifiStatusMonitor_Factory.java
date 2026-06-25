package com.motorola.taskbar;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class WifiStatusMonitor_Factory implements Factory {
    private final Provider contextProvider;

    public WifiStatusMonitor_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static WifiStatusMonitor_Factory create(Provider provider) {
        return new WifiStatusMonitor_Factory(provider);
    }

    public static WifiStatusMonitor newInstance(Context context) {
        return new WifiStatusMonitor(context);
    }

    @Override // javax.inject.Provider
    public WifiStatusMonitor get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
