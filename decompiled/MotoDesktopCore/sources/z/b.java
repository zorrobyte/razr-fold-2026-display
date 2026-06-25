package z;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
public abstract class b implements Interpolator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final float[] f2869a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final float f2870b;

    public b(float[] fArr) {
        this.f2869a = fArr;
        this.f2870b = 1.0f / (fArr.length - 1);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f2) {
        if (f2 >= 1.0f) {
            return 1.0f;
        }
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        float[] fArr = this.f2869a;
        int iMin = Math.min((int) ((fArr.length - 1) * f2), fArr.length - 2);
        float f3 = this.f2870b;
        float f4 = (f2 - (iMin * f3)) / f3;
        float f5 = fArr[iMin];
        return ((fArr[iMin + 1] - f5) * f4) + f5;
    }
}
