package com.motorola.plugin.core.channel;

import android.os.RemoteException;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.channel.AbstractCommonChannelImpl;
import com.motorola.plugin.sdk.channel.IRemoteChannelTransfer;
import com.motorola.plugin.sdk.channel.RemoteChannelRequestInfo;
import com.motorola.plugin.sdk.channel.RemoteService;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord $record;
    final /* synthetic */ RemoteChannelRequestInfo $request;
    final /* synthetic */ IRemoteChannelTransfer $target;
    int label;
    final /* synthetic */ AbstractCommonChannelImpl.RemoteChannelCaller this$0;

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$2$1, reason: invalid class name */
    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord $record;
        final /* synthetic */ RemoteChannelRequestInfo $request;
        final /* synthetic */ IRemoteChannelTransfer $target;
        int label;
        final /* synthetic */ AbstractCommonChannelImpl.RemoteChannelCaller this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(AbstractCommonChannelImpl.RemoteChannelCaller remoteChannelCaller, RemoteChannelRequestInfo remoteChannelRequestInfo, AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord actionRecord, IRemoteChannelTransfer iRemoteChannelTransfer, Continuation continuation) {
            super(2, continuation);
            this.this$0 = remoteChannelCaller;
            this.$request = remoteChannelRequestInfo;
            this.$record = actionRecord;
            this.$target = iRemoteChannelTransfer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$request, this.$record, this.$target, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws RemoteException {
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
            String str = this.this$0.logTag;
            Level level = Level.INFO;
            final AbstractCommonChannelImpl.RemoteChannelCaller remoteChannelCaller = this.this$0;
            final AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord actionRecord = this.$record;
            PluginConfigKt.trace$default(str, level, true, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl.RemoteChannelCaller.performRemoteCall.2.1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return '[' + remoteChannelCaller.token + "][" + actionRecord.getSequence() + "] Execute remote call";
                }
            }, 56, null);
            this.$request.bundle.putInt(RemoteService.EXTRA_SEQUENCE, this.$record.getSequence());
            AbstractCommonChannelImpl.RemoteChannelCaller remoteChannelCaller2 = this.this$0;
            IRemoteChannelTransfer iRemoteChannelTransfer = this.$target;
            RemoteChannelRequestInfo remoteChannelRequestInfo = this.$request;
            AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord actionRecord2 = this.$record;
            this.label = 1;
            Object objExecuteRemoteCall = remoteChannelCaller2.executeRemoteCall(iRemoteChannelTransfer, remoteChannelRequestInfo, actionRecord2, this);
            return objExecuteRemoteCall == coroutine_suspended ? coroutine_suspended : objExecuteRemoteCall;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$2(AbstractCommonChannelImpl.RemoteChannelCaller remoteChannelCaller, RemoteChannelRequestInfo remoteChannelRequestInfo, AbstractCommonChannelImpl.RemoteChannelCaller.ActionRecord actionRecord, IRemoteChannelTransfer iRemoteChannelTransfer, Continuation continuation) {
        super(2, continuation);
        this.this$0 = remoteChannelCaller;
        this.$request = remoteChannelRequestInfo;
        this.$record = actionRecord;
        this.$target = iRemoteChannelTransfer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$2(this.this$0, this.$request, this.$record, this.$target, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AbstractCommonChannelImpl$RemoteChannelCaller$performRemoteCall$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
        CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$request, this.$record, this.$target, null);
        this.label = 1;
        Object objWithContext = BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this);
        return objWithContext == coroutine_suspended ? coroutine_suspended : objWithContext;
    }
}
