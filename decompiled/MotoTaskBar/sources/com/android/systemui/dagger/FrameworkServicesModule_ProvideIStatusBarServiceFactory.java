package com.android.systemui.dagger;

import com.android.internal.statusbar.IStatusBarService;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideIStatusBarServiceFactory implements Factory {

    abstract class InstanceHolder {
        static final FrameworkServicesModule_ProvideIStatusBarServiceFactory INSTANCE = new FrameworkServicesModule_ProvideIStatusBarServiceFactory();
    }

    public static FrameworkServicesModule_ProvideIStatusBarServiceFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IStatusBarService provideIStatusBarService() {
        IStatusBarService iStatusBarServiceProvideIStatusBarService = FrameworkServicesModule.provideIStatusBarService();
        iStatusBarServiceProvideIStatusBarService.getClass();
        return iStatusBarServiceProvideIStatusBarService;
    }

    @Override // javax.inject.Provider
    public IStatusBarService get() {
        return provideIStatusBarService();
    }
}
