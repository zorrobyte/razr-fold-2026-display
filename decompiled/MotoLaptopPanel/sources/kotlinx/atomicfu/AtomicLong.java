package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* JADX INFO: compiled from: AtomicFU.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AtomicLong {
    private static final Companion Companion = new Companion(null);
    private static final AtomicLongFieldUpdater FU = AtomicLongFieldUpdater.newUpdater(AtomicLong.class, "value");
    private final TraceBase trace;
    private volatile long value;

    /* JADX INFO: compiled from: AtomicFU.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AtomicLong(long j, TraceBase traceBase) {
        traceBase.getClass();
        this.trace = traceBase;
        this.value = j;
    }

    public final long addAndGet(long j) {
        long jAddAndGet = FU.addAndGet(this, j);
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("addAndGet(" + j + "):" + jAddAndGet);
        }
        return jAddAndGet;
    }

    public final boolean compareAndSet(long j, long j2) {
        TraceBase traceBase;
        boolean zCompareAndSet = FU.compareAndSet(this, j, j2);
        if (zCompareAndSet && (traceBase = this.trace) != TraceBase.None.INSTANCE) {
            traceBase.append("CAS(" + j + ", " + j2 + ")");
        }
        return zCompareAndSet;
    }

    public final long getAndDecrement() {
        long andDecrement = FU.getAndDecrement(this);
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("getAndDec():" + andDecrement);
        }
        return andDecrement;
    }

    public final long getAndIncrement() {
        long andIncrement = FU.getAndIncrement(this);
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("getAndInc():" + andIncrement);
        }
        return andIncrement;
    }

    public final long getValue() {
        return this.value;
    }

    public final long incrementAndGet() {
        long jIncrementAndGet = FU.incrementAndGet(this);
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("incAndGet():" + jIncrementAndGet);
        }
        return jIncrementAndGet;
    }

    public final void setValue(long j) {
        this.value = j;
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("set(" + j + ")");
        }
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
