package kotlinx.coroutines;

/* JADX INFO: compiled from: Exceptions.common.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CompletionHandlerException extends RuntimeException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompletionHandlerException(String str, Throwable th) {
        super(str, th);
        str.getClass();
        th.getClass();
    }
}
