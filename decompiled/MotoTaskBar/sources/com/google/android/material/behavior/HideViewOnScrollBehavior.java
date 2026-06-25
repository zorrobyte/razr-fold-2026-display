package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.R$attr;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: classes.dex */
public class HideViewOnScrollBehavior extends CoordinatorLayout.Behavior {
    private int additionalHiddenOffset;
    private ViewPropertyAnimator currentAnimator;
    private int currentState;
    private int enterAnimDuration;
    private TimeInterpolator enterAnimInterpolator;
    private int exitAnimDuration;
    private TimeInterpolator exitAnimInterpolator;
    private HideViewOnScrollDelegate hideOnScrollViewDelegate;
    private final LinkedHashSet onScrollStateChangedListeners;
    private int size;
    private static final int ENTER_ANIM_DURATION_ATTR = R$attr.motionDurationLong2;
    private static final int EXIT_ANIM_DURATION_ATTR = R$attr.motionDurationMedium4;
    private static final int ENTER_EXIT_ANIM_EASING_ATTR = R$attr.motionEasingEmphasizedInterpolator;

    public HideViewOnScrollBehavior() {
        this.onScrollStateChangedListeners = new LinkedHashSet();
        this.size = 0;
        this.currentState = 2;
        this.additionalHiddenOffset = 0;
    }

    public HideViewOnScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.onScrollStateChangedListeners = new LinkedHashSet();
        this.size = 0;
        this.currentState = 2;
        this.additionalHiddenOffset = 0;
    }

    private void animateChildTo(View view, int i, long j, TimeInterpolator timeInterpolator) {
        this.currentAnimator = this.hideOnScrollViewDelegate.getViewTranslationAnimator(view, i).setInterpolator(timeInterpolator).setDuration(j).setListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.behavior.HideViewOnScrollBehavior.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                HideViewOnScrollBehavior.this.currentAnimator = null;
            }
        });
    }

    private boolean isGravityBottom(int i) {
        return i == 80 || i == 81;
    }

    private boolean isGravityLeft(int i) {
        return i == 3 || i == 19;
    }

    private void setViewEdge(View view, int i) {
        int i2 = ((CoordinatorLayout.LayoutParams) view.getLayoutParams()).gravity;
        if (isGravityBottom(i2)) {
            setViewEdge(1);
        } else {
            setViewEdge(isGravityLeft(Gravity.getAbsoluteGravity(i2, i)) ? 2 : 0);
        }
    }

    private void updateCurrentState(View view, int i) {
        this.currentState = i;
        Iterator it = this.onScrollStateChangedListeners.iterator();
        if (it.hasNext()) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
    }

    public boolean isScrolledIn() {
        return this.currentState == 2;
    }

    public boolean isScrolledOut() {
        return this.currentState == 1;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        setViewEdge(view, i);
        this.size = this.hideOnScrollViewDelegate.getSize(view, marginLayoutParams);
        this.enterAnimDuration = MotionUtils.resolveThemeDuration(view.getContext(), ENTER_ANIM_DURATION_ATTR, 225);
        this.exitAnimDuration = MotionUtils.resolveThemeDuration(view.getContext(), EXIT_ANIM_DURATION_ATTR, 175);
        Context context = view.getContext();
        int i2 = ENTER_EXIT_ANIM_EASING_ATTR;
        this.enterAnimInterpolator = MotionUtils.resolveThemeInterpolator(context, i2, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        this.exitAnimInterpolator = MotionUtils.resolveThemeInterpolator(view.getContext(), i2, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        return super.onLayoutChild(coordinatorLayout, view, i);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        if (i2 > 0) {
            slideOut(view);
        } else if (i2 < 0) {
            slideIn(view);
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
        return i == 2;
    }

    public void setViewEdge(int i) {
        HideViewOnScrollDelegate hideViewOnScrollDelegate = this.hideOnScrollViewDelegate;
        if (hideViewOnScrollDelegate == null || hideViewOnScrollDelegate.getViewEdge() != i) {
            if (i == 0) {
                this.hideOnScrollViewDelegate = new HideRightViewOnScrollDelegate();
                return;
            }
            if (i == 1) {
                this.hideOnScrollViewDelegate = new HideBottomViewOnScrollDelegate();
                return;
            }
            if (i == 2) {
                this.hideOnScrollViewDelegate = new HideLeftViewOnScrollDelegate();
                return;
            }
            throw new IllegalArgumentException("Invalid view edge position value: " + i + ". Must be 0, 1 or 2.");
        }
    }

    public void slideIn(View view) {
        slideIn(view, true);
    }

    public void slideIn(View view, boolean z) {
        if (isScrolledIn()) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            view.clearAnimation();
        }
        updateCurrentState(view, 2);
        int targetTranslation = this.hideOnScrollViewDelegate.getTargetTranslation();
        if (z) {
            animateChildTo(view, targetTranslation, this.enterAnimDuration, this.enterAnimInterpolator);
        } else {
            this.hideOnScrollViewDelegate.setViewTranslation(view, targetTranslation);
        }
    }

    public void slideOut(View view) {
        slideOut(view, true);
    }

    public void slideOut(View view, boolean z) {
        if (isScrolledOut()) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator = this.currentAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            view.clearAnimation();
        }
        updateCurrentState(view, 1);
        int i = this.size + this.additionalHiddenOffset;
        if (z) {
            animateChildTo(view, i, this.exitAnimDuration, this.exitAnimInterpolator);
        } else {
            view.setTranslationY(i);
        }
    }
}
