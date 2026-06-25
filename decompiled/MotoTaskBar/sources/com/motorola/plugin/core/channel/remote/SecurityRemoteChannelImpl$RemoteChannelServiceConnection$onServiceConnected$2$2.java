package com.motorola.plugin.core.channel.remote;

import com.motorola.plugin.sdk.channel.IRemoteChannelTransfer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;

/* JADX INFO: compiled from: SecurityRemoteChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceConnected$2$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ SecurityRemoteChannelImpl $channel;
    final /* synthetic */ IRemoteChannelTransfer $it;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceConnected$2$2(SecurityRemoteChannelImpl securityRemoteChannelImpl, IRemoteChannelTransfer iRemoteChannelTransfer, Continuation continuation) {
        super(2, continuation);
        this.$channel = securityRemoteChannelImpl;
        this.$it = iRemoteChannelTransfer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceConnected$2$2(this.$channel, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SecurityRemoteChannelImpl$RemoteChannelServiceConnection$onServiceConnected$2$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Channel channel = this.$channel.mRemoteChannelTransferChannel;
            IRemoteChannelTransfer iRemoteChannelTransfer = this.$it;
            this.label = 1;
            if (channel.send(iRemoteChannelTransfer, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
