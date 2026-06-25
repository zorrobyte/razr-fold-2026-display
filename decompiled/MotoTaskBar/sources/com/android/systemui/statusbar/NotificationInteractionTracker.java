package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: NotificationInteractionTracker.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationInteractionTracker implements NotifCollectionListener, NotificationInteractionListener {
    private final Map interactions;

    public NotificationInteractionTracker(NotificationClickNotifier notificationClickNotifier) {
        notificationClickNotifier.getClass();
        this.interactions = new LinkedHashMap();
        notificationClickNotifier.addNotificationInteractionListener(this);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryAdded(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        this.interactions.put(notificationEntry.getKey(), Boolean.FALSE);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryCleanUp(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        this.interactions.remove(notificationEntry.getKey());
    }

    @Override // com.android.systemui.statusbar.NotificationInteractionListener
    public void onNotificationInteraction(String str) {
        str.getClass();
        this.interactions.put(str, Boolean.TRUE);
    }
}
