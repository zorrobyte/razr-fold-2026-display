package com.motorola.taskbar.model;

import android.content.Context;
import android.os.Handler;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class HardwareDisplayControllerImpl_Factory implements Factory {
    private final Provider bgHandlerProvider;
    private final Provider contextProvider;
    private final Provider mainHandlerProvider;
    private final Provider motoFeatureProvider;
    private final Provider taskBarServiceProxyProvider;

    public HardwareDisplayControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.bgHandlerProvider = provider3;
        this.taskBarServiceProxyProvider = provider4;
        this.motoFeatureProvider = provider5;
    }

    public static HardwareDisplayControllerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new HardwareDisplayControllerImpl_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static HardwareDisplayControllerImpl newInstance(Context context, Handler handler, Handler handler2, TaskBarServiceProxy taskBarServiceProxy, MotoFeature motoFeature) {
        return new HardwareDisplayControllerImpl(context, handler, handler2, taskBarServiceProxy, motoFeature);
    }

    @Override // javax.inject.Provider
    public HardwareDisplayControllerImpl get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.mainHandlerProvider.get(), (Handler) this.bgHandlerProvider.get(), (TaskBarServiceProxy) this.taskBarServiceProxyProvider.get(), (MotoFeature) this.motoFeatureProvider.get());
    }
}
