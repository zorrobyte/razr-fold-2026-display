package com.android.systemui.log;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class LogBufferFactory_Factory implements Factory {

    abstract class InstanceHolder {
        static final LogBufferFactory_Factory INSTANCE = new LogBufferFactory_Factory();
    }

    public static LogBufferFactory_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static LogBufferFactory newInstance() {
        return new LogBufferFactory();
    }

    @Override // javax.inject.Provider
    public LogBufferFactory get() {
        return newInstance();
    }
}
