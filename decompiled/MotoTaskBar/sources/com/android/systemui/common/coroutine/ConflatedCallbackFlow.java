package com.android.systemui.common.coroutine;

import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: ConflatedCallbackFlow.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ConflatedCallbackFlow {
    public static final ConflatedCallbackFlow INSTANCE = new ConflatedCallbackFlow();

    private ConflatedCallbackFlow() {
    }

    public final Flow conflatedCallbackFlow(Function2 function2) {
        function2.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(function2);
    }
}
