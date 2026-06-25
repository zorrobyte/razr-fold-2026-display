package com.android.systemui.statusbar.notification.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

/* JADX INFO: compiled from: NotificationsHeadsUpRefactor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationsHeadsUpRefactor {
    public static final NotificationsHeadsUpRefactor INSTANCE = new NotificationsHeadsUpRefactor();

    private NotificationsHeadsUpRefactor() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (Flags.notificationsHeadsUpRefactor()) {
            throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "NotificationsHeadsUpRefactor") + " is enabled.").toString());
        }
    }

    public static final boolean isEnabled() {
        return Flags.notificationsHeadsUpRefactor();
    }

    public static final boolean isUnexpectedlyInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        boolean zNotificationsHeadsUpRefactor = Flags.notificationsHeadsUpRefactor();
        boolean z = !zNotificationsHeadsUpRefactor;
        if (!zNotificationsHeadsUpRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "NotificationsHeadsUpRefactor") + " to be enabled.");
        }
        return z;
    }
}
