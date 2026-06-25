package com.android.systemui.util;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class DeviceConfigProxy_Factory implements Factory {

    abstract class InstanceHolder {
        static final DeviceConfigProxy_Factory INSTANCE = new DeviceConfigProxy_Factory();
    }

    public static DeviceConfigProxy_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DeviceConfigProxy newInstance() {
        return new DeviceConfigProxy();
    }

    @Override // javax.inject.Provider
    public DeviceConfigProxy get() {
        return newInstance();
    }
}
