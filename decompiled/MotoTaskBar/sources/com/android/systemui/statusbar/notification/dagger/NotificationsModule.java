package com.android.systemui.statusbar.notification.dagger;

import android.content.Context;
import com.android.systemui.res.R$bool;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionRefactor;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLoggerImpl;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes.dex */
public interface NotificationsModule {
    static NotificationListContainer provideListContainer(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        return notificationStackScrollLayoutController.getNotificationListContainer();
    }

    static NotificationPanelLogger provideNotificationPanelLogger() {
        return new NotificationPanelLoggerImpl();
    }

    static NotificationsController provideNotificationsController(Context context, Provider provider, Provider provider2) {
        return context.getResources().getBoolean(R$bool.config_renderNotifications) ? (NotificationsController) provider.get() : (NotificationsController) provider2.get();
    }

    static VisualInterruptionDecisionProvider provideVisualInterruptionDecisionProvider(Provider provider, Provider provider2) {
        return VisualInterruptionRefactor.isEnabled() ? (VisualInterruptionDecisionProvider) provider2.get() : new NotificationInterruptStateProviderWrapper((NotificationInterruptStateProvider) provider.get());
    }
}
