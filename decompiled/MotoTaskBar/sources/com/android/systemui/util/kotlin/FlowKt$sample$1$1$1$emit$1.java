package com.android.systemui.util.kotlin;

import com.android.systemui.util.kotlin.FlowKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* JADX INFO: compiled from: Flow.kt */
/* JADX INFO: loaded from: classes.dex */
final class FlowKt$sample$1$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt.C01391.C00301.C00311 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FlowKt$sample$1$1$1$emit$1(FlowKt.C01391.C00301.C00311 c00311, Continuation continuation) {
        super(continuation);
        this.this$0 = c00311;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
