package androidx.compose.ui.unit.fontscaling;

/* JADX INFO: compiled from: MathUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MathUtils {
    public static final MathUtils INSTANCE = new MathUtils();

    private MathUtils() {
    }

    public final float constrainedMap(float f, float f2, float f3, float f4, float f5) {
        return lerp(f, f2, Math.max(0.0f, Math.min(1.0f, lerpInv(f3, f4, f5))));
    }

    public final float lerp(float f, float f2, float f3) {
        return f + ((f2 - f) * f3);
    }

    public final float lerpInv(float f, float f2, float f3) {
        if (f == f2) {
            return 0.0f;
        }
        return (f3 - f) / (f2 - f);
    }
}
