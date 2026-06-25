package com.android.systemui.statusbar.notification.stack;

import android.util.Property;
import androidx.collection.ArraySet;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class AnimationFilter {
    boolean animateAlpha;
    boolean animateHeight;
    boolean animateHideSensitive;
    boolean animateTopInset;
    boolean animateX;
    public boolean animateY;
    boolean animateZ;
    long customDelay;
    boolean hasDelays;
    boolean hasGoToFullShadeEvent;
    private ArraySet mAnimatedProperties = new ArraySet();

    public AnimationFilter animateAlpha() {
        this.animateAlpha = true;
        return this;
    }

    public AnimationFilter animateHeight() {
        this.animateHeight = true;
        return this;
    }

    public AnimationFilter animateHideSensitive() {
        this.animateHideSensitive = true;
        return this;
    }

    public AnimationFilter animateTopInset() {
        this.animateTopInset = true;
        return this;
    }

    public AnimationFilter animateY() {
        this.animateY = true;
        return this;
    }

    public AnimationFilter animateZ() {
        this.animateZ = true;
        return this;
    }

    public void applyCombination(ArrayList arrayList) {
        reset();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            NotificationStackScrollLayout.AnimationEvent animationEvent = (NotificationStackScrollLayout.AnimationEvent) arrayList.get(i);
            combineFilter(((NotificationStackScrollLayout.AnimationEvent) arrayList.get(i)).filter);
            if (animationEvent.animationType == 7) {
                this.hasGoToFullShadeEvent = true;
            }
        }
    }

    public void combineFilter(AnimationFilter animationFilter) {
        this.animateAlpha |= animationFilter.animateAlpha;
        this.animateX |= animationFilter.animateX;
        this.animateY |= animationFilter.animateY;
        this.animateZ |= animationFilter.animateZ;
        this.animateHeight |= animationFilter.animateHeight;
        this.animateTopInset |= animationFilter.animateTopInset;
        this.animateHideSensitive |= animationFilter.animateHideSensitive;
        this.hasDelays |= animationFilter.hasDelays;
        this.mAnimatedProperties.addAll(animationFilter.mAnimatedProperties);
    }

    public AnimationFilter hasDelays() {
        this.hasDelays = true;
        return this;
    }

    public void reset() {
        this.animateAlpha = false;
        this.animateX = false;
        this.animateY = false;
        this.animateZ = false;
        this.animateHeight = false;
        this.animateTopInset = false;
        this.animateHideSensitive = false;
        this.hasDelays = false;
        this.hasGoToFullShadeEvent = false;
        this.customDelay = -1L;
        this.mAnimatedProperties.clear();
    }

    public boolean shouldAnimateProperty(Property property) {
        return this.mAnimatedProperties.contains(property);
    }
}
