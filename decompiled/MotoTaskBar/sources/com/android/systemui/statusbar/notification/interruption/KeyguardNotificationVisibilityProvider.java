package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: KeyguardNotificationVisibilityProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public interface KeyguardNotificationVisibilityProvider {
    boolean shouldHideNotification(NotificationEntry notificationEntry);
}
