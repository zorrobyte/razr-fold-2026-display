package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Builders.kt */
/* JADX INFO: loaded from: classes2.dex */
final class SafeFlow extends AbstractFlow {
    private final Function2 block;

    public SafeFlow(Function2 function2) {
        function2.getClass();
        this.block = function2;
    }

    @Override // kotlinx.coroutines.flow.AbstractFlow
    public Object collectSafely(FlowCollector flowCollector, Continuation continuation) {
        Object objInvoke = this.block.invoke(flowCollector, continuation);
        return objInvoke == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objInvoke : Unit.INSTANCE;
    }
}
