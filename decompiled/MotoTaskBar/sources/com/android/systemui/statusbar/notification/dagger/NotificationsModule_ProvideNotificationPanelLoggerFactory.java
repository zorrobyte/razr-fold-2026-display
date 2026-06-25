package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationsModule_ProvideNotificationPanelLoggerFactory implements Factory {

    abstract class InstanceHolder {
        static final NotificationsModule_ProvideNotificationPanelLoggerFactory INSTANCE = new NotificationsModule_ProvideNotificationPanelLoggerFactory();
    }

    public static NotificationsModule_ProvideNotificationPanelLoggerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotificationPanelLogger provideNotificationPanelLogger() {
        NotificationPanelLogger notificationPanelLoggerProvideNotificationPanelLogger = NotificationsModule.provideNotificationPanelLogger();
        notificationPanelLoggerProvideNotificationPanelLogger.getClass();
        return notificationPanelLoggerProvideNotificationPanelLogger;
    }

    @Override // javax.inject.Provider
    public NotificationPanelLogger get() {
        return provideNotificationPanelLogger();
    }
}
