package com.motorola.plugin.core.channel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class AbstractCommonChannelImpl$transferNoThreadCheck$errorHandler$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Throwable $error;
    final /* synthetic */ Function1 $safetyRemoteErrorCallback;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbstractCommonChannelImpl$transferNoThreadCheck$errorHandler$1$1(Function1 function1, Throwable th, Continuation continuation) {
        super(2, continuation);
        this.$safetyRemoteErrorCallback = function1;
        this.$error = th;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AbstractCommonChannelImpl$transferNoThreadCheck$errorHandler$1$1(this.$safetyRemoteErrorCallback, this.$error, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AbstractCommonChannelImpl$transferNoThreadCheck$errorHandler$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$safetyRemoteErrorCallback.invoke(this.$error);
        return Unit.INSTANCE;
    }
}
