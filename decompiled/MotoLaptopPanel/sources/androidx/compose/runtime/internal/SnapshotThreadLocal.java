package androidx.compose.runtime.internal;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;

/* JADX INFO: compiled from: SnapshotThreadLocal.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SnapshotThreadLocal {
    private Object mainThreadValue;
    private final AtomicReference map = new AtomicReference(SnapshotThreadLocalKt.emptyThreadMap);
    private final Object writeMutex = new Object();

    public final Object get() {
        long jCurrentThreadId = Thread_jvmKt.currentThreadId();
        return jCurrentThreadId == Thread_androidKt.getMainThreadId() ? this.mainThreadValue : ((ThreadMap) this.map.get()).get(jCurrentThreadId);
    }

    public final void set(Object obj) {
        long jCurrentThreadId = Thread_jvmKt.currentThreadId();
        if (jCurrentThreadId == Thread_androidKt.getMainThreadId()) {
            this.mainThreadValue = obj;
            return;
        }
        synchronized (this.writeMutex) {
            ThreadMap threadMap = (ThreadMap) this.map.get();
            if (threadMap.trySet(jCurrentThreadId, obj)) {
                return;
            }
            this.map.set(threadMap.newWith(jCurrentThreadId, obj));
            Unit unit = Unit.INSTANCE;
        }
    }
}
