package com.android.app.tracing.coroutines;

import android.os.SystemProperties;

/* JADX INFO: compiled from: TraceContextElement.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DebugSysProps {
    public static final DebugSysProps INSTANCE = new DebugSysProps();
    public static final boolean alwaysEnableStackWalker = SystemProperties.getBoolean("debug.coroutine_tracing.walk_stack_override", false);
    public static final boolean alwaysEnableContinuationCounting = SystemProperties.getBoolean("debug.coroutine_tracing.count_continuations_override", false);

    private DebugSysProps() {
    }
}
