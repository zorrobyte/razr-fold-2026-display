package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: NotifViewBarn.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifViewBarn {
    private final Map rowMap = new LinkedHashMap();

    public final void registerViewForEntry(ListEntry listEntry, NotifViewController notifViewController) {
        listEntry.getClass();
        notifViewController.getClass();
        this.rowMap.put(listEntry.getKey(), notifViewController);
    }

    public final void removeViewForEntry(ListEntry listEntry) {
        listEntry.getClass();
        this.rowMap.remove(listEntry.getKey());
    }

    public final NotifGroupController requireGroupController(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        NotifViewController notifViewController = (NotifViewController) this.rowMap.get(notificationEntry.getKey());
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(("No view has been registered for entry: " + notificationEntry.getKey()).toString());
    }

    public final NodeController requireNodeController(ListEntry listEntry) {
        listEntry.getClass();
        NotifViewController notifViewController = (NotifViewController) this.rowMap.get(listEntry.getKey());
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(("No view has been registered for entry: " + listEntry.getKey()).toString());
    }

    public final NotifRowController requireRowController(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        NotifViewController notifViewController = (NotifViewController) this.rowMap.get(notificationEntry.getKey());
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(("No view has been registered for entry: " + notificationEntry.getKey()).toString());
    }
}
