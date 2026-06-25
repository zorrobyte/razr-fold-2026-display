package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class AirplaneQSTileController_Factory implements Factory {
    private final Provider taskBarServiceProxyProvider;

    public AirplaneQSTileController_Factory(Provider provider) {
        this.taskBarServiceProxyProvider = provider;
    }

    public static AirplaneQSTileController_Factory create(Provider provider) {
        return new AirplaneQSTileController_Factory(provider);
    }

    public static AirplaneQSTileController newInstance(TaskBarServiceProxy taskBarServiceProxy) {
        return new AirplaneQSTileController(taskBarServiceProxy);
    }

    @Override // javax.inject.Provider
    public AirplaneQSTileController get() {
        return newInstance((TaskBarServiceProxy) this.taskBarServiceProxyProvider.get());
    }
}
