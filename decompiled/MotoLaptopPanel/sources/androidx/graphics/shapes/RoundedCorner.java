package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: RoundedPolygon.kt */
/* JADX INFO: loaded from: classes.dex */
final class RoundedCorner {
    private long center;
    private final float cornerRadius;
    private final float cosAngle;
    private final long d1;
    private final long d2;
    private final float expectedRoundCut;
    private final long p0;
    private final long p1;
    private final long p2;
    private final CornerRounding rounding;
    private final float sinAngle;
    private final float smoothing;

    private RoundedCorner(long j, long j2, long j3, CornerRounding cornerRounding) {
        this.p0 = j;
        this.p1 = j2;
        this.p2 = j3;
        this.rounding = cornerRounding;
        long jM1986minusybeJwSQ = PointKt.m1986minusybeJwSQ(j, j2);
        long jM1986minusybeJwSQ2 = PointKt.m1986minusybeJwSQ(j3, j2);
        float fM1981getDistanceDnnuFBc = PointKt.m1981getDistanceDnnuFBc(jM1986minusybeJwSQ);
        float fM1981getDistanceDnnuFBc2 = PointKt.m1981getDistanceDnnuFBc(jM1986minusybeJwSQ2);
        if (fM1981getDistanceDnnuFBc <= 0.0f || fM1981getDistanceDnnuFBc2 <= 0.0f) {
            this.d1 = FloatFloatPair.m11constructorimpl(0.0f, 0.0f);
            this.d2 = FloatFloatPair.m11constructorimpl(0.0f, 0.0f);
            this.cornerRadius = 0.0f;
            this.smoothing = 0.0f;
            this.cosAngle = 0.0f;
            this.sinAngle = 0.0f;
            this.expectedRoundCut = 0.0f;
        } else {
            long jM1977divso9K2fw = PointKt.m1977divso9K2fw(jM1986minusybeJwSQ, fM1981getDistanceDnnuFBc);
            this.d1 = jM1977divso9K2fw;
            long jM1977divso9K2fw2 = PointKt.m1977divso9K2fw(jM1986minusybeJwSQ2, fM1981getDistanceDnnuFBc2);
            this.d2 = jM1977divso9K2fw2;
            float radius = cornerRounding != null ? cornerRounding.getRadius() : 0.0f;
            this.cornerRadius = radius;
            this.smoothing = cornerRounding != null ? cornerRounding.getSmoothing() : 0.0f;
            float fM1979dotProductybeJwSQ = PointKt.m1979dotProductybeJwSQ(jM1977divso9K2fw, jM1977divso9K2fw2);
            this.cosAngle = fM1979dotProductybeJwSQ;
            float f = 1;
            float fSqrt = (float) Math.sqrt(f - Utils.square(fM1979dotProductybeJwSQ));
            this.sinAngle = fSqrt;
            this.expectedRoundCut = ((double) fSqrt) > 0.001d ? (radius * (fM1979dotProductybeJwSQ + f)) / fSqrt : 0.0f;
        }
        this.center = FloatFloatPair.m11constructorimpl(0.0f, 0.0f);
    }

    public /* synthetic */ RoundedCorner(long j, long j2, long j3, CornerRounding cornerRounding, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, cornerRounding);
    }

    private final float calculateActualSmoothingValue(float f) {
        if (f > getExpectedCut()) {
            return this.smoothing;
        }
        float f2 = this.expectedRoundCut;
        if (f > f2) {
            return (this.smoothing * (f - f2)) / (getExpectedCut() - this.expectedRoundCut);
        }
        return 0.0f;
    }

    /* JADX INFO: renamed from: computeFlankingCurve-oAJzIJU, reason: not valid java name */
    private final Cubic m1991computeFlankingCurveoAJzIJU(float f, float f2, long j, long j2, long j3, long j4, long j5, float f3) {
        long jM1980getDirectionDnnuFBc = PointKt.m1980getDirectionDnnuFBc(PointKt.m1986minusybeJwSQ(j2, j));
        long jM1987plusybeJwSQ = PointKt.m1987plusybeJwSQ(j, PointKt.m1988timesso9K2fw(PointKt.m1988timesso9K2fw(jM1980getDirectionDnnuFBc, f), 1 + f2));
        long jM16unboximpl = j3;
        long jM1985interpolatedLqxh1s = PointKt.m1985interpolatedLqxh1s(jM16unboximpl, PointKt.m1977divso9K2fw(PointKt.m1987plusybeJwSQ(j3, j4), 2.0f), f2);
        long jM1987plusybeJwSQ2 = PointKt.m1987plusybeJwSQ(j5, PointKt.m1988timesso9K2fw(Utils.directionVector(PointKt.m1983getXDnnuFBc(jM1985interpolatedLqxh1s) - PointKt.m1983getXDnnuFBc(j5), PointKt.m1984getYDnnuFBc(jM1985interpolatedLqxh1s) - PointKt.m1984getYDnnuFBc(j5)), f3));
        FloatFloatPair floatFloatPairM1992lineIntersectionCBFvKDc = m1992lineIntersectionCBFvKDc(j2, jM1980getDirectionDnnuFBc, jM1987plusybeJwSQ2, Utils.m1996rotate90DnnuFBc(PointKt.m1986minusybeJwSQ(jM1987plusybeJwSQ2, j5)));
        if (floatFloatPairM1992lineIntersectionCBFvKDc != null) {
            jM16unboximpl = floatFloatPairM1992lineIntersectionCBFvKDc.m16unboximpl();
        }
        return new Cubic(jM1987plusybeJwSQ, PointKt.m1977divso9K2fw(PointKt.m1987plusybeJwSQ(jM1987plusybeJwSQ, PointKt.m1988timesso9K2fw(jM16unboximpl, 2.0f)), 3.0f), jM16unboximpl, jM1987plusybeJwSQ2, null);
    }

    /* JADX INFO: renamed from: lineIntersection-CBFvKDc, reason: not valid java name */
    private final FloatFloatPair m1992lineIntersectionCBFvKDc(long j, long j2, long j3, long j4) {
        long jM1996rotate90DnnuFBc = Utils.m1996rotate90DnnuFBc(j4);
        float fM1979dotProductybeJwSQ = PointKt.m1979dotProductybeJwSQ(j2, jM1996rotate90DnnuFBc);
        if (Math.abs(fM1979dotProductybeJwSQ) < 1.0E-4f) {
            return null;
        }
        float fM1979dotProductybeJwSQ2 = PointKt.m1979dotProductybeJwSQ(PointKt.m1986minusybeJwSQ(j3, j), jM1996rotate90DnnuFBc);
        if (Math.abs(fM1979dotProductybeJwSQ) < Math.abs(fM1979dotProductybeJwSQ2) * 1.0E-4f) {
            return null;
        }
        return FloatFloatPair.m10boximpl(PointKt.m1987plusybeJwSQ(j, PointKt.m1988timesso9K2fw(j2, fM1979dotProductybeJwSQ2 / fM1979dotProductybeJwSQ)));
    }

    public final List getCubics(float f, float f2) {
        float fMin = Math.min(f, f2);
        float f3 = this.expectedRoundCut;
        if (f3 < 1.0E-4f || fMin < 1.0E-4f || this.cornerRadius < 1.0E-4f) {
            long j = this.p1;
            this.center = j;
            return CollectionsKt.listOf(Cubic.Companion.straightLine(PointKt.m1983getXDnnuFBc(j), PointKt.m1984getYDnnuFBc(this.p1), PointKt.m1983getXDnnuFBc(this.p1), PointKt.m1984getYDnnuFBc(this.p1)));
        }
        float fMin2 = Math.min(fMin, f3);
        float fCalculateActualSmoothingValue = calculateActualSmoothingValue(f);
        float fCalculateActualSmoothingValue2 = calculateActualSmoothingValue(f2);
        float f4 = (this.cornerRadius * fMin2) / this.expectedRoundCut;
        this.center = PointKt.m1987plusybeJwSQ(this.p1, PointKt.m1988timesso9K2fw(PointKt.m1980getDirectionDnnuFBc(PointKt.m1977divso9K2fw(PointKt.m1987plusybeJwSQ(this.d1, this.d2), 2.0f)), (float) Math.sqrt(Utils.square(f4) + Utils.square(fMin2))));
        long jM1987plusybeJwSQ = PointKt.m1987plusybeJwSQ(this.p1, PointKt.m1988timesso9K2fw(this.d1, fMin2));
        long jM1987plusybeJwSQ2 = PointKt.m1987plusybeJwSQ(this.p1, PointKt.m1988timesso9K2fw(this.d2, fMin2));
        Cubic cubicM1991computeFlankingCurveoAJzIJU = m1991computeFlankingCurveoAJzIJU(fMin2, fCalculateActualSmoothingValue, this.p1, this.p0, jM1987plusybeJwSQ, jM1987plusybeJwSQ2, this.center, f4);
        Cubic cubicReverse = m1991computeFlankingCurveoAJzIJU(fMin2, fCalculateActualSmoothingValue2, this.p1, this.p2, jM1987plusybeJwSQ2, jM1987plusybeJwSQ, this.center, f4).reverse();
        return CollectionsKt.listOf((Object[]) new Cubic[]{cubicM1991computeFlankingCurveoAJzIJU, Cubic.Companion.circularArc(PointKt.m1983getXDnnuFBc(this.center), PointKt.m1984getYDnnuFBc(this.center), cubicM1991computeFlankingCurveoAJzIJU.getAnchor1X(), cubicM1991computeFlankingCurveoAJzIJU.getAnchor1Y(), cubicReverse.getAnchor0X(), cubicReverse.getAnchor0Y()), cubicReverse});
    }

    public final float getExpectedCut() {
        return (1 + this.smoothing) * this.expectedRoundCut;
    }

    public final float getExpectedRoundCut() {
        return this.expectedRoundCut;
    }
}
