package com.android.systemui;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class UiOffloadThread_Factory implements Factory {

    abstract class InstanceHolder {
        static final UiOffloadThread_Factory INSTANCE = new UiOffloadThread_Factory();
    }

    public static UiOffloadThread_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static UiOffloadThread newInstance() {
        return new UiOffloadThread();
    }

    @Override // javax.inject.Provider
    public UiOffloadThread get() {
        return newInstance();
    }
}
