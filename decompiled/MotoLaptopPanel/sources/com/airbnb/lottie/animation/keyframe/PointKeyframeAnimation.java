package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PointKeyframeAnimation extends KeyframeAnimation {
    private final PointF point;

    public PointKeyframeAnimation(List list) {
        super(list);
        this.point = new PointF();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public PointF getValue(Keyframe keyframe, float f) {
        return getValue(keyframe, f, f, f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public PointF getValue(Keyframe keyframe, float f, float f2, float f3) {
        Object obj;
        PointF pointF;
        Object obj2 = keyframe.startValue;
        if (obj2 == null || (obj = keyframe.endValue) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        PointF pointF2 = (PointF) obj2;
        PointF pointF3 = (PointF) obj;
        LottieValueCallback lottieValueCallback = this.valueCallback;
        if (lottieValueCallback != null && (pointF = (PointF) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), pointF2, pointF3, f, getLinearCurrentKeyframeProgress(), getProgress())) != null) {
            return pointF;
        }
        PointF pointF4 = this.point;
        float f4 = pointF2.x;
        float f5 = f4 + (f2 * (pointF3.x - f4));
        float f6 = pointF2.y;
        pointF4.set(f5, f6 + (f3 * (pointF3.y - f6)));
        return this.point;
    }
}
