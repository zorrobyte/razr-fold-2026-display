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
        long jM1079minusybeJwSQ = PointKt.m1079minusybeJwSQ(j, j2);
        long jM1079minusybeJwSQ2 = PointKt.m1079minusybeJwSQ(j3, j2);
        float fM1074getDistanceDnnuFBc = PointKt.m1074getDistanceDnnuFBc(jM1079minusybeJwSQ);
        float fM1074getDistanceDnnuFBc2 = PointKt.m1074getDistanceDnnuFBc(jM1079minusybeJwSQ2);
        if (fM1074getDistanceDnnuFBc <= 0.0f || fM1074getDistanceDnnuFBc2 <= 0.0f) {
            this.d1 = FloatFloatPair.m13constructorimpl(0.0f, 0.0f);
            this.d2 = FloatFloatPair.m13constructorimpl(0.0f, 0.0f);
            this.cornerRadius = 0.0f;
            this.smoothing = 0.0f;
            this.cosAngle = 0.0f;
            this.sinAngle = 0.0f;
            this.expectedRoundCut = 0.0f;
        } else {
            long jM1070divso9K2fw = PointKt.m1070divso9K2fw(jM1079minusybeJwSQ, fM1074getDistanceDnnuFBc);
            this.d1 = jM1070divso9K2fw;
            long jM1070divso9K2fw2 = PointKt.m1070divso9K2fw(jM1079minusybeJwSQ2, fM1074getDistanceDnnuFBc2);
            this.d2 = jM1070divso9K2fw2;
            float radius = cornerRounding != null ? cornerRounding.getRadius() : 0.0f;
            this.cornerRadius = radius;
            this.smoothing = cornerRounding != null ? cornerRounding.getSmoothing() : 0.0f;
            float fM1072dotProductybeJwSQ = PointKt.m1072dotProductybeJwSQ(jM1070divso9K2fw, jM1070divso9K2fw2);
            this.cosAngle = fM1072dotProductybeJwSQ;
            float f = 1;
            float fSqrt = (float) Math.sqrt(f - Utils.square(fM1072dotProductybeJwSQ));
            this.sinAngle = fSqrt;
            this.expectedRoundCut = ((double) fSqrt) > 0.001d ? (radius * (fM1072dotProductybeJwSQ + f)) / fSqrt : 0.0f;
        }
        this.center = FloatFloatPair.m13constructorimpl(0.0f, 0.0f);
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
    private final Cubic m1084computeFlankingCurveoAJzIJU(float f, float f2, long j, long j2, long j3, long j4, long j5, float f3) {
        long jM1073getDirectionDnnuFBc = PointKt.m1073getDirectionDnnuFBc(PointKt.m1079minusybeJwSQ(j2, j));
        long jM1080plusybeJwSQ = PointKt.m1080plusybeJwSQ(j, PointKt.m1081timesso9K2fw(PointKt.m1081timesso9K2fw(jM1073getDirectionDnnuFBc, f), 1 + f2));
        long jM18unboximpl = j3;
        long jM1078interpolatedLqxh1s = PointKt.m1078interpolatedLqxh1s(jM18unboximpl, PointKt.m1070divso9K2fw(PointKt.m1080plusybeJwSQ(j3, j4), 2.0f), f2);
        long jM1080plusybeJwSQ2 = PointKt.m1080plusybeJwSQ(j5, PointKt.m1081timesso9K2fw(Utils.directionVector(PointKt.m1076getXDnnuFBc(jM1078interpolatedLqxh1s) - PointKt.m1076getXDnnuFBc(j5), PointKt.m1077getYDnnuFBc(jM1078interpolatedLqxh1s) - PointKt.m1077getYDnnuFBc(j5)), f3));
        FloatFloatPair floatFloatPairM1085lineIntersectionCBFvKDc = m1085lineIntersectionCBFvKDc(j2, jM1073getDirectionDnnuFBc, jM1080plusybeJwSQ2, Utils.m1089rotate90DnnuFBc(PointKt.m1079minusybeJwSQ(jM1080plusybeJwSQ2, j5)));
        if (floatFloatPairM1085lineIntersectionCBFvKDc != null) {
            jM18unboximpl = floatFloatPairM1085lineIntersectionCBFvKDc.m18unboximpl();
        }
        return new Cubic(jM1080plusybeJwSQ, PointKt.m1070divso9K2fw(PointKt.m1080plusybeJwSQ(jM1080plusybeJwSQ, PointKt.m1081timesso9K2fw(jM18unboximpl, 2.0f)), 3.0f), jM18unboximpl, jM1080plusybeJwSQ2, null);
    }

    /* JADX INFO: renamed from: lineIntersection-CBFvKDc, reason: not valid java name */
    private final FloatFloatPair m1085lineIntersectionCBFvKDc(long j, long j2, long j3, long j4) {
        long jM1089rotate90DnnuFBc = Utils.m1089rotate90DnnuFBc(j4);
        float fM1072dotProductybeJwSQ = PointKt.m1072dotProductybeJwSQ(j2, jM1089rotate90DnnuFBc);
        if (Math.abs(fM1072dotProductybeJwSQ) < 1.0E-4f) {
            return null;
        }
        float fM1072dotProductybeJwSQ2 = PointKt.m1072dotProductybeJwSQ(PointKt.m1079minusybeJwSQ(j3, j), jM1089rotate90DnnuFBc);
        if (Math.abs(fM1072dotProductybeJwSQ) < Math.abs(fM1072dotProductybeJwSQ2) * 1.0E-4f) {
            return null;
        }
        return FloatFloatPair.m12boximpl(PointKt.m1080plusybeJwSQ(j, PointKt.m1081timesso9K2fw(j2, fM1072dotProductybeJwSQ2 / fM1072dotProductybeJwSQ)));
    }

    public final List getCubics(float f, float f2) {
        float fMin = Math.min(f, f2);
        float f3 = this.expectedRoundCut;
        if (f3 < 1.0E-4f || fMin < 1.0E-4f || this.cornerRadius < 1.0E-4f) {
            long j = this.p1;
            this.center = j;
            return CollectionsKt.listOf(Cubic.Companion.straightLine(PointKt.m1076getXDnnuFBc(j), PointKt.m1077getYDnnuFBc(this.p1), PointKt.m1076getXDnnuFBc(this.p1), PointKt.m1077getYDnnuFBc(this.p1)));
        }
        float fMin2 = Math.min(fMin, f3);
        float fCalculateActualSmoothingValue = calculateActualSmoothingValue(f);
        float fCalculateActualSmoothingValue2 = calculateActualSmoothingValue(f2);
        float f4 = (this.cornerRadius * fMin2) / this.expectedRoundCut;
        this.center = PointKt.m1080plusybeJwSQ(this.p1, PointKt.m1081timesso9K2fw(PointKt.m1073getDirectionDnnuFBc(PointKt.m1070divso9K2fw(PointKt.m1080plusybeJwSQ(this.d1, this.d2), 2.0f)), (float) Math.sqrt(Utils.square(f4) + Utils.square(fMin2))));
        long jM1080plusybeJwSQ = PointKt.m1080plusybeJwSQ(this.p1, PointKt.m1081timesso9K2fw(this.d1, fMin2));
        long jM1080plusybeJwSQ2 = PointKt.m1080plusybeJwSQ(this.p1, PointKt.m1081timesso9K2fw(this.d2, fMin2));
        Cubic cubicM1084computeFlankingCurveoAJzIJU = m1084computeFlankingCurveoAJzIJU(fMin2, fCalculateActualSmoothingValue, this.p1, this.p0, jM1080plusybeJwSQ, jM1080plusybeJwSQ2, this.center, f4);
        Cubic cubicReverse = m1084computeFlankingCurveoAJzIJU(fMin2, fCalculateActualSmoothingValue2, this.p1, this.p2, jM1080plusybeJwSQ2, jM1080plusybeJwSQ, this.center, f4).reverse();
        return CollectionsKt.listOf((Object[]) new Cubic[]{cubicM1084computeFlankingCurveoAJzIJU, Cubic.Companion.circularArc(PointKt.m1076getXDnnuFBc(this.center), PointKt.m1077getYDnnuFBc(this.center), cubicM1084computeFlankingCurveoAJzIJU.getAnchor1X(), cubicM1084computeFlankingCurveoAJzIJU.getAnchor1Y(), cubicReverse.getAnchor0X(), cubicReverse.getAnchor0Y()), cubicReverse});
    }

    public final float getExpectedCut() {
        return (1 + this.smoothing) * this.expectedRoundCut;
    }

    public final float getExpectedRoundCut() {
        return this.expectedRoundCut;
    }
}
