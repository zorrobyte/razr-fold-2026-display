package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: Flow.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface Flow {
    Object collect(FlowCollector flowCollector, Continuation continuation);
}
