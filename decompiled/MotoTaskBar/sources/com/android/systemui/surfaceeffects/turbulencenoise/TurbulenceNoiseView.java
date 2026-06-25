package com.android.systemui.surfaceeffects.turbulencenoise;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TurbulenceNoiseView.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TurbulenceNoiseView extends View {
    public static final Companion Companion = new Companion(null);
    private ValueAnimator currentAnimator;
    private TurbulenceNoiseAnimationConfig noiseConfig;
    private final Paint paint;
    private TurbulenceNoiseShader turbulenceNoiseShader;

    /* JADX INFO: compiled from: TurbulenceNoiseView.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public TurbulenceNoiseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();
    }

    public static /* synthetic */ void getCurrentAnimator$annotations() {
    }

    public static /* synthetic */ void getNoiseConfig$annotations() {
    }

    public static /* synthetic */ void getTurbulenceNoiseShader$annotations() {
    }

    public final void clearConfig$frameworks__base__packages__SystemUI__animation__android_common__SystemUIShaderLib() {
        this.noiseConfig = null;
    }

    public final void finish(Runnable runnable) {
        ValueAnimator valueAnimator = this.currentAnimator;
        if (valueAnimator != null) {
            valueAnimator.pause();
        }
        this.currentAnimator = null;
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void initShader(com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader.Companion.Type r2, com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig r3) {
        /*
            r1 = this;
            r2.getClass()
            r3.getClass()
            r1.noiseConfig = r3
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader r0 = r1.turbulenceNoiseShader
            if (r0 == 0) goto L16
            if (r0 == 0) goto L13
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader$Companion$Type r0 = r0.getBaseType()
            goto L14
        L13:
            r0 = 0
        L14:
            if (r0 == r2) goto L22
        L16:
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader r0 = new com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader
            r0.<init>(r2)
            r1.turbulenceNoiseShader = r0
            android.graphics.Paint r2 = r1.paint
            r2.setShader(r0)
        L22:
            com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader r1 = r1.turbulenceNoiseShader
            r1.getClass()
            r1.applyConfig(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.initShader(com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader$Companion$Type, com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig):void");
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.getClass();
        if (canvas.isHardwareAccelerated()) {
            canvas.drawPaint(this.paint);
        }
    }

    public final void play(final Runnable runnable) {
        final TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.noiseConfig;
        if (turbulenceNoiseAnimationConfig == null) {
            return;
        }
        turbulenceNoiseAnimationConfig.getClass();
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        turbulenceNoiseShader.getClass();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) turbulenceNoiseAnimationConfig.getMaxDuration());
        final float noiseOffsetX = turbulenceNoiseShader.getNoiseOffsetX();
        final float noiseOffsetY = turbulenceNoiseShader.getNoiseOffsetY();
        final float noiseOffsetZ = turbulenceNoiseShader.getNoiseOffsetZ();
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.play.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                valueAnimator.getClass();
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                turbulenceNoiseShader.setNoiseMove(noiseOffsetX + (turbulenceNoiseAnimationConfig.getNoiseMoveSpeedX() * currentPlayTime), noiseOffsetY + (turbulenceNoiseAnimationConfig.getNoiseMoveSpeedY() * currentPlayTime), noiseOffsetZ + (currentPlayTime * turbulenceNoiseAnimationConfig.getNoiseMoveSpeedZ()));
                turbulenceNoiseShader.setOpacity(turbulenceNoiseAnimationConfig.getLuminosityMultiplier());
                this.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.play.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.getClass();
                TurbulenceNoiseView.this.setCurrentAnimator(null);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    public final void playEaseIn(final Runnable runnable) {
        final TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.noiseConfig;
        if (turbulenceNoiseAnimationConfig == null) {
            return;
        }
        turbulenceNoiseAnimationConfig.getClass();
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        turbulenceNoiseShader.getClass();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) turbulenceNoiseAnimationConfig.getEaseInDuration());
        final float noiseOffsetX = turbulenceNoiseShader.getNoiseOffsetX();
        final float noiseOffsetY = turbulenceNoiseShader.getNoiseOffsetY();
        final float noiseOffsetZ = turbulenceNoiseShader.getNoiseOffsetZ();
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.playEaseIn.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                valueAnimator.getClass();
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                Object animatedValue = valueAnimator.getAnimatedValue();
                animatedValue.getClass();
                float fFloatValue = ((Float) animatedValue).floatValue();
                turbulenceNoiseShader.setNoiseMove(noiseOffsetX + (turbulenceNoiseAnimationConfig.getNoiseMoveSpeedX() * currentPlayTime), noiseOffsetY + (turbulenceNoiseAnimationConfig.getNoiseMoveSpeedY() * currentPlayTime), noiseOffsetZ + (currentPlayTime * turbulenceNoiseAnimationConfig.getNoiseMoveSpeedZ()));
                turbulenceNoiseShader.setOpacity(fFloatValue * turbulenceNoiseAnimationConfig.getLuminosityMultiplier());
                this.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.playEaseIn.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.getClass();
                TurbulenceNoiseView.this.setCurrentAnimator(null);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    public final void playEaseOut(final Runnable runnable) {
        final TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig = this.noiseConfig;
        if (turbulenceNoiseAnimationConfig == null) {
            return;
        }
        turbulenceNoiseAnimationConfig.getClass();
        final TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        turbulenceNoiseShader.getClass();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) turbulenceNoiseAnimationConfig.getEaseOutDuration());
        final float noiseOffsetX = turbulenceNoiseShader.getNoiseOffsetX();
        final float noiseOffsetY = turbulenceNoiseShader.getNoiseOffsetY();
        final float noiseOffsetZ = turbulenceNoiseShader.getNoiseOffsetZ();
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.playEaseOut.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                valueAnimator.getClass();
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                Object animatedValue = valueAnimator.getAnimatedValue();
                animatedValue.getClass();
                float fFloatValue = ((Float) animatedValue).floatValue();
                turbulenceNoiseShader.setNoiseMove(noiseOffsetX + (turbulenceNoiseAnimationConfig.getNoiseMoveSpeedX() * currentPlayTime), noiseOffsetY + (turbulenceNoiseAnimationConfig.getNoiseMoveSpeedY() * currentPlayTime), noiseOffsetZ + (currentPlayTime * turbulenceNoiseAnimationConfig.getNoiseMoveSpeedZ()));
                turbulenceNoiseShader.setOpacity((1.0f - fFloatValue) * turbulenceNoiseAnimationConfig.getLuminosityMultiplier());
                this.invalidate();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView.playEaseOut.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.getClass();
                TurbulenceNoiseView.this.setCurrentAnimator(null);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    public final void setBlendMode(BlendMode blendMode) {
        blendMode.getClass();
        this.paint.setBlendMode(blendMode);
    }

    public final void setCurrentAnimator(ValueAnimator valueAnimator) {
        this.currentAnimator = valueAnimator;
    }

    public final void updateColor$frameworks__base__packages__SystemUI__animation__android_common__SystemUIShaderLib(int i) {
        TurbulenceNoiseShader turbulenceNoiseShader = this.turbulenceNoiseShader;
        if (turbulenceNoiseShader != null) {
            turbulenceNoiseShader.setColor(i);
        }
    }
}
