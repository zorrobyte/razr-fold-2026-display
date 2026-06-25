package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* JADX INFO: compiled from: DispatchedTask.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class DispatchException extends Exception {
    private final Throwable cause;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DispatchException(Throwable th, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) {
        super("Coroutine dispatcher " + coroutineDispatcher + " threw an exception, context = " + coroutineContext, th);
        th.getClass();
        coroutineDispatcher.getClass();
        coroutineContext.getClass();
        this.cause = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }
}
