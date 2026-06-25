package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: NotificationDismissibilityProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotificationDismissibilityProvider {
    boolean isDismissable(NotificationEntry notificationEntry);
}
