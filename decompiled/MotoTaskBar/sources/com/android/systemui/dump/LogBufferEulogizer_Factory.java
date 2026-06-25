package com.android.systemui.dump;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class LogBufferEulogizer_Factory implements Factory {

    abstract class InstanceHolder {
        static final LogBufferEulogizer_Factory INSTANCE = new LogBufferEulogizer_Factory();
    }

    public static LogBufferEulogizer_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static LogBufferEulogizer newInstance() {
        return new LogBufferEulogizer();
    }

    @Override // javax.inject.Provider
    public LogBufferEulogizer get() {
        return newInstance();
    }
}
