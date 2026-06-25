package com.android.app.animation;

import android.graphics.Path;
import androidx.core.animation.AccelerateDecelerateInterpolator;
import androidx.core.animation.AccelerateInterpolator;
import androidx.core.animation.BounceInterpolator;
import androidx.core.animation.DecelerateInterpolator;
import androidx.core.animation.Interpolator;
import androidx.core.animation.LinearInterpolator;
import androidx.core.animation.OvershootInterpolator;
import androidx.core.animation.PathInterpolator;

/* JADX INFO: loaded from: classes.dex */
public abstract class InterpolatorsAndroidX {
    public static final Interpolator ACCELERATE;
    public static final Interpolator ACCELERATED_EASE;
    public static final Interpolator ACCELERATE_0_5;
    public static final Interpolator ACCELERATE_0_75;
    public static final Interpolator ACCELERATE_1_5;
    public static final Interpolator ACCELERATE_2;
    public static final Interpolator ACCELERATE_DECELERATE;
    public static final Interpolator AGGRESSIVE_EASE;
    public static final Interpolator AGGRESSIVE_EASE_IN_OUT;
    public static final Interpolator ALPHA_IN;
    public static final Interpolator ALPHA_OUT;
    public static final Interpolator BACK_GESTURE;
    public static final Interpolator BOUNCE;
    public static final Interpolator CONTROL_STATE;
    public static final Interpolator CUSTOM_40_40;
    public static final Interpolator DECELERATE;
    public static final Interpolator DECELERATED_EASE;
    public static final Interpolator DECELERATE_1_5;
    public static final Interpolator DECELERATE_1_7;
    public static final Interpolator DECELERATE_2;
    public static final Interpolator DECELERATE_3;
    public static final Interpolator DECELERATE_QUINT;
    public static final Interpolator EXAGGERATED_EASE;
    public static final Interpolator FAST_OUT_LINEAR_IN;
    public static final Interpolator FAST_OUT_SLOW_IN;
    public static final Interpolator FAST_OUT_SLOW_IN_REVERSE;
    public static final Interpolator FINAL_FRAME;
    public static final Interpolator ICON_OVERSHOT;
    public static final Interpolator ICON_OVERSHOT_LESS;
    public static final Interpolator INSTANT;
    public static final Interpolator LEGACY;
    public static final Interpolator LEGACY_ACCELERATE;
    public static final Interpolator LEGACY_DECELERATE;
    public static final Interpolator LINEAR;
    public static final Interpolator LINEAR_OUT_SLOW_IN;
    public static final Interpolator OVERSHOOT_0_75;
    public static final Interpolator OVERSHOOT_1_2;
    public static final Interpolator OVERSHOOT_1_7;
    public static final Interpolator PANEL_CLOSE_ACCELERATED;
    public static final Interpolator SCROLL;
    public static final Interpolator SCROLL_CUBIC;
    public static final Interpolator SLOW_OUT_LINEAR_IN;
    public static final Interpolator STANDARD;
    public static final Interpolator STANDARD_ACCELERATE;
    public static final Interpolator STANDARD_DECELERATE;
    public static final Interpolator TOUCH_RESPONSE;
    public static final Interpolator TOUCH_RESPONSE_ACCEL_DEACCEL;
    public static final Interpolator TOUCH_RESPONSE_REVERSE;
    public static final Interpolator ZOOM_IN;
    public static final Interpolator ZOOM_OUT;
    public static final Interpolator EMPHASIZED = createEmphasizedInterpolator();
    public static final Interpolator EMPHASIZED_COMPLEMENT = createEmphasizedComplement();
    public static final Interpolator EMPHASIZED_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 0.8f, 0.15f);
    public static final Interpolator EMPHASIZED_DECELERATE = new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);

    public static /* synthetic */ float $r8$lambda$21pSMbmspDS3DGwNlQ2n6WMvwzQ(float f) {
        return f < 1.0f ? 0.0f : 1.0f;
    }

    public static /* synthetic */ float $r8$lambda$sDGbh1VMSuaBkmDhdfhjafmXTmw(float f) {
        return 1.0f;
    }

    static {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(0.05f, 0.0f, 0.133333f, 0.08f, 0.166666f, 0.4f);
        path.cubicTo(0.225f, 0.94f, 0.5f, 1.0f, 1.0f, 1.0f);
        EXAGGERATED_EASE = new PathInterpolator(path);
        INSTANT = new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX$$ExternalSyntheticLambda0
            @Override // androidx.core.animation.Interpolator
            public final float getInterpolation(float f) {
                return InterpolatorsAndroidX.$r8$lambda$sDGbh1VMSuaBkmDhdfhjafmXTmw(f);
            }
        };
        FINAL_FRAME = new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX$$ExternalSyntheticLambda1
            @Override // androidx.core.animation.Interpolator
            public final float getInterpolation(float f) {
                return InterpolatorsAndroidX.$r8$lambda$21pSMbmspDS3DGwNlQ2n6WMvwzQ(f);
            }
        };
        OVERSHOOT_0_75 = new OvershootInterpolator(0.75f);
        OVERSHOOT_1_2 = new OvershootInterpolator(1.2f);
        OVERSHOOT_1_7 = new OvershootInterpolator(1.7f);
        STANDARD = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
        STANDARD_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
        STANDARD_DECELERATE = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
        PathInterpolator pathInterpolator = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        LEGACY = pathInterpolator;
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        LEGACY_ACCELERATE = pathInterpolator2;
        PathInterpolator pathInterpolator3 = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        LEGACY_DECELERATE = pathInterpolator3;
        LINEAR = new LinearInterpolator();
        FAST_OUT_SLOW_IN = pathInterpolator;
        FAST_OUT_LINEAR_IN = pathInterpolator2;
        LINEAR_OUT_SLOW_IN = pathInterpolator3;
        FAST_OUT_SLOW_IN_REVERSE = new PathInterpolator(0.8f, 0.0f, 0.6f, 1.0f);
        SLOW_OUT_LINEAR_IN = new PathInterpolator(0.8f, 0.0f, 1.0f, 1.0f);
        AGGRESSIVE_EASE = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
        AGGRESSIVE_EASE_IN_OUT = new PathInterpolator(0.6f, 0.0f, 0.4f, 1.0f);
        DECELERATED_EASE = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        ACCELERATED_EASE = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        ALPHA_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        ALPHA_OUT = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
        ACCELERATE = new AccelerateInterpolator();
        ACCELERATE_0_5 = new AccelerateInterpolator(0.5f);
        ACCELERATE_0_75 = new AccelerateInterpolator(0.75f);
        ACCELERATE_1_5 = new AccelerateInterpolator(1.5f);
        ACCELERATE_2 = new AccelerateInterpolator(2.0f);
        ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        DECELERATE = new DecelerateInterpolator();
        DECELERATE_1_5 = new DecelerateInterpolator(1.5f);
        DECELERATE_1_7 = new DecelerateInterpolator(1.7f);
        DECELERATE_2 = new DecelerateInterpolator(2.0f);
        DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
        DECELERATE_3 = new DecelerateInterpolator(3.0f);
        CUSTOM_40_40 = new PathInterpolator(0.4f, 0.0f, 0.6f, 1.0f);
        ICON_OVERSHOT = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.4f);
        ICON_OVERSHOT_LESS = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.1f);
        PANEL_CLOSE_ACCELERATED = new PathInterpolator(0.3f, 0.0f, 0.5f, 1.0f);
        BOUNCE = new BounceInterpolator();
        CONTROL_STATE = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        TOUCH_RESPONSE = new PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
        TOUCH_RESPONSE_REVERSE = new PathInterpolator(0.9f, 0.0f, 0.7f, 1.0f);
        TOUCH_RESPONSE_ACCEL_DEACCEL = new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX$$ExternalSyntheticLambda2
            @Override // androidx.core.animation.Interpolator
            public final float getInterpolation(float f) {
                return InterpolatorsAndroidX.ACCELERATE_DECELERATE.getInterpolation(InterpolatorsAndroidX.TOUCH_RESPONSE.getInterpolation(f));
            }
        };
        ZOOM_IN = new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX.1
            @Override // androidx.core.animation.Interpolator
            public float getInterpolation(float f) {
                return InterpolatorsAndroidX.DECELERATE_3.getInterpolation(1.0f - InterpolatorsAndroidX.ZOOM_OUT.getInterpolation(1.0f - f));
            }
        };
        ZOOM_OUT = new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX.2
            private float zInterpolate(float f) {
                return (1.0f - (0.35f / (f + 0.35f))) / 0.7407408f;
            }

            @Override // androidx.core.animation.Interpolator
            public float getInterpolation(float f) {
                return zInterpolate(f);
            }
        };
        SCROLL = new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX.3
            @Override // androidx.core.animation.Interpolator
            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        };
        SCROLL_CUBIC = new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX.4
            @Override // androidx.core.animation.Interpolator
            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2) + 1.0f;
            }
        };
        BACK_GESTURE = new PathInterpolator(0.1f, 0.1f, 0.0f, 1.0f);
    }

    private static PathInterpolator createEmphasizedComplement() {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(0.1217f, 0.0462f, 0.15f, 0.4686f, 0.1667f, 0.66f);
        path.cubicTo(0.1834f, 0.8878f, 0.1667f, 1.0f, 1.0f, 1.0f);
        return new PathInterpolator(path);
    }

    private static PathInterpolator createEmphasizedInterpolator() {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(0.05f, 0.0f, 0.133333f, 0.06f, 0.166666f, 0.4f);
        path.cubicTo(0.208333f, 0.82f, 0.25f, 1.0f, 1.0f, 1.0f);
        return new PathInterpolator(path);
    }
}
