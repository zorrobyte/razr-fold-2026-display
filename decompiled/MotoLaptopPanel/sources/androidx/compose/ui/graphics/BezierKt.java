package androidx.compose.ui.graphics;

import androidx.collection.FloatFloatPair;
import androidx.compose.ui.util.MathHelpersKt;

/* JADX INFO: compiled from: Bezier.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class BezierKt {
    public static final long computeCubicVerticalBounds(float f, float f2, float f3, float f4, float[] fArr, int i) {
        float f5 = (f2 - f) * 3.0f;
        float f6 = (f3 - f2) * 3.0f;
        float f7 = (f4 - f3) * 3.0f;
        int iFindQuadraticRoots = findQuadraticRoots(f5, f6, f7, fArr, i);
        float f8 = (f6 - f5) * 2.0f;
        int iWriteValidRootInUnitRange = iFindQuadraticRoots + writeValidRootInUnitRange((-f8) / (((f7 - f6) * 2.0f) - f8), fArr, i + iFindQuadraticRoots);
        float fMin = Math.min(f, f4);
        float fMax = Math.max(f, f4);
        for (int i2 = 0; i2 < iWriteValidRootInUnitRange; i2++) {
            float fEvaluateCubic = evaluateCubic(f, f2, f3, f4, fArr[i2]);
            fMin = Math.min(fMin, fEvaluateCubic);
            fMax = Math.max(fMax, fEvaluateCubic);
        }
        return FloatFloatPair.m11constructorimpl(fMin, fMax);
    }

    public static final float evaluateCubic(float f, float f2, float f3) {
        return ((((((f - f2) + 0.33333334f) * f3) + (f2 - (2.0f * f))) * f3) + f) * 3.0f * f3;
    }

    private static final float evaluateCubic(float f, float f2, float f3, float f4, float f5) {
        float f6 = (f4 + ((f2 - f3) * 3.0f)) - f;
        return (((((f6 * f5) + (((f3 - (2.0f * f2)) + f) * 3.0f)) * f5) + ((f2 - f) * 3.0f)) * f5) + f;
    }

    public static final float findFirstCubicRoot(float f, float f2, float f3, float f4) {
        float f5;
        float f6;
        double d = f;
        double d2 = ((d - (((double) f2) * 2.0d)) + ((double) f3)) * 3.0d;
        double d3 = ((double) (f2 - f)) * 3.0d;
        double d4 = ((double) (-f)) + (((double) (f2 - f3)) * 3.0d) + ((double) f4);
        if (Math.abs(d4 - 0.0d) < 1.0E-7d) {
            if (Math.abs(d2 - 0.0d) < 1.0E-7d) {
                if (Math.abs(d3 - 0.0d) < 1.0E-7d) {
                    return Float.NaN;
                }
                float f7 = (float) ((-d) / d3);
                f5 = f7 >= 0.0f ? f7 : 0.0f;
                f6 = f5 <= 1.0f ? f5 : 1.0f;
                if (Math.abs(f6 - f7) > 1.05E-6f) {
                    return Float.NaN;
                }
                return f6;
            }
            double dSqrt = Math.sqrt((d3 * d3) - ((4.0d * d2) * d));
            double d5 = d2 * 2.0d;
            float f8 = (float) ((dSqrt - d3) / d5);
            float f9 = f8 < 0.0f ? 0.0f : f8;
            if (f9 > 1.0f) {
                f9 = 1.0f;
            }
            if (Math.abs(f9 - f8) > 1.05E-6f) {
                f9 = Float.NaN;
            }
            if (!Float.isNaN(f9)) {
                return f9;
            }
            float f10 = (float) (((-d3) - dSqrt) / d5);
            f5 = f10 >= 0.0f ? f10 : 0.0f;
            f6 = f5 <= 1.0f ? f5 : 1.0f;
            if (Math.abs(f6 - f10) > 1.05E-6f) {
                return Float.NaN;
            }
            return f6;
        }
        double d6 = d2 / d4;
        double d7 = d3 / d4;
        double d8 = d / d4;
        double d9 = ((d7 * 3.0d) - (d6 * d6)) / 9.0d;
        double d10 = (((((2.0d * d6) * d6) * d6) - ((9.0d * d6) * d7)) + (d8 * 27.0d)) / 54.0d;
        double d11 = d9 * d9 * d9;
        double d12 = (d10 * d10) + d11;
        double d13 = d6 / 3.0d;
        if (d12 >= 0.0d) {
            if (d12 != 0.0d) {
                double dSqrt2 = Math.sqrt(d12);
                float fFastCbrt = (float) (((double) (MathHelpersKt.fastCbrt((float) ((-d10) + dSqrt2)) - MathHelpersKt.fastCbrt((float) (d10 + dSqrt2)))) - d13);
                f5 = fFastCbrt >= 0.0f ? fFastCbrt : 0.0f;
                f6 = f5 <= 1.0f ? f5 : 1.0f;
                if (Math.abs(f6 - fFastCbrt) > 1.05E-6f) {
                    return Float.NaN;
                }
                return f6;
            }
            float f11 = -MathHelpersKt.fastCbrt((float) d10);
            float f12 = (float) d13;
            float f13 = (2.0f * f11) - f12;
            float f14 = f13 < 0.0f ? 0.0f : f13;
            if (f14 > 1.0f) {
                f14 = 1.0f;
            }
            if (Math.abs(f14 - f13) > 1.05E-6f) {
                f14 = Float.NaN;
            }
            if (!Float.isNaN(f14)) {
                return f14;
            }
            float f15 = (-f11) - f12;
            f5 = f15 >= 0.0f ? f15 : 0.0f;
            f6 = f5 <= 1.0f ? f5 : 1.0f;
            if (Math.abs(f6 - f15) > 1.05E-6f) {
                return Float.NaN;
            }
            return f6;
        }
        double dSqrt3 = Math.sqrt(-d11);
        double d14 = (-d10) / dSqrt3;
        if (d14 < -1.0d) {
            d14 = -1.0d;
        }
        if (d14 > 1.0d) {
            d14 = 1.0d;
        }
        double dAcos = Math.acos(d14);
        double dFastCbrt = MathHelpersKt.fastCbrt((float) dSqrt3) * 2.0f;
        float fCos = (float) ((Math.cos(dAcos / 3.0d) * dFastCbrt) - d13);
        float f16 = fCos < 0.0f ? 0.0f : fCos;
        if (f16 > 1.0f) {
            f16 = 1.0f;
        }
        if (Math.abs(f16 - fCos) > 1.05E-6f) {
            f16 = Float.NaN;
        }
        if (!Float.isNaN(f16)) {
            return f16;
        }
        float fCos2 = (float) ((Math.cos((6.283185307179586d + dAcos) / 3.0d) * dFastCbrt) - d13);
        float f17 = fCos2 < 0.0f ? 0.0f : fCos2;
        if (f17 > 1.0f) {
            f17 = 1.0f;
        }
        if (Math.abs(f17 - fCos2) > 1.05E-6f) {
            f17 = Float.NaN;
        }
        if (!Float.isNaN(f17)) {
            return f17;
        }
        float fCos3 = (float) ((dFastCbrt * Math.cos((dAcos + 12.566370614359172d) / 3.0d)) - d13);
        f5 = fCos3 >= 0.0f ? fCos3 : 0.0f;
        f6 = f5 <= 1.0f ? f5 : 1.0f;
        if (Math.abs(f6 - fCos3) > 1.05E-6f) {
            return Float.NaN;
        }
        return f6;
    }

    private static final int findQuadraticRoots(float f, float f2, float f3, float[] fArr, int i) {
        double d = f;
        double d2 = f2;
        double d3 = f3;
        double d4 = d2 * 2.0d;
        double d5 = (d - d4) + d3;
        if (d5 == 0.0d) {
            if (d2 == d3) {
                return 0;
            }
            return writeValidRootInUnitRange((float) ((d4 - d3) / (d4 - (d3 * 2.0d))), fArr, i);
        }
        double d6 = -Math.sqrt((d2 * d2) - (d3 * d));
        double d7 = (-d) + d2;
        int iWriteValidRootInUnitRange = writeValidRootInUnitRange((float) ((-(d6 + d7)) / d5), fArr, i);
        int iWriteValidRootInUnitRange2 = iWriteValidRootInUnitRange + writeValidRootInUnitRange((float) ((d6 - d7) / d5), fArr, i + iWriteValidRootInUnitRange);
        if (iWriteValidRootInUnitRange2 <= 1) {
            return iWriteValidRootInUnitRange2;
        }
        float f4 = fArr[i];
        int i2 = i + 1;
        float f5 = fArr[i2];
        if (f4 <= f5) {
            return f4 == f5 ? iWriteValidRootInUnitRange2 - 1 : iWriteValidRootInUnitRange2;
        }
        fArr[i] = f5;
        fArr[i2] = f4;
        return iWriteValidRootInUnitRange2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int writeValidRootInUnitRange(float f, float[] fArr, int i) {
        float f2 = f >= 0.0f ? f : 0.0f;
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        if (Math.abs(f2 - f) > 1.05E-6f) {
            f2 = Float.NaN;
        }
        fArr[i] = f2;
        return !Float.isNaN(f2) ? 1 : 0;
    }
}
