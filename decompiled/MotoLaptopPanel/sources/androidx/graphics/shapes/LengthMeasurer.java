package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;

/* JADX INFO: compiled from: PolygonMeasure.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LengthMeasurer implements Measurer {
    private final int segments = 3;

    /* JADX INFO: renamed from: closestProgressTo-XgqJiTY, reason: not valid java name */
    private final long m1975closestProgressToXgqJiTY(Cubic cubic, float f) {
        long jM11constructorimpl = FloatFloatPair.m11constructorimpl(cubic.getAnchor0X(), cubic.getAnchor0Y());
        int i = this.segments;
        float f2 = 0.0f;
        int i2 = 1;
        if (1 <= i) {
            float f3 = f;
            while (true) {
                float f4 = i2 / this.segments;
                long jM1974pointOnCurveOOQOV4g$graphics_shapes = cubic.m1974pointOnCurveOOQOV4g$graphics_shapes(f4);
                float fM1981getDistanceDnnuFBc = PointKt.m1981getDistanceDnnuFBc(PointKt.m1986minusybeJwSQ(jM1974pointOnCurveOOQOV4g$graphics_shapes, jM11constructorimpl));
                if (fM1981getDistanceDnnuFBc < f3) {
                    f3 -= fM1981getDistanceDnnuFBc;
                    f2 += fM1981getDistanceDnnuFBc;
                    if (i2 == i) {
                        break;
                    }
                    i2++;
                    jM11constructorimpl = jM1974pointOnCurveOOQOV4g$graphics_shapes;
                } else {
                    return FloatFloatPair.m11constructorimpl(f4 - ((1.0f - (f3 / fM1981getDistanceDnnuFBc)) / this.segments), f);
                }
            }
        }
        return FloatFloatPair.m11constructorimpl(1.0f, f2);
    }

    @Override // androidx.graphics.shapes.Measurer
    public float findCubicCutPoint(Cubic cubic, float f) {
        cubic.getClass();
        return Float.intBitsToFloat((int) (m1975closestProgressToXgqJiTY(cubic, f) >> 32));
    }

    @Override // androidx.graphics.shapes.Measurer
    public float measureCubic(Cubic cubic) {
        cubic.getClass();
        return Float.intBitsToFloat((int) (m1975closestProgressToXgqJiTY(cubic, Float.POSITIVE_INFINITY) & 4294967295L));
    }
}
