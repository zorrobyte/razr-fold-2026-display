package androidx.compose.runtime;

import kotlin.KotlinNothingValueException;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: Composer.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ProvidedValue {
    public static final int $stable = 8;
    private boolean canOverride = true;
    private final CompositionLocal compositionLocal;
    private final Function1 compute;
    private final boolean explicitNull;
    private final boolean isDynamic;
    private final SnapshotMutationPolicy mutationPolicy;
    private final Object providedValue;
    private final MutableState state;

    public ProvidedValue(CompositionLocal compositionLocal, Object obj, boolean z, SnapshotMutationPolicy snapshotMutationPolicy, MutableState mutableState, Function1 function1, boolean z2) {
        this.compositionLocal = compositionLocal;
        this.explicitNull = z;
        this.mutationPolicy = snapshotMutationPolicy;
        this.state = mutableState;
        this.compute = function1;
        this.isDynamic = z2;
        this.providedValue = obj;
    }

    public final boolean getCanOverride() {
        return this.canOverride;
    }

    public final CompositionLocal getCompositionLocal() {
        return this.compositionLocal;
    }

    public final Function1 getCompute$runtime_release() {
        return this.compute;
    }

    public final Object getEffectiveValue$runtime_release() {
        if (this.explicitNull) {
            return null;
        }
        MutableState mutableState = this.state;
        if (mutableState != null) {
            return mutableState.getValue();
        }
        Object obj = this.providedValue;
        if (obj != null) {
            return obj;
        }
        ComposerKt.composeRuntimeError("Unexpected form of a provided value");
        throw new KotlinNothingValueException();
    }

    public final SnapshotMutationPolicy getMutationPolicy$runtime_release() {
        return this.mutationPolicy;
    }

    public final MutableState getState$runtime_release() {
        return this.state;
    }

    public final Object getValue() {
        return this.providedValue;
    }

    public final ProvidedValue ifNotAlreadyProvided$runtime_release() {
        this.canOverride = false;
        return this;
    }

    public final boolean isDynamic$runtime_release() {
        return this.isDynamic;
    }

    public final boolean isStatic$runtime_release() {
        return (this.explicitNull || getValue() != null) && !this.isDynamic;
    }
}
