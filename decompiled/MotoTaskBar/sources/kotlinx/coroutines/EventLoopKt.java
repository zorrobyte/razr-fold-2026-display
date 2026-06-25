package kotlinx.coroutines;

/* JADX INFO: compiled from: EventLoop.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class EventLoopKt {
    public static final EventLoop createEventLoop() {
        Thread threadCurrentThread = Thread.currentThread();
        threadCurrentThread.getClass();
        return new BlockingEventLoop(threadCurrentThread);
    }
}
