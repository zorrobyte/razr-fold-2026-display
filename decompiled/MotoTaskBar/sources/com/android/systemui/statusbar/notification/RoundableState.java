package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import android.view.View;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Roundable.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RoundableState {
    public static final Companion Companion = new Companion(null);
    private static final AnimationProperties DURATION;
    private final AnimatableProperty bottomAnimatable;
    private float bottomRoundness;
    private final Map bottomRoundnessMap;
    private float maxRadius;
    private final float[] radiiBuffer;
    private final Roundable roundable;
    private final View targetView;
    private final AnimatableProperty topAnimatable;
    private float topRoundness;
    private final Map topRoundnessMap;

    /* JADX INFO: compiled from: Roundable.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final AnimatableProperty bottomAnimatable(final Roundable roundable) {
            AnimatableProperty animatablePropertyFrom = AnimatableProperty.from(new FloatProperty() { // from class: com.android.systemui.statusbar.notification.RoundableState$Companion$bottomAnimatable$1
                {
                    super("bottomRoundness");
                }

                @Override // android.util.Property
                public Float get(View view) {
                    view.getClass();
                    return Float.valueOf(roundable.getBottomRoundness());
                }

                @Override // android.util.FloatProperty
                public void setValue(View view, float f) {
                    view.getClass();
                    roundable.getRoundableState().bottomRoundness = f;
                    roundable.applyRoundnessAndInvalidate();
                }
            }, R$id.bottom_roundess_animator_tag, R$id.bottom_roundess_animator_end_tag, R$id.bottom_roundess_animator_start_tag);
            animatablePropertyFrom.getClass();
            return animatablePropertyFrom;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final AnimatableProperty topAnimatable(final Roundable roundable) {
            AnimatableProperty animatablePropertyFrom = AnimatableProperty.from(new FloatProperty() { // from class: com.android.systemui.statusbar.notification.RoundableState$Companion$topAnimatable$1
                {
                    super("topRoundness");
                }

                @Override // android.util.Property
                public Float get(View view) {
                    view.getClass();
                    return Float.valueOf(roundable.getTopRoundness());
                }

                @Override // android.util.FloatProperty
                public void setValue(View view, float f) {
                    view.getClass();
                    roundable.getRoundableState().topRoundness = f;
                    roundable.applyRoundnessAndInvalidate();
                }
            }, R$id.top_roundess_animator_tag, R$id.top_roundess_animator_end_tag, R$id.top_roundess_animator_start_tag);
            animatablePropertyFrom.getClass();
            return animatablePropertyFrom;
        }
    }

    static {
        AnimationProperties duration = new AnimationProperties().setDuration(200L);
        duration.getClass();
        DURATION = duration;
    }

    public RoundableState(View view, Roundable roundable, float f) {
        view.getClass();
        roundable.getClass();
        this.targetView = view;
        this.roundable = roundable;
        this.maxRadius = f;
        Companion companion = Companion;
        this.topAnimatable = companion.topAnimatable(roundable);
        this.bottomAnimatable = companion.bottomAnimatable(roundable);
        this.topRoundnessMap = new LinkedHashMap();
        this.bottomRoundnessMap = new LinkedHashMap();
        this.radiiBuffer = new float[8];
    }

    public final float getBottomCornerRadius$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        int clipHeight = this.roundable.getClipHeight();
        float f = this.topRoundness;
        float f2 = this.maxRadius;
        float f3 = f * f2;
        float f4 = this.bottomRoundness;
        float f5 = f2 * f4;
        if (clipHeight == 0) {
            return 0.0f;
        }
        float f6 = f3 + f5;
        float f7 = clipHeight;
        return f6 > f7 ? f5 - (((f6 - f7) * f4) / (f + f4)) : f5;
    }

    public final float getBottomRoundness$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return this.bottomRoundness;
    }

    public final Map getBottomRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return this.bottomRoundnessMap;
    }

    public final float getMaxRadius$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return this.maxRadius;
    }

    public final float[] getRadiiBuffer$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return this.radiiBuffer;
    }

    public final View getTargetView$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return this.targetView;
    }

    public final float getTopCornerRadius$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        int clipHeight = this.roundable.getClipHeight();
        float f = this.topRoundness;
        float f2 = this.maxRadius;
        float f3 = f * f2;
        float f4 = this.bottomRoundness;
        float f5 = f2 * f4;
        if (clipHeight == 0) {
            return 0.0f;
        }
        float f6 = f5 + f3;
        float f7 = clipHeight;
        return f6 > f7 ? f3 - (((f6 - f7) * f) / (f + f4)) : f3;
    }

    public final float getTopRoundness$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return this.topRoundness;
    }

    public final Map getTopRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return this.topRoundnessMap;
    }

    public final boolean isBottomAnimating$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return PropertyAnimator.isAnimating(this.targetView, this.bottomAnimatable);
    }

    public final boolean isTopAnimating$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() {
        return PropertyAnimator.isAnimating(this.targetView, this.topAnimatable);
    }

    public final void setBottomRoundness$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(float f, boolean z) {
        PropertyAnimator.setProperty(this.targetView, this.bottomAnimatable, f, DURATION, z);
    }

    public final void setMaxRadius(float f) {
        if (this.maxRadius == f) {
            return;
        }
        this.maxRadius = f;
        this.roundable.applyRoundnessAndInvalidate();
    }

    public final void setTopRoundness$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(float f, boolean z) {
        PropertyAnimator.setProperty(this.targetView, this.topAnimatable, f, DURATION, z);
    }
}
