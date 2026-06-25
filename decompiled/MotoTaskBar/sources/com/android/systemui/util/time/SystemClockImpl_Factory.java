package com.android.systemui.util.time;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class SystemClockImpl_Factory implements Factory {

    abstract class InstanceHolder {
        static final SystemClockImpl_Factory INSTANCE = new SystemClockImpl_Factory();
    }

    public static SystemClockImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SystemClockImpl newInstance() {
        return new SystemClockImpl();
    }

    @Override // javax.inject.Provider
    public SystemClockImpl get() {
        return newInstance();
    }
}
