package com.motorola.laptoppanel.ui.compose;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.TweenSpec;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
final class AnimationSpecs {
    public static final AnimationSpecs INSTANCE = new AnimationSpecs();
    private static final TweenSpec IconAppearSpec = AnimationSpecKt.tween$default(100, 33, null, 4, null);
    private static final TweenSpec IconDisappearSpec = AnimationSpecKt.tween$default(50, 0, null, 6, null);

    private AnimationSpecs() {
    }

    public final TweenSpec getIconAppearSpec() {
        return IconAppearSpec;
    }

    public final TweenSpec getIconDisappearSpec() {
        return IconDisappearSpec;
    }
}
