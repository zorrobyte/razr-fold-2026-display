package kotlinx.coroutines.internal;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DispatchException;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.ThreadLocalEventLoop;

/* JADX INFO: compiled from: DispatchedContinuation.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DispatchedContinuationKt {
    private static final Symbol UNDEFINED = new Symbol("UNDEFINED");
    public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");

    /* JADX WARN: Removed duplicated region for block: B:27:0x0091 A[Catch: all -> 0x006a, DONT_GENERATE, TryCatch #1 {all -> 0x006a, blocks: (B:11:0x0041, B:13:0x004f, B:15:0x0055, B:28:0x0094, B:18:0x006c, B:20:0x007c, B:25:0x008b, B:27:0x0091, B:33:0x00a1, B:36:0x00aa, B:35:0x00a7, B:23:0x0082), top: B:47:0x0041, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void resumeCancellableWith(kotlin.coroutines.Continuation r6, java.lang.Object r7) {
        /*
            r6.getClass()
            boolean r0 = r6 instanceof kotlinx.coroutines.internal.DispatchedContinuation
            if (r0 == 0) goto Lb5
            kotlinx.coroutines.internal.DispatchedContinuation r6 = (kotlinx.coroutines.internal.DispatchedContinuation) r6
            java.lang.Object r0 = kotlinx.coroutines.CompletionStateKt.toState(r7)
            kotlinx.coroutines.CoroutineDispatcher r1 = r6.dispatcher
            kotlin.coroutines.CoroutineContext r2 = r6.getContext()
            boolean r1 = safeIsDispatchNeeded(r1, r2)
            r2 = 1
            if (r1 == 0) goto L29
            r6._state = r0
            r6.resumeMode = r2
            kotlinx.coroutines.CoroutineDispatcher r7 = r6.dispatcher
            kotlin.coroutines.CoroutineContext r0 = r6.getContext()
            safeDispatch(r7, r0, r6)
            goto Laf
        L29:
            kotlinx.coroutines.ThreadLocalEventLoop r1 = kotlinx.coroutines.ThreadLocalEventLoop.INSTANCE
            kotlinx.coroutines.EventLoop r1 = r1.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            boolean r3 = r1.isUnconfinedLoopActive()
            if (r3 == 0) goto L3e
            r6._state = r0
            r6.resumeMode = r2
            r1.dispatchUnconfined(r6)
            goto Laf
        L3e:
            r1.incrementUseCount(r2)
            kotlin.coroutines.CoroutineContext r3 = r6.getContext()     // Catch: java.lang.Throwable -> L6a
            kotlinx.coroutines.Job$Key r4 = kotlinx.coroutines.Job.Key     // Catch: java.lang.Throwable -> L6a
            kotlin.coroutines.CoroutineContext$Element r3 = r3.get(r4)     // Catch: java.lang.Throwable -> L6a
            kotlinx.coroutines.Job r3 = (kotlinx.coroutines.Job) r3     // Catch: java.lang.Throwable -> L6a
            if (r3 == 0) goto L6c
            boolean r4 = r3.isActive()     // Catch: java.lang.Throwable -> L6a
            if (r4 != 0) goto L6c
            java.util.concurrent.CancellationException r7 = r3.getCancellationException()     // Catch: java.lang.Throwable -> L6a
            r6.cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r0, r7)     // Catch: java.lang.Throwable -> L6a
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L6a
            java.lang.Object r7 = kotlin.ResultKt.createFailure(r7)     // Catch: java.lang.Throwable -> L6a
            java.lang.Object r7 = kotlin.Result.m2192constructorimpl(r7)     // Catch: java.lang.Throwable -> L6a
            r6.resumeWith(r7)     // Catch: java.lang.Throwable -> L6a
            goto L94
        L6a:
            r7 = move-exception
            goto Lab
        L6c:
            kotlin.coroutines.Continuation r0 = r6.continuation     // Catch: java.lang.Throwable -> L6a
            java.lang.Object r3 = r6.countOrElement     // Catch: java.lang.Throwable -> L6a
            kotlin.coroutines.CoroutineContext r4 = r0.getContext()     // Catch: java.lang.Throwable -> L6a
            java.lang.Object r3 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r4, r3)     // Catch: java.lang.Throwable -> L6a
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.internal.ThreadContextKt.NO_THREAD_ELEMENTS     // Catch: java.lang.Throwable -> L6a
            if (r3 == r5) goto L81
            kotlinx.coroutines.UndispatchedCoroutine r0 = kotlinx.coroutines.CoroutineContextKt.updateUndispatchedCompletion(r0, r4, r3)     // Catch: java.lang.Throwable -> L6a
            goto L82
        L81:
            r0 = 0
        L82:
            kotlin.coroutines.Continuation r5 = r6.continuation     // Catch: java.lang.Throwable -> L9e
            r5.resumeWith(r7)     // Catch: java.lang.Throwable -> L9e
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L9e
            if (r0 == 0) goto L91
            boolean r7 = r0.clearThreadContext()     // Catch: java.lang.Throwable -> L6a
            if (r7 == 0) goto L94
        L91:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r4, r3)     // Catch: java.lang.Throwable -> L6a
        L94:
            boolean r7 = r1.processUnconfinedEvent()     // Catch: java.lang.Throwable -> L6a
            if (r7 != 0) goto L94
        L9a:
            r1.decrementUseCount(r2)
            goto Laf
        L9e:
            r7 = move-exception
            if (r0 == 0) goto La7
            boolean r0 = r0.clearThreadContext()     // Catch: java.lang.Throwable -> L6a
            if (r0 == 0) goto Laa
        La7:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r4, r3)     // Catch: java.lang.Throwable -> L6a
        Laa:
            throw r7     // Catch: java.lang.Throwable -> L6a
        Lab:
            r6.handleFatalException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r7)     // Catch: java.lang.Throwable -> Lb0
            goto L9a
        Laf:
            return
        Lb0:
            r6 = move-exception
            r1.decrementUseCount(r2)
            throw r6
        Lb5:
            r6.resumeWith(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.DispatchedContinuationKt.resumeCancellableWith(kotlin.coroutines.Continuation, java.lang.Object):void");
    }

    public static final void safeDispatch(CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext, Runnable runnable) {
        coroutineDispatcher.getClass();
        coroutineContext.getClass();
        runnable.getClass();
        try {
            coroutineDispatcher.dispatch(coroutineContext, runnable);
        } catch (Throwable th) {
            throw new DispatchException(th, coroutineDispatcher, coroutineContext);
        }
    }

    public static final boolean safeIsDispatchNeeded(CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext) throws DispatchException {
        coroutineDispatcher.getClass();
        coroutineContext.getClass();
        try {
            return coroutineDispatcher.isDispatchNeeded(coroutineContext);
        } catch (Throwable th) {
            throw new DispatchException(th, coroutineDispatcher, coroutineContext);
        }
    }

    public static final boolean yieldUndispatched(DispatchedContinuation dispatchedContinuation) {
        dispatchedContinuation.getClass();
        Unit unit = Unit.INSTANCE;
        EventLoop eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ThreadLocalEventLoop.INSTANCE.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
        if (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.isUnconfinedLoopActive()) {
            dispatchedContinuation._state = unit;
            dispatchedContinuation.resumeMode = 1;
            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.dispatchUnconfined(dispatchedContinuation);
            return true;
        }
        eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.incrementUseCount(true);
        try {
            dispatchedContinuation.run();
            do {
            } while (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.processUnconfinedEvent());
        } finally {
            try {
            } finally {
            }
        }
        return false;
    }
}
