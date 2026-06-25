package kotlinx.coroutines.flow;

import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.internal.FlowCoroutineKt;

/* JADX INFO: compiled from: Delay.kt */
/* JADX INFO: loaded from: classes.dex */
abstract /* synthetic */ class FlowKt__DelayKt {
    public static final Flow debounce(Flow flow, final long j) {
        flow.getClass();
        if (j >= 0) {
            return j == 0 ? flow : debounceInternal$FlowKt__DelayKt(flow, new Function1() { // from class: kotlinx.coroutines.flow.FlowKt__DelayKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Long.valueOf(FlowKt__DelayKt.debounce$lambda$1$FlowKt__DelayKt(j, obj));
                }
            });
        }
        throw new IllegalArgumentException("Debounce timeout should not be negative");
    }

    public static final Flow debounce(Flow flow, Function1 function1) {
        flow.getClass();
        function1.getClass();
        return debounceInternal$FlowKt__DelayKt(flow, function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long debounce$lambda$1$FlowKt__DelayKt(long j, Object obj) {
        return j;
    }

    private static final Flow debounceInternal$FlowKt__DelayKt(Flow flow, Function1 function1) {
        return FlowCoroutineKt.scopedFlow(new FlowKt__DelayKt$debounceInternal$1(function1, flow, null));
    }
}
