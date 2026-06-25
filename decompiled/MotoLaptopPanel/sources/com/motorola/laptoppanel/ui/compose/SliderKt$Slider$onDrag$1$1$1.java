package com.motorola.laptoppanel.ui.compose;

import com.motorola.laptoppanel.ui.compose.SliderModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
final class SliderKt$Slider$onDrag$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $it;
    final /* synthetic */ SliderModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SliderKt$Slider$onDrag$1$1$1(SliderModel sliderModel, float f, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = sliderModel;
        this.$it = f;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SliderKt$Slider$onDrag$1$1$1(this.$viewModel, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SliderKt$Slider$onDrag$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SliderModel sliderModel = this.$viewModel;
            SliderModel.Drag.Dragging draggingM2157boximpl = SliderModel.Drag.Dragging.m2157boximpl(SliderModel.Drag.Dragging.m2158constructorimpl(this.$it));
            this.label = 1;
            if (sliderModel.onDrag(draggingM2157boximpl, this) == coroutine_suspended) {
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
