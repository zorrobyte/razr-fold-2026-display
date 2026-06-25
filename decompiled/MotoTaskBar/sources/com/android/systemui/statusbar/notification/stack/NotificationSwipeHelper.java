package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.SwipeHelper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
class NotificationSwipeHelper extends SwipeHelper {
    protected static final long COVER_MENU_DELAY = 4000;
    private static final SourceType SWIPE_DISMISS = SourceType.from("SwipeDismiss");
    private final NotificationCallback mCallback;
    private final Runnable mFalsingCheck;
    private View mMenuExposedView;
    private final NotificationRoundnessManager mNotificationRoundnessManager;
    private View mTranslatingParentView;

    class Builder {
        private DumpManager mDumpManager;
        private final FeatureFlags mFeatureFlags;
        private NotificationCallback mNotificationCallback;
        private NotificationRoundnessManager mNotificationRoundnessManager;
        private final Resources mResources;
        private final ViewConfiguration mViewConfiguration;

        Builder(Resources resources, ViewConfiguration viewConfiguration, DumpManager dumpManager, FeatureFlags featureFlags, NotificationRoundnessManager notificationRoundnessManager) {
            this.mResources = resources;
            this.mViewConfiguration = viewConfiguration;
            this.mDumpManager = dumpManager;
            this.mFeatureFlags = featureFlags;
            this.mNotificationRoundnessManager = notificationRoundnessManager;
        }

        NotificationSwipeHelper build() {
            NotificationSwipeHelper notificationSwipeHelper = new NotificationSwipeHelper(this.mResources, this.mViewConfiguration, this.mFeatureFlags, this.mNotificationCallback, this.mNotificationRoundnessManager);
            this.mDumpManager.registerDumpable(notificationSwipeHelper);
            return notificationSwipeHelper;
        }

        Builder setNotificationCallback(NotificationCallback notificationCallback) {
            this.mNotificationCallback = notificationCallback;
            return this;
        }
    }

    public interface NotificationCallback extends SwipeHelper.Callback {
        float getTotalTranslationLength(View view);

        void handleChildViewDismissed(View view);

        void onDismiss();

        boolean shouldDismissQuickly();
    }

    /* JADX INFO: renamed from: $r8$lambda$b6sZrs_-sA-ecUJi0hm-mP0v5hE, reason: not valid java name */
    public static /* synthetic */ void m2039$r8$lambda$b6sZrs_sAecUJi0hmmP0v5hE() {
    }

    NotificationSwipeHelper(Resources resources, ViewConfiguration viewConfiguration, FeatureFlags featureFlags, NotificationCallback notificationCallback, NotificationRoundnessManager notificationRoundnessManager) {
        super(notificationCallback, resources, viewConfiguration, featureFlags);
        this.mNotificationRoundnessManager = notificationRoundnessManager;
        this.mCallback = notificationCallback;
        this.mFalsingCheck = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationSwipeHelper.m2039$r8$lambda$b6sZrs_sAecUJi0hmmP0v5hE();
            }
        };
    }

    public void clearExposedMenuView() {
        setExposedMenuView(null);
    }

    public void clearTranslatingParentView() {
        setTranslatingParentView(null);
    }

    @Override // com.android.systemui.SwipeHelper
    protected Animator createTranslationAnimation(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        return super.createTranslationAnimation(view, f, animatorUpdateListener);
    }

    @Override // com.android.systemui.SwipeHelper
    public void dismissChild(View view, float f, boolean z) {
        superDismissChild(view, f, z);
        if (this.mCallback.shouldDismissQuickly()) {
            this.mCallback.handleChildViewDismissed(view);
        }
        this.mCallback.onDismiss();
        handleMenuCoveredOrDismissed();
    }

    @Override // com.android.systemui.SwipeHelper
    protected float getEscapeVelocity() {
        return super.getEscapeVelocity();
    }

    public View getExposedMenuView() {
        return this.mMenuExposedView;
    }

    protected Runnable getFalsingCheck() {
        return this.mFalsingCheck;
    }

    protected Handler getHandler() {
        return this.mHandler;
    }

    @Override // com.android.systemui.SwipeHelper
    protected float getTotalTranslationLength(View view) {
        return this.mCallback.getTotalTranslationLength(view);
    }

    public View getTranslatingParentView() {
        return this.mTranslatingParentView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.SwipeHelper
    public float getTranslation(View view) {
        if (view instanceof SwipeableView) {
            return ((SwipeableView) view).getTranslation();
        }
        return 0.0f;
    }

    @Override // com.android.systemui.SwipeHelper
    protected Animator getViewTranslationAnimator(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        return super.getViewTranslationAnimator(view, f, animatorUpdateListener);
    }

    protected void handleMenuCoveredOrDismissed() {
        View exposedMenuView = getExposedMenuView();
        if (exposedMenuView == null || exposedMenuView != this.mTranslatingParentView) {
            return;
        }
        clearExposedMenuView();
    }

    @Override // com.android.systemui.SwipeHelper
    public boolean handleUpEvent(MotionEvent motionEvent, View view, float f, float f2) {
        return false;
    }

    protected void initializeRow(SwipeableView swipeableView) {
    }

    @Override // com.android.systemui.SwipeHelper
    protected void onChildSnappedBack(View view, float f) {
        super.onChildSnappedBack(view, f);
        InteractionJankMonitor.getInstance().end(4);
    }

    @Override // com.android.systemui.SwipeHelper
    protected void onDismissChildWithAnimationFinished() {
        InteractionJankMonitor.getInstance().end(4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.SwipeHelper
    public void onDownUpdate(View view, MotionEvent motionEvent) {
        this.mTranslatingParentView = view;
        getHandler().removeCallbacks(getFalsingCheck());
        if (view instanceof SwipeableView) {
            initializeRow((SwipeableView) view);
        }
    }

    @Override // com.android.systemui.SwipeHelper, com.android.systemui.Gefingerpoken
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean zIsSwiping = isSwiping();
        boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        View swipedView = getSwipedView();
        if (!zIsSwiping && swipedView != null) {
            InteractionJankMonitor.getInstance().begin(swipedView, 4);
        }
        return zOnInterceptTouchEvent;
    }

    @Override // com.android.systemui.SwipeHelper
    public void onMoveUpdate(View view, MotionEvent motionEvent, float f, float f2) {
        getHandler().removeCallbacks(getFalsingCheck());
    }

    @Override // com.android.systemui.SwipeHelper
    protected void prepareDismissAnimation(View view, Animator animator) {
        super.prepareDismissAnimation(view, animator);
        if ((view instanceof ExpandableNotificationRow) && this.mNotificationRoundnessManager.isClearAllInProgress()) {
            final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            animator.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator2) {
                    expandableNotificationRow.requestRoundnessReset(NotificationSwipeHelper.SWIPE_DISMISS);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    expandableNotificationRow.requestRoundnessReset(NotificationSwipeHelper.SWIPE_DISMISS);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                    expandableNotificationRow.requestRoundness(1.0f, 1.0f, NotificationSwipeHelper.SWIPE_DISMISS);
                }
            });
        }
    }

    public void setExposedMenuView(View view) {
        this.mMenuExposedView = view;
    }

    protected void setTranslatingParentView(View view) {
        this.mTranslatingParentView = view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.SwipeHelper
    public void setTranslation(View view, float f) {
        if (view instanceof SwipeableView) {
            ((SwipeableView) view).setTranslation(f);
        }
    }

    protected boolean shouldResetMenu(boolean z) {
        View view = this.mMenuExposedView;
        if (view != null) {
            return z || view != this.mTranslatingParentView;
        }
        return false;
    }

    @Override // com.android.systemui.SwipeHelper
    protected void snapChild(View view, float f, float f2) {
        if (view instanceof SwipeableView) {
            superSnapChild(view, f, f2);
        }
        this.mCallback.onDragCancelled(view);
        if (f == 0.0f) {
            handleMenuCoveredOrDismissed();
        }
    }

    protected void snapClosed(View view, float f) {
        snapChild(view, 0.0f, f);
    }

    protected void superDismissChild(View view, float f, boolean z) {
        super.dismissChild(view, f, z);
    }

    protected void superSnapChild(View view, float f, float f2) {
        super.snapChild(view, f, f2);
    }

    @Override // com.android.systemui.SwipeHelper
    protected boolean swipedFarEnough() {
        return super.swipedFarEnough();
    }

    @Override // com.android.systemui.SwipeHelper
    protected boolean swipedFastEnough() {
        return super.swipedFastEnough();
    }

    @Override // com.android.systemui.SwipeHelper
    protected void updateSwipeProgressAlpha(View view, float f) {
        if (view instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) view).setContentAlpha(f);
        }
    }
}
