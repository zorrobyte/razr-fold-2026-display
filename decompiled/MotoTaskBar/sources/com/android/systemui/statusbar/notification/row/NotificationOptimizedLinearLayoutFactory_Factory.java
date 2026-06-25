package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationOptimizedLinearLayoutFactory_Factory implements Factory {

    abstract class InstanceHolder {
        static final NotificationOptimizedLinearLayoutFactory_Factory INSTANCE = new NotificationOptimizedLinearLayoutFactory_Factory();
    }

    public static NotificationOptimizedLinearLayoutFactory_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotificationOptimizedLinearLayoutFactory newInstance() {
        return new NotificationOptimizedLinearLayoutFactory();
    }

    @Override // javax.inject.Provider
    public NotificationOptimizedLinearLayoutFactory get() {
        return newInstance();
    }
}
