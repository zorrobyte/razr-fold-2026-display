package com.android.systemui.surfaceeffects.turbulencenoise;

import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseShader;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TurbulenceNoiseController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TurbulenceNoiseController {
    public static final Companion Companion = new Companion(null);
    private Companion.AnimationState state;
    private final TurbulenceNoiseView turbulenceNoiseView;

    /* JADX INFO: compiled from: TurbulenceNoiseController.kt */
    public final class Companion {

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* JADX INFO: compiled from: TurbulenceNoiseController.kt */
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

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public TurbulenceNoiseController(TurbulenceNoiseView turbulenceNoiseView) {
        turbulenceNoiseView.getClass();
        this.turbulenceNoiseView = turbulenceNoiseView;
        this.state = Companion.AnimationState.NOT_PLAYING;
        turbulenceNoiseView.setVisibility(4);
    }

    public static /* synthetic */ void getState$annotations() {
    }

    private final void playEaseInAnimation() {
        if (this.state != Companion.AnimationState.NOT_PLAYING) {
            return;
        }
        setState(Companion.AnimationState.EASE_IN);
        this.turbulenceNoiseView.playEaseIn(new Runnable() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController.playEaseInAnimation.1
            @Override // java.lang.Runnable
            public final void run() {
                TurbulenceNoiseController.this.playMainAnimation();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void playEaseOutAnimation() {
        if (this.state != Companion.AnimationState.MAIN) {
            return;
        }
        setState(Companion.AnimationState.EASE_OUT);
        this.turbulenceNoiseView.playEaseOut(new Runnable() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController.playEaseOutAnimation.1
            @Override // java.lang.Runnable
            public final void run() {
                TurbulenceNoiseController.this.setState(Companion.AnimationState.NOT_PLAYING);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void playMainAnimation() {
        if (this.state != Companion.AnimationState.EASE_IN) {
            return;
        }
        setState(Companion.AnimationState.MAIN);
        this.turbulenceNoiseView.play(new Runnable() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController.playMainAnimation.1
            @Override // java.lang.Runnable
            public final void run() {
                TurbulenceNoiseController.this.playEaseOutAnimation();
            }
        });
    }

    public final void finish() {
        if (this.state == Companion.AnimationState.MAIN) {
            this.turbulenceNoiseView.finish(new Runnable() { // from class: com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController.finish.1
                @Override // java.lang.Runnable
                public final void run() {
                    TurbulenceNoiseController.this.playEaseOutAnimation();
                }
            });
        }
    }

    public final void play(TurbulenceNoiseShader.Companion.Type type, TurbulenceNoiseAnimationConfig turbulenceNoiseAnimationConfig) {
        type.getClass();
        turbulenceNoiseAnimationConfig.getClass();
        if (this.state != Companion.AnimationState.NOT_PLAYING) {
            return;
        }
        this.turbulenceNoiseView.initShader(type, turbulenceNoiseAnimationConfig);
        playEaseInAnimation();
    }

    public final void setState(Companion.AnimationState animationState) {
        animationState.getClass();
        this.state = animationState;
        if (animationState != Companion.AnimationState.NOT_PLAYING) {
            this.turbulenceNoiseView.setVisibility(0);
        } else {
            this.turbulenceNoiseView.setVisibility(4);
            this.turbulenceNoiseView.clearConfig$frameworks__base__packages__SystemUI__animation__android_common__SystemUIShaderLib();
        }
    }

    public final void updateNoiseColor(int i) {
        if (this.state == Companion.AnimationState.NOT_PLAYING) {
            return;
        }
        this.turbulenceNoiseView.updateColor$frameworks__base__packages__SystemUI__animation__android_common__SystemUIShaderLib(i);
    }
}
