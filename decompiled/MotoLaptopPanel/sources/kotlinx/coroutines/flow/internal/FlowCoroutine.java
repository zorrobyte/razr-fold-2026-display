package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* JADX INFO: compiled from: FlowCoroutine.kt */
/* JADX INFO: loaded from: classes.dex */
final class FlowCoroutine extends ScopeCoroutine {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowCoroutine(CoroutineContext coroutineContext, Continuation continuation) {
        super(coroutineContext, continuation);
        coroutineContext.getClass();
        continuation.getClass();
    }

    @Override // kotlinx.coroutines.JobSupport
    public boolean childCancelled(Throwable th) {
        th.getClass();
        if (th instanceof ChildCancelledException) {
            return true;
        }
        return cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(th);
    }
}
