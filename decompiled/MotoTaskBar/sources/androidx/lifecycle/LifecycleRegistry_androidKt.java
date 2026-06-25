package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;

/* JADX INFO: compiled from: LifecycleRegistry.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LifecycleRegistry_androidKt {
    public static final boolean isMainThread() {
        return ArchTaskExecutor.getInstance().isMainThread();
    }
}
