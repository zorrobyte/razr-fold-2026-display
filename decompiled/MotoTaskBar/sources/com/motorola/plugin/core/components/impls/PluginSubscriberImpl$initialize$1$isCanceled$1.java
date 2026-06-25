package com.motorola.plugin.core.components.impls;

import com.motorola.plugin.core.components.impls.PluginSubscriberImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: PluginSubscriberImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class PluginSubscriberImpl$initialize$1$isCanceled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PluginSubscriberImpl.PluginSession $session;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PluginSubscriberImpl$initialize$1$isCanceled$1(PluginSubscriberImpl.PluginSession pluginSession, Continuation continuation) {
        super(2, continuation);
        this.$session = pluginSession;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PluginSubscriberImpl$initialize$1$isCanceled$1(this.$session, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((PluginSubscriberImpl$initialize$1$isCanceled$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.$session.isCanceled()) {
            z = true;
        } else {
            this.$session.moveToLoadingState();
            z = false;
        }
        return Boxing.boxBoolean(z);
    }
}
