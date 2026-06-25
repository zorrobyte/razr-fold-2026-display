package com.android.systemui.statusbar.notification.footer.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

/* JADX INFO: compiled from: FooterViewRefactor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FooterViewRefactor {
    public static final FooterViewRefactor INSTANCE = new FooterViewRefactor();

    private FooterViewRefactor() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (Flags.notificationsFooterViewRefactor()) {
            throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "FooterViewRefactor") + " is enabled.").toString());
        }
    }

    public static final boolean isEnabled() {
        return Flags.notificationsFooterViewRefactor();
    }

    public static final boolean isUnexpectedlyInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        boolean zNotificationsFooterViewRefactor = Flags.notificationsFooterViewRefactor();
        boolean z = !zNotificationsFooterViewRefactor;
        if (!zNotificationsFooterViewRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "FooterViewRefactor") + " to be enabled.");
        }
        return z;
    }
}
