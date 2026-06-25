package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.content.Mask;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MaskKeyframeAnimation {
    private final List maskAnimations;
    private final List masks;
    private final List opacityAnimations;

    public MaskKeyframeAnimation(List list) {
        this.masks = list;
        this.maskAnimations = new ArrayList(list.size());
        this.opacityAnimations = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            this.maskAnimations.add(((Mask) list.get(i)).getMaskPath().createAnimation());
            this.opacityAnimations.add(((Mask) list.get(i)).getOpacity().createAnimation());
        }
    }

    public List getMaskAnimations() {
        return this.maskAnimations;
    }

    public List getMasks() {
        return this.masks;
    }

    public List getOpacityAnimations() {
        return this.opacityAnimations;
    }
}
