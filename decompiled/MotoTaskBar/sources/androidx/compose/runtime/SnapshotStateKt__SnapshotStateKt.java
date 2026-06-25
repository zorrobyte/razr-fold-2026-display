package androidx.compose.runtime;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: SnapshotState.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract /* synthetic */ class SnapshotStateKt__SnapshotStateKt {
    public static final MutableState mutableStateOf(Object obj, SnapshotMutationPolicy snapshotMutationPolicy) {
        return SnapshotState_androidKt.createSnapshotMutableState(obj, snapshotMutationPolicy);
    }

    public static /* synthetic */ MutableState mutableStateOf$default(Object obj, SnapshotMutationPolicy snapshotMutationPolicy, int i, Object obj2) {
        if ((i & 2) != 0) {
            snapshotMutationPolicy = SnapshotStateKt.structuralEqualityPolicy();
        }
        return SnapshotStateKt.mutableStateOf(obj, snapshotMutationPolicy);
    }
}
