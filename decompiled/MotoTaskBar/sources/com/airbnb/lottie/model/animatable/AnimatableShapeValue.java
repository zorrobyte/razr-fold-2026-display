package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.ShapeKeyframeAnimation;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AnimatableShapeValue extends BaseAnimatableValue {
    public AnimatableShapeValue(List list) {
        super(list);
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public ShapeKeyframeAnimation createAnimation() {
        return new ShapeKeyframeAnimation(this.keyframes);
    }

    @Override // com.airbnb.lottie.model.animatable.BaseAnimatableValue, com.airbnb.lottie.model.animatable.AnimatableValue
    public /* bridge */ /* synthetic */ List getKeyframes() {
        return super.getKeyframes();
    }

    @Override // com.airbnb.lottie.model.animatable.BaseAnimatableValue, com.airbnb.lottie.model.animatable.AnimatableValue
    public /* bridge */ /* synthetic */ boolean isStatic() {
        return super.isStatic();
    }

    @Override // com.airbnb.lottie.model.animatable.BaseAnimatableValue
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }
}
