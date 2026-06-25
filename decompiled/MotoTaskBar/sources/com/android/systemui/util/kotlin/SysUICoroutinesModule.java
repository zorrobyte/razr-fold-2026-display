package com.android.systemui.util.kotlin;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ThreadPoolDispatcherKt;

/* JADX INFO: compiled from: SysUICoroutinesModule.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SysUICoroutinesModule {
    public final CoroutineContext bgCoroutineContext(CoroutineContext coroutineContext, CoroutineDispatcher coroutineDispatcher) {
        coroutineContext.getClass();
        coroutineDispatcher.getClass();
        return coroutineDispatcher.plus(coroutineContext);
    }

    public final CoroutineDispatcher bgDispatcher() {
        return ThreadPoolDispatcherKt.newFixedThreadPoolContext(Runtime.getRuntime().availableProcessors(), "SystemUIBg");
    }
}
