package androidx.compose.runtime.internal;

/* JADX INFO: compiled from: Thread.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Thread_jvmKt {
    public static final long currentThreadId() {
        return Thread.currentThread().getId();
    }

    public static final String currentThreadName() {
        return Thread.currentThread().getName();
    }
}
