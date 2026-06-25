package com.android.systemui.media.controls.domain.pipeline.interactor;

import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: MediaCarouselInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
final class MediaCarouselInteractor$sortedMedia$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    MediaCarouselInteractor$sortedMedia$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaCarouselInteractor$sortedMedia$1 mediaCarouselInteractor$sortedMedia$1 = new MediaCarouselInteractor$sortedMedia$1(continuation);
        mediaCarouselInteractor$sortedMedia$1.L$0 = obj;
        return mediaCarouselInteractor$sortedMedia$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Map map, Continuation continuation) {
        return ((MediaCarouselInteractor$sortedMedia$1) create(map, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return CollectionsKt.toList(((Map) this.L$0).values());
    }
}
