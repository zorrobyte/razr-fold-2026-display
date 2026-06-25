package androidx.compose.ui.node;

/* JADX INFO: compiled from: HitTestResult.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DistanceAndFlags {
    /* JADX INFO: renamed from: compareTo-9YPOF3E, reason: not valid java name */
    public static final int m1309compareTo9YPOF3E(long j, long j2) {
        boolean zM1313isInLayerimpl = m1313isInLayerimpl(j);
        if (zM1313isInLayerimpl != m1313isInLayerimpl(j2)) {
            return zM1313isInLayerimpl ? -1 : 1;
        }
        return (Math.min(m1311getDistanceimpl(j), m1311getDistanceimpl(j2)) >= 0.0f && m1312isInExpandedBoundsimpl(j) != m1312isInExpandedBoundsimpl(j2)) ? m1312isInExpandedBoundsimpl(j) ? -1 : 1 : (int) Math.signum(m1311getDistanceimpl(j) - m1311getDistanceimpl(j2));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m1310constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: getDistance-impl, reason: not valid java name */
    public static final float m1311getDistanceimpl(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* JADX INFO: renamed from: isInExpandedBounds-impl, reason: not valid java name */
    public static final boolean m1312isInExpandedBoundsimpl(long j) {
        return (j & 2) != 0;
    }

    /* JADX INFO: renamed from: isInLayer-impl, reason: not valid java name */
    public static final boolean m1313isInLayerimpl(long j) {
        return (j & 1) != 0;
    }
}
