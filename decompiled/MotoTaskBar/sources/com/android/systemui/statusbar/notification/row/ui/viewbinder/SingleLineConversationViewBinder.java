package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.row.HybridConversationNotificationView;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationAvatar;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationData;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;

/* JADX INFO: compiled from: SingleLineConversationViewBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SingleLineConversationViewBinder {
    public static final SingleLineConversationViewBinder INSTANCE = new SingleLineConversationViewBinder();

    private SingleLineConversationViewBinder() {
    }

    public static final void bind(SingleLineViewModel singleLineViewModel, HybridNotificationView hybridNotificationView) {
        ConversationAvatar avatar;
        singleLineViewModel.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        AsyncHybridViewInflation asyncHybridViewInflation = AsyncHybridViewInflation.INSTANCE;
        boolean zNotificationAsyncHybridViewInflation = Flags.notificationAsyncHybridViewInflation();
        if (!zNotificationAsyncHybridViewInflation) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "AsyncHybridViewInflation") + " to be enabled.");
        }
        if (zNotificationAsyncHybridViewInflation) {
            if (!(hybridNotificationView instanceof HybridConversationNotificationView) || !singleLineViewModel.isConversation()) {
                SingleLineViewBinder.bind(singleLineViewModel, hybridNotificationView);
                return;
            }
            ConversationData conversationData = singleLineViewModel.getConversationData();
            if (conversationData != null && (avatar = conversationData.getAvatar()) != null) {
                ((HybridConversationNotificationView) hybridNotificationView).setAvatar(avatar);
            }
            HybridConversationNotificationView hybridConversationNotificationView = (HybridConversationNotificationView) hybridNotificationView;
            CharSequence titleText = singleLineViewModel.getTitleText();
            CharSequence contentText = singleLineViewModel.getContentText();
            ConversationData conversationData2 = singleLineViewModel.getConversationData();
            hybridConversationNotificationView.setText(titleText, contentText, conversationData2 != null ? conversationData2.getConversationSenderName() : null);
        }
    }
}
