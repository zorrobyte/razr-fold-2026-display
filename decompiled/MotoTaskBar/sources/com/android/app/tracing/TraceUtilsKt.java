package com.android.app.tracing;

import android.os.Trace;

/* JADX INFO: compiled from: TraceUtils.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TraceUtilsKt {
    public static final void beginSlice(String str) {
        str.getClass();
        Trace.traceBegin(4096L, str);
    }

    public static final void endSlice() {
        Trace.traceEnd(4096L);
    }
}
