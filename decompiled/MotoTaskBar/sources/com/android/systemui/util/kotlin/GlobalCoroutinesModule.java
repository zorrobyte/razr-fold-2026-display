package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.TraceContextElementKt;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: GlobalCoroutinesModule.kt */
/* JADX INFO: loaded from: classes.dex */
public final class GlobalCoroutinesModule {
    public final CoroutineScope applicationScope(CoroutineContext coroutineContext) {
        coroutineContext.getClass();
        return CoroutineScopeKt.CoroutineScope(coroutineContext);
    }

    public final CoroutineContext mainCoroutineContext(CoroutineContext coroutineContext) {
        coroutineContext.getClass();
        return Dispatchers.getMain().getImmediate().plus(coroutineContext);
    }

    public final CoroutineDispatcher mainDispatcher() {
        return Dispatchers.getMain().getImmediate();
    }

    public final CoroutineContext tracingCoroutineContext() {
        return TraceContextElementKt.createCoroutineTracingContext$default(null, false, false, false, false, null, 63, null);
    }
}
