package com.android.systemui.statusbar.notification.interruption;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HunGroupAlertBehaviorSuppressor extends VisualInterruptionFilter {
    public HunGroupAlertBehaviorSuppressor() {
        super(SetsKt.setOf((Object[]) new VisualInterruptionType[]{VisualInterruptionType.PEEK, VisualInterruptionType.PULSE}), "suppressive group alert behavior");
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        StatusBarNotification sbn = notificationEntry.getSbn();
        return sbn.isGroup() && sbn.getNotification().suppressAlertingDueToGrouping();
    }
}
