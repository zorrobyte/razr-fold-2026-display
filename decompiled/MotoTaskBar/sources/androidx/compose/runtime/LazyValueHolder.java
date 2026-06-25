package androidx.compose.runtime;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: ValueHolders.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LazyValueHolder implements ValueHolder {
    private final Lazy current$delegate;

    public LazyValueHolder(Function0 function0) {
        this.current$delegate = LazyKt.lazy(function0);
    }

    private final Object getCurrent() {
        return this.current$delegate.getValue();
    }

    @Override // androidx.compose.runtime.ValueHolder
    public Object readValue(PersistentCompositionLocalMap persistentCompositionLocalMap) {
        return getCurrent();
    }
}
