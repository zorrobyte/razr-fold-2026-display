package com.android.systemui.utils.coroutines.flow;

import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: FlowConflated.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FlowConflatedKt {
    public static final Flow conflatedCallbackFlow(Function2 function2) {
        function2.getClass();
        return FlowKt.conflate(FlowKt.callbackFlow(function2));
    }
}
