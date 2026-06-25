package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.view.View;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* JADX INFO: compiled from: NotificationCompactHeadsUpTemplateViewWrapper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationCompactHeadsUpTemplateViewWrapper extends NotificationTemplateViewWrapper {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationCompactHeadsUpTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        context.getClass();
        view.getClass();
        expandableNotificationRow.getClass();
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setFeedbackIcon(FeedbackIcon feedbackIcon) {
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public void setRecentlyAudiblyAlerted(boolean z) {
    }
}
