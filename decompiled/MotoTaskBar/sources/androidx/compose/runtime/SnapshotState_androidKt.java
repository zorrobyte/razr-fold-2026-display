package androidx.compose.runtime;

import androidx.compose.runtime.snapshots.SnapshotMutableState;

/* JADX INFO: compiled from: SnapshotState.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SnapshotState_androidKt {
    public static final SnapshotMutableState createSnapshotMutableState(Object obj, SnapshotMutationPolicy snapshotMutationPolicy) {
        return new ParcelableSnapshotMutableState(obj, snapshotMutationPolicy);
    }
}
