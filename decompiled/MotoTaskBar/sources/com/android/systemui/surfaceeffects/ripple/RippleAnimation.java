package com.android.systemui.surfaceeffects.ripple;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.surfaceeffects.ripple.RippleShader;

/* JADX INFO: compiled from: RippleAnimation.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RippleAnimation {
    private final ValueAnimator animator;
    private final RippleAnimationConfig config;
    private final RippleShader rippleShader;

    public RippleAnimation(RippleAnimationConfig rippleAnimationConfig) {
        rippleAnimationConfig.getClass();
        this.config = rippleAnimationConfig;
        this.rippleShader = new RippleShader(rippleAnimationConfig.getRippleShape());
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.getClass();
        this.animator = valueAnimatorOfFloat;
        applyConfigToShader();
    }

    private final void applyConfigToShader() {
        RippleShader rippleShader = this.rippleShader;
        rippleShader.setCenter(this.config.getCenterX(), this.config.getCenterY());
        rippleShader.getRippleSize().setMaxSize(this.config.getMaxWidth(), this.config.getMaxHeight());
        rippleShader.setPixelDensity(this.config.getPixelDensity());
        rippleShader.setColor(ColorUtils.setAlphaComponent(this.config.getColor(), this.config.getOpacity()));
        rippleShader.setSparkleStrength(this.config.getSparkleStrength());
        assignFadeParams(rippleShader.getBaseRingFadeParams(), this.config.getBaseRingFadeParams());
        assignFadeParams(rippleShader.getSparkleRingFadeParams(), this.config.getSparkleRingFadeParams());
        assignFadeParams(rippleShader.getCenterFillFadeParams(), this.config.getCenterFillFadeParams());
    }

    private final void assignFadeParams(RippleShader.FadeParams fadeParams, RippleShader.FadeParams fadeParams2) {
        if (fadeParams2 != null) {
            fadeParams.setFadeInStart(fadeParams2.getFadeInStart());
            fadeParams.setFadeInEnd(fadeParams2.getFadeInEnd());
            fadeParams.setFadeOutStart(fadeParams2.getFadeOutStart());
            fadeParams.setFadeOutEnd(fadeParams2.getFadeOutEnd());
        }
    }

    public static /* synthetic */ void getRippleShader$annotations() {
    }

    public final RippleShader getRippleShader() {
        return this.rippleShader;
    }

    public final boolean isPlaying() {
        return this.animator.isRunning();
    }

    public final void play(final Runnable runnable) {
        if (isPlaying()) {
            return;
        }
        this.animator.setDuration(this.config.getDuration());
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.ripple.RippleAnimation.play.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                valueAnimator.getClass();
                long currentPlayTime = valueAnimator.getCurrentPlayTime();
                Object animatedValue = valueAnimator.getAnimatedValue();
                animatedValue.getClass();
                float fFloatValue = ((Float) animatedValue).floatValue();
                RippleAnimation.this.getRippleShader().setRawProgress(fFloatValue);
                RippleAnimation.this.getRippleShader().setDistortionStrength(RippleAnimation.this.config.getShouldDistort() ? 1 - fFloatValue : 0.0f);
                RippleAnimation.this.getRippleShader().setTime(currentPlayTime);
            }
        });
        this.animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.ripple.RippleAnimation.play.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.getClass();
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        this.animator.start();
    }

    public final void updateColor(int i) {
        this.config.setColor(i);
        applyConfigToShader();
    }
}
