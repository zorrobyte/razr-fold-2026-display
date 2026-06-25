package androidx.compose.runtime;

import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: CompositionLocal.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DynamicProvidableCompositionLocal extends ProvidableCompositionLocal {
    private final SnapshotMutationPolicy policy;

    public DynamicProvidableCompositionLocal(SnapshotMutationPolicy snapshotMutationPolicy, Function0 function0) {
        super(function0);
        this.policy = snapshotMutationPolicy;
    }

    @Override // androidx.compose.runtime.ProvidableCompositionLocal
    public ProvidedValue defaultProvidedValue$runtime_release(Object obj) {
        return new ProvidedValue(this, obj, obj == null, this.policy, null, null, true);
    }
}
