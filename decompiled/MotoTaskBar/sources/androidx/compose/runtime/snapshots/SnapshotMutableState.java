package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutationPolicy;

/* JADX INFO: compiled from: SnapshotMutableState.kt */
/* JADX INFO: loaded from: classes.dex */
public interface SnapshotMutableState extends MutableState {
    SnapshotMutationPolicy getPolicy();
}
