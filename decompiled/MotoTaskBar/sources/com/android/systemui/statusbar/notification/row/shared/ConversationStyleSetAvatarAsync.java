package com.android.systemui.statusbar.notification.row.shared;

import com.android.systemui.flags.RefactorFlagUtils;

/* JADX INFO: compiled from: ConversationStyleSetAvatarAsync.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ConversationStyleSetAvatarAsync {
    public static final ConversationStyleSetAvatarAsync INSTANCE = new ConversationStyleSetAvatarAsync();

    private ConversationStyleSetAvatarAsync() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "android.widget.flags.conversation_style_set_avatar_async") + " is enabled.").toString());
    }

    public static final boolean isEnabled() {
        return true;
    }
}
