package com.airbnb.lottie.animation.keyframe;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.parser.DropShadowEffect;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;

/* JADX INFO: loaded from: classes.dex */
public class DropShadowKeyframeAnimation implements BaseKeyframeAnimation.AnimationListener {
    private final BaseKeyframeAnimation color;
    private final FloatKeyframeAnimation direction;
    private final FloatKeyframeAnimation distance;
    private final BaseLayer layer;
    private final BaseKeyframeAnimation.AnimationListener listener;
    private final FloatKeyframeAnimation opacity;
    private final FloatKeyframeAnimation radius;
    private float paintRadius = Float.NaN;
    private float paintX = Float.NaN;
    private float paintY = Float.NaN;
    private int paintColor = 0;
    private final float[] matrixValues = new float[9];

    public DropShadowKeyframeAnimation(BaseKeyframeAnimation.AnimationListener animationListener, BaseLayer baseLayer, DropShadowEffect dropShadowEffect) {
        this.listener = animationListener;
        this.layer = baseLayer;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = dropShadowEffect.getColor().createAnimation();
        this.color = baseKeyframeAnimationCreateAnimation;
        baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation);
        FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation = dropShadowEffect.getOpacity().createAnimation();
        this.opacity = floatKeyframeAnimationCreateAnimation;
        floatKeyframeAnimationCreateAnimation.addUpdateListener(this);
        baseLayer.addAnimation(floatKeyframeAnimationCreateAnimation);
        FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation2 = dropShadowEffect.getDirection().createAnimation();
        this.direction = floatKeyframeAnimationCreateAnimation2;
        floatKeyframeAnimationCreateAnimation2.addUpdateListener(this);
        baseLayer.addAnimation(floatKeyframeAnimationCreateAnimation2);
        FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation3 = dropShadowEffect.getDistance().createAnimation();
        this.distance = floatKeyframeAnimationCreateAnimation3;
        floatKeyframeAnimationCreateAnimation3.addUpdateListener(this);
        baseLayer.addAnimation(floatKeyframeAnimationCreateAnimation3);
        FloatKeyframeAnimation floatKeyframeAnimationCreateAnimation4 = dropShadowEffect.getRadius().createAnimation();
        this.radius = floatKeyframeAnimationCreateAnimation4;
        floatKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        baseLayer.addAnimation(floatKeyframeAnimationCreateAnimation4);
    }

    public void applyTo(Paint paint, Matrix matrix, int i) {
        float floatValue = this.direction.getFloatValue() * 0.017453292f;
        float fFloatValue = ((Float) this.distance.getValue()).floatValue();
        double d = floatValue;
        float fSin = ((float) Math.sin(d)) * fFloatValue;
        float fCos = ((float) Math.cos(d + 3.141592653589793d)) * fFloatValue;
        this.layer.transform.getMatrix().getValues(this.matrixValues);
        float[] fArr = this.matrixValues;
        float f = fArr[0];
        float f2 = fArr[4];
        matrix.getValues(fArr);
        float[] fArr2 = this.matrixValues;
        float f3 = fArr2[0] / f;
        float f4 = fSin * f3;
        float f5 = fCos * (fArr2[4] / f2);
        int iIntValue = ((Integer) this.color.getValue()).intValue();
        int iArgb = Color.argb(Math.round((((Float) this.opacity.getValue()).floatValue() * i) / 255.0f), Color.red(iIntValue), Color.green(iIntValue), Color.blue(iIntValue));
        float fMax = Math.max(((Float) this.radius.getValue()).floatValue() * f3 * 0.33f, Float.MIN_VALUE);
        if (this.paintRadius == fMax && this.paintX == f4 && this.paintY == f5 && this.paintColor == iArgb) {
            return;
        }
        this.paintRadius = fMax;
        this.paintX = f4;
        this.paintY = f5;
        this.paintColor = iArgb;
        paint.setShadowLayer(fMax, f4, f5, iArgb);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        this.listener.onValueChanged();
    }

    public void setColorCallback(LottieValueCallback lottieValueCallback) {
        this.color.setValueCallback(lottieValueCallback);
    }

    public void setDirectionCallback(LottieValueCallback lottieValueCallback) {
        this.direction.setValueCallback(lottieValueCallback);
    }

    public void setDistanceCallback(LottieValueCallback lottieValueCallback) {
        this.distance.setValueCallback(lottieValueCallback);
    }

    public void setOpacityCallback(final LottieValueCallback lottieValueCallback) {
        if (lottieValueCallback == null) {
            this.opacity.setValueCallback(null);
        } else {
            this.opacity.setValueCallback(new LottieValueCallback() { // from class: com.airbnb.lottie.animation.keyframe.DropShadowKeyframeAnimation.1
                @Override // com.airbnb.lottie.value.LottieValueCallback
                public Float getValue(LottieFrameInfo lottieFrameInfo) {
                    Float f = (Float) lottieValueCallback.getValue(lottieFrameInfo);
                    if (f == null) {
                        return null;
                    }
                    return Float.valueOf(f.floatValue() * 2.55f);
                }
            });
        }
    }

    public void setRadiusCallback(LottieValueCallback lottieValueCallback) {
        this.radius.setValueCallback(lottieValueCallback);
    }
}
