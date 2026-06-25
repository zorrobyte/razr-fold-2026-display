package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: loaded from: classes.dex */
public interface OnUserInteractionCallback {
    Runnable registerFutureDismissal(NotificationEntry notificationEntry, int i);
}
