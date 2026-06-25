package com.android.systemui.media.controls.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: Merge.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaControlViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaControlViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaControlViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, MediaControlViewModel mediaControlViewModel) {
        super(3, continuation);
        this.this$0 = mediaControlViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(FlowCollector flowCollector, Object obj, Continuation continuation) {
        MediaControlViewModel$special$$inlined$flatMapLatest$1 mediaControlViewModel$special$$inlined$flatMapLatest$1 = new MediaControlViewModel$special$$inlined$flatMapLatest$1(continuation, this.this$0);
        mediaControlViewModel$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        mediaControlViewModel$special$$inlined$flatMapLatest$1.L$1 = obj;
        return mediaControlViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowCombine = FlowKt.combine(this.this$0.playTurbulenceNoise, this.this$0.interactor.getMediaControl(), new MediaControlViewModel$player$1$1(this.this$0, null));
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowCombine, this) == coroutine_suspended) {
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
