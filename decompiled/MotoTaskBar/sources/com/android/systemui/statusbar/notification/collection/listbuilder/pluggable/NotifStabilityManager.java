package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: NotifStabilityManager.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class NotifStabilityManager extends Pluggable {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected NotifStabilityManager(String str) {
        super(str);
        str.getClass();
    }

    public abstract boolean isEntryReorderingAllowed(ListEntry listEntry);

    public abstract boolean isEveryChangeAllowed();

    public abstract boolean isGroupChangeAllowed(NotificationEntry notificationEntry);

    public abstract boolean isGroupPruneAllowed(GroupEntry groupEntry);

    public abstract boolean isPipelineRunAllowed();

    public abstract boolean isSectionChangeAllowed(NotificationEntry notificationEntry);

    public abstract void onBeginRun();

    public abstract void onEntryReorderSuppressed();
}
