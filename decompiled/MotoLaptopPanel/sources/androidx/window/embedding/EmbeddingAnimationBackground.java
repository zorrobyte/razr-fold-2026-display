package androidx.window.embedding;

import android.graphics.Color;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: EmbeddingAnimationBackground.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class EmbeddingAnimationBackground {
    public static final Companion Companion = new Companion(null);
    public static final EmbeddingAnimationBackground DEFAULT = new DefaultBackground();

    /* JADX INFO: compiled from: EmbeddingAnimationBackground.kt */
    public final class ColorBackground extends EmbeddingAnimationBackground {
        private final int color;

        public ColorBackground(int i) {
            super(null);
            this.color = i;
            if (Color.alpha(i) != 255) {
                throw new IllegalArgumentException("Background color must be opaque");
            }
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof ColorBackground) && this.color == ((ColorBackground) obj).color;
        }

        public int hashCode() {
            return Integer.hashCode(this.color);
        }

        public String toString() {
            return "ColorBackground{color:" + Integer.toHexString(this.color) + '}';
        }
    }

    /* JADX INFO: compiled from: EmbeddingAnimationBackground.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ColorBackground createColorBackground(int i) {
            return new ColorBackground(i);
        }
    }

    /* JADX INFO: compiled from: EmbeddingAnimationBackground.kt */
    final class DefaultBackground extends EmbeddingAnimationBackground {
        public DefaultBackground() {
            super(null);
        }

        public int hashCode() {
            return toString().hashCode();
        }

        public String toString() {
            return "DefaultBackground";
        }
    }

    private EmbeddingAnimationBackground() {
    }

    public /* synthetic */ EmbeddingAnimationBackground(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
