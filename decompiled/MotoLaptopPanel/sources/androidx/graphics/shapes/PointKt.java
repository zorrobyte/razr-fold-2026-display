package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;

/* JADX INFO: compiled from: Point.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PointKt {
    /* JADX INFO: renamed from: clockwise-ybeJwSQ, reason: not valid java name */
    public static final boolean m1976clockwiseybeJwSQ(long j, long j2) {
        return (m1983getXDnnuFBc(j) * m1984getYDnnuFBc(j2)) - (m1984getYDnnuFBc(j) * m1983getXDnnuFBc(j2)) > 0.0f;
    }

    /* JADX INFO: renamed from: div-so9K2fw, reason: not valid java name */
    public static final long m1977divso9K2fw(long j, float f) {
        return FloatFloatPair.m11constructorimpl(m1983getXDnnuFBc(j) / f, m1984getYDnnuFBc(j) / f);
    }

    /* JADX INFO: renamed from: dotProduct-5P9i7ZU, reason: not valid java name */
    public static final float m1978dotProduct5P9i7ZU(long j, float f, float f2) {
        return (m1983getXDnnuFBc(j) * f) + (m1984getYDnnuFBc(j) * f2);
    }

    /* JADX INFO: renamed from: dotProduct-ybeJwSQ, reason: not valid java name */
    public static final float m1979dotProductybeJwSQ(long j, long j2) {
        return (m1983getXDnnuFBc(j) * m1983getXDnnuFBc(j2)) + (m1984getYDnnuFBc(j) * m1984getYDnnuFBc(j2));
    }

    /* JADX INFO: renamed from: getDirection-DnnuFBc, reason: not valid java name */
    public static final long m1980getDirectionDnnuFBc(long j) {
        float fM1981getDistanceDnnuFBc = m1981getDistanceDnnuFBc(j);
        if (fM1981getDistanceDnnuFBc > 0.0f) {
            return m1977divso9K2fw(j, fM1981getDistanceDnnuFBc);
        }
        throw new IllegalArgumentException("Can't get the direction of a 0-length vector");
    }

    /* JADX INFO: renamed from: getDistance-DnnuFBc, reason: not valid java name */
    public static final float m1981getDistanceDnnuFBc(long j) {
        return (float) Math.sqrt((m1983getXDnnuFBc(j) * m1983getXDnnuFBc(j)) + (m1984getYDnnuFBc(j) * m1984getYDnnuFBc(j)));
    }

    /* JADX INFO: renamed from: getDistanceSquared-DnnuFBc, reason: not valid java name */
    public static final float m1982getDistanceSquaredDnnuFBc(long j) {
        return (m1983getXDnnuFBc(j) * m1983getXDnnuFBc(j)) + (m1984getYDnnuFBc(j) * m1984getYDnnuFBc(j));
    }

    /* JADX INFO: renamed from: getX-DnnuFBc, reason: not valid java name */
    public static final float m1983getXDnnuFBc(long j) {
        return Float.intBitsToFloat((int) (j >> 32));
    }

    /* JADX INFO: renamed from: getY-DnnuFBc, reason: not valid java name */
    public static final float m1984getYDnnuFBc(long j) {
        return Float.intBitsToFloat((int) (j & 4294967295L));
    }

    /* JADX INFO: renamed from: interpolate-dLqxh1s, reason: not valid java name */
    public static final long m1985interpolatedLqxh1s(long j, long j2, float f) {
        return FloatFloatPair.m11constructorimpl(Utils.interpolate(m1983getXDnnuFBc(j), m1983getXDnnuFBc(j2), f), Utils.interpolate(m1984getYDnnuFBc(j), m1984getYDnnuFBc(j2), f));
    }

    /* JADX INFO: renamed from: minus-ybeJwSQ, reason: not valid java name */
    public static final long m1986minusybeJwSQ(long j, long j2) {
        return FloatFloatPair.m11constructorimpl(m1983getXDnnuFBc(j) - m1983getXDnnuFBc(j2), m1984getYDnnuFBc(j) - m1984getYDnnuFBc(j2));
    }

    /* JADX INFO: renamed from: plus-ybeJwSQ, reason: not valid java name */
    public static final long m1987plusybeJwSQ(long j, long j2) {
        return FloatFloatPair.m11constructorimpl(m1983getXDnnuFBc(j) + m1983getXDnnuFBc(j2), m1984getYDnnuFBc(j) + m1984getYDnnuFBc(j2));
    }

    /* JADX INFO: renamed from: times-so9K2fw, reason: not valid java name */
    public static final long m1988timesso9K2fw(long j, float f) {
        return FloatFloatPair.m11constructorimpl(m1983getXDnnuFBc(j) * f, m1984getYDnnuFBc(j) * f);
    }

    /* JADX INFO: renamed from: transformed-so9K2fw, reason: not valid java name */
    public static final long m1989transformedso9K2fw(long j, PointTransformer pointTransformer) {
        pointTransformer.getClass();
        long jMo1990transformXgqJiTY = pointTransformer.mo1990transformXgqJiTY(m1983getXDnnuFBc(j), m1984getYDnnuFBc(j));
        return FloatFloatPair.m11constructorimpl(Float.intBitsToFloat((int) (jMo1990transformXgqJiTY >> 32)), Float.intBitsToFloat((int) (jMo1990transformXgqJiTY & 4294967295L)));
    }
}
