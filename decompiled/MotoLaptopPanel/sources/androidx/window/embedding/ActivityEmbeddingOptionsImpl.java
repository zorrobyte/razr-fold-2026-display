package androidx.window.embedding;

import android.os.Bundle;
import androidx.window.embedding.EmbeddingBounds;

/* JADX INFO: compiled from: ActivityEmbeddingOptionsImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ActivityEmbeddingOptionsImpl {
    public static final ActivityEmbeddingOptionsImpl INSTANCE = new ActivityEmbeddingOptionsImpl();

    private ActivityEmbeddingOptionsImpl() {
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final EmbeddingBounds.Dimension getDimension(Bundle bundle, String str) {
        Bundle bundle2 = bundle.getBundle(str);
        bundle2.getClass();
        String string = bundle2.getString("androidx.window.embedding.EmbeddingBounds.dimension_type");
        if (string != null) {
            switch (string.hashCode()) {
                case -1939100487:
                    if (string.equals("expanded")) {
                        return EmbeddingBounds.Dimension.DIMENSION_EXPANDED;
                    }
                    break;
                case 99283243:
                    if (string.equals("hinge")) {
                        return EmbeddingBounds.Dimension.DIMENSION_HINGE;
                    }
                    break;
                case 106680966:
                    if (string.equals("pixel")) {
                        return EmbeddingBounds.Dimension.Companion.pixel(bundle2.getInt("androidx.window.embedding.EmbeddingBounds.dimension_value"));
                    }
                    break;
                case 108285963:
                    if (string.equals("ratio")) {
                        return EmbeddingBounds.Dimension.Companion.ratio(bundle2.getFloat("androidx.window.embedding.EmbeddingBounds.dimension_value"));
                    }
                    break;
            }
        }
        throw new IllegalArgumentException("Illegal type " + string);
    }

    private final EmbeddingBounds getEmbeddingBounds(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("androidx.window.embedding.EmbeddingBounds");
        if (bundle2 == null) {
            return null;
        }
        return new EmbeddingBounds(new EmbeddingBounds.Alignment(bundle2.getInt("androidx.window.embedding.EmbeddingBounds.alignment")), getDimension(bundle2, "androidx.window.embedding.EmbeddingBounds.width"), getDimension(bundle2, "androidx.window.embedding.EmbeddingBounds.height"));
    }

    public final OverlayAttributes getOverlayAttributes$window_release(Bundle bundle) {
        bundle.getClass();
        EmbeddingBounds embeddingBounds = getEmbeddingBounds(bundle);
        if (embeddingBounds == null) {
            return null;
        }
        return new OverlayAttributes(embeddingBounds);
    }

    public final void putActivityStackAlignment$window_release(Bundle bundle, EmbeddingBounds embeddingBounds) {
        bundle.getClass();
        embeddingBounds.getClass();
        bundle.putInt("androidx.window.embedding.ActivityStackAlignment", embeddingBounds.getAlignment().getValue$window_release());
    }
}
