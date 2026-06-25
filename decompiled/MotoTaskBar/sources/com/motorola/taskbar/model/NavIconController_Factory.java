package com.motorola.taskbar.model;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class NavIconController_Factory implements Factory {
    private final Provider broadcastDispatcherProvider;
    private final Provider contextProvider;
    private final Provider hardwareDisplayControllerProvider;
    private final Provider mainHandlerProvider;
    private final Provider taskBarServiceProxyProvider;

    public NavIconController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.hardwareDisplayControllerProvider = provider3;
        this.taskBarServiceProxyProvider = provider4;
        this.broadcastDispatcherProvider = provider5;
    }

    public static NavIconController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new NavIconController_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static NavIconController newInstance(Context context, Handler handler, HardwareDisplayController hardwareDisplayController, TaskBarServiceProxy taskBarServiceProxy, BroadcastDispatcher broadcastDispatcher) {
        return new NavIconController(context, handler, hardwareDisplayController, taskBarServiceProxy, broadcastDispatcher);
    }

    @Override // javax.inject.Provider
    public NavIconController get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.mainHandlerProvider.get(), (HardwareDisplayController) this.hardwareDisplayControllerProvider.get(), (TaskBarServiceProxy) this.taskBarServiceProxyProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
