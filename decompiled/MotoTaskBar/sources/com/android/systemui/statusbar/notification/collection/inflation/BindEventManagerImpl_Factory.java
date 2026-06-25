package com.android.systemui.statusbar.notification.collection.inflation;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class BindEventManagerImpl_Factory implements Factory {

    abstract class InstanceHolder {
        static final BindEventManagerImpl_Factory INSTANCE = new BindEventManagerImpl_Factory();
    }

    public static BindEventManagerImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static BindEventManagerImpl newInstance() {
        return new BindEventManagerImpl();
    }

    @Override // javax.inject.Provider
    public BindEventManagerImpl get() {
        return newInstance();
    }
}
