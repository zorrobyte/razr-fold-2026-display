package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.WifiStatusMonitor;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class WifiQSTileController_Factory implements Factory {
    private final Provider taskBarServiceProxyProvider;
    private final Provider wifiStatusMonitorProvider;

    public WifiQSTileController_Factory(Provider provider, Provider provider2) {
        this.taskBarServiceProxyProvider = provider;
        this.wifiStatusMonitorProvider = provider2;
    }

    public static WifiQSTileController_Factory create(Provider provider, Provider provider2) {
        return new WifiQSTileController_Factory(provider, provider2);
    }

    public static WifiQSTileController newInstance(TaskBarServiceProxy taskBarServiceProxy, WifiStatusMonitor wifiStatusMonitor) {
        return new WifiQSTileController(taskBarServiceProxy, wifiStatusMonitor);
    }

    @Override // javax.inject.Provider
    public WifiQSTileController get() {
        return newInstance((TaskBarServiceProxy) this.taskBarServiceProxyProvider.get(), (WifiStatusMonitor) this.wifiStatusMonitorProvider.get());
    }
}
