package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: loaded from: classes.dex */
public abstract class NotifFilter extends Pluggable {
    protected NotifFilter(String str) {
        super(str);
    }

    public abstract boolean shouldFilterOut(NotificationEntry notificationEntry, long j);
}
