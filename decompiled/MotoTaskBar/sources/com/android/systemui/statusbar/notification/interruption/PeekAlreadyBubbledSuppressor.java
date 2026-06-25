package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PeekAlreadyBubbledSuppressor extends VisualInterruptionFilter {
    public PeekAlreadyBubbledSuppressor() {
        super(SetsKt.setOf(VisualInterruptionType.PEEK), "already bubbled");
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return false;
    }
}
