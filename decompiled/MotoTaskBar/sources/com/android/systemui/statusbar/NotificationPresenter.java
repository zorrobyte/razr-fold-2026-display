package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public interface NotificationPresenter extends ExpandableNotificationRow.OnExpandClickListener {
    boolean isPresenterFullyCollapsed();

    void onBindRow(ExpandableNotificationRow expandableNotificationRow);

    void onUserSwitched(int i);
}
