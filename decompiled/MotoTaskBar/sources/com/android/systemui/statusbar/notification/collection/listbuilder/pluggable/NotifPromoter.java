package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: loaded from: classes.dex */
public abstract class NotifPromoter extends Pluggable {
    protected NotifPromoter(String str) {
        super(str);
    }

    public abstract boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry);
}
