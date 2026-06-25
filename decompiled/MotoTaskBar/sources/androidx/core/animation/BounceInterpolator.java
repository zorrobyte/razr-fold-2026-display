package androidx.core.animation;

/* JADX INFO: loaded from: classes.dex */
public class BounceInterpolator implements Interpolator {
    private static float bounce(float f) {
        return f * f * 8.0f;
    }

    @Override // androidx.core.animation.Interpolator
    public float getInterpolation(float f) {
        float fBounce;
        float f2;
        float f3 = f * 1.1226f;
        if (f3 < 0.3535f) {
            return bounce(f3);
        }
        if (f3 < 0.7408f) {
            fBounce = bounce(f3 - 0.54719f);
            f2 = 0.7f;
        } else if (f3 < 0.9644f) {
            fBounce = bounce(f3 - 0.8526f);
            f2 = 0.9f;
        } else {
            fBounce = bounce(f3 - 1.0435f);
            f2 = 0.95f;
        }
        return fBounce + f2;
    }
}
