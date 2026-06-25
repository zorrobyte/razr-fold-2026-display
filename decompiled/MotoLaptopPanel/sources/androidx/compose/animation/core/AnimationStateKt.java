package androidx.compose.animation.core;

/* JADX INFO: compiled from: AnimationState.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AnimationStateKt {
    public static final AnimationState copy(AnimationState animationState, Object obj, AnimationVector animationVector, long j, long j2, boolean z) {
        return new AnimationState(animationState.getTypeConverter(), obj, animationVector, j, j2, z);
    }

    public static /* synthetic */ AnimationState copy$default(AnimationState animationState, Object obj, AnimationVector animationVector, long j, long j2, boolean z, int i, Object obj2) {
        if ((i & 1) != 0) {
            obj = animationState.getValue();
        }
        if ((i & 2) != 0) {
            animationVector = AnimationVectorsKt.copy(animationState.getVelocityVector());
        }
        if ((i & 4) != 0) {
            j = animationState.getLastFrameTimeNanos();
        }
        if ((i & 8) != 0) {
            j2 = animationState.getFinishedTimeNanos();
        }
        if ((i & 16) != 0) {
            z = animationState.isRunning();
        }
        boolean z2 = z;
        long j3 = j2;
        return copy(animationState, obj, animationVector, j, j3, z2);
    }

    public static final AnimationVector createZeroVectorFrom(TwoWayConverter twoWayConverter, Object obj) {
        AnimationVector animationVector = (AnimationVector) twoWayConverter.getConvertToVector().invoke(obj);
        animationVector.reset$animation_core_release();
        return animationVector;
    }
}
