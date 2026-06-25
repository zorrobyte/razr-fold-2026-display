package com.android.systemui.statusbar.notification.data.repository;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class ActiveNotificationListRepository_Factory implements Factory {

    abstract class InstanceHolder {
        static final ActiveNotificationListRepository_Factory INSTANCE = new ActiveNotificationListRepository_Factory();
    }

    public static ActiveNotificationListRepository_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ActiveNotificationListRepository newInstance() {
        return new ActiveNotificationListRepository();
    }

    @Override // javax.inject.Provider
    public ActiveNotificationListRepository get() {
        return newInstance();
    }
}
