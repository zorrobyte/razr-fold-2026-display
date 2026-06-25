package androidx.compose.animation.core;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: VectorizedAnimationSpec.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VectorizedFloatAnimationSpec implements VectorizedFiniteAnimationSpec {
    private final Animations anims;
    private AnimationVector endVelocityVector;
    private AnimationVector valueVector;
    private AnimationVector velocityVector;

    public VectorizedFloatAnimationSpec(Animations animations) {
        this.anims = animations;
    }

    public VectorizedFloatAnimationSpec(final FloatAnimationSpec floatAnimationSpec) {
        this(new Animations() { // from class: androidx.compose.animation.core.VectorizedFloatAnimationSpec.1
            @Override // androidx.compose.animation.core.Animations
            public FloatAnimationSpec get(int i) {
                return floatAnimationSpec;
            }
        });
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        int size$animation_core_release = animationVector.getSize$animation_core_release();
        long jMax = 0;
        for (int i = 0; i < size$animation_core_release; i++) {
            jMax = Math.max(jMax, this.anims.get(i).getDurationNanos(animationVector.get$animation_core_release(i), animationVector2.get$animation_core_release(i), animationVector3.get$animation_core_release(i)));
        }
        return jMax;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public AnimationVector getEndVelocity(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        if (this.endVelocityVector == null) {
            this.endVelocityVector = AnimationVectorsKt.newInstance(animationVector3);
        }
        AnimationVector animationVector4 = this.endVelocityVector;
        if (animationVector4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("endVelocityVector");
            animationVector4 = null;
        }
        int size$animation_core_release = animationVector4.getSize$animation_core_release();
        for (int i = 0; i < size$animation_core_release; i++) {
            AnimationVector animationVector5 = this.endVelocityVector;
            if (animationVector5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("endVelocityVector");
                animationVector5 = null;
            }
            animationVector5.set$animation_core_release(i, this.anims.get(i).getEndVelocity(animationVector.get$animation_core_release(i), animationVector2.get$animation_core_release(i), animationVector3.get$animation_core_release(i)));
        }
        AnimationVector animationVector6 = this.endVelocityVector;
        if (animationVector6 != null) {
            return animationVector6;
        }
        Intrinsics.throwUninitializedPropertyAccessException("endVelocityVector");
        return null;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public AnimationVector getValueFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        if (this.valueVector == null) {
            this.valueVector = AnimationVectorsKt.newInstance(animationVector);
        }
        AnimationVector animationVector4 = this.valueVector;
        if (animationVector4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("valueVector");
            animationVector4 = null;
        }
        int size$animation_core_release = animationVector4.getSize$animation_core_release();
        for (int i = 0; i < size$animation_core_release; i++) {
            AnimationVector animationVector5 = this.valueVector;
            if (animationVector5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("valueVector");
                animationVector5 = null;
            }
            animationVector5.set$animation_core_release(i, this.anims.get(i).getValueFromNanos(j, animationVector.get$animation_core_release(i), animationVector2.get$animation_core_release(i), animationVector3.get$animation_core_release(i)));
        }
        AnimationVector animationVector6 = this.valueVector;
        if (animationVector6 != null) {
            return animationVector6;
        }
        Intrinsics.throwUninitializedPropertyAccessException("valueVector");
        return null;
    }

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    public AnimationVector getVelocityFromNanos(long j, AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        if (this.velocityVector == null) {
            this.velocityVector = AnimationVectorsKt.newInstance(animationVector3);
        }
        AnimationVector animationVector4 = this.velocityVector;
        if (animationVector4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("velocityVector");
            animationVector4 = null;
        }
        int size$animation_core_release = animationVector4.getSize$animation_core_release();
        for (int i = 0; i < size$animation_core_release; i++) {
            AnimationVector animationVector5 = this.velocityVector;
            if (animationVector5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("velocityVector");
                animationVector5 = null;
            }
            animationVector5.set$animation_core_release(i, this.anims.get(i).getVelocityFromNanos(j, animationVector.get$animation_core_release(i), animationVector2.get$animation_core_release(i), animationVector3.get$animation_core_release(i)));
        }
        AnimationVector animationVector6 = this.velocityVector;
        if (animationVector6 != null) {
            return animationVector6;
        }
        Intrinsics.throwUninitializedPropertyAccessException("velocityVector");
        return null;
    }
}
