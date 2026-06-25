package androidx.compose.animation.core;

/* JADX INFO: compiled from: VectorizedAnimationSpec.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VectorizedTweenSpec implements VectorizedDurationBasedAnimationSpec {
    private final VectorizedFloatAnimationSpec anim;
    private final int delayMillis;
    private final int durationMillis;
    private final Easing easing;

    public VectorizedTweenSpec(int i, int i2, Easing easing) {
        this.durationMillis = i;
        this.delayMillis = i2;
        this.easing = easing;
        this.anim = new VectorizedFloatAnimationSpec(new FloatTweenSpec(getDurationMillis(), getDelayMillis(), easing));
    }

    @Override // androidx.compose.animation.core.VectorizedDurationBasedAnimationSpec
    public int getDelayMillis() {
        return this.delayMillis;
    }

    @Override // androidx.compose.animation.core.VectorizedDurationBasedAnimationSpec
    public int getDurationMillis() {
        return this.durationMillis;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.anim.getValueFromNanos(j, animationVector, animationVector2, animationVector3);
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return this.anim.getVelocityFromNanos(j, animationVector, animationVector2, animationVector3);
    }
}
