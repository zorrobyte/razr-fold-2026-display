package androidx.window.embedding;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EmbeddingAnimationParams.kt */
/* JADX INFO: loaded from: classes.dex */
public final class EmbeddingAnimationParams {
    private final EmbeddingAnimationBackground animationBackground;
    private final AnimationSpec changeAnimation;
    private final AnimationSpec closeAnimation;
    private final AnimationSpec openAnimation;

    /* JADX INFO: compiled from: EmbeddingAnimationParams.kt */
    public final class AnimationSpec {
        public static final Companion Companion = new Companion(null);
        public static final AnimationSpec DEFAULT = new AnimationSpec(0);
        public static final AnimationSpec JUMP_CUT = new AnimationSpec(1);
        private final int value;

        /* JADX INFO: compiled from: EmbeddingAnimationParams.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        private AnimationSpec(int i) {
            this.value = i;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof AnimationSpec) && this.value == ((AnimationSpec) obj).value;
        }

        public int hashCode() {
            return this.value * 31;
        }

        public String toString() {
            int i = this.value;
            if (i == 0) {
                return "DEFAULT";
            }
            if (i == 1) {
                return "JUMP_CUT";
            }
            return "Unknown value: " + this.value;
        }
    }

    /* JADX INFO: compiled from: EmbeddingAnimationParams.kt */
    public final class Builder {
        private EmbeddingAnimationBackground animationBackground = EmbeddingAnimationBackground.DEFAULT;
        private AnimationSpec changeAnimation;
        private AnimationSpec closeAnimation;
        private AnimationSpec openAnimation;

        public Builder() {
            AnimationSpec animationSpec = AnimationSpec.DEFAULT;
            this.openAnimation = animationSpec;
            this.closeAnimation = animationSpec;
            this.changeAnimation = animationSpec;
        }

        public final EmbeddingAnimationParams build() {
            return new EmbeddingAnimationParams(this.animationBackground, this.openAnimation, this.closeAnimation, this.changeAnimation, null);
        }

        public final Builder setAnimationBackground(EmbeddingAnimationBackground embeddingAnimationBackground) {
            embeddingAnimationBackground.getClass();
            this.animationBackground = embeddingAnimationBackground;
            return this;
        }

        public final Builder setChangeAnimation(AnimationSpec animationSpec) {
            animationSpec.getClass();
            this.changeAnimation = animationSpec;
            return this;
        }

        public final Builder setCloseAnimation(AnimationSpec animationSpec) {
            animationSpec.getClass();
            this.closeAnimation = animationSpec;
            return this;
        }

        public final Builder setOpenAnimation(AnimationSpec animationSpec) {
            animationSpec.getClass();
            this.openAnimation = animationSpec;
            return this;
        }
    }

    private EmbeddingAnimationParams(EmbeddingAnimationBackground embeddingAnimationBackground, AnimationSpec animationSpec, AnimationSpec animationSpec2, AnimationSpec animationSpec3) {
        this.animationBackground = embeddingAnimationBackground;
        this.openAnimation = animationSpec;
        this.closeAnimation = animationSpec2;
        this.changeAnimation = animationSpec3;
    }

    public /* synthetic */ EmbeddingAnimationParams(EmbeddingAnimationBackground embeddingAnimationBackground, AnimationSpec animationSpec, AnimationSpec animationSpec2, AnimationSpec animationSpec3, DefaultConstructorMarker defaultConstructorMarker) {
        this(embeddingAnimationBackground, animationSpec, animationSpec2, animationSpec3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EmbeddingAnimationParams)) {
            return false;
        }
        EmbeddingAnimationParams embeddingAnimationParams = (EmbeddingAnimationParams) obj;
        return Intrinsics.areEqual(this.animationBackground, embeddingAnimationParams.animationBackground) && Intrinsics.areEqual(this.openAnimation, embeddingAnimationParams.openAnimation) && Intrinsics.areEqual(this.closeAnimation, embeddingAnimationParams.closeAnimation) && Intrinsics.areEqual(this.changeAnimation, embeddingAnimationParams.changeAnimation);
    }

    public int hashCode() {
        return (((((this.animationBackground.hashCode() * 31) + this.openAnimation.hashCode()) * 31) + this.closeAnimation.hashCode()) * 31) + this.changeAnimation.hashCode();
    }

    public String toString() {
        return EmbeddingAnimationParams.class.getSimpleName() + ":{animationBackground=" + this.animationBackground + ", openAnimation=" + this.openAnimation + ", closeAnimation=" + this.closeAnimation + ", changeAnimation=" + this.changeAnimation + " }";
    }
}
