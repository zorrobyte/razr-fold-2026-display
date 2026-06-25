package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SingleLineViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ConversationData {
    private final ConversationAvatar avatar;
    private final CharSequence conversationSenderName;

    public ConversationData(CharSequence charSequence, ConversationAvatar conversationAvatar) {
        conversationAvatar.getClass();
        this.conversationSenderName = charSequence;
        this.avatar = conversationAvatar;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConversationData)) {
            return false;
        }
        ConversationData conversationData = (ConversationData) obj;
        return Intrinsics.areEqual(this.conversationSenderName, conversationData.conversationSenderName) && Intrinsics.areEqual(this.avatar, conversationData.avatar);
    }

    public final ConversationAvatar getAvatar() {
        return this.avatar;
    }

    public final CharSequence getConversationSenderName() {
        return this.conversationSenderName;
    }

    public int hashCode() {
        CharSequence charSequence = this.conversationSenderName;
        return ((charSequence == null ? 0 : charSequence.hashCode()) * 31) + this.avatar.hashCode();
    }

    public String toString() {
        CharSequence charSequence = this.conversationSenderName;
        return "ConversationData(conversationSenderName=" + ((Object) charSequence) + ", avatar=" + this.avatar + ")";
    }
}
