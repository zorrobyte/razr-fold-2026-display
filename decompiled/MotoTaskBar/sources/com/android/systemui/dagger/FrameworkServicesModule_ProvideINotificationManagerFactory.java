package com.android.systemui.dagger;

import android.app.INotificationManager;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideINotificationManagerFactory implements Factory {
    private final FrameworkServicesModule module;

    public FrameworkServicesModule_ProvideINotificationManagerFactory(FrameworkServicesModule frameworkServicesModule) {
        this.module = frameworkServicesModule;
    }

    public static FrameworkServicesModule_ProvideINotificationManagerFactory create(FrameworkServicesModule frameworkServicesModule) {
        return new FrameworkServicesModule_ProvideINotificationManagerFactory(frameworkServicesModule);
    }

    public static INotificationManager provideINotificationManager(FrameworkServicesModule frameworkServicesModule) {
        INotificationManager iNotificationManagerProvideINotificationManager = frameworkServicesModule.provideINotificationManager();
        iNotificationManagerProvideINotificationManager.getClass();
        return iNotificationManagerProvideINotificationManager;
    }

    @Override // javax.inject.Provider
    public INotificationManager get() {
        return provideINotificationManager(this.module);
    }
}
