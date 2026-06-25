package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PulseLowImportanceSuppressor extends VisualInterruptionFilter {
    public PulseLowImportanceSuppressor() {
        super(SetsKt.setOf(VisualInterruptionType.PULSE), "importance < DEFAULT");
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return notificationEntry.getImportance() < 3;
    }
}
