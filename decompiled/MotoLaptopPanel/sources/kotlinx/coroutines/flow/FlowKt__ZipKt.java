package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.internal.CombineKt;

/* JADX INFO: compiled from: Zip.kt */
/* JADX INFO: loaded from: classes.dex */
abstract /* synthetic */ class FlowKt__ZipKt {
    public static final void checkCombinedFlow(Flow flow) {
        if (flow == null) {
            new NullPointerException("combine with null flow").printStackTrace();
        }
    }

    public static final Flow combine(Flow flow, Flow flow2, Function3 function3) {
        flow.getClass();
        flow2.getClass();
        function3.getClass();
        return FlowKt.flowCombine(flow, flow2, function3);
    }

    public static final Flow flowCombine(final Flow flow, final Flow flow2, final Function3 function3) {
        flow.getClass();
        flow2.getClass();
        function3.getClass();
        Flow flow3 = new Flow() { // from class: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCombineInternal = CombineKt.combineInternal(flowCollector, new Flow[]{flow, flow2}, new Function0() { // from class: kotlinx.coroutines.flow.FlowKt__ZipKt$nullArrayFactory$1
                    @Override // kotlin.jvm.functions.Function0
                    public final Void invoke() {
                        return null;
                    }
                }, new FlowKt__ZipKt$combine$1$1(function3, null), continuation);
                return objCombineInternal == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCombineInternal : Unit.INSTANCE;
            }
        };
        FlowKt.checkCombinedFlow(flow2);
        return flow3;
    }
}
