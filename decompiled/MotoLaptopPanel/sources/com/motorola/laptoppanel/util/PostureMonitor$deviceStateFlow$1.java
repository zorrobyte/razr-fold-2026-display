package com.motorola.laptoppanel.util;

import android.content.Context;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: compiled from: PostureMonitor.kt */
/* JADX INFO: loaded from: classes.dex */
final class PostureMonitor$deviceStateFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PostureMonitor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PostureMonitor$deviceStateFlow$1(PostureMonitor postureMonitor, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = postureMonitor;
        this.$context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(PostureMonitor postureMonitor, PostureMonitor$deviceStateFlow$1$callback$1 postureMonitor$deviceStateFlow$1$callback$1) {
        postureMonitor.deviceStateManager.unregisterCallback(postureMonitor$deviceStateFlow$1$callback$1);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PostureMonitor$deviceStateFlow$1 postureMonitor$deviceStateFlow$1 = new PostureMonitor$deviceStateFlow$1(this.this$0, this.$context, continuation);
        postureMonitor$deviceStateFlow$1.L$0 = obj;
        return postureMonitor$deviceStateFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((PostureMonitor$deviceStateFlow$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.hardware.devicestate.DeviceStateManager$DeviceStateCallback, com.motorola.laptoppanel.util.PostureMonitor$deviceStateFlow$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new DeviceStateManager.DeviceStateCallback() { // from class: com.motorola.laptoppanel.util.PostureMonitor$deviceStateFlow$1$callback$1
                public void onDeviceStateChanged(DeviceState deviceState) {
                    deviceState.getClass();
                    producerScope.mo2215trySendJP2dKIU(Integer.valueOf(deviceState.getIdentifier()));
                }
            };
            this.this$0.deviceStateManager.registerCallback(this.$context.getMainExecutor(), (DeviceStateManager.DeviceStateCallback) r1);
            final PostureMonitor postureMonitor = this.this$0;
            Function0 function0 = new Function0() { // from class: com.motorola.laptoppanel.util.PostureMonitor$deviceStateFlow$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return PostureMonitor$deviceStateFlow$1.invokeSuspend$lambda$0(postureMonitor, r1);
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
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
