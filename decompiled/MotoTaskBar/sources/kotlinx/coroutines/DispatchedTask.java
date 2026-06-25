package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.scheduling.Task;

/* JADX INFO: compiled from: DispatchedTask.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class DispatchedTask extends Task {
    public int resumeMode;

    public DispatchedTask(int i) {
        this.resumeMode = i;
    }

    public void cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj, Throwable th) {
        th.getClass();
    }

    public abstract Continuation getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();

    public Throwable getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    public Object getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        return obj;
    }

    public final void handleFatalException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) {
        th.getClass();
        CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host().getContext(), new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", th));
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Continuation delegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            delegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.getClass();
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) delegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
            Continuation continuation = dispatchedContinuation.continuation;
            Object obj = dispatchedContinuation.countOrElement;
            CoroutineContext context = continuation.getContext();
            Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context, obj);
            Job job = null;
            UndispatchedCoroutine undispatchedCoroutineUpdateUndispatchedCompletion = objUpdateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? CoroutineContextKt.updateUndispatchedCompletion(continuation, context, objUpdateThreadContext) : null;
            try {
                CoroutineContext context2 = continuation.getContext();
                Object objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                Throwable exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
                if (exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == null && DispatchedTaskKt.isCancellableMode(this.resumeMode)) {
                    job = (Job) context2.get(Job.Key);
                }
                if (job != null && !job.isActive()) {
                    CancellationException cancellationException = job.getCancellationException();
                    cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, cancellationException);
                    Result.Companion companion = Result.Companion;
                    continuation.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(cancellationException)));
                } else if (exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
                    Result.Companion companion2 = Result.Companion;
                    continuation.resumeWith(Result.m2707constructorimpl(ResultKt.createFailure(exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host)));
                } else {
                    Result.Companion companion3 = Result.Companion;
                    continuation.resumeWith(Result.m2707constructorimpl(getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(objTakeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host)));
                }
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
        } catch (DispatchException e) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host().getContext(), e.getCause());
        } catch (Throwable th2) {
            handleFatalException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(th2);
        }
    }

    public abstract Object takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
}
