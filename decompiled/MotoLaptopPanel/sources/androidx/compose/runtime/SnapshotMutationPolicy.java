package androidx.compose.runtime;

/* JADX INFO: compiled from: SnapshotMutationPolicy.kt */
/* JADX INFO: loaded from: classes.dex */
public interface SnapshotMutationPolicy {
    boolean equivalent(Object obj, Object obj2);

    default Object merge(Object obj, Object obj2, Object obj3) {
        return null;
    }
}
