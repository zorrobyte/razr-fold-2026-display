package com.android.systemui.media.controls.ui.viewmodel;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: MediaCarouselViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
final class MediaCarouselViewModel$mediaItems$3 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaCarouselViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MediaCarouselViewModel$mediaItems$3(MediaCarouselViewModel mediaCarouselViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mediaCarouselViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(List list, List list2, Continuation continuation) {
        MediaCarouselViewModel$mediaItems$3 mediaCarouselViewModel$mediaItems$3 = new MediaCarouselViewModel$mediaItems$3(this.this$0, continuation);
        mediaCarouselViewModel$mediaItems$3.L$0 = list;
        mediaCarouselViewModel$mediaItems$3.L$1 = list2;
        return mediaCarouselViewModel$mediaItems$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        return (this.this$0.isReorderingAllowed() || this.this$0.shouldReorder || list.isEmpty()) ? (List) this.L$1 : list;
    }
}
