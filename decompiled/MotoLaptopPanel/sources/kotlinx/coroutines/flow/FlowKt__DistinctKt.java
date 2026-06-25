package kotlinx.coroutines.flow;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* JADX INFO: compiled from: Distinct.kt */
/* JADX INFO: loaded from: classes.dex */
abstract /* synthetic */ class FlowKt__DistinctKt {
    private static final Function1 defaultKeySelector = new Function1() { // from class: kotlinx.coroutines.flow.FlowKt__DistinctKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return FlowKt__DistinctKt.defaultKeySelector$lambda$0$FlowKt__DistinctKt(obj);
        }
    };
    private static final Function2 defaultAreEquivalent = new Function2() { // from class: kotlinx.coroutines.flow.FlowKt__DistinctKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return Boolean.valueOf(Intrinsics.areEqual(obj, obj2));
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object defaultKeySelector$lambda$0$FlowKt__DistinctKt(Object obj) {
        return obj;
    }

    public static final Flow distinctUntilChanged(Flow flow) {
        flow.getClass();
        return flow instanceof StateFlow ? flow : distinctUntilChangedBy$FlowKt__DistinctKt(flow, defaultKeySelector, defaultAreEquivalent);
    }

    public static final Flow distinctUntilChanged(Flow flow, Function2 function2) {
        flow.getClass();
        function2.getClass();
        return distinctUntilChangedBy$FlowKt__DistinctKt(flow, defaultKeySelector, (Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2));
    }

    private static final Flow distinctUntilChangedBy$FlowKt__DistinctKt(Flow flow, Function1 function1, Function2 function2) {
        if (flow instanceof DistinctFlowImpl) {
            DistinctFlowImpl distinctFlowImpl = (DistinctFlowImpl) flow;
            if (distinctFlowImpl.keySelector == function1 && distinctFlowImpl.areEquivalent == function2) {
                return flow;
            }
        }
        return new DistinctFlowImpl(flow, function1, function2);
    }
}
