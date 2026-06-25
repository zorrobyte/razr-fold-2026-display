package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PeekPackageSnoozedSuppressor extends VisualInterruptionFilter {
    private final HeadsUpManager headsUpManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeekPackageSnoozedSuppressor(HeadsUpManager headsUpManager) {
        super(SetsKt.setOf(VisualInterruptionType.PEEK), "package snoozed");
        headsUpManager.getClass();
        this.headsUpManager = headsUpManager;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        if (notificationEntry.getSbn().getNotification().fullScreenIntent != null) {
            return false;
        }
        HeadsUpManager headsUpManager = this.headsUpManager;
        String packageName = notificationEntry.getSbn().getPackageName();
        packageName.getClass();
        return headsUpManager.isSnoozed(packageName);
    }
}
