package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.KotlinNothingValueException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancelHandler;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.Symbol;

/* JADX INFO: compiled from: CancellableContinuationImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
public class CancellableContinuationImpl extends DispatchedTask implements CancellableContinuation, CoroutineStackFrame, Waiter {
    private final AtomicInt _decisionAndIndex;
    private final AtomicRef _parentHandle;
    private final AtomicRef _state;
    private final CoroutineContext context;
    private final Continuation delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CancellableContinuationImpl(Continuation continuation, int i) {
        super(i);
        continuation.getClass();
        this.delegate = continuation;
        this.context = continuation.getContext();
        this._decisionAndIndex = AtomicFU.atomic(536870911);
        this._state = AtomicFU.atomic(Active.INSTANCE);
        this._parentHandle = AtomicFU.atomic((Object) null);
    }

    private final Void alreadyResumedError(Object obj) {
        throw new IllegalStateException(("Already resumed, but proposed with update " + obj).toString());
    }

    private final void callSegmentOnCancellation(Segment segment, Throwable th) {
        int value = this._decisionAndIndex.getValue() & 536870911;
        if (value == 536870911) {
            throw new IllegalStateException("The index for Segment.onCancellation(..) is broken");
        }
        try {
            segment.onCancellation(value, th, getContext());
        } catch (Throwable th2) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th2));
        }
    }

    private final boolean cancelLater(Throwable th) {
        if (!isReusable()) {
            return false;
        }
        Continuation continuation = this.delegate;
        continuation.getClass();
        return ((DispatchedContinuation) continuation).postponeCancellation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(th);
    }

    private final void detachChildIfNonReusable() {
        if (isReusable()) {
            return;
        }
        detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
    }

    private final void dispatchResume(int i) {
        if (tryResume()) {
            return;
        }
        DispatchedTaskKt.dispatch(this, i);
    }

    private final DisposableHandle getParentHandle() {
        return (DisposableHandle) this._parentHandle.getValue();
    }

    private final String getStateDebugRepresentation() {
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        return state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof NotCompleted ? "Active" : state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CancelledContinuation ? "Cancelled" : "Completed";
    }

    private final DisposableHandle installParentHandle() {
        Job job = (Job) getContext().get(Job.Key);
        if (job == null) {
            return null;
        }
        DisposableHandle disposableHandleInvokeOnCompletion$default = JobKt__JobKt.invokeOnCompletion$default(job, false, new ChildContinuation(this), 1, null);
        this._parentHandle.compareAndSet(null, disposableHandleInvokeOnCompletion$default);
        return disposableHandleInvokeOnCompletion$default;
    }

    private final void invokeOnCancellationImpl(Object obj) {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object value = atomicRef.getValue();
            if (value instanceof Active) {
                if (this._state.compareAndSet(value, obj)) {
                    return;
                }
            } else if ((value instanceof CancelHandler) || (value instanceof Segment)) {
                multipleHandlersError(obj, value);
            } else {
                if (value instanceof CompletedExceptionally) {
                    CompletedExceptionally completedExceptionally = (CompletedExceptionally) value;
                    if (!completedExceptionally.makeHandled()) {
                        multipleHandlersError(obj, value);
                    }
                    if (value instanceof CancelledContinuation) {
                        if (!(value instanceof CompletedExceptionally)) {
                            completedExceptionally = null;
                        }
                        Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
                        if (obj instanceof CancelHandler) {
                            callCancelHandler((CancelHandler) obj, th);
                            return;
                        } else {
                            obj.getClass();
                            callSegmentOnCancellation((Segment) obj, th);
                            return;
                        }
                    }
                    return;
                }
                if (value instanceof CompletedContinuation) {
                    CompletedContinuation completedContinuation = (CompletedContinuation) value;
                    if (completedContinuation.cancelHandler != null) {
                        multipleHandlersError(obj, value);
                    }
                    if (obj instanceof Segment) {
                        return;
                    }
                    obj.getClass();
                    CancelHandler cancelHandler = (CancelHandler) obj;
                    if (completedContinuation.getCancelled()) {
                        callCancelHandler(cancelHandler, completedContinuation.cancelCause);
                        return;
                    } else {
                        if (this._state.compareAndSet(value, CompletedContinuation.copy$default(completedContinuation, null, cancelHandler, null, null, null, 29, null))) {
                            return;
                        }
                    }
                } else {
                    if (obj instanceof Segment) {
                        return;
                    }
                    obj.getClass();
                    if (this._state.compareAndSet(value, new CompletedContinuation(value, (CancelHandler) obj, null, null, null, 28, null))) {
                        return;
                    }
                }
            }
        }
    }

    private final boolean isReusable() {
        if (!DispatchedTaskKt.isReusableMode(this.resumeMode)) {
            return false;
        }
        Continuation continuation = this.delegate;
        continuation.getClass();
        return ((DispatchedContinuation) continuation).isReusable$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
    }

    private final void multipleHandlersError(Object obj, Object obj2) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + obj + ", already has " + obj2).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit resume$lambda$13$lambda$12(Function1 function1, Throwable th, Object obj, CoroutineContext coroutineContext) {
        th.getClass();
        coroutineContext.getClass();
        function1.invoke(th);
        return Unit.INSTANCE;
    }

    public static /* synthetic */ void resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host$default(CancellableContinuationImpl cancellableContinuationImpl, Object obj, int i, Function3 function3, int i2, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resumeImpl");
        }
        if ((i2 & 4) != 0) {
            function3 = null;
        }
        cancellableContinuationImpl.resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj, i, function3);
    }

    private final Object resumedState(NotCompleted notCompleted, Object obj, int i, Function3 function3, Object obj2) {
        if (obj instanceof CompletedExceptionally) {
            return obj;
        }
        if ((DispatchedTaskKt.isCancellableMode(i) || obj2 != null) && !(function3 == null && !(notCompleted instanceof CancelHandler) && obj2 == null)) {
            return new CompletedContinuation(obj, notCompleted instanceof CancelHandler ? (CancelHandler) notCompleted : null, function3, obj2, null, 16, null);
        }
        return obj;
    }

    private final boolean tryResume() {
        int value;
        AtomicInt atomicInt = this._decisionAndIndex;
        do {
            value = atomicInt.getValue();
            int i = value >> 29;
            if (i != 0) {
                if (i == 1) {
                    return false;
                }
                throw new IllegalStateException("Already resumed");
            }
        } while (!this._decisionAndIndex.compareAndSet(value, 1073741824 + (536870911 & value)));
        return true;
    }

    private final Symbol tryResumeImpl(Object obj, Object obj2, Function3 function3) {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object value = atomicRef.getValue();
            if (!(value instanceof NotCompleted)) {
                Object obj3 = obj2;
                if ((value instanceof CompletedContinuation) && obj3 != null && ((CompletedContinuation) value).idempotentResume == obj3) {
                    return CancellableContinuationImplKt.RESUME_TOKEN;
                }
                return null;
            }
            CancellableContinuationImpl cancellableContinuationImpl = this;
            Object obj4 = obj;
            Object obj5 = obj2;
            Function3 function32 = function3;
            if (cancellableContinuationImpl._state.compareAndSet(value, cancellableContinuationImpl.resumedState((NotCompleted) value, obj4, this.resumeMode, function32, obj5))) {
                cancellableContinuationImpl.detachChildIfNonReusable();
                return CancellableContinuationImplKt.RESUME_TOKEN;
            }
            this = cancellableContinuationImpl;
            obj = obj4;
            function3 = function32;
            obj2 = obj5;
        }
    }

    private final boolean trySuspend() {
        int value;
        AtomicInt atomicInt = this._decisionAndIndex;
        do {
            value = atomicInt.getValue();
            int i = value >> 29;
            if (i != 0) {
                if (i == 2) {
                    return false;
                }
                throw new IllegalStateException("Already suspended");
            }
        } while (!this._decisionAndIndex.compareAndSet(value, 536870912 + (536870911 & value)));
        return true;
    }

    public final void callCancelHandler(CancelHandler cancelHandler, Throwable th) {
        cancelHandler.getClass();
        try {
            cancelHandler.invoke(th);
        } catch (Throwable th2) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th2));
        }
    }

    public final void callOnCancellation(Function3 function3, Throwable th, Object obj) {
        function3.getClass();
        th.getClass();
        try {
            function3.invoke(th, obj, getContext());
        } catch (Throwable th2) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in resume onCancellation handler for " + this, th2));
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean cancel(Throwable th) {
        Object value;
        AtomicRef atomicRef = this._state;
        do {
            value = atomicRef.getValue();
            if (!(value instanceof NotCompleted)) {
                return false;
            }
        } while (!this._state.compareAndSet(value, new CancelledContinuation(this, th, (value instanceof CancelHandler) || (value instanceof Segment))));
        NotCompleted notCompleted = (NotCompleted) value;
        if (notCompleted instanceof CancelHandler) {
            callCancelHandler((CancelHandler) value, th);
        } else if (notCompleted instanceof Segment) {
            callSegmentOnCancellation((Segment) value, th);
        }
        detachChildIfNonReusable();
        dispatchResume(this.resumeMode);
        return true;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public void cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj, Throwable th) {
        Throwable th2;
        th.getClass();
        AtomicRef atomicRef = this._state;
        while (true) {
            Object value = atomicRef.getValue();
            if (value instanceof NotCompleted) {
                throw new IllegalStateException("Not completed");
            }
            if (value instanceof CompletedExceptionally) {
                return;
            }
            if (value instanceof CompletedContinuation) {
                CompletedContinuation completedContinuation = (CompletedContinuation) value;
                if (completedContinuation.getCancelled()) {
                    throw new IllegalStateException("Must be called at most once");
                }
                Throwable th3 = th;
                th2 = th3;
                if (this._state.compareAndSet(value, CompletedContinuation.copy$default(completedContinuation, null, null, null, null, th3, 15, null))) {
                    completedContinuation.invokeHandlers(this, th2);
                    return;
                }
            } else {
                th2 = th;
                if (this._state.compareAndSet(value, new CompletedContinuation(value, null, null, null, th2, 14, null))) {
                    return;
                }
            }
            th = th2;
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void completeResume(Object obj) {
        obj.getClass();
        dispatchResume(this.resumeMode);
    }

    public final void detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        DisposableHandle parentHandle = getParentHandle();
        if (parentHandle == null) {
            return;
        }
        parentHandle.dispose();
        this._parentHandle.setValue(NonDisposableHandle.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    public CoroutineStackFrame getCallerFrame() {
        Continuation continuation = this.delegate;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlin.coroutines.Continuation
    public CoroutineContext getContext() {
        return this.context;
    }

    public Throwable getContinuationCancellationCause(Job job) {
        job.getClass();
        return job.getCancellationException();
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public final Continuation getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this.delegate;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public Throwable getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        Throwable exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = super.getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj);
        if (exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host != null) {
            return exceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        }
        return null;
    }

    public final Object getResult() {
        Job job;
        boolean zIsReusable = isReusable();
        if (trySuspend()) {
            if (getParentHandle() == null) {
                installParentHandle();
            }
            if (zIsReusable) {
                releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            }
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (zIsReusable) {
            releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        }
        Object state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).cause;
        }
        if (!DispatchedTaskKt.isCancellableMode(this.resumeMode) || (job = (Job) getContext().get(Job.Key)) == null || job.isActive()) {
            return getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
        }
        CancellationException cancellationException = job.getCancellationException();
        cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(state$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host, cancellationException);
        throw cancellationException;
    }

    public final Object getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this._state.getValue();
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public Object getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        return obj instanceof CompletedContinuation ? ((CompletedContinuation) obj).result : obj;
    }

    public void initCancellability() {
        DisposableHandle disposableHandleInstallParentHandle = installParentHandle();
        if (disposableHandleInstallParentHandle != null && isCompleted()) {
            disposableHandleInstallParentHandle.dispose();
            this._parentHandle.setValue(NonDisposableHandle.INSTANCE);
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void invokeOnCancellation(Function1 function1) {
        function1.getClass();
        CancellableContinuationKt.invokeOnCancellation(this, new CancelHandler.UserSupplied(function1));
    }

    @Override // kotlinx.coroutines.Waiter
    public void invokeOnCancellation(Segment segment, int i) {
        int value;
        segment.getClass();
        AtomicInt atomicInt = this._decisionAndIndex;
        do {
            value = atomicInt.getValue();
            if ((value & 536870911) != 536870911) {
                throw new IllegalStateException("invokeOnCancellation should be called at most once");
            }
        } while (!atomicInt.compareAndSet(value, ((value >> 29) << 29) + i));
        invokeOnCancellationImpl(segment);
    }

    public final void invokeOnCancellationInternal$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(CancelHandler cancelHandler) {
        cancelHandler.getClass();
        invokeOnCancellationImpl(cancelHandler);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isActive() {
        return getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() instanceof NotCompleted;
    }

    public boolean isCompleted() {
        return !(getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() instanceof NotCompleted);
    }

    protected String nameString() {
        return "CancellableContinuation";
    }

    public final void parentCancelled$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) {
        th.getClass();
        if (cancelLater(th)) {
            return;
        }
        cancel(th);
        detachChildIfNonReusable();
    }

    public final void releaseClaimedReusableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        Throwable thTryReleaseClaimedContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        Continuation continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        if (dispatchedContinuation == null || (thTryReleaseClaimedContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = dispatchedContinuation.tryReleaseClaimedContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(this)) == null) {
            return;
        }
        detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        cancel(thTryReleaseClaimedContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
    }

    public final boolean resetStateReusable() {
        Object value = this._state.getValue();
        if ((value instanceof CompletedContinuation) && ((CompletedContinuation) value).idempotentResume != null) {
            detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            return false;
        }
        this._decisionAndIndex.setValue(536870911);
        this._state.setValue(Active.INSTANCE);
        return true;
    }

    public void resume(Object obj, final Function1 function1) {
        resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj, this.resumeMode, function1 != null ? new Function3() { // from class: kotlinx.coroutines.CancellableContinuationImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                return CancellableContinuationImpl.resume$lambda$13$lambda$12(function1, (Throwable) obj2, obj3, (CoroutineContext) obj4);
            }
        } : null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resume(Object obj, Function3 function3) {
        resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj, this.resumeMode, function3);
    }

    public final void resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj, int i, Function3 function3) {
        AtomicRef atomicRef = this._state;
        while (true) {
            Object value = atomicRef.getValue();
            if (!(value instanceof NotCompleted)) {
                CancellableContinuationImpl cancellableContinuationImpl = this;
                Object obj2 = obj;
                Function3 function32 = function3;
                if (value instanceof CancelledContinuation) {
                    CancelledContinuation cancelledContinuation = (CancelledContinuation) value;
                    if (cancelledContinuation.makeResumed()) {
                        if (function32 != null) {
                            cancellableContinuationImpl.callOnCancellation(function32, cancelledContinuation.cause, obj2);
                            return;
                        }
                        return;
                    }
                }
                cancellableContinuationImpl.alreadyResumedError(obj2);
                throw new KotlinNothingValueException();
            }
            CancellableContinuationImpl cancellableContinuationImpl2 = this;
            Object obj3 = obj;
            int i2 = i;
            Function3 function33 = function3;
            if (cancellableContinuationImpl2._state.compareAndSet(value, cancellableContinuationImpl2.resumedState((NotCompleted) value, obj3, i2, function33, null))) {
                cancellableContinuationImpl2.detachChildIfNonReusable();
                cancellableContinuationImpl2.dispatchResume(i2);
                return;
            } else {
                this = cancellableContinuationImpl2;
                obj = obj3;
                i = i2;
                function3 = function33;
            }
        }
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resumeUndispatched(CoroutineDispatcher coroutineDispatcher, Object obj) {
        coroutineDispatcher.getClass();
        Continuation continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host$default(this, obj, (dispatchedContinuation != null ? dispatchedContinuation.dispatcher : null) == coroutineDispatcher ? 4 : this.resumeMode, null, 4, null);
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(Object obj) {
        resumeImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host$default(this, CompletionStateKt.toState(obj, this), this.resumeMode, null, 4, null);
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public Object takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
    }

    public String toString() {
        return nameString() + "(" + DebugStringsKt.toDebugString(this.delegate) + "){" + getStateDebugRepresentation() + "}@" + DebugStringsKt.getHexAddress(this);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public Object tryResume(Object obj, Object obj2, Function3 function3) {
        return tryResumeImpl(obj, obj2, function3);
    }
}
