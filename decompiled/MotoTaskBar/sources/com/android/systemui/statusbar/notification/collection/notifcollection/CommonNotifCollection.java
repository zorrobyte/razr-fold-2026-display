package com.android.systemui.statusbar.notification.collection.notifcollection;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public interface CommonNotifCollection {
    void addCollectionListener(NotifCollectionListener notifCollectionListener);

    Collection getAllNotifs();

    NotificationEntry getEntry(String str);
}
