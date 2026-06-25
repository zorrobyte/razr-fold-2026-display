package androidx.compose.runtime;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SnapshotMutationPolicy.kt */
/* JADX INFO: loaded from: classes.dex */
final class StructuralEqualityPolicy implements SnapshotMutationPolicy {
    public static final StructuralEqualityPolicy INSTANCE = new StructuralEqualityPolicy();

    private StructuralEqualityPolicy() {
    }

    @Override // androidx.compose.runtime.SnapshotMutationPolicy
    public boolean equivalent(Object obj, Object obj2) {
        return Intrinsics.areEqual(obj, obj2);
    }

    public String toString() {
        return "StructuralEqualityPolicy";
    }
}
