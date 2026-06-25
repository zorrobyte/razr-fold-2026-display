package com.android.systemui.statusbar.notification.stack.ui.view;

/* JADX INFO: compiled from: NotificationStatsLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotificationStatsLogger extends NotificationRowStatsLogger {
    void onNotificationRemoved(String str);

    void onNotificationUpdated(String str);
}
