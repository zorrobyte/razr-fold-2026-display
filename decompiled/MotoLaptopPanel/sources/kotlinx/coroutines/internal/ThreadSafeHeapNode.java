package kotlinx.coroutines.internal;

/* JADX INFO: compiled from: ThreadSafeHeap.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ThreadSafeHeapNode {
    ThreadSafeHeap getHeap();

    int getIndex();

    void setHeap(ThreadSafeHeap threadSafeHeap);

    void setIndex(int i);
}
