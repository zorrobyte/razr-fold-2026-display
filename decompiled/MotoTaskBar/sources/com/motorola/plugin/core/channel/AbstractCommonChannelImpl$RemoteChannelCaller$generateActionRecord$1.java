package com.motorola.plugin.core.channel;

import com.motorola.plugin.core.channel.AbstractCommonChannelImpl;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class AbstractCommonChannelImpl$RemoteChannelCaller$generateActionRecord$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AbstractCommonChannelImpl.RemoteChannelCaller this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbstractCommonChannelImpl$RemoteChannelCaller$generateActionRecord$1(AbstractCommonChannelImpl.RemoteChannelCaller remoteChannelCaller, Continuation continuation) {
        super(continuation);
        this.this$0 = remoteChannelCaller;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.generateActionRecord(null, this);
    }
}
