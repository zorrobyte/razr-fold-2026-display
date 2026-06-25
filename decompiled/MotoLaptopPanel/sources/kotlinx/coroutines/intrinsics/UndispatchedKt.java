package kotlinx.coroutines.intrinsics;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.DispatchException;
import kotlinx.coroutines.JobSupportKt;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;

/* JADX INFO: compiled from: Undispatched.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class UndispatchedKt {
    public static final void startCoroutineUndispatched(Function2 function2, Object obj, Continuation continuation) {
        function2.getClass();
        continuation.getClass();
        Continuation continuationProbeCoroutineCreated = DebugProbesKt.probeCoroutineCreated(continuation);
        try {
            CoroutineContext context = continuationProbeCoroutineCreated.getContext();
            Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context, null);
            try {
                DebugProbesKt.probeCoroutineResumed(continuationProbeCoroutineCreated);
                Object objWrapWithContinuationImpl = !(function2 instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(function2, obj, continuationProbeCoroutineCreated) : ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(obj, continuationProbeCoroutineCreated);
                ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
                if (objWrapWithContinuationImpl != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    continuationProbeCoroutineCreated.resumeWith(Result.m2192constructorimpl(objWrapWithContinuationImpl));
                }
            } catch (Throwable th) {
                ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (th instanceof DispatchException) {
                th = ((DispatchException) th).getCause();
            }
            Result.Companion companion = Result.Companion;
            continuationProbeCoroutineCreated.resumeWith(Result.m2192constructorimpl(ResultKt.createFailure(th)));
        }
    }

    public static final Object startUndispatchedOrReturn(ScopeCoroutine scopeCoroutine, Object obj, Function2 function2) {
        Object completedExceptionally;
        Object objMakeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        scopeCoroutine.getClass();
        function2.getClass();
        try {
            completedExceptionally = !(function2 instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(function2, obj, scopeCoroutine) : ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(obj, scopeCoroutine);
        } catch (Throwable th) {
            completedExceptionally = new CompletedExceptionally(th, false, 2, null);
        }
        if (completedExceptionally != IntrinsicsKt.getCOROUTINE_SUSPENDED() && (objMakeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = scopeCoroutine.makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(completedExceptionally)) != JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            scopeCoroutine.afterCompletionUndispatched();
            if (objMakeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CompletedExceptionally) {
                throw ((CompletedExceptionally) objMakeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).cause;
            }
            return JobSupportKt.unboxState(objMakeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
        }
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }
}
