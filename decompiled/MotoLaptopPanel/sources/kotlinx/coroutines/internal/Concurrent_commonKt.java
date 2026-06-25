package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: Concurrent.common.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Concurrent_commonKt {
    public static final Object getValue(AtomicReference atomicReference) {
        atomicReference.getClass();
        return atomicReference.get();
    }

    public static final void setValue(AtomicReference atomicReference, Object obj) {
        atomicReference.getClass();
        atomicReference.set(obj);
    }
}
