package kotlinx.coroutines;

/* JADX INFO: compiled from: Job.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ChildJob extends Job {
    void parentCancelled(ParentJob parentJob);
}
