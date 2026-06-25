package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public interface GroupExpansionManager {

    public interface OnGroupExpansionChangeListener {
        void onGroupExpansionChange(ExpandableNotificationRow expandableNotificationRow, boolean z);
    }

    boolean isGroupExpanded(NotificationEntry notificationEntry);

    void registerGroupExpansionChangeListener(OnGroupExpansionChangeListener onGroupExpansionChangeListener);

    void setGroupExpanded(NotificationEntry notificationEntry, boolean z);

    boolean toggleGroupExpansion(NotificationEntry notificationEntry);
}
