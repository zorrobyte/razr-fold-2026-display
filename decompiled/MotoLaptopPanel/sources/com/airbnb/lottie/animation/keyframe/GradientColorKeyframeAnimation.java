package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GradientColorKeyframeAnimation extends KeyframeAnimation {
    private final GradientColor gradientColor;

    public GradientColorKeyframeAnimation(List list) {
        super(list);
        int iMax = 0;
        for (int i = 0; i < list.size(); i++) {
            GradientColor gradientColor = (GradientColor) ((Keyframe) list.get(i)).startValue;
            if (gradientColor != null) {
                iMax = Math.max(iMax, gradientColor.getSize());
            }
        }
        this.gradientColor = new GradientColor(new float[iMax], new int[iMax]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public GradientColor getValue(Keyframe keyframe, float f) {
        this.gradientColor.lerp((GradientColor) keyframe.startValue, (GradientColor) keyframe.endValue, f);
        return this.gradientColor;
    }
}
