package com.android.systemui.media.controls.ui.viewmodel;

import com.android.systemui.media.controls.shared.model.MediaControlModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: MediaControlViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
final class MediaControlViewModel$player$1$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MediaControlViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MediaControlViewModel$player$1$1(MediaControlViewModel mediaControlViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mediaControlViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Boolean) obj).booleanValue(), (MediaControlModel) obj2, (Continuation) obj3);
    }

    public final Object invoke(boolean z, MediaControlModel mediaControlModel, Continuation continuation) {
        MediaControlViewModel$player$1$1 mediaControlViewModel$player$1$1 = new MediaControlViewModel$player$1$1(this.this$0, continuation);
        mediaControlViewModel$player$1$1.Z$0 = z;
        mediaControlViewModel$player$1$1.L$0 = mediaControlModel;
        return mediaControlViewModel$player$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            MediaControlModel mediaControlModel = (MediaControlModel) this.L$0;
            if (mediaControlModel == null) {
                return null;
            }
            MediaControlViewModel mediaControlViewModel = this.this$0;
            this.label = 1;
            obj = mediaControlViewModel.toViewModel(mediaControlModel, z, this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return (MediaPlayerViewModel) obj;
    }
}
