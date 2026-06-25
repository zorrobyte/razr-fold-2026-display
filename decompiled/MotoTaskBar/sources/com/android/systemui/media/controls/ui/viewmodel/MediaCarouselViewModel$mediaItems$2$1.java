package com.android.systemui.media.controls.ui.viewmodel;

import com.android.systemui.media.controls.shared.model.MediaCommonModel;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: MediaCarouselViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
final class MediaCarouselViewModel$mediaItems$2$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MediaCarouselViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MediaCarouselViewModel$mediaItems$2$1(MediaCarouselViewModel mediaCarouselViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mediaCarouselViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Boolean) obj).booleanValue(), (List) obj2, (Continuation) obj3);
    }

    public final Object invoke(boolean z, List list, Continuation continuation) {
        MediaCarouselViewModel$mediaItems$2$1 mediaCarouselViewModel$mediaItems$2$1 = new MediaCarouselViewModel$mediaItems$2$1(this.this$0, continuation);
        mediaCarouselViewModel$mediaItems$2$1.Z$0 = z;
        mediaCarouselViewModel$mediaItems$2$1.L$0 = list;
        return mediaCarouselViewModel$mediaItems$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        List<MediaCommonModel> list = (List) this.L$0;
        MediaCarouselViewModel mediaCarouselViewModel = this.this$0;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        mediaCarouselViewModel.shouldReorder = z;
        boolean zIsReorderingAllowed = mediaCarouselViewModel.isReorderingAllowed();
        for (MediaCommonModel mediaCommonModel : list) {
            if (!zIsReorderingAllowed || !mediaCarouselViewModel.modelsPendingRemoval.contains(mediaCommonModel)) {
                if (!(mediaCommonModel instanceof MediaCommonModel.MediaControl)) {
                    throw new NoWhenBranchMatchedException();
                }
                listCreateListBuilder.add(mediaCarouselViewModel.toViewModel((MediaCommonModel.MediaControl) mediaCommonModel));
            }
        }
        if (zIsReorderingAllowed) {
            mediaCarouselViewModel.modelsPendingRemoval.clear();
        }
        return CollectionsKt.build(listCreateListBuilder);
    }
}
