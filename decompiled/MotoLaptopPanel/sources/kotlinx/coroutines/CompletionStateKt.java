package kotlinx.coroutines;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: CompletionState.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CompletionStateKt {
    public static final Object recoverResult(Object obj, Continuation continuation) {
        continuation.getClass();
        if (!(obj instanceof CompletedExceptionally)) {
            return Result.m2192constructorimpl(obj);
        }
        Result.Companion companion = Result.Companion;
        return Result.m2192constructorimpl(ResultKt.createFailure(((CompletedExceptionally) obj).cause));
    }

    public static final Object toState(Object obj) {
        Throwable thM2193exceptionOrNullimpl = Result.m2193exceptionOrNullimpl(obj);
        return thM2193exceptionOrNullimpl == null ? obj : new CompletedExceptionally(thM2193exceptionOrNullimpl, false, 2, null);
    }

    public static final Object toState(Object obj, CancellableContinuation cancellableContinuation) {
        cancellableContinuation.getClass();
        Throwable thM2193exceptionOrNullimpl = Result.m2193exceptionOrNullimpl(obj);
        return thM2193exceptionOrNullimpl == null ? obj : new CompletedExceptionally(thM2193exceptionOrNullimpl, false, 2, null);
    }
}
