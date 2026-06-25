package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.SystemClock;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.ExpandHelper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.res.R$bool;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$drawable;
import com.android.systemui.res.R$string;
import com.android.systemui.statusbar.EmptyShadeView;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.HeadsUpUtil;
import com.android.systemui.statusbar.policy.ScrollAdapter;
import com.android.systemui.util.Assert;
import com.android.systemui.util.Compile;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$layout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationStackScrollLayout extends ViewGroup implements Dumpable {
    static final float RUBBER_BAND_FACTOR_NORMAL = 0.1f;
    private final Callable collectVisibleLocationsCallable;
    private boolean mActivateNeedsAnimation;
    private int mActivePointerId;
    private ActivityStarter mActivityStarter;
    private final ArrayList mAddedHeadsUpChildren;
    private final AmbientState mAmbientState;
    private boolean mAnimateStackYForContentHeightChange;
    private final ArrayList mAnimationEvents;
    private final HashSet mAnimationFinishedRunnables;
    private boolean mAnimationRunning;
    private boolean mAnimationsEnabled;
    private final Rect mBackgroundAnimationRect;
    private float mBackgroundXFactor;
    private boolean mBackwardScrollable;
    private final float[] mBgCornerRadii;
    private int mBottomPadding;
    private boolean mChangePositionInProgress;
    boolean mCheckForLeavebehind;
    private boolean mChildTransferInProgress;
    private final ArrayList mChildrenChangingPositions;
    private final HashSet mChildrenToAddAnimated;
    private final ArrayList mChildrenToRemoveAnimated;
    private boolean mChildrenUpdateRequested;
    private final ViewTreeObserver.OnPreDrawListener mChildrenUpdater;
    private ClearAllAnimationListener mClearAllAnimationListener;
    private Runnable mClearAllFinishedWhilePanelExpandedRunnable;
    private boolean mClearAllInProgress;
    private ClearAllListener mClearAllListener;
    private final HashSet mClearTransientViewsWhenFinished;
    private final Rect mClipRect;
    private int mContentHeight;
    private boolean mContinuousShadowUpdate;
    private NotificationStackScrollLayoutController mController;
    private int mCornerRadius;
    private int mCurrentStackHeight;
    private final boolean mDebugLines;
    private Paint mDebugPaint;
    private final boolean mDebugRemoveAnimation;
    private Set mDebugTextUsedYPositions;
    private boolean mDisallowDismissInThisMotion;
    private boolean mDisallowScrollingInThisMotion;
    private boolean mDismissUsingRowTranslationX;
    private boolean mDontClampNextScroll;
    private boolean mDontReportNextOverScroll;
    private int mDownX;
    protected EmptyShadeView mEmptyShadeView;
    private boolean mEverythingNeedsAnimation;
    private final ExpandHelper mExpandHelper;
    private final ExpandHelper.Callback mExpandHelperCallback;
    private ExpandableView mExpandedGroupView;
    private final ArrayList mExpandedHeightListeners;
    private boolean mExpandedInThisMotion;
    private boolean mExpandingNotification;
    private final FeatureFlags mFeatureFlags;
    private Runnable mFinishScrollingCallback;
    private boolean mFlingAfterUpEvent;
    protected FooterView mFooterView;
    private boolean mForceNoOverlappingRendering;
    private View mForcedScroll;
    private boolean mForwardScrollable;
    private final HashSet mFromMoreCardAdditions;
    private int mGapHeight;
    private long mGoToFullShadeDelay;
    private boolean mGoToFullShadeNeedsAnimation;
    private final GroupExpansionManager mGroupExpansionManager;
    private final GroupMembershipManager mGroupMembershipManager;
    private boolean mHasFilteredOutSeenNotifications;
    boolean mHeadsUpAnimatingAway;
    private Consumer mHeadsUpAnimatingAwayListener;
    private final HashSet mHeadsUpChangeAnimations;
    private boolean mHeadsUpGoingAwayAnimationsAllowed;
    private int mHeadsUpInset;
    private boolean mHideSensitiveNeedsAnimation;
    private Interpolator mHideXInterpolator;
    private boolean mHighPriorityBeforeSpeedBump;
    int mImeInset;
    private boolean mInHeadsUpPinnedMode;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private final WindowInsetsAnimation.Callback mInsetsCallback;
    private float mInterpolatedHideAmount;
    private float mIntrinsicContentHeight;
    private int mIntrinsicPadding;
    private boolean mIsBeingDragged;
    private boolean mIsClipped;
    private boolean mIsCurrentUserSetup;
    private boolean mIsExpanded;
    private boolean mIsExpansionChanging;
    private boolean mIsInsetAnimationRunning;
    private boolean mIsRemoteInputActive;
    private boolean mIsSmallLandscapeLockscreenEnabled;
    private float mKeyguardBottomPadding;
    private boolean mKeyguardBypassEnabled;
    private String mLastInitViewDumpString;
    private long mLastInitViewElapsedRealtime;
    private int mLastMotionY;
    private String mLastUpdateSidePaddingDumpString;
    private long mLastUpdateSidePaddingElapsedRealtime;
    private final Path mLaunchedNotificationClipPath;
    private boolean mLaunchingNotification;
    private float mLinearHideAmount;
    private NotificationLogger.OnChildLocationsChangedListener mListener;
    private NotificationStackScrollLogger mLogger;
    private View.OnClickListener mManageButtonClickListener;
    private int mMaxDisplayedNotifications;
    private int mMaxLayoutHeight;
    private float mMaxOverScroll;
    private int mMaxScrollAfterExpand;
    private int mMaximumVelocity;
    private int mMinInteractionHeight;
    private float mMinTopOverScrollToEscape;
    private int mMinimumPaddings;
    private int mMinimumVelocity;
    private boolean mNeedViewResizeAnimation;
    private boolean mNeedsAnimation;
    private NotificationStackSizeCalculator mNotificationStackSizeCalculator;
    private final ExpandableView.OnHeightChangedListener mOnChildHeightChangedListener;
    private final NotificationEntry.OnSensitivityChangedListener mOnChildSensitivityChangedListener;
    private ExpandableView.OnHeightChangedListener mOnHeightChangedListener;
    private Runnable mOnHeightChangedRunnable;
    private boolean mOnlyScrollingInThisMotion;
    private final ViewOutlineProvider mOutlineProvider;
    private float mOverScrolledBottomPixels;
    private float mOverScrolledTopPixels;
    private int mOverflingDistance;
    private int mOwnScrollY;
    private int mPaddingBetweenElements;
    private boolean mPanelTracking;
    private boolean mPulsing;
    private float mQsExpansionFraction;
    private boolean mQsFullScreen;
    protected ViewGroup mQsHeader;
    private final Rect mQsHeaderBound;
    private int mQsScrollBoundaryPosition;
    private int mQsTilePadding;
    private final Runnable mReclamp;
    private final Runnable mReflingAndAnimateScroll;
    private Rect mRequestedClipBounds;
    private Runnable mResetUserExpandedStatesRunnable;
    private final Path mRoundedClipPath;
    private int mRoundedRectClippingBottom;
    private final ViewTreeObserver.OnPreDrawListener mRunningAnimationUpdater;
    private final ScrollAdapter mScrollAdapter;
    private Consumer mScrollListener;
    private final ScrollViewFields mScrollViewFields;
    private boolean mScrollable;
    private boolean mScrolledToTopOnFirstDown;
    private OverScroller mScroller;
    protected boolean mScrollingEnabled;
    private final NotificationSection[] mSections;
    private final NotificationSectionsManager mSectionsManager;
    private boolean mSendingTouchesToSceneFramework;
    private boolean mShadeNeedsToClose;
    private final ViewTreeObserver.OnPreDrawListener mShadowUpdater;
    private boolean mShouldSkipTopPaddingAnimationAfterFold;
    private boolean mShouldUseRoundedRectClipping;
    private boolean mShouldUseSplitNotificationShade;
    private int mSidePaddings;
    private boolean mSkinnyNotifsInLandscape;
    private float mSlopMultiplier;
    private int mSpeedBumpIndex;
    private boolean mSpeedBumpIndexDirty;
    private final int mSplitShadeMinContentHeight;
    private final StackScrollAlgorithm mStackScrollAlgorithm;
    private final StackStateAnimator mStateAnimator;
    int mStatusBarHeight;
    private int mStatusBarState;
    private boolean mSuppressChildrenMeasureAndLayout;
    private NotificationSwipeHelper mSwipeHelper;
    private final ArrayList mSwipedOutViews;
    private final int[] mTempInt2;
    private final ArrayList mTmpList;
    private final Rect mTmpRect;
    private final ArrayList mTmpSortedChildren;
    private ExpandableNotificationRow mTopHeadsUpRow;
    private boolean mTopPaddingNeedsAnimation;
    private NotificationStackScrollLayoutController.TouchHandler mTouchHandler;
    private boolean mTouchIsClick;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private final Comparator mViewPositionComparator;
    private int mWaterfallTopInset;
    private boolean mWillExpand;
    private static final boolean SPEW = Log.isLoggable("StackScroller", 2);
    private static final boolean DEBUG_UPDATE_SIDE_PADDING = Compile.IS_DEBUG;

    class AnimationEvent {
        static AnimationFilter[] FILTERS = {new AnimationFilter().animateAlpha().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateAlpha().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateZ(), new AnimationFilter(), new AnimationFilter().animateAlpha().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateHideSensitive(), new AnimationFilter().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateAlpha().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateHeight().animateTopInset().animateY().animateZ().hasDelays(), new AnimationFilter().animateHeight().animateTopInset().animateY().animateZ(), new AnimationFilter().animateAlpha().animateHideSensitive().animateHeight().animateTopInset().animateY().animateZ()};
        static int[] LENGTHS = {464, 464, 360, 360, 220, 220, 360, 448, 360, 360, 360, 400, 400, 400, 360, 360};
        final int animationType;
        final long eventStartTime;
        final AnimationFilter filter;
        boolean headsUpFromBottom;
        final long length;
        final ExpandableView mChangingView;
        View viewAfterChangingView;

        AnimationEvent(ExpandableView expandableView, int i) {
            this(expandableView, i, LENGTHS[i]);
        }

        AnimationEvent(ExpandableView expandableView, int i, long j) {
            this(expandableView, i, j, FILTERS[i]);
        }

        AnimationEvent(ExpandableView expandableView, int i, long j, AnimationFilter animationFilter) {
            this.eventStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mChangingView = expandableView;
            this.animationType = i;
            this.length = j;
            this.filter = animationFilter;
        }

        static long combineLength(ArrayList arrayList) {
            int size = arrayList.size();
            long jMax = 0;
            for (int i = 0; i < size; i++) {
                AnimationEvent animationEvent = (AnimationEvent) arrayList.get(i);
                jMax = Math.max(jMax, animationEvent.length);
                if (animationEvent.animationType == 7) {
                    return animationEvent.length;
                }
            }
            return jMax;
        }
    }

    interface ClearAllAnimationListener {
        void onAnimationEnd(List list, int i);
    }

    interface ClearAllListener {
        void onClearAll(int i);
    }

    public NotificationStackScrollLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        this.mShadeNeedsToClose = false;
        this.mCurrentStackHeight = Integer.MAX_VALUE;
        this.mActivePointerId = -1;
        this.mImeInset = 0;
        this.mScrollViewFields = new ScrollViewFields();
        this.mChildrenToAddAnimated = new HashSet();
        this.mAddedHeadsUpChildren = new ArrayList();
        this.mChildrenToRemoveAnimated = new ArrayList();
        this.mChildrenChangingPositions = new ArrayList();
        this.mFromMoreCardAdditions = new HashSet();
        this.mAnimationEvents = new ArrayList();
        this.mSwipedOutViews = new ArrayList();
        this.mAnimationsEnabled = false;
        this.mSpeedBumpIndex = -1;
        this.mSpeedBumpIndexDirty = true;
        this.mIsExpanded = true;
        this.mChildrenUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                NotificationStackScrollLayout.this.updateForcedScroll();
                NotificationStackScrollLayout.this.updateChildren();
                NotificationStackScrollLayout.this.mChildrenUpdateRequested = false;
                NotificationStackScrollLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        };
        this.mTempInt2 = new int[2];
        this.mAnimationFinishedRunnables = new HashSet();
        this.mClearTransientViewsWhenFinished = new HashSet();
        this.mHeadsUpChangeAnimations = new HashSet();
        this.mTmpList = new ArrayList();
        this.mRunningAnimationUpdater = new ViewTreeObserver.OnPreDrawListener(this) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                return true;
            }
        };
        this.mTmpSortedChildren = new ArrayList();
        this.mQsHeaderBound = new Rect();
        this.mShadowUpdater = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                return this.f$0.lambda$new$0();
            }
        };
        this.mViewPositionComparator = new Comparator() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Float.compare(((ExpandableView) obj).getTranslationY() + r1.getActualHeight(), ((ExpandableView) obj2).getTranslationY() + r2.getActualHeight());
            }
        };
        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.3
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                if (!NotificationStackScrollLayout.this.mAmbientState.isHiddenAtAll()) {
                    ViewOutlineProvider.BACKGROUND.getOutline(view, outline);
                    return;
                }
                outline.setRoundRect(NotificationStackScrollLayout.this.mBackgroundAnimationRect, MathUtils.lerp(NotificationStackScrollLayout.this.mCornerRadius / 2.0f, NotificationStackScrollLayout.this.mCornerRadius, NotificationStackScrollLayout.this.mHideXInterpolator.getInterpolation((1.0f - NotificationStackScrollLayout.this.mLinearHideAmount) * NotificationStackScrollLayout.this.mBackgroundXFactor)));
                outline.setAlpha(1.0f - NotificationStackScrollLayout.this.mAmbientState.getHideAmount());
            }
        };
        this.mOutlineProvider = viewOutlineProvider;
        this.collectVisibleLocationsCallable = new Callable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.4
            @Override // java.util.concurrent.Callable
            public Map call() {
                return NotificationStackScrollLayout.this.collectVisibleNotificationLocations();
            }
        };
        WindowInsetsAnimation.Callback callback = new WindowInsetsAnimation.Callback(1) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.5
            @Override // android.view.WindowInsetsAnimation.Callback
            public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                NotificationStackScrollLayout.this.mIsInsetAnimationRunning = false;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                NotificationStackScrollLayout.this.mIsInsetAnimationRunning = true;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsets onProgress(WindowInsets windowInsets, List list) {
                NotificationStackScrollLayout.this.updateImeInset(windowInsets);
                return windowInsets;
            }
        };
        this.mInsetsCallback = callback;
        this.mInterpolatedHideAmount = 0.0f;
        this.mLinearHideAmount = 0.0f;
        this.mBackgroundXFactor = 1.0f;
        this.mMaxDisplayedNotifications = -1;
        this.mKeyguardBottomPadding = -1.0f;
        this.mClipRect = new Rect();
        this.mHeadsUpGoingAwayAnimationsAllowed = true;
        this.mReflingAndAnimateScroll = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.animateScroll();
            }
        };
        this.mBackgroundAnimationRect = new Rect();
        this.mExpandedHeightListeners = new ArrayList();
        this.mTmpRect = new Rect();
        this.mHideXInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        this.mRoundedClipPath = new Path();
        this.mLaunchedNotificationClipPath = new Path();
        this.mShouldUseRoundedRectClipping = false;
        this.mBgCornerRadii = new float[8];
        this.mAnimateStackYForContentHeightChange = false;
        this.mDismissUsingRowTranslationX = true;
        this.mShouldSkipTopPaddingAnimationAfterFold = false;
        this.mIsSmallLandscapeLockscreenEnabled = false;
        this.mOnChildHeightChangedListener = new ExpandableView.OnHeightChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.6
            @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
            public void onHeightChanged(ExpandableView expandableView, boolean z) {
                NotificationStackScrollLayout.this.onChildHeightChanged(expandableView, z);
            }

            @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
            public void onReset(ExpandableView expandableView) {
                NotificationStackScrollLayout.this.onChildHeightReset(expandableView);
            }
        };
        this.mOnChildSensitivityChangedListener = new NotificationEntry.OnSensitivityChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.7
            @Override // com.android.systemui.statusbar.notification.collection.NotificationEntry.OnSensitivityChangedListener
            public void onSensitivityChanged(NotificationEntry notificationEntry) {
                if (NotificationStackScrollLayout.this.mAnimationsEnabled) {
                    NotificationStackScrollLayout.this.mHideSensitiveNeedsAnimation = true;
                    NotificationStackScrollLayout.this.requestChildrenUpdate();
                }
            }
        };
        ScrollAdapter scrollAdapter = new ScrollAdapter() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.8
            @Override // com.android.systemui.statusbar.policy.ScrollAdapter
            public View getHostView() {
                return NotificationStackScrollLayout.this;
            }

            @Override // com.android.systemui.statusbar.policy.ScrollAdapter
            public boolean isScrolledToBottom() {
                return NotificationStackScrollLayout.this.mOwnScrollY >= NotificationStackScrollLayout.this.getScrollRange();
            }

            @Override // com.android.systemui.statusbar.policy.ScrollAdapter
            public boolean isScrolledToTop() {
                return NotificationStackScrollLayout.this.mOwnScrollY == 0;
            }
        };
        this.mScrollAdapter = scrollAdapter;
        this.mSuppressChildrenMeasureAndLayout = false;
        this.mReclamp = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.9
            @Override // java.lang.Runnable
            public void run() {
                NotificationStackScrollLayout.this.mScroller.startScroll(((ViewGroup) NotificationStackScrollLayout.this).mScrollX, NotificationStackScrollLayout.this.mOwnScrollY, 0, NotificationStackScrollLayout.this.getScrollRange() - NotificationStackScrollLayout.this.mOwnScrollY);
                NotificationStackScrollLayout.this.mDontReportNextOverScroll = true;
                NotificationStackScrollLayout.this.mDontClampNextScroll = true;
                NotificationStackScrollLayout.this.animateScroll();
            }
        };
        ExpandHelper.Callback callback2 = new ExpandHelper.Callback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.11
            @Override // com.android.systemui.ExpandHelper.Callback
            public boolean canChildBeExpanded(View view) {
                if (!(view instanceof ExpandableNotificationRow)) {
                    return false;
                }
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.isExpandable()) {
                    return NotificationStackScrollLayout.this.mIsExpanded || !expandableNotificationRow.isPinned();
                }
                return false;
            }

            @Override // com.android.systemui.ExpandHelper.Callback
            public void expansionStateChanged(boolean z) {
                NotificationStackScrollLayout.this.mExpandingNotification = z;
                if (NotificationStackScrollLayout.this.mExpandedInThisMotion) {
                    return;
                }
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayout.this;
                notificationStackScrollLayout.mMaxScrollAfterExpand = notificationStackScrollLayout.mOwnScrollY;
                NotificationStackScrollLayout.this.mExpandedInThisMotion = true;
            }

            @Override // com.android.systemui.ExpandHelper.Callback
            public ExpandableView getChildAtPosition(float f, float f2) {
                return NotificationStackScrollLayout.this.getChildAtPosition(f, f2);
            }

            @Override // com.android.systemui.ExpandHelper.Callback
            public ExpandableView getChildAtRawPosition(float f, float f2) {
                return NotificationStackScrollLayout.this.getChildAtRawPosition(f, f2);
            }

            @Override // com.android.systemui.ExpandHelper.Callback
            public int getMaxExpandHeight(ExpandableView expandableView) {
                return expandableView.getMaxContentHeight();
            }

            @Override // com.android.systemui.ExpandHelper.Callback
            public void setExpansionCancelled(View view) {
                if (view instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) view).setGroupExpansionChanging(false);
                }
            }

            @Override // com.android.systemui.ExpandHelper.Callback
            public void setUserExpandedChild(View view, boolean z) {
                if (view instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                    if (!z || !NotificationStackScrollLayout.this.onKeyguard()) {
                        expandableNotificationRow.setUserExpanded(z, true);
                        expandableNotificationRow.onExpandedByGesture(z);
                    } else {
                        expandableNotificationRow.setUserLocked(false);
                        NotificationStackScrollLayout.this.updateContentHeight();
                        NotificationStackScrollLayout.this.notifyHeightChangeListener(expandableNotificationRow);
                    }
                }
            }

            @Override // com.android.systemui.ExpandHelper.Callback
            public void setUserLockedChild(View view, boolean z) {
                if (view instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) view).setUserLocked(z);
                }
                NotificationStackScrollLayout.this.cancelLongPress();
                NotificationStackScrollLayout.this.requestDisallowInterceptTouchEvent(true);
            }
        };
        this.mExpandHelperCallback = callback2;
        Resources resources = getResources();
        FeatureFlags featureFlags = (FeatureFlags) Dependency.get(FeatureFlags.class);
        this.mFeatureFlags = featureFlags;
        this.mIsSmallLandscapeLockscreenEnabled = featureFlags.isEnabled(Flags.LOCKSCREEN_ENABLE_LANDSCAPE);
        boolean zIsEnabled = featureFlags.isEnabled(Flags.NSSL_DEBUG_LINES);
        this.mDebugLines = zIsEnabled;
        this.mDebugRemoveAnimation = featureFlags.isEnabled(Flags.NSSL_DEBUG_REMOVE_ANIMATION);
        NotificationSectionsManager notificationSectionsManager = (NotificationSectionsManager) Dependency.getDisplay(this, NotificationSectionsManager.class);
        this.mSectionsManager = notificationSectionsManager;
        notificationSectionsManager.initialize(this);
        this.mSections = notificationSectionsManager.createSectionsForBuckets();
        this.mAmbientState = (AmbientState) Dependency.getDisplay(this, AmbientState.class);
        int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.notification_min_height);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R$dimen.notification_max_height);
        this.mSplitShadeMinContentHeight = resources.getDimensionPixelSize(R$dimen.nssl_split_shade_min_content_height);
        ExpandHelper expandHelper = new ExpandHelper(getContext(), callback2, dimensionPixelSize, dimensionPixelSize2);
        this.mExpandHelper = expandHelper;
        expandHelper.setEventSource(this);
        expandHelper.setScrollAdapter(scrollAdapter);
        this.mStackScrollAlgorithm = createStackScrollAlgorithm(context);
        this.mStateAnimator = new StackStateAnimator(context, this);
        setOutlineProvider(viewOutlineProvider);
        setWillNotDraw(!zIsEnabled);
        if (zIsEnabled) {
            Paint paint = new Paint();
            this.mDebugPaint = paint;
            paint.setColor(-65536);
            this.mDebugPaint.setStrokeWidth(2.0f);
            this.mDebugPaint.setStyle(Paint.Style.STROKE);
            this.mDebugPaint.setTextSize(25.0f);
        }
        this.mGroupMembershipManager = (GroupMembershipManager) Dependency.getDisplay(this, GroupMembershipManager.class);
        this.mGroupExpansionManager = (GroupExpansionManager) Dependency.getDisplay(this, GroupExpansionManager.class);
        setImportantForAccessibility(1);
        setWindowInsetsAnimationCallback(callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateScroll() {
        if (!this.mScroller.computeScrollOffset()) {
            this.mDontClampNextScroll = false;
            Runnable runnable = this.mFinishScrollingCallback;
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        int i = this.mOwnScrollY;
        int currY = this.mScroller.getCurrY();
        if (i != currY) {
            int scrollRange = getScrollRange();
            if ((currY < 0 && i >= 0) || (currY > scrollRange && i <= scrollRange)) {
                setMaxOverScrollFromCurrentVelocity();
            }
            if (this.mDontClampNextScroll) {
                scrollRange = Math.max(scrollRange, i);
            }
            customOverScrollBy(currY - i, i, scrollRange, (int) this.mMaxOverScroll);
        }
        postOnAnimation(this.mReflingAndAnimateScroll);
    }

    private void applyCurrentState() {
        NotificationLogger.OnChildLocationsChangedListener onChildLocationsChangedListener;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAtIndex(i).applyViewState();
        }
        if (!NotificationsLiveDataStoreRefactor.isEnabled() && (onChildLocationsChangedListener = this.mListener) != null) {
            onChildLocationsChangedListener.onChildLocationsChanged();
        }
        runAnimationFinishedRunnables();
        setAnimationRunning(false);
        updateViewShadows();
    }

    private boolean areChildrenVisible(ExpandableNotificationRow expandableNotificationRow) {
        return isVisible(expandableNotificationRow) && expandableNotificationRow.getAttachedChildren() != null && expandableNotificationRow.areChildrenExpanded();
    }

    static boolean canChildBeCleared(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        if (expandableNotificationRow.getEntry().hasFinishedInitialization()) {
            return expandableNotificationRow.canViewBeCleared();
        }
        return false;
    }

    static boolean canChildBeDismissed(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        if (expandableNotificationRow.getEntry().hasFinishedInitialization()) {
            return expandableNotificationRow.canViewBeDismissed();
        }
        return false;
    }

    private void clampScrollPosition() {
        int scrollRange = getScrollRange();
        if (scrollRange >= this.mOwnScrollY || this.mAmbientState.isClearAllInProgress()) {
            return;
        }
        setOwnScrollY(scrollRange, scrollRange < getScrollAmountToScrollBoundary() && this.mAnimateStackYForContentHeightChange);
    }

    private void clearHeadsUpDisappearRunning() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                expandableNotificationRow.setHeadsUpAnimatingAway(false);
                if (expandableNotificationRow.isSummaryWithChildren()) {
                    Iterator it = expandableNotificationRow.getAttachedChildren().iterator();
                    while (it.hasNext()) {
                        ((ExpandableNotificationRow) it.next()).setHeadsUpAnimatingAway(false);
                    }
                }
            }
        }
    }

    private void clearTransient() {
        Iterator it = this.mClearTransientViewsWhenFinished.iterator();
        while (it.hasNext()) {
            ((ExpandableView) it.next()).removeFromTransientContainer();
        }
        this.mClearTransientViewsWhenFinished.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map collectVisibleNotificationLocations() {
        HashMap map = new HashMap();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView childAtIndex = getChildAtIndex(i);
            if (childAtIndex instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) childAtIndex).collectVisibleLocations(map);
            }
        }
        return map;
    }

    private int computeDebugYTextPosition(int i) {
        while (this.mDebugTextUsedYPositions.contains(Integer.valueOf(i))) {
            i = (int) (i + this.mDebugPaint.getTextSize());
        }
        this.mDebugTextUsedYPositions.add(Integer.valueOf(i));
        return i;
    }

    private void customOverScrollBy(int i, int i2, int i3, int i4) {
        int i5 = i2 + i;
        int i6 = -i4;
        int i7 = i4 + i3;
        boolean z = true;
        if (i5 > i7) {
            i5 = i7;
        } else if (i5 < i6) {
            i5 = i6;
        } else {
            z = false;
        }
        onCustomOverScrolled(i5, z);
    }

    private void debugShadeLog(String str) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger == null) {
            return;
        }
        notificationStackScrollLogger.logShadeDebugEvent(str);
    }

    private void drawDebugInfo(Canvas canvas, int i, int i2, String str) {
        this.mDebugPaint.setColor(i2);
        float f = i;
        canvas.drawLine(0.0f, f, getWidth(), f, this.mDebugPaint);
        canvas.drawText(str, 0.0f, computeDebugYTextPosition(i), this.mDebugPaint);
    }

    private void endDrag() {
        setIsBeingDragged(false);
        recycleVelocityTracker();
        if (getCurrentOverScrollAmount(true) > 0.0f) {
            setOverScrollAmount(0.0f, true, true);
        }
        if (getCurrentOverScrollAmount(false) > 0.0f) {
            setOverScrollAmount(0.0f, false, true);
        }
    }

    private void ensureRemovedFromTransientContainer(View view) {
        if (view.getParent() == null || !(view instanceof ExpandableView)) {
            return;
        }
        ((ExpandableView) view).removeFromTransientContainerForAdditionTo(this);
    }

    private void finalizeClearAllAnimation() {
        if (this.mAmbientState.isClearAllInProgress()) {
            setClearAllInProgress(false);
            if (this.mShadeNeedsToClose) {
                this.mShadeNeedsToClose = false;
                if (this.mIsExpanded) {
                    this.mClearAllFinishedWhilePanelExpandedRunnable.run();
                }
            }
        }
    }

    private void focusNextViewIfFocused(View view) {
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            if (expandableNotificationRow.shouldRefocusOnDismiss()) {
                View childAfterViewWhenDismissed = expandableNotificationRow.getChildAfterViewWhenDismissed();
                if (childAfterViewWhenDismissed == null) {
                    View groupParentWhenDismissed = expandableNotificationRow.getGroupParentWhenDismissed();
                    childAfterViewWhenDismissed = getFirstChildBelowTranlsationY(groupParentWhenDismissed != null ? groupParentWhenDismissed.getTranslationY() : view.getTranslationY(), true);
                }
                if (childAfterViewWhenDismissed != null) {
                    childAfterViewWhenDismissed.requestAccessibilityFocus();
                }
            }
        }
    }

    private void generateActivateEvent() {
        if (this.mActivateNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 4));
        }
        this.mActivateNeedsAnimation = false;
    }

    private void generateAllAnimationEvents() {
        generateHeadsUpAnimationEvents();
        generateChildRemovalEvents();
        generateChildAdditionEvents();
        generatePositionChangeEvents();
        generateTopPaddingEvent();
        generateActivateEvent();
        generateHideSensitiveEvent();
        generateGoToFullShadeEvent();
        generateViewResizeEvent();
        generateGroupExpansionEvent();
        generateAnimateEverythingEvent();
    }

    private void generateAnimateEverythingEvent() {
        if (this.mEverythingNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 15));
        }
        this.mEverythingNeedsAnimation = false;
    }

    private void generateChildAdditionEvents() {
        for (ExpandableView expandableView : this.mChildrenToAddAnimated) {
            if (this.mFromMoreCardAdditions.contains(expandableView)) {
                this.mAnimationEvents.add(new AnimationEvent(expandableView, 0, 360L));
            } else {
                this.mAnimationEvents.add(new AnimationEvent(expandableView, 0));
            }
        }
        this.mChildrenToAddAnimated.clear();
        this.mFromMoreCardAdditions.clear();
    }

    private void generateChildRemovalEvents() {
        boolean z;
        ArrayList arrayList = this.mChildrenToRemoveAnimated;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ExpandableView expandableView = (ExpandableView) obj;
            boolean zContains = this.mSwipedOutViews.contains(expandableView);
            float translationY = expandableView.getTranslationY();
            boolean z2 = expandableView instanceof ExpandableNotificationRow;
            if (z2) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                if (expandableNotificationRow.isRemoved() && expandableNotificationRow.wasChildInGroupWhenRemoved()) {
                    translationY = expandableNotificationRow.getTranslationWhenRemoved();
                    z = false;
                } else {
                    z = true;
                }
                zContains |= isFullySwipedOut(expandableNotificationRow);
            } else if (expandableView instanceof MediaContainerView) {
                zContains = true;
                z = true;
            } else {
                z = true;
            }
            if (!zContains) {
                Rect clipBounds = expandableView.getClipBounds();
                zContains = clipBounds != null && clipBounds.height() == 0;
                if (zContains) {
                    expandableView.removeFromTransientContainer();
                }
            }
            AnimationEvent animationEvent = new AnimationEvent(expandableView, zContains ? 2 : 1);
            animationEvent.viewAfterChangingView = getFirstChildBelowTranlsationY(translationY, z);
            this.mAnimationEvents.add(animationEvent);
            this.mSwipedOutViews.remove(expandableView);
            if (this.mDebugRemoveAnimation) {
                Log.d("StackScroller", "created Remove Event - SwipedOut: " + zContains + " " + (z2 ? ((ExpandableNotificationRow) expandableView).getEntry().getKey() : ""));
            }
        }
        this.mChildrenToRemoveAnimated.clear();
    }

    private void generateGoToFullShadeEvent() {
        if (this.mGoToFullShadeNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 7));
        }
        this.mGoToFullShadeNeedsAnimation = false;
    }

    private void generateGroupExpansionEvent() {
        if (this.mExpandedGroupView != null) {
            this.mAnimationEvents.add(new AnimationEvent(this.mExpandedGroupView, 10));
            this.mExpandedGroupView = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void generateHeadsUpAnimationEvents() {
        /*
            Method dump skipped, instruction units count: 252
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.generateHeadsUpAnimationEvents():void");
    }

    private void generateHideSensitiveEvent() {
        if (this.mHideSensitiveNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 8));
        }
        this.mHideSensitiveNeedsAnimation = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void generatePositionChangeEvents() {
        /*
            r10 = this;
            java.util.ArrayList r0 = r10.mChildrenChangingPositions
            int r1 = r0.size()
            r2 = 0
            r3 = r2
        L8:
            if (r3 >= r1) goto L4c
            java.lang.Object r4 = r0.get(r3)
            int r3 = r3 + 1
            com.android.systemui.statusbar.notification.row.ExpandableView r4 = (com.android.systemui.statusbar.notification.row.ExpandableView) r4
            boolean r5 = r4 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r5 == 0) goto L31
            r5 = r4
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r5 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r5
            com.android.systemui.statusbar.notification.collection.NotificationEntry r6 = r5.getEntry()
            boolean r6 = r6.isMarkedForUserTriggeredMovement()
            if (r6 == 0) goto L31
            r6 = 500(0x1f4, float:7.0E-43)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.getEntry()
            r5.markForUserTriggeredMovement(r2)
            goto L32
        L31:
            r6 = 0
        L32:
            r5 = 6
            if (r6 != 0) goto L3b
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r6 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            r6.<init>(r4, r5)
            goto L46
        L3b:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent r7 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$AnimationEvent
            int r6 = r6.intValue()
            long r8 = (long) r6
            r7.<init>(r4, r5, r8)
            r6 = r7
        L46:
            java.util.ArrayList r4 = r10.mAnimationEvents
            r4.add(r6)
            goto L8
        L4c:
            java.util.ArrayList r10 = r10.mChildrenChangingPositions
            r10.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.generatePositionChangeEvents():void");
    }

    private void generateTopPaddingEvent() {
        if (this.mTopPaddingNeedsAnimation) {
            this.mAnimationEvents.add(new AnimationEvent(null, 3));
        }
        this.mTopPaddingNeedsAnimation = false;
    }

    private void generateViewResizeEvent() {
        if (this.mNeedViewResizeAnimation) {
            ArrayList arrayList = this.mAnimationEvents;
            int size = arrayList.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    this.mAnimationEvents.add(new AnimationEvent(null, 9));
                    break;
                }
                Object obj = arrayList.get(i);
                i++;
                int i2 = ((AnimationEvent) obj).animationType;
                if (i2 == 13 || i2 == 12) {
                    break;
                }
            }
        }
        this.mNeedViewResizeAnimation = false;
    }

    private ExpandableView getChildAtIndex(int i) {
        return (ExpandableView) getChildAt(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ExpandableView getChildAtPosition(float f, float f2) {
        return getChildAtPosition(f, f2, true, true, true);
    }

    private List getChildrenWithBackground() {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView childAtIndex = getChildAtIndex(i);
            if (childAtIndex.getVisibility() != 8 && !(childAtIndex instanceof StackScrollerDecorView)) {
                arrayList.add(childAtIndex);
            }
        }
        return arrayList;
    }

    private View getFirstChildBelowTranlsationY(float f, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                float translationY = childAt.getTranslationY();
                if (translationY >= f) {
                    return childAt;
                }
                if (!z && (childAt instanceof ExpandableNotificationRow)) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                    if (expandableNotificationRow.isSummaryWithChildren() && expandableNotificationRow.areChildrenExpanded()) {
                        List attachedChildren = expandableNotificationRow.getAttachedChildren();
                        int size = attachedChildren.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) attachedChildren.get(i2);
                            if (expandableNotificationRow2.getTranslationY() + translationY >= f) {
                                return expandableNotificationRow2;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private NotificationSection getFirstVisibleSection() {
        for (NotificationSection notificationSection : this.mSections) {
            if (notificationSection.getFirstVisibleChild() != null) {
                return notificationSection;
            }
        }
        return null;
    }

    private int getImeInset() {
        return Math.max(0, this.mImeInset - ((getRootView().getHeight() - getHeight()) - getLocationOnScreen()[1]));
    }

    private int getIntrinsicHeight(View view) {
        return view instanceof ExpandableView ? ((ExpandableView) view).getIntrinsicHeight() : view.getHeight();
    }

    private ExpandableView getLastChildWithBackground() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ExpandableView childAtIndex = getChildAtIndex(childCount);
            if (childAtIndex.getVisibility() != 8 && !(childAtIndex instanceof StackScrollerDecorView)) {
                return childAtIndex;
            }
        }
        return null;
    }

    private NotificationSection getLastVisibleSection() {
        for (int length = this.mSections.length - 1; length >= 0; length--) {
            NotificationSection notificationSection = this.mSections[length];
            if (notificationSection.getLastVisibleChild() != null) {
                return notificationSection;
            }
        }
        return null;
    }

    private int getLayoutHeight() {
        return Math.min(this.mMaxLayoutHeight, this.mCurrentStackHeight);
    }

    private ArrayList getRowsToDismissInBackend(int i) {
        int childCount = getChildCount();
        ArrayList arrayList = new ArrayList(childCount);
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                if (includeChildInClearAll(expandableNotificationRow, i)) {
                    arrayList.add(expandableNotificationRow);
                }
                List<ExpandableNotificationRow> attachedChildren = expandableNotificationRow.getAttachedChildren();
                if (isVisible(expandableNotificationRow) && attachedChildren != null) {
                    for (ExpandableNotificationRow expandableNotificationRow2 : attachedChildren) {
                        if (includeChildInClearAll(expandableNotificationRow, i)) {
                            arrayList.add(expandableNotificationRow2);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private float getRubberBandFactor(boolean z) {
        if (!z) {
            return RUBBER_BAND_FACTOR_NORMAL;
        }
        if (this.mExpandedInThisMotion) {
            return 0.15f;
        }
        if (this.mIsExpansionChanging || this.mPanelTracking) {
            return 0.21f;
        }
        if (!this.mScrolledToTopOnFirstDown || this.mShouldUseSplitNotificationShade) {
            return RUBBER_BAND_FACTOR_NORMAL;
        }
        return 1.0f;
    }

    private int getScrollAmountToScrollBoundary() {
        return this.mShouldUseSplitNotificationShade ? this.mSidePaddings : getTopPadding() - this.mQsScrollBoundaryPosition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getScrollRange() {
        int topHeadsUpPinnedHeight = this.mContentHeight;
        if (!isExpanded() && this.mInHeadsUpPinnedMode) {
            topHeadsUpPinnedHeight = this.mHeadsUpInset + getTopHeadsUpPinnedHeight();
        }
        int iMax = Math.max(0, topHeadsUpPinnedHeight - this.mMaxLayoutHeight);
        int imeInset = getImeInset();
        int iMin = iMax + Math.min(imeInset, Math.max(0, topHeadsUpPinnedHeight - (getHeight() - imeInset)));
        return iMin > 0 ? Math.max(getScrollAmountToScrollBoundary(), iMin) : iMin;
    }

    private int getTopHeadsUpPinnedHeight() {
        NotificationEntry groupSummary;
        ExpandableNotificationRow row = this.mTopHeadsUpRow;
        if (row == null) {
            return 0;
        }
        if (row.isChildInGroup() && (groupSummary = this.mGroupMembershipManager.getGroupSummary(row.getEntry())) != null) {
            row = groupSummary.getRow();
        }
        return row.getPinnedHeadsUpHeight();
    }

    private float getTouchSlop(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mSlopMultiplier : this.mTouchSlop;
    }

    private ArrayList getVisibleViewsToAnimateAway(int i, boolean z) {
        int childCount = getChildCount();
        ArrayList arrayList = new ArrayList(childCount);
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((childAt instanceof SectionHeaderView) && z) {
                arrayList.add(childAt);
            }
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                if (isVisible(expandableNotificationRow) && includeChildInClearAll(expandableNotificationRow, i)) {
                    arrayList.add(expandableNotificationRow);
                }
                if (areChildrenVisible(expandableNotificationRow)) {
                    for (ExpandableNotificationRow expandableNotificationRow2 : expandableNotificationRow.getAttachedChildren()) {
                        if (isVisible(expandableNotificationRow2) && includeChildInClearAll(expandableNotificationRow2, i)) {
                            arrayList.add(expandableNotificationRow2);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private boolean includeChildInClearAll(ExpandableNotificationRow expandableNotificationRow, int i) {
        return canChildBeCleared(expandableNotificationRow) && matchesSelection(expandableNotificationRow, i);
    }

    private void inflateEmptyShadeView() {
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        EmptyShadeView emptyShadeView2 = (EmptyShadeView) LayoutInflater.from(((ViewGroup) this).mContext).inflate(R$layout.status_bar_no_notifications, (ViewGroup) this, false);
        emptyShadeView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$inflateEmptyShadeView$8(view);
            }
        });
        setEmptyShadeView(emptyShadeView2);
        emptyShadeView2.setVisible(emptyShadeView != null && emptyShadeView.isVisible(), false);
        updateEmptyShadeViewResources(emptyShadeView == null ? R$string.empty_shade_text : emptyShadeView.getTextResource(), emptyShadeView == null ? 0 : emptyShadeView.getFooterTextResource(), emptyShadeView != null ? emptyShadeView.getFooterIconResource() : 0);
    }

    private void initOrResetVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private boolean isChildInGroup(View view) {
        return (view instanceof ExpandableNotificationRow) && this.mGroupMembershipManager.isChildInGroup(((ExpandableNotificationRow) view).getEntry());
    }

    private boolean isClickedHeadsUp(View view) {
        return HeadsUpUtil.isClickedHeadsUpNotification(view);
    }

    private boolean isCurrentlyAnimating() {
        return this.mStateAnimator.isRunning();
    }

    private boolean isHeadsUp(View view) {
        if (view instanceof ExpandableNotificationRow) {
            return ((ExpandableNotificationRow) view).isHeadsUp();
        }
        return false;
    }

    private boolean isHeadsUpTransition() {
        return this.mAmbientState.getTrackedHeadsUpRow() != null;
    }

    private boolean isInContentBounds(MotionEvent motionEvent) {
        return isInContentBounds(motionEvent.getY());
    }

    public static boolean isPinnedHeadsUp(View view) {
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            if (expandableNotificationRow.isHeadsUp() && expandableNotificationRow.isPinned()) {
                return true;
            }
        }
        return false;
    }

    private boolean isRubberbanded(boolean z) {
        return !z || this.mExpandedInThisMotion || this.mIsExpansionChanging || this.mPanelTracking || !this.mScrolledToTopOnFirstDown;
    }

    private boolean isScrollingEnabled() {
        return this.mScrollingEnabled;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearNotifications$7(final ArrayList arrayList, final int i, Boolean bool) {
        if (bool.booleanValue()) {
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$clearNotifications$6(arrayList, i);
                }
            });
        } else {
            lambda$clearNotifications$6(arrayList, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflateEmptyShadeView$8(View view) {
        this.mActivityStarter.startActivity(this.mController.isHistoryEnabled() ? new Intent("android.settings.NOTIFICATION_HISTORY") : new Intent("android.settings.NOTIFICATION_SETTINGS"), true, true, 536870912);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0() {
        updateViewShadows();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onScrollTouch$2() {
        this.mFlingAfterUpEvent = false;
        InteractionJankMonitor.getInstance().end(2);
        setFinishScrollingCallback(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFooterView$3(FooterView footerView, View view) {
        clearNotifications(0, true);
        footerView.setClearAllButtonVisible(false, true);
    }

    private void logAddTransientChild(ExpandableView expandableView, ViewGroup viewGroup) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null && (expandableView instanceof ExpandableNotificationRow)) {
            if (viewGroup instanceof NotificationChildrenContainer) {
                notificationStackScrollLogger.addTransientChildNotificationToChildContainer(((ExpandableNotificationRow) expandableView).getEntry(), ((NotificationChildrenContainer) viewGroup).getContainingNotification().getEntry());
            } else if (viewGroup instanceof NotificationStackScrollLayout) {
                notificationStackScrollLogger.addTransientChildNotificationToNssl(((ExpandableNotificationRow) expandableView).getEntry());
            } else {
                notificationStackScrollLogger.addTransientChildNotificationToViewGroup(((ExpandableNotificationRow) expandableView).getEntry(), viewGroup);
            }
        }
    }

    private void logEmptySpaceClick(MotionEvent motionEvent, boolean z, int i, boolean z2) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger == null) {
            return;
        }
        notificationStackScrollLogger.logEmptySpaceClick(z, i, z2, MotionEvent.actionToString(motionEvent.getActionMasked()));
    }

    private void logHunAnimationEventAdded(ExpandableNotificationRow expandableNotificationRow, int i) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger == null) {
            return;
        }
        notificationStackScrollLogger.hunAnimationEventAdded(expandableNotificationRow.getEntry(), i);
    }

    private void logHunAnimationSkipped(ExpandableNotificationRow expandableNotificationRow, String str) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger == null) {
            return;
        }
        notificationStackScrollLogger.hunAnimationSkipped(expandableNotificationRow.getEntry(), str);
    }

    private void logHunSkippedForUnexpectedState(ExpandableNotificationRow expandableNotificationRow, boolean z, boolean z2) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger == null) {
            return;
        }
        notificationStackScrollLogger.hunSkippedForUnexpectedState(expandableNotificationRow.getEntry(), z, z2);
    }

    static boolean matchesSelection(ExpandableNotificationRow expandableNotificationRow, int i) {
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return expandableNotificationRow.getEntry().getBucket() < 6;
        }
        if (i == 2) {
            return expandableNotificationRow.getEntry().getBucket() == 6;
        }
        throw new IllegalArgumentException("Unknown selection: " + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyHeightChangeListener(ExpandableView expandableView) {
        notifyHeightChangeListener(expandableView, false);
    }

    private void notifyHeightChangeListener(ExpandableView expandableView, boolean z) {
        ExpandableView.OnHeightChangedListener onHeightChangedListener = this.mOnHeightChangedListener;
        if (onHeightChangedListener != null) {
            onHeightChangedListener.onHeightChanged(expandableView, z);
        }
        Runnable runnable = this.mOnHeightChangedRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }

    private void notifyOverscrollTopListener(float f, boolean z) {
        this.mExpandHelper.onlyObserveMovements(f > 1.0f);
        if (this.mDontReportNextOverScroll) {
            this.mDontReportNextOverScroll = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onClearAllAnimationsEnd, reason: merged with bridge method [inline-methods] */
    public void lambda$clearNotifications$6(List list, int i) {
        InteractionJankMonitor.getInstance().end(62);
        ClearAllAnimationListener clearAllAnimationListener = this.mClearAllAnimationListener;
        if (clearAllAnimationListener != null) {
            clearAllAnimationListener.onAnimationEnd(list, i);
        }
    }

    private void onCustomOverScrolled(int i, boolean z) {
        if (this.mScroller.isFinished()) {
            setOwnScrollY(i);
            return;
        }
        setOwnScrollY(i);
        if (z) {
            springBack();
            return;
        }
        float currentOverScrollAmount = getCurrentOverScrollAmount(true);
        if (this.mOwnScrollY < 0) {
            notifyOverscrollTopListener(-r0, isRubberbanded(true));
        } else {
            notifyOverscrollTopListener(currentOverScrollAmount, isRubberbanded(true));
        }
    }

    private void onDrawDebug(Canvas canvas) {
        Set set = this.mDebugTextUsedYPositions;
        if (set == null) {
            this.mDebugTextUsedYPositions = new HashSet();
        } else {
            set.clear();
        }
        this.mDebugPaint.setColor(-12303292);
        canvas.drawPath(this.mRoundedClipPath, this.mDebugPaint);
        drawDebugInfo(canvas, 0, -65536, "y = 0");
        int topPadding = getTopPadding();
        drawDebugInfo(canvas, topPadding, -65536, "getTopPadding() = " + topPadding);
        int layoutHeight = getLayoutHeight();
        drawDebugInfo(canvas, layoutHeight, -256, "getLayoutHeight() = " + layoutHeight);
        int i = this.mMaxLayoutHeight;
        drawDebugInfo(canvas, i, -65281, "mMaxLayoutHeight = " + i);
        if (this.mKeyguardBottomPadding >= 0.0f) {
            int height = getHeight() - ((int) this.mKeyguardBottomPadding);
            drawDebugInfo(canvas, height, -65536, "getHeight() - mKeyguardBottomPadding = " + height);
        }
        int height2 = getHeight() - getEmptyBottomMargin();
        drawDebugInfo(canvas, height2, -16711936, "getHeight() - getEmptyBottomMargin() = " + height2);
        int stackY = (int) this.mAmbientState.getStackY();
        drawDebugInfo(canvas, stackY, -16711681, "mAmbientState.getStackY() = " + stackY);
        int stackY2 = (int) (this.mAmbientState.getStackY() + this.mAmbientState.getStackHeight());
        drawDebugInfo(canvas, stackY2, -3355444, "mAmbientState.getStackY() + mAmbientState.getStackHeight() = " + stackY2);
        int stackY3 = (int) (this.mAmbientState.getStackY() + this.mIntrinsicContentHeight);
        drawDebugInfo(canvas, stackY3, -256, "mAmbientState.getStackY() + mIntrinsicContentHeight = " + stackY3);
        int i2 = this.mContentHeight;
        drawDebugInfo(canvas, i2, -65281, "mContentHeight = " + i2);
        int i3 = this.mRoundedRectClippingBottom;
        drawDebugInfo(canvas, i3, -12303292, "mRoundedRectClippingBottom) = " + i3);
    }

    private void onJustBeforeDraw() {
    }

    private void onOverScrollFling(boolean z, int i) {
        this.mDontReportNextOverScroll = true;
        setOverScrollAmount(0.0f, true, false);
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mLastMotionY = (int) motionEvent.getY(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    private void onViewAddedInternal(ExpandableView expandableView) {
        updateHideSensitiveForChild(expandableView);
        expandableView.setOnHeightChangedListener(this.mOnChildHeightChangedListener);
        boolean z = expandableView instanceof ExpandableNotificationRow;
        if (z) {
            ((ExpandableNotificationRow) expandableView).getEntry().addOnSensitivityChangedListener(this.mOnChildSensitivityChangedListener);
        }
        generateAddAnimation(expandableView, false);
        updateAnimationState(expandableView);
        updateChronometerForChild(expandableView);
        if (z) {
            ((ExpandableNotificationRow) expandableView).setDismissUsingRowTranslationX(this.mDismissUsingRowTranslationX);
        }
    }

    private void onViewRemovedInternal(ExpandableView expandableView, ViewGroup viewGroup) {
        if (this.mChangePositionInProgress) {
            return;
        }
        expandableView.setOnHeightChangedListener(null);
        boolean z = expandableView instanceof ExpandableNotificationRow;
        if (z) {
            ((ExpandableNotificationRow) expandableView).getEntry().removeOnSensitivityChangedListener(this.mOnChildSensitivityChangedListener);
        }
        if (viewGroup == null || !generateRemoveAnimation(expandableView)) {
            this.mSwipedOutViews.remove(expandableView);
            if (z) {
                ((ExpandableNotificationRow) expandableView).removeChildrenWithKeepInParent();
            }
        } else if (!this.mSwipedOutViews.contains(expandableView) || !isFullySwipedOut(expandableView)) {
            logAddTransientChild(expandableView, viewGroup);
            expandableView.addToTransientContainer(viewGroup, 0);
        }
        updateAnimationState(false, expandableView);
        focusNextViewIfFocused(expandableView);
    }

    private float overScrollDown(int i) {
        int iMin = Math.min(i, 0);
        float currentOverScrollAmount = getCurrentOverScrollAmount(false);
        float f = iMin + currentOverScrollAmount;
        if (currentOverScrollAmount > 0.0f) {
            setOverScrollAmount(f, false, false);
        }
        if (f >= 0.0f) {
            f = 0.0f;
        }
        float f2 = this.mOwnScrollY + f;
        if (f2 >= 0.0f) {
            return f;
        }
        setOverScrolledPixels(getCurrentOverScrolledPixels(true) - f2, true, false);
        setOwnScrollY(0);
        return 0.0f;
    }

    private float overScrollUp(int i, int i2) {
        int iMax = Math.max(i, 0);
        float currentOverScrollAmount = getCurrentOverScrollAmount(true);
        float f = currentOverScrollAmount - iMax;
        if (currentOverScrollAmount > 0.0f) {
            setOverScrollAmount(f, true, false);
        }
        float f2 = f < 0.0f ? -f : 0.0f;
        float f3 = this.mOwnScrollY + f2;
        float f4 = i2;
        if (f3 <= f4) {
            return f2;
        }
        if (!this.mExpandedInThisMotion) {
            setOverScrolledPixels((getCurrentOverScrolledPixels(false) + f3) - f4, false, false);
        }
        setOwnScrollY(i2);
        return 0.0f;
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void reinitView() {
        initView(getContext(), this.mSwipeHelper, this.mNotificationStackSizeCalculator);
    }

    private boolean removeRemovedChildFromHeadsUpChangeAnimations(View view) {
        boolean z = false;
        for (Pair pair : this.mHeadsUpChangeAnimations) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) pair.first;
            boolean zBooleanValue = ((Boolean) pair.second).booleanValue();
            if (view == expandableNotificationRow) {
                this.mTmpList.add(pair);
                z |= zBooleanValue;
            }
        }
        if (z) {
            this.mHeadsUpChangeAnimations.removeAll(this.mTmpList);
            ((ExpandableNotificationRow) view).setHeadsUpAnimatingAway(false);
        }
        this.mTmpList.clear();
        return z && this.mAddedHeadsUpChildren.contains(view);
    }

    private void requestAnimationOnViewResize(ExpandableNotificationRow expandableNotificationRow) {
        if (this.mAnimationsEnabled) {
            if (this.mIsExpanded || (expandableNotificationRow != null && expandableNotificationRow.isPinned())) {
                this.mNeedViewResizeAnimation = true;
                this.mNeedsAnimation = true;
            }
        }
    }

    private void resetExposedMenuView(boolean z, boolean z2) {
    }

    private void runAnimationFinishedRunnables() {
        Iterator it = this.mAnimationFinishedRunnables.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.mAnimationFinishedRunnables.clear();
    }

    private void setMaxLayoutHeight(int i) {
        this.mMaxLayoutHeight = i;
        updateAlgorithmHeightAndPadding();
    }

    private void setMaxOverScrollFromCurrentVelocity() {
        float currVelocity = this.mScroller.getCurrVelocity();
        if (currVelocity >= this.mMinimumVelocity) {
            this.mMaxOverScroll = (Math.abs(currVelocity) / 1000.0f) * this.mOverflingDistance;
        }
    }

    private void setOverScrollAmountInternal(float f, boolean z, boolean z2, boolean z3) {
        float fMax = Math.max(0.0f, f);
        if (z2) {
            this.mStateAnimator.animateOverScrollToAmount(fMax, z, z3);
            return;
        }
        setOverScrolledPixels(fMax / getRubberBandFactor(z), z);
        this.mAmbientState.setOverScrollAmount(fMax, z);
        if (z) {
            notifyOverscrollTopListener(fMax, z3);
        }
        requestChildrenUpdate();
    }

    private void setOverScrolledPixels(float f, boolean z) {
        if (z) {
            this.mOverScrolledTopPixels = f;
        } else {
            this.mOverScrolledBottomPixels = f;
        }
    }

    private void setOwnScrollY(int i, boolean z) {
        int i2;
        if (this.mAmbientState.isClosing() || this.mAmbientState.isClearAllInProgress() || i == (i2 = this.mOwnScrollY)) {
            return;
        }
        int i3 = ((ViewGroup) this).mScrollX;
        onScrollChanged(i3, i, i3, i2);
        this.mOwnScrollY = i;
        this.mAmbientState.setScrollY(i);
        updateOnScrollChange();
    }

    private boolean shouldOverScrollFling(int i) {
        float currentOverScrollAmount = getCurrentOverScrollAmount(true);
        if (!this.mScrolledToTopOnFirstDown || this.mExpandedInThisMotion || this.mShouldUseSplitNotificationShade) {
            return false;
        }
        return i > this.mMinimumVelocity || (currentOverScrollAmount > this.mMinTopOverScrollToEscape && i > 0);
    }

    private boolean shouldShowDismissView() {
        FooterViewRefactor.assertInLegacyMode();
        return this.mController.hasActiveClearableNotifications(0);
    }

    private boolean shouldShowFooterView(boolean z) {
        FooterViewRefactor.assertInLegacyMode();
        if ((z || this.mController.getVisibleNotificationCount() > 0) && this.mIsCurrentUserSetup && !onKeyguard()) {
            return ((this.mQsExpansionFraction == 1.0f && this.mQsFullScreen) || this.mIsRemoteInputActive) ? false : true;
        }
        return false;
    }

    private void snapViewIfNeeded(NotificationEntry notificationEntry) {
        ExpandableNotificationRow row = notificationEntry.getRow();
        if (this.mIsExpanded) {
            return;
        }
        isPinnedHeadsUp(row);
    }

    private void springBack() {
        float f;
        boolean z;
        int scrollRange = getScrollRange();
        int i = this.mOwnScrollY;
        boolean z2 = i <= 0;
        boolean z3 = i >= scrollRange;
        if (z2 || z3) {
            if (z2) {
                f = -i;
                setOwnScrollY(0);
                this.mDontReportNextOverScroll = true;
                z = true;
            } else {
                setOwnScrollY(scrollRange);
                f = i - scrollRange;
                z = false;
            }
            setOverScrollAmount(f, z, false);
            setOverScrollAmount(0.0f, z, true);
            this.mScroller.forceFinished(true);
        }
    }

    private void startAnimationToState() {
        if (this.mNeedsAnimation) {
            generateAllAnimationEvents();
            this.mNeedsAnimation = false;
        }
        if (!this.mAnimationEvents.isEmpty() || isCurrentlyAnimating()) {
            setAnimationRunning(true);
            this.mStateAnimator.startAnimationForEvents(this.mAnimationEvents, this.mGoToFullShadeDelay);
            this.mAnimationEvents.clear();
            updateViewShadows();
        } else {
            applyCurrentState();
        }
        this.mGoToFullShadeDelay = 0L;
    }

    private int targetScrollForView(ExpandableView expandableView, int i) {
        return (((i + expandableView.getIntrinsicHeight()) + getImeInset()) - getHeight()) + ((isExpanded() || !isPinnedHeadsUp(expandableView)) ? getTopPadding() : this.mHeadsUpInset);
    }

    private void updateAlgorithmHeightAndPadding() {
        this.mAmbientState.setLayoutHeight(getLayoutHeight());
        this.mAmbientState.setLayoutMaxHeight(this.mMaxLayoutHeight);
        updateAlgorithmLayoutMinHeight();
    }

    private void updateAlgorithmLayoutMinHeight() {
        this.mAmbientState.setLayoutMinHeight((this.mQsFullScreen || isHeadsUpTransition()) ? getLayoutMinHeight() : 0);
    }

    private void updateAnimationState(boolean z, View view) {
        if (view instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) view).setAnimationRunning(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateChildren() {
        Trace.beginSection("NSSL#updateChildren");
        updateScrollStateForAddedChildren();
        this.mAmbientState.setCurrentScrollVelocity(this.mScroller.isFinished() ? 0.0f : this.mScroller.getCurrVelocity());
        this.mStackScrollAlgorithm.resetViewStates(this.mAmbientState, getSpeedBumpIndex());
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int stackScrollHeightExtent = (int) this.mStackScrollAlgorithm.getStackScrollHeightExtent();
        if (layoutParams.height != stackScrollHeightExtent) {
            layoutParams.height = stackScrollHeightExtent;
            setLayoutParams(layoutParams);
            setMinimumHeight(stackScrollHeightExtent);
        }
        if (isCurrentlyAnimating() || this.mNeedsAnimation) {
            startAnimationToState();
        } else {
            applyCurrentState();
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateContentHeight() {
        float f = this.mAmbientState.isOnKeyguard() ? 0.0f : this.mMinimumPaddings;
        FooterView footerView = this.mFooterView;
        int intrinsicHeight = footerView != null ? footerView.getIntrinsicHeight() : 0;
        float fComputeHeight = ((int) f) + ((int) this.mNotificationStackSizeCalculator.computeHeight(this, this.mMaxDisplayedNotifications, 0.0f));
        this.mIntrinsicContentHeight = fComputeHeight;
        this.mScrollViewFields.sendStackHeight(intrinsicHeight + fComputeHeight);
        this.mContentHeight = (int) (fComputeHeight + Math.max(this.mIntrinsicPadding, getTopPadding()) + this.mBottomPadding);
        updateScrollability();
        clampScrollPosition();
        this.mAmbientState.setContentHeight(this.mContentHeight);
        this.mAmbientState.setStackEndHeight(this.mContentHeight);
        this.mAmbientState.setStackHeight(this.mContentHeight);
    }

    private void updateDismissBehavior() {
        boolean z = !this.mShouldUseSplitNotificationShade || this.mIsExpanded;
        if (this.mDismissUsingRowTranslationX != z) {
            this.mDismissUsingRowTranslationX = z;
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) childAt).setDismissUsingRowTranslationX(z);
                }
            }
        }
    }

    private void updateEmptyShadeViewResources(int i, int i2, int i3) {
        if (this.mEmptyShadeView.getTextResource() != i) {
            this.mEmptyShadeView.setText(i);
        }
        if (this.mEmptyShadeView.getFooterTextResource() != i2) {
            this.mEmptyShadeView.setFooterText(i2);
        }
        if (this.mEmptyShadeView.getFooterIconResource() != i3) {
            this.mEmptyShadeView.setFooterIcon(i3);
        }
        if (i3 == 0 && i2 == 0) {
            this.mEmptyShadeView.setFooterVisibility(8);
        } else {
            this.mEmptyShadeView.setFooterVisibility(0);
        }
    }

    private void updateFirstAndLastBackgroundViews() {
        ExpandableView lastChildWithBackground = getLastChildWithBackground();
        this.mSectionsManager.updateFirstAndLastViewsForAllSections(this.mSections, getChildrenWithBackground());
        this.mAmbientState.setLastVisibleBackgroundChild(lastChildWithBackground);
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForcedScroll() {
        View view = this.mForcedScroll;
        if (view != null && (!view.hasFocus() || !this.mForcedScroll.isAttachedToWindow())) {
            this.mForcedScroll = null;
        }
        View view2 = this.mForcedScroll;
        if (view2 != null) {
            ExpandableView expandableView = (ExpandableView) view2;
            int positionInLinearLayout = getPositionInLinearLayout(expandableView);
            int iTargetScrollForView = targetScrollForView(expandableView, positionInLinearLayout);
            int intrinsicHeight = positionInLinearLayout + expandableView.getIntrinsicHeight();
            int iMax = Math.max(0, Math.min(iTargetScrollForView, getScrollRange()));
            int i = this.mOwnScrollY;
            if (i < iMax || intrinsicHeight < i) {
                setOwnScrollY(iMax);
            }
        }
    }

    private void updateForwardAndBackwardScrollability() {
        boolean z = this.mScrollable && !this.mScrollAdapter.isScrolledToBottom();
        boolean z2 = this.mScrollable && !this.mScrollAdapter.isScrolledToTop();
        boolean z3 = (z == this.mForwardScrollable && z2 == this.mBackwardScrollable) ? false : true;
        this.mForwardScrollable = z;
        this.mBackwardScrollable = z2;
        if (z3) {
            sendAccessibilityEvent(2048);
        }
    }

    private void updateHideSensitiveForChild(ExpandableView expandableView) {
        expandableView.setHideSensitiveForIntrinsicHeight(this.mAmbientState.isHideSensitive());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateImeInset(WindowInsets windowInsets) {
        this.mImeInset = windowInsets.getInsets(WindowInsets.Type.ime()).bottom;
        FooterView footerView = this.mFooterView;
        if (footerView != null && footerView.getViewState() != null) {
            ((FooterView.FooterViewState) this.mFooterView.getViewState()).resetY |= this.mImeInset > 0;
        }
        if (this.mImeInset <= 0) {
            this.mScroller.abortAnimation();
            setOwnScrollY(0, true);
            return;
        }
        if (this.mForcedScroll != null) {
            updateForcedScroll();
        }
        int scrollRange = getScrollRange();
        if (this.mOwnScrollY > scrollRange) {
            setOwnScrollY(scrollRange);
        }
    }

    private void updateOnScrollChange() {
        Consumer consumer = this.mScrollListener;
        if (consumer != null) {
            consumer.accept(Integer.valueOf(this.mOwnScrollY));
        }
        updateForwardAndBackwardScrollability();
        requestChildrenUpdate();
    }

    private void updateOwnTranslationZ() {
        ExpandableView firstChildNotGone;
        setTranslationZ((this.mKeyguardBypassEnabled && this.mAmbientState.isHiddenAtAll() && (firstChildNotGone = getFirstChildNotGone()) != null && firstChildNotGone.showingPulsing()) ? firstChildNotGone.getTranslationZ() : 0.0f);
    }

    private void updateScrollPositionOnExpandInBottom(ExpandableView expandableView) {
        if (!(expandableView instanceof ExpandableNotificationRow) || onKeyguard()) {
            return;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        if (!expandableNotificationRow.isUserLocked() || expandableNotificationRow == getFirstChildNotGone() || expandableNotificationRow.isSummaryWithChildren()) {
            return;
        }
        float translationY = expandableNotificationRow.getTranslationY() + expandableNotificationRow.getActualHeight();
        if (expandableNotificationRow.isChildInGroup()) {
            translationY += expandableNotificationRow.getNotificationParent().getTranslationY();
        }
        int stackTranslation = this.mMaxLayoutHeight + ((int) getStackTranslation());
        getLastVisibleSection();
        float f = stackTranslation;
        if (translationY > f) {
            setOwnScrollY((int) ((this.mOwnScrollY + translationY) - f));
            this.mDisallowScrollingInThisMotion = true;
        }
    }

    private void updateScrollStateForAddedChildren() {
        if (this.mChildrenToAddAnimated.isEmpty()) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView childAtIndex = getChildAtIndex(i);
            if (this.mChildrenToAddAnimated.contains(childAtIndex)) {
                int positionInLinearLayout = getPositionInLinearLayout(childAtIndex);
                int intrinsicHeight = getIntrinsicHeight(childAtIndex) + this.mPaddingBetweenElements;
                int i2 = this.mOwnScrollY;
                if (positionInLinearLayout < i2) {
                    setOwnScrollY(i2 + intrinsicHeight);
                }
            }
        }
        clampScrollPosition();
    }

    private void updateScrollability() {
        boolean z = !this.mQsFullScreen && getScrollRange() > 0;
        if (z != this.mScrollable) {
            this.mScrollable = z;
            setFocusable(z);
            updateForwardAndBackwardScrollability();
        }
    }

    private void updateSpeedBumpIndex() {
        this.mSpeedBumpIndexDirty = true;
    }

    private void updateUseRoundedRectClipping() {
        boolean z = this.mIsExpanded && ((this.mQsExpansionFraction > 0.5f ? 1 : (this.mQsExpansionFraction == 0.5f ? 0 : -1)) < 0 || this.mShouldUseSplitNotificationShade);
        if (z != this.mShouldUseRoundedRectClipping) {
            this.mShouldUseRoundedRectClipping = z;
            invalidate();
        }
    }

    private void updateViewShadows() {
        for (int i = 0; i < getChildCount(); i++) {
            ExpandableView childAtIndex = getChildAtIndex(i);
            if (childAtIndex.getVisibility() != 8) {
                this.mTmpSortedChildren.add(childAtIndex);
            }
        }
        Collections.sort(this.mTmpSortedChildren, this.mViewPositionComparator);
        ExpandableView expandableView = null;
        int i2 = 0;
        while (i2 < this.mTmpSortedChildren.size()) {
            ExpandableView expandableView2 = (ExpandableView) this.mTmpSortedChildren.get(i2);
            float translationZ = expandableView2.getTranslationZ();
            float translationZ2 = (expandableView == null ? translationZ : expandableView.getTranslationZ()) - translationZ;
            if (translationZ2 <= 0.0f || translationZ2 >= RUBBER_BAND_FACTOR_NORMAL) {
                expandableView2.setFakeShadowIntensity(0.0f, 0.0f, 0, 0);
            } else {
                expandableView2.setFakeShadowIntensity(translationZ2 / RUBBER_BAND_FACTOR_NORMAL, expandableView.getOutlineAlpha(), (int) ((expandableView.getTranslationY() + expandableView.getActualHeight()) - expandableView2.getTranslationY()), (int) (expandableView.getOutlineTranslation() + expandableView.getTranslation()));
            }
            i2++;
            expandableView = expandableView2;
        }
        this.mTmpSortedChildren.clear();
    }

    public void addContainerViewAt(View view, int i) {
        Assert.isMainThread();
        ensureRemovedFromTransientContainer(view);
        addView(view, i);
        updateSpeedBumpIndex();
    }

    void addSwipedOutView(View view) {
        this.mSwipedOutViews.add(view);
    }

    public void addTransientView(View view, int i) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null && (view instanceof ExpandableNotificationRow)) {
            notificationStackScrollLogger.addTransientRow(((ExpandableNotificationRow) view).getEntry(), i);
        }
        super.addTransientView(view, i);
    }

    public float calculateGapHeight(ExpandableView expandableView, ExpandableView expandableView2, int i) {
        return this.mStackScrollAlgorithm.getGapHeightForChild(this.mSectionsManager, i, expandableView2, expandableView, this.mAmbientState.getFractionToShade(), this.mAmbientState.isOnKeyguard());
    }

    @Override // android.view.View
    public void cancelLongPress() {
        this.mSwipeHelper.cancelLongPress();
    }

    public void changeViewPosition(ExpandableView expandableView, int i) {
        Assert.isMainThread();
        if (this.mChangePositionInProgress) {
            throw new IllegalStateException("Reentrant call to changeViewPosition");
        }
        int iIndexOfChild = indexOfChild(expandableView);
        boolean z = false;
        if (iIndexOfChild == -1) {
            if ((expandableView instanceof ExpandableNotificationRow) && expandableView.getTransientContainer() != null) {
                z = true;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Attempting to re-position ");
            sb.append(z ? "transient" : "");
            sb.append(" view {");
            sb.append(expandableView);
            sb.append("}");
            Log.e("StackScroller", sb.toString());
            return;
        }
        if (expandableView == null || expandableView.getParent() != this || iIndexOfChild == i) {
            return;
        }
        this.mChangePositionInProgress = true;
        expandableView.setChangingPosition(true);
        removeView(expandableView);
        addView(expandableView, i);
        expandableView.setChangingPosition(false);
        this.mChangePositionInProgress = false;
        if (this.mIsExpanded && this.mAnimationsEnabled && expandableView.getVisibility() != 8) {
            this.mChildrenChangingPositions.add(expandableView);
            this.mNeedsAnimation = true;
        }
    }

    public void cleanUpViewStateForEntry(NotificationEntry notificationEntry) {
        if (notificationEntry.getRow() == this.mSwipeHelper.getTranslatingParentView()) {
            this.mSwipeHelper.clearTranslatingParentView();
        }
    }

    public void clearAllNotifications(boolean z) {
        clearNotifications(0, true, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void clearChildFocus(View view) {
        super.clearChildFocus(view);
        if (this.mForcedScroll == view) {
            this.mForcedScroll = null;
        }
    }

    void clearNotifications(int i, boolean z) {
        FooterViewRefactor.assertInLegacyMode();
        clearNotifications(i, z, !this.mController.hasNotifications(2, false));
    }

    void clearNotifications(final int i, boolean z, boolean z2) {
        ArrayList visibleViewsToAnimateAway = getVisibleViewsToAnimateAway(i, z2);
        final ArrayList rowsToDismissInBackend = getRowsToDismissInBackend(i);
        ClearAllListener clearAllListener = this.mClearAllListener;
        if (clearAllListener != null) {
            clearAllListener.onClearAll(i);
        }
        Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$clearNotifications$7(rowsToDismissInBackend, i, (Boolean) obj);
            }
        };
        if (visibleViewsToAnimateAway.isEmpty()) {
            consumer.accept(Boolean.TRUE);
            return;
        }
        setClearAllInProgress(true);
        this.mShadeNeedsToClose = z;
        InteractionJankMonitor.getInstance().begin(this, 62);
        int size = visibleViewsToAnimateAway.size() - 1;
        int iMax = 60;
        int i2 = 0;
        while (size >= 0) {
            NotificationStackScrollLayout notificationStackScrollLayout = this;
            notificationStackScrollLayout.dismissViewAnimated((View) visibleViewsToAnimateAway.get(size), size == 0 ? consumer : null, i2, 200L);
            iMax = Math.max(30, iMax - 5);
            i2 += iMax;
            size--;
            this = notificationStackScrollLayout;
        }
    }

    public void clearSilentNotifications(boolean z, boolean z2) {
        clearNotifications(2, z, z2);
    }

    protected StackScrollAlgorithm createStackScrollAlgorithm(Context context) {
        return new StackScrollAlgorithm(context, this);
    }

    public void dismissViewAnimated(View view, Consumer consumer, int i, long j) {
        if (view instanceof SectionHeaderView) {
            ((StackScrollerDecorView) view).setContentVisible(false, true, consumer);
        } else {
            this.mSwipeHelper.dismissChild(view, 0.0f, consumer, i, true, j, true);
        }
    }

    void dispatchDownEventToScroller(MotionEvent motionEvent) {
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.setAction(0);
        onScrollTouch(motionEventObtain);
        motionEventObtain.recycle();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mShouldUseRoundedRectClipping && !this.mLaunchingNotification) {
            canvas.clipPath(this.mRoundedClipPath);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        if (!this.mShouldUseRoundedRectClipping || !this.mLaunchingNotification) {
            return super.drawChild(canvas, view, j);
        }
        canvas.save();
        ExpandableView expandableView = (ExpandableView) view;
        Path path = (expandableView.isExpandAnimationRunning() || expandableView.hasExpandingChild()) ? null : this.mRoundedClipPath;
        if (path != null) {
            canvas.clipPath(path);
        }
        boolean zDrawChild = super.drawChild(canvas, view, j);
        canvas.restore();
        return zDrawChild;
    }

    protected void fling(int i) {
        if (getChildCount() > 0) {
            float currentOverScrollAmount = getCurrentOverScrollAmount(true);
            float currentOverScrollAmount2 = getCurrentOverScrollAmount(false);
            if (i < 0 && currentOverScrollAmount > 0.0f) {
                setOwnScrollY(this.mOwnScrollY - ((int) currentOverScrollAmount));
                if (!this.mShouldUseSplitNotificationShade) {
                    this.mDontReportNextOverScroll = true;
                    setOverScrollAmount(0.0f, true, false);
                }
                this.mMaxOverScroll = ((Math.abs(i) / 1000.0f) * getRubberBandFactor(true) * this.mOverflingDistance) + currentOverScrollAmount;
            } else if (i <= 0 || currentOverScrollAmount2 <= 0.0f) {
                this.mMaxOverScroll = 0.0f;
            } else {
                setOwnScrollY((int) (this.mOwnScrollY + currentOverScrollAmount2));
                setOverScrollAmount(0.0f, false, false);
                this.mMaxOverScroll = ((Math.abs(i) / 1000.0f) * getRubberBandFactor(false) * this.mOverflingDistance) + currentOverScrollAmount2;
            }
            int iMax = Math.max(0, getScrollRange());
            if (this.mExpandedInThisMotion) {
                iMax = Math.min(iMax, this.mMaxScrollAfterExpand);
            }
            int i2 = iMax;
            OverScroller overScroller = this.mScroller;
            int i3 = ((ViewGroup) this).mScrollX;
            int i4 = this.mOwnScrollY;
            overScroller.fling(i3, i4, 1, i, 0, 0, 0, i2, 0, (!this.mExpandedInThisMotion || i4 < 0) ? 1073741823 : 0);
            animateScroll();
        }
    }

    public void generateAddAnimation(ExpandableView expandableView, boolean z) {
        if (this.mIsExpanded && this.mAnimationsEnabled && !this.mChangePositionInProgress && !isFullyHidden()) {
            this.mChildrenToAddAnimated.add(expandableView);
            if (z) {
                this.mFromMoreCardAdditions.add(expandableView);
            }
            this.mNeedsAnimation = true;
        }
        if (!isHeadsUp(expandableView) || !this.mAnimationsEnabled || this.mChangePositionInProgress || isFullyHidden()) {
            return;
        }
        this.mAddedHeadsUpChildren.add(expandableView);
        this.mChildrenToAddAnimated.remove(expandableView);
    }

    public void generateHeadsUpAnimation(NotificationEntry notificationEntry, boolean z) {
        NotificationsHeadsUpRefactor.assertInLegacyMode();
        generateHeadsUpAnimation(notificationEntry.getHeadsUpAnimationView(), z);
    }

    public void generateHeadsUpAnimation(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        boolean z2 = this.mAnimationsEnabled && (z || this.mHeadsUpGoingAwayAnimationsAllowed);
        boolean z3 = SPEW;
        if (z3) {
            Log.v("StackScroller", "generateHeadsUpAnimation: willAdd=" + z2 + " isHeadsUp=" + z + " row=" + expandableNotificationRow.getEntry().getKey());
        }
        if (z2) {
            if (!z && this.mHeadsUpChangeAnimations.remove(new Pair(expandableNotificationRow, Boolean.TRUE))) {
                if (z3) {
                    Log.v("StackScroller", "generateHeadsUpAnimation: previous hun appear animation cancelled");
                }
                logHunAnimationSkipped(expandableNotificationRow, "previous hun appear animation cancelled");
                return;
            }
            this.mHeadsUpChangeAnimations.add(new Pair(expandableNotificationRow, Boolean.valueOf(z)));
            this.mNeedsAnimation = true;
            if (!this.mIsExpanded && !this.mWillExpand && !z) {
                expandableNotificationRow.setHeadsUpAnimatingAway(true);
                if (NotificationsHeadsUpRefactor.isEnabled()) {
                    setHeadsUpAnimatingAway(true);
                }
            }
            requestChildrenUpdate();
        }
    }

    boolean generateRemoveAnimation(ExpandableView expandableView) {
        String key;
        key = "";
        if (this.mDebugRemoveAnimation) {
            key = expandableView instanceof ExpandableNotificationRow ? ((ExpandableNotificationRow) expandableView).getEntry().getKey() : "";
            Log.d("StackScroller", "generateRemoveAnimation " + key);
        }
        if (removeRemovedChildFromHeadsUpChangeAnimations(expandableView)) {
            if (this.mDebugRemoveAnimation) {
                Log.d("StackScroller", "removedBecauseOfHeadsUp " + key);
            }
            this.mAddedHeadsUpChildren.remove(expandableView);
            return false;
        }
        if (this.mFeatureFlags.isEnabled(Flags.UNCLEARED_TRANSIENT_HUN_FIX)) {
            if (!isExpanded() && isClickedHeadsUp(expandableView)) {
                this.mClearTransientViewsWhenFinished.add(expandableView);
                return expandableView.inRemovalAnimation();
            }
        } else if (isClickedHeadsUp(expandableView)) {
            this.mClearTransientViewsWhenFinished.add(expandableView);
            return true;
        }
        if (this.mDebugRemoveAnimation) {
            Log.d("StackScroller", "generateRemove " + key + "\nmIsExpanded " + this.mIsExpanded + "\nmAnimationsEnabled " + this.mAnimationsEnabled);
        }
        if (this.mIsExpanded && this.mAnimationsEnabled) {
            if (!this.mChildrenToAddAnimated.contains(expandableView)) {
                if (this.mDebugRemoveAnimation) {
                    Log.d("StackScroller", "needsAnimation = true " + key);
                }
                this.mChildrenToRemoveAnimated.add(expandableView);
                this.mNeedsAnimation = true;
                return true;
            }
            this.mChildrenToAddAnimated.remove(expandableView);
            this.mFromMoreCardAdditions.remove(expandableView);
        }
        return false;
    }

    boolean getCheckSnoozeLeaveBehind() {
        return this.mCheckForLeavebehind;
    }

    ExpandableView getChildAtPosition(float f, float f2, boolean z, boolean z2, boolean z3) {
        ExpandableNotificationRow expandableNotificationRow;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ExpandableView childAtIndex = getChildAtIndex(i);
            if (childAtIndex.getVisibility() == 0 && (!z2 || !(childAtIndex instanceof StackScrollerDecorView))) {
                float translationY = childAtIndex.getTranslationY();
                float fMax = Math.max(0, childAtIndex.getClipTopAmount()) + translationY;
                float actualHeight = (childAtIndex.getActualHeight() + translationY) - childAtIndex.getClipBottomAmount();
                int left = z3 ? 0 : childAtIndex.getLeft();
                int width = z3 ? getWidth() : childAtIndex.getRight();
                if ((actualHeight - fMax >= this.mMinInteractionHeight || !z) && f2 >= fMax && f2 <= actualHeight && f >= left && f <= width) {
                    if (!(childAtIndex instanceof ExpandableNotificationRow)) {
                        return childAtIndex;
                    }
                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) childAtIndex;
                    NotificationEntry entry = expandableNotificationRow2.getEntry();
                    if (this.mIsExpanded || !expandableNotificationRow2.isHeadsUp() || !expandableNotificationRow2.isPinned() || (expandableNotificationRow = this.mTopHeadsUpRow) == expandableNotificationRow2 || this.mGroupMembershipManager.getGroupSummary(expandableNotificationRow.getEntry()) == entry) {
                        return expandableNotificationRow2.getViewAtPosition(f2 - translationY);
                    }
                }
            }
        }
        return null;
    }

    public ExpandableView getChildAtRawPosition(float f, float f2) {
        getLocationOnScreen(this.mTempInt2);
        int[] iArr = this.mTempInt2;
        return getChildAtPosition(f - iArr[0], f2 - iArr[1]);
    }

    boolean getClearAllInProgress() {
        return this.mClearAllInProgress;
    }

    public View getContainerChildAt(int i) {
        return getChildAt(i);
    }

    public int getContainerChildCount() {
        return getChildCount();
    }

    public float getCurrentOverScrollAmount(boolean z) {
        return this.mAmbientState.getOverScrollAmount(z);
    }

    public float getCurrentOverScrolledPixels(boolean z) {
        return z ? this.mOverScrolledTopPixels : this.mOverScrolledBottomPixels;
    }

    boolean getDisallowDismissInThisMotion() {
        return this.mDisallowDismissInThisMotion;
    }

    boolean getDisallowScrollingInThisMotion() {
        return this.mDisallowScrollingInThisMotion;
    }

    int getEmptyBottomMargin() {
        return Math.max(this.mMaxLayoutHeight - (this.mShouldUseSplitNotificationShade ? Math.max(this.mSplitShadeMinContentHeight, this.mContentHeight) : this.mContentHeight), 0);
    }

    ExpandHelper getExpandHelper() {
        return this.mExpandHelper;
    }

    boolean getExpandedInThisMotion() {
        return this.mExpandedInThisMotion;
    }

    public ExpandableView getFirstChildNotGone() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                return (ExpandableView) childAt;
            }
        }
        return null;
    }

    public boolean getIsExpanded() {
        return this.mIsExpanded;
    }

    public ExpandableView getLastChildNotGone() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt.getVisibility() != 8) {
                return (ExpandableView) childAt;
            }
        }
        return null;
    }

    public int getLayoutMinHeight() {
        return 0;
    }

    boolean getOnlyScrollingInThisMotion() {
        return this.mOnlyScrollingInThisMotion;
    }

    public int getPositionInLinearLayout(View view) {
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableNotificationRow notificationParent;
        ExpandableView expandableView = null;
        if (isChildInGroup(view)) {
            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) view;
            notificationParent = expandableNotificationRow2.getNotificationParent();
            expandableNotificationRow = expandableNotificationRow2;
            view = notificationParent;
        } else {
            expandableNotificationRow = null;
            notificationParent = null;
        }
        float f = this.mAmbientState.isOnKeyguard() ? 0.0f : this.mMinimumPaddings;
        int intrinsicHeight = (int) f;
        int i = -1;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            ExpandableView childAtIndex = getChildAtIndex(i2);
            boolean z = childAtIndex.getVisibility() != 8;
            if (z) {
                i++;
            }
            if (z && !childAtIndex.hasNoContentHeight()) {
                float f2 = intrinsicHeight;
                if (f2 != f) {
                    if (expandableView != null) {
                        intrinsicHeight = (int) (f2 + calculateGapHeight(expandableView, childAtIndex, i));
                    }
                    intrinsicHeight += this.mPaddingBetweenElements;
                }
            }
            if (childAtIndex == view) {
                return notificationParent != null ? intrinsicHeight + notificationParent.getPositionOfChild(expandableNotificationRow) : intrinsicHeight;
            }
            if (z) {
                intrinsicHeight += getIntrinsicHeight(childAtIndex);
                expandableView = childAtIndex;
            }
        }
        return 0;
    }

    public int getSpeedBumpIndex() {
        if (this.mSpeedBumpIndexDirty) {
            this.mSpeedBumpIndexDirty = false;
            int childCount = getChildCount();
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() != 8 && (childAt instanceof ExpandableNotificationRow)) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                    i2++;
                    boolean zIsAmbient = true;
                    if (!this.mHighPriorityBeforeSpeedBump) {
                        zIsAmbient = true ^ expandableNotificationRow.getEntry().isAmbient();
                    } else if (expandableNotificationRow.getEntry().getBucket() >= 6) {
                        zIsAmbient = false;
                    }
                    if (zIsAmbient) {
                        i = i2;
                    }
                }
            }
            this.mSpeedBumpIndex = i;
        }
        return this.mSpeedBumpIndex;
    }

    public float getStackTranslation() {
        return this.mAmbientState.getStackTranslation();
    }

    public int getTopPadding() {
        return this.mAmbientState.getTopPadding();
    }

    public float getTotalTranslationLength(View view) {
        if (!this.mDismissUsingRowTranslationX) {
            return view.getMeasuredWidth();
        }
        float measuredWidth = view.getMeasuredWidth();
        float measuredWidth2 = getMeasuredWidth();
        return measuredWidth2 - ((measuredWidth2 - measuredWidth) / 2.0f);
    }

    public ViewGroup getViewParentForNotification(NotificationEntry notificationEntry) {
        return this;
    }

    void handleEmptySpaceClick(MotionEvent motionEvent) {
        logEmptySpaceClick(motionEvent, isBelowLastNotification(this.mInitialTouchX, this.mInitialTouchY), this.mStatusBarState, this.mTouchIsClick);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            if (this.mTouchIsClick && isBelowLastNotification(this.mInitialTouchX, this.mInitialTouchY)) {
                debugShadeLog("handleEmptySpaceClick: touch event propagated further");
                return;
            }
            return;
        }
        if (actionMasked != 2) {
            debugShadeLog("handleEmptySpaceClick: MotionEvent ignored");
            return;
        }
        float touchSlop = getTouchSlop(motionEvent);
        if (this.mTouchIsClick) {
            if (Math.abs(motionEvent.getY() - this.mInitialTouchY) > touchSlop || Math.abs(motionEvent.getX() - this.mInitialTouchX) > touchSlop) {
                this.mTouchIsClick = false;
            }
        }
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return !this.mForceNoOverlappingRendering && super.hasOverlappingRendering();
    }

    public boolean hasPulsingNotifications() {
        return this.mPulsing;
    }

    protected void inflateFooterView() {
        FooterViewRefactor.assertInLegacyMode();
        setFooterView((FooterView) LayoutInflater.from(((ViewGroup) this).mContext).inflate(R$layout.status_bar_notification_footer, (ViewGroup) this, false));
    }

    void initDownStates(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mExpandedInThisMotion = false;
            this.mOnlyScrollingInThisMotion = !this.mScroller.isFinished();
            this.mDisallowScrollingInThisMotion = false;
            this.mDisallowDismissInThisMotion = false;
            this.mTouchIsClick = true;
            this.mInitialTouchX = motionEvent.getX();
            this.mInitialTouchY = motionEvent.getY();
        }
    }

    void initView(Context context, NotificationSwipeHelper notificationSwipeHelper, NotificationStackSizeCalculator notificationStackSizeCalculator) {
        this.mScroller = new OverScroller(getContext());
        this.mSwipeHelper = notificationSwipeHelper;
        this.mNotificationStackSizeCalculator = notificationStackSizeCalculator;
        setDescendantFocusability(262144);
        setClipChildren(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        Resources resources = context.getResources();
        boolean z = resources.getBoolean(R$bool.config_skinnyNotifsInLandscape);
        this.mSkinnyNotifsInLandscape = z;
        this.mLastInitViewDumpString = "mIsSmallLandscapeLockscreenEnabled=" + this.mIsSmallLandscapeLockscreenEnabled + " isSmallScreenLandscape=false useSmallLandscapeLockscreenResources=false skinnyNotifsInLandscape=" + z + " mSkinnyNotifsInLandscape=" + this.mSkinnyNotifsInLandscape;
        this.mLastInitViewElapsedRealtime = SystemClock.elapsedRealtime();
        if (DEBUG_UPDATE_SIDE_PADDING) {
            Log.v("StackScroller", "initView @ elapsedRealtime " + this.mLastInitViewElapsedRealtime + ": " + this.mLastInitViewDumpString);
        }
        this.mGapHeight = resources.getDimensionPixelSize(R$dimen.notification_section_divider_height);
        this.mStackScrollAlgorithm.initView(context);
        this.mStateAnimator.initView(context);
        this.mAmbientState.reload(context);
        this.mPaddingBetweenElements = Math.max(1, resources.getDimensionPixelSize(R$dimen.notification_divider_height));
        this.mMinTopOverScrollToEscape = resources.getDimensionPixelSize(R$dimen.min_top_overscroll_to_qs);
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(((ViewGroup) this).mContext);
        this.mBottomPadding = resources.getDimensionPixelSize(R$dimen.notification_panel_padding_bottom);
        this.mMinimumPaddings = resources.getDimensionPixelSize(R$dimen.notification_side_paddings);
        this.mQsTilePadding = resources.getDimensionPixelOffset(R$dimen.qs_tile_margin_horizontal);
        this.mSidePaddings = this.mMinimumPaddings;
        this.mMinInteractionHeight = resources.getDimensionPixelSize(R$dimen.notification_min_interaction_height);
        this.mCornerRadius = resources.getDimensionPixelSize(R$dimen.notification_corner_radius);
        this.mHeadsUpInset = this.mStatusBarHeight + resources.getDimensionPixelSize(R$dimen.heads_up_status_bar_padding);
        this.mQsScrollBoundaryPosition = SystemBarUtils.getQuickQsOffsetHeight(((ViewGroup) this).mContext);
    }

    boolean isBeingDragged() {
        return this.mIsBeingDragged;
    }

    public boolean isBelowLastNotification(float f, float f2) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ExpandableView childAtIndex = getChildAtIndex(childCount);
            if (childAtIndex.getVisibility() != 8) {
                float y = childAtIndex.getY();
                if (y > f2) {
                    return false;
                }
                boolean z = f2 > (((float) childAtIndex.getActualHeight()) + y) - ((float) childAtIndex.getClipBottomAmount());
                FooterView footerView = this.mFooterView;
                if (childAtIndex == footerView) {
                    if (!z && !footerView.isOnEmptySpace(f - footerView.getX(), f2 - y)) {
                        return false;
                    }
                } else {
                    if (childAtIndex == this.mEmptyShadeView) {
                        return true;
                    }
                    if (!z) {
                        return false;
                    }
                }
            }
        }
        return f2 > ((float) getTopPadding()) + getStackTranslation();
    }

    public boolean isExpanded() {
        return this.mIsExpanded;
    }

    boolean isExpandingNotification() {
        return this.mExpandingNotification;
    }

    boolean isFlingAfterUpEvent() {
        return this.mFlingAfterUpEvent;
    }

    public boolean isFullyAwake() {
        return this.mAmbientState.isFullyAwake();
    }

    public boolean isFullyHidden() {
        return this.mAmbientState.isFullyHidden();
    }

    public boolean isFullySwipedOut(ExpandableView expandableView) {
        return Math.abs(expandableView.getTranslation()) >= Math.abs(getTotalTranslationLength(expandableView));
    }

    public boolean isHistoryShown() {
        FooterViewRefactor.assertInLegacyMode();
        FooterView footerView = this.mFooterView;
        return footerView != null && footerView.isHistoryShown();
    }

    public boolean isInContentBounds(float f) {
        return f < ((float) (getHeight() - getEmptyBottomMargin()));
    }

    protected boolean isInsideQsHeader(MotionEvent motionEvent) {
        this.mQsHeader.getBoundsOnScreen(this.mQsHeaderBound);
        this.mQsHeaderBound.offsetTo(Math.round((motionEvent.getRawX() - motionEvent.getX()) + this.mQsHeader.getLeft()), Math.round(motionEvent.getRawY() - motionEvent.getY()));
        return this.mQsHeaderBound.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
    }

    boolean isQsFullScreen() {
        return this.mQsFullScreen;
    }

    public boolean isVisible(View view) {
        boolean clipBounds = view.getClipBounds(this.mTmpRect);
        if (view.getVisibility() == 0) {
            return !clipBounds || this.mTmpRect.height() > 0;
        }
        return false;
    }

    public void lockScrollTo(View view) {
        if (this.mForcedScroll == view) {
            return;
        }
        this.mForcedScroll = view;
        updateForcedScroll();
    }

    public void notifyGroupChildAdded(ExpandableView expandableView) {
        onViewAddedInternal(expandableView);
    }

    public void notifyGroupChildRemoved(ExpandableView expandableView, ViewGroup viewGroup) {
        onViewRemovedInternal(expandableView, viewGroup);
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mWaterfallTopInset = 0;
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            this.mWaterfallTopInset = displayCutout.getWaterfallInsets().top;
        }
        if (!this.mIsInsetAnimationRunning) {
            updateImeInset(windowInsets);
        }
        return windowInsets;
    }

    void onChildAnimationFinished() {
        setAnimationRunning(false);
        if (NotificationsHeadsUpRefactor.isEnabled()) {
            setHeadsUpAnimatingAway(false);
        }
        requestChildrenUpdate();
        runAnimationFinishedRunnables();
        clearTransient();
        clearHeadsUpDisappearRunning();
        finalizeClearAllAnimation();
    }

    void onChildHeightChanged(ExpandableView expandableView, boolean z) {
        boolean z2 = this.mAnimateStackYForContentHeightChange;
        if (z) {
            this.mAnimateStackYForContentHeightChange = true;
        }
        updateContentHeight();
        updateScrollPositionOnExpandInBottom(expandableView);
        clampScrollPosition();
        notifyHeightChangeListener(expandableView, z);
        ExpandableNotificationRow expandableNotificationRow = expandableView instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) expandableView : null;
        NotificationSection firstVisibleSection = getFirstVisibleSection();
        ExpandableView firstVisibleChild = firstVisibleSection != null ? firstVisibleSection.getFirstVisibleChild() : null;
        if (expandableNotificationRow != null && (expandableNotificationRow == firstVisibleChild || expandableNotificationRow.getNotificationParent() == firstVisibleChild)) {
            updateAlgorithmLayoutMinHeight();
        }
        if (z) {
            requestAnimationOnViewResize(expandableNotificationRow);
        }
        requestChildrenUpdate();
        this.mAnimateStackYForContentHeightChange = z2;
    }

    void onChildHeightReset(ExpandableView expandableView) {
        updateAnimationState(expandableView);
        updateChronometerForChild(expandableView);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Resources resources = getResources();
        updateSplitNotificationShade();
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(((ViewGroup) this).mContext);
        this.mSwipeHelper.setDensityScale(resources.getDisplayMetrics().density);
        this.mSwipeHelper.setPagingTouchSlop(ViewConfiguration.get(getContext()).getScaledPagingTouchSlop());
        reinitView();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        onJustBeforeDraw();
        if (this.mDebugLines) {
            onDrawDebug(canvas);
        }
    }

    void onEntryUpdated(NotificationEntry notificationEntry) {
        if (!notificationEntry.rowExists() || notificationEntry.getSbn().isClearable()) {
            return;
        }
        snapViewIfNeeded(notificationEntry);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflateEmptyShadeView();
        if (!FooterViewRefactor.isEnabled()) {
            inflateFooterView();
        }
        updateDecorViews();
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (!isScrollingEnabled() || !this.mIsExpanded || this.mSwipeHelper.isSwiping() || this.mExpandingNotification || this.mDisallowScrollingInThisMotion) {
            return false;
        }
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8 && !this.mIsBeingDragged) {
            float axisValue = motionEvent.getAxisValue(9);
            if (axisValue != 0.0f) {
                int verticalScrollFactor = (int) (axisValue * getVerticalScrollFactor());
                int scrollRange = getScrollRange();
                int i = this.mOwnScrollY;
                int i2 = i - verticalScrollFactor;
                int i3 = i2 >= 0 ? i2 > scrollRange ? scrollRange : i2 : 0;
                if (i3 != i) {
                    setOwnScrollY(i3);
                    return true;
                }
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    void onGroupExpandChanged(final ExpandableNotificationRow expandableNotificationRow, boolean z) {
        boolean z2 = this.mAnimationsEnabled && (this.mIsExpanded || expandableNotificationRow.isPinned());
        if (z2) {
            this.mExpandedGroupView = expandableNotificationRow;
            this.mNeedsAnimation = true;
        }
        expandableNotificationRow.setChildrenExpanded(z, z2);
        onChildHeightChanged(expandableNotificationRow, false);
        runAfterAnimationFinished(new Runnable(this) { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.10
            @Override // java.lang.Runnable
            public void run() {
                expandableNotificationRow.onFinishedExpansionChange();
            }
        });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        NotificationStackScrollLayoutController.TouchHandler touchHandler = this.mTouchHandler;
        if (touchHandler == null || !touchHandler.onInterceptTouchEvent(motionEvent)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean onInterceptTouchEventScroll(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instruction units count: 238
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.onInterceptTouchEventScroll(android.view.MotionEvent):boolean");
    }

    boolean onKeyguard() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!this.mSuppressChildrenMeasureAndLayout) {
            float width = getWidth() / 2.0f;
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                float measuredWidth = r8.getMeasuredWidth() / 2.0f;
                getChildAt(i5).layout((int) (width - measuredWidth), 0, (int) (measuredWidth + width), r8.getMeasuredHeight());
            }
        }
        setMaxLayoutHeight(getHeight());
        updateContentHeight();
        clampScrollPosition();
        requestChildrenUpdate();
        updateFirstAndLastBackgroundViews();
        updateAlgorithmLayoutMinHeight();
        updateOwnTranslationZ();
        StackScrollAlgorithm stackScrollAlgorithm = this.mStackScrollAlgorithm;
        ViewGroup viewGroup = this.mQsHeader;
        stackScrollAlgorithm.updateQSFrameTop(viewGroup == null ? 0 : viewGroup.getHeight());
        this.mAnimateStackYForContentHeightChange = false;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        Trace.beginSection("NotificationStackScrollLayout#onMeasure");
        if (SPEW) {
            Log.d("StackScroller", "onMeasure(widthMeasureSpec=" + View.MeasureSpec.toString(i) + ", heightMeasureSpec=" + View.MeasureSpec.toString(i2) + ")");
        }
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        updateSidePadding(size);
        if (this.mSuppressChildrenMeasureAndLayout) {
            Trace.endSection();
            return;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size - (this.mSidePaddings * 2), View.MeasureSpec.getMode(i));
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 0);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            measureChild(getChildAt(i3), iMakeMeasureSpec, iMakeMeasureSpec2);
        }
        Trace.endSection();
    }

    boolean onScrollTouch(MotionEvent motionEvent) {
        if (!isScrollingEnabled()) {
            return false;
        }
        if (isInsideQsHeader(motionEvent) && !this.mIsBeingDragged) {
            return false;
        }
        this.mForcedScroll = null;
        initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (motionEvent.findPointerIndex(this.mActivePointerId) == -1 && actionMasked != 0) {
            Log.e("StackScroller", "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent " + MotionEvent.actionToString(motionEvent.getActionMasked()));
            return true;
        }
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (iFindPointerIndex == -1) {
                        Log.e("StackScroller", "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                    } else {
                        int y = (int) motionEvent.getY(iFindPointerIndex);
                        int x = (int) motionEvent.getX(iFindPointerIndex);
                        int i = this.mLastMotionY - y;
                        int iAbs = Math.abs(x - this.mDownX);
                        int iAbs2 = Math.abs(i);
                        float touchSlop = getTouchSlop(motionEvent);
                        if (!this.mIsBeingDragged && iAbs2 > touchSlop && iAbs2 > iAbs) {
                            setIsBeingDragged(true);
                            i = (int) (i > 0 ? i - touchSlop : i + touchSlop);
                        }
                        if (this.mIsBeingDragged) {
                            this.mLastMotionY = y;
                            int scrollRange = getScrollRange();
                            if (this.mExpandedInThisMotion) {
                                scrollRange = Math.min(scrollRange, this.mMaxScrollAfterExpand);
                            }
                            float fOverScrollDown = i < 0 ? overScrollDown(i) : overScrollUp(i, scrollRange);
                            if (fOverScrollDown != 0.0f) {
                                customOverScrollBy((int) fOverScrollDown, this.mOwnScrollY, scrollRange, getHeight() / 2);
                                this.mController.checkSnoozeLeavebehind();
                            }
                        }
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked == 5) {
                        int actionIndex = motionEvent.getActionIndex();
                        this.mLastMotionY = (int) motionEvent.getY(actionIndex);
                        this.mDownX = (int) motionEvent.getX(actionIndex);
                        this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                    } else if (actionMasked == 6) {
                        onSecondaryPointerUp(motionEvent);
                        this.mLastMotionY = (int) motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
                        this.mDownX = (int) motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                    }
                } else if (this.mIsBeingDragged && getChildCount() > 0) {
                    if (this.mScroller.springBack(((ViewGroup) this).mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                        animateScroll();
                    }
                    this.mActivePointerId = -1;
                    endDrag();
                }
            } else if (this.mIsBeingDragged) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int yVelocity = (int) velocityTracker.getYVelocity(this.mActivePointerId);
                if (shouldOverScrollFling(yVelocity)) {
                    onOverScrollFling(true, yVelocity);
                } else if (getChildCount() > 0) {
                    if (Math.abs(yVelocity) > this.mMinimumVelocity) {
                        if (getCurrentOverScrollAmount(true) == 0.0f || yVelocity > 0) {
                            this.mFlingAfterUpEvent = true;
                            setFinishScrollingCallback(new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda7
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$onScrollTouch$2();
                                }
                            });
                            fling(-yVelocity);
                        } else {
                            onOverScrollFling(false, yVelocity);
                        }
                    } else if (this.mScroller.springBack(((ViewGroup) this).mScrollX, this.mOwnScrollY, 0, 0, 0, getScrollRange())) {
                        animateScroll();
                    }
                }
                this.mActivePointerId = -1;
                endDrag();
            }
        } else {
            if (getChildCount() == 0 || !isInContentBounds(motionEvent)) {
                return false;
            }
            setIsBeingDragged(!this.mScroller.isFinished());
            if (!this.mScroller.isFinished()) {
                this.mScroller.forceFinished(true);
            }
            this.mLastMotionY = (int) motionEvent.getY();
            this.mDownX = (int) motionEvent.getX();
            this.mActivePointerId = motionEvent.getPointerId(0);
        }
        return true;
    }

    void onSwipeBegin(View view) {
        if (view instanceof ExpandableNotificationRow) {
            this.mSectionsManager.updateFirstAndLastViewsForAllSections(this.mSections, getChildrenWithBackground());
            RoundableTargets roundableTargetsFindRoundableTargets = this.mController.getNotificationTargetsHelper().findRoundableTargets((ExpandableNotificationRow) view, this, this.mSectionsManager);
            this.mController.getNotificationRoundnessManager().setViewsAffectedBySwipe(roundableTargetsFindRoundableTargets.getBefore(), roundableTargetsFindRoundableTargets.getSwiped(), roundableTargetsFindRoundableTargets.getAfter());
            updateFirstAndLastBackgroundViews();
            requestDisallowInterceptTouchEvent(true);
            updateContinuousShadowDrawing();
            requestChildrenUpdate();
        }
    }

    void onSwipeEnd() {
        updateFirstAndLastBackgroundViews();
        this.mController.getNotificationRoundnessManager().setViewsAffectedBySwipe(null, null, null);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        NotificationStackScrollLayoutController.TouchHandler touchHandler = this.mTouchHandler;
        if (touchHandler == null || !touchHandler.onTouchEvent(motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (view instanceof ExpandableView) {
            onViewAddedInternal((ExpandableView) view);
        }
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        ExpandableView expandableView = (ExpandableView) view;
        if (this.mChildTransferInProgress) {
            return;
        }
        onViewRemovedInternal(expandableView, this);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            return;
        }
        cancelLongPress();
    }

    void reinflateViews() {
        if (!FooterViewRefactor.isEnabled()) {
            inflateFooterView();
            updateFooter();
        }
        inflateEmptyShadeView();
        this.mSectionsManager.reinflateViews();
        updateDecorViews();
    }

    public void removeContainerView(View view) {
        Assert.isMainThread();
        removeView(view);
        updateSpeedBumpIndex();
    }

    public void removeTransientView(View view) {
        NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
        if (notificationStackScrollLogger != null && (view instanceof ExpandableNotificationRow)) {
            notificationStackScrollLogger.removeTransientRow(((ExpandableNotificationRow) view).getEntry());
        }
        super.removeTransientView(view);
    }

    void requestChildrenUpdate() {
        if (this.mChildrenUpdateRequested) {
            return;
        }
        getViewTreeObserver().addOnPreDrawListener(this.mChildrenUpdater);
        this.mChildrenUpdateRequested = true;
        invalidate();
    }

    public void requestDisallowDismiss() {
        this.mDisallowDismissInThisMotion = true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z) {
            cancelLongPress();
        }
    }

    public void requestDisallowLongPress() {
        cancelLongPress();
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        Trace.instant(4096L, "NotificationStackScrollLayout#requestLayout");
        super.requestLayout();
    }

    public void runAfterAnimationFinished(Runnable runnable) {
        this.mAnimationFinishedRunnables.add(runnable);
    }

    public void setActivityStarter(ActivityStarter activityStarter) {
        this.mActivityStarter = activityStarter;
    }

    public void setAnimationRunning(boolean z) {
        if (z != this.mAnimationRunning) {
            if (z) {
                getViewTreeObserver().addOnPreDrawListener(this.mRunningAnimationUpdater);
            } else {
                getViewTreeObserver().removeOnPreDrawListener(this.mRunningAnimationUpdater);
            }
            this.mAnimationRunning = z;
            updateContinuousShadowDrawing();
        }
    }

    void setCheckForLeaveBehind(boolean z) {
        this.mCheckForLeavebehind = z;
    }

    public void setChildLocationsChangedListener(NotificationLogger.OnChildLocationsChangedListener onChildLocationsChangedListener) {
        NotificationsLiveDataStoreRefactor.assertInLegacyMode();
        this.mListener = onChildLocationsChangedListener;
    }

    public void setChildTransferInProgress(boolean z) {
        Assert.isMainThread();
        this.mChildTransferInProgress = z;
    }

    void setClearAllAnimationListener(ClearAllAnimationListener clearAllAnimationListener) {
        this.mClearAllAnimationListener = clearAllAnimationListener;
    }

    void setClearAllFinishedWhilePanelExpandedRunnable(Runnable runnable) {
        this.mClearAllFinishedWhilePanelExpandedRunnable = runnable;
    }

    public void setClearAllInProgress(boolean z) {
        this.mClearAllInProgress = z;
        this.mAmbientState.setClearAllInProgress(z);
        this.mController.getNotificationRoundnessManager().setClearAllInProgress(z);
    }

    void setClearAllListener(ClearAllListener clearAllListener) {
        this.mClearAllListener = clearAllListener;
    }

    public void setController(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.mController = notificationStackScrollLayoutController;
        notificationStackScrollLayoutController.getNotificationRoundnessManager().setAnimatedChildren(this.mChildrenToAddAnimated);
    }

    public void setCurrentUserSetup(boolean z) {
        FooterViewRefactor.assertInLegacyMode();
        if (this.mIsCurrentUserSetup != z) {
            this.mIsCurrentUserSetup = z;
            updateFooter();
        }
    }

    public void setEmptyShadeView(EmptyShadeView emptyShadeView) {
        int iIndexOfChild;
        EmptyShadeView emptyShadeView2 = this.mEmptyShadeView;
        if (emptyShadeView2 != null) {
            iIndexOfChild = indexOfChild(emptyShadeView2);
            removeView(this.mEmptyShadeView);
        } else {
            iIndexOfChild = -1;
        }
        this.mEmptyShadeView = emptyShadeView;
        addView(emptyShadeView, iIndexOfChild);
    }

    public void setFinishScrollingCallback(Runnable runnable) {
        this.mFinishScrollingCallback = runnable;
    }

    public void setFooterView(final FooterView footerView) {
        int iIndexOfChild;
        FooterView footerView2 = this.mFooterView;
        if (footerView2 != null) {
            iIndexOfChild = indexOfChild(footerView2);
            removeView(this.mFooterView);
        } else {
            iIndexOfChild = -1;
        }
        this.mFooterView = footerView;
        addView(footerView, iIndexOfChild);
        if (FooterViewRefactor.isEnabled()) {
            return;
        }
        View.OnClickListener onClickListener = this.mManageButtonClickListener;
        if (onClickListener != null) {
            this.mFooterView.setManageButtonClickListener(onClickListener);
        }
        this.mFooterView.setClearAllButtonClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setFooterView$3(footerView, view);
            }
        });
    }

    public void setHasFilteredOutSeenNotifications(boolean z) {
        FooterViewRefactor.assertInLegacyMode();
        this.mHasFilteredOutSeenNotifications = z;
    }

    public void setHeadsUpAnimatingAway(boolean z) {
        if (this.mHeadsUpAnimatingAway != z) {
            this.mHeadsUpAnimatingAway = z;
            Consumer consumer = this.mHeadsUpAnimatingAwayListener;
            if (consumer != null) {
                consumer.accept(Boolean.valueOf(z));
            }
        }
        updateClipping();
    }

    public void setHeadsUpAnimatingAwayListener(Consumer consumer) {
        this.mHeadsUpAnimatingAwayListener = consumer;
    }

    public void setInHeadsUpPinnedMode(boolean z) {
        this.mInHeadsUpPinnedMode = z;
        updateClipping();
    }

    void setIntrinsicPadding(int i) {
        this.mIntrinsicPadding = i;
    }

    void setIsBeingDragged(boolean z) {
        this.mIsBeingDragged = z;
        if (!z) {
            this.mSendingTouchesToSceneFramework = false;
            return;
        }
        requestDisallowInterceptTouchEvent(true);
        cancelLongPress();
        resetExposedMenuView(true, true);
    }

    public void setIsRemoteInputActive(boolean z) {
        FooterViewRefactor.assertInLegacyMode();
        this.mIsRemoteInputActive = z;
        updateFooter();
    }

    @Override // android.view.View
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
    }

    protected void setLogger(NotificationStackScrollLogger notificationStackScrollLogger) {
        this.mLogger = notificationStackScrollLogger;
    }

    public void setManageButtonClickListener(View.OnClickListener onClickListener) {
        FooterViewRefactor.assertInLegacyMode();
        this.mManageButtonClickListener = onClickListener;
        FooterView footerView = this.mFooterView;
        if (footerView != null) {
            footerView.setManageButtonClickListener(onClickListener);
        }
    }

    public void setOverScrollAmount(float f, boolean z, boolean z2) {
        setOverScrollAmount(f, z, z2, true);
    }

    public void setOverScrollAmount(float f, boolean z, boolean z2, boolean z3) {
        setOverScrollAmount(f, z, z2, z3, isRubberbanded(z));
    }

    public void setOverScrollAmount(float f, boolean z, boolean z2, boolean z3, boolean z4) {
        if (z3) {
            this.mStateAnimator.cancelOverScrollAnimators(z);
        }
        setOverScrollAmountInternal(f, z, z2, z4);
    }

    public void setOverScrolledPixels(float f, boolean z, boolean z2) {
        setOverScrollAmount(f * getRubberBandFactor(z), z, z2, true);
    }

    void setOwnScrollY(int i) {
        setOwnScrollY(i, false);
    }

    public void setResetUserExpandedStatesRunnable(Runnable runnable) {
        this.mResetUserExpandedStatesRunnable = runnable;
    }

    protected void setStackStateLogger(StackStateLogger stackStateLogger) {
        this.mStateAnimator.setLogger(stackStateLogger);
    }

    public void setTopHeadsUpRow(ExpandableNotificationRow expandableNotificationRow) {
        this.mTopHeadsUpRow = expandableNotificationRow;
    }

    void setTouchHandler(NotificationStackScrollLayoutController.TouchHandler touchHandler) {
        this.mTouchHandler = touchHandler;
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    void updateAnimationState(View view) {
        updateAnimationState((this.mAnimationsEnabled || hasPulsingNotifications()) && (this.mIsExpanded || isPinnedHeadsUp(view)), view);
    }

    void updateBgColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ActivatableNotificationView) {
                ((ActivatableNotificationView) childAt).updateBackgroundColors();
            }
        }
    }

    void updateChronometerForChild(View view) {
        if (view instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) view).setChronometerRunning(this.mIsExpanded);
        }
    }

    public void updateClipping() {
        boolean z = (this.mRequestedClipBounds == null || this.mInHeadsUpPinnedMode || this.mHeadsUpAnimatingAway) ? false : true;
        if (this.mIsClipped != z) {
            this.mIsClipped = z;
        }
        if (this.mAmbientState.isHiddenAtAll()) {
            invalidateOutline();
            if (isFullyHidden()) {
                setClipBounds(null);
            }
        } else if (z) {
            setClipBounds(this.mRequestedClipBounds);
        } else {
            setClipBounds(null);
        }
        setClipToOutline(false);
    }

    void updateContinuousShadowDrawing() {
        boolean z = this.mAnimationRunning || this.mSwipeHelper.isSwiping();
        if (z != this.mContinuousShadowUpdate) {
            if (z) {
                getViewTreeObserver().addOnPreDrawListener(this.mShadowUpdater);
            } else {
                getViewTreeObserver().removeOnPreDrawListener(this.mShadowUpdater);
            }
            this.mContinuousShadowUpdate = z;
        }
    }

    void updateCornerRadius() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.notification_corner_radius);
        if (this.mCornerRadius != dimensionPixelSize) {
            this.mCornerRadius = dimensionPixelSize;
            invalidate();
        }
    }

    void updateDecorViews() {
        int color = ((ViewGroup) this).mContext.getColor(R$color.desktop_qs_notification_sections_head_text);
        this.mSectionsManager.setHeaderForegroundColors(color, color);
        FooterView footerView = this.mFooterView;
        if (footerView != null) {
            footerView.updateColors();
        }
        EmptyShadeView emptyShadeView = this.mEmptyShadeView;
        if (emptyShadeView != null) {
            emptyShadeView.setTextColors(color, color);
        }
    }

    public void updateEmptyShadeView(boolean z, boolean z2) {
        FooterViewRefactor.assertInLegacyMode();
        updateEmptyShadeView(z, z2, this.mHasFilteredOutSeenNotifications);
    }

    public void updateEmptyShadeView(boolean z, boolean z2, boolean z3) {
        this.mEmptyShadeView.setVisible(z, this.mIsExpanded && this.mAnimationsEnabled);
        if (z2) {
            updateEmptyShadeViewResources(R$string.dnd_suppressing_shade_text, 0, 0);
        } else if (z3) {
            updateEmptyShadeViewResources(R$string.no_unseen_notif_text, R$string.unlock_to_see_notif_text, R$drawable.ic_friction_lock_closed);
        } else {
            updateEmptyShadeViewResources(R$string.empty_shade_text, 0, 0);
        }
    }

    public void updateFooter() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController;
        FooterViewRefactor.assertInLegacyMode();
        if (this.mFooterView == null || (notificationStackScrollLayoutController = this.mController) == null) {
            return;
        }
        boolean zIsHistoryEnabled = notificationStackScrollLayoutController.isHistoryEnabled();
        boolean zShouldShowDismissView = shouldShowDismissView();
        updateFooterView(shouldShowFooterView(zShouldShowDismissView), zShouldShowDismissView, zIsHistoryEnabled);
    }

    public void updateFooterView(boolean z, boolean z2, boolean z3) {
        FooterViewRefactor.assertInLegacyMode();
        FooterView footerView = this.mFooterView;
        if (footerView == null || this.mNotificationStackSizeCalculator == null) {
            return;
        }
        boolean z4 = this.mIsExpanded && this.mAnimationsEnabled;
        footerView.setVisible(z, z4);
        this.mFooterView.showHistory(z3);
        this.mFooterView.setClearAllButtonVisible(z2, z4);
        this.mFooterView.setFooterLabelVisible(this.mHasFilteredOutSeenNotifications);
        this.mFooterView.setManageOrHistoryButtonVisible(false);
    }

    void updateSensitiveness(boolean z, boolean z2) {
        if (z2 != this.mAmbientState.isHideSensitive()) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAtIndex(i).setHideSensitiveForIntrinsicHeight(z2);
            }
            this.mAmbientState.setHideSensitive(z2);
            if (z && this.mAnimationsEnabled) {
                this.mHideSensitiveNeedsAnimation = true;
                this.mNeedsAnimation = true;
            }
            updateContentHeight();
            requestChildrenUpdate();
        }
    }

    void updateSidePadding(int i) {
        this.mLastUpdateSidePaddingDumpString = "viewWidth=" + i + " skinnyNotifsInLandscape=" + this.mSkinnyNotifsInLandscape + " orientation=" + getResources().getConfiguration().orientation;
        this.mLastUpdateSidePaddingElapsedRealtime = SystemClock.elapsedRealtime();
        if (DEBUG_UPDATE_SIDE_PADDING) {
            Log.v("StackScroller", "updateSidePadding @ elapsedRealtime " + this.mLastUpdateSidePaddingElapsedRealtime + ": " + this.mLastUpdateSidePaddingDumpString);
        }
        this.mSidePaddings = this.mMinimumPaddings;
    }

    void updateSplitNotificationShade() {
        if (this.mShouldUseSplitNotificationShade) {
            this.mShouldUseSplitNotificationShade = false;
            this.mShouldSkipTopPaddingAnimationAfterFold = true;
            this.mAmbientState.setUseSplitShade(false);
            updateDismissBehavior();
            updateUseRoundedRectClipping();
            requestLayout();
        }
    }
}
