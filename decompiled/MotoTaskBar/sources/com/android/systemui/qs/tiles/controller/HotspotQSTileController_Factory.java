package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class HotspotQSTileController_Factory implements Factory {
    private final Provider taskBarServiceProxyProvider;

    public HotspotQSTileController_Factory(Provider provider) {
        this.taskBarServiceProxyProvider = provider;
    }

    public static HotspotQSTileController_Factory create(Provider provider) {
        return new HotspotQSTileController_Factory(provider);
    }

    public static HotspotQSTileController newInstance(TaskBarServiceProxy taskBarServiceProxy) {
        return new HotspotQSTileController(taskBarServiceProxy);
    }

    @Override // javax.inject.Provider
    public HotspotQSTileController get() {
        return newInstance((TaskBarServiceProxy) this.taskBarServiceProxyProvider.get());
    }
}
