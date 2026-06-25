package androidx.compose.animation.core;

/* JADX INFO: compiled from: VectorizedAnimationSpec.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class VectorizedAnimationSpecKt {
    private static final int[] EmptyIntArray = new int[0];
    private static final float[] EmptyFloatArray = new float[0];
    private static final ArcSpline EmptyArcSpline = new ArcSpline(new int[2], new float[2], new float[][]{new float[2], new float[2]});

    /* JADX INFO: Access modifiers changed from: private */
    public static final Animations createSpringAnimations(AnimationVector animationVector, float f, float f2) {
        return animationVector != null ? new Animations(animationVector, f, f2) { // from class: androidx.compose.animation.core.VectorizedAnimationSpecKt.createSpringAnimations.1
            private final FloatSpringSpec[] anims;

            {
                int size$animation_core_release = animationVector.getSize$animation_core_release();
                FloatSpringSpec[] floatSpringSpecArr = new FloatSpringSpec[size$animation_core_release];
                for (int i = 0; i < size$animation_core_release; i++) {
                    floatSpringSpecArr[i] = new FloatSpringSpec(f, f2, animationVector.get$animation_core_release(i));
                }
                this.anims = floatSpringSpecArr;
            }

            @Override // androidx.compose.animation.core.Animations
            public FloatSpringSpec get(int i) {
                return this.anims[i];
            }
        } : new Animations(f, f2) { // from class: androidx.compose.animation.core.VectorizedAnimationSpecKt.createSpringAnimations.2
            private final FloatSpringSpec anim;

            {
                this.anim = new FloatSpringSpec(f, f2, 0.0f, 4, null);
            }

            @Override // androidx.compose.animation.core.Animations
            public FloatSpringSpec get(int i) {
                return this.anim;
            }
        };
    }
}
