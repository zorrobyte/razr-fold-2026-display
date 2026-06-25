package com.android.systemui.statusbar.notification.row.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

/* JADX INFO: compiled from: AsyncGroupHeaderViewInflation.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AsyncGroupHeaderViewInflation {
    public static final AsyncGroupHeaderViewInflation INSTANCE = new AsyncGroupHeaderViewInflation();

    private AsyncGroupHeaderViewInflation() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (Flags.notificationAsyncGroupHeaderInflation()) {
            throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "AsyncGroupHeaderViewInflation") + " is enabled.").toString());
        }
    }

    public static final boolean isEnabled() {
        return Flags.notificationAsyncGroupHeaderInflation();
    }

    public static final boolean isUnexpectedlyInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        boolean zNotificationAsyncGroupHeaderInflation = Flags.notificationAsyncGroupHeaderInflation();
        boolean z = !zNotificationAsyncGroupHeaderInflation;
        if (!zNotificationAsyncGroupHeaderInflation) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "AsyncGroupHeaderViewInflation") + " to be enabled.");
        }
        return z;
    }
}
