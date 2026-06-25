package kotlinx.coroutines.internal;

/* JADX INFO: compiled from: ThreadLocal.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class ThreadLocalKt {
    public static final ThreadLocal commonThreadLocal(Symbol symbol) {
        symbol.getClass();
        return new ThreadLocal();
    }
}
