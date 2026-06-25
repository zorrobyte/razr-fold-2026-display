package androidx.compose.animation.core;

/* JADX INFO: compiled from: AnimationVectors.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AnimationVectorsKt {
    public static final AnimationVector1D AnimationVector(float f) {
        return new AnimationVector1D(f);
    }

    public static final AnimationVector2D AnimationVector(float f, float f2) {
        return new AnimationVector2D(f, f2);
    }

    public static final AnimationVector3D AnimationVector(float f, float f2, float f3) {
        return new AnimationVector3D(f, f2, f3);
    }

    public static final AnimationVector4D AnimationVector(float f, float f2, float f3, float f4) {
        return new AnimationVector4D(f, f2, f3, f4);
    }

    public static final AnimationVector copy(AnimationVector animationVector) {
        AnimationVector animationVectorNewInstance = newInstance(animationVector);
        int size$animation_core_release = animationVectorNewInstance.getSize$animation_core_release();
        for (int i = 0; i < size$animation_core_release; i++) {
            animationVectorNewInstance.set$animation_core_release(i, animationVector.get$animation_core_release(i));
        }
        return animationVectorNewInstance;
    }

    public static final void copyFrom(AnimationVector animationVector, AnimationVector animationVector2) {
        int size$animation_core_release = animationVector.getSize$animation_core_release();
        for (int i = 0; i < size$animation_core_release; i++) {
            animationVector.set$animation_core_release(i, animationVector2.get$animation_core_release(i));
        }
    }

    public static final AnimationVector newInstance(AnimationVector animationVector) {
        AnimationVector animationVectorNewVector$animation_core_release = animationVector.newVector$animation_core_release();
        animationVectorNewVector$animation_core_release.getClass();
        return animationVectorNewVector$animation_core_release;
    }
}
