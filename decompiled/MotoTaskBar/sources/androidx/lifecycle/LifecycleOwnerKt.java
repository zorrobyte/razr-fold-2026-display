package androidx.lifecycle;

/* JADX INFO: compiled from: LifecycleOwner.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LifecycleOwnerKt {
    public static final LifecycleCoroutineScope getLifecycleScope(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getClass();
        return LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle());
    }
}
