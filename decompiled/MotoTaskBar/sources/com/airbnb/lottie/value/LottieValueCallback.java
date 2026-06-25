package com.airbnb.lottie.value;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;

/* JADX INFO: loaded from: classes.dex */
public class LottieValueCallback {
    private BaseKeyframeAnimation animation;
    private final LottieFrameInfo frameInfo;
    protected Object value;

    public LottieValueCallback() {
        this.frameInfo = new LottieFrameInfo();
        this.value = null;
    }

    public LottieValueCallback(Object obj) {
        this.frameInfo = new LottieFrameInfo();
        this.value = obj;
    }

    public Object getValue(LottieFrameInfo lottieFrameInfo) {
        return this.value;
    }

    public final Object getValueInternal(float f, float f2, Object obj, Object obj2, float f3, float f4, float f5) {
        return getValue(this.frameInfo.set(f, f2, obj, obj2, f3, f4, f5));
    }

    public final void setAnimation(BaseKeyframeAnimation baseKeyframeAnimation) {
        this.animation = baseKeyframeAnimation;
    }
}
