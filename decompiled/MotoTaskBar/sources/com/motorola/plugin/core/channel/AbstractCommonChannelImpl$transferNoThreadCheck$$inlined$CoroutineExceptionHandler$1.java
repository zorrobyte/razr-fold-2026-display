package com.motorola.plugin.core.channel;

import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineExceptionHandler;

/* JADX INFO: compiled from: CoroutineExceptionHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class AbstractCommonChannelImpl$transferNoThreadCheck$$inlined$CoroutineExceptionHandler$1 extends AbstractCoroutineContextElement implements CoroutineExceptionHandler {
    final /* synthetic */ Function1 $safetyRemoteErrorCallback$inlined;
    final /* synthetic */ AbstractCommonChannelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractCommonChannelImpl$transferNoThreadCheck$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key key, AbstractCommonChannelImpl abstractCommonChannelImpl, Function1 function1) {
        super(key);
        this.this$0 = abstractCommonChannelImpl;
        this.$safetyRemoteErrorCallback$inlined = function1;
    }

    @Override // kotlinx.coroutines.CoroutineExceptionHandler
    public void handleException(CoroutineContext coroutineContext, Throwable th) {
        BuildersKt__Builders_commonKt.launch$default(this.this$0.getChannelScope(), null, null, new AbstractCommonChannelImpl$transferNoThreadCheck$errorHandler$1$1(this.$safetyRemoteErrorCallback$inlined, th, null), 3, null);
    }
}
