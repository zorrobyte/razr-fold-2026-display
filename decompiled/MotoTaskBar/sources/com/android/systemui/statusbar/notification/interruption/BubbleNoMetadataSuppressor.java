package com.android.systemui.statusbar.notification.interruption;

import android.app.Notification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BubbleNoMetadataSuppressor extends VisualInterruptionFilter {
    public BubbleNoMetadataSuppressor() {
        super(SetsKt.setOf(VisualInterruptionType.BUBBLE), "has no or invalid bubble metadata");
    }

    private final boolean isValidMetadata(Notification.BubbleMetadata bubbleMetadata) {
        if (bubbleMetadata != null) {
            return (bubbleMetadata.getIntent() == null && bubbleMetadata.getShortcutId() == null) ? false : true;
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return !isValidMetadata(notificationEntry.getBubbleMetadata());
    }
}
