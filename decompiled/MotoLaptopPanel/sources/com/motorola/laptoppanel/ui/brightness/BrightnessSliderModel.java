package com.motorola.laptoppanel.ui.brightness;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.motorola.laptoppanel.R;
import com.motorola.laptoppanel.ui.compose.SliderModel;
import com.motorola.laptoppanel.ui.panel.LaptopPanelModel;
import com.motorola.laptoppanel.util.Logger;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: BrightnessSliderModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BrightnessSliderModel extends SliderModel {
    private final MutableStateFlow _tempBrightness;
    private final BrightnessHelper brightnessHelper;
    private final Function0 brightnessListener;
    private final StateFlow tempBrightness;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: BrightnessSliderModel.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ViewModelProvider.Factory provideFactory(final LaptopPanelModel laptopPanelModel, final Application application) {
            laptopPanelModel.getClass();
            application.getClass();
            return new ViewModelProvider.Factory() { // from class: com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel$Companion$provideFactory$1
                @Override // androidx.lifecycle.ViewModelProvider.Factory
                public ViewModel create(Class cls) {
                    cls.getClass();
                    if (cls.isAssignableFrom(BrightnessSliderModel.class)) {
                        return new BrightnessSliderModel(application, laptopPanelModel);
                    }
                    throw new IllegalArgumentException("Unknown ViewModel class");
                }
            };
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel$onDrag$1, reason: invalid class name */
    /* JADX INFO: compiled from: BrightnessSliderModel.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BrightnessSliderModel.this.onDrag(null, this);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BrightnessSliderModel(Application application, LaptopPanelModel laptopPanelModel) {
        super(application, laptopPanelModel);
        application.getClass();
        laptopPanelModel.getClass();
        MutableStateFlow MutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(-1.0f));
        this._tempBrightness = MutableStateFlow;
        this.tempBrightness = MutableStateFlow;
        BrightnessHelper companion = BrightnessHelper.Companion.getInstance(application);
        this.brightnessHelper = companion;
        Function0 function0 = new Function0() { // from class: com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return BrightnessSliderModel.brightnessListener$lambda$0(this.f$0);
            }
        };
        this.brightnessListener = function0;
        setMaxSliderValue(65535.0f);
        setMinSliderValue(0.0f);
        Logger.INSTANCE.d("BrightnessSliderModel", "minSliderValue = " + getMinSliderValue() + ", maxSliderValue = " + getMaxSliderValue(), new Object[0]);
        companion.registerBrightnessObserver(function0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit brightnessListener$lambda$0(BrightnessSliderModel brightnessSliderModel) {
        brightnessSliderModel.mo2156getCurrentSliderValue();
        return Unit.INSTANCE;
    }

    private final void setBrightness(float f) {
        this._tempBrightness.setValue(Float.valueOf(-1.0f));
        this.brightnessHelper.setBrightness(RangesKt.coerceIn(f, getMinSliderValue(), getMaxSliderValue()));
    }

    @Override // com.motorola.laptoppanel.ui.compose.SliderModel
    /* JADX INFO: renamed from: getCurrentSliderValue */
    public void mo2156getCurrentSliderValue() {
        getCurrentSliderValue().setValue(Float.valueOf(this.brightnessHelper.getCurrentBrightness()));
    }

    @Override // com.motorola.laptoppanel.ui.compose.SliderModel
    public int getIconForPercentage(float f) {
        return f <= 20.0f ? R.drawable.zz_moto_ic_brightness_in_bar_low : f >= 80.0f ? R.drawable.zz_moto_ic_brightness_in_bar_high : R.drawable.zz_moto_ic_brightness_in_bar_medium;
    }

    public final StateFlow getTempBrightness() {
        return this.tempBrightness;
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        Logger.INSTANCE.d("BrightnessSliderModel", "onCleared: Cleaning up.", new Object[0]);
        this.brightnessHelper.unregisterBrightnessObserver(this.brightnessListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.motorola.laptoppanel.ui.compose.SliderModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object onDrag(com.motorola.laptoppanel.ui.compose.SliderModel.Drag r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r6
            com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel$onDrag$1 r0 = (com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel$onDrag$1 r0 = new com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel$onDrag$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r4 = r0.L$1
            r5 = r4
            com.motorola.laptoppanel.ui.compose.SliderModel$Drag r5 = (com.motorola.laptoppanel.ui.compose.SliderModel.Drag) r5
            java.lang.Object r4 = r0.L$0
            com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel r4 = (com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4a
        L32:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3a:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r4
            r0.L$1 = r5
            r0.label = r3
            java.lang.Object r6 = super.onDrag(r5, r0)
            if (r6 != r1) goto L4a
            return r1
        L4a:
            boolean r6 = r5 instanceof com.motorola.laptoppanel.ui.compose.SliderModel.Drag.Dragging
            if (r6 == 0) goto L7e
            r6 = r5
            com.motorola.laptoppanel.ui.compose.SliderModel$Drag$Dragging r6 = (com.motorola.laptoppanel.ui.compose.SliderModel.Drag.Dragging) r6
            float r0 = r6.m2162unboximpl()
            kotlinx.coroutines.flow.MutableStateFlow r1 = r4.getCurrentSliderValue()
            java.lang.Object r1 = r1.getValue()
            java.lang.Number r1 = (java.lang.Number) r1
            float r1 = r1.floatValue()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 != 0) goto L76
            com.motorola.laptoppanel.util.Logger r4 = com.motorola.laptoppanel.util.Logger.INSTANCE
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r6 = "BrightnessSliderModel"
            java.lang.String r0 = "Same drag value, no operation!"
            r4.d(r6, r0, r5)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L76:
            float r6 = r6.m2162unboximpl()
            r4.setTemporaryBrightness(r6)
            goto L8c
        L7e:
            boolean r6 = r5 instanceof com.motorola.laptoppanel.ui.compose.SliderModel.Drag.Stopped
            if (r6 == 0) goto L9e
            r6 = r5
            com.motorola.laptoppanel.ui.compose.SliderModel$Drag$Stopped r6 = (com.motorola.laptoppanel.ui.compose.SliderModel.Drag.Stopped) r6
            float r6 = r6.m2168unboximpl()
            r4.setBrightness(r6)
        L8c:
            kotlinx.coroutines.flow.MutableStateFlow r4 = r4.getCurrentSliderValue()
            float r5 = r5.getDragValue()
            java.lang.Float r5 = kotlin.coroutines.jvm.internal.Boxing.boxFloat(r5)
            r4.setValue(r5)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L9e:
            kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.brightness.BrightnessSliderModel.onDrag(com.motorola.laptoppanel.ui.compose.SliderModel$Drag, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void setTemporaryBrightness(float f) {
        this._tempBrightness.setValue(Float.valueOf(this.brightnessHelper.getLinearBrightness(RangesKt.coerceIn(f, getMinSliderValue(), getMaxSliderValue()))));
        Logger.INSTANCE.v("BrightnessSliderModel", "Set TEMP brightness = " + this._tempBrightness.getValue(), new Object[0]);
    }
}
