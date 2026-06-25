package androidx.window.embedding;

import android.app.Activity;
import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ActivityEmbeddingController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ActivityEmbeddingController {
    public static final Companion Companion = new Companion(null);
    private final EmbeddingBackend backend;

    /* JADX INFO: compiled from: ActivityEmbeddingController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ActivityEmbeddingController getInstance(Context context) {
            context.getClass();
            return new ActivityEmbeddingController(EmbeddingBackend.Companion.getInstance(context));
        }
    }

    public ActivityEmbeddingController(EmbeddingBackend embeddingBackend) {
        embeddingBackend.getClass();
        this.backend = embeddingBackend;
    }

    public static final ActivityEmbeddingController getInstance(Context context) {
        return Companion.getInstance(context);
    }

    public final boolean isActivityEmbedded(Activity activity) {
        activity.getClass();
        return this.backend.isActivityEmbedded(activity);
    }
}
