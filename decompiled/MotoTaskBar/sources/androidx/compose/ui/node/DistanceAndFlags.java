package androidx.compose.ui.node;

/* JADX INFO: compiled from: HitTestResult.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DistanceAndFlags {
    /* JADX INFO: renamed from: compareTo-9YPOF3E, reason: not valid java name */
    public static final int m563compareTo9YPOF3E(long j, long j2) {
        boolean zM567isInLayerimpl = m567isInLayerimpl(j);
        if (zM567isInLayerimpl != m567isInLayerimpl(j2)) {
            return zM567isInLayerimpl ? -1 : 1;
        }
        return (Math.min(m565getDistanceimpl(j), m565getDistanceimpl(j2)) >= 0.0f && m566isInExpandedBoundsimpl(j) != m566isInExpandedBoundsimpl(j2)) ? m566isInExpandedBoundsimpl(j) ? -1 : 1 : (int) Math.signum(m565getDistanceimpl(j) - m565getDistanceimpl(j2));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m564constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: getDistance-impl, reason: not valid java name */
    public static final float m565getDistanceimpl(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* JADX INFO: renamed from: isInExpandedBounds-impl, reason: not valid java name */
    public static final boolean m566isInExpandedBoundsimpl(long j) {
        return (j & 2) != 0;
    }

    /* JADX INFO: renamed from: isInLayer-impl, reason: not valid java name */
    public static final boolean m567isInLayerimpl(long j) {
        return (j & 1) != 0;
    }
}
