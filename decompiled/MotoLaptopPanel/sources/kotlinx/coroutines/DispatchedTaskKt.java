package kotlinx.coroutines;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.ThreadContextKt;

/* JADX INFO: compiled from: DispatchedTask.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DispatchedTaskKt {
    public static final void dispatch(DispatchedTask dispatchedTask, int i) {
        dispatchedTask.getClass();
        Continuation delegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = dispatchedTask.getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        boolean z = i == 4;
        if (z || !(delegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof DispatchedContinuation) || isCancellableMode(i) != isCancellableMode(dispatchedTask.resumeMode)) {
            resume(dispatchedTask, delegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, z);
            return;
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) delegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        CoroutineDispatcher coroutineDispatcher = dispatchedContinuation.dispatcher;
        CoroutineContext context = dispatchedContinuation.getContext();
        if (DispatchedContinuationKt.safeIsDispatchNeeded(coroutineDispatcher, context)) {
            DispatchedContinuationKt.safeDispatch(coroutineDispatcher, context, dispatchedTask);
        } else {
            resumeUnconfined(dispatchedTask);
        }
    }

    public static final boolean isCancellableMode(int i) {
        return i == 1 || i == 2;
    }

    public static final boolean isReusableMode(int i) {
        return i == 2;
    }

    public static final void resume(DispatchedTask dispatchedTask, Continuation continuation, boolean z) {
        Object successfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        dispatchedTask.getClass();
        continuation.getClass();
        Object objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = dispatchedTask.takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        Throwable exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = dispatchedTask.getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
        if (exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
            Result.Companion companion = Result.Companion;
            successfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ResultKt.createFailure(exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
        } else {
            Result.Companion companion2 = Result.Companion;
            successfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = dispatchedTask.getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
        }
        Object objM2192constructorimpl = Result.m2192constructorimpl(successfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
        if (!z) {
            continuation.resumeWith(objM2192constructorimpl);
            return;
        }
        DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) continuation;
        Continuation continuation2 = dispatchedContinuation.continuation;
        Object obj = dispatchedContinuation.countOrElement;
        CoroutineContext context = continuation2.getContext();
        Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context, obj);
        UndispatchedCoroutine undispatchedCoroutineUpdateUndispatchedCompletion = objUpdateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation2, context, objUpdateThreadContext) : null;
        try {
            dispatchedContinuation.continuation.resumeWith(objM2192constructorimpl);
            Unit unit = Unit.INSTANCE;
            if (undispatchedCoroutineUpdateUndispatchedCompletion == null || undispatchedCoroutineUpdateUndispatchedCompletion.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
            }
        } catch (Throwable th) {
            if (undispatchedCoroutineUpdateUndispatchedCompletion == null || undispatchedCoroutineUpdateUndispatchedCompletion.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
            }
            throw th;
        }
    }

    private static final void resumeUnconfined(DispatchedTask dispatchedTask) {
        EventLoop eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ThreadLocalEventLoop.INSTANCE.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.isUnconfinedLoopActive()) {
            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.dispatchUnconfined(dispatchedTask);
            return;
        }
        eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.incrementUseCount(true);
        try {
            resume(dispatchedTask, dispatchedTask.getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(), true);
            do {
            } while (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.processUnconfinedEvent());
        } finally {
            try {
            } finally {
            }
        }
    }
}
