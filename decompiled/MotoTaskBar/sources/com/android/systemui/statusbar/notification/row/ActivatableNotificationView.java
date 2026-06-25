package com.android.systemui.statusbar.notification.row;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.fake.FlagsFake;
import com.android.systemui.res.R$color;
import com.android.systemui.statusbar.notification.FakeShadowView;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.shared.NotificationsImprovedHunAnimation;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class ActivatableNotificationView extends ExpandableOutlineView {
    private float mAnimationTranslationY;
    private float mAppearAnimationFraction;
    private float mAppearAnimationTranslation;
    private ValueAnimator mAppearAnimator;
    private ValueAnimator mBackgroundColorAnimator;
    protected NotificationBackgroundView mBackgroundNormal;
    int mBgTint;
    private Interpolator mCurrentAppearInterpolator;
    private int mCurrentBackgroundTint;
    private boolean mDismissed;
    private boolean mDrawingAppearAnimation;
    private FakeShadowView mFakeShadow;
    private boolean mIsBelowSpeedBump;
    private boolean mIsHeadsUpAnimation;
    private long mLastActionUpTime;
    private float mNormalBackgroundVisibilityAmount;
    private int mNormalColor;
    private int mNormalRippleColor;
    private final Set mOnDetachResetRoundness;
    private float mOverrideAmount;
    private int mOverrideTint;
    private boolean mRefocusOnDismiss;
    private boolean mShadowHidden;
    private int mStartTint;
    protected Point mTargetPoint;
    private int mTargetTint;
    private int mTintedRippleColor;
    private Gefingerpoken mTouchHandler;

    public ActivatableNotificationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnDetachResetRoundness = new HashSet();
        this.mBgTint = 0;
        this.mAppearAnimationFraction = -1.0f;
        setClipChildren(false);
        setClipToPadding(false);
        updateColors();
    }

    private void applyBackgroundRoundness(float f, float f2) {
        this.mBackgroundNormal.setRadius(f, f2);
    }

    private int calculateBgColor(boolean z, boolean z2) {
        int i;
        return (!z2 || this.mOverrideTint == 0) ? (!z || (i = this.mBgTint) == 0) ? this.mNormalColor : i : NotificationUtils.interpolateColors(calculateBgColor(z, false), this.mOverrideTint, this.mOverrideAmount);
    }

    private void cancelAppearAnimation() {
        ValueAnimator valueAnimator = this.mAppearAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mAppearAnimator = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableAppearDrawing(boolean z) {
        if (z != this.mDrawingAppearAnimation) {
            this.mDrawingAppearAnimation = z;
            if (!z) {
                setContentAlpha(1.0f);
                this.mAppearAnimationFraction = -1.0f;
                setOutlineRect(null);
            }
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCujType(boolean z) {
        return this.mIsHeadsUpAnimation ? z ? 12 : 13 : z ? 14 : 15;
    }

    private float getInterpolatedAppearAnimationFraction() {
        float f = this.mAppearAnimationFraction;
        if (f >= 0.0f) {
            return this.mCurrentAppearInterpolator.getInterpolation(f);
        }
        return 1.0f;
    }

    private int getRippleColor() {
        return this.mBgTint != 0 ? this.mTintedRippleColor : this.mNormalRippleColor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAppearAnimation$1(ValueAnimator valueAnimator) {
        this.mAppearAnimationFraction = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        updateAppearAnimationAlpha();
        updateAppearRect();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAppearAnimation$2(ValueAnimator valueAnimator, long j) {
        ValueAnimator valueAnimator2 = this.mAppearAnimator;
        if (valueAnimator2 == valueAnimator) {
            valueAnimator2.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateBackgroundTint$0(ValueAnimator valueAnimator) {
        setBackgroundTintColor(NotificationUtils.interpolateColors(this.mStartTint, this.mTargetTint, valueAnimator.getAnimatedFraction()));
    }

    private void setContentAlpha(float f) {
        setAlphaAndLayerType(getContentView(), f);
        if (f == 1.0f) {
            resetAllContentAlphas();
        }
    }

    private void startAppearAnimation(final boolean z, float f, long j, long j2, final Runnable runnable, final Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter) {
        this.mAnimationTranslationY = f * getActualHeight();
        cancelAppearAnimation();
        float f2 = 1.0f;
        if (this.mAppearAnimationFraction == -1.0f) {
            if (z) {
                this.mAppearAnimationFraction = 0.0f;
                this.mAppearAnimationTranslation = this.mAnimationTranslationY;
            } else {
                this.mAppearAnimationFraction = 1.0f;
                this.mAppearAnimationTranslation = 0.0f;
            }
        }
        if (z) {
            this.mCurrentAppearInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        } else {
            this.mCurrentAppearInterpolator = Interpolators.FAST_OUT_SLOW_IN_REVERSE;
            f2 = 0.0f;
        }
        this.mAppearAnimator = ValueAnimator.ofFloat(this.mAppearAnimationFraction, f2);
        if (NotificationsImprovedHunAnimation.isEnabled()) {
            this.mAppearAnimator.setInterpolator(this.mCurrentAppearInterpolator);
        } else {
            this.mAppearAnimator.setInterpolator(Interpolators.LINEAR);
        }
        this.mAppearAnimator.setDuration((long) (j2 * Math.abs(this.mAppearAnimationFraction - f2)));
        this.mAppearAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$startAppearAnimation$1(valueAnimator);
            }
        });
        if (animatorListenerAdapter != null) {
            this.mAppearAnimator.addListener(animatorListenerAdapter);
        }
        updateAppearAnimationAlpha();
        updateAppearRect();
        this.mAppearAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView.2
            private boolean mRunWithoutInterruptions;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.mRunWithoutInterruptions = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Runnable runnable3 = runnable2;
                if (runnable3 != null) {
                    runnable3.run();
                }
                if (this.mRunWithoutInterruptions) {
                    ActivatableNotificationView.this.enableAppearDrawing(false);
                }
                ActivatableNotificationView.this.onAppearAnimationFinished(z);
                if (this.mRunWithoutInterruptions) {
                    InteractionJankMonitor.getInstance().end(ActivatableNotificationView.this.getCujType(z));
                } else {
                    InteractionJankMonitor.getInstance().cancel(ActivatableNotificationView.this.getCujType(z));
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    runnable3.run();
                }
                this.mRunWithoutInterruptions = true;
                InteractionJankMonitor.getInstance().begin(InteractionJankMonitor.Configuration.Builder.withView(ActivatableNotificationView.this.getCujType(z), ActivatableNotificationView.this));
            }
        });
        final ValueAnimator valueAnimator = this.mAppearAnimator;
        Choreographer.getInstance().postFrameCallbackDelayed(new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView$$ExternalSyntheticLambda2
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j3) {
                this.f$0.lambda$startAppearAnimation$2(valueAnimator, j3);
            }
        }, j);
    }

    private void updateAppearAnimationAlpha() {
        setContentAlpha(Interpolators.ALPHA_IN.getInterpolation((MathUtils.constrain(this.mAppearAnimationFraction, 0.7f, 1.0f) - 0.7f) / 0.3f));
    }

    private void updateAppearRect() {
        float interpolation = NotificationsImprovedHunAnimation.isEnabled() ? this.mAppearAnimationFraction : this.mCurrentAppearInterpolator.getInterpolation(this.mAppearAnimationFraction);
        this.mAppearAnimationTranslation = (1.0f - interpolation) * this.mAnimationTranslationY;
        float actualHeight = getActualHeight();
        float f = interpolation * actualHeight;
        if (this.mTargetPoint == null) {
            setOutlineRect(0.0f, this.mAppearAnimationTranslation, getWidth(), f + this.mAppearAnimationTranslation);
            return;
        }
        int width = getWidth();
        float f2 = 1.0f - this.mAppearAnimationFraction;
        Point point = this.mTargetPoint;
        int i = point.x;
        float f3 = this.mAnimationTranslationY;
        setOutlineRect(i * f2, f3 + ((f3 - point.y) * f2), width - ((width - i) * f2), actualHeight - ((r2 - r4) * f2));
    }

    private void updateBackgroundTint(boolean z) {
        ValueAnimator valueAnimator = this.mBackgroundColorAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mBackgroundNormal.setRippleColor(getRippleColor());
        int iCalculateBgColor = calculateBgColor();
        if (!z) {
            setBackgroundTintColor(iCalculateBgColor);
            return;
        }
        int i = this.mCurrentBackgroundTint;
        if (iCalculateBgColor != i) {
            this.mStartTint = i;
            this.mTargetTint = iCalculateBgColor;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mBackgroundColorAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$updateBackgroundTint$0(valueAnimator2);
                }
            });
            this.mBackgroundColorAnimator.setDuration(360L);
            this.mBackgroundColorAnimator.setInterpolator(Interpolators.LINEAR);
            this.mBackgroundColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ActivatableNotificationView.this.mBackgroundColorAnimator = null;
                }
            });
            this.mBackgroundColorAnimator.start();
        }
    }

    private void updateColors() {
        this.mNormalColor = Utils.getColorAttrDefaultColor(((FrameLayout) this).mContext, R.^attr-private.colorSurfaceHeader);
        this.mTintedRippleColor = ((FrameLayout) this).mContext.getColor(R$color.notification_ripple_tinted_color);
        this.mNormalRippleColor = ((FrameLayout) this).mContext.getColor(R$color.notification_ripple_untinted_color);
        this.mBgTint = 0;
        this.mOverrideTint = 0;
        this.mOverrideAmount = 0.0f;
    }

    private void updateOutlineAlpha() {
        setOutlineAlpha((0.3f * this.mNormalBackgroundVisibilityAmount) + 0.7f);
    }

    public void addOnDetachResetRoundness(SourceType sourceType) {
        this.mOnDetachResetRoundness.add(sourceType);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.Roundable
    public void applyRoundnessAndInvalidate() {
        applyBackgroundRoundness(getTopCornerRadius(), getBottomCornerRadius());
        super.applyRoundnessAndInvalidate();
    }

    public int calculateBgColor() {
        return calculateBgColor(true, true);
    }

    public void cancelAppearDrawing() {
        cancelAppearAnimation();
        enableAppearDrawing(false);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    protected boolean childNeedsClipping(View view) {
        if ((view instanceof NotificationBackgroundView) && isClippingNeeded()) {
            return true;
        }
        return super.childNeedsClipping(view);
    }

    public void dismiss(boolean z) {
        this.mDismissed = true;
        this.mRefocusOnDismiss = z;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mDrawingAppearAnimation) {
            canvas.save();
            canvas.translate(0.0f, this.mAppearAnimationTranslation);
        }
        super.dispatchDraw(canvas);
        if (this.mDrawingAppearAnimation) {
            canvas.restore();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.mBackgroundNormal.setState(getDrawableState());
    }

    public int getBackgroundColorWithoutTint() {
        return calculateBgColor(false, false);
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public float getBottomCornerRadius() {
        if (NotificationsImprovedHunAnimation.isEnabled()) {
            return super.getBottomCornerRadius();
        }
        return MathUtils.lerp(0.0f, super.getBottomCornerRadius(), getInterpolatedAppearAnimationFraction());
    }

    protected abstract View getContentView();

    public int getCurrentBackgroundTint() {
        return this.mCurrentBackgroundTint;
    }

    protected int getNormalBgColor() {
        return this.mNormalColor;
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public float getTopCornerRadius() {
        if (NotificationsImprovedHunAnimation.isEnabled()) {
            return super.getTopCornerRadius();
        }
        return MathUtils.lerp(0.0f, super.getTopCornerRadius(), getInterpolatedAppearAnimationFraction());
    }

    protected boolean hideBackground() {
        return false;
    }

    protected void initBackground() {
        this.mBackgroundNormal.setCustomBackground(R$drawable.notification_material_bg);
    }

    public boolean isDismissed() {
        return this.mDismissed;
    }

    protected void onAppearAnimationFinished(boolean z) {
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mOnDetachResetRoundness.isEmpty()) {
            return;
        }
        Iterator it = this.mOnDetachResetRoundness.iterator();
        while (it.hasNext()) {
            requestRoundnessReset((SourceType) it.next());
        }
        this.mOnDetachResetRoundness.clear();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mBackgroundNormal = (NotificationBackgroundView) findViewById(R$id.backgroundNormal);
        FakeShadowView fakeShadowView = (FakeShadowView) findViewById(R$id.fake_shadow);
        this.mFakeShadow = fakeShadowView;
        this.mShadowHidden = fakeShadowView.getVisibility() != 0;
        initBackground();
        updateBackgroundTint();
        updateOutlineAlpha();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.mTouchHandler;
        if (gefingerpoken == null || !gefingerpoken.onInterceptTouchEvent(motionEvent)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setPivotX(getWidth() / 2);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void performAddAnimation(long j, long j2, boolean z, Runnable runnable) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(true, z ? 0.0f : -1.0f, j, j2, null, null, null);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public long performRemoveAnimation(long j, long j2, float f, boolean z, Runnable runnable, Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(false, f, j2, j, runnable, runnable2, animatorListenerAdapter);
            return 0L;
        }
        if (runnable != null) {
            runnable.run();
        }
        if (runnable2 == null) {
            return 0L;
        }
        runnable2.run();
        return 0L;
    }

    protected void resetAllContentAlphas() {
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setActualHeight(int i, boolean z) {
        super.setActualHeight(i, z);
        setPivotY(i / 2);
        this.mBackgroundNormal.setActualHeight(i);
    }

    protected void setAlphaAndLayerType(View view, float f) {
        if (view.hasOverlappingRendering()) {
            view.setLayerType((f == 0.0f || f == 1.0f) ? 0 : 2, null);
        }
        view.setAlpha(f);
    }

    protected void setBackgroundTintColor(int i) {
        if (i != this.mCurrentBackgroundTint) {
            this.mCurrentBackgroundTint = i;
            if (FlagsFake.notificationBackgroundTintOptimization() && i == this.mNormalColor) {
                i = 0;
            }
            this.mBackgroundNormal.setTint(i);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setBelowSpeedBump(boolean z) {
        super.setBelowSpeedBump(z);
        if (z != this.mIsBelowSpeedBump) {
            this.mIsBelowSpeedBump = z;
            updateBackgroundTint();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipBottomAmount(int i) {
        super.setClipBottomAmount(i);
        this.mBackgroundNormal.setClipBottomAmount(i);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipTopAmount(int i) {
        super.setClipTopAmount(i);
        this.mBackgroundNormal.setClipTopAmount(i);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setFakeShadowIntensity(float f, float f2, int i, int i2) {
        boolean z = this.mShadowHidden;
        boolean z2 = f == 0.0f;
        this.mShadowHidden = z2;
        if (z2 && z) {
            return;
        }
        this.mFakeShadow.setFakeShadowTranslationZ(f * (getTranslationZ() + 0.1f), f2, i, i2);
    }

    public void setLastActionUpTime(long j) {
        this.mLastActionUpTime = j;
    }

    public void setOverrideTintColor(int i, float f) {
        this.mOverrideTint = i;
        this.mOverrideAmount = f;
        setBackgroundTintColor(calculateBgColor());
    }

    void setTintColor(int i, boolean z) {
        if (i != this.mBgTint) {
            this.mBgTint = i;
            updateBackgroundTint(z);
        }
    }

    public void setTouchHandler(Gefingerpoken gefingerpoken) {
        this.mTouchHandler = gefingerpoken;
    }

    public boolean shouldRefocusOnDismiss() {
        return this.mRefocusOnDismiss || isAccessibilityFocused();
    }

    public void unDismiss() {
        this.mDismissed = false;
    }

    protected void updateBackground() {
        this.mBackgroundNormal.setVisibility(hideBackground() ? 4 : 0);
    }

    protected void updateBackgroundClipping() {
        this.mBackgroundNormal.setBottomAmountClips(!isChildInGroup());
    }

    public void updateBackgroundColors() {
        updateColors();
        initBackground();
        updateBackgroundTint();
    }

    protected void updateBackgroundTint() {
        updateBackgroundTint(false);
    }
}
