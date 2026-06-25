package androidx.compose.animation.core;

/* JADX INFO: compiled from: Animation.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AnimationKt {
    public static final TargetBasedAnimation TargetBasedAnimation(AnimationSpec animationSpec, TwoWayConverter twoWayConverter, Object obj, Object obj2, Object obj3) {
        return new TargetBasedAnimation(animationSpec, twoWayConverter, obj, obj2, (AnimationVector) twoWayConverter.getConvertToVector().invoke(obj3));
    }

    public static final long getDurationMillis(Animation animation) {
        return animation.getDurationNanos() / 1000000;
    }
}
