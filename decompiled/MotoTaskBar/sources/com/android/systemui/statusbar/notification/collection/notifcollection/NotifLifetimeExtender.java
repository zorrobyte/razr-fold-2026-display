package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: loaded from: classes.dex */
public interface NotifLifetimeExtender {

    public interface OnEndLifetimeExtensionCallback {
        void onEndLifetimeExtension(NotifLifetimeExtender notifLifetimeExtender, NotificationEntry notificationEntry);
    }

    void cancelLifetimeExtension(NotificationEntry notificationEntry);

    String getName();

    boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i);

    void setCallback(OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback);
}
