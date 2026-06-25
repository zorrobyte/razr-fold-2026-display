package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Builders.common.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract /* synthetic */ class BuildersKt__Builders_commonKt {
    public static final Job launch(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2) {
        coroutineScope.getClass();
        coroutineContext.getClass();
        coroutineStart.getClass();
        function2.getClass();
        CoroutineContext coroutineContextNewCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext);
        StandaloneCoroutine lazyStandaloneCoroutine = coroutineStart.isLazy() ? new LazyStandaloneCoroutine(coroutineContextNewCoroutineContext, function2) : new StandaloneCoroutine(coroutineContextNewCoroutineContext, true);
        lazyStandaloneCoroutine.start(coroutineStart, lazyStandaloneCoroutine, function2);
        return lazyStandaloneCoroutine;
    }

    public static /* synthetic */ Job launch$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 2) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        return BuildersKt.launch(coroutineScope, coroutineContext, coroutineStart, function2);
    }

    public static final Object withContext(CoroutineContext coroutineContext, Function2 function2, Continuation continuation) {
        Object result$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        CoroutineContext context = continuation.getContext();
        CoroutineContext coroutineContextNewCoroutineContext = CoroutineContextKt.newCoroutineContext(context, coroutineContext);
        JobKt.ensureActive(coroutineContextNewCoroutineContext);
        if (coroutineContextNewCoroutineContext == context) {
            ScopeCoroutine scopeCoroutine = new ScopeCoroutine(coroutineContextNewCoroutineContext, continuation);
            result$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = UndispatchedKt.startUndispatchedOrReturn(scopeCoroutine, scopeCoroutine, function2);
        } else {
            ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
            if (Intrinsics.areEqual(coroutineContextNewCoroutineContext.get(key), context.get(key))) {
                UndispatchedCoroutine undispatchedCoroutine = new UndispatchedCoroutine(coroutineContextNewCoroutineContext, continuation);
                CoroutineContext context2 = undispatchedCoroutine.getContext();
                Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context2, null);
                try {
                    Object objStartUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(undispatchedCoroutine, undispatchedCoroutine, function2);
                    ThreadContextKt.restoreThreadContext(context2, objUpdateThreadContext);
                    result$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = objStartUndispatchedOrReturn;
                } catch (Throwable th) {
                    ThreadContextKt.restoreThreadContext(context2, objUpdateThreadContext);
                    throw th;
                }
            } else {
                DispatchedCoroutine dispatchedCoroutine = new DispatchedCoroutine(coroutineContextNewCoroutineContext, continuation);
                CancellableKt.startCoroutineCancellable(function2, dispatchedCoroutine, dispatchedCoroutine);
                result$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = dispatchedCoroutine.getResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            }
        }
        if (result$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
    }
}
