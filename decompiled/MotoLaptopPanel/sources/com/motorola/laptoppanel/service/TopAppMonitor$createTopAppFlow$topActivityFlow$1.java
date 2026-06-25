package com.motorola.laptoppanel.service;

import com.motorola.laptoppanel.util.Logger;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import motorola.core_services.activity.IMotoTopActivityListener;
import motorola.core_services.activity.MotoActivityTaskManager;

/* JADX INFO: compiled from: TopAppMonitor.kt */
/* JADX INFO: loaded from: classes.dex */
final class TopAppMonitor$createTopAppFlow$topActivityFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TopAppMonitor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    TopAppMonitor$createTopAppFlow$topActivityFlow$1(TopAppMonitor topAppMonitor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = topAppMonitor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(TopAppMonitor topAppMonitor, TopAppMonitor$createTopAppFlow$topActivityFlow$1$listener$1 topAppMonitor$createTopAppFlow$topActivityFlow$1$listener$1) {
        try {
            topAppMonitor.getMotoAtm().unregisterTopActivityListener(topAppMonitor$createTopAppFlow$topActivityFlow$1$listener$1);
            Logger.INSTANCE.d("TopAppMonitor", "Top activity listener unregistered.", new Object[0]);
        } catch (Exception e) {
            Logger.INSTANCE.e("TopAppMonitor", (Throwable) e, "unregisterTopActivityListener failed", new Object[0]);
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TopAppMonitor$createTopAppFlow$topActivityFlow$1 topAppMonitor$createTopAppFlow$topActivityFlow$1 = new TopAppMonitor$createTopAppFlow$topActivityFlow$1(this.this$0, continuation);
        topAppMonitor$createTopAppFlow$topActivityFlow$1.L$0 = obj;
        return topAppMonitor$createTopAppFlow$topActivityFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((TopAppMonitor$createTopAppFlow$topActivityFlow$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final AtomicInteger atomicInteger = new AtomicInteger(-1);
            final IMotoTopActivityListener iMotoTopActivityListener = new IMotoTopActivityListener.Stub() { // from class: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$topActivityFlow$1$listener$1
                public void onTaskMultiWindowingModeChanged(int i2, int i3) {
                    Logger logger = Logger.INSTANCE;
                    logger.d("TopAppMonitor", "onTaskMultiWindowingModeChanged(taskId=" + i2 + ", windowingMode=" + i3 + ")", new Object[0]);
                    if (i2 == atomicInteger.get() && 1 == i3) {
                        logger.d("TopAppMonitor", "Triggering refresh for last focused task: " + i2, new Object[0]);
                        producerScope.mo2215trySendJP2dKIU(Integer.valueOf(i2));
                    }
                }

                public void onTopActivityChanged(int i2, boolean z) {
                    Logger.INSTANCE.d("TopAppMonitor", "onTopActivityChanged(taskId=" + i2 + ", focused=" + z + ")", new Object[0]);
                    if (z) {
                        atomicInteger.set(i2);
                        producerScope.mo2215trySendJP2dKIU(Integer.valueOf(i2));
                    }
                }
            };
            try {
                MotoActivityTaskManager.TopActivityInfo topActivityInfo = this.this$0.getMotoAtm().getTopActivityInfo();
                if (topActivityInfo != null) {
                    Logger.INSTANCE.i("TopAppMonitor", "Initial top activity task Id = " + topActivityInfo.mTaskId, new Object[0]);
                    atomicInteger.set(topActivityInfo.mTaskId);
                }
                this.this$0.getMotoAtm().registerTopActivityListener(iMotoTopActivityListener);
                Logger.INSTANCE.d("TopAppMonitor", "Top activity listener registered.", new Object[0]);
            } catch (Exception e) {
                Logger.INSTANCE.e("TopAppMonitor", (Throwable) e, "registerTopActivityListener failed", new Object[0]);
                producerScope.close(e);
            }
            final TopAppMonitor topAppMonitor = this.this$0;
            Function0 function0 = new Function0() { // from class: com.motorola.laptoppanel.service.TopAppMonitor$createTopAppFlow$topActivityFlow$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return TopAppMonitor$createTopAppFlow$topActivityFlow$1.invokeSuspend$lambda$0(topAppMonitor, iMotoTopActivityListener);
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
