package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;

/* JADX INFO: compiled from: Point.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PointKt {
    /* JADX INFO: renamed from: clockwise-ybeJwSQ, reason: not valid java name */
    public static final boolean m1069clockwiseybeJwSQ(long j, long j2) {
        return (m1076getXDnnuFBc(j) * m1077getYDnnuFBc(j2)) - (m1077getYDnnuFBc(j) * m1076getXDnnuFBc(j2)) > 0.0f;
    }

    /* JADX INFO: renamed from: div-so9K2fw, reason: not valid java name */
    public static final long m1070divso9K2fw(long j, float f) {
        return FloatFloatPair.m13constructorimpl(m1076getXDnnuFBc(j) / f, m1077getYDnnuFBc(j) / f);
    }

    /* JADX INFO: renamed from: dotProduct-5P9i7ZU, reason: not valid java name */
    public static final float m1071dotProduct5P9i7ZU(long j, float f, float f2) {
        return (m1076getXDnnuFBc(j) * f) + (m1077getYDnnuFBc(j) * f2);
    }

    /* JADX INFO: renamed from: dotProduct-ybeJwSQ, reason: not valid java name */
    public static final float m1072dotProductybeJwSQ(long j, long j2) {
        return (m1076getXDnnuFBc(j) * m1076getXDnnuFBc(j2)) + (m1077getYDnnuFBc(j) * m1077getYDnnuFBc(j2));
    }

    /* JADX INFO: renamed from: getDirection-DnnuFBc, reason: not valid java name */
    public static final long m1073getDirectionDnnuFBc(long j) {
        float fM1074getDistanceDnnuFBc = m1074getDistanceDnnuFBc(j);
        if (fM1074getDistanceDnnuFBc > 0.0f) {
            return m1070divso9K2fw(j, fM1074getDistanceDnnuFBc);
        }
        throw new IllegalArgumentException("Can't get the direction of a 0-length vector");
    }

    /* JADX INFO: renamed from: getDistance-DnnuFBc, reason: not valid java name */
    public static final float m1074getDistanceDnnuFBc(long j) {
        return (float) Math.sqrt((m1076getXDnnuFBc(j) * m1076getXDnnuFBc(j)) + (m1077getYDnnuFBc(j) * m1077getYDnnuFBc(j)));
    }

    /* JADX INFO: renamed from: getDistanceSquared-DnnuFBc, reason: not valid java name */
    public static final float m1075getDistanceSquaredDnnuFBc(long j) {
        return (m1076getXDnnuFBc(j) * m1076getXDnnuFBc(j)) + (m1077getYDnnuFBc(j) * m1077getYDnnuFBc(j));
    }

    /* JADX INFO: renamed from: getX-DnnuFBc, reason: not valid java name */
    public static final float m1076getXDnnuFBc(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* JADX INFO: renamed from: getY-DnnuFBc, reason: not valid java name */
    public static final float m1077getYDnnuFBc(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* JADX INFO: renamed from: interpolate-dLqxh1s, reason: not valid java name */
    public static final long m1078interpolatedLqxh1s(long j, long j2, float f) {
        return FloatFloatPair.m13constructorimpl(Utils.interpolate(m1076getXDnnuFBc(j), m1076getXDnnuFBc(j2), f), Utils.interpolate(m1077getYDnnuFBc(j), m1077getYDnnuFBc(j2), f));
    }

    /* JADX INFO: renamed from: minus-ybeJwSQ, reason: not valid java name */
    public static final long m1079minusybeJwSQ(long j, long j2) {
        return FloatFloatPair.m13constructorimpl(m1076getXDnnuFBc(j) - m1076getXDnnuFBc(j2), m1077getYDnnuFBc(j) - m1077getYDnnuFBc(j2));
    }

    /* JADX INFO: renamed from: plus-ybeJwSQ, reason: not valid java name */
    public static final long m1080plusybeJwSQ(long j, long j2) {
        return FloatFloatPair.m13constructorimpl(m1076getXDnnuFBc(j) + m1076getXDnnuFBc(j2), m1077getYDnnuFBc(j) + m1077getYDnnuFBc(j2));
    }

    /* JADX INFO: renamed from: times-so9K2fw, reason: not valid java name */
    public static final long m1081timesso9K2fw(long j, float f) {
        return FloatFloatPair.m13constructorimpl(m1076getXDnnuFBc(j) * f, m1077getYDnnuFBc(j) * f);
    }

    /* JADX INFO: renamed from: transformed-so9K2fw, reason: not valid java name */
    public static final long m1082transformedso9K2fw(long j, PointTransformer pointTransformer) {
        pointTransformer.getClass();
        long jMo1083transformXgqJiTY = pointTransformer.mo1083transformXgqJiTY(m1076getXDnnuFBc(j), m1077getYDnnuFBc(j));
        return FloatFloatPair.m13constructorimpl(Float.intBitsToFloat((int) (jMo1083transformXgqJiTY >> 32)), Float.intBitsToFloat((int) (jMo1083transformXgqJiTY & 4294967295L)));
    }
}
