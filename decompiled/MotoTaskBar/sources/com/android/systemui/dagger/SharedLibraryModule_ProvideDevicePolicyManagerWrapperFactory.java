package com.android.systemui.dagger;

import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class SharedLibraryModule_ProvideDevicePolicyManagerWrapperFactory implements Factory {
    private final SharedLibraryModule module;

    public SharedLibraryModule_ProvideDevicePolicyManagerWrapperFactory(SharedLibraryModule sharedLibraryModule) {
        this.module = sharedLibraryModule;
    }

    public static SharedLibraryModule_ProvideDevicePolicyManagerWrapperFactory create(SharedLibraryModule sharedLibraryModule) {
        return new SharedLibraryModule_ProvideDevicePolicyManagerWrapperFactory(sharedLibraryModule);
    }

    public static DevicePolicyManagerWrapper provideDevicePolicyManagerWrapper(SharedLibraryModule sharedLibraryModule) {
        DevicePolicyManagerWrapper devicePolicyManagerWrapperProvideDevicePolicyManagerWrapper = sharedLibraryModule.provideDevicePolicyManagerWrapper();
        devicePolicyManagerWrapperProvideDevicePolicyManagerWrapper.getClass();
        return devicePolicyManagerWrapperProvideDevicePolicyManagerWrapper;
    }

    @Override // javax.inject.Provider
    public DevicePolicyManagerWrapper get() {
        return provideDevicePolicyManagerWrapper(this.module);
    }
}
