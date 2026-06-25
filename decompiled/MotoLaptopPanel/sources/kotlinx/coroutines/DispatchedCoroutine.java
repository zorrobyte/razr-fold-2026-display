package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* JADX INFO: compiled from: Builders.common.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DispatchedCoroutine extends ScopeCoroutine {
    private final AtomicInt _decision;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DispatchedCoroutine(CoroutineContext coroutineContext, Continuation continuation) {
        super(coroutineContext, continuation);
        coroutineContext.getClass();
        continuation.getClass();
        this._decision = AtomicFU.atomic(0);
    }

    private final boolean tryResume() {
        AtomicInt atomicInt = this._decision;
        do {
            int value = atomicInt.getValue();
            if (value != 0) {
                if (value == 1) {
                    return false;
                }
                throw new IllegalStateException("Already resumed");
            }
        } while (!this._decision.compareAndSet(0, 2));
        return true;
    }

    private final boolean trySuspend() {
        AtomicInt atomicInt = this._decision;
        do {
            int value = atomicInt.getValue();
            if (value != 0) {
                if (value == 2) {
                    return false;
                }
                throw new IllegalStateException("Already suspended");
            }
        } while (!this._decision.compareAndSet(0, 1));
        return true;
    }

    @Override // kotlinx.coroutines.internal.ScopeCoroutine, kotlinx.coroutines.JobSupport
    protected void afterCompletion(Object obj) {
        afterResume(obj);
    }

    @Override // kotlinx.coroutines.internal.ScopeCoroutine, kotlinx.coroutines.AbstractCoroutine
    protected void afterResume(Object obj) {
        if (tryResume()) {
            return;
        }
        DispatchedContinuationKt.resumeCancellableWith(IntrinsicsKt.intercepted(this.uCont), CompletionStateKt.recoverResult(obj, this.uCont));
    }

    public final Object getResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        if (trySuspend()) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        Object objUnboxState = JobSupportKt.unboxState(getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host());
        if (objUnboxState instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) objUnboxState).cause;
        }
        return objUnboxState;
    }
}
