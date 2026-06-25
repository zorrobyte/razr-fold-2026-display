package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import dagger.internal.InstanceFactory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifLayoutInflaterFactory_Provider_Impl implements NotifLayoutInflaterFactory.Provider {
    private final NotifLayoutInflaterFactory_Factory delegateFactory;

    NotifLayoutInflaterFactory_Provider_Impl(NotifLayoutInflaterFactory_Factory notifLayoutInflaterFactory_Factory) {
        this.delegateFactory = notifLayoutInflaterFactory_Factory;
    }

    public static Provider createFactoryProvider(NotifLayoutInflaterFactory_Factory notifLayoutInflaterFactory_Factory) {
        return InstanceFactory.create(new NotifLayoutInflaterFactory_Provider_Impl(notifLayoutInflaterFactory_Factory));
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory.Provider
    public NotifLayoutInflaterFactory provide(ExpandableNotificationRow expandableNotificationRow, int i) {
        return this.delegateFactory.get(expandableNotificationRow, i);
    }
}
