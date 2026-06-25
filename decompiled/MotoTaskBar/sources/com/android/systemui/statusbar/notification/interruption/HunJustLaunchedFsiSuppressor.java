package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HunJustLaunchedFsiSuppressor extends VisualInterruptionFilter {
    public HunJustLaunchedFsiSuppressor() {
        super(SetsKt.setOf((Object[]) new VisualInterruptionType[]{VisualInterruptionType.PEEK, VisualInterruptionType.PULSE}), "just launched FSI");
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return notificationEntry.hasJustLaunchedFullScreenIntent();
    }
}
