package androidx.window.embedding;

import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import android.util.ArrayMap;
import androidx.window.WindowSdkExtensions;
import androidx.window.extensions.core.util.function.Consumer;
import androidx.window.extensions.embedding.ActivityEmbeddingComponent;
import androidx.window.extensions.embedding.EmbeddedActivityWindowInfo;
import androidx.window.reflection.Consumer2;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;

/* JADX INFO: compiled from: ActivityWindowInfoCallbackController.kt */
/* JADX INFO: loaded from: classes.dex */
public class ActivityWindowInfoCallbackController {
    private Map callbacks;
    private final ActivityEmbeddingComponent embeddingExtension;
    private final Consumer extensionsCallback;
    private final ReentrantLock globalLock;

    public ActivityWindowInfoCallbackController(ActivityEmbeddingComponent activityEmbeddingComponent) {
        activityEmbeddingComponent.getClass();
        this.embeddingExtension = activityEmbeddingComponent;
        this.globalLock = new ReentrantLock();
        this.callbacks = new ArrayMap();
        WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(6);
        this.extensionsCallback = new Consumer2() { // from class: androidx.window.embedding.ActivityWindowInfoCallbackController$$ExternalSyntheticLambda0
            @Override // androidx.window.reflection.Consumer2
            public final void accept(Object obj) {
                ActivityWindowInfoCallbackController._init_$lambda$1(this.f$0, (EmbeddedActivityWindowInfo) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(ActivityWindowInfoCallbackController activityWindowInfoCallbackController, EmbeddedActivityWindowInfo embeddedActivityWindowInfo) {
        embeddedActivityWindowInfo.getClass();
        ReentrantLock reentrantLock = activityWindowInfoCallbackController.globalLock;
        reentrantLock.lock();
        try {
            Iterator it = activityWindowInfoCallbackController.callbacks.values().iterator();
            if (it.hasNext()) {
                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }
}
