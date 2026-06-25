package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.view.View;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.HeadsUpManager;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarNotificationPresenter implements NotificationPresenter {
    private final AboveShelfObserver mAboveShelfObserver;
    private final ActivityStarter mActivityStarter;
    private final HeadsUpManager mHeadsUpManager;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private final NotificationMediaManager mMediaManager;
    private final NotificationStackScrollLayoutController mNsslController;

    /* JADX INFO: renamed from: $r8$lambda$SWoNEcyVS-AcBbwAQTEP-fKUCnM, reason: not valid java name */
    public static /* synthetic */ boolean m2088$r8$lambda$SWoNEcyVSAcBbwAQTEPfKUCnM() {
        return false;
    }

    StatusBarNotificationPresenter(Context context, HeadsUpManager headsUpManager, ActivityStarter activityStarter, NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationMediaManager notificationMediaManager, NotificationRemoteInputManager notificationRemoteInputManager, NotificationRemoteInputManager.Callback callback) {
        this.mActivityStarter = activityStarter;
        this.mHeadsUpManager = headsUpManager;
        this.mNsslController = notificationStackScrollLayoutController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mMediaManager = notificationMediaManager;
        this.mAboveShelfObserver = new AboveShelfObserver(notificationStackScrollLayoutController.getView());
        notificationRemoteInputManager.setUpWithCallback(callback, notificationStackScrollLayoutController.createDelegate());
        notificationLockscreenUserManager.setUpWithPresenter(this);
        onUserSwitched(notificationLockscreenUserManager.getCurrentUserId());
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public boolean isPresenterFullyCollapsed() {
        return false;
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void onBindRow(ExpandableNotificationRow expandableNotificationRow) {
        expandableNotificationRow.setAboveShelfChangedListener(this.mAboveShelfObserver);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.OnExpandClickListener
    public void onExpandClicked(NotificationEntry notificationEntry, View view, boolean z) {
        this.mHeadsUpManager.setExpanded(notificationEntry, z);
        if (z) {
            this.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda0
                @Override // com.android.systemui.displays.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    return StatusBarNotificationPresenter.m2088$r8$lambda$SWoNEcyVSAcBbwAQTEPfKUCnM();
                }
            }, null, false);
        }
    }

    @Override // com.android.systemui.statusbar.NotificationPresenter
    public void onUserSwitched(int i) {
        this.mHeadsUpManager.setUser(i);
        this.mMediaManager.clearCurrentMediaNotification();
    }
}
