package com.android.systemui.statusbar.notification.row.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

/* JADX INFO: compiled from: AsyncHybridViewInflation.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AsyncHybridViewInflation {
    public static final AsyncHybridViewInflation INSTANCE = new AsyncHybridViewInflation();

    private AsyncHybridViewInflation() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (Flags.notificationAsyncHybridViewInflation()) {
            throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "AsyncHybridViewInflation") + " is enabled.").toString());
        }
    }

    public static final boolean isEnabled() {
        return Flags.notificationAsyncHybridViewInflation();
    }

    public static final boolean isUnexpectedlyInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        boolean zNotificationAsyncHybridViewInflation = Flags.notificationAsyncHybridViewInflation();
        boolean z = !zNotificationAsyncHybridViewInflation;
        if (!zNotificationAsyncHybridViewInflation) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "AsyncHybridViewInflation") + " to be enabled.");
        }
        return z;
    }
}
