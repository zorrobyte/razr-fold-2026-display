package com.motorola.laptoppanel.ui.brightness;

import android.view.Window;
import android.view.WindowManager;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: BrightnessSlider.kt */
/* JADX INFO: loaded from: classes.dex */
final class BrightnessSliderKt$BrightnessSlider$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State $desiredBrightness$delegate;
    final /* synthetic */ Window $window;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BrightnessSliderKt$BrightnessSlider$2$1(Window window, State state, Continuation continuation) {
        super(2, continuation);
        this.$window = window;
        this.$desiredBrightness$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BrightnessSliderKt$BrightnessSlider$2$1(this.$window, this.$desiredBrightness$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((BrightnessSliderKt$BrightnessSlider$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Window window = this.$window;
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (attributes.screenBrightness != BrightnessSliderKt.BrightnessSlider$lambda$1(this.$desiredBrightness$delegate)) {
                attributes.screenBrightness = BrightnessSliderKt.BrightnessSlider$lambda$1(this.$desiredBrightness$delegate);
                this.$window.setAttributes(attributes);
            }
        }
        return Unit.INSTANCE;
    }
}
