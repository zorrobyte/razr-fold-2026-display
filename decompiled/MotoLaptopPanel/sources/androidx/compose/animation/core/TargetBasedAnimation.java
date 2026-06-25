package androidx.compose.animation.core;

/* JADX INFO: compiled from: Animation.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TargetBasedAnimation implements Animation {
    private long _durationNanos;
    private AnimationVector _endVelocity;
    private final VectorizedAnimationSpec animationSpec;
    private AnimationVector initialValueVector;
    private final AnimationVector initialVelocityVector;
    private Object mutableInitialValue;
    private Object mutableTargetValue;
    private AnimationVector targetValueVector;
    private final TwoWayConverter typeConverter;

    public TargetBasedAnimation(AnimationSpec animationSpec, TwoWayConverter twoWayConverter, Object obj, Object obj2, AnimationVector animationVector) {
        this(animationSpec.vectorize(twoWayConverter), twoWayConverter, obj, obj2, animationVector);
    }

    public TargetBasedAnimation(VectorizedAnimationSpec vectorizedAnimationSpec, TwoWayConverter twoWayConverter, Object obj, Object obj2, AnimationVector animationVector) {
        AnimationVector animationVectorCopy;
        this.animationSpec = vectorizedAnimationSpec;
        this.typeConverter = twoWayConverter;
        this.mutableTargetValue = obj2;
        this.mutableInitialValue = obj;
        this.initialValueVector = (AnimationVector) getTypeConverter().getConvertToVector().invoke(obj);
        this.targetValueVector = (AnimationVector) getTypeConverter().getConvertToVector().invoke(obj2);
        this.initialVelocityVector = (animationVector == null || (animationVectorCopy = AnimationVectorsKt.copy(animationVector)) == null) ? AnimationVectorsKt.newInstance((AnimationVector) getTypeConverter().getConvertToVector().invoke(obj)) : animationVectorCopy;
        this._durationNanos = -1L;
    }

    private final AnimationVector getEndVelocity() {
        AnimationVector animationVector = this._endVelocity;
        if (animationVector != null) {
            return animationVector;
        }
        AnimationVector endVelocity = this.animationSpec.getEndVelocity(this.initialValueVector, this.targetValueVector, this.initialVelocityVector);
        this._endVelocity = endVelocity;
        return endVelocity;
    }

    @Override // androidx.compose.animation.core.Animation
    public long getDurationNanos() {
        if (this._durationNanos < 0) {
            this._durationNanos = this.animationSpec.getDurationNanos(this.initialValueVector, this.targetValueVector, this.initialVelocityVector);
        }
        return this._durationNanos;
    }

    public final Object getInitialValue() {
        return this.mutableInitialValue;
    }

    @Override // androidx.compose.animation.core.Animation
    public Object getTargetValue() {
        return this.mutableTargetValue;
    }

    @Override // androidx.compose.animation.core.Animation
    public TwoWayConverter getTypeConverter() {
        return this.typeConverter;
    }

    @Override // androidx.compose.animation.core.Animation
    public Object getValueFromNanos(long j) {
        if (isFinishedFromNanos(j)) {
            return getTargetValue();
        }
        AnimationVector valueFromNanos = this.animationSpec.getValueFromNanos(j, this.initialValueVector, this.targetValueVector, this.initialVelocityVector);
        int size$animation_core_release = valueFromNanos.getSize$animation_core_release();
        for (int i = 0; i < size$animation_core_release; i++) {
            if (Float.isNaN(valueFromNanos.get$animation_core_release(i))) {
                PreconditionsKt.throwIllegalStateException("AnimationVector cannot contain a NaN. " + valueFromNanos + ". Animation: " + this + ", playTimeNanos: " + j);
            }
        }
        return getTypeConverter().getConvertFromVector().invoke(valueFromNanos);
    }

    @Override // androidx.compose.animation.core.Animation
    public AnimationVector getVelocityVectorFromNanos(long j) {
        return !isFinishedFromNanos(j) ? this.animationSpec.getVelocityFromNanos(j, this.initialValueVector, this.targetValueVector, this.initialVelocityVector) : getEndVelocity();
    }

    @Override // androidx.compose.animation.core.Animation
    public boolean isInfinite() {
        return this.animationSpec.isInfinite();
    }

    public String toString() {
        return "TargetBasedAnimation: " + getInitialValue() + " -> " + getTargetValue() + ",initial velocity: " + this.initialVelocityVector + ", duration: " + AnimationKt.getDurationMillis(this) + " ms,animationSpec: " + this.animationSpec;
    }
}
