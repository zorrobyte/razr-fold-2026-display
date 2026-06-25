package com.android.systemui.statusbar.notification.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

/* JADX INFO: compiled from: NotificationsImprovedHunAnimation.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationsImprovedHunAnimation {
    public static final NotificationsImprovedHunAnimation INSTANCE = new NotificationsImprovedHunAnimation();

    private NotificationsImprovedHunAnimation() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (Flags.notificationsImprovedHunAnimation()) {
            throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "NotificationsHeadsUpRefactor") + " is enabled.").toString());
        }
    }

    public static final boolean isEnabled() {
        return Flags.notificationsImprovedHunAnimation();
    }
}
