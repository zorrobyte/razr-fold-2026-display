package androidx.compose.foundation.internal;

import java.util.concurrent.CancellationException;

/* JADX INFO: compiled from: PlatformOptimizedCancellationException.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PlatformOptimizedCancellationException extends CancellationException {
    public PlatformOptimizedCancellationException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public Throwable fillInStackTrace() {
        setStackTrace(PlatformOptimizedCancellationException_jvmKt.EmptyStackTraceElements);
        return this;
    }
}
