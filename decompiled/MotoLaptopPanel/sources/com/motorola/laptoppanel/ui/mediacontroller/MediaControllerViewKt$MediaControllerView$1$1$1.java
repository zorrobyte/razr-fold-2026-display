package com.motorola.laptoppanel.ui.mediacontroller;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import com.motorola.laptoppanel.ui.mediacontroller.MediaUiState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: MediaControllerView.kt */
/* JADX INFO: loaded from: classes.dex */
final class MediaControllerViewKt$MediaControllerView$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState $isDragging$delegate;
    final /* synthetic */ MutableFloatState $sliderPosition$delegate;
    final /* synthetic */ MediaUiState $state;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MediaControllerViewKt$MediaControllerView$1$1$1(MediaUiState mediaUiState, MutableState mutableState, MutableFloatState mutableFloatState, Continuation continuation) {
        super(2, continuation);
        this.$state = mediaUiState;
        this.$isDragging$delegate = mutableState;
        this.$sliderPosition$delegate = mutableFloatState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaControllerViewKt$MediaControllerView$1$1$1(this.$state, this.$isDragging$delegate, this.$sliderPosition$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((MediaControllerViewKt$MediaControllerView$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (!MediaControllerViewKt.MediaControllerView$lambda$2(this.$isDragging$delegate)) {
            this.$sliderPosition$delegate.setFloatValue(((MediaUiState.Ready) this.$state).getProgress());
        }
        return Unit.INSTANCE;
    }
}
