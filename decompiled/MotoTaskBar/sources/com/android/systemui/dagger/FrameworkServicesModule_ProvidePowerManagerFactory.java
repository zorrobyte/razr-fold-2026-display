package com.android.systemui.dagger;

import android.content.Context;
import android.os.PowerManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvidePowerManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvidePowerManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvidePowerManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvidePowerManagerFactory(provider);
    }

    public static PowerManager providePowerManager(Context context) {
        PowerManager powerManagerProvidePowerManager = FrameworkServicesModule.providePowerManager(context);
        powerManagerProvidePowerManager.getClass();
        return powerManagerProvidePowerManager;
    }

    @Override // javax.inject.Provider
    public PowerManager get() {
        return providePowerManager((Context) this.contextProvider.get());
    }
}
