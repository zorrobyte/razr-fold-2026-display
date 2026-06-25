package com.android.systemui.util.concurrency;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class ThreadFactoryImpl_Factory implements Factory {

    abstract class InstanceHolder {
        static final ThreadFactoryImpl_Factory INSTANCE = new ThreadFactoryImpl_Factory();
    }

    public static ThreadFactoryImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ThreadFactoryImpl newInstance() {
        return new ThreadFactoryImpl();
    }

    @Override // javax.inject.Provider
    public ThreadFactoryImpl get() {
        return newInstance();
    }
}
