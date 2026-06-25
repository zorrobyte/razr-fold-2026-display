package com.android.systemui.statusbar.notification.collection.provider;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class VisibilityLocationProviderDelegator_Factory implements Factory {

    abstract class InstanceHolder {
        static final VisibilityLocationProviderDelegator_Factory INSTANCE = new VisibilityLocationProviderDelegator_Factory();
    }

    public static VisibilityLocationProviderDelegator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static VisibilityLocationProviderDelegator newInstance() {
        return new VisibilityLocationProviderDelegator();
    }

    @Override // javax.inject.Provider
    public VisibilityLocationProviderDelegator get() {
        return newInstance();
    }
}
