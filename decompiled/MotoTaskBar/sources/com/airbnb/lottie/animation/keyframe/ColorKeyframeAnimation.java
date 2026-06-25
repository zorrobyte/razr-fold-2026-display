package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ColorKeyframeAnimation extends KeyframeAnimation {
    public ColorKeyframeAnimation(List list) {
        super(list);
    }

    public int getIntValue() {
        return getIntValue(getCurrentKeyframe(), getInterpolatedCurrentKeyframeProgress());
    }

    public int getIntValue(Keyframe keyframe, float f) {
        float f2;
        Float f3;
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        LottieValueCallback lottieValueCallback = this.valueCallback;
        if (lottieValueCallback == null || (f3 = keyframe.endFrame) == null) {
            f2 = f;
        } else {
            f2 = f;
            Integer num = (Integer) lottieValueCallback.getValueInternal(keyframe.startFrame, f3.floatValue(), (Integer) keyframe.startValue, (Integer) keyframe.endValue, f2, getLinearCurrentKeyframeProgress(), getProgress());
            if (num != null) {
                return num.intValue();
            }
        }
        return GammaEvaluator.evaluate(MiscUtils.clamp(f2, 0.0f, 1.0f), ((Integer) keyframe.startValue).intValue(), ((Integer) keyframe.endValue).intValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public Integer getValue(Keyframe keyframe, float f) {
        return Integer.valueOf(getIntValue(keyframe, f));
    }
}
