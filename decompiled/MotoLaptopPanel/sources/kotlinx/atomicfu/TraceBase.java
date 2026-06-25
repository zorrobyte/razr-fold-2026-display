package kotlinx.atomicfu;

/* JADX INFO: compiled from: Trace.common.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TraceBase {

    /* JADX INFO: compiled from: Trace.common.kt */
    public final class None extends TraceBase {
        public static final None INSTANCE = new None();

        private None() {
        }
    }

    public void append(Object obj) {
        obj.getClass();
    }
}
