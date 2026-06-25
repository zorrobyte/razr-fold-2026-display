package com.android.systemui.statusbar.notification.shared;

import com.android.systemui.Flags;

/* JADX INFO: compiled from: NotificationContentAlphaOptimization.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationContentAlphaOptimization {
    public static final NotificationContentAlphaOptimization INSTANCE = new NotificationContentAlphaOptimization();

    private NotificationContentAlphaOptimization() {
    }

    public static final boolean isEnabled() {
        return Flags.notificationContentAlphaOptimization();
    }
}
