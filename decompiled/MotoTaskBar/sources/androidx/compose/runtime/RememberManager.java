package androidx.compose.runtime;

/* JADX INFO: compiled from: Composer.kt */
/* JADX INFO: loaded from: classes.dex */
public interface RememberManager {
    void endResumingScope(RecomposeScopeImpl recomposeScopeImpl);

    void forgetting(RememberObserverHolder rememberObserverHolder, int i, int i2, int i3);

    void releasing(ComposeNodeLifecycleCallback composeNodeLifecycleCallback, int i, int i2, int i3);

    void remembering(RememberObserverHolder rememberObserverHolder);

    void startResumingScope(RecomposeScopeImpl recomposeScopeImpl);
}
