package com.android.systemui.settings;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: UserTrackerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
final class UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitchComplete$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $newUserId;
    int label;
    final /* synthetic */ UserTrackerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitchComplete$1(UserTrackerImpl userTrackerImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userTrackerImpl;
        this.$newUserId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitchComplete$1(this.this$0, this.$newUserId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitchComplete$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.handleUserSwitchComplete(this.$newUserId);
        return Unit.INSTANCE;
    }
}
