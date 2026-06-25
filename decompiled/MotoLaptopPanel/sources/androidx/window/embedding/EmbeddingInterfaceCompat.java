package androidx.window.embedding;

import android.app.Activity;
import java.util.List;

/* JADX INFO: compiled from: EmbeddingInterfaceCompat.kt */
/* JADX INFO: loaded from: classes.dex */
public interface EmbeddingInterfaceCompat {

    /* JADX INFO: compiled from: EmbeddingInterfaceCompat.kt */
    public interface EmbeddingCallbackInterface {
        void onActivityStackChanged(List list);

        void onSplitInfoChanged(List list);
    }

    boolean isActivityEmbedded(Activity activity);

    void setEmbeddingCallback(EmbeddingCallbackInterface embeddingCallbackInterface);
}
