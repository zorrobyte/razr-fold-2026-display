package com.android.wm.shell.animation;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.core.animation.Animator;
import com.android.wm.shell.shared.animation.Interpolators;

/* JADX INFO: loaded from: classes.dex */
public class FlingAnimationUtils {
    private AnimatorProperties mAnimatorProperties;
    private float mCachedStartGradient;
    private float mCachedVelocityFactor;
    private float mHighVelocityPxPerSecond;
    private PathInterpolator mInterpolator;
    private float mLinearOutSlowInX2;
    private float mMaxLengthSeconds;
    private float mMinVelocityPxPerSecond;
    private final float mSpeedUpFactor;
    private final float mY2;

    class AnimatorProperties {
        long mDuration;
        Interpolator mInterpolator;

        private AnimatorProperties() {
        }

        public androidx.core.animation.Interpolator getInterpolator() {
            final Interpolator interpolator = this.mInterpolator;
            interpolator.getClass();
            return new androidx.core.animation.Interpolator() { // from class: com.android.wm.shell.animation.FlingAnimationUtils$AnimatorProperties$$ExternalSyntheticLambda0
                @Override // androidx.core.animation.Interpolator
                public final float getInterpolation(float f) {
                    return interpolator.getInterpolation(f);
                }
            };
        }
    }

    final class InterpolatorInterpolator implements Interpolator {
        private Interpolator mCrossfader;
        private Interpolator mInterpolator1;
        private Interpolator mInterpolator2;

        InterpolatorInterpolator(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3) {
            this.mInterpolator1 = interpolator;
            this.mInterpolator2 = interpolator2;
            this.mCrossfader = interpolator3;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float interpolation = this.mCrossfader.getInterpolation(f);
            return ((1.0f - interpolation) * this.mInterpolator1.getInterpolation(f)) + (interpolation * this.mInterpolator2.getInterpolation(f));
        }
    }

    final class VelocityInterpolator implements Interpolator {
        private float mDiff;
        private float mDurationSeconds;
        private float mVelocity;

        private VelocityInterpolator(float f, float f2, float f3) {
            this.mDurationSeconds = f;
            this.mVelocity = f2;
            this.mDiff = f3;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            return ((f * this.mDurationSeconds) * this.mVelocity) / this.mDiff;
        }
    }

    public FlingAnimationUtils(DisplayMetrics displayMetrics, float f) {
        this(displayMetrics, f, 0.0f);
    }

    public FlingAnimationUtils(DisplayMetrics displayMetrics, float f, float f2) {
        this(displayMetrics, f, f2, -1.0f, 1.0f);
    }

    public FlingAnimationUtils(DisplayMetrics displayMetrics, float f, float f2, float f3, float f4) {
        this.mAnimatorProperties = new AnimatorProperties();
        this.mCachedStartGradient = -1.0f;
        this.mCachedVelocityFactor = -1.0f;
        this.mMaxLengthSeconds = f;
        this.mSpeedUpFactor = f2;
        if (f3 < 0.0f) {
            this.mLinearOutSlowInX2 = interpolate(0.35f, 0.68f, f2);
        } else {
            this.mLinearOutSlowInX2 = f3;
        }
        this.mY2 = f4;
        float f5 = displayMetrics.density;
        this.mMinVelocityPxPerSecond = 250.0f * f5;
        this.mHighVelocityPxPerSecond = f5 * 3000.0f;
    }

    private float calculateLinearOutFasterInY2(float f) {
        float f2 = this.mMinVelocityPxPerSecond;
        float fMax = Math.max(0.0f, Math.min(1.0f, (f - f2) / (this.mHighVelocityPxPerSecond - f2)));
        return ((1.0f - fMax) * 0.4f) + (fMax * 0.5f);
    }

    private AnimatorProperties getDismissingProperties(float f, float f2, float f3, float f4) {
        float f5 = f2 - f;
        float fPow = (float) (((double) this.mMaxLengthSeconds) * Math.pow(Math.abs(f5) / f4, 0.5d));
        float fAbs = Math.abs(f5);
        float fAbs2 = Math.abs(f3);
        float fCalculateLinearOutFasterInY2 = calculateLinearOutFasterInY2(fAbs2);
        PathInterpolator pathInterpolator = new PathInterpolator(0.0f, 0.0f, 0.5f, fCalculateLinearOutFasterInY2);
        float f6 = ((fCalculateLinearOutFasterInY2 / 0.5f) * fAbs) / fAbs2;
        if (f6 <= fPow) {
            this.mAnimatorProperties.mInterpolator = pathInterpolator;
            fPow = f6;
        } else if (fAbs2 >= this.mMinVelocityPxPerSecond) {
            this.mAnimatorProperties.mInterpolator = new InterpolatorInterpolator(new VelocityInterpolator(fPow, fAbs2, fAbs), pathInterpolator, Interpolators.LINEAR_OUT_SLOW_IN);
        } else {
            this.mAnimatorProperties.mInterpolator = Interpolators.FAST_OUT_LINEAR_IN;
        }
        AnimatorProperties animatorProperties = this.mAnimatorProperties;
        animatorProperties.mDuration = (long) (fPow * 1000.0f);
        return animatorProperties;
    }

    private Interpolator getInterpolator(float f, float f2) {
        if (Float.isNaN(f2)) {
            Log.e("FlingAnimationUtils", "Invalid velocity factor", new Throwable());
            return Interpolators.LINEAR_OUT_SLOW_IN;
        }
        if (f != this.mCachedStartGradient || f2 != this.mCachedVelocityFactor) {
            float f3 = this.mSpeedUpFactor * (1.0f - f2);
            float f4 = f3 * f;
            float f5 = this.mLinearOutSlowInX2;
            float f6 = this.mY2;
            try {
                this.mInterpolator = new PathInterpolator(f3, f4, f5, f6);
                this.mCachedStartGradient = f;
                this.mCachedVelocityFactor = f2;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Illegal path with x1=" + f3 + " y1=" + f4 + " x2=" + f5 + " y2=" + f6, e);
            }
        }
        return this.mInterpolator;
    }

    private AnimatorProperties getProperties(float f, float f2, float f3, float f4) {
        float f5 = f2 - f;
        float fSqrt = (float) (((double) this.mMaxLengthSeconds) * Math.sqrt(Math.abs(f5) / f4));
        float fAbs = Math.abs(f5);
        float fAbs2 = Math.abs(f3);
        float fMin = this.mSpeedUpFactor != 0.0f ? Math.min(fAbs2 / 3000.0f, 1.0f) : 1.0f;
        float fInterpolate = interpolate(0.75f, this.mY2 / this.mLinearOutSlowInX2, fMin);
        float f6 = (fInterpolate * fAbs) / fAbs2;
        Interpolator interpolator = getInterpolator(fInterpolate, fMin);
        if (f6 <= fSqrt) {
            this.mAnimatorProperties.mInterpolator = interpolator;
            fSqrt = f6;
        } else if (fAbs2 >= this.mMinVelocityPxPerSecond) {
            this.mAnimatorProperties.mInterpolator = new InterpolatorInterpolator(new VelocityInterpolator(fSqrt, fAbs2, fAbs), interpolator, Interpolators.LINEAR_OUT_SLOW_IN);
        } else {
            this.mAnimatorProperties.mInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        }
        AnimatorProperties animatorProperties = this.mAnimatorProperties;
        animatorProperties.mDuration = (long) (fSqrt * 1000.0f);
        return animatorProperties;
    }

    private static float interpolate(float f, float f2, float f3) {
        return (f * (1.0f - f3)) + (f2 * f3);
    }

    public void apply(Animator animator, float f, float f2, float f3) {
        apply(animator, f, f2, f3, Math.abs(f2 - f));
    }

    public void apply(Animator animator, float f, float f2, float f3, float f4) {
        AnimatorProperties properties = getProperties(f, f2, f3, f4);
        animator.setDuration(properties.mDuration);
        animator.setInterpolator(properties.getInterpolator());
    }

    public void applyDismissing(android.animation.Animator animator, float f, float f2, float f3, float f4) {
        AnimatorProperties dismissingProperties = getDismissingProperties(f, f2, f3, f4);
        animator.setDuration(dismissingProperties.mDuration);
        animator.setInterpolator(dismissingProperties.mInterpolator);
    }
}
