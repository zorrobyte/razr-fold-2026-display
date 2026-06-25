package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: HeadsUpCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
interface HunMutator {
    void removeNotification(String str, boolean z);

    void updateNotification(NotificationEntry notificationEntry, boolean z);
}
