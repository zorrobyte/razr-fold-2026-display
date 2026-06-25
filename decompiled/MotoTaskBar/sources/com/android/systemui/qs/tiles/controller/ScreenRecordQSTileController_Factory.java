package com.android.systemui.qs.tiles.controller;

import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ScreenRecordQSTileController_Factory implements Factory {
    private final Provider taskBarServiceProxyProvider;

    public ScreenRecordQSTileController_Factory(Provider provider) {
        this.taskBarServiceProxyProvider = provider;
    }

    public static ScreenRecordQSTileController_Factory create(Provider provider) {
        return new ScreenRecordQSTileController_Factory(provider);
    }

    public static ScreenRecordQSTileController newInstance(TaskBarServiceProxy taskBarServiceProxy) {
        return new ScreenRecordQSTileController(taskBarServiceProxy);
    }

    @Override // javax.inject.Provider
    public ScreenRecordQSTileController get() {
        return newInstance((TaskBarServiceProxy) this.taskBarServiceProxyProvider.get());
    }
}
