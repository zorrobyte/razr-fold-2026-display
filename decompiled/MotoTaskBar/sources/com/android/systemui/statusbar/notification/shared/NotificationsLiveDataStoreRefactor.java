package com.android.systemui.statusbar.notification.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

/* JADX INFO: compiled from: NotificationsLiveDataStoreRefactor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationsLiveDataStoreRefactor {
    public static final NotificationsLiveDataStoreRefactor INSTANCE = new NotificationsLiveDataStoreRefactor();

    private NotificationsLiveDataStoreRefactor() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (Flags.notificationsLiveDataStoreRefactor()) {
            throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "NotificationsLiveDataStoreRefactor") + " is enabled.").toString());
        }
    }

    public static final boolean isEnabled() {
        return Flags.notificationsLiveDataStoreRefactor();
    }
}
