package androidx.core.animation;

/* JADX INFO: loaded from: classes.dex */
public final class FloatEvaluator implements TypeEvaluator {
    private static final FloatEvaluator sInstance = new FloatEvaluator();

    private FloatEvaluator() {
    }

    public static FloatEvaluator getInstance() {
        return sInstance;
    }

    @Override // androidx.core.animation.TypeEvaluator
    public Float evaluate(float f, Float f2, Float f3) {
        float fFloatValue = f2.floatValue();
        return Float.valueOf(fFloatValue + (f * (f3.floatValue() - fFloatValue)));
    }
}
