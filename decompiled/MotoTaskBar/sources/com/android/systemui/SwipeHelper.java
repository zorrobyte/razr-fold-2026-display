package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.res.Resources;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.res.R$bool;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public abstract class SwipeHelper implements Gefingerpoken, Dumpable {
    private final Callback mCallback;
    private boolean mCanCurrViewBeDimissed;
    private float mDensityScale;
    private boolean mDisableHwLayers;
    private final boolean mFadeDependingOnAmountSwiped;
    private final int mFalsingThreshold;
    private final FeatureFlags mFeatureFlags;
    private final FlingAnimationUtils mFlingAnimationUtils;
    private float mInitialTouchPos;
    private boolean mIsSwiping;
    private boolean mLongPressSent;
    private boolean mMenuRowIntercepting;
    private float mPagingTouchSlop;
    private float mPerpendicularInitialTouchPos;
    private final float mSlopMultiplier;
    private boolean mSnappingChild;
    private boolean mTouchAboveFalsingThreshold;
    private int mTouchSlop;
    private View mTouchedView;
    private final PhysicsAnimator.SpringConfig mSnapBackSpringConfig = new PhysicsAnimator.SpringConfig(200.0f, 0.75f);
    private float mTranslation = 0.0f;
    private final float[] mDownLocation = new float[2];
    private final Runnable mPerformLongPress = new Runnable() { // from class: com.android.systemui.SwipeHelper.1
        private final int[] mViewOffset = new int[2];

        @Override // java.lang.Runnable
        public void run() {
            if (SwipeHelper.this.mTouchedView == null || SwipeHelper.this.mLongPressSent) {
                return;
            }
            SwipeHelper.this.mLongPressSent = true;
            if (SwipeHelper.this.mTouchedView instanceof ExpandableNotificationRow) {
                SwipeHelper.this.mTouchedView.getLocationOnScreen(this.mViewOffset);
                int i = ((int) SwipeHelper.this.mDownLocation[0]) - this.mViewOffset[0];
                int i2 = ((int) SwipeHelper.this.mDownLocation[1]) - this.mViewOffset[1];
                SwipeHelper.this.mTouchedView.sendAccessibilityEvent(2);
                ((ExpandableNotificationRow) SwipeHelper.this.mTouchedView).doLongClickCallback(i, i2);
                SwipeHelper swipeHelper = SwipeHelper.this;
                if (swipeHelper.isAvailableToDragAndDrop(swipeHelper.mTouchedView)) {
                    SwipeHelper.this.mCallback.onLongPressSent(SwipeHelper.this.mTouchedView);
                }
            }
        }
    };
    private final ArrayMap mDismissPendingMap = new ArrayMap();
    protected final Handler mHandler = new Handler();
    private final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private float mTouchSlopMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
    private final long mLongPressTimeout = (long) (ViewConfiguration.getLongPressTimeout() * 1.5f);

    public interface Callback {
        boolean canChildBeDismissed(View view);

        boolean canChildBeDismissedInDirection(View view, boolean z);

        default boolean canChildBeDragged(View view) {
            return true;
        }

        View getChildAtPosition(MotionEvent motionEvent);

        int getConstrainSwipeStartPosition();

        float getFalsingThresholdFactor();

        void onBeginDrag(View view);

        void onChildDismissed(View view);

        void onChildSnappedBack(View view, float f);

        void onDragCancelled(View view);

        void onLongPressSent(View view);

        boolean updateSwipeProgress(View view, boolean z, float f);
    }

    public SwipeHelper(Callback callback, Resources resources, ViewConfiguration viewConfiguration, FeatureFlags featureFlags) {
        this.mCallback = callback;
        this.mPagingTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mDensityScale = resources.getDisplayMetrics().density;
        this.mFalsingThreshold = resources.getDimensionPixelSize(R$dimen.swipe_helper_falsing_threshold);
        this.mFadeDependingOnAmountSwiped = resources.getBoolean(R$bool.config_fadeDependingOnAmountSwiped);
        this.mFeatureFlags = featureFlags;
        this.mFlingAnimationUtils = new FlingAnimationUtils(resources.getDisplayMetrics(), getMaxEscapeAnimDuration() / 1000.0f);
    }

    private void cancelSnapbackAnimation(View view) {
        PhysicsAnimator.getInstance(view).cancel();
    }

    private void cancelTranslateAnimation(View view) {
        if (view instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) view).cancelTranslateAnimation();
        }
        cancelSnapbackAnimation(view);
    }

    private PhysicsAnimator createSnapBackAnimation(View view, float f, float f2) {
        return view instanceof ExpandableNotificationRow ? PhysicsAnimator.getInstance((ExpandableNotificationRow) view).spring(FloatPropertyCompat.createFloatPropertyCompat(ExpandableNotificationRow.TRANSLATE_CONTENT), f, f2, this.mSnapBackSpringConfig) : PhysicsAnimator.getInstance(view).spring(DynamicAnimation.TRANSLATION_X, f, f2, this.mSnapBackSpringConfig);
    }

    private int getFalsingThreshold() {
        return (int) (this.mFalsingThreshold * this.mCallback.getFalsingThresholdFactor());
    }

    private float getMaxVelocity() {
        return this.mDensityScale * 4000.0f;
    }

    private float getPerpendicularPos(MotionEvent motionEvent) {
        return motionEvent.getY();
    }

    private float getPos(MotionEvent motionEvent) {
        return motionEvent.getX();
    }

    private float getSwipeProgressForOffset(View view, float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        return Math.min(Math.max(0.0f, Math.abs(f / getSize(view))), 1.0f);
    }

    private float getTouchSlop(MotionEvent motionEvent) {
        return motionEvent.getClassification() == 1 ? this.mTouchSlop * this.mTouchSlopMultiplier : this.mTouchSlop;
    }

    private float getVelocity(VelocityTracker velocityTracker) {
        return velocityTracker.getXVelocity();
    }

    public static void invalidateGlobalRegion(View view) {
        Trace.beginSection("SwipeHelper.invalidateGlobalRegion");
        invalidateGlobalRegion(view, new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        Trace.endSection();
    }

    public static void invalidateGlobalRegion(View view, RectF rectF) {
        while (view.getParent() != null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
            view.getMatrix().mapRect(rectF);
            view.invalidate((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAvailableToDragAndDrop(View view) {
        if (!this.mFeatureFlags.isEnabled(com.android.systemui.flags.Flags.NOTIFICATION_DRAG_TO_CONTENTS) || !(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        boolean zCanBubble = expandableNotificationRow.getEntry().canBubble();
        Notification notification = expandableNotificationRow.getEntry().getSbn().getNotification();
        PendingIntent pendingIntent = notification.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification.fullScreenIntent;
        }
        return (pendingIntent == null || !pendingIntent.isActivity() || zCanBubble) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$snapChild$0(boolean z, View view, ArrayMap arrayMap) {
        onTranslationUpdate(view, getTranslation(view), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$snapChild$1(View view, boolean z, float f, View view2, FloatPropertyCompat floatPropertyCompat, boolean z2, boolean z3, float f2, float f3, boolean z4) {
        this.mSnappingChild = false;
        if (!z3) {
            updateSwipeProgressFromOffset(view, z);
            resetViewIfSwiping(view);
            if (view == this.mTouchedView && !this.mIsSwiping) {
                this.mTouchedView = null;
            }
        }
        onChildSnappedBack(view, f);
    }

    private void resetSwipeState() {
        resetSwipeStates(false);
    }

    private void resetSwipeStates(boolean z) {
        View view = this.mTouchedView;
        boolean z2 = this.mSnappingChild;
        boolean z3 = this.mIsSwiping;
        this.mTouchedView = null;
        this.mIsSwiping = false;
        boolean z4 = z | z3;
        if (z4) {
            this.mSnappingChild = false;
        }
        if (view == null) {
            return;
        }
        boolean z5 = z4 && z2;
        if (z5) {
            cancelTranslateAnimation(view);
        }
        if (z4) {
            snapChildIfNeeded(view, false, 0.0f);
        }
        if (z3 || z5) {
            onChildSnappedBack(view, 0.0f);
        }
    }

    private void snapChildInstantly(View view) {
        boolean zCanChildBeDismissed = this.mCallback.canChildBeDismissed(view);
        setTranslation(view, 0.0f);
        updateSwipeProgressFromOffset(view, zCanChildBeDismissed);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSwipeProgressFromOffset(View view, boolean z) {
        updateSwipeProgressFromOffset(view, z, getTranslation(view));
    }

    private void updateSwipeProgressFromOffset(View view, boolean z, float f) {
        float swipeProgressForOffset = getSwipeProgressForOffset(view, f);
        if (!this.mCallback.updateSwipeProgress(view, z, swipeProgressForOffset) && z) {
            if (!this.mDisableHwLayers) {
                if (swipeProgressForOffset == 0.0f || swipeProgressForOffset == 1.0f) {
                    view.setLayerType(0, null);
                } else {
                    view.setLayerType(2, null);
                }
            }
            updateSwipeProgressAlpha(view, getSwipeAlpha(swipeProgressForOffset));
        }
        invalidateGlobalRegion(view);
    }

    public void cancelLongPress() {
        this.mHandler.removeCallbacks(this.mPerformLongPress);
    }

    protected Animator createTranslationAnimation(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_X, f);
        if (animatorUpdateListener != null) {
            objectAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
        }
        return objectAnimatorOfFloat;
    }

    public void dismissChild(final View view, float f, final Consumer consumer, long j, boolean z, long j2, boolean z2) {
        final boolean zCanChildBeDismissed = this.mCallback.canChildBeDismissed(view);
        boolean z3 = false;
        boolean z4 = view.getLayoutDirection() == 1;
        if (f == 0.0f && ((getTranslation(view) == 0.0f || z2) && z4)) {
            z3 = true;
        }
        float totalTranslationLength = ((Math.abs(f) <= getEscapeVelocity() || f >= 0.0f) && (getTranslation(view) >= 0.0f || z2) && !z3) ? getTotalTranslationLength(view) : -getTotalTranslationLength(view);
        long jMin = j2 == 0 ? f != 0.0f ? Math.min(400L, (int) ((Math.abs(totalTranslationLength - getTranslation(view)) * 1000.0f) / Math.abs(f))) : 200L : j2;
        if (!this.mDisableHwLayers) {
            view.setLayerType(2, null);
        }
        Animator viewTranslationAnimator = getViewTranslationAnimator(view, totalTranslationLength, new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.SwipeHelper.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                SwipeHelper.this.onTranslationUpdate(view, ((Float) valueAnimator.getAnimatedValue()).floatValue(), zCanChildBeDismissed);
            }
        });
        if (viewTranslationAnimator == null) {
            onDismissChildWithAnimationFinished();
            return;
        }
        if (z) {
            viewTranslationAnimator.setInterpolator(com.android.app.animation.Interpolators.FAST_OUT_LINEAR_IN);
            viewTranslationAnimator.setDuration(jMin);
        } else {
            this.mFlingAnimationUtils.applyDismissing(viewTranslationAnimator, getTranslation(view), totalTranslationLength, f, getSize(view));
        }
        if (j > 0) {
            viewTranslationAnimator.setStartDelay(j);
        }
        viewTranslationAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.SwipeHelper.3
            private boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            /* JADX WARN: Removed duplicated region for block: B:14:0x0044  */
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public void onAnimationEnd(android.animation.Animator r4) {
                /*
                    r3 = this;
                    com.android.systemui.SwipeHelper r4 = com.android.systemui.SwipeHelper.this
                    android.view.View r0 = r2
                    boolean r1 = r3
                    com.android.systemui.SwipeHelper.m1178$$Nest$mupdateSwipeProgressFromOffset(r4, r0, r1)
                    com.android.systemui.SwipeHelper r4 = com.android.systemui.SwipeHelper.this
                    android.util.ArrayMap r4 = com.android.systemui.SwipeHelper.m1171$$Nest$fgetmDismissPendingMap(r4)
                    android.view.View r0 = r2
                    r4.remove(r0)
                    android.view.View r4 = r2
                    boolean r0 = r4 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                    r1 = 0
                    if (r0 == 0) goto L44
                    com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r4
                    com.android.systemui.SwipeHelper r0 = com.android.systemui.SwipeHelper.this
                    com.android.systemui.flags.FeatureFlags r0 = com.android.systemui.SwipeHelper.m1173$$Nest$fgetmFeatureFlags(r0)
                    com.android.systemui.flags.ReleasedFlag r2 = com.android.systemui.flags.Flags.SWIPE_UNCLEARED_TRANSIENT_VIEW_FIX
                    boolean r0 = r0.isEnabled(r2)
                    if (r0 == 0) goto L3f
                    android.view.ViewGroup r0 = r4.getTransientContainer()
                    if (r0 != 0) goto L3d
                    android.view.ViewParent r0 = r4.getParent()
                    if (r0 == 0) goto L3d
                    boolean r4 = r4.isRemoved()
                    if (r4 == 0) goto L44
                L3d:
                    r4 = 1
                    goto L45
                L3f:
                    boolean r4 = r4.isRemoved()
                    goto L45
                L44:
                    r4 = r1
                L45:
                    boolean r0 = r3.mCancelled
                    if (r0 == 0) goto L4b
                    if (r4 == 0) goto L5d
                L4b:
                    com.android.systemui.SwipeHelper r4 = com.android.systemui.SwipeHelper.this
                    com.android.systemui.SwipeHelper$Callback r4 = com.android.systemui.SwipeHelper.m1169$$Nest$fgetmCallback(r4)
                    android.view.View r0 = r2
                    r4.onChildDismissed(r0)
                    com.android.systemui.SwipeHelper r4 = com.android.systemui.SwipeHelper.this
                    android.view.View r0 = r2
                    r4.resetViewIfSwiping(r0)
                L5d:
                    java.util.function.Consumer r4 = r4
                    if (r4 == 0) goto L6a
                    boolean r0 = r3.mCancelled
                    java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                    r4.accept(r0)
                L6a:
                    com.android.systemui.SwipeHelper r4 = com.android.systemui.SwipeHelper.this
                    boolean r4 = com.android.systemui.SwipeHelper.m1170$$Nest$fgetmDisableHwLayers(r4)
                    if (r4 != 0) goto L78
                    android.view.View r4 = r2
                    r0 = 0
                    r4.setLayerType(r1, r0)
                L78:
                    com.android.systemui.SwipeHelper r3 = com.android.systemui.SwipeHelper.this
                    r3.onDismissChildWithAnimationFinished()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.AnonymousClass3.onAnimationEnd(android.animation.Animator):void");
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SwipeHelper.this.mCallback.onBeginDrag(view);
            }
        });
        prepareDismissAnimation(view, viewTranslationAnimator);
        this.mDismissPendingMap.put(view, viewTranslationAnimator);
        viewTranslationAnimator.start();
    }

    public void dismissChild(View view, float f, boolean z) {
        dismissChild(view, f, null, 0L, z, 0L, false);
    }

    protected float getEscapeVelocity() {
        return getUnscaledEscapeVelocity() * this.mDensityScale;
    }

    protected long getMaxEscapeAnimDuration() {
        return 400L;
    }

    protected float getSize(View view) {
        return view.getMeasuredWidth();
    }

    public float getSwipeAlpha(float f) {
        return this.mFadeDependingOnAmountSwiped ? Math.max(1.0f - f, 0.0f) : 1.0f - Math.max(0.0f, Math.min(1.0f, f / 0.6f));
    }

    public View getSwipedView() {
        if (this.mIsSwiping) {
            return this.mTouchedView;
        }
        return null;
    }

    protected abstract float getTotalTranslationLength(View view);

    protected abstract float getTranslation(View view);

    protected float getUnscaledEscapeVelocity() {
        return 500.0f;
    }

    protected Animator getViewTranslationAnimator(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        cancelSnapbackAnimation(view);
        return view instanceof ExpandableNotificationRow ? ((ExpandableNotificationRow) view).getTranslateViewAnimator(f, animatorUpdateListener) : createTranslationAnimation(view, f, animatorUpdateListener);
    }

    protected abstract boolean handleUpEvent(MotionEvent motionEvent, View view, float f, float f2);

    public boolean isDismissGesture(MotionEvent motionEvent) {
        float translation = getTranslation(this.mTouchedView);
        if (motionEvent.getActionMasked() == 1 && !isFalseGesture() && (swipedFastEnough() || swipedFarEnough())) {
            if (this.mCallback.canChildBeDismissedInDirection(this.mTouchedView, translation > 0.0f)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFalseGesture() {
        return false;
    }

    public boolean isSwiping() {
        return this.mIsSwiping;
    }

    protected void onChildSnappedBack(View view, float f) {
        this.mCallback.onChildSnappedBack(view, f);
    }

    protected abstract void onDismissChildWithAnimationFinished();

    public abstract void onDownUpdate(View view, MotionEvent motionEvent);

    /* JADX WARN: Removed duplicated region for block: B:31:0x0090  */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r8) {
        /*
            Method dump skipped, instruction units count: 292
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    protected abstract void onMoveUpdate(View view, MotionEvent motionEvent, float f, float f2);

    /* JADX WARN: Removed duplicated region for block: B:23:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d6  */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            Method dump skipped, instruction units count: 283
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void onTranslationUpdate(View view, float f, boolean z) {
        updateSwipeProgressFromOffset(view, z, f);
    }

    protected void prepareDismissAnimation(View view, Animator animator) {
    }

    protected void resetViewIfSwiping(View view) {
        if (getSwipedView() == view) {
            resetSwipeState();
        }
    }

    public void setDensityScale(float f) {
        this.mDensityScale = f;
    }

    public void setPagingTouchSlop(float f) {
        this.mPagingTouchSlop = f;
    }

    protected abstract void setTranslation(View view, float f);

    protected void snapChild(final View view, final float f, float f2) {
        final boolean zCanChildBeDismissed = this.mCallback.canChildBeDismissed(view);
        cancelTranslateAnimation(view);
        PhysicsAnimator physicsAnimatorCreateSnapBackAnimation = createSnapBackAnimation(view, f, f2);
        physicsAnimatorCreateSnapBackAnimation.addUpdateListener(new PhysicsAnimator.UpdateListener() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.UpdateListener
            public final void onAnimationUpdateForProperty(Object obj, ArrayMap arrayMap) {
                this.f$0.lambda$snapChild$0(zCanChildBeDismissed, (View) obj, arrayMap);
            }
        });
        physicsAnimatorCreateSnapBackAnimation.addEndListener(new PhysicsAnimator.EndListener() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda1
            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.EndListener
            public final void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z, boolean z2, float f3, float f4, boolean z3) {
                this.f$0.lambda$snapChild$1(view, zCanChildBeDismissed, f, (View) obj, floatPropertyCompat, z, z2, f3, f4, z3);
            }
        });
        this.mSnappingChild = true;
        physicsAnimatorCreateSnapBackAnimation.start();
    }

    public void snapChildIfNeeded(View view, boolean z, float f) {
        if ((this.mIsSwiping && this.mTouchedView == view) || this.mSnappingChild) {
            return;
        }
        Animator animator = (Animator) this.mDismissPendingMap.get(view);
        if (animator != null) {
            animator.cancel();
        } else if (getTranslation(view) == 0.0f) {
            return;
        }
        if (z) {
            snapChild(view, f, 0.0f);
        } else {
            snapChildInstantly(view);
        }
    }

    protected boolean swipedFarEnough() {
        return Math.abs(getTranslation(this.mTouchedView)) > getSize(this.mTouchedView) * 0.6f;
    }

    protected boolean swipedFastEnough() {
        float velocity = getVelocity(this.mVelocityTracker);
        float translation = getTranslation(this.mTouchedView);
        if (Math.abs(velocity) > getEscapeVelocity()) {
            if ((velocity > 0.0f) == (translation > 0.0f)) {
                return true;
            }
        }
        return false;
    }

    protected abstract void updateSwipeProgressAlpha(View view, float f);
}
