package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;

/* JADX INFO: compiled from: PolygonMeasure.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LengthMeasurer implements Measurer {
    private final int segments = 3;

    /* JADX INFO: renamed from: closestProgressTo-XgqJiTY, reason: not valid java name */
    private final long m1068closestProgressToXgqJiTY(Cubic cubic, float f) {
        long jM13constructorimpl = FloatFloatPair.m13constructorimpl(cubic.getAnchor0X(), cubic.getAnchor0Y());
        int i = this.segments;
        float f2 = 0.0f;
        int i2 = 1;
        if (1 <= i) {
            float f3 = f;
            while (true) {
                float f4 = i2 / this.segments;
                long jM1067pointOnCurveOOQOV4g$graphics_shapes = cubic.m1067pointOnCurveOOQOV4g$graphics_shapes(f4);
                float fM1074getDistanceDnnuFBc = PointKt.m1074getDistanceDnnuFBc(PointKt.m1079minusybeJwSQ(jM1067pointOnCurveOOQOV4g$graphics_shapes, jM13constructorimpl));
                if (fM1074getDistanceDnnuFBc < f3) {
                    f3 -= fM1074getDistanceDnnuFBc;
                    f2 += fM1074getDistanceDnnuFBc;
                    if (i2 == i) {
                        break;
                    }
                    i2++;
                    jM13constructorimpl = jM1067pointOnCurveOOQOV4g$graphics_shapes;
                } else {
                    return FloatFloatPair.m13constructorimpl(f4 - ((1.0f - (f3 / fM1074getDistanceDnnuFBc)) / this.segments), f);
                }
            }
        }
        return FloatFloatPair.m13constructorimpl(1.0f, f2);
    }

    @Override // androidx.graphics.shapes.Measurer
    public float findCubicCutPoint(Cubic cubic, float f) {
        cubic.getClass();
        return Float.intBitsToFloat((int) (m1068closestProgressToXgqJiTY(cubic, f) >> 32));
    }

    @Override // androidx.graphics.shapes.Measurer
    public float measureCubic(Cubic cubic) {
        cubic.getClass();
        return Float.intBitsToFloat((int) (m1068closestProgressToXgqJiTY(cubic, Float.POSITIVE_INFINITY) & 4294967295L));
    }
}
