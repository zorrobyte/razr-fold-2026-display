package com.google.android.material.navigationrail;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

/* JADX INFO: loaded from: classes.dex */
class LabelMoveTransition extends Transition {
    LabelMoveTransition() {
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put("NavigationRailLabelVisibility", Integer.valueOf(transitionValues.view.getVisibility()));
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put("NavigationRailLabelVisibility", Integer.valueOf(transitionValues.view.getVisibility()));
    }

    @Override // androidx.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null || transitionValues.values.get("NavigationRailLabelVisibility") == null || transitionValues2.values.get("NavigationRailLabelVisibility") == null) {
            return super.createAnimator(viewGroup, transitionValues, transitionValues2);
        }
        if (((Integer) transitionValues.values.get("NavigationRailLabelVisibility")).intValue() != 8 || ((Integer) transitionValues2.values.get("NavigationRailLabelVisibility")).intValue() != 0) {
            return super.createAnimator(viewGroup, transitionValues, transitionValues2);
        }
        final View view = transitionValues2.view;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.navigationrail.LabelMoveTransition$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setTranslationX((1.0f - valueAnimator.getAnimatedFraction()) * (-30.0f));
            }
        });
        return valueAnimatorOfFloat;
    }
}
