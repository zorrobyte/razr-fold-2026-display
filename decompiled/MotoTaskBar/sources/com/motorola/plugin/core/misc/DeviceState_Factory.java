package com.motorola.plugin.core.misc;

import android.content.Context;

/* JADX INFO: loaded from: classes2.dex */
public final class DeviceState_Factory implements dagger.internal.Factory {
    private final javax.inject.Provider contextProvider;

    public DeviceState_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static DeviceState_Factory create(javax.inject.Provider provider) {
        return new DeviceState_Factory(provider);
    }

    public static DeviceState newInstance(Context context) {
        return new DeviceState(context);
    }

    @Override // javax.inject.Provider
    public DeviceState get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
