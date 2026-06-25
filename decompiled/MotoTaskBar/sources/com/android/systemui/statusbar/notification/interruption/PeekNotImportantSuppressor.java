package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PeekNotImportantSuppressor extends VisualInterruptionFilter {
    public PeekNotImportantSuppressor() {
        super(SetsKt.setOf(VisualInterruptionType.PEEK), "importance < HIGH");
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return notificationEntry.getImportance() < 4;
    }
}
