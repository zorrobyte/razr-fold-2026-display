package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;

/* JADX INFO: compiled from: CompositeGeneratedAdaptersObserver.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CompositeGeneratedAdaptersObserver implements LifecycleEventObserver {
    private final GeneratedAdapter[] generatedAdapters;

    public CompositeGeneratedAdaptersObserver(GeneratedAdapter[] generatedAdapterArr) {
        generatedAdapterArr.getClass();
        this.generatedAdapters = generatedAdapterArr;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        lifecycleOwner.getClass();
        event.getClass();
        new MethodCallsLogger();
        GeneratedAdapter[] generatedAdapterArr = this.generatedAdapters;
        if (generatedAdapterArr.length > 0) {
            GeneratedAdapter generatedAdapter = generatedAdapterArr[0];
            throw null;
        }
        if (generatedAdapterArr.length <= 0) {
            return;
        }
        GeneratedAdapter generatedAdapter2 = generatedAdapterArr[0];
        throw null;
    }
}
