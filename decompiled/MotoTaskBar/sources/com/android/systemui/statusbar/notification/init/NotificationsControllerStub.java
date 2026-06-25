package com.android.systemui.statusbar.notification.init;

import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

/* JADX INFO: compiled from: NotificationsControllerStub.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationsControllerStub implements NotificationsController {
    private final NotificationListener notificationListener;

    public NotificationsControllerStub(NotificationListener notificationListener) {
        notificationListener.getClass();
        this.notificationListener = notificationListener;
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public void initialize(NotificationPresenter notificationPresenter, NotificationListContainer notificationListContainer, NotifStackController notifStackController, NotificationActivityStarter notificationActivityStarter) {
        notificationPresenter.getClass();
        notificationListContainer.getClass();
        notifStackController.getClass();
        notificationActivityStarter.getClass();
        this.notificationListener.registerAsSystemService();
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public void resetUserExpandedStates() {
    }
}
