package androidx.window.embedding;

import android.app.Activity;
import android.content.Context;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: EmbeddingBackend.kt */
/* JADX INFO: loaded from: classes.dex */
public interface EmbeddingBackend {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: EmbeddingBackend.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static Function1 decorator = new Function1() { // from class: androidx.window.embedding.EmbeddingBackend$Companion$decorator$1
            @Override // kotlin.jvm.functions.Function1
            public final EmbeddingBackend invoke(EmbeddingBackend embeddingBackend) {
                embeddingBackend.getClass();
                return embeddingBackend;
            }
        };

        private Companion() {
        }

        public final EmbeddingBackend getInstance(Context context) {
            context.getClass();
            return (EmbeddingBackend) decorator.invoke(ExtensionEmbeddingBackend.Companion.getInstance(context));
        }
    }

    boolean isActivityEmbedded(Activity activity);
}
