package com.android.app.tracing.coroutines;

import android.os.PerfettoTrace;

/* JADX INFO: compiled from: TraceContextElement.kt */
/* JADX INFO: loaded from: classes.dex */
final class PerfettoTraceConfig {
    public static final PerfettoTraceConfig INSTANCE = new PerfettoTraceConfig();
    public static final PerfettoTrace.Category COROUTINE_CATEGORY = new PerfettoTrace.Category("cc");

    private PerfettoTraceConfig() {
    }
}
