package com.android.systemui.flags;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class SystemPropertiesHelper_Factory implements Factory {

    abstract class InstanceHolder {
        static final SystemPropertiesHelper_Factory INSTANCE = new SystemPropertiesHelper_Factory();
    }

    public static SystemPropertiesHelper_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SystemPropertiesHelper newInstance() {
        return new SystemPropertiesHelper();
    }

    @Override // javax.inject.Provider
    public SystemPropertiesHelper get() {
        return newInstance();
    }
}
