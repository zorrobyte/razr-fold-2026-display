package androidx.interpolator.view.animation;

/* JADX INFO: loaded from: classes.dex */
abstract class LookupTableInterpolator {
    static float interpolate(float[] fArr, float f, float f2) {
        if (f2 >= 1.0f) {
            return 1.0f;
        }
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        int iMin = Math.min((int) ((fArr.length - 1) * f2), fArr.length - 2);
        float f3 = (f2 - (iMin * f)) / f;
        float f4 = fArr[iMin];
        return f4 + (f3 * (fArr[iMin + 1] - f4));
    }
}
