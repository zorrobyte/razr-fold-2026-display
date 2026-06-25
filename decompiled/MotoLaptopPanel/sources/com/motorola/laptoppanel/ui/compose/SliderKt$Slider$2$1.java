package com.motorola.laptoppanel.ui.compose;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
final class SliderKt$Slider$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState $dragging$delegate;
    final /* synthetic */ MutableFloatState $localSliderValue$delegate;
    final /* synthetic */ State $sliderValue$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SliderKt$Slider$2$1(MutableState mutableState, State state, MutableFloatState mutableFloatState, Continuation continuation) {
        super(2, continuation);
        this.$dragging$delegate = mutableState;
        this.$sliderValue$delegate = state;
        this.$localSliderValue$delegate = mutableFloatState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SliderKt$Slider$2$1(this.$dragging$delegate, this.$sliderValue$delegate, this.$localSliderValue$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((SliderKt$Slider$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (!SliderKt.Slider$lambda$16(this.$dragging$delegate)) {
            this.$localSliderValue$delegate.setFloatValue(SliderKt.Slider$lambda$1(this.$sliderValue$delegate));
        }
        return Unit.INSTANCE;
    }
}
