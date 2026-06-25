package com.google.android.material.progressindicator;

import androidx.core.math.MathUtils;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback;
import com.google.android.material.progressindicator.DrawingDelegate;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
abstract class IndeterminateAnimatorDelegate {
    protected final List activeIndicators = new ArrayList();
    protected IndeterminateDrawable drawable;

    protected IndeterminateAnimatorDelegate(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            this.activeIndicators.add(new DrawingDelegate.ActiveIndicator());
        }
    }

    abstract void cancelAnimatorImmediately();

    protected float getFractionInRange(int i, int i2, int i3) {
        return MathUtils.clamp((i - i2) / i3, 0.0f, 1.0f);
    }

    public abstract void registerAnimatorsCompleteCallback(Animatable2Compat$AnimationCallback animatable2Compat$AnimationCallback);

    protected void registerDrawable(IndeterminateDrawable indeterminateDrawable) {
        this.drawable = indeterminateDrawable;
    }

    abstract void requestCancelAnimatorAfterCurrentCycle();

    abstract void resetPropertiesForNewStart();

    abstract void setAnimationFraction(float f);

    abstract void startAnimator();

    public abstract void unregisterAnimatorsCompleteCallback();
}
