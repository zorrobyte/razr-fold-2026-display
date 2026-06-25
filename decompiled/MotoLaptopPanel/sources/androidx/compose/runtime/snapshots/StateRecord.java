package androidx.compose.runtime.snapshots;

/* JADX INFO: compiled from: Snapshot.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class StateRecord {
    private StateRecord next;
    private long snapshotId;

    public StateRecord(long j) {
        this.snapshotId = j;
    }

    public abstract void assign(StateRecord stateRecord);

    public abstract StateRecord create(long j);

    public final StateRecord getNext$runtime_release() {
        return this.next;
    }

    public final long getSnapshotId$runtime_release() {
        return this.snapshotId;
    }

    public final void setNext$runtime_release(StateRecord stateRecord) {
        this.next = stateRecord;
    }

    public final void setSnapshotId$runtime_release(long j) {
        this.snapshotId = j;
    }
}
