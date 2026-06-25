package androidx.core.animation;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
interface Keyframes extends Cloneable {

    public interface FloatKeyframes extends Keyframes {
        float getFloatValue(float f);
    }

    Keyframes clone();

    List getKeyframes();

    void setEvaluator(TypeEvaluator typeEvaluator);
}
