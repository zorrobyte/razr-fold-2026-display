package com.android.app.tracing.coroutines;

import com.android.systemui.Flags;

/* JADX INFO: compiled from: TraceData.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TraceDataThreadLocal extends ThreadLocal {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // java.lang.ThreadLocal
    public TraceStorage initialValue() {
        if (Flags.coroutineTracing()) {
            return new TraceStorage(null);
        }
        return null;
    }
}
