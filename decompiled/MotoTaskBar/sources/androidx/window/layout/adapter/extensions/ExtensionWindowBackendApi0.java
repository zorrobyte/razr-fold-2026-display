package androidx.window.layout.adapter.extensions;

import android.content.Context;
import androidx.core.util.Consumer;
import androidx.window.layout.WindowLayoutInfo;
import androidx.window.layout.adapter.WindowBackend;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: ExtensionWindowBackendApi0.kt */
/* JADX INFO: loaded from: classes.dex */
public class ExtensionWindowBackendApi0 implements WindowBackend {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerLayoutChangeCallback$lambda$0(Consumer consumer) {
        consumer.accept(new WindowLayoutInfo(CollectionsKt.emptyList()));
    }

    @Override // androidx.window.layout.adapter.WindowBackend
    public void registerLayoutChangeCallback(Context context, Executor executor, final Consumer consumer) {
        context.getClass();
        executor.getClass();
        consumer.getClass();
        executor.execute(new Runnable() { // from class: androidx.window.layout.adapter.extensions.ExtensionWindowBackendApi0$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ExtensionWindowBackendApi0.registerLayoutChangeCallback$lambda$0(consumer);
            }
        });
    }

    @Override // androidx.window.layout.adapter.WindowBackend
    public void unregisterLayoutChangeCallback(Consumer consumer) {
        consumer.getClass();
    }
}
