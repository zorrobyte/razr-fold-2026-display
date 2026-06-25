package kotlinx.atomicfu;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.atomicfu.TraceBase;

/* JADX INFO: compiled from: AtomicFU.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AtomicBoolean {
    private static final Companion Companion = new Companion(null);
    private static final AtomicIntegerFieldUpdater FU = AtomicIntegerFieldUpdater.newUpdater(AtomicBoolean.class, "_value");
    private volatile int _value;
    private final TraceBase trace;

    /* JADX INFO: compiled from: AtomicFU.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AtomicBoolean(boolean z, TraceBase traceBase) {
        traceBase.getClass();
        this.trace = traceBase;
        this._value = z ? 1 : 0;
    }

    public final boolean compareAndSet(boolean z, boolean z2) {
        TraceBase traceBase;
        boolean zCompareAndSet = FU.compareAndSet(this, z ? 1 : 0, z2 ? 1 : 0);
        if (zCompareAndSet && (traceBase = this.trace) != TraceBase.None.INSTANCE) {
            traceBase.append("CAS(" + z + ", " + z2 + ")");
        }
        return zCompareAndSet;
    }

    public final boolean getValue() {
        return this._value != 0;
    }

    public final void setValue(boolean z) {
        this._value = z ? 1 : 0;
        TraceBase traceBase = this.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.append("set(" + z + ")");
        }
    }

    public String toString() {
        return String.valueOf(getValue());
    }
}
