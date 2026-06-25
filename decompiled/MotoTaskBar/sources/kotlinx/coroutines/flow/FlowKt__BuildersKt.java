package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Builders.kt */
/* JADX INFO: loaded from: classes2.dex */
abstract /* synthetic */ class FlowKt__BuildersKt {
    public static final Flow callbackFlow(Function2 function2) {
        function2.getClass();
        return new CallbackFlowBuilder(function2, null, 0, null, 14, null);
    }

    public static final Flow flow(Function2 function2) {
        function2.getClass();
        return new SafeFlow(function2);
    }

    public static final Flow flowOf(final Object obj) {
        return new Flow() { // from class: kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objEmit = flowCollector.emit(obj, continuation);
                return objEmit == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEmit : Unit.INSTANCE;
            }
        };
    }
}
