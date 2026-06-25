package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.FeedbackIcon;

/* JADX INFO: compiled from: NotifRowController.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotifRowController {
    void setFeedbackIcon(FeedbackIcon feedbackIcon);

    void setLastAudibleMs(long j);

    void setSystemExpanded(boolean z);
}
