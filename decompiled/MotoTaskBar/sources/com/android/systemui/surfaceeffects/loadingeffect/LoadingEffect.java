package com.android.systemui.surfaceeffects.loadingeffect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import com.android.systemui.surfaceeffects.PaintDrawCallback;
import com.android.systemui.surfaceeffects.RenderEffectDrawCallback;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseAnimationConfig;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LoadingEffect.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LoadingEffect {
    private static final Companion Companion = new Companion(null);
    private final AnimationStateChangedCallback animationStateChangedCallback;
    private final TurbulenceNoiseAnimationConfig config;
    private ValueAnimator currentAnimator;
    private final Paint paint;
    private final PaintDrawCallback paintCallback;
    private AnimationState state;
    private final TurbulenceNoiseShader turbulenceNoiseShader;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: LoadingEffect.kt */
    public final class AnimationState {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ AnimationState[] $VALUES;
        public static final AnimationState EASE_IN = new AnimationState("EASE_IN", 0);
        public static final AnimationState MAIN = new AnimationState("MAIN", 1);
        public static final AnimationState EASE_OUT = new AnimationState("EASE_OUT", 2);
        public static final AnimationState NOT_PLAYING = new AnimationState("NOT_PLAYING", 3);

        private static final /* synthetic */ AnimationState[] $values() {
            return new AnimationState[]{EASE_IN, MAIN, EASE_OUT, NOT_PLAYING};
        }

        static {
            AnimationState[] animationStateArr$values = $values();
            $VALUES = animationStateArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(animationStateArr$values);
        }

        private AnimationState(String str, int i) {
        }

        public static AnimationState valueOf(String str) {
            return (AnimationState) Enum.valueOf(AnimationState.class, str);
        }

        public static AnimationState[] values() {
            return (AnimationState[]) $VALUES.clone();
        }
    }

    /* JADX INFO: compiled from: LoadingEffect.kt */
    public interface AnimationStateChangedCallback {
        void onStateChanged(AnimationState animationState, AnimationState animationState2);
    }

    /* JADX INFO: compiled from: LoadingEffect.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private LoadingEffect(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig, PaintDrawCallback paintDrawCallback, RenderEffectDrawCallback renderEffectDrawCallback, AnimationStateChangedCallback animationStateChangedCallback) {
        Paint paint;
        this.config = turbulenceNoiseAnimationConfig;
        this.paintCallback = paintDrawCallback;
        this.animationStateChangedCallback = animationStateChangedCallback;
        TurbulenceNoiseShader turbulenceNoiseShader = new TurbulenceNoiseShader(type);
        turbulenceNoiseShader.applyConfig(turbulenceNoiseAnimationConfig);
        this.turbulenceNoiseShader = turbulenceNoiseShader;
        this.state = AnimationState.NOT_PLAYING;
        if (paintDrawCallback != null) {
            paint = new Paint();
            paint.setShader(turbulenceNoiseShader);
        } else {
            paint = null;
        }
        this.paint = paint;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LoadingEffect(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig, PaintDrawCallback paintDrawCallback, AnimationStateChangedCallback animationStateChangedCallback) {
        this(type, turbulenceNoiseAnimationConfig, paintDrawCallback, null, animationStateChangedCallback);
        type.getClass();
        turbulenceNoiseAnimationConfig.getClass();
        paintDrawCallback.getClass();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void draw() {
        PaintDrawCallback paintDrawCallback = this.paintCallback;
        if (paintDrawCallback != null) {
            Paint paint = this.paint;
            paint.getClass();
            paintDrawCallback.onDraw(paint);
        }
    }

    private final void playEaseIn() {
        if (this.state != AnimationState.NOT_PLAYING) {
            return;
        }
        setState(AnimationState.EASE_IN);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) this.config.getEaseInDuration());
        final float noiseOffsetX = this.turbulenceNoiseShader.getNoiseOffsetX();
        final float noiseOffsetY = this.turbulenceNoiseShader.getNoiseOffsetY();
        final float noiseOffsetZ = this.turbulenceNoiseShader.getNoiseOffsetZ();
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.playEaseIn.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                valueAnimator.getClass();
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                Object animatedValue = valueAnimator.getAnimatedValue();
                animatedValue.getClass();
                float fFloatValue = ((Float) animatedValue).floatValue();
                LoadingEffect.this.turbulenceNoiseShader.setNoiseMove(noiseOffsetX + (LoadingEffect.this.config.getNoiseMoveSpeedX() * currentPlayTime), noiseOffsetY + (LoadingEffect.this.config.getNoiseMoveSpeedY() * currentPlayTime), noiseOffsetZ + (currentPlayTime * LoadingEffect.this.config.getNoiseMoveSpeedZ()));
                LoadingEffect.this.turbulenceNoiseShader.setOpacity(fFloatValue * LoadingEffect.this.config.getLuminosityMultiplier());
                LoadingEffect.this.draw();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.playEaseIn.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.getClass();
                LoadingEffect.this.currentAnimator = null;
                LoadingEffect.this.playMain();
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void playEaseOut() {
        if (this.state != AnimationState.MAIN) {
            return;
        }
        setState(AnimationState.EASE_OUT);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) this.config.getEaseOutDuration());
        final float noiseOffsetX = this.turbulenceNoiseShader.getNoiseOffsetX();
        final float noiseOffsetY = this.turbulenceNoiseShader.getNoiseOffsetY();
        final float noiseOffsetZ = this.turbulenceNoiseShader.getNoiseOffsetZ();
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.playEaseOut.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                valueAnimator.getClass();
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                Object animatedValue = valueAnimator.getAnimatedValue();
                animatedValue.getClass();
                float fFloatValue = ((Float) animatedValue).floatValue();
                LoadingEffect.this.turbulenceNoiseShader.setNoiseMove(noiseOffsetX + (LoadingEffect.this.config.getNoiseMoveSpeedX() * currentPlayTime), noiseOffsetY + (LoadingEffect.this.config.getNoiseMoveSpeedY() * currentPlayTime), noiseOffsetZ + (currentPlayTime * LoadingEffect.this.config.getNoiseMoveSpeedZ()));
                LoadingEffect.this.turbulenceNoiseShader.setOpacity((1.0f - fFloatValue) * LoadingEffect.this.config.getLuminosityMultiplier());
                LoadingEffect.this.draw();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.playEaseOut.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.getClass();
                LoadingEffect.this.currentAnimator = null;
                LoadingEffect.this.setState(AnimationState.NOT_PLAYING);
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void playMain() {
        if (this.state != AnimationState.EASE_IN) {
            return;
        }
        setState(AnimationState.MAIN);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration((long) this.config.getMaxDuration());
        final float noiseOffsetX = this.turbulenceNoiseShader.getNoiseOffsetX();
        final float noiseOffsetY = this.turbulenceNoiseShader.getNoiseOffsetY();
        final float noiseOffsetZ = this.turbulenceNoiseShader.getNoiseOffsetZ();
        this.turbulenceNoiseShader.setOpacity(this.config.getLuminosityMultiplier());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.playMain.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                valueAnimator.getClass();
                float currentPlayTime = valueAnimator.getCurrentPlayTime() * 0.001f;
                LoadingEffect.this.turbulenceNoiseShader.setNoiseMove(noiseOffsetX + (LoadingEffect.this.config.getNoiseMoveSpeedX() * currentPlayTime), noiseOffsetY + (LoadingEffect.this.config.getNoiseMoveSpeedY() * currentPlayTime), noiseOffsetZ + (currentPlayTime * LoadingEffect.this.config.getNoiseMoveSpeedZ()));
                LoadingEffect.this.draw();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.surfaceeffects.loadingeffect.LoadingEffect.playMain.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.getClass();
                LoadingEffect.this.currentAnimator = null;
                LoadingEffect.this.playEaseOut();
            }
        });
        valueAnimatorOfFloat.start();
        this.currentAnimator = valueAnimatorOfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setState(AnimationState animationState) {
        AnimationState animationState2 = this.state;
        if (animationState2 != animationState) {
            AnimationStateChangedCallback animationStateChangedCallback = this.animationStateChangedCallback;
            if (animationStateChangedCallback != null) {
                animationStateChangedCallback.onStateChanged(animationState2, animationState);
            }
            this.state = animationState;
        }
    }

    public final void finish() {
        if (this.state == AnimationState.MAIN) {
            ValueAnimator valueAnimator = this.currentAnimator;
            if (valueAnimator != null) {
                valueAnimator.pause();
            }
            this.currentAnimator = null;
            playEaseOut();
        }
    }

    public final void play() {
        if (this.state != AnimationState.NOT_PLAYING) {
            return;
        }
        playEaseIn();
    }

    public final void updateColor(int i) {
        this.turbulenceNoiseShader.setColor(i);
    }
}
