package com.android.systemui.media.controls.domain.pipeline.interactor;

import com.android.systemui.media.controls.shared.model.MediaData;
import java.util.Iterator;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: MediaCarouselInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
final class MediaCarouselInteractor$hasActiveMedia$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    MediaCarouselInteractor$hasActiveMedia$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaCarouselInteractor$hasActiveMedia$1 mediaCarouselInteractor$hasActiveMedia$1 = new MediaCarouselInteractor$hasActiveMedia$1(continuation);
        mediaCarouselInteractor$hasActiveMedia$1.L$0 = obj;
        return mediaCarouselInteractor$hasActiveMedia$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Map map, Continuation continuation) {
        return ((MediaCarouselInteractor$hasActiveMedia$1) create(map, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Map map = (Map) this.L$0;
        boolean z = false;
        if (!map.isEmpty()) {
            Iterator it = map.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((MediaData) ((Map.Entry) it.next()).getValue()).getActive()) {
                    z = true;
                    break;
                }
            }
        }
        return Boxing.boxBoolean(z);
    }
}
