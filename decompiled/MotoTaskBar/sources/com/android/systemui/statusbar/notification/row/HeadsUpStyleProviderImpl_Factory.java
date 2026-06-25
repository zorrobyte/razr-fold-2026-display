package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpStyleProviderImpl_Factory implements Factory {

    abstract class InstanceHolder {
        static final HeadsUpStyleProviderImpl_Factory INSTANCE = new HeadsUpStyleProviderImpl_Factory();
    }

    public static HeadsUpStyleProviderImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static HeadsUpStyleProviderImpl newInstance() {
        return new HeadsUpStyleProviderImpl();
    }

    @Override // javax.inject.Provider
    public HeadsUpStyleProviderImpl get() {
        return newInstance();
    }
}
