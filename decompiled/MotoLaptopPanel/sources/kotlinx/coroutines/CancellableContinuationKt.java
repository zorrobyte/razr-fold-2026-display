package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.DispatchedContinuation;

/* JADX INFO: compiled from: CancellableContinuation.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CancellableContinuationKt {
    public static final void disposeOnCancellation(CancellableContinuation cancellableContinuation, DisposableHandle disposableHandle) {
        cancellableContinuation.getClass();
        disposableHandle.getClass();
        invokeOnCancellation(cancellableContinuation, new DisposeOnCancel(disposableHandle));
    }

    public static final CancellableContinuationImpl getOrCreateCancellableContinuation(Continuation continuation) {
        continuation.getClass();
        if (!(continuation instanceof DispatchedContinuation)) {
            return new CancellableContinuationImpl(continuation, 1);
        }
        CancellableContinuationImpl cancellableContinuationImplClaimReusableCancellableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ((DispatchedContinuation) continuation).claimReusableCancellableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (cancellableContinuationImplClaimReusableCancellableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
            if (!cancellableContinuationImplClaimReusableCancellableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.resetStateReusable()) {
                cancellableContinuationImplClaimReusableCancellableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = null;
            }
            if (cancellableContinuationImplClaimReusableCancellableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
                return cancellableContinuationImplClaimReusableCancellableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
            }
        }
        return new CancellableContinuationImpl(continuation, 2);
    }

    public static final void invokeOnCancellation(CancellableContinuation cancellableContinuation, CancelHandler cancelHandler) {
        cancellableContinuation.getClass();
        cancelHandler.getClass();
        if (!(cancellableContinuation instanceof CancellableContinuationImpl)) {
            throw new UnsupportedOperationException("third-party implementation of CancellableContinuation is not supported");
        }
        ((CancellableContinuationImpl) cancellableContinuation).invokeOnCancellationInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(cancelHandler);
    }
}
