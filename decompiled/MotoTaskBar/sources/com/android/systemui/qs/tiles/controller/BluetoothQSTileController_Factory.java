package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class BluetoothQSTileController_Factory implements Factory {
    private final Provider taskBarServiceProxyProvider;

    public BluetoothQSTileController_Factory(Provider provider) {
        this.taskBarServiceProxyProvider = provider;
    }

    public static BluetoothQSTileController_Factory create(Provider provider) {
        return new BluetoothQSTileController_Factory(provider);
    }

    public static BluetoothQSTileController newInstance(TaskBarServiceProxy taskBarServiceProxy) {
        return new BluetoothQSTileController(taskBarServiceProxy);
    }

    @Override // javax.inject.Provider
    public BluetoothQSTileController get() {
        return newInstance((TaskBarServiceProxy) this.taskBarServiceProxyProvider.get());
    }
}
