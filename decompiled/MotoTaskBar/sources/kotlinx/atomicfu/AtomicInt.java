package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* JADX INFO: compiled from: AtomicFU.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class AtomicInt {
    private static final Companion Companion = new Companion(null);
    private static final AtomicIntegerFieldUpdater FU = AtomicIntegerFieldUpdater.newUpdater(AtomicInt.class, "value");
    private final TraceBase trace;
    private volatile int value;

    /* JADX INFO: compiled from: AtomicFU.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AtomicInt(int i, TraceBase traceBase) {
        traceBase.getClass();
        this.trace = traceBase;
        this.value = i;
    }

    public final int addAndGet(int i) {
        int iAddAndGet = FU.addAndGet(this, i);
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("addAndGet(" + i + "):" + iAddAndGet);
        }
        return iAddAndGet;
    }

    public final boolean compareAndSet(int i, int i2) {
        TraceBase traceBase;
        boolean zCompareAndSet = FU.compareAndSet(this, i, i2);
        if (zCompareAndSet && (traceBase = this.trace) != TraceBase.None.INSTANCE) {
            traceBase.append("CAS(" + i + ", " + i2 + ")");
        }
        return zCompareAndSet;
    }

    public final int decrementAndGet() {
        int iDecrementAndGet = FU.decrementAndGet(this);
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("decAndGet():" + iDecrementAndGet);
        }
        return iDecrementAndGet;
    }

    public final int getAndDecrement() {
        int andDecrement = FU.getAndDecrement(this);
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("getAndDec():" + andDecrement);
        }
        return andDecrement;
    }

    public final int getAndIncrement() {
        int andIncrement = FU.getAndIncrement(this);
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("getAndInc():" + andIncrement);
        }
        return andIncrement;
    }

    public final int getValue() {
        return this.value;
    }

    public final int incrementAndGet() {
        int iIncrementAndGet = FU.incrementAndGet(this);
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("incAndGet():" + iIncrementAndGet);
        }
        return iIncrementAndGet;
    }

    public final void setValue(int i) {
        this.value = i;
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("set(" + i + ")");
        }
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
