package com.motorola.trackpad;

import android.content.Context;
import android.os.Handler;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.model.HardwareDisplayController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class ReadyForProxy_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider handlerProvider;
    private final Provider hardwareDisplayControllerProvider;
    private final Provider motoFeatureProvider;
    private final Provider taskBarControllerProvider;
    private final Provider taskBarServiceProxyProvider;
    private final Provider trackpadGestureHandlerProvider;

    public ReadyForProxy_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.contextProvider = provider;
        this.handlerProvider = provider2;
        this.motoFeatureProvider = provider3;
        this.taskBarServiceProxyProvider = provider4;
        this.hardwareDisplayControllerProvider = provider5;
        this.trackpadGestureHandlerProvider = provider6;
        this.taskBarControllerProvider = provider7;
    }

    public static ReadyForProxy_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new ReadyForProxy_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static ReadyForProxy newInstance(Context context, Handler handler, MotoFeature motoFeature, TaskBarServiceProxy taskBarServiceProxy, HardwareDisplayController hardwareDisplayController, TrackpadGestureHandler trackpadGestureHandler, TaskBarController taskBarController) {
        return new ReadyForProxy(context, handler, motoFeature, taskBarServiceProxy, hardwareDisplayController, trackpadGestureHandler, taskBarController);
    }

    @Override // javax.inject.Provider
    public ReadyForProxy get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.handlerProvider.get(), (MotoFeature) this.motoFeatureProvider.get(), (TaskBarServiceProxy) this.taskBarServiceProxyProvider.get(), (HardwareDisplayController) this.hardwareDisplayControllerProvider.get(), (TrackpadGestureHandler) this.trackpadGestureHandlerProvider.get(), (TaskBarController) this.taskBarControllerProvider.get());
    }
}
