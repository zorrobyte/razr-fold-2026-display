package com.android.systemui.statusbar.notification.init;

import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

/* JADX INFO: compiled from: NotificationsController.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotificationsController {
    void initialize(NotificationPresenter notificationPresenter, NotificationListContainer notificationListContainer, NotifStackController notifStackController, NotificationActivityStarter notificationActivityStarter);

    void resetUserExpandedStates();
}
