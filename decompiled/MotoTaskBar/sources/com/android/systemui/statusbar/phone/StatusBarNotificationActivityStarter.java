package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.service.dreams.IDreamManager;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.view.View;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarNotificationActivityStarter implements NotificationActivityStarter {
    private final ActivityIntentHelper mActivityIntentHelper;
    private final ActivityStarter mActivityStarter;
    private final NotificationClickNotifier mClickNotifier;
    private final Context mContext;
    private final int mDisplayId;
    private final IDreamManager mDreamManager;
    private final HeadsUpManager mHeadsUpManager;
    private boolean mIsCollapsingToShowActivityOverLockscreen;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private final Handler mMainThreadHandler;
    private final OnUserInteractionCallback mOnUserInteractionCallback;
    private final NotificationPresenter mPresenter;
    private final QSNotificationPanelController mQSNotificationPanelController;
    private final NotificationRemoteInputManager mRemoteInputManager;
    private final Executor mUiBgExecutor;
    private final UserTracker mUserTracker;
    private final NotificationVisibilityProvider mVisibilityProvider;

    StatusBarNotificationActivityStarter(Context context, int i, Handler handler, Executor executor, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManager headsUpManager, ActivityStarter activityStarter, NotificationClickNotifier notificationClickNotifier, IDreamManager iDreamManager, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ActivityIntentHelper activityIntentHelper, OnUserInteractionCallback onUserInteractionCallback, NotificationPresenter notificationPresenter, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, UserTracker userTracker, QSNotificationPanelController qSNotificationPanelController) {
        this.mContext = context;
        this.mDisplayId = i;
        this.mMainThreadHandler = handler;
        this.mUiBgExecutor = executor;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mHeadsUpManager = headsUpManager;
        this.mActivityStarter = activityStarter;
        this.mClickNotifier = notificationClickNotifier;
        this.mDreamManager = iDreamManager;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mActivityIntentHelper = activityIntentHelper;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mPresenter = notificationPresenter;
        this.mUserTracker = userTracker;
        this.mQSNotificationPanelController = qSNotificationPanelController;
        launchFullScreenIntentProvider.registerListener(new LaunchFullScreenIntentProvider.Listener() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda3
            @Override // com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider.Listener
            public final void onFullScreenIntentRequested(NotificationEntry notificationEntry) {
                this.f$0.lambda$new$0(notificationEntry);
            }
        });
    }

    static Bundle getActivityOptions(int i) {
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setSplashScreenStyle(0);
        activityOptionsMakeBasic.setLaunchDisplayId(i);
        activityOptionsMakeBasic.setCallerDisplayId(i);
        activityOptionsMakeBasic.setPendingIntentBackgroundActivityLaunchAllowed(true);
        return activityOptionsMakeBasic.toBundle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleNotificationClickAfterKeyguardDismissed(final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, final PendingIntent pendingIntent, final boolean z, final boolean z2, boolean z3) {
        new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleNotificationClickAfterKeyguardDismissed$1(notificationEntry, expandableNotificationRow, pendingIntent, z, z2);
            }
        }.run();
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: handleNotificationClickAfterPanelCollapsed, reason: merged with bridge method [inline-methods] */
    public void lambda$handleNotificationClickAfterKeyguardDismissed$1(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, PendingIntent pendingIntent, boolean z, boolean z2) {
        String key = notificationEntry.getKey();
        try {
            ActivityManager.getService().resumeAppSwitches();
        } catch (RemoteException unused) {
        }
        Intent intentPutExtra = null;
        CharSequence charSequence = !TextUtils.isEmpty(notificationEntry.remoteInputText) ? notificationEntry.remoteInputText : null;
        if (!TextUtils.isEmpty(charSequence) && !this.mRemoteInputManager.isSpinning(key)) {
            intentPutExtra = new Intent().putExtra("android.remoteInputDraft", charSequence.toString());
        }
        boolean zCanBubble = notificationEntry.canBubble();
        startNotificationIntent(pendingIntent, intentPutExtra, notificationEntry, expandableNotificationRow, z2, z);
        NotificationVisibility notificationVisibilityObtain = this.mVisibilityProvider.obtain(notificationEntry, true);
        if (!zCanBubble && (shouldAutoCancel(notificationEntry.getSbn()) || this.mRemoteInputManager.isNotificationKeptForRemoteInputHistory(key))) {
            final Runnable runnableRegisterFutureDismissal = this.mOnUserInteractionCallback.registerFutureDismissal(notificationEntry, 1);
            this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    runnableRegisterFutureDismissal.run();
                }
            });
        }
        this.mClickNotifier.onNotificationClick(key, notificationVisibilityObtain);
        this.mIsCollapsingToShowActivityOverLockscreen = false;
    }

    private static boolean shouldAutoCancel(StatusBarNotification statusBarNotification) {
        return (statusBarNotification.getNotification().flags & 16) == 16;
    }

    private void startNotificationIntent(PendingIntent pendingIntent, Intent intent, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, boolean z, boolean z2) {
        try {
            pendingIntent.sendAndReturnResult(this.mContext, 0, intent, null, null, null, getActivityOptions(this.mDisplayId));
            this.mQSNotificationPanelController.requestHideNotificationPanel(this.mDisplayId);
        } catch (PendingIntent.CanceledException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: launchFullScreenIntent, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0(NotificationEntry notificationEntry) {
    }

    @Override // com.android.systemui.statusbar.notification.NotificationActivityStarter
    public void onDragSuccess(NotificationEntry notificationEntry) {
        NotificationVisibility notificationVisibilityObtain = this.mVisibilityProvider.obtain(notificationEntry, true);
        String key = notificationEntry.getKey();
        if (shouldAutoCancel(notificationEntry.getSbn()) || this.mRemoteInputManager.isNotificationKeptForRemoteInputHistory(key)) {
            final Runnable runnableRegisterFutureDismissal = this.mOnUserInteractionCallback.registerFutureDismissal(notificationEntry, 1);
            this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    runnableRegisterFutureDismissal.run();
                }
            });
        }
        this.mClickNotifier.onNotificationClick(key, notificationVisibilityObtain);
        this.mIsCollapsingToShowActivityOverLockscreen = false;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationActivityStarter
    public void onNotificationClicked(final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow) {
        if (this.mRemoteInputManager.isRemoteInputActive(notificationEntry)) {
            this.mRemoteInputManager.closeRemoteInputs();
            return;
        }
        Notification notification = notificationEntry.getSbn().getNotification();
        PendingIntent pendingIntent = notification.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification.fullScreenIntent;
        }
        final PendingIntent pendingIntent2 = pendingIntent;
        boolean zIsBubble = notificationEntry.isBubble();
        if (pendingIntent2 != null || zIsBubble) {
            boolean z = false;
            final boolean z2 = (pendingIntent2 == null || !pendingIntent2.isActivity() || zIsBubble) ? false : true;
            if (z2 && this.mActivityIntentHelper.wouldPendingLaunchResolverActivity(pendingIntent2, this.mLockscreenUserManager.getCurrentUserId())) {
                z = true;
            }
            final boolean z3 = false;
            this.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.1
                @Override // com.android.systemui.displays.ActivityStarter.OnDismissAction
                public boolean onDismiss() {
                    return StatusBarNotificationActivityStarter.this.handleNotificationClickAfterKeyguardDismissed(notificationEntry, expandableNotificationRow, pendingIntent2, z2, false, z3);
                }
            }, null, z);
        }
    }

    @Override // com.android.systemui.statusbar.notification.NotificationActivityStarter
    public void startHistoryIntent(View view, boolean z) {
    }
}
