package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class DndQSTileController_Factory implements Factory {
    private final Provider taskBarServiceProxyProvider;

    public DndQSTileController_Factory(Provider provider) {
        this.taskBarServiceProxyProvider = provider;
    }

    public static DndQSTileController_Factory create(Provider provider) {
        return new DndQSTileController_Factory(provider);
    }

    public static DndQSTileController newInstance(TaskBarServiceProxy taskBarServiceProxy) {
        return new DndQSTileController(taskBarServiceProxy);
    }

    @Override // javax.inject.Provider
    public DndQSTileController get() {
        return newInstance((TaskBarServiceProxy) this.taskBarServiceProxyProvider.get());
    }
}
