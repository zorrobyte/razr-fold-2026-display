package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GroupMembershipManagerImpl implements GroupMembershipManager {
    private boolean isTopLevelEntry(NotificationEntry notificationEntry) {
        return notificationEntry.getParent() == GroupEntry.ROOT_ENTRY;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager
    public List getChildren(ListEntry listEntry) {
        GroupEntry parent;
        if (listEntry instanceof GroupEntry) {
            return ((GroupEntry) listEntry).getChildren();
        }
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry == null || !isGroupSummary(representativeEntry) || (parent = representativeEntry.getParent()) == null) {
            return null;
        }
        return parent.getChildren();
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager
    public NotificationEntry getGroupSummary(NotificationEntry notificationEntry) {
        if (isTopLevelEntry(notificationEntry) || notificationEntry.getParent() == null) {
            return null;
        }
        return notificationEntry.getParent().getSummary();
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager
    public boolean isChildInGroup(NotificationEntry notificationEntry) {
        return (isGroupSummary(notificationEntry) || isTopLevelEntry(notificationEntry) || notificationEntry.getParent() == null) ? false : true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager
    public boolean isGroupSummary(NotificationEntry notificationEntry) {
        return notificationEntry.getParent() != null && notificationEntry.getParent().getSummary() == notificationEntry;
    }
}
