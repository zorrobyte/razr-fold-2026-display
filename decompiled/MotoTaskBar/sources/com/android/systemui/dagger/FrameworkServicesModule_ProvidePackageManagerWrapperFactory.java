package com.android.systemui.dagger;

import com.android.systemui.shared.system.PackageManagerWrapper;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvidePackageManagerWrapperFactory implements Factory {

    abstract class InstanceHolder {
        static final FrameworkServicesModule_ProvidePackageManagerWrapperFactory INSTANCE = new FrameworkServicesModule_ProvidePackageManagerWrapperFactory();
    }

    public static FrameworkServicesModule_ProvidePackageManagerWrapperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static PackageManagerWrapper providePackageManagerWrapper() {
        PackageManagerWrapper packageManagerWrapperProvidePackageManagerWrapper = FrameworkServicesModule.providePackageManagerWrapper();
        packageManagerWrapperProvidePackageManagerWrapper.getClass();
        return packageManagerWrapperProvidePackageManagerWrapper;
    }

    @Override // javax.inject.Provider
    public PackageManagerWrapper get() {
        return providePackageManagerWrapper();
    }
}
