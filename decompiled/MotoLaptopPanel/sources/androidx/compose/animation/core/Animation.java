package androidx.compose.animation.core;

/* JADX INFO: compiled from: Animation.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Animation {
    long getDurationNanos();

    Object getTargetValue();

    TwoWayConverter getTypeConverter();

    Object getValueFromNanos(long j);

    AnimationVector getVelocityVectorFromNanos(long j);

    default boolean isFinishedFromNanos(long j) {
        return j >= getDurationNanos();
    }

    boolean isInfinite();
}
