package com.android.systemui.animation;

import android.animation.Animator;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.view.animation.Interpolator;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TransitionAnimator.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TransitionAnimator {
    public static final Companion Companion = new Companion(null);
    private static final boolean DEBUG = !Build.IS_PRODUCTION_DEVICE;
    private static final PorterDuffXfermode SRC_MODE = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    private static final SpringParams DEFAULT_SPRING_PARAMS = new SpringParams(450.0f, 0.965f, 400.0f, 0.95f, 500.0f, 0.99f);

    /* JADX INFO: compiled from: TransitionAnimator.kt */
    public interface Animation {
        void cancel();
    }

    /* JADX INFO: compiled from: TransitionAnimator.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void assertLongLivedReturnAnimations() {
            if (!longLivedReturnAnimationsEnabled()) {
                throw new IllegalStateException("Long-lived registrations cannot be used when the returnAnimationFrameworkLibrary or the returnAnimationFrameworkLongLived flag are disabled");
            }
        }

        public final boolean getDEBUG$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib() {
            return TransitionAnimator.DEBUG;
        }

        public final boolean longLivedReturnAnimationsEnabled() {
            return true;
        }
    }

    /* JADX INFO: compiled from: TransitionAnimator.kt */
    public final class InterpolatedAnimation implements Animation {
        private final Animator animator;

        @Override // com.android.systemui.animation.TransitionAnimator.Animation
        public void cancel() {
            this.animator.cancel();
        }

        public final Animator getAnimator() {
            return this.animator;
        }
    }

    /* JADX INFO: compiled from: TransitionAnimator.kt */
    public final class Interpolators {
        private final Interpolator contentAfterFadeInInterpolator;
        private final Interpolator contentBeforeFadeOutInterpolator;
        private final Interpolator positionInterpolator;
        private final Interpolator positionXInterpolator;

        public Interpolators(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4) {
            interpolator.getClass();
            interpolator2.getClass();
            interpolator3.getClass();
            interpolator4.getClass();
            this.positionInterpolator = interpolator;
            this.positionXInterpolator = interpolator2;
            this.contentBeforeFadeOutInterpolator = interpolator3;
            this.contentAfterFadeInInterpolator = interpolator4;
        }

        public static /* synthetic */ Interpolators copy$default(Interpolators interpolators, Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4, int i, Object obj) {
            if ((i & 1) != 0) {
                interpolator = interpolators.positionInterpolator;
            }
            if ((i & 2) != 0) {
                interpolator2 = interpolators.positionXInterpolator;
            }
            if ((i & 4) != 0) {
                interpolator3 = interpolators.contentBeforeFadeOutInterpolator;
            }
            if ((i & 8) != 0) {
                interpolator4 = interpolators.contentAfterFadeInInterpolator;
            }
            return interpolators.copy(interpolator, interpolator2, interpolator3, interpolator4);
        }

        public final Interpolators copy(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4) {
            interpolator.getClass();
            interpolator2.getClass();
            interpolator3.getClass();
            interpolator4.getClass();
            return new Interpolators(interpolator, interpolator2, interpolator3, interpolator4);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Interpolators)) {
                return false;
            }
            Interpolators interpolators = (Interpolators) obj;
            return Intrinsics.areEqual(this.positionInterpolator, interpolators.positionInterpolator) && Intrinsics.areEqual(this.positionXInterpolator, interpolators.positionXInterpolator) && Intrinsics.areEqual(this.contentBeforeFadeOutInterpolator, interpolators.contentBeforeFadeOutInterpolator) && Intrinsics.areEqual(this.contentAfterFadeInInterpolator, interpolators.contentAfterFadeInInterpolator);
        }

        public int hashCode() {
            return (((((this.positionInterpolator.hashCode() * 31) + this.positionXInterpolator.hashCode()) * 31) + this.contentBeforeFadeOutInterpolator.hashCode()) * 31) + this.contentAfterFadeInInterpolator.hashCode();
        }

        public String toString() {
            return "Interpolators(positionInterpolator=" + this.positionInterpolator + ", positionXInterpolator=" + this.positionXInterpolator + ", contentBeforeFadeOutInterpolator=" + this.contentBeforeFadeOutInterpolator + ", contentAfterFadeInInterpolator=" + this.contentAfterFadeInInterpolator + ")";
        }
    }

    /* JADX INFO: compiled from: TransitionAnimator.kt */
    public final class MultiSpringAnimation implements Animation {
        private final SpringAnimation springScale;
        private final SpringAnimation springX;
        private final SpringAnimation springY;

        @Override // com.android.systemui.animation.TransitionAnimator.Animation
        public void cancel() {
            this.springX.cancel();
            this.springY.cancel();
            this.springScale.cancel();
        }

        public final SpringAnimation getSpringScale() {
            return this.springScale;
        }

        public final SpringAnimation getSpringX() {
            return this.springX;
        }

        public final SpringAnimation getSpringY() {
            return this.springY;
        }

        public final boolean isDone() {
            throw null;
        }
    }

    /* JADX INFO: compiled from: TransitionAnimator.kt */
    public final class SpringParams {
        private final float centerXDampingRatio;
        private final float centerXStiffness;
        private final float centerYDampingRatio;
        private final float centerYStiffness;
        private final float scaleDampingRatio;
        private final float scaleStiffness;

        public SpringParams(float f, float f2, float f3, float f4, float f5, float f6) {
            this.centerXStiffness = f;
            this.centerXDampingRatio = f2;
            this.centerYStiffness = f3;
            this.centerYDampingRatio = f4;
            this.scaleStiffness = f5;
            this.scaleDampingRatio = f6;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SpringParams)) {
                return false;
            }
            SpringParams springParams = (SpringParams) obj;
            return Float.compare(this.centerXStiffness, springParams.centerXStiffness) == 0 && Float.compare(this.centerXDampingRatio, springParams.centerXDampingRatio) == 0 && Float.compare(this.centerYStiffness, springParams.centerYStiffness) == 0 && Float.compare(this.centerYDampingRatio, springParams.centerYDampingRatio) == 0 && Float.compare(this.scaleStiffness, springParams.scaleStiffness) == 0 && Float.compare(this.scaleDampingRatio, springParams.scaleDampingRatio) == 0;
        }

        public int hashCode() {
            return (((((((((Float.hashCode(this.centerXStiffness) * 31) + Float.hashCode(this.centerXDampingRatio)) * 31) + Float.hashCode(this.centerYStiffness)) * 31) + Float.hashCode(this.centerYDampingRatio)) * 31) + Float.hashCode(this.scaleStiffness)) * 31) + Float.hashCode(this.scaleDampingRatio);
        }

        public String toString() {
            return "SpringParams(centerXStiffness=" + this.centerXStiffness + ", centerXDampingRatio=" + this.centerXDampingRatio + ", centerYStiffness=" + this.centerYStiffness + ", centerYDampingRatio=" + this.centerYDampingRatio + ", scaleStiffness=" + this.scaleStiffness + ", scaleDampingRatio=" + this.scaleDampingRatio + ")";
        }
    }

    /* JADX INFO: compiled from: TransitionAnimator.kt */
    public final class SpringTimings {
        private final float contentAfterFadeInDelay;
        private final float contentAfterFadeInDuration;
        private final float contentBeforeFadeOutDelay;
        private final float contentBeforeFadeOutDuration;

        public SpringTimings(float f, float f2, float f3, float f4) {
            this.contentBeforeFadeOutDelay = f;
            this.contentBeforeFadeOutDuration = f2;
            this.contentAfterFadeInDelay = f3;
            this.contentAfterFadeInDuration = f4;
        }
    }

    /* JADX INFO: compiled from: TransitionAnimator.kt */
    public final class Timings {
        private final long contentAfterFadeInDelay;
        private final long contentAfterFadeInDuration;
        private final long contentBeforeFadeOutDelay;
        private final long contentBeforeFadeOutDuration;
        private final long totalDuration;

        public Timings(long j, long j2, long j3, long j4, long j5) {
            this.totalDuration = j;
            this.contentBeforeFadeOutDelay = j2;
            this.contentBeforeFadeOutDuration = j3;
            this.contentAfterFadeInDelay = j4;
            this.contentAfterFadeInDuration = j5;
        }

        public static /* synthetic */ Timings copy$default(Timings timings, long j, long j2, long j3, long j4, long j5, int i, Object obj) {
            if ((i & 1) != 0) {
                j = timings.totalDuration;
            }
            long j6 = j;
            if ((i & 2) != 0) {
                j2 = timings.contentBeforeFadeOutDelay;
            }
            return timings.copy(j6, j2, (i & 4) != 0 ? timings.contentBeforeFadeOutDuration : j3, (i & 8) != 0 ? timings.contentAfterFadeInDelay : j4, (i & 16) != 0 ? timings.contentAfterFadeInDuration : j5);
        }

        public final Timings copy(long j, long j2, long j3, long j4, long j5) {
            return new Timings(j, j2, j3, j4, j5);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Timings)) {
                return false;
            }
            Timings timings = (Timings) obj;
            return this.totalDuration == timings.totalDuration && this.contentBeforeFadeOutDelay == timings.contentBeforeFadeOutDelay && this.contentBeforeFadeOutDuration == timings.contentBeforeFadeOutDuration && this.contentAfterFadeInDelay == timings.contentAfterFadeInDelay && this.contentAfterFadeInDuration == timings.contentAfterFadeInDuration;
        }

        public final long getTotalDuration() {
            return this.totalDuration;
        }

        public int hashCode() {
            return (((((((Long.hashCode(this.totalDuration) * 31) + Long.hashCode(this.contentBeforeFadeOutDelay)) * 31) + Long.hashCode(this.contentBeforeFadeOutDuration)) * 31) + Long.hashCode(this.contentAfterFadeInDelay)) * 31) + Long.hashCode(this.contentAfterFadeInDuration);
        }

        public String toString() {
            return "Timings(totalDuration=" + this.totalDuration + ", contentBeforeFadeOutDelay=" + this.contentBeforeFadeOutDelay + ", contentBeforeFadeOutDuration=" + this.contentBeforeFadeOutDuration + ", contentAfterFadeInDelay=" + this.contentAfterFadeInDelay + ", contentAfterFadeInDuration=" + this.contentAfterFadeInDuration + ")";
        }
    }
}
