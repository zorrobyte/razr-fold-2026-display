package androidx.compose.runtime;

/* JADX INFO: compiled from: SnapshotMutationPolicy.kt */
/* JADX INFO: loaded from: classes.dex */
abstract /* synthetic */ class SnapshotStateKt__SnapshotMutationPolicyKt {
    public static final SnapshotMutationPolicy neverEqualPolicy() {
        NeverEqualPolicy neverEqualPolicy = NeverEqualPolicy.INSTANCE;
        neverEqualPolicy.getClass();
        return neverEqualPolicy;
    }

    public static final SnapshotMutationPolicy referentialEqualityPolicy() {
        ReferentialEqualityPolicy referentialEqualityPolicy = ReferentialEqualityPolicy.INSTANCE;
        referentialEqualityPolicy.getClass();
        return referentialEqualityPolicy;
    }

    public static final SnapshotMutationPolicy structuralEqualityPolicy() {
        StructuralEqualityPolicy structuralEqualityPolicy = StructuralEqualityPolicy.INSTANCE;
        structuralEqualityPolicy.getClass();
        return structuralEqualityPolicy;
    }
}
