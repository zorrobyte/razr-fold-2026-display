package com.android.systemui.statusbar.notification.collection.render;

import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class GroupExpansionManagerImpl implements GroupExpansionManager, Dumpable {
    private final DumpManager mDumpManager;
    private final GroupMembershipManager mGroupMembershipManager;
    private final Set mOnGroupChangeListeners = new HashSet();
    private final Set mExpandedGroups = new HashSet();
    private final OnBeforeRenderListListener mNotifTracker = new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl$$ExternalSyntheticLambda0
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
        public final void onBeforeRenderList(List list) {
            this.f$0.lambda$new$0(list);
        }
    };

    public GroupExpansionManagerImpl(DumpManager dumpManager, GroupMembershipManager groupMembershipManager) {
        this.mDumpManager = dumpManager;
        this.mGroupMembershipManager = groupMembershipManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(List list) {
        if (this.mExpandedGroups.isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            if (listEntry instanceof GroupEntry) {
                hashSet.add(listEntry.getRepresentativeEntry());
            }
        }
        Iterator it2 = setDifference(this.mExpandedGroups, hashSet).iterator();
        while (it2.hasNext()) {
            setGroupExpanded((NotificationEntry) it2.next(), false);
        }
    }

    private void sendOnGroupExpandedChange(NotificationEntry notificationEntry, boolean z) {
        Iterator it = this.mOnGroupChangeListeners.iterator();
        while (it.hasNext()) {
            ((GroupExpansionManager.OnGroupExpansionChangeListener) it.next()).onGroupExpansionChange(notificationEntry.getRow(), z);
        }
    }

    private Set setDifference(Set set, Set set2) {
        if (set == null || set.isEmpty()) {
            return new HashSet();
        }
        if (set2 == null || set2.isEmpty()) {
            return new HashSet(set);
        }
        HashSet hashSet = new HashSet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            NotificationEntry notificationEntry = (NotificationEntry) it.next();
            if (!set2.contains(notificationEntry)) {
                hashSet.add(notificationEntry);
            }
        }
        return hashSet;
    }

    public void attach(NotifPipeline notifPipeline) {
        this.mDumpManager.registerDumpable(this);
        notifPipeline.addOnBeforeRenderListListener(this.mNotifTracker);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager
    public boolean isGroupExpanded(NotificationEntry notificationEntry) {
        return this.mExpandedGroups.contains(this.mGroupMembershipManager.getGroupSummary(notificationEntry));
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager
    public void registerGroupExpansionChangeListener(GroupExpansionManager.OnGroupExpansionChangeListener onGroupExpansionChangeListener) {
        this.mOnGroupChangeListeners.add(onGroupExpansionChangeListener);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager
    public void setGroupExpanded(NotificationEntry notificationEntry, boolean z) {
        NotificationEntry groupSummary = this.mGroupMembershipManager.getGroupSummary(notificationEntry);
        if (notificationEntry.getParent() == null) {
            if (z) {
                Log.wtf("GroupExpansionaManagerImpl", "Cannot expand group that is not attached");
            } else {
                groupSummary = notificationEntry;
            }
        }
        if (z ? this.mExpandedGroups.add(groupSummary) : this.mExpandedGroups.remove(groupSummary)) {
            sendOnGroupExpandedChange(notificationEntry, z);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager
    public boolean toggleGroupExpansion(NotificationEntry notificationEntry) {
        setGroupExpanded(notificationEntry, !isGroupExpanded(notificationEntry));
        return isGroupExpanded(notificationEntry);
    }
}
