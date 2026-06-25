package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.RemoteException;
import android.view.View;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarRemoteInputCallback implements NotificationRemoteInputManager.Callback {
    private final ActivityIntentHelper mActivityIntentHelper;
    private final ActivityStarter mActivityStarter;
    private final Context mContext;
    private Executor mExecutor;
    private final GroupExpansionManager mGroupExpansionManager;
    private final NotificationLockscreenUserManager mLockscreenUserManager;

    public static /* synthetic */ boolean $r8$lambda$pOD2pdATCSIuv7GtECxSjT5_hIk(NotificationRemoteInputManager.ClickHandler clickHandler) {
        try {
            ActivityManager.getService().resumeAppSwitches();
        } catch (RemoteException unused) {
        }
        return clickHandler.handleClick();
    }

    public StatusBarRemoteInputCallback(Context context, GroupExpansionManager groupExpansionManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ActivityStarter activityStarter, Executor executor) {
        this.mContext = context;
        this.mExecutor = executor;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mActivityStarter = activityStarter;
        this.mActivityIntentHelper = new ActivityIntentHelper(context);
        this.mGroupExpansionManager = groupExpansionManager;
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.Callback
    public boolean handleRemoteViewClick(View view, PendingIntent pendingIntent, boolean z, Integer num, final NotificationRemoteInputManager.ClickHandler clickHandler) {
        if (!pendingIntent.isActivity() && !z) {
            return clickHandler.handleClick();
        }
        this.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback$$ExternalSyntheticLambda0
            @Override // com.android.systemui.displays.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                return StatusBarRemoteInputCallback.$r8$lambda$pOD2pdATCSIuv7GtECxSjT5_hIk(clickHandler);
            }
        }, null, this.mActivityIntentHelper.wouldPendingLaunchResolverActivity(pendingIntent, this.mLockscreenUserManager.getCurrentUserId()));
        return true;
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.Callback
    public void onLockedWorkRemoteInput(int i, ExpandableNotificationRow expandableNotificationRow, View view) {
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.Callback
    public void onMakeExpandedVisibleForRemoteInput(ExpandableNotificationRow expandableNotificationRow, View view, boolean z, Runnable runnable) {
        if (expandableNotificationRow.isChildInGroup() && !expandableNotificationRow.areChildrenExpanded()) {
            this.mGroupExpansionManager.toggleGroupExpansion(expandableNotificationRow.getEntry());
        }
        expandableNotificationRow.setUserExpanded(true);
        expandableNotificationRow.getPrivateLayout().setOnExpandedVisibleListener(runnable);
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.Callback
    public boolean shouldHandleRemoteInput(View view, PendingIntent pendingIntent) {
        return false;
    }
}
