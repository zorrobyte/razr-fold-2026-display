package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.RemoteException;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationCustomViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.Compile;
import com.motorola.taskbar.R$id;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationContentView extends FrameLayout implements NotificationFadeAware {
    private static final boolean DEBUG;
    private boolean mAnimate;
    private int mAnimationStartVisibleType;
    private boolean mBeforeN;
    private boolean mBubblesEnabledForUser;
    private RemoteInputView mCachedExpandedRemoteInput;
    private RemoteInputViewController mCachedExpandedRemoteInputViewController;
    private RemoteInputView mCachedHeadsUpRemoteInput;
    private RemoteInputViewController mCachedHeadsUpRemoteInputViewController;
    private int mClipBottomAmount;
    private final Rect mClipBounds;
    private boolean mClipToActualHeight;
    private int mClipTopAmount;
    private ExpandableNotificationRow mContainingNotification;
    private boolean mContentAnimating;
    private int mContentHeight;
    private int mContentHeightAtAnimationStart;
    private View mContractedChild;
    private NotificationViewWrapper mContractedWrapper;
    private InflatedSmartReplyState mCurrentSmartReplyState;
    private final ViewTreeObserver.OnPreDrawListener mEnableAnimationPredrawListener;
    private View.OnClickListener mExpandClickListener;
    private boolean mExpandable;
    private View mExpandedChild;
    private InflatedSmartReplyViewHolder mExpandedInflatedSmartReplies;
    private RemoteInputView mExpandedRemoteInput;
    private RemoteInputViewController mExpandedRemoteInputController;
    private SmartReplyView mExpandedSmartReplyView;
    private Runnable mExpandedVisibleListener;
    private NotificationViewWrapper mExpandedWrapper;
    private boolean mFocusOnVisibilityChange;
    private boolean mForceSelectNextLayout;
    private boolean mHeadsUpAnimatingAway;
    private View mHeadsUpChild;
    private int mHeadsUpHeight;
    private InflatedSmartReplyViewHolder mHeadsUpInflatedSmartReplies;
    private RemoteInputView mHeadsUpRemoteInput;
    private RemoteInputViewController mHeadsUpRemoteInputController;
    private SmartReplyView mHeadsUpSmartReplyView;
    private NotificationViewWrapper mHeadsUpWrapper;
    private final HybridGroupManager mHybridGroupManager;
    private boolean mIsChildInGroup;
    private boolean mIsContentExpandable;
    private boolean mIsHeadsUp;
    private boolean mLegacy;
    private int mMinContractedHeight;
    private int mMinSingleLineHeight;
    private NotificationEntry mNotificationEntry;
    private int mNotificationMaxHeight;
    private final ArrayMap mOnContentViewInactiveListeners;
    private PeopleNotificationIdentifier mPeopleIdentifier;
    private PendingIntent mPreviousExpandedRemoteInputIntent;
    private PendingIntent mPreviousHeadsUpRemoteInputIntent;
    private RemoteInputController mRemoteInputController;
    private RemoteInputViewSubcomponent.Factory mRemoteInputSubcomponentFactory;
    private boolean mRemoteInputVisible;
    private NotificationViewWrapper mShownWrapper;
    private HybridNotificationView mSingleLineView;
    private int mSingleLineWidthIndention;
    private int mSmallHeight;
    private SmartReplyConstants mSmartReplyConstants;
    private SmartReplyController mSmartReplyController;
    private IStatusBarService mStatusBarService;
    private int mTransformationStartVisibleType;
    private int mUnrestrictedContentHeight;
    private boolean mUserExpanding;
    private int mVisibleType;

    class RemoteInputViewData {
        RemoteInputViewController mController;
        RemoteInputView mView;

        private RemoteInputViewData() {
        }
    }

    static {
        DEBUG = Compile.IS_DEBUG && Log.isLoggable("NotificationContentView", 3);
    }

    public NotificationContentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClipBounds = new Rect();
        this.mShownWrapper = null;
        this.mVisibleType = -1;
        this.mOnContentViewInactiveListeners = new ArrayMap();
        this.mEnableAnimationPredrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                NotificationContentView.this.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        NotificationContentView.this.mAnimate = true;
                    }
                });
                NotificationContentView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        };
        this.mClipToActualHeight = true;
        this.mAnimationStartVisibleType = -1;
        this.mForceSelectNextLayout = true;
        this.mContentHeightAtAnimationStart = -1;
        this.mHybridGroupManager = new HybridGroupManager(getContext());
        reinflate();
    }

    private void animateToVisibleType(int i) {
        TransformableView transformableViewForVisibleType = getTransformableViewForVisibleType(i);
        final TransformableView transformableViewForVisibleType2 = getTransformableViewForVisibleType(this.mVisibleType);
        if (transformableViewForVisibleType == transformableViewForVisibleType2 || transformableViewForVisibleType2 == null) {
            transformableViewForVisibleType.setVisible(true);
            return;
        }
        this.mAnimationStartVisibleType = this.mVisibleType;
        transformableViewForVisibleType.transformFrom(transformableViewForVisibleType2);
        getViewForVisibleType(i).setVisibility(0);
        updateShownWrapper(i);
        transformableViewForVisibleType2.transformTo(transformableViewForVisibleType, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView.2
            @Override // java.lang.Runnable
            public void run() {
                TransformableView transformableView = transformableViewForVisibleType2;
                NotificationContentView notificationContentView = NotificationContentView.this;
                if (transformableView != notificationContentView.getTransformableViewForVisibleType(notificationContentView.mVisibleType)) {
                    transformableViewForVisibleType2.setVisible(false);
                }
                NotificationContentView.this.mAnimationStartVisibleType = -1;
                NotificationContentView.this.notifySubtreeForAccessibilityContentChange();
            }
        });
        fireExpandedVisibleListenerIfVisible();
    }

    private void applyBubbleAction(View view, NotificationEntry notificationEntry) {
    }

    private void applyExternalSmartReplyState(View view, InflatedSmartReplyState inflatedSmartReplyState) {
        boolean z = inflatedSmartReplyState != null && inflatedSmartReplyState.getHasPhishingAction();
        View viewFindViewById = view.findViewById(R.id.pin_text);
        if (viewFindViewById != null) {
            if (DEBUG) {
                Log.d("NotificationContentView", "Setting 'phishing_alert' view visible=" + z + ".");
            }
            viewFindViewById.setVisibility(z ? 0 : 8);
        }
        List suppressedActionIndices = inflatedSmartReplyState != null ? inflatedSmartReplyState.getSuppressedActionIndices() : Collections.EMPTY_LIST;
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.actions);
        if (viewGroup != null) {
            if (DEBUG && !suppressedActionIndices.isEmpty()) {
                Log.d("NotificationContentView", "Suppressing actions with indices: " + suppressedActionIndices);
            }
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                Object tag = childAt.getTag(R.id.notification_material_reply_text_2);
                childAt.setVisibility(((tag instanceof Integer) && suppressedActionIndices.contains(tag)) ? 8 : 0);
            }
        }
    }

    private RemoteInputViewData applyRemoteInput(View view, NotificationEntry notificationEntry, boolean z, PendingIntent pendingIntent, RemoteInputView remoteInputView, RemoteInputViewController remoteInputViewController, NotificationViewWrapper notificationViewWrapper) {
        RemoteInputViewData remoteInputViewData = new RemoteInputViewData();
        View viewFindViewById = view.findViewById(R.id.actions_container);
        if (viewFindViewById instanceof FrameLayout) {
            RemoteInputView remoteInputView2 = (RemoteInputView) view.findViewWithTag(RemoteInputView.VIEW_TAG);
            remoteInputViewData.mView = remoteInputView2;
            if (remoteInputView2 != null) {
                remoteInputView2.onNotificationUpdateOrReset();
                remoteInputViewData.mController = remoteInputViewData.mView.getController();
            }
            if (remoteInputViewData.mView == null && z) {
                FrameLayout frameLayout = (FrameLayout) viewFindViewById;
                if (remoteInputView == null) {
                    RemoteInputView remoteInputViewInflate = RemoteInputView.inflate(((FrameLayout) this).mContext, frameLayout, notificationEntry, this.mRemoteInputController);
                    remoteInputViewInflate.setVisibility(8);
                    frameLayout.addView(remoteInputViewInflate, new FrameLayout.LayoutParams(-1, -1));
                    remoteInputViewData.mView = remoteInputViewInflate;
                    RemoteInputViewController controller = this.mRemoteInputSubcomponentFactory.create(remoteInputViewInflate, this.mRemoteInputController).getController();
                    remoteInputViewData.mController = controller;
                    remoteInputViewData.mView.setController(controller);
                } else {
                    frameLayout.addView(remoteInputView);
                    remoteInputView.dispatchFinishTemporaryDetach();
                    remoteInputView.requestFocus();
                    remoteInputViewData.mView = remoteInputView;
                    remoteInputViewData.mController = remoteInputViewController;
                }
            }
            if (z) {
                remoteInputViewData.mView.setWrapper(notificationViewWrapper);
                remoteInputViewData.mView.addOnVisibilityChangedListener(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentView$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.setRemoteInputVisible(((Boolean) obj).booleanValue());
                    }
                });
                if (pendingIntent != null || remoteInputViewData.mView.isActive()) {
                    Notification.Action[] actionArr = notificationEntry.getSbn().getNotification().actions;
                    if (pendingIntent != null) {
                        remoteInputViewData.mController.setPendingIntent(pendingIntent);
                    }
                    if (remoteInputViewData.mController.updatePendingIntentFromActions(actionArr)) {
                        if (!remoteInputViewData.mController.isActive()) {
                            remoteInputViewData.mController.focus();
                        }
                    } else if (remoteInputViewData.mController.isActive()) {
                        remoteInputViewData.mController.close();
                    }
                }
            }
            if (remoteInputViewData.mView != null) {
                remoteInputViewData.mView.setBackgroundTintColor(notificationEntry.getRow().getCurrentBackgroundTint(), notificationEntry.getSbn().getNotification().isColorized());
            }
        }
        return remoteInputViewData;
    }

    private void applyRemoteInput() {
        NotificationContentView notificationContentView;
        boolean zHasFreeformRemoteInput = hasFreeformRemoteInput(this.mNotificationEntry);
        View view = this.mExpandedChild;
        if (view != null) {
            notificationContentView = this;
            RemoteInputViewData remoteInputViewDataApplyRemoteInput = notificationContentView.applyRemoteInput(view, this.mNotificationEntry, zHasFreeformRemoteInput, this.mPreviousExpandedRemoteInputIntent, this.mCachedExpandedRemoteInput, this.mCachedExpandedRemoteInputViewController, this.mExpandedWrapper);
            notificationContentView.mExpandedRemoteInput = remoteInputViewDataApplyRemoteInput.mView;
            RemoteInputViewController remoteInputViewController = remoteInputViewDataApplyRemoteInput.mController;
            notificationContentView.mExpandedRemoteInputController = remoteInputViewController;
            if (remoteInputViewController != null) {
                remoteInputViewController.bind();
            }
        } else {
            notificationContentView = this;
            notificationContentView.mExpandedRemoteInput = null;
            RemoteInputViewController remoteInputViewController2 = notificationContentView.mExpandedRemoteInputController;
            if (remoteInputViewController2 != null) {
                remoteInputViewController2.unbind();
            }
            notificationContentView.mExpandedRemoteInputController = null;
        }
        RemoteInputView remoteInputView = notificationContentView.mCachedExpandedRemoteInput;
        if (remoteInputView != null && remoteInputView != notificationContentView.mExpandedRemoteInput) {
            remoteInputView.dispatchFinishTemporaryDetach();
        }
        notificationContentView.mCachedExpandedRemoteInput = null;
        notificationContentView.mCachedExpandedRemoteInputViewController = null;
        View view2 = notificationContentView.mHeadsUpChild;
        if (view2 != null) {
            RemoteInputViewData remoteInputViewDataApplyRemoteInput2 = notificationContentView.applyRemoteInput(view2, notificationContentView.mNotificationEntry, zHasFreeformRemoteInput, notificationContentView.mPreviousHeadsUpRemoteInputIntent, notificationContentView.mCachedHeadsUpRemoteInput, notificationContentView.mCachedHeadsUpRemoteInputViewController, notificationContentView.mHeadsUpWrapper);
            notificationContentView.mHeadsUpRemoteInput = remoteInputViewDataApplyRemoteInput2.mView;
            RemoteInputViewController remoteInputViewController3 = remoteInputViewDataApplyRemoteInput2.mController;
            notificationContentView.mHeadsUpRemoteInputController = remoteInputViewController3;
            if (remoteInputViewController3 != null) {
                remoteInputViewController3.bind();
            }
        } else {
            notificationContentView.mHeadsUpRemoteInput = null;
            RemoteInputViewController remoteInputViewController4 = notificationContentView.mHeadsUpRemoteInputController;
            if (remoteInputViewController4 != null) {
                remoteInputViewController4.unbind();
            }
            notificationContentView.mHeadsUpRemoteInputController = null;
        }
        RemoteInputView remoteInputView2 = notificationContentView.mCachedHeadsUpRemoteInput;
        if (remoteInputView2 != null && remoteInputView2 != notificationContentView.mHeadsUpRemoteInput) {
            remoteInputView2.dispatchFinishTemporaryDetach();
        }
        notificationContentView.mCachedHeadsUpRemoteInput = null;
        notificationContentView.mCachedHeadsUpRemoteInputViewController = null;
    }

    private void applyRemoteInputAndSmartReply() {
        if (this.mRemoteInputController != null) {
            applyRemoteInput();
        }
        if (this.mCurrentSmartReplyState == null) {
            if (DEBUG) {
                Log.d("NotificationContentView", "InflatedSmartReplies are null, don't add smart replies.");
            }
        } else {
            if (DEBUG) {
                Log.d("NotificationContentView", String.format("Adding suggestions for %s, %d actions, and %d replies.", this.mNotificationEntry.getSbn().getKey(), Integer.valueOf(this.mCurrentSmartReplyState.getSmartActionsList().size()), Integer.valueOf(this.mCurrentSmartReplyState.getSmartRepliesList().size())));
            }
            applySmartReplyView();
        }
    }

    private static SmartReplyView applySmartReplyView(View view, InflatedSmartReplyState inflatedSmartReplyState, NotificationEntry notificationEntry, InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder) {
        View viewFindViewById = view.findViewById(R.id.splashscreen);
        SmartReplyView smartReplyView = null;
        smartReplyView = null;
        if (!(viewFindViewById instanceof LinearLayout)) {
            return null;
        }
        LinearLayout linearLayout = (LinearLayout) viewFindViewById;
        if (!SmartReplyStateInflaterKt.shouldShowSmartReplyView(notificationEntry, inflatedSmartReplyState)) {
            linearLayout.setVisibility(8);
            return null;
        }
        int childCount = linearLayout.getChildCount();
        int i = 0;
        while (i < childCount) {
            View childAt = linearLayout.getChildAt(i);
            if (childAt.getId() == R$id.smart_reply_view && (childAt instanceof SmartReplyView)) {
                break;
            }
            i++;
        }
        if (i < childCount) {
            linearLayout.removeViewAt(i);
        }
        if (inflatedSmartReplyViewHolder != null && inflatedSmartReplyViewHolder.getSmartReplyView() != null) {
            smartReplyView = inflatedSmartReplyViewHolder.getSmartReplyView();
            linearLayout.addView(smartReplyView, i);
        }
        if (smartReplyView != null) {
            smartReplyView.resetSmartSuggestions(linearLayout);
            smartReplyView.addPreInflatedButtons(inflatedSmartReplyViewHolder.getSmartSuggestionButtons());
            smartReplyView.setBackgroundTintColor(notificationEntry.getRow().getCurrentBackgroundTint(), notificationEntry.getSbn().getNotification().isColorized());
            linearLayout.setVisibility(0);
        }
        return smartReplyView;
    }

    private void applySmartReplyView() {
        View view = this.mContractedChild;
        if (view != null) {
            applyExternalSmartReplyState(view, this.mCurrentSmartReplyState);
        }
        View view2 = this.mExpandedChild;
        if (view2 != null) {
            applyExternalSmartReplyState(view2, this.mCurrentSmartReplyState);
            SmartReplyView smartReplyViewApplySmartReplyView = applySmartReplyView(this.mExpandedChild, this.mCurrentSmartReplyState, this.mNotificationEntry, this.mExpandedInflatedSmartReplies);
            this.mExpandedSmartReplyView = smartReplyViewApplySmartReplyView;
            if (smartReplyViewApplySmartReplyView != null) {
                SmartReplyView.SmartReplies smartReplies = this.mCurrentSmartReplyState.getSmartReplies();
                SmartReplyView.SmartActions smartActions = this.mCurrentSmartReplyState.getSmartActions();
                if (smartReplies != null || smartActions != null) {
                    boolean z = false;
                    int size = smartReplies == null ? 0 : smartReplies.choices.size();
                    int size2 = smartActions == null ? 0 : smartActions.actions.size();
                    boolean z2 = smartReplies == null ? smartActions.fromAssistant : smartReplies.fromAssistant;
                    if (smartReplies != null && this.mSmartReplyConstants.getEffectiveEditChoicesBeforeSending(smartReplies.remoteInput.getEditChoicesBeforeSending())) {
                        z = true;
                    }
                    this.mSmartReplyController.smartSuggestionsAdded(this.mNotificationEntry, size, size2, z2, z);
                }
            }
        }
        View view3 = this.mHeadsUpChild;
        if (view3 != null) {
            applyExternalSmartReplyState(view3, this.mCurrentSmartReplyState);
            if (this.mSmartReplyConstants.getShowInHeadsUp()) {
                this.mHeadsUpSmartReplyView = applySmartReplyView(this.mHeadsUpChild, this.mCurrentSmartReplyState, this.mNotificationEntry, this.mHeadsUpInflatedSmartReplies);
            }
        }
    }

    private void applySnoozeAction(View view) {
    }

    private void applySystemActions(View view, NotificationEntry notificationEntry) {
        applySnoozeAction(view);
        applyBubbleAction(view, notificationEntry);
    }

    private float calculateTransformationAmount() {
        int viewHeight = getViewHeight(this.mTransformationStartVisibleType);
        int viewHeight2 = getViewHeight(this.mVisibleType);
        int iAbs = Math.abs(this.mContentHeight - viewHeight);
        int iAbs2 = Math.abs(viewHeight2 - viewHeight);
        if (iAbs2 != 0) {
            return Math.min(1.0f, iAbs / iAbs2);
        }
        Log.wtf("NotificationContentView", "the total transformation distance is 0\n StartType: " + this.mTransformationStartVisibleType + " height: " + viewHeight + "\n VisibleType: " + this.mVisibleType + " height: " + viewHeight2 + "\n mContentHeight: " + this.mContentHeight);
        return 1.0f;
    }

    private void cancelNotification(Exception exc) {
        try {
            setVisibility(8);
            StatusBarNotification sbn = this.mNotificationEntry.getSbn();
            IStatusBarService iStatusBarService = this.mStatusBarService;
            if (iStatusBarService != null) {
                iStatusBarService.onNotificationError(sbn.getPackageName(), sbn.getTag(), sbn.getId(), sbn.getUid(), sbn.getInitialPid(), exc.getMessage(), sbn.getUser().getIdentifier());
            }
        } catch (RemoteException e) {
            Log.e("NotificationContentView", "cancelNotification failed: " + e);
        }
    }

    private void fireExpandedVisibleListenerIfVisible() {
        if (this.mExpandedVisibleListener == null || this.mExpandedChild == null || !isShown() || this.mExpandedChild.getVisibility() != 0) {
            return;
        }
        Runnable runnable = this.mExpandedVisibleListener;
        this.mExpandedVisibleListener = null;
        runnable.run();
    }

    private void focusExpandButtonIfNecessary() {
        View expandButton;
        if (this.mFocusOnVisibilityChange) {
            NotificationViewWrapper visibleWrapper = getVisibleWrapper(this.mVisibleType);
            if (visibleWrapper != null && (expandButton = visibleWrapper.getExpandButton()) != null) {
                expandButton.requestAccessibilityFocus();
            }
            this.mFocusOnVisibilityChange = false;
        }
    }

    private void forceUpdateVisibilities() {
        forceUpdateVisibility(0, this.mContractedChild, this.mContractedWrapper);
        forceUpdateVisibility(1, this.mExpandedChild, this.mExpandedWrapper);
        forceUpdateVisibility(2, this.mHeadsUpChild, this.mHeadsUpWrapper);
        HybridNotificationView hybridNotificationView = this.mSingleLineView;
        forceUpdateVisibility(3, hybridNotificationView, hybridNotificationView);
        updateShownWrapper(this.mVisibleType);
        fireExpandedVisibleListenerIfVisible();
        this.mAnimationStartVisibleType = -1;
        notifySubtreeForAccessibilityContentChange();
    }

    private void forceUpdateVisibility(int i, View view, TransformableView transformableView) {
        if (view == null) {
            return;
        }
        if (this.mVisibleType == i || this.mTransformationStartVisibleType == i) {
            transformableView.setVisible(true);
        } else {
            view.setVisibility(4);
        }
    }

    private int getExtraRemoteInputHeight(RemoteInputView remoteInputView) {
        if (remoteInputView == null) {
            return 0;
        }
        if (remoteInputView.isActive() || remoteInputView.isSending()) {
            return getResources().getDimensionPixelSize(R.dimen.notification_header_shrink_hide_width);
        }
        return 0;
    }

    private int getMinContentHeightHint() {
        int i;
        if (this.mIsChildInGroup && isVisibleOrTransitioning(3)) {
            return ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.notification_custom_view_max_image_width);
        }
        int viewHeight = 0;
        if (this.mHeadsUpChild != null && this.mExpandedChild != null) {
            boolean z = isTransitioningFromTo(2, 1) || isTransitioningFromTo(1, 2);
            boolean z2 = !isVisibleOrTransitioning(0) && (this.mIsHeadsUp || this.mHeadsUpAnimatingAway) && this.mContainingNotification.canShowHeadsUp();
            if (z || z2) {
                return Math.min(getViewHeight(2), getViewHeight(1));
            }
        }
        if (this.mVisibleType == 1 && (i = this.mContentHeightAtAnimationStart) != -1 && this.mExpandedChild != null) {
            return Math.min(i, getViewHeight(1));
        }
        if (this.mHeadsUpChild == null || !isVisibleOrTransitioning(2)) {
            viewHeight = this.mExpandedChild != null ? getViewHeight(1) : this.mContractedChild != null ? getViewHeight(0) + ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.notification_custom_view_max_image_width) : getMinHeight();
        } else {
            int viewHeight2 = getViewHeight(2);
            RemoteInputView remoteInputView = this.mHeadsUpRemoteInput;
            if (remoteInputView == null || !remoteInputView.isAnimatingAppearance()) {
                viewHeight = viewHeight2;
            }
        }
        return (this.mExpandedChild == null || !isVisibleOrTransitioning(1)) ? viewHeight : Math.min(viewHeight, getViewHeight(1));
    }

    private RemoteInputView getRemoteInputForView(View view) {
        if (view == this.mExpandedChild) {
            return this.mExpandedRemoteInput;
        }
        if (view == this.mHeadsUpChild) {
            return this.mHeadsUpRemoteInput;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TransformableView getTransformableViewForVisibleType(int i) {
        return i != 1 ? i != 2 ? i != 3 ? this.mContractedWrapper : this.mSingleLineView : this.mHeadsUpWrapper : this.mExpandedWrapper;
    }

    private View getViewForVisibleType(int i) {
        return i != 1 ? i != 2 ? i != 3 ? this.mContractedChild : this.mSingleLineView : this.mHeadsUpChild : this.mExpandedChild;
    }

    private int getViewHeight(int i) {
        return getViewHeight(i, false);
    }

    private int getViewHeight(int i, boolean z) {
        View viewForVisibleType = getViewForVisibleType(i);
        int height = viewForVisibleType.getHeight();
        NotificationViewWrapper wrapperForView = getWrapperForView(viewForVisibleType);
        return wrapperForView != null ? height + wrapperForView.getHeaderTranslation(z) : height;
    }

    private int getVisualTypeForHeight(float f) {
        boolean z = this.mExpandedChild == null;
        if (!z && f == getViewHeight(1)) {
            return 1;
        }
        if (!this.mUserExpanding && this.mIsChildInGroup && !isGroupExpanded()) {
            return 3;
        }
        if ((this.mIsHeadsUp || this.mHeadsUpAnimatingAway) && this.mHeadsUpChild != null && this.mContainingNotification.canShowHeadsUp()) {
            return (f <= ((float) getViewHeight(2)) || z) ? 2 : 1;
        }
        if (z || !(this.mContractedChild == null || f > getViewHeight(0) || (this.mIsChildInGroup && !isGroupExpanded() && this.mContainingNotification.isExpanded(true)))) {
            return 0;
        }
        return !z ? 1 : -1;
    }

    private NotificationViewWrapper getWrapperForView(View view) {
        if (view == this.mContractedChild) {
            return this.mContractedWrapper;
        }
        if (view == this.mExpandedChild) {
            return this.mExpandedWrapper;
        }
        if (view == this.mHeadsUpChild) {
            return this.mHeadsUpWrapper;
        }
        return null;
    }

    public static boolean hasFreeformRemoteInput(NotificationEntry notificationEntry) {
        return notificationEntry.getSbn().getNotification().findRemoteInputActionPair(true) != null;
    }

    private boolean isContentViewInactive(View view) {
        return (view != null && isShown() && (view.getVisibility() == 0 || getViewForVisibleType(this.mVisibleType) == view)) ? false : true;
    }

    private boolean isGroupExpanded() {
        return this.mContainingNotification.isGroupExpanded();
    }

    private boolean isTransitioningFromTo(int i, int i2) {
        return (this.mTransformationStartVisibleType == i || this.mAnimationStartVisibleType == i) && this.mVisibleType == i2;
    }

    private boolean isVisibleOrTransitioning(int i) {
        return this.mVisibleType == i || this.mTransformationStartVisibleType == i || this.mAnimationStartVisibleType == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySubtreeForAccessibilityContentChange() {
        ViewParent viewParent = ((FrameLayout) this).mParent;
        if (viewParent != null) {
            viewParent.notifySubtreeAccessibilityStateChanged(this, this, 1);
        }
    }

    private void selectLayout(boolean z, boolean z2) {
        if (this.mContractedChild == null) {
            return;
        }
        if (this.mUserExpanding) {
            updateContentTransformation();
            return;
        }
        int iCalculateVisibleType = calculateVisibleType();
        boolean z3 = iCalculateVisibleType != this.mVisibleType;
        if (z3 || z2) {
            View viewForVisibleType = getViewForVisibleType(iCalculateVisibleType);
            if (viewForVisibleType != null) {
                viewForVisibleType.setVisibility(0);
                transferRemoteInputFocus(iCalculateVisibleType);
            }
            if (!z || ((iCalculateVisibleType != 1 || this.mExpandedChild == null) && ((iCalculateVisibleType != 2 || this.mHeadsUpChild == null) && ((iCalculateVisibleType != 3 || this.mSingleLineView == null) && iCalculateVisibleType != 0)))) {
                updateViewVisibilities(iCalculateVisibleType);
            } else {
                animateToVisibleType(iCalculateVisibleType);
            }
            this.mVisibleType = iCalculateVisibleType;
            if (z3) {
                focusExpandButtonIfNecessary();
            }
            NotificationViewWrapper visibleWrapper = getVisibleWrapper(iCalculateVisibleType);
            if (visibleWrapper != null) {
                visibleWrapper.setContentHeight(this.mUnrestrictedContentHeight, getMinContentHeightHint());
            }
            updateBackgroundColor(z);
        }
    }

    private void setActionsImportanceForAccessibility(int i) {
        View view = this.mExpandedChild;
        if (view != null) {
            setActionsImportanceForAccessibility(i, view);
        }
        View view2 = this.mHeadsUpChild;
        if (view2 != null) {
            setActionsImportanceForAccessibility(i, view2);
        }
    }

    private void setActionsImportanceForAccessibility(int i, View view) {
        View viewFindViewById = view.findViewById(R.id.actions);
        if (viewFindViewById != null) {
            viewFindViewById.setImportantForAccessibility(i);
        }
    }

    private void setVisible(boolean z) {
        if (z) {
            getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
            getViewTreeObserver().addOnPreDrawListener(this.mEnableAnimationPredrawListener);
        } else {
            getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
            this.mAnimate = false;
        }
    }

    private boolean shouldClipToRounding(int i, boolean z, boolean z2) {
        NotificationViewWrapper visibleWrapper = getVisibleWrapper(i);
        if (visibleWrapper == null) {
            return false;
        }
        return visibleWrapper.shouldClipToRounding(z, z2);
    }

    private boolean shouldContractedBeFixedSize() {
        return this.mBeforeN && (this.mContractedWrapper instanceof NotificationCustomViewWrapper);
    }

    private void transferRemoteInputFocus(int i) {
        RemoteInputViewController remoteInputViewController;
        RemoteInputViewController remoteInputViewController2;
        if (i == 2 && this.mHeadsUpRemoteInputController != null && (remoteInputViewController2 = this.mExpandedRemoteInputController) != null && remoteInputViewController2.isActive()) {
            this.mHeadsUpRemoteInputController.stealFocusFrom(this.mExpandedRemoteInputController);
        }
        if (i != 1 || this.mExpandedRemoteInputController == null || (remoteInputViewController = this.mHeadsUpRemoteInputController) == null || !remoteInputViewController.isActive()) {
            return;
        }
        this.mExpandedRemoteInputController.stealFocusFrom(this.mHeadsUpRemoteInputController);
    }

    private void updateAllSingleLineViews() {
        updateSingleLineView();
    }

    private void updateBackgroundTransformation(float f) {
        int backgroundColor = getBackgroundColor(this.mVisibleType);
        int backgroundColor2 = getBackgroundColor(this.mTransformationStartVisibleType);
        if (backgroundColor != backgroundColor2) {
            if (backgroundColor2 == 0) {
                backgroundColor2 = this.mContainingNotification.getBackgroundColorWithoutTint();
            }
            if (backgroundColor == 0) {
                backgroundColor = this.mContainingNotification.getBackgroundColorWithoutTint();
            }
            backgroundColor = NotificationUtils.interpolateColors(backgroundColor2, backgroundColor, f);
        }
        this.mContainingNotification.setContentBackground(backgroundColor, false, this);
    }

    private void updateClipping() {
        if (!this.mClipToActualHeight) {
            setClipBounds(null);
            return;
        }
        int translationY = (int) (this.mClipTopAmount - getTranslationY());
        this.mClipBounds.set(0, translationY, getWidth(), Math.max(translationY, (int) ((this.mUnrestrictedContentHeight - this.mClipBottomAmount) - getTranslationY())));
        setClipBounds(this.mClipBounds);
    }

    private void updateContentTransformation() {
        int iCalculateVisibleType = calculateVisibleType();
        if (getTransformableViewForVisibleType(this.mVisibleType) == null) {
            this.mVisibleType = iCalculateVisibleType;
            updateViewVisibilities(iCalculateVisibleType);
            updateBackgroundColor(false);
            return;
        }
        int i = this.mVisibleType;
        if (iCalculateVisibleType != i) {
            this.mTransformationStartVisibleType = i;
            TransformableView transformableViewForVisibleType = getTransformableViewForVisibleType(iCalculateVisibleType);
            TransformableView transformableViewForVisibleType2 = getTransformableViewForVisibleType(this.mTransformationStartVisibleType);
            transformableViewForVisibleType.transformFrom(transformableViewForVisibleType2, 0.0f);
            getViewForVisibleType(iCalculateVisibleType).setVisibility(0);
            transformableViewForVisibleType2.transformTo(transformableViewForVisibleType, 0.0f);
            this.mVisibleType = iCalculateVisibleType;
            updateBackgroundColor(true);
        }
        if (this.mForceSelectNextLayout) {
            forceUpdateVisibilities();
        }
        int i2 = this.mTransformationStartVisibleType;
        if (i2 == -1 || this.mVisibleType == i2 || getViewForVisibleType(i2) == null) {
            updateViewVisibilities(iCalculateVisibleType);
            updateBackgroundColor(false);
            return;
        }
        TransformableView transformableViewForVisibleType3 = getTransformableViewForVisibleType(this.mVisibleType);
        TransformableView transformableViewForVisibleType4 = getTransformableViewForVisibleType(this.mTransformationStartVisibleType);
        float fCalculateTransformationAmount = calculateTransformationAmount();
        transformableViewForVisibleType3.transformFrom(transformableViewForVisibleType4, fCalculateTransformationAmount);
        transformableViewForVisibleType4.transformTo(transformableViewForVisibleType3, fCalculateTransformationAmount);
        updateBackgroundTransformation(fCalculateTransformationAmount);
    }

    private void updateExpandButtonsDuringLayout(boolean z, boolean z2) {
        this.mExpandable = z;
        View view = this.mExpandedChild;
        boolean z3 = false;
        if (view != null && view.getHeight() != 0 && ((this.mIsHeadsUp || this.mHeadsUpAnimatingAway) && this.mHeadsUpChild != null && this.mContainingNotification.canShowHeadsUp() ? this.mExpandedChild.getHeight() <= this.mHeadsUpChild.getHeight() : !(this.mContractedChild != null && this.mExpandedChild.getHeight() > this.mContractedChild.getHeight()))) {
            z = false;
        }
        if (z2 && this.mIsContentExpandable != z) {
            z3 = true;
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.updateExpandability(z, this.mExpandClickListener, z3);
        }
        if (this.mContractedChild != null) {
            this.mContractedWrapper.updateExpandability(z, this.mExpandClickListener, z3);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.updateExpandability(z, this.mExpandClickListener, z3);
        }
        this.mIsContentExpandable = z;
    }

    private void updateLegacy() {
        if (this.mContractedChild != null) {
            this.mContractedWrapper.setLegacy(this.mLegacy);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.setLegacy(this.mLegacy);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.setLegacy(this.mLegacy);
        }
    }

    private void updateShownWrapper(int i) {
        NotificationViewWrapper visibleWrapper = isShown() ? getVisibleWrapper(i) : null;
        NotificationViewWrapper notificationViewWrapper = this.mShownWrapper;
        if (notificationViewWrapper != visibleWrapper) {
            this.mShownWrapper = visibleWrapper;
            if (notificationViewWrapper != null) {
                notificationViewWrapper.onContentShown(false);
            }
            if (visibleWrapper != null) {
                visibleWrapper.onContentShown(true);
            }
        }
    }

    private void updateSingleLineView() {
        try {
            Trace.beginSection("NotifContentView#updateSingleLineView");
            if (AsyncHybridViewInflation.isEnabled()) {
                return;
            }
            AsyncHybridViewInflation.assertInLegacyMode();
            if (this.mIsChildInGroup) {
                HybridNotificationView hybridNotificationView = this.mSingleLineView;
                boolean z = hybridNotificationView == null;
                HybridNotificationView hybridNotificationViewBindFromNotification = this.mHybridGroupManager.bindFromNotification(hybridNotificationView, this.mContractedChild, this.mNotificationEntry.getSbn(), this);
                this.mSingleLineView = hybridNotificationViewBindFromNotification;
                if (z && hybridNotificationViewBindFromNotification != null) {
                    updateViewVisibility(this.mVisibleType, 3, hybridNotificationViewBindFromNotification, hybridNotificationViewBindFromNotification);
                }
            } else {
                View view = this.mSingleLineView;
                if (view != null) {
                    removeView(view);
                    this.mSingleLineView = null;
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    private void updateViewVisibilities(int i) {
        updateViewVisibility(i, 0, this.mContractedChild, this.mContractedWrapper);
        updateViewVisibility(i, 1, this.mExpandedChild, this.mExpandedWrapper);
        updateViewVisibility(i, 2, this.mHeadsUpChild, this.mHeadsUpWrapper);
        HybridNotificationView hybridNotificationView = this.mSingleLineView;
        updateViewVisibility(i, 3, hybridNotificationView, hybridNotificationView);
        updateShownWrapper(i);
        fireExpandedVisibleListenerIfVisible();
        this.mAnimationStartVisibleType = -1;
        notifySubtreeForAccessibilityContentChange();
    }

    private void updateViewVisibility(int i, int i2, View view, TransformableView transformableView) {
        if (view != null) {
            transformableView.setVisible(i == i2);
        }
    }

    private void updateVisibility() {
        setVisible(isShown());
    }

    public int calculateVisibleType() {
        if (!this.mUserExpanding) {
            int intrinsicHeight = this.mContainingNotification.getIntrinsicHeight();
            int iMin = this.mContentHeight;
            if (intrinsicHeight != 0) {
                iMin = Math.min(iMin, intrinsicHeight);
            }
            return getVisualTypeForHeight(iMin);
        }
        int maxContentHeight = (!this.mIsChildInGroup || isGroupExpanded() || this.mContainingNotification.isExpanded(true)) ? this.mContainingNotification.getMaxContentHeight() : this.mContainingNotification.getShowingLayout().getMinHeight();
        if (maxContentHeight == 0) {
            maxContentHeight = this.mContentHeight;
        }
        int visualTypeForHeight = getVisualTypeForHeight(maxContentHeight);
        int visualTypeForHeight2 = (!this.mIsChildInGroup || isGroupExpanded()) ? getVisualTypeForHeight(this.mContainingNotification.getCollapsedHeight()) : 3;
        return this.mTransformationStartVisibleType == visualTypeForHeight2 ? visualTypeForHeight : visualTypeForHeight2;
    }

    public void closeRemoteInput() {
        RemoteInputView remoteInputView = this.mHeadsUpRemoteInput;
        if (remoteInputView != null) {
            remoteInputView.close();
        }
        RemoteInputView remoteInputView2 = this.mExpandedRemoteInput;
        if (remoteInputView2 != null) {
            remoteInputView2.close();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        try {
            super.dispatchDraw(canvas);
        } catch (Exception e) {
            Log.e("NotificationContentView", "Drawing view failed: " + e);
            cancelNotification(e);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        float y = motionEvent.getY();
        RemoteInputView remoteInputForView = getRemoteInputForView(getViewForVisibleType(this.mVisibleType));
        if (remoteInputForView != null && remoteInputForView.getVisibility() == 0) {
            int height = this.mUnrestrictedContentHeight - remoteInputForView.getHeight();
            if (y <= this.mUnrestrictedContentHeight && y >= height) {
                motionEvent.offsetLocation(0.0f, -height);
                return remoteInputForView.dispatchTouchEvent(motionEvent);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public View focusSearch(View view, int i) {
        ViewParent viewParent = ((FrameLayout) this).mParent;
        if (viewParent != null) {
            return viewParent.focusSearch(view, i);
        }
        Log.wtf("NotificationContentView", "NotificationContentView doesn't have parent");
        return null;
    }

    public View[] getAllViews() {
        return new View[]{this.mContractedChild, this.mHeadsUpChild, this.mExpandedChild, this.mSingleLineView};
    }

    public int getBackgroundColor(int i) {
        NotificationViewWrapper visibleWrapper = getVisibleWrapper(i);
        if (visibleWrapper != null) {
            return visibleWrapper.getCustomBackgroundColor();
        }
        return 0;
    }

    public int getBackgroundColorForExpansionState() {
        return getBackgroundColor((isGroupExpanded() || this.mContainingNotification.isUserLocked()) ? calculateVisibleType() : getVisibleType());
    }

    public View getContractedChild() {
        return this.mContractedChild;
    }

    protected NotificationViewWrapper getContractedWrapper() {
        return this.mContractedWrapper;
    }

    public InflatedSmartReplyState getCurrentSmartReplyState() {
        return this.mCurrentSmartReplyState;
    }

    public int getExpandHeight() {
        int i;
        if (this.mExpandedChild != null) {
            i = 1;
        } else {
            if (this.mContractedChild == null) {
                return getMinHeight();
            }
            i = 0;
        }
        return getViewHeight(i) + getExtraRemoteInputHeight(this.mExpandedRemoteInput);
    }

    public View getExpandedChild() {
        return this.mExpandedChild;
    }

    public RemoteInputView getExpandedRemoteInput() {
        return this.mExpandedRemoteInput;
    }

    protected NotificationViewWrapper getExpandedWrapper() {
        return this.mExpandedWrapper;
    }

    public View getHeadsUpChild() {
        return this.mHeadsUpChild;
    }

    public int getHeadsUpHeight(boolean z) {
        int i;
        if (this.mHeadsUpChild != null) {
            i = 2;
        } else {
            if (this.mContractedChild == null) {
                return getMinHeight();
            }
            i = 0;
        }
        return getViewHeight(i, z) + getExtraRemoteInputHeight(this.mHeadsUpRemoteInput) + getExtraRemoteInputHeight(this.mExpandedRemoteInput);
    }

    protected NotificationViewWrapper getHeadsUpWrapper() {
        return this.mHeadsUpWrapper;
    }

    public int getMaxHeight() {
        int viewHeight;
        int extraRemoteInputHeight;
        if (this.mExpandedChild != null) {
            viewHeight = getViewHeight(1);
            extraRemoteInputHeight = getExtraRemoteInputHeight(this.mExpandedRemoteInput);
        } else {
            if (!this.mIsHeadsUp || this.mHeadsUpChild == null || !this.mContainingNotification.canShowHeadsUp()) {
                return this.mContractedChild != null ? getViewHeight(0) : this.mNotificationMaxHeight;
            }
            viewHeight = getViewHeight(2);
            extraRemoteInputHeight = getExtraRemoteInputHeight(this.mHeadsUpRemoteInput);
        }
        return viewHeight + extraRemoteInputHeight;
    }

    public int getMinHeight() {
        return getMinHeight(false);
    }

    public int getMinHeight(boolean z) {
        if (z || !this.mIsChildInGroup || isGroupExpanded()) {
            return this.mContractedChild != null ? getViewHeight(0) : this.mMinContractedHeight;
        }
        if (AsyncHybridViewInflation.isEnabled()) {
            return this.mSingleLineView != null ? getViewHeight(3) : this.mMinSingleLineHeight;
        }
        AsyncHybridViewInflation.assertInLegacyMode();
        return this.mSingleLineView.getHeight();
    }

    public NotificationViewWrapper getNotificationViewWrapper() {
        NotificationViewWrapper notificationViewWrapper;
        NotificationViewWrapper notificationViewWrapper2;
        NotificationViewWrapper notificationViewWrapper3;
        if (this.mContractedChild != null && (notificationViewWrapper3 = this.mContractedWrapper) != null) {
            return notificationViewWrapper3;
        }
        if (this.mExpandedChild != null && (notificationViewWrapper2 = this.mExpandedWrapper) != null) {
            return notificationViewWrapper2;
        }
        if (this.mHeadsUpChild == null || (notificationViewWrapper = this.mHeadsUpWrapper) == null) {
            return null;
        }
        return notificationViewWrapper;
    }

    public int getOriginalIconColor() {
        NotificationViewWrapper visibleWrapper = getVisibleWrapper(this.mVisibleType);
        if (visibleWrapper != null) {
            return visibleWrapper.getOriginalIconColor();
        }
        return 1;
    }

    public HybridNotificationView getSingleLineView() {
        return this.mSingleLineView;
    }

    public int getVisibleType() {
        return this.mVisibleType;
    }

    public NotificationViewWrapper getVisibleWrapper() {
        return getVisibleWrapper(this.mVisibleType);
    }

    public NotificationViewWrapper getVisibleWrapper(int i) {
        if (i == 0) {
            return this.mContractedWrapper;
        }
        if (i == 1) {
            return this.mExpandedWrapper;
        }
        if (i != 2) {
            return null;
        }
        return this.mHeadsUpWrapper;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void initialize(PeopleNotificationIdentifier peopleNotificationIdentifier, RemoteInputViewSubcomponent.Factory factory, SmartReplyConstants smartReplyConstants, SmartReplyController smartReplyController, IStatusBarService iStatusBarService) {
        this.mPeopleIdentifier = peopleNotificationIdentifier;
        this.mRemoteInputSubcomponentFactory = factory;
        this.mSmartReplyConstants = smartReplyConstants;
        this.mSmartReplyController = smartReplyController;
        this.mStatusBarService = iStatusBarService;
        setIsRootNamespace(true);
    }

    boolean isAnimatingVisibleType() {
        return this.mAnimationStartVisibleType != -1;
    }

    public boolean isContentExpandable() {
        return this.mIsContentExpandable;
    }

    public boolean isContentViewInactive(int i) {
        return isContentViewInactive(getViewForVisibleType(i));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void notifySubtreeAccessibilityStateChanged(View view, View view2, int i) {
        if (isAnimatingVisibleType()) {
            return;
        }
        super.notifySubtreeAccessibilityStateChanged(view, view2, i);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateVisibility();
    }

    protected void onChildVisibilityChanged(View view, int i, int i2) {
        Runnable runnable;
        super.onChildVisibilityChanged(view, i, i2);
        if (!isContentViewInactive(view) || (runnable = (Runnable) this.mOnContentViewInactiveListeners.remove(view)) == null) {
            return;
        }
        runnable.run();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnPreDrawListener(this.mEnableAnimationPredrawListener);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View view = this.mExpandedChild;
        int height = view != null ? view.getHeight() : 0;
        super.onLayout(z, i, i2, i3, i4);
        if (height != 0 && this.mExpandedChild.getHeight() != height) {
            this.mContentHeightAtAnimationStart = height;
        }
        updateClipping();
        invalidateOutline();
        selectLayout(false, this.mForceSelectNextLayout);
        this.mForceSelectNextLayout = false;
        updateExpandButtonsDuringLayout(this.mExpandable, true);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int iMax;
        boolean z;
        boolean z2;
        int mode = View.MeasureSpec.getMode(i2);
        boolean z3 = true;
        boolean z4 = mode == 1073741824;
        boolean z5 = mode == Integer.MIN_VALUE;
        int size = View.MeasureSpec.getSize(i);
        int size2 = (z4 || z5) ? View.MeasureSpec.getSize(i2) : 1073741823;
        if (this.mExpandedChild != null) {
            int heightUpperLimit = this.mNotificationMaxHeight;
            SmartReplyView smartReplyView = this.mExpandedSmartReplyView;
            if (smartReplyView != null) {
                heightUpperLimit += smartReplyView.getHeightUpperLimit();
            }
            int extraMeasureHeight = heightUpperLimit + this.mExpandedWrapper.getExtraMeasureHeight();
            int i3 = this.mExpandedChild.getLayoutParams().height;
            if (i3 >= 0) {
                extraMeasureHeight = Math.min(extraMeasureHeight, i3);
                z2 = true;
            } else {
                z2 = false;
            }
            measureChildWithMargins(this.mExpandedChild, i, 0, View.MeasureSpec.makeMeasureSpec(extraMeasureHeight, z2 ? 1073741824 : Integer.MIN_VALUE), 0);
            iMax = Math.max(0, this.mExpandedChild.getMeasuredHeight());
        } else {
            iMax = 0;
        }
        View view = this.mContractedChild;
        if (view != null) {
            int iMin = this.mSmallHeight;
            int i4 = view.getLayoutParams().height;
            if (i4 >= 0) {
                iMin = Math.min(iMin, i4);
                z = true;
            } else {
                z = false;
            }
            measureChildWithMargins(this.mContractedChild, i, 0, (shouldContractedBeFixedSize() || z) ? View.MeasureSpec.makeMeasureSpec(iMin, 1073741824) : View.MeasureSpec.makeMeasureSpec(iMin, Integer.MIN_VALUE), 0);
            int measuredHeight = this.mContractedChild.getMeasuredHeight();
            int i5 = this.mMinContractedHeight;
            if (measuredHeight < i5) {
                measureChildWithMargins(this.mContractedChild, i, 0, View.MeasureSpec.makeMeasureSpec(i5, 1073741824), 0);
            }
            iMax = Math.max(iMax, measuredHeight);
            if (this.mExpandedChild != null && this.mContractedChild.getMeasuredHeight() > this.mExpandedChild.getMeasuredHeight()) {
                measureChildWithMargins(this.mExpandedChild, i, 0, View.MeasureSpec.makeMeasureSpec(this.mContractedChild.getMeasuredHeight(), 1073741824), 0);
            }
        }
        if (this.mHeadsUpChild != null) {
            int heightUpperLimit2 = this.mHeadsUpHeight;
            SmartReplyView smartReplyView2 = this.mHeadsUpSmartReplyView;
            if (smartReplyView2 != null) {
                heightUpperLimit2 += smartReplyView2.getHeightUpperLimit();
            }
            int extraMeasureHeight2 = heightUpperLimit2 + this.mHeadsUpWrapper.getExtraMeasureHeight();
            int i6 = this.mHeadsUpChild.getLayoutParams().height;
            if (i6 >= 0) {
                extraMeasureHeight2 = Math.min(extraMeasureHeight2, i6);
            } else {
                z3 = false;
            }
            measureChildWithMargins(this.mHeadsUpChild, i, 0, View.MeasureSpec.makeMeasureSpec(extraMeasureHeight2, z3 ? 1073741824 : Integer.MIN_VALUE), 0);
            iMax = Math.max(iMax, this.mHeadsUpChild.getMeasuredHeight());
        }
        if (this.mSingleLineView != null) {
            this.mSingleLineView.measure((this.mSingleLineWidthIndention == 0 || View.MeasureSpec.getMode(i) == 0) ? i : View.MeasureSpec.makeMeasureSpec((size - this.mSingleLineWidthIndention) + this.mSingleLineView.getPaddingEnd(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.mNotificationMaxHeight, Integer.MIN_VALUE));
            iMax = Math.max(iMax, this.mSingleLineView.getMeasuredHeight());
        }
        setMeasuredDimension(size, Math.min(iMax, size2));
    }

    public void onNotificationUpdated(NotificationEntry notificationEntry) {
        this.mNotificationEntry = notificationEntry;
        this.mBeforeN = notificationEntry.targetSdk < 24;
        updateAllSingleLineViews();
        ExpandableNotificationRow row = notificationEntry.getRow();
        if (this.mContractedChild != null) {
            this.mContractedWrapper.onContentUpdated(row);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.onContentUpdated(row);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.onContentUpdated(row);
        }
        applyRemoteInputAndSmartReply();
        updateLegacy();
        this.mForceSelectNextLayout = true;
        this.mPreviousExpandedRemoteInputIntent = null;
        this.mPreviousHeadsUpRemoteInputIntent = null;
        applySystemActions(this.mExpandedChild, notificationEntry);
        applySystemActions(this.mHeadsUpChild, notificationEntry);
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        view.setTag(com.android.systemui.res.R$id.row_tag_for_content_view, this.mContainingNotification);
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        updateShownWrapper(this.mVisibleType);
        if (z) {
            fireExpandedVisibleListenerIfVisible();
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        updateVisibility();
        if (i == 0 || this.mOnContentViewInactiveListeners.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mOnContentViewInactiveListeners.values());
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((Runnable) obj).run();
        }
        this.mOnContentViewInactiveListeners.clear();
    }

    void performWhenContentInactive(int i, Runnable runnable) {
        View viewForVisibleType = getViewForVisibleType(i);
        if (viewForVisibleType == null || isContentViewInactive(i)) {
            runnable.run();
        } else {
            this.mOnContentViewInactiveListeners.put(viewForVisibleType, runnable);
        }
    }

    public boolean pointInView(float f, float f2, float f3) {
        return f >= (-f3) && f2 >= ((float) this.mClipTopAmount) - f3 && f < ((float) (((FrameLayout) this).mRight - ((FrameLayout) this).mLeft)) + f3 && f2 < ((float) this.mUnrestrictedContentHeight) + f3;
    }

    public void reInflateViews() {
        HybridNotificationView hybridNotificationView;
        if (!this.mIsChildInGroup || (hybridNotificationView = this.mSingleLineView) == null) {
            return;
        }
        removeView(hybridNotificationView);
        this.mSingleLineView = null;
        updateAllSingleLineViews();
    }

    public void reinflate() {
        this.mMinContractedHeight = getResources().getDimensionPixelSize(R$dimen.min_notification_layout_height);
        if (AsyncHybridViewInflation.isEnabled()) {
            this.mMinSingleLineHeight = getResources().getDimensionPixelSize(R$dimen.conversation_single_line_face_pile_size);
        }
    }

    void removeContentInactiveRunnable(int i) {
        View viewForVisibleType = getViewForVisibleType(i);
        if (viewForVisibleType == null) {
            return;
        }
        this.mOnContentViewInactiveListeners.remove(viewForVisibleType);
    }

    public void requestSelectLayout(boolean z) {
        selectLayout(z, false);
    }

    public boolean requireRowToHaveOverlappingRendering() {
        RemoteInputView remoteInputView = this.mHeadsUpRemoteInput;
        if (remoteInputView != null && remoteInputView.isActive()) {
            return true;
        }
        RemoteInputView remoteInputView2 = this.mExpandedRemoteInput;
        return remoteInputView2 != null && remoteInputView2.isActive();
    }

    protected void setAnimationStartVisibleType(int i) {
        this.mAnimationStartVisibleType = i;
    }

    public void setBackgroundTintColor(int i) {
        boolean zIsColorized = this.mNotificationEntry.getSbn().getNotification().isColorized();
        SmartReplyView smartReplyView = this.mExpandedSmartReplyView;
        if (smartReplyView != null) {
            smartReplyView.setBackgroundTintColor(i, zIsColorized);
        }
        SmartReplyView smartReplyView2 = this.mHeadsUpSmartReplyView;
        if (smartReplyView2 != null) {
            smartReplyView2.setBackgroundTintColor(i, zIsColorized);
        }
        RemoteInputView remoteInputView = this.mExpandedRemoteInput;
        if (remoteInputView != null) {
            remoteInputView.setBackgroundTintColor(i, zIsColorized);
        }
        RemoteInputView remoteInputView2 = this.mHeadsUpRemoteInput;
        if (remoteInputView2 != null) {
            remoteInputView2.setBackgroundTintColor(i, zIsColorized);
        }
    }

    public void setBubblesEnabledForUser(boolean z) {
        this.mBubblesEnabledForUser = z;
        applyBubbleAction(this.mExpandedChild, this.mNotificationEntry);
        applyBubbleAction(this.mHeadsUpChild, this.mNotificationEntry);
    }

    public void setClipBottomAmount(int i) {
        this.mClipBottomAmount = i;
        updateClipping();
    }

    @Override // android.view.ViewGroup
    public void setClipChildren(boolean z) {
        super.setClipChildren(z && !this.mRemoteInputVisible);
    }

    public void setClipToActualHeight(boolean z) {
        this.mClipToActualHeight = z;
        updateClipping();
    }

    public void setClipTopAmount(int i) {
        this.mClipTopAmount = i;
        updateClipping();
    }

    public void setContainingNotification(ExpandableNotificationRow expandableNotificationRow) {
        this.mContainingNotification = expandableNotificationRow;
    }

    public boolean setContentAnimationRunning(boolean z) {
        if (z == this.mContentAnimating) {
            return false;
        }
        NotificationViewWrapper notificationViewWrapper = this.mContractedWrapper;
        if (notificationViewWrapper != null) {
            notificationViewWrapper.setAnimationsRunning(z);
        }
        NotificationViewWrapper notificationViewWrapper2 = this.mExpandedWrapper;
        if (notificationViewWrapper2 != null) {
            notificationViewWrapper2.setAnimationsRunning(z);
        }
        NotificationViewWrapper notificationViewWrapper3 = this.mHeadsUpWrapper;
        if (notificationViewWrapper3 != null) {
            notificationViewWrapper3.setAnimationsRunning(z);
        }
        this.mContentAnimating = z;
        return true;
    }

    public void setContentHeight(int i) {
        this.mUnrestrictedContentHeight = Math.max(i, getMinHeight());
        this.mContentHeight = Math.min(this.mUnrestrictedContentHeight, (this.mContainingNotification.getIntrinsicHeight() - getExtraRemoteInputHeight(this.mExpandedRemoteInput)) - getExtraRemoteInputHeight(this.mHeadsUpRemoteInput));
        selectLayout(this.mAnimate, false);
        if (this.mContractedChild == null) {
            return;
        }
        int minContentHeightHint = getMinContentHeightHint();
        NotificationViewWrapper visibleWrapper = getVisibleWrapper(this.mVisibleType);
        if (visibleWrapper != null) {
            visibleWrapper.setContentHeight(this.mUnrestrictedContentHeight, minContentHeightHint);
        }
        NotificationViewWrapper visibleWrapper2 = getVisibleWrapper(this.mTransformationStartVisibleType);
        if (visibleWrapper2 != null) {
            visibleWrapper2.setContentHeight(this.mUnrestrictedContentHeight, minContentHeightHint);
        }
        updateClipping();
        invalidateOutline();
    }

    public void setContentHeightAnimating(boolean z) {
        if (z) {
            return;
        }
        this.mContentHeightAtAnimationStart = -1;
    }

    public void setContractedChild(View view) {
        View view2 = this.mContractedChild;
        if (view2 != null) {
            this.mOnContentViewInactiveListeners.remove(view2);
            this.mContractedChild.animate().cancel();
            removeView(this.mContractedChild);
        }
        if (view != null) {
            addView(view);
            this.mContractedChild = view;
            this.mContractedWrapper = NotificationViewWrapper.wrap(getContext(), view, this.mContainingNotification);
            updateShownWrapper(this.mVisibleType);
            return;
        }
        this.mContractedChild = null;
        this.mContractedWrapper = null;
        if (this.mTransformationStartVisibleType == 0) {
            this.mTransformationStartVisibleType = -1;
        }
    }

    protected void setContractedWrapper(NotificationViewWrapper notificationViewWrapper) {
        this.mContractedWrapper = notificationViewWrapper;
    }

    public void setExpandClickListener(View.OnClickListener onClickListener) {
        this.mExpandClickListener = onClickListener;
    }

    public void setExpandedChild(View view) {
        if (this.mExpandedChild != null) {
            this.mPreviousExpandedRemoteInputIntent = null;
            RemoteInputView remoteInputView = this.mExpandedRemoteInput;
            if (remoteInputView != null) {
                remoteInputView.onNotificationUpdateOrReset();
                if (this.mExpandedRemoteInput.isActive()) {
                    RemoteInputViewController remoteInputViewController = this.mExpandedRemoteInputController;
                    if (remoteInputViewController != null) {
                        this.mPreviousExpandedRemoteInputIntent = remoteInputViewController.getPendingIntent();
                    }
                    RemoteInputView remoteInputView2 = this.mExpandedRemoteInput;
                    this.mCachedExpandedRemoteInput = remoteInputView2;
                    this.mCachedExpandedRemoteInputViewController = this.mExpandedRemoteInputController;
                    remoteInputView2.dispatchStartTemporaryDetach();
                    ((ViewGroup) this.mExpandedRemoteInput.getParent()).removeView(this.mExpandedRemoteInput);
                }
            }
            this.mOnContentViewInactiveListeners.remove(this.mExpandedChild);
            this.mExpandedChild.animate().cancel();
            removeView(this.mExpandedChild);
            this.mExpandedRemoteInput = null;
            RemoteInputViewController remoteInputViewController2 = this.mExpandedRemoteInputController;
            if (remoteInputViewController2 != null) {
                remoteInputViewController2.unbind();
            }
            this.mExpandedRemoteInputController = null;
        }
        if (view == null) {
            this.mExpandedChild = null;
            this.mExpandedWrapper = null;
            if (this.mTransformationStartVisibleType == 1) {
                this.mTransformationStartVisibleType = -1;
            }
            if (this.mVisibleType == 1) {
                selectLayout(false, true);
                return;
            }
            return;
        }
        addView(view);
        this.mExpandedChild = view;
        this.mExpandedWrapper = NotificationViewWrapper.wrap(getContext(), view, this.mContainingNotification);
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if (expandableNotificationRow != null) {
            applySystemActions(this.mExpandedChild, expandableNotificationRow.getEntry());
        }
        updateShownWrapper(this.mVisibleType);
    }

    public void setExpandedInflatedSmartReplies(InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder) {
        this.mExpandedInflatedSmartReplies = inflatedSmartReplyViewHolder;
        if (inflatedSmartReplyViewHolder == null) {
            this.mExpandedSmartReplyView = null;
        }
    }

    protected void setExpandedWrapper(NotificationViewWrapper notificationViewWrapper) {
        this.mExpandedWrapper = notificationViewWrapper;
    }

    public void setFeedbackIcon(FeedbackIcon feedbackIcon) {
        if (this.mContractedChild != null) {
            this.mContractedWrapper.setFeedbackIcon(feedbackIcon);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.setFeedbackIcon(feedbackIcon);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.setFeedbackIcon(feedbackIcon);
        }
    }

    public void setFocusOnVisibilityChange() {
        this.mFocusOnVisibilityChange = true;
    }

    public void setGroupMembershipManager(GroupMembershipManager groupMembershipManager) {
    }

    public void setHeadsUp(boolean z) {
        this.mIsHeadsUp = z;
        selectLayout(false, true);
        updateExpandButtons(this.mExpandable);
    }

    public void setHeadsUpAnimatingAway(boolean z) {
        this.mHeadsUpAnimatingAway = z;
        selectLayout(false, true);
    }

    public void setHeadsUpChild(View view) {
        if (this.mHeadsUpChild != null) {
            this.mPreviousHeadsUpRemoteInputIntent = null;
            RemoteInputView remoteInputView = this.mHeadsUpRemoteInput;
            if (remoteInputView != null) {
                remoteInputView.onNotificationUpdateOrReset();
                if (this.mHeadsUpRemoteInput.isActive()) {
                    RemoteInputViewController remoteInputViewController = this.mHeadsUpRemoteInputController;
                    if (remoteInputViewController != null) {
                        this.mPreviousHeadsUpRemoteInputIntent = remoteInputViewController.getPendingIntent();
                    }
                    RemoteInputView remoteInputView2 = this.mHeadsUpRemoteInput;
                    this.mCachedHeadsUpRemoteInput = remoteInputView2;
                    this.mCachedHeadsUpRemoteInputViewController = this.mHeadsUpRemoteInputController;
                    remoteInputView2.dispatchStartTemporaryDetach();
                    ((ViewGroup) this.mHeadsUpRemoteInput.getParent()).removeView(this.mHeadsUpRemoteInput);
                }
            }
            this.mOnContentViewInactiveListeners.remove(this.mHeadsUpChild);
            this.mHeadsUpChild.animate().cancel();
            removeView(this.mHeadsUpChild);
            this.mHeadsUpRemoteInput = null;
            RemoteInputViewController remoteInputViewController2 = this.mHeadsUpRemoteInputController;
            if (remoteInputViewController2 != null) {
                remoteInputViewController2.unbind();
            }
            this.mHeadsUpRemoteInputController = null;
        }
        if (view == null) {
            this.mHeadsUpChild = null;
            this.mHeadsUpWrapper = null;
            if (this.mTransformationStartVisibleType == 2) {
                this.mTransformationStartVisibleType = -1;
            }
            if (this.mVisibleType == 2) {
                selectLayout(false, true);
                return;
            }
            return;
        }
        addView(view);
        this.mHeadsUpChild = view;
        this.mHeadsUpWrapper = NotificationViewWrapper.wrap(getContext(), view, this.mContainingNotification);
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        if (expandableNotificationRow != null) {
            applySystemActions(this.mHeadsUpChild, expandableNotificationRow.getEntry());
        }
        updateShownWrapper(this.mVisibleType);
    }

    public void setHeadsUpInflatedSmartReplies(InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder) {
        this.mHeadsUpInflatedSmartReplies = inflatedSmartReplyViewHolder;
        if (inflatedSmartReplyViewHolder == null) {
            this.mHeadsUpSmartReplyView = null;
        }
    }

    protected void setHeadsUpWrapper(NotificationViewWrapper notificationViewWrapper) {
        this.mHeadsUpWrapper = notificationViewWrapper;
    }

    public void setHeights(int i, int i2, int i3) {
        this.mSmallHeight = i;
        this.mHeadsUpHeight = i2;
        this.mNotificationMaxHeight = i3;
    }

    public void setInflatedSmartReplyState(InflatedSmartReplyState inflatedSmartReplyState) {
        this.mCurrentSmartReplyState = inflatedSmartReplyState;
    }

    public void setIsChildInGroup(boolean z) {
        this.mIsChildInGroup = z;
        if (this.mContractedChild != null) {
            this.mContractedWrapper.setIsChildInGroup(z);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.setIsChildInGroup(this.mIsChildInGroup);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.setIsChildInGroup(this.mIsChildInGroup);
        }
        updateAllSingleLineViews();
    }

    public void setIsLowPriority(boolean z) {
    }

    public void setLegacy(boolean z) {
        this.mLegacy = z;
        updateLegacy();
    }

    @Override // com.android.systemui.statusbar.notification.NotificationFadeAware
    public void setNotificationFaded(boolean z) {
        NotificationViewWrapper notificationViewWrapper = this.mContractedWrapper;
        if (notificationViewWrapper != null) {
            notificationViewWrapper.setNotificationFaded(z);
        }
        NotificationViewWrapper notificationViewWrapper2 = this.mHeadsUpWrapper;
        if (notificationViewWrapper2 != null) {
            notificationViewWrapper2.setNotificationFaded(z);
        }
        NotificationViewWrapper notificationViewWrapper3 = this.mExpandedWrapper;
        if (notificationViewWrapper3 != null) {
            notificationViewWrapper3.setNotificationFaded(z);
        }
        HybridNotificationView hybridNotificationView = this.mSingleLineView;
        if (hybridNotificationView != null) {
            hybridNotificationView.setNotificationFaded(z);
        }
    }

    public void setNotificationWhen(long j) {
        NotificationViewWrapper notificationViewWrapper = getNotificationViewWrapper();
        if (notificationViewWrapper instanceof NotificationHeaderViewWrapper) {
            ((NotificationHeaderViewWrapper) notificationViewWrapper).setNotificationWhen(j);
        }
    }

    public void setOnExpandedVisibleListener(Runnable runnable) {
        this.mExpandedVisibleListener = runnable;
        fireExpandedVisibleListenerIfVisible();
    }

    public void setRecentlyAudiblyAlerted(boolean z) {
        if (this.mContractedChild != null) {
            this.mContractedWrapper.setRecentlyAudiblyAlerted(z);
        }
        if (this.mExpandedChild != null) {
            this.mExpandedWrapper.setRecentlyAudiblyAlerted(z);
        }
        if (this.mHeadsUpChild != null) {
            this.mHeadsUpWrapper.setRecentlyAudiblyAlerted(z);
        }
    }

    public void setRemoteInputController(RemoteInputController remoteInputController) {
        this.mRemoteInputController = remoteInputController;
    }

    public void setRemoteInputVisible(boolean z) {
        this.mRemoteInputVisible = z;
        setClipChildren(!z);
        setActionsImportanceForAccessibility(z ? 4 : 0);
    }

    public void setRemoved() {
        RemoteInputView remoteInputView = this.mExpandedRemoteInput;
        if (remoteInputView != null) {
            remoteInputView.setRemoved();
        }
        RemoteInputView remoteInputView2 = this.mHeadsUpRemoteInput;
        if (remoteInputView2 != null) {
            remoteInputView2.setRemoved();
        }
        NotificationViewWrapper notificationViewWrapper = this.mExpandedWrapper;
        if (notificationViewWrapper != null) {
            notificationViewWrapper.setRemoved();
        }
        NotificationViewWrapper notificationViewWrapper2 = this.mContractedWrapper;
        if (notificationViewWrapper2 != null) {
            notificationViewWrapper2.setRemoved();
        }
        NotificationViewWrapper notificationViewWrapper3 = this.mHeadsUpWrapper;
        if (notificationViewWrapper3 != null) {
            notificationViewWrapper3.setRemoved();
        }
    }

    public void setSingleLineView(HybridNotificationView hybridNotificationView) {
        if (AsyncHybridViewInflation.isUnexpectedlyInLegacyMode()) {
            return;
        }
        HybridNotificationView hybridNotificationView2 = this.mSingleLineView;
        if (hybridNotificationView2 != null) {
            this.mOnContentViewInactiveListeners.remove(hybridNotificationView2);
            this.mSingleLineView.animate().cancel();
            removeView(this.mSingleLineView);
        }
        if (hybridNotificationView != null) {
            addView(hybridNotificationView);
            this.mSingleLineView = hybridNotificationView;
        } else {
            this.mSingleLineView = null;
            if (this.mTransformationStartVisibleType == 3) {
                this.mTransformationStartVisibleType = -1;
            }
        }
    }

    public void setSingleLineWidthIndention(int i) {
        if (i != this.mSingleLineWidthIndention) {
            this.mSingleLineWidthIndention = i;
            this.mContainingNotification.forceLayout();
            forceLayout();
        }
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        updateClipping();
    }

    public void setUserExpanding(boolean z) {
        this.mUserExpanding = z;
        if (z) {
            this.mTransformationStartVisibleType = this.mVisibleType;
            return;
        }
        this.mTransformationStartVisibleType = -1;
        int iCalculateVisibleType = calculateVisibleType();
        this.mVisibleType = iCalculateVisibleType;
        updateViewVisibilities(iCalculateVisibleType);
        updateBackgroundColor(false);
    }

    public boolean shouldClipToRounding(boolean z, boolean z2) {
        boolean zShouldClipToRounding = shouldClipToRounding(getVisibleType(), z, z2);
        return this.mUserExpanding ? shouldClipToRounding(this.mTransformationStartVisibleType, z, z2) | zShouldClipToRounding : zShouldClipToRounding;
    }

    boolean shouldShowBubbleButton(NotificationEntry notificationEntry) {
        return this.mBubblesEnabledForUser && (this.mPeopleIdentifier.getPeopleNotificationType(notificationEntry) >= 2) && notificationEntry.getBubbleMetadata() != null;
    }

    public void updateBackgroundColor(boolean z) {
        this.mContainingNotification.setContentBackground(getBackgroundColor(this.mVisibleType), z, this);
    }

    public void updateExpandButtons(boolean z) {
        updateExpandButtonsDuringLayout(z, false);
    }
}
