package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

/* JADX INFO: compiled from: ShadeViewManager.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ShadeViewManagerFactory {
    ShadeViewManager create(NotificationListContainer notificationListContainer, NotifStackController notifStackController);
}
