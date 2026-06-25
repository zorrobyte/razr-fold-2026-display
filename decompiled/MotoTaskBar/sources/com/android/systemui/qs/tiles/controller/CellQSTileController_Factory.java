package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class CellQSTileController_Factory implements Factory {
    private final Provider taskBarServiceProxyProvider;

    public CellQSTileController_Factory(Provider provider) {
        this.taskBarServiceProxyProvider = provider;
    }

    public static CellQSTileController_Factory create(Provider provider) {
        return new CellQSTileController_Factory(provider);
    }

    public static CellQSTileController newInstance(TaskBarServiceProxy taskBarServiceProxy) {
        return new CellQSTileController(taskBarServiceProxy);
    }

    @Override // javax.inject.Provider
    public CellQSTileController get() {
        return newInstance((TaskBarServiceProxy) this.taskBarServiceProxyProvider.get());
    }
}
