package androidx.compose.runtime;

/* JADX INFO: compiled from: ComposeNodeLifecycleCallback.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ComposeNodeLifecycleCallback {
    void onDeactivate();

    void onRelease();

    void onReuse();
}
