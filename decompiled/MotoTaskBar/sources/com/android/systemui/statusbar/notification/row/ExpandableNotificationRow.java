package com.android.systemui.statusbar.notification.row;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.widget.CallLayout;
import com.android.systemui.Dependency;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.res.R$bool;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.AboveShelfChangedListener;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationContentAlphaOptimization;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.SwipeableView;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.ColorUtilKt;
import com.android.systemui.util.Compile;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.qsnotification.QsNotificationTooltipPopupManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableNotificationRow extends ActivatableNotificationView implements SwipeableView, NotificationFadeAware.FadeOptimizedNotification {
    private static final SourceType BASE_VALUE;
    private static final boolean DEBUG;
    private static final boolean DEBUG_ONMEASURE;
    private static final SourceType FROM_PARENT;
    private static final long RECENTLY_ALERTED_THRESHOLD_MS;
    private static final int SLOW_MEASURE_SIMULATE_DELAY_MS;
    public static final FloatProperty TRANSLATE_CONTENT;
    private boolean mAboveShelf;
    private AboveShelfChangedListener mAboveShelfChangedListener;
    private boolean mAnimationRunning;
    private String mAppName;
    private BigPictureIconManager mBigPictureIconManager;
    private View mChildAfterViewWhenDismissed;
    private boolean mChildIsExpanding;
    private NotificationChildrenContainer mChildrenContainer;
    private NotificationChildrenContainerLogger mChildrenContainerLogger;
    private ViewStub mChildrenContainerStub;
    private boolean mChildrenExpanded;
    private ColorUpdateLogger mColorUpdateLogger;
    private NotificationDismissibilityProvider mDismissibilityProvider;
    private ExpandableNotificationRowDragController mDragController;
    private boolean mEnableNonGroupedNotificationExpand;
    private NotificationEntry mEntry;
    private boolean mExpandAnimationRunning;
    private View.OnClickListener mExpandClickListener;
    private boolean mExpandable;
    private boolean mExpandedWhenPinned;
    private Path mExpandingClipPath;
    private OnExpansionChangedListener mExpansionChangedListener;
    private final Runnable mExpireRecentlyAlertedFlag;
    private FeatureFlags mFeatureFlags;
    private boolean mGroupExpansionChanging;
    private GroupExpansionManager mGroupExpansionManager;
    private GroupMembershipManager mGroupMembershipManager;
    private View mGroupParentWhenDismissed;
    private boolean mHasUserChangedExpansion;
    private float mHeaderVisibleAmount;
    private Consumer mHeadsUpAnimatingAwayListener;
    private HeadsUpManager mHeadsUpManager;
    private boolean mHeadsupDisappearRunning;
    private boolean mHideSensitiveForIntrinsicHeight;
    private int mIconTransformContentShift;
    private boolean mIgnoreLockscreenConstraints;
    private final NotificationInlineImageResolver mImageResolver;
    private boolean mIsFaded;
    private boolean mIsHeadsUp;
    private boolean mIsMinimized;
    private boolean mIsPinned;
    private boolean mIsSnoozed;
    private boolean mIsSummaryWithChildren;
    private boolean mIsSystemChildExpanded;
    private boolean mIsSystemExpanded;
    private boolean mJustClicked;
    private boolean mKeepInParentForDismissAnimation;
    private boolean mLastChronometerRunning;
    private NotificationContentView[] mLayouts;
    private ExpandableNotificationRowLogger mLogger;
    private String mLoggingKey;
    private int mMaxExpandedHeight;
    private int mMaxHeadsUpHeight;
    private int mMaxHeadsUpHeightBeforeN;
    private int mMaxHeadsUpHeightBeforeP;
    private int mMaxHeadsUpHeightBeforeS;
    private int mMaxHeadsUpHeightIncreased;
    private int mMaxSmallHeight;
    private int mMaxSmallHeightBeforeN;
    private int mMaxSmallHeightBeforeP;
    private int mMaxSmallHeightBeforeS;
    private int mMaxSmallHeightLarge;
    private boolean mMustStayOnScreen;
    private int mNotificationColor;
    private ExpandableNotificationRow mNotificationParent;
    private View.OnClickListener mOnClickListener;
    private OnDragSuccessListener mOnDragSuccessListener;
    private OnExpandClickListener mOnExpandClickListener;
    private Runnable mOnIntrinsicHeightReachedRunnable;
    private boolean mOnKeyguard;
    private OnUserInteractionCallback mOnUserInteractionCallback;
    private PeopleNotificationIdentifier mPeopleNotificationIdentifier;
    private NotificationContentView mPrivateLayout;
    private NotificationContentView mPublicLayout;
    private boolean mRemoved;
    private RowContentBindStage mRowContentBindStage;
    private boolean mSaveSpaceOnLockscreen;
    private boolean mSensitive;
    private boolean mSensitiveHiddenInGeneral;
    private boolean mShowGroupBackgroundWhenExpanded;
    private boolean mShowNoBackground;
    private boolean mShowPublicExpander;
    private boolean mShowSnooze;
    private boolean mShowingPublic;
    private boolean mShowingPublicInitialized;
    private float mSmallRoundness;
    private QsNotificationTooltipPopupManager mTooltipPopupManager;
    private long mTooltipPopupShowingId;
    private Animator mTranslateAnim;
    private ArrayList mTranslateableViews;
    private float mTranslationWhenRemoved;
    private boolean mUpdateSelfBackgroundOnUpdate;
    private boolean mUseIncreasedCollapsedHeight;
    private boolean mUseIncreasedHeadsUpHeight;
    private boolean mUserExpanded;
    private boolean mUserLocked;
    private boolean mWasChildInGroupWhenRemoved;

    public interface ExpandableNotificationRowLogger {
        void logAddTransientRow(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, int i);

        void logKeepInParentChildDetached(NotificationEntry notificationEntry, NotificationEntry notificationEntry2);

        void logNotificationExpansion(String str, int i, boolean z, boolean z2);

        void logRemoveTransientFromContainer(NotificationEntry notificationEntry, NotificationEntry notificationEntry2);

        void logRemoveTransientFromNssl(NotificationEntry notificationEntry);

        void logRemoveTransientFromViewGroup(NotificationEntry notificationEntry, ViewGroup viewGroup);

        void logRemoveTransientRow(NotificationEntry notificationEntry, NotificationEntry notificationEntry2);

        void logSkipAttachingKeepInParentChild(NotificationEntry notificationEntry, NotificationEntry notificationEntry2);
    }

    class NotificationViewState extends ExpandableViewState {
        private NotificationViewState() {
        }

        private void handleFixedTranslationZ(ExpandableNotificationRow expandableNotificationRow) {
            if (expandableNotificationRow.hasExpandingChild()) {
                setZTranslation(expandableNotificationRow.getTranslationZ());
                this.clipTopAmount = expandableNotificationRow.getClipTopAmount();
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public void animateTo(View view, AnimationProperties animationProperties) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.isExpandAnimationRunning()) {
                    return;
                }
                handleFixedTranslationZ(expandableNotificationRow);
                super.animateTo(view, animationProperties);
                expandableNotificationRow.startChildAnimation(animationProperties);
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public void applyToView(View view) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.isExpandAnimationRunning()) {
                    return;
                }
                handleFixedTranslationZ(expandableNotificationRow);
                super.applyToView(view);
                expandableNotificationRow.applyChildrenState();
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.ViewState
        protected void onYTranslationAnimationFinished(View view) {
            super.onYTranslationAnimationFinished(view);
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.isHeadsUpAnimatingAway()) {
                    expandableNotificationRow.setHeadsUpAnimatingAway(false);
                }
            }
        }
    }

    public interface OnDragSuccessListener {
        void onDragSuccess(NotificationEntry notificationEntry);
    }

    public interface OnExpandClickListener {
        void onExpandClicked(NotificationEntry notificationEntry, View view, boolean z);
    }

    public interface OnExpansionChangedListener {
        void onExpansionChanged(boolean z);
    }

    static {
        boolean z = Compile.IS_DEBUG;
        boolean z2 = false;
        DEBUG = z && Log.isLoggable("ExpandableNotifRow", 3);
        if (z && Log.isLoggable("ExpandableNotifRow", 2)) {
            z2 = true;
        }
        DEBUG_ONMEASURE = z2;
        RECENTLY_ALERTED_THRESHOLD_MS = TimeUnit.SECONDS.toMillis(30L);
        BASE_VALUE = SourceType.from("BaseValue");
        FROM_PARENT = SourceType.from("FromParent(ENR)");
        SLOW_MEASURE_SIMULATE_DELAY_MS = SystemProperties.getInt("persist.notifications.extra_measure_delay_ms", 150);
        TRANSLATE_CONTENT = new FloatProperty("translate") { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.2
            @Override // android.util.Property
            public Float get(ExpandableNotificationRow expandableNotificationRow) {
                return Float.valueOf(expandableNotificationRow.getTranslation());
            }

            @Override // android.util.FloatProperty
            public void setValue(ExpandableNotificationRow expandableNotificationRow, float f) {
                expandableNotificationRow.setTranslation(f);
            }
        };
    }

    public ExpandableNotificationRow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, context);
        throw new UnsupportedOperationException("Insecure constructor");
    }

    private ExpandableNotificationRow(Context context, AttributeSet attributeSet, Context context2) {
        super(context, attributeSet);
        this.mUpdateSelfBackgroundOnUpdate = true;
        this.mShowSnooze = false;
        this.mShowPublicExpander = true;
        this.mHeaderVisibleAmount = 1.0f;
        this.mLastChronometerRunning = true;
        this.mExpandClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                boolean z;
                if (!ExpandableNotificationRow.this.shouldShowPublic() && ((!ExpandableNotificationRow.this.mIsMinimized || ExpandableNotificationRow.this.isExpanded()) && ExpandableNotificationRow.this.mGroupMembershipManager.isGroupSummary(ExpandableNotificationRow.this.mEntry))) {
                    ExpandableNotificationRow.this.mGroupExpansionChanging = true;
                    boolean zIsGroupExpanded = ExpandableNotificationRow.this.mGroupExpansionManager.isGroupExpanded(ExpandableNotificationRow.this.mEntry);
                    ExpandableNotificationRow.this.mOnExpandClickListener.onExpandClicked(ExpandableNotificationRow.this.mEntry, view, ExpandableNotificationRow.this.mGroupExpansionManager.toggleGroupExpansion(ExpandableNotificationRow.this.mEntry));
                    ExpandableNotificationRow.this.onExpansionChanged(true, zIsGroupExpanded);
                    return;
                }
                if (ExpandableNotificationRow.this.mEnableNonGroupedNotificationExpand) {
                    if (view.isAccessibilityFocused()) {
                        ExpandableNotificationRow.this.mPrivateLayout.setFocusOnVisibilityChange();
                    }
                    if (ExpandableNotificationRow.this.isPinned()) {
                        z = !ExpandableNotificationRow.this.mExpandedWhenPinned;
                        ExpandableNotificationRow.this.mExpandedWhenPinned = z;
                        if (ExpandableNotificationRow.this.mExpansionChangedListener != null) {
                            ExpandableNotificationRow.this.mExpansionChangedListener.onExpansionChanged(z);
                        }
                    } else {
                        z = !ExpandableNotificationRow.this.isExpanded();
                        ExpandableNotificationRow.this.setUserExpanded(z);
                    }
                    ExpandableNotificationRow.this.notifyHeightChanged(true);
                    ExpandableNotificationRow.this.mOnExpandClickListener.onExpandClicked(ExpandableNotificationRow.this.mEntry, view, z);
                }
            }
        };
        this.mExpireRecentlyAlertedFlag = new Runnable() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$1();
            }
        };
        this.mImageResolver = new NotificationInlineImageResolver(context2, new NotificationInlineImageCache());
        this.mSmallRoundness = getResources().getDimension(R$dimen.notification_corner_radius_small) / getMaxRadius();
        initDimens();
    }

    public ExpandableNotificationRow(Context context, AttributeSet attributeSet, NotificationEntry notificationEntry) {
        this(context, attributeSet, userContextForEntry(context, notificationEntry));
    }

    private void animateShowingPublic(long j, long j2, boolean z) {
        View[] viewArr = this.mIsSummaryWithChildren ? new View[]{this.mChildrenContainer} : new View[]{this.mPrivateLayout};
        View[] viewArr2 = {this.mPublicLayout};
        View[] viewArr3 = z ? viewArr : viewArr2;
        if (z) {
            viewArr = viewArr2;
        }
        long j3 = (j2 / 10) / 2;
        long j4 = (j2 / 3) + j3;
        long j5 = (j2 - j4) + j3;
        for (final View view : viewArr3) {
            view.setVisibility(0);
            view.animate().cancel();
            view.animate().alpha(0.0f).setStartDelay(j).setDuration(j4).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$animateShowingPublic$3(view);
                }
            });
        }
        for (View view2 : viewArr) {
            view2.setVisibility(0);
            view2.setAlpha(0.0f);
            view2.animate().cancel();
            view2.animate().alpha(1.0f).setStartDelay((j + j2) - j5).setDuration(j5);
        }
    }

    private String appendTraceStyleTag(String str) {
        if (!Trace.isEnabled()) {
            return str;
        }
        return str + "(" + getEntry().getNotificationStyle() + ")";
    }

    private void applyAudiblyAlertedRecently(boolean z) {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.setRecentlyAudiblyAlerted(z);
        }
        this.mPrivateLayout.setRecentlyAudiblyAlerted(z);
        this.mPublicLayout.setRecentlyAudiblyAlerted(z);
    }

    private void applyChildrenRoundness() {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.requestRoundness(getTopRoundness(), getBottomRoundness(), FROM_PARENT, false);
        }
    }

    private boolean canEntryBeDismissed() {
        return this.mDismissibilityProvider.isDismissable(this.mEntry);
    }

    private boolean childrenRequireOverlappingRendering() {
        if (getEntry().getSbn().getNotification().isColorized()) {
            return true;
        }
        NotificationContentView showingLayout = getShowingLayout();
        return showingLayout != null && showingLayout.requireRowToHaveOverlappingRendering();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void delegateNotificationFaded(View view, boolean z) {
        if (view instanceof NotificationFadeAware) {
            ((NotificationFadeAware) view).setNotificationFaded(z);
        } else {
            NotificationFadeAware.setLayerTypeForFaded(view, z);
        }
    }

    private void doLongClickCallback() {
        doLongClickCallback(getWidth() / 2, getHeight() / 2);
    }

    private int getHeadsUpHeight() {
        return getShowingLayout().getHeadsUpHeight(false);
    }

    private int getPinnedHeadsUpHeight(boolean z) {
        return this.mIsSummaryWithChildren ? this.mChildrenContainer.getIntrinsicHeight() : this.mExpandedWhenPinned ? Math.max(getMaxExpandHeight(), getHeadsUpHeight()) : z ? Math.max(getCollapsedHeight(), getHeadsUpHeight()) : getHeadsUpHeight();
    }

    private void handleIntrinsicHeightReached() {
        if (this.mOnIntrinsicHeightReachedRunnable == null || getActualHeight() != getIntrinsicHeight()) {
            return;
        }
        this.mOnIntrinsicHeightReachedRunnable.run();
        this.mOnIntrinsicHeightReachedRunnable = null;
    }

    private void initDimens() {
        this.mMaxSmallHeightBeforeN = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_min_height_legacy);
        this.mMaxSmallHeightBeforeP = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_min_height_before_p);
        this.mMaxSmallHeightBeforeS = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_min_height_before_s);
        this.mMaxSmallHeight = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_min_height);
        this.mMaxSmallHeightLarge = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_min_height_increased);
        this.mMaxExpandedHeight = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_max_height);
        this.mMaxHeadsUpHeightBeforeN = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_max_heads_up_height_legacy);
        this.mMaxHeadsUpHeightBeforeP = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_max_heads_up_height_before_p);
        this.mMaxHeadsUpHeightBeforeS = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_max_heads_up_height_before_s);
        this.mMaxHeadsUpHeight = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_max_heads_up_height);
        this.mMaxHeadsUpHeightIncreased = NotificationUtils.getFontScaledHeight(((FrameLayout) this).mContext, R$dimen.notification_max_heads_up_height_increased);
        Resources resources = getResources();
        this.mEnableNonGroupedNotificationExpand = resources.getBoolean(R$bool.config_enableNonGroupedNotificationExpand);
        this.mShowGroupBackgroundWhenExpanded = resources.getBoolean(R$bool.config_showGroupNotificationBgWhenExpanded);
    }

    private boolean isBypassEnabled() {
        return false;
    }

    private boolean isConversation() {
        return this.mPeopleNotificationIdentifier.getPeopleNotificationType(this.mEntry) != 0;
    }

    private boolean isDozing() {
        return false;
    }

    private boolean isSystemChildExpanded() {
        return this.mIsSystemChildExpanded;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$animateShowingPublic$3(View view) {
        view.setVisibility(4);
        resetAllContentAlphas();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        applyAudiblyAlertedRecently(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$2(ViewStub viewStub, View view) {
        NotificationChildrenContainer notificationChildrenContainer = (NotificationChildrenContainer) view;
        this.mChildrenContainer = notificationChildrenContainer;
        notificationChildrenContainer.setIsMinimized(this.mIsMinimized);
        this.mChildrenContainer.setContainingNotification(this);
        this.mChildrenContainer.onNotificationUpdated();
        this.mChildrenContainer.setLogger(this.mChildrenContainerLogger);
        this.mTranslateableViews.add(this.mChildrenContainer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showContextPopupMenu$5(View view) {
        performDismiss(false);
        this.mTooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
    }

    private void logAddTransientRow(ExpandableNotificationRow expandableNotificationRow, int i) {
        ExpandableNotificationRowLogger expandableNotificationRowLogger = this.mLogger;
        if (expandableNotificationRowLogger == null) {
            return;
        }
        expandableNotificationRowLogger.logAddTransientRow(expandableNotificationRow.getEntry(), getEntry(), i);
    }

    private void logKeepInParentChildDetached(ExpandableNotificationRow expandableNotificationRow) {
        ExpandableNotificationRowLogger expandableNotificationRowLogger = this.mLogger;
        if (expandableNotificationRowLogger != null) {
            expandableNotificationRowLogger.logKeepInParentChildDetached(expandableNotificationRow.getEntry(), getEntry());
        }
    }

    private void logRemoveTransientRow(ExpandableNotificationRow expandableNotificationRow) {
        ExpandableNotificationRowLogger expandableNotificationRowLogger = this.mLogger;
        if (expandableNotificationRowLogger == null) {
            return;
        }
        expandableNotificationRowLogger.logRemoveTransientRow(expandableNotificationRow.getEntry(), getEntry());
    }

    private void logSkipAttachingKeepInParentChild(ExpandableNotificationRow expandableNotificationRow) {
        ExpandableNotificationRowLogger expandableNotificationRowLogger = this.mLogger;
        if (expandableNotificationRowLogger != null) {
            expandableNotificationRowLogger.logSkipAttachingKeepInParentChild(expandableNotificationRow.getEntry(), getEntry());
        }
    }

    private void onAttachedChildrenCountChanged() {
        NotificationViewWrapper notificationViewWrapper;
        boolean z = this.mIsSummaryWithChildren;
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        boolean z2 = notificationChildrenContainer != null && notificationChildrenContainer.getNotificationChildCount() > 0;
        this.mIsSummaryWithChildren = z2;
        if (z2) {
            Trace.beginSection("ExpNotRow#onChildCountChanged (summary)");
            if (!AsyncGroupHeaderViewInflation.isEnabled() && ((notificationViewWrapper = this.mChildrenContainer.getNotificationViewWrapper()) == null || notificationViewWrapper.getNotificationHeader() == null)) {
                this.mChildrenContainer.recreateNotificationHeader(this.mExpandClickListener, isConversation());
            }
        }
        if (!this.mIsSummaryWithChildren && z) {
            this.mPublicLayout.setNotificationWhen(this.mEntry.getSbn().getNotification().when);
        }
        getShowingLayout().updateBackgroundColor(false);
        this.mPrivateLayout.updateExpandButtons(isExpandable());
        updateChildrenAppearance();
        updateChildrenVisibility();
        applyChildrenRoundness();
        if (this.mIsSummaryWithChildren) {
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onExpansionChanged(boolean z, boolean z2) {
        boolean zIsExpanded = isExpanded();
        if (this.mIsSummaryWithChildren && (!this.mIsMinimized || z2)) {
            zIsExpanded = this.mGroupExpansionManager.isGroupExpanded(this.mEntry);
        }
        if (zIsExpanded != z2) {
            updateShelfIconColor();
            ExpandableNotificationRowLogger expandableNotificationRowLogger = this.mLogger;
            if (expandableNotificationRowLogger != null) {
                expandableNotificationRowLogger.logNotificationExpansion(this.mLoggingKey, getViewState().location, z, zIsExpanded);
            }
            if (this.mIsSummaryWithChildren) {
                this.mChildrenContainer.onExpansionChanged();
            }
            OnExpansionChangedListener onExpansionChangedListener = this.mExpansionChangedListener;
            if (onExpansionChangedListener != null) {
                onExpansionChangedListener.onExpansionChanged(zIsExpanded);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onGenericMotion(View view, MotionEvent motionEvent) {
        if (motionEvent.getToolType(0) != 3 || motionEvent.getAction() != 11 || motionEvent.getButtonState() != 2) {
            return false;
        }
        showContextPopupMenu((int) motionEvent.getX(), (int) motionEvent.getY());
        return true;
    }

    private void reInflateViews() {
        Trace.beginSection("ExpandableNotificationRow#reInflateViews");
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.reInflateViews(this.mExpandClickListener, this.mEntry.getSbn());
        }
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.reinflate();
            notificationContentView.reInflateViews();
        }
        this.mEntry.getSbn().clearPackageContext();
        ((RowContentBindParams) this.mRowContentBindStage.getStageParams(this.mEntry)).setNeedsReinflation(true);
        this.mRowContentBindStage.requestRebind(this.mEntry, null);
        Trace.endSection();
    }

    private void setChildIsExpanding(boolean z) {
        this.mChildIsExpanding = z;
        updateClipping();
        invalidate();
    }

    private void setChronometerRunning(boolean z, NotificationContentView notificationContentView) {
        if (notificationContentView != null) {
            boolean z2 = z || isPinned();
            View contractedChild = notificationContentView.getContractedChild();
            View expandedChild = notificationContentView.getExpandedChild();
            View headsUpChild = notificationContentView.getHeadsUpChild();
            setChronometerRunningForChild(z2, contractedChild);
            setChronometerRunningForChild(z2, expandedChild);
            setChronometerRunningForChild(z2, headsUpChild);
        }
    }

    private void setChronometerRunningForChild(boolean z, View view) {
        if (view != null) {
            View viewFindViewById = view.findViewById(R.id.clipBounds);
            if (viewFindViewById instanceof Chronometer) {
                ((Chronometer) viewFindViewById).setStarted(z);
            }
        }
    }

    private void setIconAnimationRunning(boolean z, NotificationContentView notificationContentView) {
        if (notificationContentView != null) {
            View contractedChild = notificationContentView.getContractedChild();
            View expandedChild = notificationContentView.getExpandedChild();
            View headsUpChild = notificationContentView.getHeadsUpChild();
            setIconAnimationRunningForChild(z, contractedChild);
            setIconAnimationRunningForChild(z, expandedChild);
            setIconAnimationRunningForChild(z, headsUpChild);
        }
    }

    private void setIconAnimationRunningForChild(boolean z, View view) {
        if (view != null) {
            setImageViewAnimationRunning((ImageView) view.findViewById(R.id.icon), z);
            setImageViewAnimationRunning((ImageView) view.findViewById(R.id.scrollView), z);
        }
    }

    private void setImageViewAnimationRunning(ImageView imageView, boolean z) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
                if (z) {
                    animationDrawable.start();
                    return;
                } else {
                    animationDrawable.stop();
                    return;
                }
            }
            if (drawable instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
                if (z) {
                    animatedVectorDrawable.start();
                } else {
                    animatedVectorDrawable.stop();
                }
            }
        }
    }

    private void setNotificationFadedOnChildren(boolean z) {
        delegateNotificationFaded(this.mChildrenContainer, z);
        for (NotificationContentView notificationContentView : this.mLayouts) {
            delegateNotificationFaded(notificationContentView, z);
        }
    }

    private void setTargetPoint(Point point) {
        this.mTargetPoint = point;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldShowPublic() {
        return this.mSensitive && this.mHideSensitiveForIntrinsicHeight;
    }

    private static boolean shouldSimulateSlowMeasure() {
        return false;
    }

    private void showContextPopupMenu(int i, int i2) {
        boolean zCanViewBeDismissed = canViewBeDismissed();
        if (DEBUG) {
            Log.d("ExpandableNotifRow", "showContextPopupMenu canBeDismissed: " + zCanViewBeDismissed);
        }
        if (zCanViewBeDismissed) {
            this.mTooltipPopupShowingId = this.mTooltipPopupManager.show(5, this, i, i2, getContext().getText(R$string.remove_notification), new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showContextPopupMenu$5(view);
                }
            });
        }
    }

    private void simulateExtraMeasureDelay() {
        try {
            try {
                Trace.beginSection("ExtraDebugMeasureDelay");
                Thread.sleep(SLOW_MEASURE_SIMULATE_DELAY_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } finally {
            Trace.endSection();
        }
    }

    private void updateBackgroundColorsOfSelf() {
        super.updateBackgroundColors();
        if (this.mColorUpdateLogger.isEnabled()) {
            this.mColorUpdateLogger.logNotificationEvent("ENR.updateBackgroundColorsOfSelf()", this.mLoggingKey, "normalBgColor=" + ColorUtilKt.hexColorString(Integer.valueOf(getNormalBgColor())) + " background=" + this.mBackgroundNormal.toDumpString());
        }
    }

    private void updateBaseRoundness() {
        if (isChildInGroup()) {
            requestRoundnessReset(BASE_VALUE);
        } else {
            float f = this.mSmallRoundness;
            requestRoundness(f, f, BASE_VALUE);
        }
    }

    private void updateChildrenVisibility() {
        int i = 4;
        this.mPrivateLayout.setVisibility((this.mShowingPublic || this.mIsSummaryWithChildren) ? 4 : 0);
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            if (!this.mShowingPublic && this.mIsSummaryWithChildren) {
                i = 0;
            }
            notificationChildrenContainer.setVisibility(i);
        }
        updateLimits();
    }

    private void updateClickAndFocus() {
        boolean z = !isChildInGroup() || isGroupExpanded();
        boolean z2 = this.mOnClickListener != null && z;
        if (isFocusable() != z) {
            setFocusable(z);
        }
        if (isClickable() != z2) {
            setClickable(z2);
        }
    }

    private void updateContentShiftHeight() {
        NotificationViewWrapper visibleNotificationViewWrapper = getVisibleNotificationViewWrapper();
        View icon = visibleNotificationViewWrapper == null ? null : visibleNotificationViewWrapper.getIcon();
        if (icon != null) {
            this.mIconTransformContentShift = getRelativeTopPadding(icon) + icon.getHeight();
        } else {
            this.mIconTransformContentShift = this.mContentShift;
        }
    }

    private void updateLimits() {
        for (NotificationContentView notificationContentView : this.mLayouts) {
            updateLimitsForView(notificationContentView);
        }
    }

    private void updateLimitsForView(NotificationContentView notificationContentView) {
        View contractedChild = notificationContentView.getContractedChild();
        boolean z = (contractedChild == null || contractedChild.getId() == 16909670) ? false : true;
        int i = this.mEntry.targetSdk;
        boolean z2 = i < 24;
        boolean z3 = i < 28;
        boolean z4 = i < 31;
        int i2 = (z && z4 && !this.mIsSummaryWithChildren) ? z2 ? this.mMaxSmallHeightBeforeN : z3 ? this.mMaxSmallHeightBeforeP : this.mMaxSmallHeightBeforeS : contractedChild instanceof CallLayout ? this.mMaxExpandedHeight : (this.mUseIncreasedCollapsedHeight && notificationContentView == this.mPrivateLayout) ? this.mMaxSmallHeightLarge : this.mMaxSmallHeight;
        int iMax = (notificationContentView.getHeadsUpChild() == null || notificationContentView.getHeadsUpChild().getId() == 16909670 || !z4) ? (this.mUseIncreasedHeadsUpHeight && notificationContentView == this.mPrivateLayout) ? this.mMaxHeadsUpHeightIncreased : this.mMaxHeadsUpHeight : z2 ? this.mMaxHeadsUpHeightBeforeN : z3 ? this.mMaxHeadsUpHeightBeforeP : this.mMaxHeadsUpHeightBeforeS;
        NotificationViewWrapper visibleWrapper = notificationContentView.getVisibleWrapper(2);
        if (visibleWrapper != null) {
            iMax = Math.max(iMax, visibleWrapper.getMinLayoutHeight());
        }
        notificationContentView.setHeights(i2, iMax, this.mMaxExpandedHeight);
    }

    private static Context userContextForEntry(Context context, NotificationEntry notificationEntry) {
        return context.getUserId() == notificationEntry.getSbn().getNormalizedUserId() ? context : context.createContextAsUser(UserHandle.of(notificationEntry.getSbn().getNormalizedUserId()), 0);
    }

    public void addChildNotification(ExpandableNotificationRow expandableNotificationRow, int i) {
        if (this.mChildrenContainer == null) {
            this.mChildrenContainerStub.inflate();
        }
        if (expandableNotificationRow.keepInParentForDismissAnimation()) {
            logSkipAttachingKeepInParentChild(expandableNotificationRow);
            return;
        }
        this.mChildrenContainer.addNotification(expandableNotificationRow, i);
        onAttachedChildrenCountChanged();
        expandableNotificationRow.setIsChildInGroup(true, this);
    }

    public void addTransientView(View view, int i) {
        if (view instanceof ExpandableNotificationRow) {
            logAddTransientRow((ExpandableNotificationRow) view, i);
        }
        super.addTransientView(view, i);
    }

    public void animateResetTranslation() {
        Animator animator = this.mTranslateAnim;
        if (animator != null) {
            animator.cancel();
        }
        Animator translateViewAnimator = getTranslateViewAnimator(0.0f, null);
        this.mTranslateAnim = translateViewAnimator;
        if (translateViewAnimator != null) {
            translateViewAnimator.start();
        }
    }

    public void applyChildrenState() {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.applyState();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    protected void applyContentTransformation(float f, float f2) {
        super.applyContentTransformation(f, f2);
        if (!this.mIsLastChild) {
            f = 1.0f;
        }
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.setAlpha(f);
            notificationContentView.setTranslationY(f2);
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setAlpha(f);
            this.mChildrenContainer.setTranslationY(f2);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.Roundable
    public void applyRoundnessAndInvalidate() {
        applyChildrenRoundness();
        super.applyRoundnessAndInvalidate();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean areChildrenExpanded() {
        return this.mChildrenExpanded;
    }

    public boolean canShowHeadsUp() {
        if (!this.mOnKeyguard || isDozing() || isBypassEnabled()) {
            return true;
        }
        if (this.mEntry.isStickyAndNotDemoted()) {
            return this.mIgnoreLockscreenConstraints || !this.mSaveSpaceOnLockscreen;
        }
        return false;
    }

    public boolean canViewBeCleared() {
        if (this.mEntry.isClearable()) {
            return (shouldShowPublic() && this.mSensitiveHiddenInGeneral) ? false : true;
        }
        return false;
    }

    public boolean canViewBeDismissed() {
        if (canEntryBeDismissed()) {
            return (shouldShowPublic() && this.mSensitiveHiddenInGeneral) ? false : true;
        }
        return false;
    }

    public void cancelTranslateAnimation() {
        Animator animator = this.mTranslateAnim;
        if (animator != null) {
            animator.cancel();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    protected boolean childNeedsClipping(View view) {
        if (view instanceof NotificationContentView) {
            NotificationContentView notificationContentView = (NotificationContentView) view;
            if (isClippingNeeded()) {
                return true;
            }
            if (hasRoundedCorner()) {
                if (notificationContentView.shouldClipToRounding(getTopRoundness() != 0.0f, getBottomRoundness() != 0.0f)) {
                    return true;
                }
            }
        } else if (view == this.mChildrenContainer && (isClippingNeeded() || hasRoundedCorner())) {
            return true;
        }
        return super.childNeedsClipping(view);
    }

    public void closeRemoteInput() {
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.closeRemoteInput();
        }
    }

    public void collectVisibleLocations(Map map) {
        if (getVisibility() == 0) {
            map.put(getEntry().getKey(), Integer.valueOf(getViewState().location));
            NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
            if (notificationChildrenContainer != null) {
                List attachedChildren = notificationChildrenContainer.getAttachedChildren();
                for (int i = 0; i < attachedChildren.size(); i++) {
                    ((ExpandableNotificationRow) attachedChildren.get(i)).collectVisibleLocations(map);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public ExpandableViewState createExpandableViewState() {
        return new NotificationViewState();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public void dismiss(boolean z) {
        List attachedChildren;
        int iIndexOf;
        super.dismiss(z);
        setDragController(null);
        this.mGroupParentWhenDismissed = this.mNotificationParent;
        this.mChildAfterViewWhenDismissed = null;
        this.mEntry.getIcons().getStatusBarIcon().setDismissed();
        if (!isChildInGroup() || (iIndexOf = (attachedChildren = this.mNotificationParent.getAttachedChildren()).indexOf(this)) == -1 || iIndexOf >= attachedChildren.size() - 1) {
            return;
        }
        this.mChildAfterViewWhenDismissed = (View) attachedChildren.get(iIndexOf + 1);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        Path path = this.mExpandingClipPath;
        if (path != null && (this.mExpandAnimationRunning || this.mChildIsExpanding)) {
            canvas.clipPath(path);
        }
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public void doDragCallback(float f, float f2) {
        if (this.mDragController != null) {
            setTargetPoint(new Point((int) f, (int) f2));
            this.mDragController.startDragAndDrop(this);
        }
    }

    public void doLongClickCallback(int i, int i2) {
    }

    public void doSmartActionClick(int i, int i2, int i3) {
    }

    public void dragAndDropSuccess() {
        OnDragSuccessListener onDragSuccessListener = this.mOnDragSuccessListener;
        if (onDragSuccessListener != null) {
            onDragSuccessListener.onDragSuccess(getEntry());
        }
    }

    public List getAttachedChildren() {
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer == null) {
            return null;
        }
        return notificationChildrenContainer.getAttachedChildren();
    }

    public BigPictureIconManager getBigPictureIconManager() {
        return this.mBigPictureIconManager;
    }

    public View getChildAfterViewWhenDismissed() {
        return this.mChildAfterViewWhenDismissed;
    }

    public ExpandableNotificationRow getChildNotificationAt(int i) {
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer == null || notificationChildrenContainer.getAttachedChildren().size() <= i) {
            return null;
        }
        return (ExpandableNotificationRow) this.mChildrenContainer.getAttachedChildren().get(i);
    }

    public NotificationChildrenContainer getChildrenContainer() {
        return this.mChildrenContainer;
    }

    public NotificationChildrenContainer getChildrenContainerNonNull() {
        if (this.mChildrenContainer == null) {
            this.mChildrenContainerStub.inflate();
        }
        return this.mChildrenContainer;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public int getCollapsedHeight() {
        return (!this.mIsSummaryWithChildren || shouldShowPublic()) ? getMinHeight() : this.mChildrenContainer.getCollapsedHeight();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    protected float getContentTransformationShift() {
        return this.mIconTransformContentShift;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    protected View getContentView() {
        return (!this.mIsSummaryWithChildren || shouldShowPublic()) ? getShowingLayout() : this.mChildrenContainer;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    public Path getCustomClipPath(View view) {
        return super.getCustomClipPath(view);
    }

    public NotificationEntry getEntry() {
        return this.mEntry;
    }

    public InflatedSmartReplyState getExistingSmartReplyState() {
        return this.mPrivateLayout.getCurrentSmartReplyState();
    }

    public View getGroupParentWhenDismissed() {
        return this.mGroupParentWhenDismissed;
    }

    public float getHeaderVisibleAmount() {
        return this.mHeaderVisibleAmount;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public int getHeightWithoutLockscreenConstraints() {
        this.mIgnoreLockscreenConstraints = true;
        int intrinsicHeight = getIntrinsicHeight();
        this.mIgnoreLockscreenConstraints = false;
        return intrinsicHeight;
    }

    NotificationInlineImageResolver getImageResolver() {
        return this.mImageResolver;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public int getIntrinsicHeight() {
        return isUserLocked() ? getActualHeight() : (!isChildInGroup() || isGroupExpanded()) ? (this.mSensitive && this.mHideSensitiveForIntrinsicHeight) ? getMinHeight() : this.mIsSummaryWithChildren ? this.mChildrenContainer.getIntrinsicHeight() : (canShowHeadsUp() && isHeadsUpState()) ? (isPinned() || this.mHeadsupDisappearRunning) ? getPinnedHeadsUpHeight(true) : isExpanded() ? Math.max(getMaxExpandHeight(), getHeadsUpHeight()) : Math.max(getCollapsedHeight(), getHeadsUpHeight()) : isExpanded() ? getMaxExpandHeight() : getCollapsedHeight() : this.mPrivateLayout.getMinHeight();
    }

    public NotificationContentView[] getLayouts() {
        NotificationContentView[] notificationContentViewArr = this.mLayouts;
        return (NotificationContentView[]) Arrays.copyOf(notificationContentViewArr, notificationContentViewArr.length);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public int getMaxContentHeight() {
        return (!this.mIsSummaryWithChildren || shouldShowPublic()) ? getShowingLayout().getMaxHeight() : this.mChildrenContainer.getMaxContentHeight();
    }

    public int getMaxExpandHeight() {
        return this.mPrivateLayout.getExpandHeight();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public int getMinHeight(boolean z) {
        return (!z && canShowHeadsUp() && this.mIsHeadsUp && this.mHeadsUpManager.isTrackingHeadsUp()) ? getPinnedHeadsUpHeight(false) : (!this.mIsSummaryWithChildren || isGroupExpanded() || shouldShowPublic()) ? (!z && canShowHeadsUp() && this.mIsHeadsUp) ? getHeadsUpHeight() : getShowingLayout().getMinHeight() : this.mChildrenContainer.getMinHeight();
    }

    public ExpandableNotificationRow getNotificationParent() {
        return this.mNotificationParent;
    }

    public NotificationViewWrapper getNotificationViewWrapper() {
        return this.mIsSummaryWithChildren ? this.mChildrenContainer.getNotificationViewWrapper() : this.mPrivateLayout.getNotificationViewWrapper();
    }

    public int getOriginalIconColor() {
        if (this.mIsSummaryWithChildren && !shouldShowPublic() && !AsyncGroupHeaderViewInflation.isEnabled()) {
            return this.mChildrenContainer.getVisibleWrapper().getOriginalIconColor();
        }
        int originalIconColor = getShowingLayout().getOriginalIconColor();
        if (originalIconColor != 1) {
            return originalIconColor;
        }
        return this.mEntry.getContrastedColor(((FrameLayout) this).mContext, this.mIsMinimized && !isExpanded(), getBackgroundColorWithoutTint());
    }

    public int getPinnedHeadsUpHeight() {
        return getPinnedHeadsUpHeight(true);
    }

    public int getPositionOfChild(ExpandableNotificationRow expandableNotificationRow) {
        if (this.mIsSummaryWithChildren) {
            return this.mChildrenContainer.getPositionInLinearLayout(expandableNotificationRow);
        }
        return 0;
    }

    public NotificationContentView getPrivateLayout() {
        return this.mPrivateLayout;
    }

    public NotificationContentView getPublicLayout() {
        return this.mPublicLayout;
    }

    public NotificationContentView getShowingLayout() {
        return shouldShowPublic() ? this.mPublicLayout : this.mPrivateLayout;
    }

    public HybridNotificationView getSingleLineView() {
        return this.mPrivateLayout.getSingleLineView();
    }

    public Animator getTranslateViewAnimator(float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        Animator animator = this.mTranslateAnim;
        if (animator != null) {
            animator.cancel();
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, TRANSLATE_CONTENT, f);
        if (animatorUpdateListener != null) {
            objectAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
        }
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.3
            boolean cancelled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator2) {
                this.cancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                ExpandableNotificationRow.this.mTranslateAnim = null;
            }
        });
        this.mTranslateAnim = objectAnimatorOfFloat;
        return objectAnimatorOfFloat;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.statusbar.notification.stack.SwipeableView
    public float getTranslation() {
        if (this.mDismissUsingRowTranslationX) {
            return getTranslationX();
        }
        ArrayList arrayList = this.mTranslateableViews;
        if (arrayList == null || arrayList.size() <= 0) {
            return 0.0f;
        }
        return ((View) this.mTranslateableViews.get(0)).getTranslationX();
    }

    public float getTranslationWhenRemoved() {
        return this.mTranslationWhenRemoved;
    }

    public ExpandableNotificationRow getViewAtPosition(float f) {
        ExpandableNotificationRow viewAtPosition;
        return (this.mIsSummaryWithChildren && this.mChildrenExpanded && (viewAtPosition = this.mChildrenContainer.getViewAtPosition(f)) != null) ? viewAtPosition : this;
    }

    public NotificationViewWrapper getVisibleNotificationViewWrapper() {
        return (!this.mIsSummaryWithChildren || shouldShowPublic()) ? getShowingLayout().getVisibleWrapper() : this.mChildrenContainer.getVisibleWrapper();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean hasExpandingChild() {
        return this.mChildIsExpanding;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public boolean hasOverlappingRendering() {
        return super.hasOverlappingRendering() && childrenRequireOverlappingRendering();
    }

    public boolean hasUserChangedExpansion() {
        return this.mHasUserChangedExpansion;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    protected boolean hideBackground() {
        return this.mShowNoBackground || super.hideBackground();
    }

    public void initialize(NotificationEntry notificationEntry, RemoteInputViewSubcomponent.Factory factory, String str, String str2, ExpandableNotificationRowLogger expandableNotificationRowLogger, GroupMembershipManager groupMembershipManager, GroupExpansionManager groupExpansionManager, HeadsUpManager headsUpManager, RowContentBindStage rowContentBindStage, OnExpandClickListener onExpandClickListener, PeopleNotificationIdentifier peopleNotificationIdentifier, OnUserInteractionCallback onUserInteractionCallback, NotificationDismissibilityProvider notificationDismissibilityProvider, NotificationChildrenContainerLogger notificationChildrenContainerLogger, ColorUpdateLogger colorUpdateLogger, SmartReplyConstants smartReplyConstants, SmartReplyController smartReplyController, FeatureFlags featureFlags, IStatusBarService iStatusBarService) {
        this.mEntry = notificationEntry;
        this.mAppName = str;
        this.mLogger = expandableNotificationRowLogger;
        this.mLoggingKey = str2;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mGroupExpansionManager = groupExpansionManager;
        this.mPrivateLayout.setGroupMembershipManager(groupMembershipManager);
        this.mHeadsUpManager = headsUpManager;
        this.mRowContentBindStage = rowContentBindStage;
        this.mOnExpandClickListener = onExpandClickListener;
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.initialize(this.mPeopleNotificationIdentifier, factory, smartReplyConstants, smartReplyController, iStatusBarService);
        }
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mChildrenContainerLogger = notificationChildrenContainerLogger;
        this.mColorUpdateLogger = colorUpdateLogger;
        this.mDismissibilityProvider = notificationDismissibilityProvider;
        this.mFeatureFlags = featureFlags;
    }

    public boolean isAboveShelf() {
        if (!canShowHeadsUp()) {
            return false;
        }
        if (this.mIsPinned || this.mHeadsupDisappearRunning) {
            return true;
        }
        return (this.mIsHeadsUp && this.mAboveShelf) || this.mExpandAnimationRunning || this.mChildIsExpanding;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isChildInGroup() {
        return this.mNotificationParent != null;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isExpandAnimationRunning() {
        return this.mExpandAnimationRunning;
    }

    public boolean isExpandable() {
        return (!this.mIsSummaryWithChildren || shouldShowPublic()) ? this.mEnableNonGroupedNotificationExpand && this.mExpandable : !this.mChildrenExpanded;
    }

    public boolean isExpanded() {
        return isExpanded(false);
    }

    public boolean isExpanded(boolean z) {
        if (shouldShowPublic()) {
            return false;
        }
        if (!this.mOnKeyguard || z) {
            return (!hasUserChangedExpansion() && (isSystemExpanded() || isSystemChildExpanded())) || isUserExpanded();
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isGroupExpanded() {
        return this.mGroupExpansionManager.isGroupExpanded(this.mEntry);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isGroupExpansionChanging() {
        return isChildInGroup() ? this.mNotificationParent.isGroupExpansionChanging() : this.mGroupExpansionChanging;
    }

    public boolean isHeadsUp() {
        return this.mIsHeadsUp;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isHeadsUpAnimatingAway() {
        return this.mHeadsupDisappearRunning;
    }

    public boolean isHeadsUpState() {
        return this.mIsHeadsUp || this.mHeadsupDisappearRunning;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationFadeAware.FadeOptimizedNotification
    public boolean isNotificationFaded() {
        return this.mIsFaded;
    }

    public boolean isNotificationRowLongClickable() {
        return false;
    }

    public boolean isOnKeyguard() {
        return this.mOnKeyguard;
    }

    public boolean isParentDismissed() {
        return getEntry().getDismissState() == NotificationEntry.DismissState.PARENT_DISMISSED;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isPinned() {
        return this.mIsPinned;
    }

    public boolean isPinnedAndExpanded() {
        if (isPinned()) {
            return this.mExpandedWhenPinned;
        }
        return false;
    }

    public boolean isRemoved() {
        return this.mRemoved;
    }

    @Override // android.view.View
    public boolean isSoundEffectsEnabled() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean isSummaryWithChildren() {
        return this.mIsSummaryWithChildren;
    }

    public boolean isSystemExpanded() {
        return this.mIsSystemExpanded;
    }

    public boolean isUserExpanded() {
        return this.mUserExpanded;
    }

    public boolean isUserLocked() {
        return this.mUserLocked;
    }

    public boolean keepInParentForDismissAnimation() {
        return this.mKeepInParentForDismissAnimation;
    }

    public void logRemoveFromTransientContainer(ViewGroup viewGroup) {
        ExpandableNotificationRowLogger expandableNotificationRowLogger = this.mLogger;
        if (expandableNotificationRowLogger == null) {
            return;
        }
        if (viewGroup instanceof NotificationChildrenContainer) {
            expandableNotificationRowLogger.logRemoveTransientFromContainer(getEntry(), ((NotificationChildrenContainer) viewGroup).getContainingNotification().getEntry());
        } else if (viewGroup instanceof NotificationStackScrollLayout) {
            expandableNotificationRowLogger.logRemoveTransientFromNssl(getEntry());
        } else {
            expandableNotificationRowLogger.logRemoveTransientFromViewGroup(getEntry(), viewGroup);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean mustStayOnScreen() {
        return this.mIsHeadsUp && this.mMustStayOnScreen;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void notifyHeightChanged(boolean z) {
        super.notifyHeightChanged(z);
        getShowingLayout().requestSelectLayout(z || isUserLocked());
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    protected void onAppearAnimationFinished(boolean z) {
        super.onAppearAnimationFinished(z);
        if (!z) {
            setHeadsUpAnimatingAway(false);
        } else {
            resetAllContentAlphas();
            setNotificationFaded(false);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateBaseRoundness();
        this.mTooltipPopupManager = (QsNotificationTooltipPopupManager) Dependency.getDisplay(this, QsNotificationTooltipPopupManager.class);
        setOnGenericMotionListener(new View.OnGenericMotionListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda2
            @Override // android.view.View.OnGenericMotionListener
            public final boolean onGenericMotion(View view, MotionEvent motionEvent) {
                return this.f$0.onGenericMotion(view, motionEvent);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        NotificationInlineImageResolver notificationInlineImageResolver = this.mImageResolver;
        if (notificationInlineImageResolver != null) {
            notificationInlineImageResolver.updateMaxImageSizes();
        }
        BigPictureIconManager bigPictureIconManager = this.mBigPictureIconManager;
        if (bigPictureIconManager != null) {
            bigPictureIconManager.updateMaxImageSizes();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    public void onDensityOrFontScaleChanged() {
        super.onDensityOrFontScaleChanged();
        initDimens();
        initBackground();
        reInflateViews();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        QsNotificationTooltipPopupManager qsNotificationTooltipPopupManager = this.mTooltipPopupManager;
        if (qsNotificationTooltipPopupManager != null) {
            qsNotificationTooltipPopupManager.hide(this.mTooltipPopupShowingId, true);
        }
    }

    public void onExpandedByGesture(boolean z) {
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPublicLayout = (NotificationContentView) findViewById(R$id.expandedPublic);
        NotificationContentView notificationContentView = (NotificationContentView) findViewById(R$id.expanded);
        this.mPrivateLayout = notificationContentView;
        NotificationContentView[] notificationContentViewArr = {notificationContentView, this.mPublicLayout};
        this.mLayouts = notificationContentViewArr;
        for (NotificationContentView notificationContentView2 : notificationContentViewArr) {
            notificationContentView2.setExpandClickListener(this.mExpandClickListener);
            notificationContentView2.setContainingNotification(this);
        }
        ViewStub viewStub = (ViewStub) findViewById(R$id.child_container_stub);
        this.mChildrenContainerStub = viewStub;
        viewStub.setOnInflateListener(new ViewStub.OnInflateListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$$ExternalSyntheticLambda1
            @Override // android.view.ViewStub.OnInflateListener
            public final void onInflate(ViewStub viewStub2, View view) {
                this.f$0.lambda$onFinishInflate$2(viewStub2, view);
            }
        });
        this.mTranslateableViews = new ArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            this.mTranslateableViews.add(getChildAt(i));
        }
        this.mTranslateableViews.remove(this.mChildrenContainerStub);
        setDefaultFocusHighlightEnabled(false);
    }

    public void onFinishedExpansionChange() {
        this.mGroupExpansionChanging = false;
        updateBackgroundForGroupState();
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        boolean zIsNotificationRowLongClickable = isNotificationRowLongClickable();
        if (zIsNotificationRowLongClickable) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
        }
        accessibilityNodeInfo.setLongClickable(zIsNotificationRowLongClickable);
        if (canViewBeDismissed() && !this.mIsSnoozed) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
        }
        boolean zShouldShowPublic = shouldShowPublic();
        if (!zShouldShowPublic) {
            if (this.mIsSummaryWithChildren) {
                zIsExpanded = (!this.mIsMinimized || isExpanded()) ? isGroupExpanded() : false;
                zShouldShowPublic = true;
            } else {
                zShouldShowPublic = this.mPrivateLayout.isContentExpandable();
                zIsExpanded = isExpanded();
            }
        }
        if (!zShouldShowPublic || this.mIsSnoozed) {
            return;
        }
        if (zIsExpanded) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        } else {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!KeyEvent.isConfirmKey(i)) {
            return super.onKeyDown(i, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        if (!KeyEvent.isConfirmKey(i)) {
            return false;
        }
        doLongClickCallback();
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!KeyEvent.isConfirmKey(i)) {
            return super.onKeyUp(i, keyEvent);
        }
        if (keyEvent.isCanceled()) {
            return true;
        }
        performClick();
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Trace.beginSection(appendTraceStyleTag("ExpNotRow#onLayout"));
        int intrinsicHeight = getIntrinsicHeight();
        super.onLayout(z, i, i2, i3, i4);
        if (intrinsicHeight != getIntrinsicHeight() && (intrinsicHeight != 0 || getActualHeight() > 0)) {
            notifyHeightChanged(true);
        }
        updateContentShiftHeight();
        Trace.endSection();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        Trace.beginSection(appendTraceStyleTag("ExpNotRow#onMeasure"));
        if (DEBUG_ONMEASURE) {
            Log.d("ExpandableNotifRow", "onMeasure(widthMeasureSpec=" + View.MeasureSpec.toString(i) + ", heightMeasureSpec=" + View.MeasureSpec.toString(i2) + ")");
        }
        super.onMeasure(i, i2);
        if (shouldSimulateSlowMeasure()) {
            simulateExtraMeasureDelay();
        }
        Trace.endSection();
    }

    public void onNotificationUpdated() {
        if (this.mIsSummaryWithChildren) {
            Trace.beginSection("ExpNotRow#onNotifUpdated (summary)");
        } else {
            Trace.beginSection("ExpNotRow#onNotifUpdated (leaf)");
        }
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.onNotificationUpdated(this.mEntry);
        }
        this.mShowingPublicInitialized = false;
        updateNotificationColor();
        if (this.mIsSummaryWithChildren) {
            if (!AsyncGroupHeaderViewInflation.isEnabled()) {
                this.mChildrenContainer.recreateNotificationHeader(this.mExpandClickListener, isConversation());
            }
            this.mChildrenContainer.onNotificationUpdated();
        }
        if (this.mAnimationRunning) {
            setAnimationRunning(true);
        }
        if (this.mLastChronometerRunning) {
            setChronometerRunning(true);
        }
        ExpandableNotificationRow expandableNotificationRow = this.mNotificationParent;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.updateChildrenAppearance();
        }
        onAttachedChildrenCountChanged();
        this.mPublicLayout.updateExpandButtons(this.mShowPublicExpander);
        updateLimits();
        updateShelfIconColor();
        if (this.mUpdateSelfBackgroundOnUpdate) {
            this.mUpdateSelfBackgroundOnUpdate = false;
            updateBackgroundColorsOfSelf();
        }
        Trace.endSection();
    }

    public boolean onRequestSendAccessibilityEventInternal(View view, AccessibilityEvent accessibilityEvent) {
        if (!super.onRequestSendAccessibilityEventInternal(view, accessibilityEvent)) {
            return false;
        }
        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
        onInitializeAccessibilityEvent(accessibilityEventObtain);
        dispatchPopulateAccessibilityEvent(accessibilityEventObtain);
        accessibilityEvent.appendRecord(accessibilityEventObtain);
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0 && isChildInGroup() && !isGroupExpanded()) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void onUiModeChanged() {
        this.mUpdateSelfBackgroundOnUpdate = true;
        reInflateViews();
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            Iterator it = notificationChildrenContainer.getAttachedChildren().iterator();
            while (it.hasNext()) {
                ((ExpandableNotificationRow) it.next()).onUiModeChanged();
            }
        }
    }

    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (i == 32) {
            doLongClickCallback();
            return true;
        }
        if (i == 262144 || i == 524288) {
            this.mExpandClickListener.onClick(this);
            return true;
        }
        if (i != 1048576) {
            return false;
        }
        performDismiss(true);
        return true;
    }

    public void performDismiss(boolean z) {
        OnUserInteractionCallback onUserInteractionCallback;
        dismiss(z);
        if (!canEntryBeDismissed() || (onUserInteractionCallback = this.mOnUserInteractionCallback) == null) {
            return;
        }
        onUserInteractionCallback.registerFutureDismissal(this.mEntry, 2).run();
    }

    public void performOnIntrinsicHeightReached(Runnable runnable) {
        this.mOnIntrinsicHeightReachedRunnable = runnable;
        handleIntrinsicHeightReached();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableView
    public long performRemoveAnimation(long j, long j2, float f, boolean z, Runnable runnable, Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter) {
        return super.performRemoveAnimation(j, j2, f, z, runnable, runnable2, animatorListenerAdapter);
    }

    public void prepareExpansionChanged() {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.prepareExpansionChanged();
        }
    }

    public void removeChildNotification(ExpandableNotificationRow expandableNotificationRow) {
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.removeNotification(expandableNotificationRow);
            expandableNotificationRow.setKeepInParentForDismissAnimation(false);
        }
        onAttachedChildrenCountChanged();
        expandableNotificationRow.setIsChildInGroup(false, null);
    }

    public void removeChildrenWithKeepInParent() {
        if (this.mChildrenContainer == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mChildrenContainer.getAttachedChildren());
        int size = arrayList.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) obj;
            if (expandableNotificationRow.keepInParentForDismissAnimation()) {
                this.mChildrenContainer.removeNotification(expandableNotificationRow);
                expandableNotificationRow.setIsChildInGroup(false, null);
                expandableNotificationRow.setKeepInParentForDismissAnimation(false);
                logKeepInParentChildDetached(expandableNotificationRow);
                z = true;
            }
        }
        if (z) {
            onAttachedChildrenCountChanged();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void removeFromTransientContainer() {
        ViewGroup transientContainer = getTransientContainer();
        ViewParent parent = getParent();
        if (transientContainer == null || transientContainer != parent) {
            super.removeFromTransientContainer();
        } else {
            logRemoveFromTransientContainer(transientContainer);
            super.removeFromTransientContainer();
        }
    }

    public void removeTransientView(View view) {
        if (view instanceof ExpandableNotificationRow) {
            logRemoveTransientRow((ExpandableNotificationRow) view);
        }
        super.removeTransientView(view);
    }

    public void reset() {
        this.mShowingPublicInitialized = false;
        unDismiss();
        resetTranslation();
        onHeightReset();
        requestLayout();
        setTargetPoint(null);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    protected void resetAllContentAlphas() {
        this.mPrivateLayout.setAlpha(1.0f);
        this.mPrivateLayout.setLayerType(0, null);
        this.mPublicLayout.setAlpha(1.0f);
        this.mPublicLayout.setLayerType(0, null);
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setAlpha(1.0f);
            this.mChildrenContainer.setLayerType(0, null);
        }
    }

    public void resetChildSystemExpandedStates() {
        if (isSummaryWithChildren()) {
            this.mChildrenContainer.updateExpansionStates();
        }
    }

    public void resetTranslation() {
        Animator animator = this.mTranslateAnim;
        if (animator != null) {
            animator.cancel();
        }
        if (this.mDismissUsingRowTranslationX) {
            setTranslationX(0.0f);
            return;
        }
        if (this.mTranslateableViews != null) {
            for (int i = 0; i < this.mTranslateableViews.size(); i++) {
                ((View) this.mTranslateableViews.get(i)).setTranslationX(0.0f);
            }
            invalidateOutline();
            getEntry().getIcons().getShelfIcon().setScrollX(0);
        }
    }

    public void resetUserExpansion() {
        boolean zIsExpanded = isExpanded();
        this.mHasUserChangedExpansion = false;
        this.mUserExpanded = false;
        if (zIsExpanded != isExpanded()) {
            if (this.mIsSummaryWithChildren) {
                this.mChildrenContainer.onExpansionChanged();
            }
            notifyHeightChanged(false);
        }
        updateShelfIconColor();
    }

    public void setAboveShelf(boolean z) {
        boolean zIsAboveShelf = isAboveShelf();
        this.mAboveShelf = z;
        if (isAboveShelf() != zIsAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!zIsAboveShelf);
        }
    }

    public void setAboveShelfChangedListener(AboveShelfChangedListener aboveShelfChangedListener) {
        this.mAboveShelfChangedListener = aboveShelfChangedListener;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setActualHeight(int i, boolean z) {
        ViewGroup viewGroup;
        boolean z2 = i != getActualHeight();
        super.setActualHeight(i, z);
        if (z2 && isRemoved() && (viewGroup = (ViewGroup) getParent()) != null) {
            viewGroup.invalidate();
        }
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.setContentHeight(i);
        }
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.setActualHeight(i);
        }
        handleIntrinsicHeightReached();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setActualHeightAnimating(boolean z) {
        NotificationContentView notificationContentView = this.mPrivateLayout;
        if (notificationContentView != null) {
            notificationContentView.setContentHeightAnimating(z);
        }
    }

    public void setAnimationRunning(boolean z) {
        for (NotificationContentView notificationContentView : this.mLayouts) {
            if (notificationContentView != null) {
                notificationContentView.setContentAnimationRunning(z);
                setIconAnimationRunning(z, notificationContentView);
            }
        }
        if (this.mIsSummaryWithChildren) {
            NotificationViewWrapper notificationViewWrapper = this.mChildrenContainer.getNotificationViewWrapper();
            if (notificationViewWrapper != null) {
                setIconAnimationRunningForChild(z, notificationViewWrapper.getIcon());
            }
            NotificationViewWrapper minimizedGroupHeaderWrapper = this.mChildrenContainer.getMinimizedGroupHeaderWrapper();
            if (minimizedGroupHeaderWrapper != null) {
                setIconAnimationRunningForChild(z, minimizedGroupHeaderWrapper.getIcon());
            }
            List attachedChildren = this.mChildrenContainer.getAttachedChildren();
            for (int i = 0; i < attachedChildren.size(); i++) {
                ((ExpandableNotificationRow) attachedChildren.get(i)).setAnimationRunning(z);
            }
        }
        this.mAnimationRunning = z;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    protected void setBackgroundTintColor(int i) {
        super.setBackgroundTintColor(i);
        NotificationContentView showingLayout = getShowingLayout();
        if (showingLayout != null) {
            showingLayout.setBackgroundTintColor(i);
        }
    }

    public void setBigPictureIconManager(BigPictureIconManager bigPictureIconManager) {
        this.mBigPictureIconManager = bigPictureIconManager;
    }

    protected void setChildrenContainer(NotificationChildrenContainer notificationChildrenContainer) {
        this.mChildrenContainer = notificationChildrenContainer;
    }

    public void setChildrenExpanded(boolean z, boolean z2) {
        this.mChildrenExpanded = z;
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setChildrenExpanded(z);
        }
        updateBackgroundForGroupState();
        updateClickAndFocus();
    }

    public void setChronometerRunning(boolean z) {
        this.mLastChronometerRunning = z;
        setChronometerRunning(z, this.mPrivateLayout);
        setChronometerRunning(z, this.mPublicLayout);
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            List attachedChildren = notificationChildrenContainer.getAttachedChildren();
            for (int i = 0; i < attachedChildren.size(); i++) {
                ((ExpandableNotificationRow) attachedChildren.get(i)).setChronometerRunning(z);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipBottomAmount(int i) {
        if (this.mExpandAnimationRunning) {
            return;
        }
        if (i != this.mClipBottomAmount) {
            super.setClipBottomAmount(i);
            for (NotificationContentView notificationContentView : this.mLayouts) {
                notificationContentView.setClipBottomAmount(i);
            }
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer == null || this.mChildIsExpanding) {
            return;
        }
        notificationChildrenContainer.setClipBottomAmount(i);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipToActualHeight(boolean z) {
        boolean z2 = true;
        super.setClipToActualHeight(z || isUserLocked());
        NotificationContentView showingLayout = getShowingLayout();
        if (!z && !isUserLocked()) {
            z2 = false;
        }
        showingLayout.setClipToActualHeight(z2);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipTopAmount(int i) {
        super.setClipTopAmount(i);
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.setClipTopAmount(i);
        }
    }

    public void setContentAlpha(float f) {
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.setAlpha(f);
        }
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setContentAlpha(f);
        }
    }

    public void setContentBackground(int i, boolean z, NotificationContentView notificationContentView) {
        if (getShowingLayout() == notificationContentView) {
            setTintColor(i, z);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    public void setDismissUsingRowTranslationX(boolean z) {
        if (z != this.mDismissUsingRowTranslationX) {
            float translation = getTranslation();
            if (translation != 0.0f) {
                setTranslation(0.0f);
            }
            super.setDismissUsingRowTranslationX(z);
            if (translation != 0.0f) {
                setTranslation(translation);
            }
            NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
            if (notificationChildrenContainer != null) {
                List attachedChildren = notificationChildrenContainer.getAttachedChildren();
                for (int i = 0; i < attachedChildren.size(); i++) {
                    ((ExpandableNotificationRow) attachedChildren.get(i)).setDismissUsingRowTranslationX(z);
                }
            }
        }
    }

    public void setDragController(ExpandableNotificationRowDragController expandableNotificationRowDragController) {
        this.mDragController = expandableNotificationRowDragController;
    }

    protected void setEntry(NotificationEntry notificationEntry) {
        this.mEntry = notificationEntry;
    }

    public void setExpandable(boolean z) {
        this.mExpandable = z;
        this.mPrivateLayout.updateExpandButtons(isExpandable());
    }

    public void setExpandingClipPath(Path path) {
        this.mExpandingClipPath = path;
        invalidate();
    }

    public void setFeedbackIcon(FeedbackIcon feedbackIcon) {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.setFeedbackIcon(feedbackIcon);
        }
        this.mPrivateLayout.setFeedbackIcon(feedbackIcon);
        this.mPublicLayout.setFeedbackIcon(feedbackIcon);
    }

    public void setGroupExpansionChanging(boolean z) {
        this.mGroupExpansionChanging = z;
    }

    public void setGroupHeader(NotificationHeaderView notificationHeaderView) {
        getChildrenContainerNonNull().setGroupHeader(notificationHeaderView, this.mExpandClickListener);
    }

    public void setHeadsUp(boolean z) {
        boolean zIsAboveShelf = isAboveShelf();
        int intrinsicHeight = getIntrinsicHeight();
        this.mIsHeadsUp = z;
        this.mPrivateLayout.setHeadsUp(z);
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.updateGroupOverflow();
        }
        if (intrinsicHeight != getIntrinsicHeight()) {
            notifyHeightChanged(false);
        }
        if (z) {
            this.mMustStayOnScreen = true;
            setAboveShelf(true);
        } else if (isAboveShelf() != zIsAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!zIsAboveShelf);
        }
    }

    public void setHeadsUpAnimatingAway(boolean z) {
        Consumer consumer;
        boolean zIsAboveShelf = isAboveShelf();
        boolean z2 = z != this.mHeadsupDisappearRunning;
        this.mHeadsupDisappearRunning = z;
        this.mPrivateLayout.setHeadsUpAnimatingAway(z);
        if (z2 && (consumer = this.mHeadsUpAnimatingAwayListener) != null) {
            consumer.accept(Boolean.valueOf(z));
        }
        if (isAboveShelf() != zIsAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!zIsAboveShelf);
        }
    }

    public void setHeadsUpAnimatingAwayListener(Consumer consumer) {
        this.mHeadsUpAnimatingAwayListener = consumer;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setHeadsUpIsVisible() {
        super.setHeadsUpIsVisible();
        this.mMustStayOnScreen = false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setHideSensitive(boolean z, boolean z2, long j, long j2) {
        if (getVisibility() == 8) {
            return;
        }
        boolean z3 = this.mShowingPublic;
        boolean z4 = this.mSensitive && z;
        this.mShowingPublic = z4;
        if (this.mShowingPublicInitialized && z4 == z3) {
            return;
        }
        float alpha = getContentView().getAlpha();
        if (z2) {
            animateShowingPublic(j, j2, this.mShowingPublic);
        } else {
            this.mPublicLayout.animate().cancel();
            this.mPrivateLayout.animate().cancel();
            NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
            if (notificationChildrenContainer != null) {
                notificationChildrenContainer.animate().cancel();
            }
            resetAllContentAlphas();
            this.mPublicLayout.setVisibility(this.mShowingPublic ? 0 : 4);
            updateChildrenVisibility();
            if (NotificationContentAlphaOptimization.isEnabled() && alpha != 1.0f) {
                setAlphaAndLayerType(this.mShowingPublic ? this.mPublicLayout : this.mPrivateLayout, alpha);
            }
        }
        getShowingLayout().updateBackgroundColor(z2);
        this.mPrivateLayout.updateExpandButtons(isExpandable());
        updateShelfIconColor();
        this.mShowingPublicInitialized = true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setHideSensitiveForIntrinsicHeight(boolean z) {
        this.mHideSensitiveForIntrinsicHeight = z;
        if (this.mIsSummaryWithChildren) {
            List attachedChildren = this.mChildrenContainer.getAttachedChildren();
            for (int i = 0; i < attachedChildren.size(); i++) {
                ((ExpandableNotificationRow) attachedChildren.get(i)).setHideSensitiveForIntrinsicHeight(z);
            }
        }
    }

    public void setIsChildInGroup(boolean z, ExpandableNotificationRow expandableNotificationRow) {
        ExpandableNotificationRow expandableNotificationRow2;
        if (this.mExpandAnimationRunning && !z && (expandableNotificationRow2 = this.mNotificationParent) != null) {
            expandableNotificationRow2.setChildIsExpanding(false);
            this.mNotificationParent.setExpandingClipPath(null);
            this.mNotificationParent.setExtraWidthForClipping(0.0f);
            this.mNotificationParent.setMinimumHeightForClipping(0);
        }
        if (!z) {
            expandableNotificationRow = null;
        }
        this.mNotificationParent = expandableNotificationRow;
        this.mPrivateLayout.setIsChildInGroup(z);
        updateBackgroundForGroupState();
        updateClickAndFocus();
        if (this.mNotificationParent != null) {
            setOverrideTintColor(0, 0.0f);
            this.mNotificationParent.updateBackgroundForGroupState();
        }
        updateBackgroundClipping();
        updateBaseRoundness();
    }

    public void setIsMinimized(boolean z) {
        this.mIsMinimized = z;
        this.mPrivateLayout.setIsLowPriority(z);
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setIsMinimized(z);
        }
    }

    public void setJustClicked(boolean z) {
        this.mJustClicked = z;
    }

    public void setKeepInParentForDismissAnimation(boolean z) {
        this.mKeepInParentForDismissAnimation = z;
    }

    public void setLastAudiblyAlertedMs(long j) {
        long jCurrentTimeMillis = System.currentTimeMillis() - j;
        long j2 = RECENTLY_ALERTED_THRESHOLD_MS;
        boolean z = jCurrentTimeMillis < j2;
        applyAudiblyAlertedRecently(z);
        removeCallbacks(this.mExpireRecentlyAlertedFlag);
        if (z) {
            postDelayed(this.mExpireRecentlyAlertedFlag, j2 - jCurrentTimeMillis);
        }
    }

    public void setLegacy(boolean z) {
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.setLegacy(z);
        }
    }

    public void setMinimizedGroupHeader(NotificationHeaderView notificationHeaderView) {
        getChildrenContainerNonNull().setLowPriorityGroupHeader(notificationHeaderView, this.mExpandClickListener);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationFadeAware
    public void setNotificationFaded(boolean z) {
        this.mIsFaded = z;
        if (childrenRequireOverlappingRendering()) {
            NotificationFadeAware.setLayerTypeForFaded(this, z);
            setNotificationFadedOnChildren(false);
        } else {
            NotificationFadeAware.setLayerTypeForFaded(this, false);
            setNotificationFadedOnChildren(z);
        }
    }

    public void setNotificationGroupWhen(long j) {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.setNotificationGroupWhen(j);
            this.mPublicLayout.setNotificationWhen(j);
            return;
        }
        Log.w("ExpandableNotifRow", "setNotificationGroupWhen( whenMillis: " + j + ") mIsSummaryWithChildren: false mChildrenContainer has not been inflated yet.");
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
        updateClickAndFocus();
    }

    public void setOnDragSuccessListener(OnDragSuccessListener onDragSuccessListener) {
        this.mOnDragSuccessListener = onDragSuccessListener;
    }

    public void setOnExpansionChangedListener(OnExpansionChangedListener onExpansionChangedListener) {
        this.mExpansionChangedListener = onExpansionChangedListener;
    }

    void setOnKeyguard(boolean z) {
        if (z != this.mOnKeyguard) {
            boolean zIsAboveShelf = isAboveShelf();
            boolean zIsExpanded = isExpanded();
            this.mOnKeyguard = z;
            onExpansionChanged(false, zIsExpanded);
            if (zIsExpanded != isExpanded()) {
                if (this.mIsSummaryWithChildren) {
                    this.mChildrenContainer.updateGroupOverflow();
                }
                notifyHeightChanged(false);
            }
            if (isAboveShelf() != zIsAboveShelf) {
                this.mAboveShelfChangedListener.onAboveShelfStateChanged(!zIsAboveShelf);
            }
        }
    }

    public void setPinned(boolean z) {
        int intrinsicHeight = getIntrinsicHeight();
        boolean zIsAboveShelf = isAboveShelf();
        this.mIsPinned = z;
        if (intrinsicHeight != getIntrinsicHeight()) {
            notifyHeightChanged(false);
        }
        if (z) {
            setAnimationRunning(true);
            this.mExpandedWhenPinned = false;
        } else if (this.mExpandedWhenPinned) {
            setUserExpanded(true);
        }
        setChronometerRunning(this.mLastChronometerRunning);
        if (isAboveShelf() != zIsAboveShelf) {
            this.mAboveShelfChangedListener.onAboveShelfStateChanged(!zIsAboveShelf);
        }
    }

    protected void setPrivateLayout(NotificationContentView notificationContentView) {
        this.mPrivateLayout = notificationContentView;
        this.mLayouts = new NotificationContentView[]{notificationContentView, this.mPublicLayout};
    }

    public void setPublicExpanderVisible(boolean z) {
        if (this.mShowPublicExpander != z) {
            this.mShowPublicExpander = z;
            this.mPublicLayout.updateExpandButtons(z);
        }
    }

    protected void setPublicLayout(NotificationContentView notificationContentView) {
        this.mPublicLayout = notificationContentView;
        this.mLayouts = new NotificationContentView[]{this.mPrivateLayout, notificationContentView};
    }

    public void setRemoteInputController(RemoteInputController remoteInputController) {
        this.mPrivateLayout.setRemoteInputController(remoteInputController);
    }

    public void setRemoved() {
        this.mRemoved = true;
        this.mTranslationWhenRemoved = getTranslationY();
        this.mWasChildInGroupWhenRemoved = isChildInGroup();
        if (isChildInGroup()) {
            this.mTranslationWhenRemoved += getNotificationParent().getTranslationY();
        }
        for (NotificationContentView notificationContentView : this.mLayouts) {
            notificationContentView.setRemoved();
        }
    }

    public void setSensitive(boolean z, boolean z2) {
        int intrinsicHeight = getIntrinsicHeight();
        this.mSensitive = z;
        this.mSensitiveHiddenInGeneral = z2;
        if (intrinsicHeight != getIntrinsicHeight()) {
            notifyHeightChanged(true);
        }
    }

    public void setShowSnooze(boolean z) {
        this.mShowSnooze = z;
    }

    public void setSingleLineWidthIndention(int i) {
        this.mPrivateLayout.setSingleLineWidthIndention(i);
    }

    public void setSystemChildExpanded(boolean z) {
        this.mIsSystemChildExpanded = z;
    }

    public void setSystemExpanded(boolean z) {
        if (z != this.mIsSystemExpanded) {
            boolean zIsExpanded = isExpanded();
            this.mIsSystemExpanded = z;
            notifyHeightChanged(false);
            onExpansionChanged(false, zIsExpanded);
            if (this.mIsSummaryWithChildren) {
                this.mChildrenContainer.updateGroupOverflow();
                resetChildSystemExpandedStates();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.SwipeableView
    public void setTranslation(float f) {
        invalidate();
        if (this.mDismissUsingRowTranslationX) {
            setTranslationX(f);
            return;
        }
        if (this.mTranslateableViews != null) {
            for (int i = 0; i < this.mTranslateableViews.size(); i++) {
                if (this.mTranslateableViews.get(i) != null) {
                    ((View) this.mTranslateableViews.get(i)).setTranslationX(f);
                }
            }
            invalidateOutline();
            getEntry().getIcons().getShelfIcon().setScrollX((int) (-f));
        }
    }

    public void setUntruncatedChildCount(int i) {
        if (this.mChildrenContainer == null) {
            this.mChildrenContainerStub.inflate();
        }
        this.mChildrenContainer.setUntruncatedChildCount(i);
    }

    public void setUserExpanded(boolean z) {
        setUserExpanded(z, false);
    }

    public void setUserExpanded(boolean z, boolean z2) {
        if (this.mIsSummaryWithChildren && !shouldShowPublic() && z2 && !this.mChildrenContainer.showingAsLowPriority()) {
            boolean zIsGroupExpanded = this.mGroupExpansionManager.isGroupExpanded(this.mEntry);
            this.mGroupExpansionManager.setGroupExpanded(this.mEntry, z);
            onExpansionChanged(true, zIsGroupExpanded);
        } else if (!z || this.mExpandable) {
            boolean zIsExpanded = isExpanded();
            this.mHasUserChangedExpansion = true;
            this.mUserExpanded = z;
            onExpansionChanged(true, zIsExpanded);
            if (zIsExpanded || !isExpanded() || getActualHeight() == getIntrinsicHeight()) {
                return;
            }
            notifyHeightChanged(true);
        }
    }

    public void setUserLocked(boolean z) {
        this.mUserLocked = z;
        this.mPrivateLayout.setUserExpanding(z);
        NotificationChildrenContainer notificationChildrenContainer = this.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.setUserLocked(z);
            if (this.mIsSummaryWithChildren) {
                if (z || !isGroupExpanded()) {
                    updateBackgroundForGroupState();
                }
            }
        }
    }

    public void setUsesIncreasedCollapsedHeight(boolean z) {
        this.mUseIncreasedCollapsedHeight = z;
    }

    public void setUsesIncreasedHeadsUpHeight(boolean z) {
        this.mUseIncreasedHeadsUpHeight = z;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    protected boolean shouldClipToActualHeight() {
        return super.shouldClipToActualHeight() && !this.mExpandAnimationRunning;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public boolean showingPulsing() {
        if (!isHeadsUpState()) {
            return false;
        }
        if (isDozing()) {
            return true;
        }
        return this.mOnKeyguard && isBypassEnabled();
    }

    public void startChildAnimation(AnimationProperties animationProperties) {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.startAnimationToState(animationProperties);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public void updateBackgroundColors() {
        updateBackgroundColorsOfSelf();
        if (this.mIsSummaryWithChildren) {
            Iterator it = this.mChildrenContainer.getAttachedChildren().iterator();
            while (it.hasNext()) {
                ((ExpandableNotificationRow) it.next()).updateBackgroundColors();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void updateBackgroundForGroupState() {
        int i = 0;
        if (this.mIsSummaryWithChildren) {
            boolean z = (this.mShowGroupBackgroundWhenExpanded || !isGroupExpanded() || isGroupExpansionChanging() || isUserLocked()) ? false : true;
            this.mShowNoBackground = z;
            this.mChildrenContainer.updateHeaderForExpansion(z);
            List attachedChildren = this.mChildrenContainer.getAttachedChildren();
            while (i < attachedChildren.size()) {
                ((ExpandableNotificationRow) attachedChildren.get(i)).updateBackgroundForGroupState();
                i++;
            }
        } else if (isChildInGroup()) {
            int backgroundColorForExpansionState = getShowingLayout().getBackgroundColorForExpansionState();
            if (isGroupExpanded() || ((this.mNotificationParent.isGroupExpansionChanging() || this.mNotificationParent.isUserLocked()) && backgroundColorForExpansionState != 0)) {
                i = 1;
            }
            this.mShowNoBackground = i ^ 1;
        } else {
            this.mShowNoBackground = false;
        }
        updateOutline();
        updateBackground();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    protected void updateBackgroundTint() {
        super.updateBackgroundTint();
        updateBackgroundForGroupState();
        if (this.mIsSummaryWithChildren) {
            List attachedChildren = this.mChildrenContainer.getAttachedChildren();
            for (int i = 0; i < attachedChildren.size(); i++) {
                ((ExpandableNotificationRow) attachedChildren.get(i)).updateBackgroundForGroupState();
            }
        }
    }

    public void updateChildrenAppearance() {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.updateChildrenAppearance();
        }
    }

    public void updateChildrenStates() {
        if (this.mIsSummaryWithChildren) {
            this.mChildrenContainer.updateState(getViewState());
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    protected void updateContentTransformation() {
        if (this.mExpandAnimationRunning) {
            return;
        }
        super.updateContentTransformation();
    }

    public void updateNotificationColor() {
        this.mNotificationColor = ContrastColorUtil.resolveContrastColor(((FrameLayout) this).mContext, this.mEntry.getSbn().getNotification().color, getBackgroundColorWithoutTint(), (getResources().getConfiguration().uiMode & 48) == 32);
    }

    void updateShelfIconColor() {
        StatusBarIconView shelfIcon = this.mEntry.getIcons().getShelfIcon();
        shelfIcon.setStaticDrawableColor(!Boolean.TRUE.equals(shelfIcon.getTag(com.android.systemui.res.R$id.icon_is_pre_L)) || NotificationUtils.isGrayscale(shelfIcon, ContrastColorUtil.getInstance(((FrameLayout) this).mContext)) ? getOriginalIconColor() : 0);
    }

    public boolean wasChildInGroupWhenRemoved() {
        return this.mWasChildInGroupWhenRemoved;
    }

    public boolean wasJustClicked() {
        return this.mJustClicked;
    }
}
