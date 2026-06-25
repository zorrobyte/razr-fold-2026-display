package com.android.systemui.statusbar.notification.row;

import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationSettingsController;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.time.SystemClock;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableNotificationRowController implements NotifViewController {
    static final Uri BUBBLES_SETTING_URI = Settings.Secure.getUriFor("notification_bubbles");
    private final ActivatableNotificationViewController mActivatableNotificationViewController;
    private final boolean mAllowLongPress;
    private final String mAppName;
    private final NotificationChildrenContainerLogger mChildrenContainerLogger;
    private final SystemClock mClock;
    private final ColorUpdateLogger mColorUpdateLogger;
    private final NotificationDismissibilityProvider mDismissibilityProvider;
    private final ExpandableNotificationRowDragController mDragController;
    private final FeatureFlags mFeatureFlags;
    private final GroupExpansionManager mGroupExpansionManager;
    private final GroupMembershipManager mGroupMembershipManager;
    private final HeadsUpManager mHeadsUpManager;
    private final NotificationListContainer mListContainer;
    private final NotificationRowLogger mLogBufferLogger;
    private final String mNotificationKey;
    private final ExpandableNotificationRow.OnExpandClickListener mOnExpandClickListener;
    private final OnUserInteractionCallback mOnUserInteractionCallback;
    private final PeopleNotificationIdentifier mPeopleNotificationIdentifier;
    private final RemoteInputViewSubcomponent.Factory mRemoteInputViewSubcomponentFactory;
    private final RowContentBindStage mRowContentBindStage;
    private final NotificationSettingsController mSettingsController;
    private final SmartReplyConstants mSmartReplyConstants;
    private final SmartReplyController mSmartReplyController;
    private final NotificationRowStatsLogger mStatsLogger;
    private final IStatusBarService mStatusBarService;
    private final ExpandableNotificationRow mView;
    final NotificationSettingsController.Listener mSettingsListener = new NotificationSettingsController.Listener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController.1
        @Override // com.android.systemui.statusbar.notification.row.NotificationSettingsController.Listener
        public void onSettingChanged(Uri uri, int i, String str) {
            if (ExpandableNotificationRowController.BUBBLES_SETTING_URI.equals(uri)) {
                int userId = ExpandableNotificationRowController.this.mView.getEntry().getSbn().getUserId();
                if (userId == -1 || userId == i) {
                    ExpandableNotificationRowController.this.mView.getPrivateLayout().setBubblesEnabledForUser("1".equals(str));
                }
            }
        }
    };
    private final ExpandableNotificationRow.ExpandableNotificationRowLogger mLoggerCallback = new ExpandableNotificationRow.ExpandableNotificationRowLogger() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController.2
        @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.ExpandableNotificationRowLogger
        public void logAddTransientRow(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, int i) {
            ExpandableNotificationRowController.this.mLogBufferLogger.logAddTransientRow(notificationEntry, notificationEntry2, i);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.ExpandableNotificationRowLogger
        public void logKeepInParentChildDetached(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
            ExpandableNotificationRowController.this.mLogBufferLogger.logKeepInParentChildDetached(notificationEntry, notificationEntry2);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.ExpandableNotificationRowLogger
        public void logNotificationExpansion(String str, int i, boolean z, boolean z2) {
            ExpandableNotificationRowController.this.mStatsLogger.onNotificationExpansionChanged(str, z2, i, z);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.ExpandableNotificationRowLogger
        public void logRemoveTransientFromContainer(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
            ExpandableNotificationRowController.this.mLogBufferLogger.logRemoveTransientFromContainer(notificationEntry, notificationEntry2);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.ExpandableNotificationRowLogger
        public void logRemoveTransientFromNssl(NotificationEntry notificationEntry) {
            ExpandableNotificationRowController.this.mLogBufferLogger.logRemoveTransientFromNssl(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.ExpandableNotificationRowLogger
        public void logRemoveTransientFromViewGroup(NotificationEntry notificationEntry, ViewGroup viewGroup) {
            ExpandableNotificationRowController.this.mLogBufferLogger.logRemoveTransientFromViewGroup(notificationEntry, viewGroup);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.ExpandableNotificationRowLogger
        public void logRemoveTransientRow(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
            ExpandableNotificationRowController.this.mLogBufferLogger.logRemoveTransientRow(notificationEntry, notificationEntry2);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.ExpandableNotificationRowLogger
        public void logSkipAttachingKeepInParentChild(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
            ExpandableNotificationRowController.this.mLogBufferLogger.logSkipAttachingKeepInParentChild(notificationEntry, notificationEntry2);
        }
    };

    public ExpandableNotificationRowController(ExpandableNotificationRow expandableNotificationRow, ActivatableNotificationViewController activatableNotificationViewController, RemoteInputViewSubcomponent.Factory factory, ColorUpdateLogger colorUpdateLogger, NotificationRowLogger notificationRowLogger, NotificationChildrenContainerLogger notificationChildrenContainerLogger, NotificationListContainer notificationListContainer, SmartReplyConstants smartReplyConstants, SmartReplyController smartReplyController, SystemClock systemClock, String str, String str2, GroupMembershipManager groupMembershipManager, GroupExpansionManager groupExpansionManager, RowContentBindStage rowContentBindStage, NotificationRowStatsLogger notificationRowStatsLogger, HeadsUpManager headsUpManager, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, boolean z, OnUserInteractionCallback onUserInteractionCallback, FeatureFlags featureFlags, PeopleNotificationIdentifier peopleNotificationIdentifier, NotificationSettingsController notificationSettingsController, ExpandableNotificationRowDragController expandableNotificationRowDragController, NotificationDismissibilityProvider notificationDismissibilityProvider, IStatusBarService iStatusBarService) {
        this.mView = expandableNotificationRow;
        this.mListContainer = notificationListContainer;
        this.mRemoteInputViewSubcomponentFactory = factory;
        this.mActivatableNotificationViewController = activatableNotificationViewController;
        this.mClock = systemClock;
        this.mAppName = str;
        this.mNotificationKey = str2;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mGroupExpansionManager = groupExpansionManager;
        this.mRowContentBindStage = rowContentBindStage;
        this.mStatsLogger = notificationRowStatsLogger;
        this.mHeadsUpManager = headsUpManager;
        this.mOnExpandClickListener = onExpandClickListener;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mAllowLongPress = z;
        this.mFeatureFlags = featureFlags;
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        this.mSettingsController = notificationSettingsController;
        this.mDragController = expandableNotificationRowDragController;
        this.mChildrenContainerLogger = notificationChildrenContainerLogger;
        this.mColorUpdateLogger = colorUpdateLogger;
        this.mLogBufferLogger = notificationRowLogger;
        this.mSmartReplyConstants = smartReplyConstants;
        this.mSmartReplyController = smartReplyController;
        this.mDismissibilityProvider = notificationDismissibilityProvider;
        this.mStatusBarService = iStatusBarService;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void addChildAt(NodeController nodeController, int i) {
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) nodeController.getView();
        this.mView.addChildNotification((ExpandableNotificationRow) nodeController.getView(), i);
        this.mListContainer.notifyGroupChildAdded(expandableNotificationRow);
        expandableNotificationRow.setChangingPosition(false);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public View getChildAt(int i) {
        return this.mView.getChildNotificationAt(i);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public int getChildCount() {
        List attachedChildren = this.mView.getAttachedChildren();
        if (attachedChildren != null) {
            return attachedChildren.size();
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public String getNodeLabel() {
        return NotificationUtils.logKey(this.mView.getEntry());
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public View getView() {
        return this.mView;
    }

    public void init(NotificationEntry notificationEntry) {
        this.mActivatableNotificationViewController.init();
        this.mView.initialize(notificationEntry, this.mRemoteInputViewSubcomponentFactory, this.mAppName, this.mNotificationKey, this.mLoggerCallback, this.mGroupMembershipManager, this.mGroupExpansionManager, this.mHeadsUpManager, this.mRowContentBindStage, this.mOnExpandClickListener, this.mPeopleNotificationIdentifier, this.mOnUserInteractionCallback, this.mDismissibilityProvider, this.mChildrenContainerLogger, this.mColorUpdateLogger, this.mSmartReplyConstants, this.mSmartReplyController, this.mFeatureFlags, this.mStatusBarService);
        this.mView.setDescendantFocusability(393216);
        if (this.mAllowLongPress && this.mFeatureFlags.isEnabled(Flags.NOTIFICATION_DRAG_TO_CONTENTS)) {
            this.mView.setDragController(this.mDragController);
        }
        if (NotificationRemoteInputManager.ENABLE_REMOTE_INPUT) {
            this.mView.setDescendantFocusability(131072);
        }
        this.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController.3
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                ExpandableNotificationRowController.this.mView.getEntry().setInitializationTime(ExpandableNotificationRowController.this.mClock.elapsedRealtime());
                ExpandableNotificationRowController.this.mView.setOnKeyguard(false);
                ExpandableNotificationRowController.this.mSettingsController.addCallback(ExpandableNotificationRowController.BUBBLES_SETTING_URI, ExpandableNotificationRowController.this.mSettingsListener);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                ExpandableNotificationRowController.this.mSettingsController.removeCallback(ExpandableNotificationRowController.BUBBLES_SETTING_URI, ExpandableNotificationRowController.this.mSettingsListener);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void moveChildTo(NodeController nodeController, int i) {
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) nodeController.getView();
        expandableNotificationRow.setChangingPosition(true);
        this.mView.removeChildNotification(expandableNotificationRow);
        this.mView.addChildNotification(expandableNotificationRow, i);
        expandableNotificationRow.setChangingPosition(false);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public boolean offerToKeepInParentForAnimation() {
        if (!this.mView.isParentDismissed()) {
            return false;
        }
        this.mView.setKeepInParentForDismissAnimation(true);
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void onViewAdded() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void onViewMoved() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void onViewRemoved() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void removeChild(NodeController nodeController, boolean z) {
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) nodeController.getView();
        if (z) {
            expandableNotificationRow.setChangingPosition(true);
        }
        this.mView.removeChildNotification(expandableNotificationRow);
        if (z) {
            return;
        }
        this.mListContainer.notifyGroupChildRemoved(expandableNotificationRow, this.mView.getChildrenContainer());
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public boolean removeFromParentIfKeptForAnimation() {
        ExpandableNotificationRow notificationParent = this.mView.getNotificationParent();
        if (!this.mView.keepInParentForDismissAnimation() || notificationParent == null) {
            return false;
        }
        notificationParent.removeChildNotification(this.mView);
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public void resetKeepInParentForAnimation() {
        this.mView.setKeepInParentForDismissAnimation(false);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotifRowController
    public void setFeedbackIcon(FeedbackIcon feedbackIcon) {
        this.mView.setFeedbackIcon(feedbackIcon);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotifRowController
    public void setLastAudibleMs(long j) {
        this.mView.setLastAudiblyAlertedMs(j);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotifGroupController
    public void setNotificationGroupWhen(long j) {
        if (this.mView.isSummaryWithChildren()) {
            this.mView.setNotificationGroupWhen(j);
            return;
        }
        Log.w("NotifRowController", "Called setNotificationTime(" + j + ") on a leaf row");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotifRowController
    public void setSystemExpanded(boolean z) {
        this.mView.setSystemExpanded(z);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotifGroupController
    public void setUntruncatedChildCount(int i) {
        if (this.mView.isSummaryWithChildren()) {
            this.mView.setUntruncatedChildCount(i);
            return;
        }
        Log.w("NotifRowController", "Called setUntruncatedChildCount(" + i + ") on a leaf row");
    }
}
