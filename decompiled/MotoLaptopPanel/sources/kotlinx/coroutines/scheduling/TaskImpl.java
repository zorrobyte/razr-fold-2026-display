package kotlinx.coroutines.scheduling;

import kotlinx.coroutines.DebugStringsKt;

/* JADX INFO: compiled from: Tasks.kt */
/* JADX INFO: loaded from: classes.dex */
final class TaskImpl extends Task {
    public final Runnable block;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TaskImpl(Runnable runnable, long j, boolean z) {
        super(j, z);
        runnable.getClass();
        this.block = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.block.run();
    }

    public String toString() {
        return "Task[" + DebugStringsKt.getClassSimpleName(this.block) + "@" + DebugStringsKt.getHexAddress(this.block) + ", " + this.submissionTime + ", " + TasksKt.taskContextString(this.taskContext) + "]";
    }
}
