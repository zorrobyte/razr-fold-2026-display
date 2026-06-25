package com.android.systemui.dagger;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideDevicePolicyManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideDevicePolicyManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideDevicePolicyManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideDevicePolicyManagerFactory(provider);
    }

    public static DevicePolicyManager provideDevicePolicyManager(Context context) {
        DevicePolicyManager devicePolicyManagerProvideDevicePolicyManager = FrameworkServicesModule.provideDevicePolicyManager(context);
        devicePolicyManagerProvideDevicePolicyManager.getClass();
        return devicePolicyManagerProvideDevicePolicyManager;
    }

    @Override // javax.inject.Provider
    public DevicePolicyManager get() {
        return provideDevicePolicyManager((Context) this.contextProvider.get());
    }
}
