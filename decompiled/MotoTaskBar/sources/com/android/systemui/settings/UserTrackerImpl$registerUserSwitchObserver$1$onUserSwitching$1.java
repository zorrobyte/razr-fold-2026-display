package com.android.systemui.settings;

import android.os.Bundle;
import android.os.IRemoteCallback;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: UserTrackerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
final class UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitching$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $newUserId;
    final /* synthetic */ IRemoteCallback $reply;
    int label;
    final /* synthetic */ UserTrackerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitching$1(UserTrackerImpl userTrackerImpl, int i, IRemoteCallback iRemoteCallback, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userTrackerImpl;
        this.$newUserId = i;
        this.$reply = iRemoteCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(IRemoteCallback iRemoteCallback) {
        if (iRemoteCallback != null) {
            iRemoteCallback.sendResult((Bundle) null);
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitching$1(this.this$0, this.$newUserId, this.$reply, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitching$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserTrackerImpl userTrackerImpl = this.this$0;
            int i2 = this.$newUserId;
            final IRemoteCallback iRemoteCallback = this.$reply;
            Function0 function0 = new Function0() { // from class: com.android.systemui.settings.UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitching$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return UserTrackerImpl$registerUserSwitchObserver$1$onUserSwitching$1.invokeSuspend$lambda$0(iRemoteCallback);
                }
            };
            this.label = 1;
            if (userTrackerImpl.handleUserSwitchingCoroutines(i2, function0, this) == coroutine_suspended) {
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
