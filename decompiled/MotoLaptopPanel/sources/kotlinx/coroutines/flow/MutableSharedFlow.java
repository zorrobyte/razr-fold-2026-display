package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: SharedFlow.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MutableSharedFlow extends SharedFlow, FlowCollector {
    @Override // kotlinx.coroutines.flow.FlowCollector
    Object emit(Object obj, Continuation continuation);

    StateFlow getSubscriptionCount();

    void resetReplayCache();

    boolean tryEmit(Object obj);
}
