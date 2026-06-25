package com.android.systemui.statusbar.notification.row.dagger;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory implements Factory {
    private final Provider notificationEntryProvider;

    public ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory(Provider provider) {
        this.notificationEntryProvider = provider;
    }

    public static ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory create(Provider provider) {
        return new ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory(provider);
    }

    public static StatusBarNotification provideStatusBarNotification(NotificationEntry notificationEntry) {
        StatusBarNotification statusBarNotificationProvideStatusBarNotification = ExpandableNotificationRowComponent.ExpandableNotificationRowModule.provideStatusBarNotification(notificationEntry);
        statusBarNotificationProvideStatusBarNotification.getClass();
        return statusBarNotificationProvideStatusBarNotification;
    }

    @Override // javax.inject.Provider
    public StatusBarNotification get() {
        return provideStatusBarNotification((NotificationEntry) this.notificationEntryProvider.get());
    }
}
