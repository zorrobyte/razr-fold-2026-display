package com.motorola.laptoppanel.ui.compose;

import androidx.compose.runtime.MutableState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* JADX INFO: compiled from: AutoDimBox.kt */
/* JADX INFO: loaded from: classes.dex */
final class AutoDimBoxKt$AutoDimBox$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState $isDimmed$delegate;
    final /* synthetic */ long $timeoutMillis;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AutoDimBoxKt$AutoDimBox$1$1(long j, MutableState mutableState, Continuation continuation) {
        super(2, continuation);
        this.$timeoutMillis = j;
        this.$isDimmed$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AutoDimBoxKt$AutoDimBox$1$1(this.$timeoutMillis, this.$isDimmed$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((AutoDimBoxKt$AutoDimBox$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AutoDimBoxKt.AutoDimBox$lambda$2(this.$isDimmed$delegate, false);
            long j = this.$timeoutMillis;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        AutoDimBoxKt.AutoDimBox$lambda$2(this.$isDimmed$delegate, true);
        return Unit.INSTANCE;
    }
}
