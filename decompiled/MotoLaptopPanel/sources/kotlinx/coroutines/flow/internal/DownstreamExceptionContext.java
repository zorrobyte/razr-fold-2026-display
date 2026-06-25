package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: SafeCollector.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DownstreamExceptionContext implements CoroutineContext {
    private final /* synthetic */ CoroutineContext $$delegate_0;
    public final Throwable e;

    public DownstreamExceptionContext(Throwable th, CoroutineContext coroutineContext) {
        th.getClass();
        coroutineContext.getClass();
        this.$$delegate_0 = coroutineContext;
        this.e = th;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public Object fold(Object obj, Function2 function2) {
        function2.getClass();
        return this.$$delegate_0.fold(obj, function2);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext.Element get(CoroutineContext.Key key) {
        key.getClass();
        return this.$$delegate_0.get(key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext minusKey(CoroutineContext.Key key) {
        key.getClass();
        return this.$$delegate_0.minusKey(key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public CoroutineContext plus(CoroutineContext coroutineContext) {
        coroutineContext.getClass();
        return this.$$delegate_0.plus(coroutineContext);
    }
}
