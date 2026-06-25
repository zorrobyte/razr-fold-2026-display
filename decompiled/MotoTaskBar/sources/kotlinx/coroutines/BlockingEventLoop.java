package kotlinx.coroutines;

/* JADX INFO: compiled from: EventLoop.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BlockingEventLoop extends EventLoopImplBase {
    private final Thread thread;

    public BlockingEventLoop(Thread thread) {
        thread.getClass();
        this.thread = thread;
    }

    @Override // kotlinx.coroutines.EventLoopImplPlatform
    protected Thread getThread() {
        return this.thread;
    }
}
