package androidx.window.embedding;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OverlayAttributes.kt */
/* JADX INFO: loaded from: classes.dex */
public final class OverlayAttributes {
    private final EmbeddingBounds bounds;

    public OverlayAttributes(EmbeddingBounds embeddingBounds) {
        embeddingBounds.getClass();
        this.bounds = embeddingBounds;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OverlayAttributes) {
            return Intrinsics.areEqual(this.bounds, ((OverlayAttributes) obj).bounds);
        }
        return false;
    }

    public final EmbeddingBounds getBounds() {
        return this.bounds;
    }

    public int hashCode() {
        return this.bounds.hashCode();
    }

    public String toString() {
        return OverlayAttributes.class.getSimpleName() + ": {bounds=" + this.bounds + '}';
    }
}
