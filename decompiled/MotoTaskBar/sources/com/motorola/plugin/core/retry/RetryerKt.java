package com.motorola.plugin.core.retry;

import java.util.concurrent.ExecutionException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: Retryer.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RetryerKt {

    /* JADX INFO: renamed from: com.motorola.plugin.core.retry.RetryerKt$callBlocking$1, reason: invalid class name */
    /* JADX INFO: compiled from: Retryer.kt */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function0 $callable;
        final /* synthetic */ Retryer $this_callBlocking;
        int label;

        /* JADX INFO: renamed from: com.motorola.plugin.core.retry.RetryerKt$callBlocking$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: Retryer.kt */
        final class C00431 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function0 $callable;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00431(Function0 function0, Continuation continuation) {
                super(2, continuation);
                this.$callable = function0;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00431(this.$callable, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00431) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return this.$callable.mo2224invoke();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Retryer retryer, Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$this_callBlocking = retryer;
            this.$callable = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$this_callBlocking, this.$callable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws ExecutionException, RetryException {
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
            Retryer retryer = this.$this_callBlocking;
            C00431 c00431 = new C00431(this.$callable, null);
            this.label = 1;
            Object objCall = retryer.call(c00431, this);
            return objCall == coroutine_suspended ? coroutine_suspended : objCall;
        }
    }

    public static final Object callBlocking(Retryer retryer, Function0 function0) {
        retryer.getClass();
        function0.getClass();
        return BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(retryer, function0, null), 1, null);
    }
}
