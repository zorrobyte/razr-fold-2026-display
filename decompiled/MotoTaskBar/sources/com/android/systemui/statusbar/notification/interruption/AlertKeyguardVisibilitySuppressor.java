package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AlertKeyguardVisibilitySuppressor extends VisualInterruptionFilter {
    private final KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlertKeyguardVisibilitySuppressor(KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider) {
        super(SetsKt.setOf((Object[]) new VisualInterruptionType[]{VisualInterruptionType.PEEK, VisualInterruptionType.PULSE, VisualInterruptionType.BUBBLE}), "hidden on keyguard");
        keyguardNotificationVisibilityProvider.getClass();
        this.keyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return this.keyguardNotificationVisibilityProvider.shouldHideNotification(notificationEntry);
    }
}
