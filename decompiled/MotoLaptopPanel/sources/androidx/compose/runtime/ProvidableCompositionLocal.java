package androidx.compose.runtime;

import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: CompositionLocal.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ProvidableCompositionLocal extends CompositionLocal {
    public ProvidableCompositionLocal(Function0 function0) {
        super(function0, null);
    }

    private final ValueHolder valueHolderOf(ProvidedValue providedValue) {
        if (!providedValue.isDynamic$runtime_release()) {
            return providedValue.getCompute$runtime_release() != null ? new ComputedValueHolder(providedValue.getCompute$runtime_release()) : providedValue.getState$runtime_release() != null ? new DynamicValueHolder(providedValue.getState$runtime_release()) : new StaticValueHolder(providedValue.getEffectiveValue$runtime_release());
        }
        MutableState state$runtime_release = providedValue.getState$runtime_release();
        if (state$runtime_release == null) {
            Object value = providedValue.getValue();
            SnapshotMutationPolicy mutationPolicy$runtime_release = providedValue.getMutationPolicy$runtime_release();
            if (mutationPolicy$runtime_release == null) {
                mutationPolicy$runtime_release = SnapshotStateKt.structuralEqualityPolicy();
            }
            state$runtime_release = SnapshotStateKt.mutableStateOf(value, mutationPolicy$runtime_release);
        }
        return new DynamicValueHolder(state$runtime_release);
    }

    public abstract ProvidedValue defaultProvidedValue$runtime_release(Object obj);

    public final ProvidedValue provides(Object obj) {
        return defaultProvidedValue$runtime_release(obj);
    }

    public final ProvidedValue providesDefault(Object obj) {
        return defaultProvidedValue$runtime_release(obj).ifNotAlreadyProvided$runtime_release();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0034 A[PHI: r5
      0x0034: PHI (r5v2 androidx.compose.runtime.ValueHolder) = (r5v5 androidx.compose.runtime.ValueHolder), (r5v6 androidx.compose.runtime.ValueHolder) binds: [B:17:0x0044, B:12:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.compose.runtime.CompositionLocal
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public androidx.compose.runtime.ValueHolder updatedStateOf$runtime_release(androidx.compose.runtime.ProvidedValue r4, androidx.compose.runtime.ValueHolder r5) {
        /*
            r3 = this;
            boolean r0 = r5 instanceof androidx.compose.runtime.DynamicValueHolder
            r1 = 0
            if (r0 == 0) goto L1a
            boolean r0 = r4.isDynamic$runtime_release()
            if (r0 == 0) goto L47
            r1 = r5
            androidx.compose.runtime.DynamicValueHolder r1 = (androidx.compose.runtime.DynamicValueHolder) r1
            androidx.compose.runtime.MutableState r5 = r1.getState()
            java.lang.Object r0 = r4.getEffectiveValue$runtime_release()
            r5.setValue(r0)
            goto L47
        L1a:
            boolean r0 = r5 instanceof androidx.compose.runtime.StaticValueHolder
            if (r0 == 0) goto L36
            boolean r0 = r4.isStatic$runtime_release()
            if (r0 == 0) goto L47
            java.lang.Object r0 = r4.getEffectiveValue$runtime_release()
            androidx.compose.runtime.StaticValueHolder r5 = (androidx.compose.runtime.StaticValueHolder) r5
            java.lang.Object r2 = r5.getValue()
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
            if (r0 == 0) goto L47
        L34:
            r1 = r5
            goto L47
        L36:
            boolean r0 = r5 instanceof androidx.compose.runtime.ComputedValueHolder
            if (r0 == 0) goto L47
            kotlin.jvm.functions.Function1 r0 = r4.getCompute$runtime_release()
            androidx.compose.runtime.ComputedValueHolder r5 = (androidx.compose.runtime.ComputedValueHolder) r5
            kotlin.jvm.functions.Function1 r2 = r5.getCompute()
            if (r0 != r2) goto L47
            goto L34
        L47:
            if (r1 != 0) goto L4e
            androidx.compose.runtime.ValueHolder r3 = r3.valueHolderOf(r4)
            return r3
        L4e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ProvidableCompositionLocal.updatedStateOf$runtime_release(androidx.compose.runtime.ProvidedValue, androidx.compose.runtime.ValueHolder):androidx.compose.runtime.ValueHolder");
    }
}
