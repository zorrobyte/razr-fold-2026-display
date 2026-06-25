package com.motorola.laptoppanel.ui.compose;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class SliderKt$Slider$iconResProvider$1$1 extends FunctionReferenceImpl implements Function2 {
    public static final SliderKt$Slider$iconResProvider$1$1 INSTANCE = new SliderKt$Slider$iconResProvider$1$1();

    SliderKt$Slider$iconResProvider$1$1() {
        super(2, SliderModel.class, "getIconForPercentage", "getIconForPercentage(F)I", 0);
    }

    public final Integer invoke(SliderModel sliderModel, float f) {
        sliderModel.getClass();
        return Integer.valueOf(sliderModel.getIconForPercentage(f));
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke((SliderModel) obj, ((Number) obj2).floatValue());
    }
}
