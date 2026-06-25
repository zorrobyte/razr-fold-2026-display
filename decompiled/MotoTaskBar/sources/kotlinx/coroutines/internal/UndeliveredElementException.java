package kotlinx.coroutines.internal;

/* JADX INFO: compiled from: OnUndeliveredElement.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class UndeliveredElementException extends RuntimeException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UndeliveredElementException(String str, Throwable th) {
        super(str, th);
        str.getClass();
        th.getClass();
    }
}
