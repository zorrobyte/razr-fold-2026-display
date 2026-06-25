package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: loaded from: classes.dex */
public interface VisibilityLocationProvider {
    boolean isInVisibleLocation(NotificationEntry notificationEntry);
}
