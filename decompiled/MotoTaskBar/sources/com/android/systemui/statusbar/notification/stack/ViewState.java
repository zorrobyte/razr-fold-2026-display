package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dumpable;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.policy.HeadsUpUtil;

/* JADX INFO: loaded from: classes.dex */
public class ViewState implements Dumpable {
    public boolean gone;
    public boolean hidden;
    private float mAlpha = 1.0f;
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    private float mXTranslation;
    private float mYTranslation;
    private float mZTranslation;
    protected static final AnimationProperties NO_NEW_ANIMATIONS = new AnimationProperties() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.1
        AnimationFilter mAnimationFilter = new AnimationFilter();

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    };
    private static final int TAG_ANIMATOR_TRANSLATION_X = R$id.translation_x_animator_tag;
    private static final int TAG_ANIMATOR_TRANSLATION_Y = R$id.translation_y_animator_tag;
    private static final int TAG_ANIMATOR_TRANSLATION_Z = R$id.translation_z_animator_tag;
    private static final int TAG_ANIMATOR_ALPHA = R$id.alpha_animator_tag;
    private static final int TAG_END_TRANSLATION_X = R$id.translation_x_animator_end_value_tag;
    private static final int TAG_END_TRANSLATION_Y = R$id.translation_y_animator_end_value_tag;
    private static final int TAG_END_TRANSLATION_Z = R$id.translation_z_animator_end_value_tag;
    private static final int TAG_END_ALPHA = R$id.alpha_animator_end_value_tag;
    private static final int TAG_START_TRANSLATION_X = R$id.translation_x_animator_start_value_tag;
    private static final int TAG_START_TRANSLATION_Y = R$id.translation_y_animator_start_value_tag;
    private static final int TAG_START_TRANSLATION_Z = R$id.translation_z_animator_start_value_tag;
    private static final int TAG_START_ALPHA = R$id.alpha_animator_start_value_tag;
    private static final AnimatableProperty SCALE_X_PROPERTY = new AnimatableProperty() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.2
        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimationEndTag() {
            return R$id.scale_x_animator_end_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimationStartTag() {
            return R$id.scale_x_animator_start_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimatorTag() {
            return R$id.scale_x_animator_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public Property getProperty() {
            return View.SCALE_X;
        }
    };
    private static final AnimatableProperty SCALE_Y_PROPERTY = new AnimatableProperty() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.3
        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimationEndTag() {
            return R$id.scale_y_animator_end_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimationStartTag() {
            return R$id.scale_y_animator_start_value_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public int getAnimatorTag() {
            return R$id.scale_y_animator_tag;
        }

        @Override // com.android.systemui.statusbar.notification.AnimatableProperty
        public Property getProperty() {
            return View.SCALE_Y;
        }
    };

    public static long cancelAnimatorAndGetNewDuration(long j, ValueAnimator valueAnimator) {
        if (valueAnimator == null) {
            return j;
        }
        long jMax = Math.max(valueAnimator.getDuration() - valueAnimator.getCurrentPlayTime(), j);
        valueAnimator.cancel();
        return jMax;
    }

    public static Object getChildTag(View view, int i) {
        return view.getTag(i);
    }

    private static boolean isAnimating(View view, int i) {
        return getChildTag(view, i) != null;
    }

    public static boolean isAnimating(View view, AnimatableProperty animatableProperty) {
        return getChildTag(view, animatableProperty.getAnimatorTag()) != null;
    }

    public static boolean isAnimatingY(View view) {
        return getChildTag(view, TAG_ANIMATOR_TRANSLATION_Y) != null;
    }

    private boolean isValidFloat(float f, String str) {
        if (!Float.isNaN(f)) {
            return true;
        }
        Log.wtf("StackViewState", "Cannot set property " + str + " to NaN");
        return false;
    }

    private void startAlphaAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_ALPHA;
        Float f = (Float) getChildTag(view, i);
        int i2 = TAG_END_ALPHA;
        Float f2 = (Float) getChildTag(view, i2);
        final float f3 = this.mAlpha;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_ALPHA;
            ObjectAnimator objectAnimator = (ObjectAnimator) getChildTag(view, i3);
            if (!animationProperties.getAnimationFilter().animateAlpha) {
                if (objectAnimator != null) {
                    PropertyValuesHolder[] values = objectAnimator.getValues();
                    float fFloatValue = f.floatValue() + (f3 - f2.floatValue());
                    values[0].setFloatValues(fFloatValue, f3);
                    view.setTag(i, Float.valueOf(fFloatValue));
                    view.setTag(i2, Float.valueOf(f3));
                    objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                    return;
                }
                view.setAlpha(f3);
                if (f3 == 0.0f) {
                    view.setVisibility(4);
                }
            }
            Property property = View.ALPHA;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getAlpha(), f3);
            objectAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            view.setLayerType(2, null);
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ViewState.4
                public boolean mWasCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.mWasCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    view.setLayerType(0, null);
                    if (f3 == 0.0f && !this.mWasCancelled) {
                        view.setVisibility(4);
                    }
                    view.setTag(ViewState.TAG_ANIMATOR_ALPHA, null);
                    view.setTag(ViewState.TAG_START_ALPHA, null);
                    view.setTag(ViewState.TAG_END_ALPHA, null);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    this.mWasCancelled = false;
                }
            });
            objectAnimatorOfFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                objectAnimatorOfFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                objectAnimatorOfFloat.addListener(animationFinishListener);
            }
            startAnimator(objectAnimatorOfFloat, animationFinishListener);
            view.setTag(i3, objectAnimatorOfFloat);
            view.setTag(i, Float.valueOf(view.getAlpha()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    public static void startAnimator(Animator animator, AnimatorListenerAdapter animatorListenerAdapter) {
        if (animatorListenerAdapter != null) {
            animatorListenerAdapter.onAnimationStart(animator);
        }
        animator.start();
    }

    private void startXTranslationAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_TRANSLATION_X;
        Float f = (Float) getChildTag(view, i);
        int i2 = TAG_END_TRANSLATION_X;
        Float f2 = (Float) getChildTag(view, i2);
        float f3 = this.mXTranslation;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_TRANSLATION_X;
            ObjectAnimator objectAnimator = (ObjectAnimator) getChildTag(view, i3);
            if (!animationProperties.getAnimationFilter().animateX) {
                if (objectAnimator == null) {
                    view.setTranslationX(f3);
                    return;
                }
                PropertyValuesHolder[] values = objectAnimator.getValues();
                float fFloatValue = f.floatValue() + (f3 - f2.floatValue());
                values[0].setFloatValues(fFloatValue, f3);
                view.setTag(i, Float.valueOf(fFloatValue));
                view.setTag(i2, Float.valueOf(f3));
                objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                return;
            }
            Property property = View.TRANSLATION_X;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getTranslationX(), f3);
            Interpolator customInterpolator = animationProperties.getCustomInterpolator(view, property);
            if (customInterpolator == null) {
                customInterpolator = Interpolators.FAST_OUT_SLOW_IN;
            }
            objectAnimatorOfFloat.setInterpolator(customInterpolator);
            objectAnimatorOfFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                objectAnimatorOfFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                objectAnimatorOfFloat.addListener(animationFinishListener);
            }
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ViewState.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_X, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_X, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_X, null);
                }
            });
            startAnimator(objectAnimatorOfFloat, animationFinishListener);
            view.setTag(i3, objectAnimatorOfFloat);
            view.setTag(i, Float.valueOf(view.getTranslationX()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    private void startYTranslationAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_TRANSLATION_Y;
        Float f = (Float) getChildTag(view, i);
        int i2 = TAG_END_TRANSLATION_Y;
        Float f2 = (Float) getChildTag(view, i2);
        float f3 = this.mYTranslation;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_TRANSLATION_Y;
            ObjectAnimator objectAnimator = (ObjectAnimator) getChildTag(view, i3);
            if (!animationProperties.getAnimationFilter().animateY) {
                if (objectAnimator == null) {
                    view.setTranslationY(f3);
                    return;
                }
                PropertyValuesHolder[] values = objectAnimator.getValues();
                float fFloatValue = f.floatValue() + (f3 - f2.floatValue());
                values[0].setFloatValues(fFloatValue, f3);
                view.setTag(i, Float.valueOf(fFloatValue));
                view.setTag(i2, Float.valueOf(f3));
                objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                return;
            }
            Property property = View.TRANSLATION_Y;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getTranslationY(), f3);
            Interpolator customInterpolator = animationProperties.getCustomInterpolator(view, property);
            if (customInterpolator == null) {
                customInterpolator = Interpolators.FAST_OUT_SLOW_IN;
            }
            objectAnimatorOfFloat.setInterpolator(customInterpolator);
            objectAnimatorOfFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                objectAnimatorOfFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                objectAnimatorOfFloat.addListener(animationFinishListener);
            }
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.stack.ViewState.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    HeadsUpUtil.setNeedsHeadsUpDisappearAnimationAfterClick(view, false);
                    view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_Y, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_Y, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_Y, null);
                    ViewState.this.onYTranslationAnimationFinished(view);
                }
            });
            startAnimator(objectAnimatorOfFloat, animationFinishListener);
            view.setTag(i3, objectAnimatorOfFloat);
            view.setTag(i, Float.valueOf(view.getTranslationY()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    private void startZTranslationAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_TRANSLATION_Z;
        Float f = (Float) getChildTag(view, i);
        int i2 = TAG_END_TRANSLATION_Z;
        Float f2 = (Float) getChildTag(view, i2);
        float f3 = this.mZTranslation;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_TRANSLATION_Z;
            ObjectAnimator objectAnimator = (ObjectAnimator) getChildTag(view, i3);
            if (!animationProperties.getAnimationFilter().animateZ) {
                if (objectAnimator != null) {
                    PropertyValuesHolder[] values = objectAnimator.getValues();
                    float fFloatValue = f.floatValue() + (f3 - f2.floatValue());
                    values[0].setFloatValues(fFloatValue, f3);
                    view.setTag(i, Float.valueOf(fFloatValue));
                    view.setTag(i2, Float.valueOf(f3));
                    objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                    return;
                }
                view.setTranslationZ(f3);
            }
            Property property = View.TRANSLATION_Z;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getTranslationZ(), f3);
            objectAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            objectAnimatorOfFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                objectAnimatorOfFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                objectAnimatorOfFloat.addListener(animationFinishListener);
            }
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.ViewState.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_Z, null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_Z, null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_Z, null);
                }
            });
            startAnimator(objectAnimatorOfFloat, animationFinishListener);
            view.setTag(i3, objectAnimatorOfFloat);
            view.setTag(i, Float.valueOf(view.getTranslationZ()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    private void updateAlphaAnimation(View view) {
        startAlphaAnimation(view, NO_NEW_ANIMATIONS);
    }

    private void updateAnimation(View view, AnimatableProperty animatableProperty, float f) {
        PropertyAnimator.startAnimation(view, animatableProperty, f, NO_NEW_ANIMATIONS);
    }

    private void updateAnimationX(View view) {
        startXTranslationAnimation(view, NO_NEW_ANIMATIONS);
    }

    private void updateAnimationY(View view) {
        startYTranslationAnimation(view, NO_NEW_ANIMATIONS);
    }

    private void updateAnimationZ(View view) {
        startZTranslationAnimation(view, NO_NEW_ANIMATIONS);
    }

    protected void abortAnimation(View view, int i) {
        Animator animator = (Animator) getChildTag(view, i);
        if (animator != null) {
            animator.cancel();
        }
    }

    public void animateTo(View view, AnimationProperties animationProperties) {
        boolean z = view.getVisibility() == 0;
        float f = this.mAlpha;
        if (!z && ((f != 0.0f || view.getAlpha() != 0.0f) && !this.gone && !this.hidden)) {
            view.setVisibility(0);
        }
        boolean z2 = this.mAlpha != view.getAlpha();
        if (view instanceof ExpandableView) {
            z2 &= !((ExpandableView) view).willBeGone();
        }
        if (view.getTranslationX() != this.mXTranslation) {
            startXTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_X);
        }
        if (view.getTranslationY() != this.mYTranslation) {
            startYTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_Y);
        }
        if (view.getTranslationZ() != this.mZTranslation) {
            startZTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_Z);
        }
        float scaleX = view.getScaleX();
        float f2 = this.mScaleX;
        if (scaleX != f2) {
            PropertyAnimator.startAnimation(view, SCALE_X_PROPERTY, f2, animationProperties);
        } else {
            abortAnimation(view, SCALE_X_PROPERTY.getAnimatorTag());
        }
        float scaleY = view.getScaleY();
        float f3 = this.mScaleY;
        if (scaleY != f3) {
            PropertyAnimator.startAnimation(view, SCALE_Y_PROPERTY, f3, animationProperties);
        } else {
            abortAnimation(view, SCALE_Y_PROPERTY.getAnimatorTag());
        }
        if (z2) {
            startAlphaAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_ALPHA);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void applyToView(View view) {
        if (this.gone) {
            return;
        }
        if (isAnimating(view, TAG_ANIMATOR_TRANSLATION_X)) {
            updateAnimationX(view);
        } else {
            float translationX = view.getTranslationX();
            float f = this.mXTranslation;
            if (translationX != f) {
                view.setTranslationX(f);
            }
        }
        if (isAnimating(view, TAG_ANIMATOR_TRANSLATION_Y)) {
            updateAnimationY(view);
        } else {
            float translationY = view.getTranslationY();
            float f2 = this.mYTranslation;
            if (translationY != f2) {
                view.setTranslationY(f2);
            }
        }
        if (isAnimating(view, TAG_ANIMATOR_TRANSLATION_Z)) {
            updateAnimationZ(view);
        } else {
            float translationZ = view.getTranslationZ();
            float f3 = this.mZTranslation;
            if (translationZ != f3) {
                view.setTranslationZ(f3);
            }
        }
        AnimatableProperty animatableProperty = SCALE_X_PROPERTY;
        if (isAnimating(view, animatableProperty)) {
            updateAnimation(view, animatableProperty, this.mScaleX);
        } else {
            float scaleX = view.getScaleX();
            float f4 = this.mScaleX;
            if (scaleX != f4) {
                view.setScaleX(f4);
            }
        }
        AnimatableProperty animatableProperty2 = SCALE_Y_PROPERTY;
        if (isAnimating(view, animatableProperty2)) {
            updateAnimation(view, animatableProperty2, this.mScaleY);
        } else {
            float scaleY = view.getScaleY();
            float f5 = this.mScaleY;
            if (scaleY != f5) {
                view.setScaleY(f5);
            }
        }
        int visibility = view.getVisibility();
        boolean z = this.mAlpha == 0.0f || (this.hidden && !(isAnimating(view) && visibility == 0));
        if (isAnimating(view, TAG_ANIMATOR_ALPHA)) {
            updateAlphaAnimation(view);
        } else {
            float alpha = view.getAlpha();
            float f6 = this.mAlpha;
            if (alpha != f6) {
                boolean z2 = (z || ((f6 > 1.0f ? 1 : (f6 == 1.0f ? 0 : -1)) == 0)) ? false : true;
                if (view instanceof NotificationFadeAware.FadeOptimizedNotification) {
                    NotificationFadeAware.FadeOptimizedNotification fadeOptimizedNotification = (NotificationFadeAware.FadeOptimizedNotification) view;
                    if (fadeOptimizedNotification.isNotificationFaded() != z2) {
                        fadeOptimizedNotification.setNotificationFaded(z2);
                    }
                } else {
                    boolean z3 = z2 && view.hasOverlappingRendering();
                    int layerType = view.getLayerType();
                    int i = z3 ? 2 : 0;
                    if (layerType != i) {
                        view.setLayerType(i, null);
                    }
                }
                view.setAlpha(this.mAlpha);
            }
        }
        int i2 = z ? 4 : 0;
        if (i2 != visibility) {
            if ((view instanceof ExpandableView) && ((ExpandableView) view).willBeGone()) {
                return;
            }
            view.setVisibility(i2);
        }
    }

    public void cancelAnimations(View view) {
        Animator animator = (Animator) getChildTag(view, TAG_ANIMATOR_TRANSLATION_X);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = (Animator) getChildTag(view, TAG_ANIMATOR_TRANSLATION_Y);
        if (animator2 != null) {
            animator2.cancel();
        }
        Animator animator3 = (Animator) getChildTag(view, TAG_ANIMATOR_TRANSLATION_Z);
        if (animator3 != null) {
            animator3.cancel();
        }
        Animator animator4 = (Animator) getChildTag(view, TAG_ANIMATOR_ALPHA);
        if (animator4 != null) {
            animator4.cancel();
        }
    }

    public void copyFrom(ViewState viewState) {
        this.mAlpha = viewState.mAlpha;
        this.mXTranslation = viewState.mXTranslation;
        this.mYTranslation = viewState.mYTranslation;
        this.mZTranslation = viewState.mZTranslation;
        this.gone = viewState.gone;
        this.hidden = viewState.hidden;
        this.mScaleX = viewState.mScaleX;
        this.mScaleY = viewState.mScaleY;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public float getYTranslation() {
        return this.mYTranslation;
    }

    public float getZTranslation() {
        return this.mZTranslation;
    }

    public void initFrom(View view) {
        this.mAlpha = view.getAlpha();
        this.mXTranslation = view.getTranslationX();
        this.mYTranslation = view.getTranslationY();
        this.mZTranslation = view.getTranslationZ();
        this.gone = view.getVisibility() == 8;
        this.hidden = view.getVisibility() == 4;
        this.mScaleX = view.getScaleX();
        this.mScaleY = view.getScaleY();
    }

    public boolean isAnimating(View view) {
        return isAnimating(view, TAG_ANIMATOR_TRANSLATION_X) || isAnimating(view, TAG_ANIMATOR_TRANSLATION_Y) || isAnimating(view, TAG_ANIMATOR_TRANSLATION_Z) || isAnimating(view, TAG_ANIMATOR_ALPHA) || isAnimating(view, SCALE_X_PROPERTY) || isAnimating(view, SCALE_Y_PROPERTY);
    }

    protected void onYTranslationAnimationFinished(View view) {
        if (!this.hidden || this.gone) {
            return;
        }
        view.setVisibility(4);
    }

    public void setAlpha(float f) {
        if (isValidFloat(f, "alpha")) {
            this.mAlpha = f;
        }
    }

    public void setScaleX(float f) {
        if (isValidFloat(f, "scaleX")) {
            this.mScaleX = f;
        }
    }

    public void setScaleY(float f) {
        if (isValidFloat(f, "scaleY")) {
            this.mScaleY = f;
        }
    }

    public void setXTranslation(float f) {
        if (isValidFloat(f, "xTranslation")) {
            this.mXTranslation = f;
        }
    }

    public void setYTranslation(float f) {
        if (isValidFloat(f, "yTranslation")) {
            this.mYTranslation = f;
        }
    }

    public void setZTranslation(float f) {
        if (isValidFloat(f, "zTranslation")) {
            this.mZTranslation = f;
        }
    }
}
