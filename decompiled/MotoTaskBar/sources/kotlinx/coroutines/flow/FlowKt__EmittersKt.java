package kotlinx.coroutines.flow;

import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Emitters.kt */
/* JADX INFO: loaded from: classes2.dex */
abstract /* synthetic */ class FlowKt__EmittersKt {
    public static final void ensureActive(FlowCollector flowCollector) {
        flowCollector.getClass();
    }

    public static final Flow onStart(Flow flow, Function2 function2) {
        flow.getClass();
        function2.getClass();
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(function2, flow);
    }
}
