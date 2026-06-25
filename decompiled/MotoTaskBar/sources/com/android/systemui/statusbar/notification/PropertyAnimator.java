package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.statusbar.notification.stack.AnimationFilter;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ViewState;

/* JADX INFO: loaded from: classes.dex */
public abstract class PropertyAnimator {
    public static boolean isAnimating(View view, AnimatableProperty animatableProperty) {
        return view.getTag(animatableProperty.getAnimatorTag()) != null;
    }

    public static void setProperty(View view, AnimatableProperty animatableProperty, float f, AnimationProperties animationProperties, boolean z) {
        if (((ValueAnimator) ViewState.getChildTag(view, animatableProperty.getAnimatorTag())) == null && !z) {
            animatableProperty.getProperty().set(view, Float.valueOf(f));
            return;
        }
        if (!z) {
            animationProperties = null;
        }
        startAnimation(view, animatableProperty, f, animationProperties);
    }

    public static void startAnimation(final View view, AnimatableProperty animatableProperty, float f, AnimationProperties animationProperties) {
        final Property property = animatableProperty.getProperty();
        final int animationStartTag = animatableProperty.getAnimationStartTag();
        final int animationEndTag = animatableProperty.getAnimationEndTag();
        Float f2 = (Float) ViewState.getChildTag(view, animationStartTag);
        Float f3 = (Float) ViewState.getChildTag(view, animationEndTag);
        if (f3 == null || f3.floatValue() != f) {
            final int animatorTag = animatableProperty.getAnimatorTag();
            ValueAnimator valueAnimator = (ValueAnimator) ViewState.getChildTag(view, animatorTag);
            AnimationFilter animationFilter = animationProperties != null ? animationProperties.getAnimationFilter() : null;
            if (animationFilter == null || !animationFilter.shouldAnimateProperty(property)) {
                if (valueAnimator == null) {
                    property.set(view, Float.valueOf(f));
                    return;
                }
                PropertyValuesHolder[] values = valueAnimator.getValues();
                float fFloatValue = f2.floatValue() + (f - f3.floatValue());
                values[0].setFloatValues(fFloatValue, f);
                view.setTag(animationStartTag, Float.valueOf(fFloatValue));
                view.setTag(animationEndTag, Float.valueOf(f));
                valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
                return;
            }
            Float f4 = (Float) property.get(view);
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (f4.equals(Float.valueOf(f))) {
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                if (animationFinishListener != null) {
                    animationFinishListener.onAnimationEnd(null);
                    return;
                }
                return;
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f4.floatValue(), f);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.PropertyAnimator$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    property.set(view, (Float) valueAnimator2.getAnimatedValue());
                }
            });
            Interpolator customInterpolator = animationProperties.getCustomInterpolator(view, property);
            if (customInterpolator == null) {
                customInterpolator = Interpolators.FAST_OUT_SLOW_IN;
            }
            valueAnimatorOfFloat.setInterpolator(customInterpolator);
            valueAnimatorOfFloat.setDuration(ViewState.cancelAnimatorAndGetNewDuration(animationProperties.duration, valueAnimator));
            if (animationProperties.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                valueAnimatorOfFloat.setStartDelay(animationProperties.delay);
            }
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.PropertyAnimator.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (((Animator) view.getTag(animatorTag)) != animator) {
                        Log.e("PropertyAnimator", "Unexpected Animator set during onAnimationEnd. Not cleaning up.");
                        return;
                    }
                    view.setTag(animatorTag, null);
                    view.setTag(animationStartTag, null);
                    view.setTag(animationEndTag, null);
                }
            });
            if (animationFinishListener != null) {
                valueAnimatorOfFloat.addListener(animationFinishListener);
            }
            ViewState.startAnimator(valueAnimatorOfFloat, animationFinishListener);
            view.setTag(animatorTag, valueAnimatorOfFloat);
            view.setTag(animationStartTag, f4);
            view.setTag(animationEndTag, Float.valueOf(f));
        }
    }
}
