package com.android.systemui.dagger;

import android.content.Context;
import android.content.pm.LauncherApps;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideLauncherAppsFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideLauncherAppsFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideLauncherAppsFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideLauncherAppsFactory(provider);
    }

    public static LauncherApps provideLauncherApps(Context context) {
        LauncherApps launcherAppsProvideLauncherApps = FrameworkServicesModule.provideLauncherApps(context);
        launcherAppsProvideLauncherApps.getClass();
        return launcherAppsProvideLauncherApps;
    }

    @Override // javax.inject.Provider
    public LauncherApps get() {
        return provideLauncherApps((Context) this.contextProvider.get());
    }
}
