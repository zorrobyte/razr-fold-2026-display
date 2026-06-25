package kotlinx.atomicfu;

import kotlinx.atomicfu.TraceBase;

/* JADX INFO: compiled from: AtomicFU.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AtomicFU {
    public static final AtomicBoolean atomic(boolean z) {
        return atomic(z, TraceBase.None.INSTANCE);
    }

    public static final AtomicBoolean atomic(boolean z, TraceBase traceBase) {
        traceBase.getClass();
        return new AtomicBoolean(z, traceBase);
    }

    public static final AtomicInt atomic(int i) {
        return atomic(i, (TraceBase) TraceBase.None.INSTANCE);
    }

    public static final AtomicInt atomic(int i, TraceBase traceBase) {
        traceBase.getClass();
        return new AtomicInt(i, traceBase);
    }

    public static final AtomicLong atomic(long j) {
        return atomic(j, TraceBase.None.INSTANCE);
    }

    public static final AtomicLong atomic(long j, TraceBase traceBase) {
        traceBase.getClass();
        return new AtomicLong(j, traceBase);
    }

    public static final AtomicRef atomic(Object obj) {
        return atomic(obj, TraceBase.None.INSTANCE);
    }

    public static final AtomicRef atomic(Object obj, TraceBase traceBase) {
        traceBase.getClass();
        return new AtomicRef(obj, traceBase);
    }
}
