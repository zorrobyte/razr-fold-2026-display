package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: NotifStabilityManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DefaultNotifStabilityManager extends NotifStabilityManager {
    public static final DefaultNotifStabilityManager INSTANCE = new DefaultNotifStabilityManager();

    private DefaultNotifStabilityManager() {
        super("DefaultNotifStabilityManager");
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public boolean isEntryReorderingAllowed(ListEntry listEntry) {
        listEntry.getClass();
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public boolean isEveryChangeAllowed() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public boolean isGroupChangeAllowed(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public boolean isGroupPruneAllowed(GroupEntry groupEntry) {
        groupEntry.getClass();
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public boolean isPipelineRunAllowed() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public boolean isSectionChangeAllowed(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public void onBeginRun() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
    public void onEntryReorderSuppressed() {
    }
}
