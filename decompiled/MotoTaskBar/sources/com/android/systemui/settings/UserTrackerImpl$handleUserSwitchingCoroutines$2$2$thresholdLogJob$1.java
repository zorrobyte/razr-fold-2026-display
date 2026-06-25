package com.android.systemui.settings;

import android.util.Log;
import com.android.systemui.settings.UserTracker;
import com.motorola.plugin.core.utils.TimeoutRemoteCaller;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* JADX INFO: compiled from: UserTrackerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
final class UserTrackerImpl$handleUserSwitchingCoroutines$2$2$thresholdLogJob$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserTracker.Callback $callback;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UserTrackerImpl$handleUserSwitchingCoroutines$2$2$thresholdLogJob$1(UserTracker.Callback callback, Continuation continuation) {
        super(2, continuation);
        this.$callback = callback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserTrackerImpl$handleUserSwitchingCoroutines$2$2$thresholdLogJob$1(this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((UserTrackerImpl$handleUserSwitchingCoroutines$2$2$thresholdLogJob$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(TimeoutRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        Log.e("UserTrackerImpl", "Failed to finish " + this.$callback + " in time");
        return Unit.INSTANCE;
    }
}
