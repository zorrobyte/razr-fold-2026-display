package androidx.core.animation;

/* JADX INFO: loaded from: classes.dex */
public class AccelerateDecelerateInterpolator implements Interpolator {
    @Override // androidx.core.animation.Interpolator
    public float getInterpolation(float f) {
        return ((float) (Math.cos(((double) (f + 1.0f)) * 3.141592653589793d) / 2.0d)) + 0.5f;
    }
}
