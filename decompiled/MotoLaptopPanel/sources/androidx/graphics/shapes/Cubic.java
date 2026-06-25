package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import java.util.Arrays;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Cubic.kt */
/* JADX INFO: loaded from: classes.dex */
public class Cubic {
    public static final Companion Companion = new Companion(null);
    private final float[] points;

    /* JADX INFO: compiled from: Cubic.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Cubic circularArc(float f, float f2, float f3, float f4, float f5, float f6) {
            float f7 = f3 - f;
            float f8 = f4 - f2;
            long jDirectionVector = Utils.directionVector(f7, f8);
            float f9 = f5 - f;
            float f10 = f6 - f2;
            long jDirectionVector2 = Utils.directionVector(f9, f10);
            long jM1996rotate90DnnuFBc = Utils.m1996rotate90DnnuFBc(jDirectionVector);
            long jM1996rotate90DnnuFBc2 = Utils.m1996rotate90DnnuFBc(jDirectionVector2);
            boolean z = PointKt.m1978dotProduct5P9i7ZU(jM1996rotate90DnnuFBc, f9, f10) >= 0.0f;
            float fM1979dotProductybeJwSQ = PointKt.m1979dotProductybeJwSQ(jDirectionVector, jDirectionVector2);
            if (fM1979dotProductybeJwSQ > 0.999f) {
                return straightLine(f3, f4, f5, f6);
            }
            float fDistance = ((((Utils.distance(f7, f8) * 4.0f) / 3.0f) * (((float) Math.sqrt(2 * r9)) - ((float) Math.sqrt(r5 - (fM1979dotProductybeJwSQ * fM1979dotProductybeJwSQ))))) / (1 - fM1979dotProductybeJwSQ)) * (z ? 1.0f : -1.0f);
            return CubicKt.Cubic(f3, f4, (PointKt.m1983getXDnnuFBc(jM1996rotate90DnnuFBc) * fDistance) + f3, (PointKt.m1984getYDnnuFBc(jM1996rotate90DnnuFBc) * fDistance) + f4, f5 - (PointKt.m1983getXDnnuFBc(jM1996rotate90DnnuFBc2) * fDistance), f6 - (PointKt.m1984getYDnnuFBc(jM1996rotate90DnnuFBc2) * fDistance), f5, f6);
        }

        public final Cubic straightLine(float f, float f2, float f3, float f4) {
            return CubicKt.Cubic(f, f2, Utils.interpolate(f, f3, 0.33333334f), Utils.interpolate(f2, f4, 0.33333334f), Utils.interpolate(f, f3, 0.6666667f), Utils.interpolate(f2, f4, 0.6666667f), f3, f4);
        }
    }

    private Cubic(long j, long j2, long j3, long j4) {
        this(new float[]{PointKt.m1983getXDnnuFBc(j), PointKt.m1984getYDnnuFBc(j), PointKt.m1983getXDnnuFBc(j2), PointKt.m1984getYDnnuFBc(j2), PointKt.m1983getXDnnuFBc(j3), PointKt.m1984getYDnnuFBc(j3), PointKt.m1983getXDnnuFBc(j4), PointKt.m1984getYDnnuFBc(j4)});
    }

    public /* synthetic */ Cubic(long j, long j2, long j3, long j4, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4);
    }

    public Cubic(float[] fArr) {
        fArr.getClass();
        this.points = fArr;
        if (fArr.length != 8) {
            throw new IllegalArgumentException("Points array size should be 8");
        }
    }

    public /* synthetic */ Cubic(float[] fArr, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new float[8] : fArr);
    }

    private final boolean zeroIsh(float f) {
        return Math.abs(f) < 1.0E-4f;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01bc A[PHI: r0 r6
      0x01bc: PHI (r0v2 float) = (r0v1 float), (r0v3 float) binds: [B:83:0x0209, B:61:0x01ba] A[DONT_GENERATE, DONT_INLINE]
      0x01bc: PHI (r6v9 float) = (r6v4 float), (r6v10 float) binds: [B:83:0x0209, B:61:0x01ba] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void calculateBounds$graphics_shapes(float[] r22, boolean r23) {
        /*
            Method dump skipped, instruction units count: 533
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.graphics.shapes.Cubic.calculateBounds$graphics_shapes(float[], boolean):void");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Cubic) {
            return Arrays.equals(this.points, ((Cubic) obj).points);
        }
        return false;
    }

    public final float getAnchor0X() {
        return this.points[0];
    }

    public final float getAnchor0Y() {
        return this.points[1];
    }

    public final float getAnchor1X() {
        return this.points[6];
    }

    public final float getAnchor1Y() {
        return this.points[7];
    }

    public final float getControl0X() {
        return this.points[2];
    }

    public final float getControl0Y() {
        return this.points[3];
    }

    public final float getControl1X() {
        return this.points[4];
    }

    public final float getControl1Y() {
        return this.points[5];
    }

    public final float[] getPoints$graphics_shapes() {
        return this.points;
    }

    public int hashCode() {
        return Arrays.hashCode(this.points);
    }

    /* JADX INFO: renamed from: pointOnCurve-OOQOV4g$graphics_shapes, reason: not valid java name */
    public final long m1974pointOnCurveOOQOV4g$graphics_shapes(float f) {
        float f2 = 1 - f;
        float f3 = f2 * f2 * f2;
        float f4 = 3 * f;
        float f5 = f4 * f2 * f2;
        float f6 = f4 * f * f2;
        float f7 = f * f * f;
        return FloatFloatPair.m11constructorimpl((getAnchor0X() * f3) + (getControl0X() * f5) + (getControl1X() * f6) + (getAnchor1X() * f7), (getAnchor0Y() * f3) + (getControl0Y() * f5) + (getControl1Y() * f6) + (getAnchor1Y() * f7));
    }

    public final Cubic reverse() {
        return CubicKt.Cubic(getAnchor1X(), getAnchor1Y(), getControl1X(), getControl1Y(), getControl0X(), getControl0Y(), getAnchor0X(), getAnchor0Y());
    }

    public final Pair split(float f) {
        float f2 = 1 - f;
        long jM1974pointOnCurveOOQOV4g$graphics_shapes = m1974pointOnCurveOOQOV4g$graphics_shapes(f);
        float f3 = f2 * f2;
        float f4 = 2 * f2 * f;
        float f5 = f * f;
        return TuplesKt.to(CubicKt.Cubic(getAnchor0X(), getAnchor0Y(), (getAnchor0X() * f2) + (getControl0X() * f), (getAnchor0Y() * f2) + (getControl0Y() * f), (getAnchor0X() * f3) + (getControl0X() * f4) + (getControl1X() * f5), (getAnchor0Y() * f3) + (getControl0Y() * f4) + (getControl1Y() * f5), PointKt.m1983getXDnnuFBc(jM1974pointOnCurveOOQOV4g$graphics_shapes), PointKt.m1984getYDnnuFBc(jM1974pointOnCurveOOQOV4g$graphics_shapes)), CubicKt.Cubic(PointKt.m1983getXDnnuFBc(jM1974pointOnCurveOOQOV4g$graphics_shapes), PointKt.m1984getYDnnuFBc(jM1974pointOnCurveOOQOV4g$graphics_shapes), (getControl0X() * f3) + (getControl1X() * f4) + (getAnchor1X() * f5), (getControl0Y() * f3) + (getControl1Y() * f4) + (getAnchor1Y() * f5), (getControl1X() * f2) + (getAnchor1X() * f), (getControl1Y() * f2) + (getAnchor1Y() * f), getAnchor1X(), getAnchor1Y()));
    }

    public String toString() {
        return "anchor0: (" + getAnchor0X() + ", " + getAnchor0Y() + ") control0: (" + getControl0X() + ", " + getControl0Y() + "), control1: (" + getControl1X() + ", " + getControl1Y() + "), anchor1: (" + getAnchor1X() + ", " + getAnchor1Y() + ')';
    }

    public final Cubic transformed(PointTransformer pointTransformer) {
        pointTransformer.getClass();
        MutableCubic mutableCubic = new MutableCubic();
        ArraysKt.copyInto$default(this.points, mutableCubic.getPoints$graphics_shapes(), 0, 0, 0, 14, (Object) null);
        mutableCubic.transform(pointTransformer);
        return mutableCubic;
    }

    public final boolean zeroLength$graphics_shapes() {
        return Math.abs(getAnchor0X() - getAnchor1X()) < 1.0E-4f && Math.abs(getAnchor0Y() - getAnchor1Y()) < 1.0E-4f;
    }
}
