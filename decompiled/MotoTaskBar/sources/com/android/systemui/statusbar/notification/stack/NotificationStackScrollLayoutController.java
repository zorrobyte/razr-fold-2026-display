package com.android.systemui.statusbar.notification.stack;

import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.os.Trace;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.ExpandHelper;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.fake.FlagsFake;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.VisibilityLocationProviderDelegator;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder;
import com.android.systemui.statusbar.policy.AnimationStateHandler;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.util.Compile;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationStackScrollLayoutController implements Dumpable {
    private static final boolean DEBUG;
    private static final Property HIDE_ALPHA_PROPERTY;
    private final ActivityStarter mActivityStarter;
    private final boolean mAllowLongPress;
    private final ColorUpdateLogger mColorUpdateLogger;
    private final ConfigurationController mConfigurationController;
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final NotificationDismissibilityProvider mDismissibilityProvider;
    private final DumpManager mDumpManager;
    private final DynamicPrivacyController mDynamicPrivacyController;
    private final GroupExpansionManager mGroupExpansionManager;
    private final HeadsUpManager mHeadsUpManager;
    private Boolean mHistoryEnabled;
    private final InteractionJankMonitor mJankMonitor;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private final NotificationStackScrollLogger mLogger;
    private View mLongPressedView;
    private final NotifCollection mNotifCollection;
    private final NotifPipeline mNotifPipeline;
    private final NotifStackController mNotifStackController;
    private NotificationActivityStarter mNotificationActivityStarter;
    private final NotificationListContainerImpl mNotificationListContainer;
    private final NotificationRoundnessManager mNotificationRoundnessManager;
    private final NotificationStackSizeCalculator mNotificationStackSizeCalculator;
    private final NotificationSwipeHelper.Builder mNotificationSwipeHelperBuilder;
    private final NotificationTargetsHelper mNotificationTargetsHelper;
    private final NotificationsController mNotificationsController;
    private final NotificationRemoteInputManager mRemoteInputManager;
    private final SecureSettings mSecureSettings;
    private final SeenNotificationsInteractor mSeenNotificationsInteractor;
    private final SensitiveNotificationProtectionController mSensitiveNotificationProtectionController;
    private final SectionHeaderController mSilentHeaderController;
    private final StackStateLogger mStackStateLogger;
    private NotificationSwipeHelper mSwipeHelper;
    private final UiEventLogger mUiEventLogger;
    private NotificationStackScrollLayout mView;
    private final NotificationListViewBinder mViewBinder;
    private final VisibilityLocationProviderDelegator mVisibilityLocationProviderDelegator;
    private final NotificationVisibilityProvider mVisibilityProvider;
    private boolean mIsInTransitionToAod = false;
    final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.1
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            NotificationStackScrollLayoutController.this.mColorUpdateLogger.logTriggerEvent("NSSLC.onViewAttachedToWindow()");
            NotificationStackScrollLayoutController.this.mConfigurationController.addCallback(NotificationStackScrollLayoutController.this.mConfigurationListener);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            NotificationStackScrollLayoutController.this.mColorUpdateLogger.logTriggerEvent("NSSLC.onViewDetachedFromWindow()");
            NotificationStackScrollLayoutController.this.mConfigurationController.removeCallback(NotificationStackScrollLayoutController.this.mConfigurationListener);
        }
    };
    private ObjectAnimator mHideAlphaAnimator = null;
    private final DeviceProvisionedController.DeviceProvisionedListener mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.3
        private void updateCurrentUserIsSetup() {
            NotificationStackScrollLayoutController.this.mView.setCurrentUserSetup(NotificationStackScrollLayoutController.this.mDeviceProvisionedController.isCurrentUserSetup());
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onDeviceProvisionedChanged() {
            updateCurrentUserIsSetup();
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSetupChanged() {
            updateCurrentUserIsSetup();
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onUserSwitched() {
            updateCurrentUserIsSetup();
        }
    };
    private final Runnable mSensitiveStateChangedListener = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.4
        @Override // java.lang.Runnable
        public void run() {
            NotificationStackScrollLayoutController.this.updateSensitivenessWithAnimation(false);
        }
    };
    private final DynamicPrivacyController.Listener mDynamicPrivacyControllerListener = new DynamicPrivacyController.Listener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda9
    };
    final ConfigurationController.ConfigurationListener mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.5
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onConfigChanged(Configuration configuration) {
            NotificationStackScrollLayoutController.this.updateResources();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onDensityOrFontScaleChanged() {
            if (!FooterViewRefactor.isEnabled()) {
                NotificationStackScrollLayoutController.this.updateShowEmptyShadeView();
            }
            NotificationStackScrollLayoutController.this.mView.reinflateViews();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onThemeChanged() {
            NotificationStackScrollLayoutController.this.mColorUpdateLogger.logTriggerEvent("NSSLC.onThemeChanged()", "mode=" + NotificationStackScrollLayoutController.this.mConfigurationController.getNightModeName());
            NotificationStackScrollLayoutController.this.mView.updateCornerRadius();
            NotificationStackScrollLayoutController.this.mView.updateBgColor();
            NotificationStackScrollLayoutController.this.mView.updateDecorViews();
            NotificationStackScrollLayoutController.this.mView.reinflateViews();
            if (FooterViewRefactor.isEnabled()) {
                return;
            }
            NotificationStackScrollLayoutController.this.updateShowEmptyShadeView();
            NotificationStackScrollLayoutController.this.updateFooter();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onUiModeChanged() {
            NotificationStackScrollLayoutController.this.mColorUpdateLogger.logTriggerEvent("NSSLC.onUiModeChanged()", "mode=" + NotificationStackScrollLayoutController.this.mConfigurationController.getNightModeName());
            NotificationStackScrollLayoutController.this.mView.updateBgColor();
            NotificationStackScrollLayoutController.this.mView.updateDecorViews();
        }
    };
    private NotifStats mNotifStats = NotifStats.getEmpty();
    private float mMaxAlphaForKeyguard = 1.0f;
    private String mMaxAlphaForKeyguardSource = "constructor";
    private float mMaxAlphaForUnhide = 1.0f;
    private float mMaxAlphaForGlanceableHub = 1.0f;
    private final NotificationLockscreenUserManager.UserChangedListener mLockscreenUserChangeListener = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.6
        @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
        public void onUserChanged(int i) {
            NotificationStackScrollLayoutController.this.updateSensitivenessWithAnimation(false);
            NotificationStackScrollLayoutController.this.mHistoryEnabled = null;
            if (FooterViewRefactor.isEnabled()) {
                return;
            }
            NotificationStackScrollLayoutController.this.updateFooter();
        }
    };
    final NotificationSwipeHelper.NotificationCallback mNotificationCallback = new NotificationSwipeHelper.NotificationCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.7
        @Override // com.android.systemui.SwipeHelper.Callback
        public boolean canChildBeDismissed(View view) {
            return NotificationStackScrollLayout.canChildBeDismissed(view);
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public boolean canChildBeDismissedInDirection(View view, boolean z) {
            return canChildBeDismissed(view);
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public View getChildAtPosition(MotionEvent motionEvent) {
            ExpandableNotificationRow notificationParent;
            ExpandableView childAtPosition = NotificationStackScrollLayoutController.this.mView.getChildAtPosition(motionEvent.getX(), motionEvent.getY(), true, false, true);
            return ((childAtPosition instanceof ExpandableNotificationRow) && (notificationParent = ((ExpandableNotificationRow) childAtPosition).getNotificationParent()) != null && notificationParent.areChildrenExpanded()) ? notificationParent : childAtPosition;
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public int getConstrainSwipeStartPosition() {
            return 0;
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public float getFalsingThresholdFactor() {
            return 0.0f;
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.NotificationCallback
        public float getTotalTranslationLength(View view) {
            return NotificationStackScrollLayoutController.this.mView.getTotalTranslationLength(view);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.NotificationCallback
        public void handleChildViewDismissed(View view) {
            NotificationStackScrollLayoutController.this.mView.onSwipeEnd();
            if (NotificationStackScrollLayoutController.this.mView.getClearAllInProgress()) {
                return;
            }
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.isHeadsUp()) {
                    NotificationStackScrollLayoutController.this.mHeadsUpManager.addSwipedOutNotification(expandableNotificationRow.getEntry().getSbn().getKey());
                }
                expandableNotificationRow.performDismiss(false);
            }
            NotificationStackScrollLayoutController.this.mView.addSwipedOutView(view);
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public void onBeginDrag(View view) {
            NotificationStackScrollLayoutController.this.mView.onSwipeBegin(view);
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public void onChildDismissed(View view) {
            if (view instanceof ActivatableNotificationView) {
                ActivatableNotificationView activatableNotificationView = (ActivatableNotificationView) view;
                if (!activatableNotificationView.isDismissed()) {
                    handleChildViewDismissed(view);
                }
                activatableNotificationView.removeFromTransientContainer();
                if (activatableNotificationView instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) activatableNotificationView).removeChildrenWithKeepInParent();
                }
            }
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public void onChildSnappedBack(View view, float f) {
            NotificationStackScrollLayoutController.this.mView.onSwipeEnd();
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.isPinned() && !canChildBeDismissed(expandableNotificationRow) && expandableNotificationRow.getEntry().getSbn().getNotification().fullScreenIntent == null) {
                    NotificationStackScrollLayoutController.this.mHeadsUpManager.removeNotification(expandableNotificationRow.getEntry().getSbn().getKey(), true);
                }
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.NotificationCallback
        public void onDismiss() {
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public void onDragCancelled(View view) {
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public void onLongPressSent(View view) {
            NotificationStackScrollLayoutController.this.mLongPressedView = view;
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.NotificationCallback
        public boolean shouldDismissQuickly() {
            return NotificationStackScrollLayoutController.this.mView.isExpanded() && NotificationStackScrollLayoutController.this.mView.isFullyAwake();
        }

        @Override // com.android.systemui.SwipeHelper.Callback
        public boolean updateSwipeProgress(View view, boolean z, float f) {
            return false;
        }
    };
    private final OnHeadsUpChangedListener mOnHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.8
        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public void onHeadsUpPinnedModeChanged(boolean z) {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            NotificationStackScrollLayoutController.this.mView.setInHeadsUpPinnedMode(z);
        }

        @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
        public void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            NotificationEntry topEntry = NotificationStackScrollLayoutController.this.mHeadsUpManager.getTopEntry();
            NotificationStackScrollLayoutController.this.mView.setTopHeadsUpRow(topEntry != null ? topEntry.getRow() : null);
            NotificationStackScrollLayoutController.this.generateHeadsUpAnimation(notificationEntry, z);
        }
    };

    class NotifStackControllerImpl implements NotifStackController {
        private NotifStackControllerImpl() {
        }

        @Override // com.android.systemui.statusbar.notification.collection.render.NotifStackController
        public void setNotifStats(NotifStats notifStats) {
            FooterViewRefactor.assertInLegacyMode();
            NotificationStackScrollLayoutController.this.mNotifStats = notifStats;
            if (FooterViewRefactor.isEnabled()) {
                return;
            }
            NotificationStackScrollLayoutController.this.mView.setHasFilteredOutSeenNotifications(((Boolean) NotificationStackScrollLayoutController.this.mSeenNotificationsInteractor.getHasFilteredOutSeenNotifications().getValue()).booleanValue());
            NotificationStackScrollLayoutController.this.updateFooter();
            NotificationStackScrollLayoutController.this.updateShowEmptyShadeView();
            NotificationStackScrollLayoutController.this.updateImportantForAccessibility();
        }
    }

    class NotificationListContainerImpl implements NotificationListContainer {
        private NotificationListContainerImpl() {
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public void addContainerViewAt(View view, int i) {
            NotificationStackScrollLayoutController.this.mView.addContainerViewAt(view, i);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public void bindRow(final ExpandableNotificationRow expandableNotificationRow) {
            expandableNotificationRow.setHeadsUpAnimatingAwayListener(new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    expandableNotificationRow.getEntry();
                }
            });
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public void changeViewPosition(ExpandableView expandableView, int i) {
            NotificationStackScrollLayoutController.this.mView.changeViewPosition(expandableView, i);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public void cleanUpViewStateForEntry(NotificationEntry notificationEntry) {
            NotificationStackScrollLayoutController.this.mView.cleanUpViewStateForEntry(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public View getContainerChildAt(int i) {
            return NotificationStackScrollLayoutController.this.mView.getContainerChildAt(i);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public int getContainerChildCount() {
            return NotificationStackScrollLayoutController.this.mView.getContainerChildCount();
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public ViewGroup getViewParentForNotification(NotificationEntry notificationEntry) {
            return NotificationStackScrollLayoutController.this.mView.getViewParentForNotification(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.VisibilityLocationProvider
        public boolean isInVisibleLocation(NotificationEntry notificationEntry) {
            return NotificationStackScrollLayoutController.this.isInVisibleLocation(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public void notifyGroupChildAdded(ExpandableView expandableView) {
            NotificationStackScrollLayoutController.this.mView.notifyGroupChildAdded(expandableView);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public void notifyGroupChildRemoved(ExpandableView expandableView, ViewGroup viewGroup) {
            NotificationStackScrollLayoutController.this.mView.notifyGroupChildRemoved(expandableView, viewGroup);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public void onHeightChanged(ExpandableView expandableView, boolean z) {
            NotificationStackScrollLayoutController.this.mView.onChildHeightChanged(expandableView, z);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public void onReset(ExpandableView expandableView) {
            NotificationStackScrollLayoutController.this.mView.onChildHeightReset(expandableView);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public void removeContainerView(View view) {
            NotificationStackScrollLayoutController.this.mView.removeContainerView(view);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public void setChildLocationsChangedListener(NotificationLogger.OnChildLocationsChangedListener onChildLocationsChangedListener) {
            NotificationStackScrollLayoutController.this.mView.setChildLocationsChangedListener(onChildLocationsChangedListener);
        }

        @Override // com.android.systemui.statusbar.notification.stack.NotificationListContainer
        public void setChildTransferInProgress(boolean z) {
            NotificationStackScrollLayoutController.this.mView.setChildTransferInProgress(z);
        }
    }

    enum NotificationPanelEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        DISMISS_ALL_NOTIFICATIONS_PANEL(312),
        DISMISS_SILENT_NOTIFICATIONS_PANEL(314);

        private final int mId;

        NotificationPanelEvent(int i) {
            this.mId = i;
        }

        public static UiEventLogger.UiEventEnum fromSelection(int i) {
            if (i == 0) {
                return DISMISS_ALL_NOTIFICATIONS_PANEL;
            }
            if (i == 2) {
                return DISMISS_SILENT_NOTIFICATIONS_PANEL;
            }
            if (!NotificationStackScrollLayoutController.DEBUG) {
                return INVALID;
            }
            throw new IllegalArgumentException("Unexpected selection" + i);
        }

        public int getId() {
            return this.mId;
        }
    }

    class TouchHandler implements Gefingerpoken {
        TouchHandler() {
        }

        private void traceJankOnTouchEvent(int i, boolean z) {
            if (NotificationStackScrollLayoutController.this.mJankMonitor == null) {
                Log.w("StackScrollerController", "traceJankOnTouchEvent, mJankMonitor is null");
                return;
            }
            if (i == 0) {
                if (z) {
                    NotificationStackScrollLayoutController.this.mJankMonitor.begin(NotificationStackScrollLayoutController.this.mView, 2);
                }
            } else {
                if (i != 1) {
                    if (i == 3 && z) {
                        NotificationStackScrollLayoutController.this.mJankMonitor.cancel(2);
                        return;
                    }
                    return;
                }
                if (!z || NotificationStackScrollLayoutController.this.mView.isFlingAfterUpEvent()) {
                    return;
                }
                NotificationStackScrollLayoutController.this.mJankMonitor.end(2);
            }
        }

        @Override // com.android.systemui.Gefingerpoken
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            NotificationStackScrollLayoutController.this.mView.initDownStates(motionEvent);
            NotificationStackScrollLayoutController.this.mView.handleEmptySpaceClick(motionEvent);
            boolean zOnInterceptTouchEvent = NotificationStackScrollLayoutController.this.mLongPressedView != null ? NotificationStackScrollLayoutController.this.mSwipeHelper.onInterceptTouchEvent(motionEvent) : false;
            boolean zOnInterceptTouchEvent2 = (NotificationStackScrollLayoutController.this.mLongPressedView != null || NotificationStackScrollLayoutController.this.mSwipeHelper.isSwiping() || NotificationStackScrollLayoutController.this.mView.getOnlyScrollingInThisMotion()) ? false : NotificationStackScrollLayoutController.this.mView.getExpandHelper().onInterceptTouchEvent(motionEvent);
            boolean zOnInterceptTouchEventScroll = (NotificationStackScrollLayoutController.this.mLongPressedView != null || NotificationStackScrollLayoutController.this.mSwipeHelper.isSwiping() || NotificationStackScrollLayoutController.this.mView.isExpandingNotification()) ? false : NotificationStackScrollLayoutController.this.mView.onInterceptTouchEventScroll(motionEvent);
            boolean zOnInterceptTouchEvent3 = (NotificationStackScrollLayoutController.this.mLongPressedView != null || NotificationStackScrollLayoutController.this.mView.isBeingDragged() || NotificationStackScrollLayoutController.this.mView.isExpandingNotification() || NotificationStackScrollLayoutController.this.mView.getExpandedInThisMotion() || NotificationStackScrollLayoutController.this.mView.getOnlyScrollingInThisMotion() || NotificationStackScrollLayoutController.this.mView.getDisallowDismissInThisMotion()) ? false : NotificationStackScrollLayoutController.this.mSwipeHelper.onInterceptTouchEvent(motionEvent);
            if (motionEvent.getActionMasked() == 1 && !zOnInterceptTouchEvent3 && !zOnInterceptTouchEvent2 && !zOnInterceptTouchEventScroll) {
                NotificationStackScrollLayoutController.this.mView.setCheckForLeaveBehind(false);
            }
            if (motionEvent.getActionMasked() == 1) {
                NotificationStackScrollLayoutController.this.mView.setCheckForLeaveBehind(true);
            }
            if (NotificationStackScrollLayoutController.this.mJankMonitor != null && zOnInterceptTouchEventScroll && motionEvent.getActionMasked() != 0) {
                NotificationStackScrollLayoutController.this.mJankMonitor.begin(NotificationStackScrollLayoutController.this.mView, 2);
            }
            return zOnInterceptTouchEvent3 || zOnInterceptTouchEventScroll || zOnInterceptTouchEvent2 || zOnInterceptTouchEvent;
        }

        @Override // com.android.systemui.Gefingerpoken
        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean zOnTouchEvent;
            boolean zOnTouchEvent2;
            boolean zOnScrollTouch;
            boolean z = motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1;
            NotificationStackScrollLayoutController.this.mView.handleEmptySpaceClick(motionEvent);
            boolean onlyScrollingInThisMotion = NotificationStackScrollLayoutController.this.mView.getOnlyScrollingInThisMotion();
            boolean zIsExpandingNotification = NotificationStackScrollLayoutController.this.mView.isExpandingNotification();
            if (NotificationStackScrollLayoutController.this.mLongPressedView != null || !NotificationStackScrollLayoutController.this.mView.getIsExpanded() || NotificationStackScrollLayoutController.this.mSwipeHelper.isSwiping() || onlyScrollingInThisMotion) {
                zOnTouchEvent = false;
            } else {
                ExpandHelper expandHelper = NotificationStackScrollLayoutController.this.mView.getExpandHelper();
                if (z) {
                    expandHelper.onlyObserveMovements(false);
                }
                zOnTouchEvent = expandHelper.onTouchEvent(motionEvent);
                boolean zIsExpandingNotification2 = NotificationStackScrollLayoutController.this.mView.isExpandingNotification();
                if (NotificationStackScrollLayoutController.this.mView.getExpandedInThisMotion() && !zIsExpandingNotification2 && zIsExpandingNotification && !NotificationStackScrollLayoutController.this.mView.getDisallowScrollingInThisMotion()) {
                    NotificationStackScrollLayoutController.this.mView.dispatchDownEventToScroller(motionEvent);
                }
                zIsExpandingNotification = zIsExpandingNotification2;
            }
            if (FlagsFake.nsslFalsingFix()) {
                zOnTouchEvent2 = (NotificationStackScrollLayoutController.this.mLongPressedView != null || NotificationStackScrollLayoutController.this.mView.isBeingDragged() || zIsExpandingNotification || NotificationStackScrollLayoutController.this.mView.getExpandedInThisMotion() || onlyScrollingInThisMotion || NotificationStackScrollLayoutController.this.mView.getDisallowDismissInThisMotion()) ? false : NotificationStackScrollLayoutController.this.mSwipeHelper.onTouchEvent(motionEvent);
                zOnScrollTouch = (NotificationStackScrollLayoutController.this.mLongPressedView != null || !NotificationStackScrollLayoutController.this.mView.isExpanded() || NotificationStackScrollLayoutController.this.mSwipeHelper.isSwiping() || zIsExpandingNotification || NotificationStackScrollLayoutController.this.mView.getDisallowScrollingInThisMotion()) ? false : NotificationStackScrollLayoutController.this.mView.onScrollTouch(motionEvent);
            } else {
                boolean zOnScrollTouch2 = (NotificationStackScrollLayoutController.this.mLongPressedView != null || !NotificationStackScrollLayoutController.this.mView.isExpanded() || NotificationStackScrollLayoutController.this.mSwipeHelper.isSwiping() || zIsExpandingNotification || NotificationStackScrollLayoutController.this.mView.getDisallowScrollingInThisMotion()) ? false : NotificationStackScrollLayoutController.this.mView.onScrollTouch(motionEvent);
                zOnTouchEvent2 = (NotificationStackScrollLayoutController.this.mLongPressedView != null || NotificationStackScrollLayoutController.this.mView.isBeingDragged() || zIsExpandingNotification || NotificationStackScrollLayoutController.this.mView.getExpandedInThisMotion() || onlyScrollingInThisMotion || NotificationStackScrollLayoutController.this.mView.getDisallowDismissInThisMotion()) ? false : NotificationStackScrollLayoutController.this.mSwipeHelper.onTouchEvent(motionEvent);
                zOnScrollTouch = zOnScrollTouch2;
            }
            if (motionEvent.getActionMasked() == 1) {
                NotificationStackScrollLayoutController.this.mView.setCheckForLeaveBehind(true);
            }
            traceJankOnTouchEvent(motionEvent.getActionMasked(), zOnScrollTouch);
            return zOnTouchEvent2 || zOnScrollTouch || zOnTouchEvent;
        }
    }

    public static /* synthetic */ void $r8$lambda$g1c78ES7JoFOiIPcjDFDdpnjsPw() {
    }

    static {
        DEBUG = Compile.IS_DEBUG && Log.isLoggable("StackScrollerController", 3);
        HIDE_ALPHA_PROPERTY = new Property(Float.class, "HideNotificationsAlpha") { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.2
            @Override // android.util.Property
            public Float get(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
                return Float.valueOf(notificationStackScrollLayoutController.mMaxAlphaForUnhide);
            }

            @Override // android.util.Property
            public void set(NotificationStackScrollLayoutController notificationStackScrollLayoutController, Float f) {
                notificationStackScrollLayoutController.setMaxAlphaForUnhide(f.floatValue());
            }
        };
    }

    public NotificationStackScrollLayoutController(boolean z, NotificationsController notificationsController, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManager headsUpManager, NotificationRoundnessManager notificationRoundnessManager, DeviceProvisionedController deviceProvisionedController, DynamicPrivacyController dynamicPrivacyController, ConfigurationController configurationController, NotificationLockscreenUserManager notificationLockscreenUserManager, ColorUpdateLogger colorUpdateLogger, DumpManager dumpManager, NotificationSwipeHelper.Builder builder, GroupExpansionManager groupExpansionManager, SectionHeaderController sectionHeaderController, NotifPipeline notifPipeline, NotifCollection notifCollection, UiEventLogger uiEventLogger, NotificationRemoteInputManager notificationRemoteInputManager, VisibilityLocationProviderDelegator visibilityLocationProviderDelegator, SeenNotificationsInteractor seenNotificationsInteractor, NotificationListViewBinder notificationListViewBinder, InteractionJankMonitor interactionJankMonitor, StackStateLogger stackStateLogger, NotificationStackScrollLogger notificationStackScrollLogger, NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationTargetsHelper notificationTargetsHelper, SecureSettings secureSettings, NotificationDismissibilityProvider notificationDismissibilityProvider, ActivityStarter activityStarter, SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        this.mNotificationListContainer = new NotificationListContainerImpl();
        this.mNotifStackController = new NotifStackControllerImpl();
        this.mViewBinder = notificationListViewBinder;
        this.mStackStateLogger = stackStateLogger;
        this.mLogger = notificationStackScrollLogger;
        this.mAllowLongPress = z;
        this.mNotificationsController = notificationsController;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mHeadsUpManager = headsUpManager;
        this.mNotificationRoundnessManager = notificationRoundnessManager;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mConfigurationController = configurationController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mColorUpdateLogger = colorUpdateLogger;
        this.mDumpManager = dumpManager;
        this.mNotificationSwipeHelperBuilder = builder;
        this.mJankMonitor = interactionJankMonitor;
        this.mNotificationStackSizeCalculator = notificationStackSizeCalculator;
        this.mGroupExpansionManager = groupExpansionManager;
        this.mSilentHeaderController = sectionHeaderController;
        this.mNotifPipeline = notifPipeline;
        this.mNotifCollection = notifCollection;
        this.mUiEventLogger = uiEventLogger;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mVisibilityLocationProviderDelegator = visibilityLocationProviderDelegator;
        this.mSeenNotificationsInteractor = seenNotificationsInteractor;
        this.mNotificationTargetsHelper = notificationTargetsHelper;
        this.mSecureSettings = secureSettings;
        this.mDismissibilityProvider = notificationDismissibilityProvider;
        this.mActivityStarter = activityStarter;
        this.mSensitiveNotificationProtectionController = sensitiveNotificationProtectionController;
        dumpManager.registerDumpable(this);
        updateResources();
    }

    private DismissedByUserStats getDismissedByUserStats(NotificationEntry notificationEntry) {
        return new DismissedByUserStats(3, 1, this.mVisibilityProvider.obtain(notificationEntry, true));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInVisibleLocation(NotificationEntry notificationEntry) {
        ExpandableNotificationRow row = notificationEntry.getRow();
        return (row == null || (row.getViewState().location & 5) == 0 || row.getVisibility() != 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUpView$1(int i) {
        this.mUiEventLogger.log(NotificationPanelEvent.fromSelection(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUpView$3(View view) {
        NotificationActivityStarter notificationActivityStarter = this.mNotificationActivityStarter;
        if (notificationActivityStarter != null) {
            notificationActivityStarter.startHistoryIntent(view, this.mView.isHistoryShown());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUpView$4(View view) {
        clearSilentNotifications();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setUpView$5(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        this.mView.onGroupExpandChanged(expandableNotificationRow, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationEnd(List list, int i) {
        if (i == 0) {
            this.mNotifCollection.dismissAllNotifications(this.mLockscreenUserManager.getCurrentUserId());
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NotificationEntry entry = ((ExpandableNotificationRow) it.next()).getEntry();
            arrayList.add(new Pair(entry, getDismissedByUserStats(entry)));
        }
        this.mNotifCollection.dismissNotifications(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMaxAlphaForUnhide(float f) {
        this.mMaxAlphaForUnhide = f;
        updateAlpha();
    }

    private void updateAlpha() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        if (notificationStackScrollLayout != null) {
            notificationStackScrollLayout.setAlpha(Math.min(this.mMaxAlphaForKeyguard, Math.min(this.mMaxAlphaForUnhide, this.mMaxAlphaForGlanceableHub)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateResources() {
        this.mNotificationStackSizeCalculator.updateResources();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSensitivenessWithAnimation(boolean z) {
        Trace.beginSection("NSSLC.updateSensitivenessWithAnimation");
        if (FlagsFake.screenshareNotificationHiding()) {
            boolean zIsAnyProfilePublicMode = this.mLockscreenUserManager.isAnyProfilePublicMode();
            boolean zIsSensitiveStateActive = this.mSensitiveNotificationProtectionController.isSensitiveStateActive();
            this.mView.updateSensitiveness(z && !zIsSensitiveStateActive, zIsAnyProfilePublicMode || zIsSensitiveStateActive);
        } else {
            this.mView.updateSensitiveness(z, this.mLockscreenUserManager.isAnyProfilePublicMode());
        }
        Trace.endSection();
    }

    public void checkSnoozeLeavebehind() {
        if (this.mView.getCheckSnoozeLeaveBehind()) {
            this.mView.setCheckForLeaveBehind(false);
        }
    }

    public void clearSilentNotifications() {
        FooterViewRefactor.assertInLegacyMode();
        this.mView.clearNotifications(2, true ^ hasActiveClearableNotifications(1));
    }

    public RemoteInputController.Delegate createDelegate() {
        return new RemoteInputController.Delegate() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.11
            @Override // com.android.systemui.statusbar.RemoteInputController.Delegate
            public void lockScrollTo(NotificationEntry notificationEntry) {
                NotificationStackScrollLayoutController.this.mView.lockScrollTo(notificationEntry.getRow());
            }

            @Override // com.android.systemui.statusbar.RemoteInputController.Delegate
            public void requestDisallowLongPressAndDismiss() {
                NotificationStackScrollLayoutController.this.mView.requestDisallowLongPress();
                NotificationStackScrollLayoutController.this.mView.requestDisallowDismiss();
            }

            @Override // com.android.systemui.statusbar.RemoteInputController.Delegate
            public void setRemoteInputActive(NotificationEntry notificationEntry, boolean z) {
                NotificationStackScrollLayoutController.this.mHeadsUpManager.setRemoteInputActive(notificationEntry, z);
                notificationEntry.notifyHeightChanged(true);
                if (FooterViewRefactor.isEnabled()) {
                    return;
                }
                NotificationStackScrollLayoutController.this.updateFooter();
            }
        };
    }

    public void generateHeadsUpAnimation(NotificationEntry notificationEntry, boolean z) {
        this.mView.generateHeadsUpAnimation(notificationEntry, z);
    }

    public NotifStackController getNotifStackController() {
        return this.mNotifStackController;
    }

    public NotificationListContainer getNotificationListContainer() {
        return this.mNotificationListContainer;
    }

    NotificationRoundnessManager getNotificationRoundnessManager() {
        return this.mNotificationRoundnessManager;
    }

    public NotificationTargetsHelper getNotificationTargetsHelper() {
        return this.mNotificationTargetsHelper;
    }

    public NotificationStackScrollLayout getView() {
        return this.mView;
    }

    public int getVisibleNotificationCount() {
        FooterViewRefactor.assertInLegacyMode();
        return this.mNotifStats.getNumActiveNotifs();
    }

    public boolean hasActiveClearableNotifications(int i) {
        FooterViewRefactor.assertInLegacyMode();
        return hasNotifications(i, true);
    }

    public boolean hasNotifications(int i, boolean z) {
        FooterViewRefactor.assertInLegacyMode();
        boolean hasClearableAlertingNotifs = z ? this.mNotifStats.getHasClearableAlertingNotifs() : this.mNotifStats.getHasNonClearableAlertingNotifs();
        boolean hasClearableSilentNotifs = z ? this.mNotifStats.getHasClearableSilentNotifs() : this.mNotifStats.getHasNonClearableSilentNotifs();
        if (i == 0) {
            return hasClearableSilentNotifs || hasClearableAlertingNotifs;
        }
        if (i == 1) {
            return hasClearableAlertingNotifs;
        }
        if (i == 2) {
            return hasClearableSilentNotifs;
        }
        throw new IllegalStateException("Bad selection: " + i);
    }

    public boolean isHistoryEnabled() {
        return false;
    }

    public void setIntrinsicPadding(int i) {
        this.mView.setIntrinsicPadding(i);
    }

    public void setUpView(NotificationStackScrollLayout notificationStackScrollLayout) {
        this.mView = notificationStackScrollLayout;
        notificationStackScrollLayout.setStackStateLogger(this.mStackStateLogger);
        this.mView.setController(this);
        this.mView.setLogger(this.mLogger);
        this.mView.setTouchHandler(new TouchHandler());
        NotificationStackScrollLayout notificationStackScrollLayout2 = this.mView;
        final NotificationsController notificationsController = this.mNotificationsController;
        notificationsController.getClass();
        notificationStackScrollLayout2.setResetUserExpandedStatesRunnable(new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                notificationsController.resetUserExpandedStates();
            }
        });
        this.mView.setActivityStarter(this.mActivityStarter);
        this.mView.setClearAllAnimationListener(new NotificationStackScrollLayout.ClearAllAnimationListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.ClearAllAnimationListener
            public final void onAnimationEnd(List list, int i) {
                this.f$0.onAnimationEnd(list, i);
            }
        });
        this.mView.setClearAllListener(new NotificationStackScrollLayout.ClearAllListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.ClearAllListener
            public final void onClearAll(int i) {
                this.f$0.lambda$setUpView$1(i);
            }
        });
        if (!FooterViewRefactor.isEnabled()) {
            this.mView.setIsRemoteInputActive(this.mRemoteInputManager.isRemoteInputActive());
            this.mRemoteInputManager.addControllerCallback(new RemoteInputController.Callback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.9
                @Override // com.android.systemui.statusbar.RemoteInputController.Callback
                public void onRemoteInputActive(boolean z) {
                    NotificationStackScrollLayoutController.this.mView.setIsRemoteInputActive(z);
                }
            });
        }
        this.mView.setClearAllFinishedWhilePanelExpandedRunnable(new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayoutController.$r8$lambda$g1c78ES7JoFOiIPcjDFDdpnjsPw();
            }
        });
        this.mDumpManager.registerDumpable(this.mView);
        this.mSwipeHelper = this.mNotificationSwipeHelperBuilder.setNotificationCallback(this.mNotificationCallback).build();
        this.mNotifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.10
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry) {
                NotificationStackScrollLayoutController.this.mView.onEntryUpdated(notificationEntry);
            }
        });
        NotificationStackScrollLayout notificationStackScrollLayout3 = this.mView;
        notificationStackScrollLayout3.initView(notificationStackScrollLayout3.getContext(), this.mSwipeHelper, this.mNotificationStackSizeCalculator);
        if (!FooterViewRefactor.isEnabled()) {
            this.mView.setManageButtonClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$setUpView$3(view);
                }
            });
        }
        if (!NotificationsHeadsUpRefactor.isEnabled()) {
            this.mHeadsUpManager.addListener(this.mOnHeadsUpChangedListener);
        }
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        final NotificationStackScrollLayout notificationStackScrollLayout4 = this.mView;
        notificationStackScrollLayout4.getClass();
        headsUpManager.setAnimationStateHandler(new AnimationStateHandler() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda5
        });
        this.mDynamicPrivacyController.addListener(this.mDynamicPrivacyControllerListener);
        this.mLockscreenUserManager.addUserChangedListener(this.mLockscreenUserChangeListener);
        this.mVisibilityLocationProviderDelegator.setDelegate(new VisibilityLocationProvider() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda6
            @Override // com.android.systemui.statusbar.notification.VisibilityLocationProvider
            public final boolean isInVisibleLocation(NotificationEntry notificationEntry) {
                return this.f$0.isInVisibleLocation(notificationEntry);
            }
        });
        if (!FooterViewRefactor.isEnabled()) {
            this.mDeviceProvisionedController.addCallback(this.mDeviceProvisionedListener);
            this.mDeviceProvisionedListener.onDeviceProvisionedChanged();
        }
        if (FlagsFake.screenshareNotificationHiding()) {
            this.mSensitiveNotificationProtectionController.registerSensitiveStateListener(this.mSensitiveStateChangedListener);
        }
        if (this.mView.isAttachedToWindow()) {
            this.mOnAttachStateChangeListener.onViewAttachedToWindow(this.mView);
        }
        this.mView.addOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
        if (!FooterViewRefactor.isEnabled()) {
            this.mSilentHeaderController.setOnClearSectionClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$setUpView$4(view);
                }
            });
        }
        this.mGroupExpansionManager.registerGroupExpansionChangeListener(new GroupExpansionManager.OnGroupExpansionChangeListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda8
            @Override // com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager.OnGroupExpansionChangeListener
            public final void onGroupExpansionChange(ExpandableNotificationRow expandableNotificationRow, boolean z) {
                this.f$0.lambda$setUpView$5(expandableNotificationRow, z);
            }
        });
        this.mViewBinder.bindWhileAttached(this.mView, this);
    }

    public void updateFooter() {
        FooterViewRefactor.assertInLegacyMode();
        Trace.beginSection("NSSLC.updateFooter");
        this.mView.updateFooter();
        Trace.endSection();
    }

    public void updateImportantForAccessibility() {
        FooterViewRefactor.assertInLegacyMode();
        if (getVisibleNotificationCount() == 0 && this.mView.onKeyguard()) {
            this.mView.setImportantForAccessibility(2);
        } else {
            this.mView.setImportantForAccessibility(1);
        }
    }

    public void updateShowEmptyShadeView() {
        FooterViewRefactor.assertInLegacyMode();
        Trace.beginSection("NSSLC.updateShowEmptyShadeView");
        this.mView.updateEmptyShadeView((getVisibleNotificationCount() != 0 || this.mView.isQsFullScreen() || this.mIsInTransitionToAod) ? false : true, false);
        Trace.endSection();
    }
}
