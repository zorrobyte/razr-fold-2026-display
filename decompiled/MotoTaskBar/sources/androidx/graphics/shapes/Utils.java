package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Utils {
    private static final long Zero = FloatFloatPair.m13constructorimpl(0.0f, 0.0f);
    private static final float FloatPi = 3.1415927f;
    private static final float TwoPi = 6.2831855f;

    /* JADX INFO: renamed from: convex-b22R3LQ, reason: not valid java name */
    public static final boolean m1086convexb22R3LQ(long j, long j2, long j3) {
        return PointKt.m1069clockwiseybeJwSQ(PointKt.m1079minusybeJwSQ(j2, j), PointKt.m1079minusybeJwSQ(j3, j2));
    }

    public static final long directionVector(float f) {
        double d = f;
        return FloatFloatPair.m13constructorimpl((float) Math.cos(d), (float) Math.sin(d));
    }

    public static final long directionVector(float f, float f2) {
        float fDistance = distance(f, f2);
        if (fDistance > 0.0f) {
            return FloatFloatPair.m13constructorimpl(f / fDistance, f2 / fDistance);
        }
        throw new IllegalArgumentException("Required distance greater than zero");
    }

    public static final float distance(float f, float f2) {
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    public static final float distanceSquared(float f, float f2) {
        return (f * f) + (f2 * f2);
    }

    public static final float getFloatPi() {
        return FloatPi;
    }

    public static final float interpolate(float f, float f2, float f3) {
        return ((1 - f3) * f) + (f3 * f2);
    }

    public static final float positiveModulo(float f, float f2) {
        return ((f % f2) + f2) % f2;
    }

    /* JADX INFO: renamed from: radialToCartesian-L6JJ3z0, reason: not valid java name */
    public static final long m1087radialToCartesianL6JJ3z0(float f, float f2, long j) {
        return PointKt.m1080plusybeJwSQ(PointKt.m1081timesso9K2fw(directionVector(f2), f), j);
    }

    /* JADX INFO: renamed from: radialToCartesian-L6JJ3z0$default, reason: not valid java name */
    public static /* synthetic */ long m1088radialToCartesianL6JJ3z0$default(float f, float f2, long j, int i, Object obj) {
        if ((i & 4) != 0) {
            j = Zero;
        }
        return m1087radialToCartesianL6JJ3z0(f, f2, j);
    }

    /* JADX INFO: renamed from: rotate90-DnnuFBc, reason: not valid java name */
    public static final long m1089rotate90DnnuFBc(long j) {
        return FloatFloatPair.m13constructorimpl(-PointKt.m1077getYDnnuFBc(j), PointKt.m1076getXDnnuFBc(j));
    }

    public static final float square(float f) {
        return f * f;
    }
}
