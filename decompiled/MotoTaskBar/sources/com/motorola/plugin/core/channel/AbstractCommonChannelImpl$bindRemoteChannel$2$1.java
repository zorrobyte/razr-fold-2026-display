package com.motorola.plugin.core.channel;

import com.motorola.plugin.sdk.channel.ConnectionException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class AbstractCommonChannelImpl$bindRemoteChannel$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $reason;
    int label;
    final /* synthetic */ AbstractCommonChannelImpl this$0;

    /* JADX INFO: renamed from: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$bindRemoteChannel$2$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: AbstractCommonChannelImpl.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $reason;
        int label;
        final /* synthetic */ AbstractCommonChannelImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(AbstractCommonChannelImpl abstractCommonChannelImpl, String str, Continuation continuation) {
            super(2, continuation);
            this.this$0 = abstractCommonChannelImpl;
            this.$reason = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$reason, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws ConnectionException {
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
            AbstractCommonChannelImpl abstractCommonChannelImpl = this.this$0;
            String str = this.$reason;
            this.label = 1;
            Object objBindRemoteChannelLocked = abstractCommonChannelImpl.bindRemoteChannelLocked(str, this);
            return objBindRemoteChannelLocked == coroutine_suspended ? coroutine_suspended : objBindRemoteChannelLocked;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbstractCommonChannelImpl$bindRemoteChannel$2$1(AbstractCommonChannelImpl abstractCommonChannelImpl, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = abstractCommonChannelImpl;
        this.$reason = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AbstractCommonChannelImpl$bindRemoteChannel$2$1(this.this$0, this.$reason, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AbstractCommonChannelImpl$bindRemoteChannel$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$reason, null);
        this.label = 1;
        Object objWithContext = BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this);
        return objWithContext == coroutine_suspended ? coroutine_suspended : objWithContext;
    }
}
