package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.ArrayMap;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class AnimationProperties {
    public long delay;
    public long duration;
    private Consumer mAnimationCancelAction;
    private Consumer mAnimationEndAction;
    private ArrayMap mInterpolatorMap;

    public AnimationFilter getAnimationFilter() {
        return new AnimationFilter(this) { // from class: com.android.systemui.statusbar.notification.stack.AnimationProperties.1
            @Override // com.android.systemui.statusbar.notification.stack.AnimationFilter
            public boolean shouldAnimateProperty(Property property) {
                return true;
            }
        };
    }

    public AnimatorListenerAdapter getAnimationFinishListener(final Property property) {
        final Consumer consumer = this.mAnimationEndAction;
        if (consumer == null && this.mAnimationCancelAction == null) {
            return null;
        }
        final Consumer consumer2 = this.mAnimationCancelAction;
        return new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.AnimationProperties.2
            private boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
                Consumer consumer3 = consumer2;
                if (consumer3 != null) {
                    consumer3.accept(property);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Consumer consumer3;
                if (this.mCancelled || (consumer3 = consumer) == null) {
                    return;
                }
                consumer3.accept(property);
            }
        };
    }

    public Interpolator getCustomInterpolator(View view, Property property) {
        ArrayMap arrayMap = this.mInterpolatorMap;
        if (arrayMap != null) {
            return (Interpolator) arrayMap.get(property);
        }
        return null;
    }

    public AnimationProperties setCustomInterpolator(Property property, Interpolator interpolator) {
        if (this.mInterpolatorMap == null) {
            this.mInterpolatorMap = new ArrayMap();
        }
        this.mInterpolatorMap.put(property, interpolator);
        return this;
    }

    public AnimationProperties setDelay(long j) {
        this.delay = j;
        return this;
    }

    public AnimationProperties setDuration(long j) {
        this.duration = j;
        return this;
    }

    public boolean wasAdded(View view) {
        return false;
    }
}
