package com.motorola.laptoppanel.ui.compose;

import android.content.Context;
import androidx.compose.runtime.ProduceStateScope;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
final class SliderKt$Slider$painter$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ State $iconRes$delegate;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SliderKt$Slider$painter$2$1(Context context, State state, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$iconRes$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SliderKt$Slider$painter$2$1 sliderKt$Slider$painter$2$1 = new SliderKt$Slider$painter$2$1(this.$context, this.$iconRes$delegate, continuation);
        sliderKt$Slider$painter$2$1.L$0 = obj;
        return sliderKt$Slider$painter$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProduceStateScope produceStateScope, Continuation continuation) {
        return ((SliderKt$Slider$painter$2$1) create(produceStateScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((ProduceStateScope) this.L$0).setValue(SliderModel.Companion.vectorResToBitmapPainter(SliderKt.Slider$lambda$11(this.$iconRes$delegate), this.$context));
        return Unit.INSTANCE;
    }
}
