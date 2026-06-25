package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.util.time.SystemClock;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PeekOldWhenSuppressor extends VisualInterruptionFilter {
    private final SystemClock systemClock;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeekOldWhenSuppressor(SystemClock systemClock) {
        super(SetsKt.setOf(VisualInterruptionType.PEEK), "has old `when`", NotificationInterruptStateProviderImpl.NotificationInterruptEvent.HUN_SUPPRESSED_OLD_WHEN, null, 8, null);
        systemClock.getClass();
        this.systemClock = systemClock;
    }

    private final long whenAge(NotificationEntry notificationEntry) {
        return this.systemClock.currentTimeMillis() - notificationEntry.getSbn().getNotification().when;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return notificationEntry.getSbn().getNotification().when > 0 && notificationEntry.getSbn().getNotification().fullScreenIntent == null && !notificationEntry.getSbn().getNotification().isForegroundService() && !notificationEntry.getSbn().getNotification().isUserInitiatedJob() && whenAge(notificationEntry) >= 86400000;
    }
}
