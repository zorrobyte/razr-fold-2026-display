package androidx.compose.animation.core;

/* JADX INFO: compiled from: VectorizedAnimationSpec.kt */
/* JADX INFO: loaded from: classes.dex */
public interface VectorizedDurationBasedAnimationSpec extends VectorizedFiniteAnimationSpec {
    int getDelayMillis();

    int getDurationMillis();

    @Override // androidx.compose.animation.core.VectorizedAnimationSpec
    default long getDurationNanos(AnimationVector animationVector, AnimationVector animationVector2, AnimationVector animationVector3) {
        return ((long) (getDelayMillis() + getDurationMillis())) * 1000000;
    }
}
