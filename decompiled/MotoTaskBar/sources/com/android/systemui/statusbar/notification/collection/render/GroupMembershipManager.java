package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface GroupMembershipManager {
    List getChildren(ListEntry listEntry);

    NotificationEntry getGroupSummary(NotificationEntry notificationEntry);

    boolean isChildInGroup(NotificationEntry notificationEntry);

    boolean isGroupSummary(NotificationEntry notificationEntry);
}
