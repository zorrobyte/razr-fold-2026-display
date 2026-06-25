package com.android.systemui.dagger;

import android.content.Context;
import android.content.pm.PackageManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvidePackageManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvidePackageManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvidePackageManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvidePackageManagerFactory(provider);
    }

    public static PackageManager providePackageManager(Context context) {
        PackageManager packageManagerProvidePackageManager = FrameworkServicesModule.providePackageManager(context);
        packageManagerProvidePackageManager.getClass();
        return packageManagerProvidePackageManager;
    }

    @Override // javax.inject.Provider
    public PackageManager get() {
        return providePackageManager((Context) this.contextProvider.get());
    }
}
