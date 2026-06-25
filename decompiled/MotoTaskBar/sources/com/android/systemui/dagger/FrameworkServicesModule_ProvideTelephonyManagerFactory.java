package com.android.systemui.dagger;

import android.content.Context;
import android.telephony.TelephonyManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideTelephonyManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideTelephonyManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideTelephonyManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideTelephonyManagerFactory(provider);
    }

    public static TelephonyManager provideTelephonyManager(Context context) {
        TelephonyManager telephonyManagerProvideTelephonyManager = FrameworkServicesModule.provideTelephonyManager(context);
        telephonyManagerProvideTelephonyManager.getClass();
        return telephonyManagerProvideTelephonyManager;
    }

    @Override // javax.inject.Provider
    public TelephonyManager get() {
        return provideTelephonyManager((Context) this.contextProvider.get());
    }
}
