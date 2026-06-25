package com.motorola.plugin.core.retry;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: Retryer.kt */
/* JADX INFO: loaded from: classes2.dex */
final class Retryer$call$2$attempt$result$1 extends SuspendLambda implements Function1 {
    final /* synthetic */ CoroutineScope $$this$coroutineScope;
    final /* synthetic */ Function2 $callable;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    Retryer$call$2$attempt$result$1(Function2 function2, CoroutineScope coroutineScope, Continuation continuation) {
        super(1, continuation);
        this.$callable = function2;
        this.$$this$coroutineScope = coroutineScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new Retryer$call$2$attempt$result$1(this.$callable, this.$$this$coroutineScope, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation continuation) {
        return ((Retryer$call$2$attempt$result$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        Function2 function2 = this.$callable;
        CoroutineScope coroutineScope = this.$$this$coroutineScope;
        this.label = 1;
        Object objInvoke = function2.invoke(coroutineScope, this);
        return objInvoke == coroutine_suspended ? coroutine_suspended : objInvoke;
    }
}
