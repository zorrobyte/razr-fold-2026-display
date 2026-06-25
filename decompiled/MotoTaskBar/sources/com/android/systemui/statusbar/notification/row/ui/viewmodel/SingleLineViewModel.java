package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SingleLineViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SingleLineViewModel {
    private CharSequence contentText;
    private ConversationData conversationData;
    private CharSequence titleText;

    public SingleLineViewModel(CharSequence charSequence, CharSequence charSequence2, ConversationData conversationData) {
        this.titleText = charSequence;
        this.contentText = charSequence2;
        this.conversationData = conversationData;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SingleLineViewModel)) {
            return false;
        }
        SingleLineViewModel singleLineViewModel = (SingleLineViewModel) obj;
        return Intrinsics.areEqual(this.titleText, singleLineViewModel.titleText) && Intrinsics.areEqual(this.contentText, singleLineViewModel.contentText) && Intrinsics.areEqual(this.conversationData, singleLineViewModel.conversationData);
    }

    public final CharSequence getContentText() {
        return this.contentText;
    }

    public final ConversationData getConversationData() {
        return this.conversationData;
    }

    public final CharSequence getTitleText() {
        return this.titleText;
    }

    public int hashCode() {
        CharSequence charSequence = this.titleText;
        int iHashCode = (charSequence == null ? 0 : charSequence.hashCode()) * 31;
        CharSequence charSequence2 = this.contentText;
        int iHashCode2 = (iHashCode + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        ConversationData conversationData = this.conversationData;
        return iHashCode2 + (conversationData != null ? conversationData.hashCode() : 0);
    }

    public final boolean isConversation() {
        return this.conversationData != null;
    }

    public String toString() {
        CharSequence charSequence = this.titleText;
        CharSequence charSequence2 = this.contentText;
        return "SingleLineViewModel(titleText=" + ((Object) charSequence) + ", contentText=" + ((Object) charSequence2) + ", conversationData=" + this.conversationData + ")";
    }
}
