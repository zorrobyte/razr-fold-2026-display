package com.google.android.material.color.utilities;

/* JADX INFO: loaded from: classes.dex */
public final class ViewingConditions {
    public static final ViewingConditions DEFAULT = defaultWithBackgroundLstar(50.0d);
    private final double aw;
    private final double c;
    private final double fl;
    private final double flRoot;
    private final double n;
    private final double nbb;
    private final double nc;
    private final double ncb;
    private final double[] rgbD;
    private final double z;

    private ViewingConditions(double d, double d2, double d3, double d4, double d5, double d6, double[] dArr, double d7, double d8, double d9) {
        this.n = d;
        this.aw = d2;
        this.nbb = d3;
        this.ncb = d4;
        this.c = d5;
        this.nc = d6;
        this.rgbD = dArr;
        this.fl = d7;
        this.flRoot = d8;
        this.z = d9;
    }

    public static ViewingConditions defaultWithBackgroundLstar(double d) {
        return make(ColorUtils.whitePointD65(), (ColorUtils.yFromLstar(50.0d) * 63.66197723675813d) / 100.0d, d, 2.0d, false);
    }

    public static ViewingConditions make(double[] dArr, double d, double d2, double d3, boolean z) {
        double d4;
        double dExp;
        double dMax = Math.max(0.1d, d2);
        double[][] dArr2 = Cam16.XYZ_TO_CAM16RGB;
        double d5 = dArr[0];
        double[] dArr3 = dArr2[0];
        double d6 = dArr3[0] * d5;
        double d7 = dArr[1];
        double d8 = d6 + (dArr3[1] * d7);
        double d9 = dArr[2];
        double d10 = d8 + (dArr3[2] * d9);
        double[] dArr4 = dArr2[1];
        double d11 = (dArr4[0] * d5) + (dArr4[1] * d7) + (dArr4[2] * d9);
        double[] dArr5 = dArr2[2];
        double d12 = (d5 * dArr5[0]) + (d7 * dArr5[1]) + (d9 * dArr5[2]);
        double d13 = (d3 / 10.0d) + 0.8d;
        double dLerp = d13 >= 0.9d ? MathUtils.lerp(0.59d, 0.69d, (d13 - 0.9d) * 10.0d) : MathUtils.lerp(0.525d, 0.59d, (d13 - 0.8d) * 10.0d);
        if (z) {
            d4 = 0.1d;
            dExp = 1.0d;
        } else {
            d4 = 0.1d;
            dExp = (1.0d - (Math.exp(((-d) - 42.0d) / 92.0d) * 0.2777777777777778d)) * d13;
        }
        double dClampDouble = MathUtils.clampDouble(0.0d, 1.0d, dExp);
        double[] dArr6 = {(((100.0d / d10) * dClampDouble) + 1.0d) - dClampDouble, (((100.0d / d11) * dClampDouble) + 1.0d) - dClampDouble, (((100.0d / d12) * dClampDouble) + 1.0d) - dClampDouble};
        double d14 = 5.0d * d;
        double d15 = 1.0d / (d14 + 1.0d);
        double d16 = d15 * d15 * d15 * d15;
        double d17 = 1.0d - d16;
        double dCbrt = (d16 * d) + (d17 * d4 * d17 * Math.cbrt(d14));
        double dYFromLstar = ColorUtils.yFromLstar(dMax) / dArr[1];
        double dSqrt = Math.sqrt(dYFromLstar) + 1.48d;
        double dPow = 0.725d / Math.pow(dYFromLstar, 0.2d);
        double[] dArr7 = {Math.pow(((dArr6[0] * dCbrt) * d10) / 100.0d, 0.42d), Math.pow(((dArr6[1] * dCbrt) * d11) / 100.0d, 0.42d), Math.pow(((dArr6[2] * dCbrt) * d12) / 100.0d, 0.42d)};
        double d18 = dArr7[0];
        double d19 = (d18 * 400.0d) / (d18 + 27.13d);
        double d20 = dArr7[1];
        double d21 = (d20 * 400.0d) / (d20 + 27.13d);
        double d22 = dArr7[2];
        double[] dArr8 = {d19, d21, (400.0d * d22) / (d22 + 27.13d)};
        return new ViewingConditions(dYFromLstar, ((dArr8[0] * 2.0d) + dArr8[1] + (dArr8[2] * 0.05d)) * dPow, dPow, dPow, dLerp, d13, dArr6, dCbrt, Math.pow(dCbrt, 0.25d), dSqrt);
    }

    public double getAw() {
        return this.aw;
    }

    double getC() {
        return this.c;
    }

    double getFl() {
        return this.fl;
    }

    public double getFlRoot() {
        return this.flRoot;
    }

    public double getN() {
        return this.n;
    }

    public double getNbb() {
        return this.nbb;
    }

    double getNc() {
        return this.nc;
    }

    double getNcb() {
        return this.ncb;
    }

    public double[] getRgbD() {
        return this.rgbD;
    }

    double getZ() {
        return this.z;
    }
}
