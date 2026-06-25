package kotlinx.coroutines.scheduling;

/* JADX INFO: compiled from: Tasks.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class Task implements Runnable {
    public long submissionTime;
    public boolean taskContext;

    public Task() {
        this(0L, false);
    }

    public Task(long j, boolean z) {
        this.submissionTime = j;
        this.taskContext = z;
    }
}
