package androidx.compose.runtime;

import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: CompositionLocal.kt */
/* JADX INFO: loaded from: classes.dex */
public final class StaticProvidableCompositionLocal extends ProvidableCompositionLocal {
    public StaticProvidableCompositionLocal(Function0 function0) {
        super(function0);
    }

    @Override // androidx.compose.runtime.ProvidableCompositionLocal
    public ProvidedValue defaultProvidedValue$runtime_release(Object obj) {
        return new ProvidedValue(this, obj, obj == null, null, null, null, false);
    }
}
