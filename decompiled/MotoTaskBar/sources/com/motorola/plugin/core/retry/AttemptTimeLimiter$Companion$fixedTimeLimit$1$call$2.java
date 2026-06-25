package com.motorola.plugin.core.retry;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AttemptTimeLimiter.kt */
/* JADX INFO: loaded from: classes2.dex */
final class AttemptTimeLimiter$Companion$fixedTimeLimit$1$call$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $callable;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AttemptTimeLimiter$Companion$fixedTimeLimit$1$call$2(Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$callable = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AttemptTimeLimiter$Companion$fixedTimeLimit$1$call$2(this.$callable, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AttemptTimeLimiter$Companion$fixedTimeLimit$1$call$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
        Function1 function1 = this.$callable;
        this.label = 1;
        Object objInvoke = function1.invoke(this);
        return objInvoke == coroutine_suspended ? coroutine_suspended : objInvoke;
    }
}
