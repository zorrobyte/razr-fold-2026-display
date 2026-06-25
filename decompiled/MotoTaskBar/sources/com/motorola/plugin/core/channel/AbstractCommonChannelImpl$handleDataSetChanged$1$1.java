package com.motorola.plugin.core.channel;

import android.os.Bundle;
import com.motorola.plugin.core.PluginConfigKt;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class AbstractCommonChannelImpl$handleDataSetChanged$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Bundle $data;
    final /* synthetic */ List $it;
    int label;
    final /* synthetic */ AbstractCommonChannelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbstractCommonChannelImpl$handleDataSetChanged$1$1(AbstractCommonChannelImpl abstractCommonChannelImpl, List list, Bundle bundle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = abstractCommonChannelImpl;
        this.$it = list;
        this.$data = bundle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AbstractCommonChannelImpl$handleDataSetChanged$1$1(this.this$0, this.$it, this.$data, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AbstractCommonChannelImpl$handleDataSetChanged$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final AbstractCommonChannelImpl abstractCommonChannelImpl = this.this$0;
        final List list = this.$it;
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CHANNEL, null, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$handleDataSetChanged$1$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + abstractCommonChannelImpl.getToken() + "] received dataset change " + list;
            }
        }, 62, null);
        this.this$0.myDataSetChangedRegistry.notifyDataSetChanged(this.$it, this.$data);
        return Unit.INSTANCE;
    }
}
