package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: loaded from: classes.dex */
public interface SensitiveNotificationProtectionController {
    boolean isSensitiveStateActive();

    void registerSensitiveStateListener(Runnable runnable);

    boolean shouldProtectNotification(NotificationEntry notificationEntry);
}
