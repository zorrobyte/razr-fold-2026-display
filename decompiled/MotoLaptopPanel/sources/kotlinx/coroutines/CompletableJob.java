package kotlinx.coroutines;

/* JADX INFO: compiled from: CompletableJob.kt */
/* JADX INFO: loaded from: classes.dex */
public interface CompletableJob extends Job {
    boolean completeExceptionally(Throwable th);
}
