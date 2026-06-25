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
public final class MediaCarouselViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaCarouselViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCarouselViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, MediaCarouselViewModel mediaCarouselViewModel) {
        super(3, continuation);
        this.this$0 = mediaCarouselViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(FlowCollector flowCollector, Object obj, Continuation continuation) {
        MediaCarouselViewModel$special$$inlined$flatMapLatest$1 mediaCarouselViewModel$special$$inlined$flatMapLatest$1 = new MediaCarouselViewModel$special$$inlined$flatMapLatest$1(continuation, this.this$0);
        mediaCarouselViewModel$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        mediaCarouselViewModel$special$$inlined$flatMapLatest$1.L$1 = obj;
        return mediaCarouselViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowCombine = FlowKt.combine(this.this$0.interactor.isMediaFromRec(), this.this$0.interactor.getSortedMedia(), new MediaCarouselViewModel$mediaItems$2$1(this.this$0, null));
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
