package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlinx.coroutines.EventLoopImplBase;

/* JADX INFO: compiled from: EventLoop.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class EventLoopImplPlatform extends EventLoop {
    protected abstract Thread getThread();

    protected void reschedule(long j, EventLoopImplBase.DelayedTask delayedTask) {
        delayedTask.getClass();
        DefaultExecutor.INSTANCE.schedule(j, delayedTask);
    }

    protected final void unpark() {
        Thread thread = getThread();
        if (Thread.currentThread() != thread) {
            AbstractTimeSourceKt.access$getTimeSource$p();
            LockSupport.unpark(thread);
        }
    }
}
