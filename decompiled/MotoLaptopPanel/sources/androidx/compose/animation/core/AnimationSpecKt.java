package androidx.compose.animation.core;

/* JADX INFO: compiled from: AnimationSpec.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AnimationSpecKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final AnimationVector convert(TwoWayConverter twoWayConverter, Object obj) {
        if (obj == null) {
            return null;
        }
        return (AnimationVector) twoWayConverter.getConvertToVector().invoke(obj);
    }

    public static final AnimationSpec delayed(AnimationSpec animationSpec, long j) {
        return new StartDelayAnimationSpec(animationSpec, j);
    }

    /* JADX INFO: renamed from: infiniteRepeatable-9IiC70o, reason: not valid java name */
    public static final InfiniteRepeatableSpec m43infiniteRepeatable9IiC70o(DurationBasedAnimationSpec durationBasedAnimationSpec, RepeatMode repeatMode, long j) {
        return new InfiniteRepeatableSpec(durationBasedAnimationSpec, repeatMode, j, null);
    }

    /* JADX INFO: renamed from: infiniteRepeatable-9IiC70o$default, reason: not valid java name */
    public static /* synthetic */ InfiniteRepeatableSpec m44infiniteRepeatable9IiC70o$default(DurationBasedAnimationSpec durationBasedAnimationSpec, RepeatMode repeatMode, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            repeatMode = RepeatMode.Restart;
        }
        if ((i & 4) != 0) {
            j = StartOffset.m51constructorimpl$default(0, 0, 2, null);
        }
        return m43infiniteRepeatable9IiC70o(durationBasedAnimationSpec, repeatMode, j);
    }

    /* JADX INFO: renamed from: repeatable-91I0pcU, reason: not valid java name */
    public static final RepeatableSpec m45repeatable91I0pcU(int i, DurationBasedAnimationSpec durationBasedAnimationSpec, RepeatMode repeatMode, long j) {
        return new RepeatableSpec(i, durationBasedAnimationSpec, repeatMode, j, null);
    }

    /* JADX INFO: renamed from: repeatable-91I0pcU$default, reason: not valid java name */
    public static /* synthetic */ RepeatableSpec m46repeatable91I0pcU$default(int i, DurationBasedAnimationSpec durationBasedAnimationSpec, RepeatMode repeatMode, long j, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            repeatMode = RepeatMode.Restart;
        }
        if ((i2 & 8) != 0) {
            j = StartOffset.m51constructorimpl$default(0, 0, 2, null);
        }
        return m45repeatable91I0pcU(i, durationBasedAnimationSpec, repeatMode, j);
    }

    public static final SpringSpec spring(float f, float f2, Object obj) {
        return new SpringSpec(f, f2, obj);
    }

    public static /* synthetic */ SpringSpec spring$default(float f, float f2, Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            f = 1.0f;
        }
        if ((i & 2) != 0) {
            f2 = 1500.0f;
        }
        if ((i & 4) != 0) {
            obj = null;
        }
        return spring(f, f2, obj);
    }

    public static final TweenSpec tween(int i, int i2, Easing easing) {
        return new TweenSpec(i, i2, easing);
    }

    public static /* synthetic */ TweenSpec tween$default(int i, int i2, Easing easing, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 300;
        }
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        if ((i3 & 4) != 0) {
            easing = EasingKt.getFastOutSlowInEasing();
        }
        return tween(i, i2, easing);
    }
}
