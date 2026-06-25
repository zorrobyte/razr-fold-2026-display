package com.android.systemui.statusbar.notification.row;

import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifLayoutInflaterFactory_Factory {
    private final Provider notifRemoteViewsFactoryContainerProvider;

    public NotifLayoutInflaterFactory_Factory(Provider provider) {
        this.notifRemoteViewsFactoryContainerProvider = provider;
    }

    public static NotifLayoutInflaterFactory_Factory create(Provider provider) {
        return new NotifLayoutInflaterFactory_Factory(provider);
    }

    public static NotifLayoutInflaterFactory newInstance(ExpandableNotificationRow expandableNotificationRow, int i, NotifRemoteViewsFactoryContainer notifRemoteViewsFactoryContainer) {
        return new NotifLayoutInflaterFactory(expandableNotificationRow, i, notifRemoteViewsFactoryContainer);
    }

    public NotifLayoutInflaterFactory get(ExpandableNotificationRow expandableNotificationRow, int i) {
        return newInstance(expandableNotificationRow, i, (NotifRemoteViewsFactoryContainer) this.notifRemoteViewsFactoryContainerProvider.get());
    }
}
