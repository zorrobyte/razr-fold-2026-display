package androidx.core.animation;

import androidx.core.animation.Keyframe;
import androidx.core.animation.Keyframes;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class FloatKeyframeSet extends KeyframeSet implements Keyframes.FloatKeyframes {
    FloatKeyframeSet(Keyframe.FloatKeyframe... floatKeyframeArr) {
        super(floatKeyframeArr);
    }

    @Override // androidx.core.animation.Keyframes
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public FloatKeyframeSet m1048clone() {
        List list = this.mKeyframes;
        int size = list.size();
        Keyframe.FloatKeyframe[] floatKeyframeArr = new Keyframe.FloatKeyframe[size];
        for (int i = 0; i < size; i++) {
            floatKeyframeArr[i] = (Keyframe.FloatKeyframe) ((Keyframe) list.get(i)).m1049clone();
        }
        return new FloatKeyframeSet(floatKeyframeArr);
    }

    @Override // androidx.core.animation.Keyframes.FloatKeyframes
    public float getFloatValue(float f) {
        float floatValue;
        float floatValue2;
        float f2;
        if (f <= 0.0f) {
            Keyframe.FloatKeyframe floatKeyframe = (Keyframe.FloatKeyframe) this.mKeyframes.get(0);
            Keyframe.FloatKeyframe floatKeyframe2 = (Keyframe.FloatKeyframe) this.mKeyframes.get(1);
            floatValue = floatKeyframe.getFloatValue();
            floatValue2 = floatKeyframe2.getFloatValue();
            float fraction = floatKeyframe.getFraction();
            float fraction2 = floatKeyframe2.getFraction();
            Interpolator interpolator = floatKeyframe2.getInterpolator();
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            f2 = (f - fraction) / (fraction2 - fraction);
            TypeEvaluator typeEvaluator = this.mEvaluator;
            if (typeEvaluator != null) {
                return ((Float) typeEvaluator.evaluate(f2, Float.valueOf(floatValue), Float.valueOf(floatValue2))).floatValue();
            }
        } else if (f >= 1.0f) {
            Keyframe.FloatKeyframe floatKeyframe3 = (Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            Keyframe.FloatKeyframe floatKeyframe4 = (Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            floatValue = floatKeyframe3.getFloatValue();
            floatValue2 = floatKeyframe4.getFloatValue();
            float fraction3 = floatKeyframe3.getFraction();
            float fraction4 = floatKeyframe4.getFraction();
            Interpolator interpolator2 = floatKeyframe4.getInterpolator();
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            f2 = (f - fraction3) / (fraction4 - fraction3);
            TypeEvaluator typeEvaluator2 = this.mEvaluator;
            if (typeEvaluator2 != null) {
                return ((Float) typeEvaluator2.evaluate(f2, Float.valueOf(floatValue), Float.valueOf(floatValue2))).floatValue();
            }
        } else {
            Keyframe.FloatKeyframe floatKeyframe5 = (Keyframe.FloatKeyframe) this.mKeyframes.get(0);
            int i = 1;
            while (true) {
                int i2 = this.mNumKeyframes;
                if (i >= i2) {
                    return ((Float) ((Keyframe) this.mKeyframes.get(i2 - 1)).getValue()).floatValue();
                }
                Keyframe.FloatKeyframe floatKeyframe6 = (Keyframe.FloatKeyframe) this.mKeyframes.get(i);
                if (f < floatKeyframe6.getFraction()) {
                    Interpolator interpolator3 = floatKeyframe6.getInterpolator();
                    float fraction5 = (f - floatKeyframe5.getFraction()) / (floatKeyframe6.getFraction() - floatKeyframe5.getFraction());
                    float floatValue3 = floatKeyframe5.getFloatValue();
                    float floatValue4 = floatKeyframe6.getFloatValue();
                    if (interpolator3 != null) {
                        fraction5 = interpolator3.getInterpolation(fraction5);
                    }
                    TypeEvaluator typeEvaluator3 = this.mEvaluator;
                    return typeEvaluator3 == null ? floatValue3 + (fraction5 * (floatValue4 - floatValue3)) : ((Float) typeEvaluator3.evaluate(fraction5, Float.valueOf(floatValue3), Float.valueOf(floatValue4))).floatValue();
                }
                i++;
                floatKeyframe5 = floatKeyframe6;
            }
        }
        return floatValue + (f2 * (floatValue2 - floatValue));
    }
}
