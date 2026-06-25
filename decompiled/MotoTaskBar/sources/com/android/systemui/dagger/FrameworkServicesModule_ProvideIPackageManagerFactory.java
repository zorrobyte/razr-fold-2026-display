package com.android.systemui.dagger;

import android.content.pm.IPackageManager;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideIPackageManagerFactory implements Factory {

    abstract class InstanceHolder {
        static final FrameworkServicesModule_ProvideIPackageManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIPackageManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIPackageManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IPackageManager provideIPackageManager() {
        IPackageManager iPackageManagerProvideIPackageManager = FrameworkServicesModule.provideIPackageManager();
        iPackageManagerProvideIPackageManager.getClass();
        return iPackageManagerProvideIPackageManager;
    }

    @Override // javax.inject.Provider
    public IPackageManager get() {
        return provideIPackageManager();
    }
}
