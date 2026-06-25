package androidx.compose.animation.core;

/* JADX INFO: compiled from: AnimationSpec.kt */
/* JADX INFO: loaded from: classes.dex */
public interface DurationBasedAnimationSpec extends FiniteAnimationSpec {
    @Override // androidx.compose.animation.core.AnimationSpec
    VectorizedDurationBasedAnimationSpec vectorize(TwoWayConverter twoWayConverter);
}
