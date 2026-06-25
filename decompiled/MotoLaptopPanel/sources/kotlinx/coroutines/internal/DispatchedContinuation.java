package kotlinx.coroutines.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.ThreadLocalEventLoop;

/* JADX INFO: compiled from: DispatchedContinuation.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DispatchedContinuation extends DispatchedTask implements CoroutineStackFrame, Continuation {
    private final AtomicRef _reusableCancellableContinuation;
    public Object _state;
    public final Continuation continuation;
    public final Object countOrElement;
    public final CoroutineDispatcher dispatcher;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DispatchedContinuation(CoroutineDispatcher coroutineDispatcher, Continuation continuation) {
        super(-1);
        coroutineDispatcher.getClass();
        continuation.getClass();
        this.dispatcher = coroutineDispatcher;
        this.continuation = continuation;
        this._state = DispatchedContinuationKt.UNDEFINED;
        this.countOrElement = ThreadContextKt.threadContextElements(getContext());
        this._reusableCancellableContinuation = AtomicFU.atomic((Object) null);
    }

    private final CancellableContinuationImpl getReusableCancellableContinuation() {
        Object value = this._reusableCancellableContinuation.getValue();
        if (value instanceof CancellableContinuationImpl) {
            return (CancellableContinuationImpl) value;
        }
        return null;
    }

    public final void awaitReusability$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        while (this._reusableCancellableContinuation.getValue() == DispatchedContinuationKt.REUSABLE_CLAIMED) {
        }
    }

    public final CancellableContinuationImpl claimReusableCancellableContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        AtomicRef atomicRef = this._reusableCancellableContinuation;
        while (true) {
            Object value = atomicRef.getValue();
            if (value == null) {
                this._reusableCancellableContinuation.setValue(DispatchedContinuationKt.REUSABLE_CLAIMED);
                return null;
            }
            if (value instanceof CancellableContinuationImpl) {
                if (this._reusableCancellableContinuation.compareAndSet(value, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
                    return (CancellableContinuationImpl) value;
                }
            } else if (value != DispatchedContinuationKt.REUSABLE_CLAIMED && !(value instanceof Throwable)) {
                throw new IllegalStateException(("Inconsistent state " + value).toString());
            }
        }
    }

    public final void dispatchYield$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(CoroutineContext coroutineContext, Object obj) {
        coroutineContext.getClass();
        this._state = obj;
        this.resumeMode = 1;
        this.dispatcher.dispatchYield(coroutineContext, this);
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    public CoroutineStackFrame getCallerFrame() {
        Continuation continuation = this.continuation;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlin.coroutines.Continuation
    public CoroutineContext getContext() {
        return this.continuation.getContext();
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public Continuation getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this;
    }

    public final boolean isReusable$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        return this._reusableCancellableContinuation.getValue() != null;
    }

    public final boolean postponeCancellation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) {
        th.getClass();
        AtomicRef atomicRef = this._reusableCancellableContinuation;
        while (true) {
            Object value = atomicRef.getValue();
            Symbol symbol = DispatchedContinuationKt.REUSABLE_CLAIMED;
            if (Intrinsics.areEqual(value, symbol)) {
                if (this._reusableCancellableContinuation.compareAndSet(symbol, th)) {
                    return true;
                }
            } else {
                if (value instanceof Throwable) {
                    return true;
                }
                if (this._reusableCancellableContinuation.compareAndSet(value, null)) {
                    return false;
                }
            }
        }
    }

    public final void release$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        awaitReusability$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        CancellableContinuationImpl reusableCancellableContinuation = getReusableCancellableContinuation();
        if (reusableCancellableContinuation != null) {
            reusableCancellableContinuation.detachChild$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        }
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(Object obj) {
        Object state = CompletionStateKt.toState(obj);
        if (DispatchedContinuationKt.safeIsDispatchNeeded(this.dispatcher, getContext())) {
            this._state = state;
            this.resumeMode = 0;
            DispatchedContinuationKt.safeDispatch(this.dispatcher, getContext(), this);
            return;
        }
        EventLoop eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ThreadLocalEventLoop.INSTANCE.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.isUnconfinedLoopActive()) {
            this._state = state;
            this.resumeMode = 0;
            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.dispatchUnconfined(this);
            return;
        }
        eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.incrementUseCount(true);
        try {
            CoroutineContext context = getContext();
            Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context, this.countOrElement);
            try {
                this.continuation.resumeWith(obj);
                Unit unit = Unit.INSTANCE;
                while (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.processUnconfinedEvent()) {
                }
            } finally {
                ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public Object takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        Object obj = this._state;
        this._state = DispatchedContinuationKt.UNDEFINED;
        return obj;
    }

    public String toString() {
        return "DispatchedContinuation[" + this.dispatcher + ", " + DebugStringsKt.toDebugString(this.continuation) + "]";
    }

    public final Throwable tryReleaseClaimedContinuation$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(CancellableContinuation cancellableContinuation) {
        Symbol symbol;
        cancellableContinuation.getClass();
        AtomicRef atomicRef = this._reusableCancellableContinuation;
        do {
            Object value = atomicRef.getValue();
            symbol = DispatchedContinuationKt.REUSABLE_CLAIMED;
            if (value != symbol) {
                if (value instanceof Throwable) {
                    if (this._reusableCancellableContinuation.compareAndSet(value, null)) {
                        return (Throwable) value;
                    }
                    throw new IllegalArgumentException("Failed requirement.");
                }
                throw new IllegalStateException(("Inconsistent state " + value).toString());
            }
        } while (!this._reusableCancellableContinuation.compareAndSet(symbol, cancellableContinuation));
        return null;
    }
}
