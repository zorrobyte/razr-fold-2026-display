package com.android.systemui.statusbar.notification;

import android.view.View;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public interface NotificationActivityStarter {
    void onDragSuccess(NotificationEntry notificationEntry);

    void onNotificationClicked(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow);

    void startHistoryIntent(View view, boolean z);
}
