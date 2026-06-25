package androidx.compose.runtime;

import kotlin.coroutines.CoroutineContext;

/* JADX INFO: compiled from: ProduceState.kt */
/* JADX INFO: loaded from: classes.dex */
final class ProduceStateScopeImpl implements ProduceStateScope, MutableState {
    private final /* synthetic */ MutableState $$delegate_0;
    private final CoroutineContext coroutineContext;

    public ProduceStateScopeImpl(MutableState mutableState, CoroutineContext coroutineContext) {
        this.coroutineContext = coroutineContext;
        this.$$delegate_0 = mutableState;
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        return this.coroutineContext;
    }

    @Override // androidx.compose.runtime.MutableState, androidx.compose.runtime.State
    public Object getValue() {
        return this.$$delegate_0.getValue();
    }

    @Override // androidx.compose.runtime.MutableState
    public void setValue(Object obj) {
        this.$$delegate_0.setValue(obj);
    }
}
