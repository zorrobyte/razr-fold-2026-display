package com.motorola.glass;

import android.content.Context;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class GlassesMonitor_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider motoFeatureProvider;
    private final Provider taskBarServiceProxyProvider;

    public GlassesMonitor_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.taskBarServiceProxyProvider = provider2;
        this.motoFeatureProvider = provider3;
    }

    public static GlassesMonitor_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new GlassesMonitor_Factory(provider, provider2, provider3);
    }

    public static GlassesMonitor newInstance(Context context, TaskBarServiceProxy taskBarServiceProxy, MotoFeature motoFeature) {
        return new GlassesMonitor(context, taskBarServiceProxy, motoFeature);
    }

    @Override // javax.inject.Provider
    public GlassesMonitor get() {
        return newInstance((Context) this.contextProvider.get(), (TaskBarServiceProxy) this.taskBarServiceProxyProvider.get(), (MotoFeature) this.motoFeatureProvider.get());
    }
}
