package androidx.core.animation;

/* JADX INFO: loaded from: classes.dex */
public class IntEvaluator implements TypeEvaluator {
    private static final IntEvaluator sInstance = new IntEvaluator();

    private IntEvaluator() {
    }

    public static IntEvaluator getInstance() {
        return sInstance;
    }

    @Override // androidx.core.animation.TypeEvaluator
    public Integer evaluate(float f, Integer num, Integer num2) {
        return Integer.valueOf((int) (num.intValue() + (f * (num2.intValue() - r0))));
    }
}
