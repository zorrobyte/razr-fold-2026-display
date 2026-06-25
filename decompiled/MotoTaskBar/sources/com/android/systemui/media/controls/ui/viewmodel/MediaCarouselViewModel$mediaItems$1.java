package com.android.systemui.media.controls.ui.viewmodel;

import com.android.systemui.statusbar.notification.collection.provider.OnReorderingAllowedListener;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: compiled from: MediaCarouselViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
final class MediaCarouselViewModel$mediaItems$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaCarouselViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MediaCarouselViewModel$mediaItems$1(MediaCarouselViewModel mediaCarouselViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaCarouselViewModel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(MediaCarouselViewModel mediaCarouselViewModel, OnReorderingAllowedListener onReorderingAllowedListener) {
        mediaCarouselViewModel.visualStabilityProvider.removeReorderingAllowedListener(onReorderingAllowedListener);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaCarouselViewModel$mediaItems$1 mediaCarouselViewModel$mediaItems$1 = new MediaCarouselViewModel$mediaItems$1(this.this$0, continuation);
        mediaCarouselViewModel$mediaItems$1.L$0 = obj;
        return mediaCarouselViewModel$mediaItems$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((MediaCarouselViewModel$mediaItems$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final OnReorderingAllowedListener onReorderingAllowedListener = new OnReorderingAllowedListener() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$mediaItems$1$listener$1
            };
            this.this$0.visualStabilityProvider.addPersistentReorderingAllowedListener(onReorderingAllowedListener);
            producerScope.mo2736trySendJP2dKIU(Unit.INSTANCE);
            final MediaCarouselViewModel mediaCarouselViewModel = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$mediaItems$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return MediaCarouselViewModel$mediaItems$1.invokeSuspend$lambda$0(mediaCarouselViewModel, onReorderingAllowedListener);
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
