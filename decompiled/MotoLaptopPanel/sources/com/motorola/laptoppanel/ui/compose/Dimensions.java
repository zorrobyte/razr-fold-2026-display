package com.motorola.laptoppanel.ui.compose;

import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Dimensions {
    private static final float IconLeftPadding;
    private static final long IconSize;
    private static final long SliderBackgroundFrameSize;
    private static final float SliderBackgroundRoundedCorner;
    private static final float SliderTrackRoundedCorner;
    private static final float ThumbTrackGapSize;
    public static final Dimensions INSTANCE = new Dimensions();
    private static final float IconPadding = Dp.m1877constructorimpl(8);

    static {
        float f = 6;
        SliderBackgroundFrameSize = DpKt.m1886DpSizeYgX7TsA(Dp.m1877constructorimpl(f), Dp.m1877constructorimpl(f));
        float f2 = 24;
        SliderBackgroundRoundedCorner = Dp.m1877constructorimpl(f2);
        float f3 = 12;
        SliderTrackRoundedCorner = Dp.m1877constructorimpl(f3);
        IconSize = DpKt.m1886DpSizeYgX7TsA(Dp.m1877constructorimpl(f2), Dp.m1877constructorimpl(f2));
        IconLeftPadding = Dp.m1877constructorimpl(f3);
        ThumbTrackGapSize = Dp.m1877constructorimpl(f);
    }

    private Dimensions() {
    }

    /* JADX INFO: renamed from: getIconLeftPadding-D9Ej5fM, reason: not valid java name */
    public final float m2142getIconLeftPaddingD9Ej5fM() {
        return IconLeftPadding;
    }

    /* JADX INFO: renamed from: getIconPadding-D9Ej5fM, reason: not valid java name */
    public final float m2143getIconPaddingD9Ej5fM() {
        return IconPadding;
    }

    /* JADX INFO: renamed from: getIconSize-MYxV2XQ, reason: not valid java name */
    public final long m2144getIconSizeMYxV2XQ() {
        return IconSize;
    }

    /* JADX INFO: renamed from: getSliderBackgroundFrameSize-MYxV2XQ, reason: not valid java name */
    public final long m2145getSliderBackgroundFrameSizeMYxV2XQ() {
        return SliderBackgroundFrameSize;
    }

    /* JADX INFO: renamed from: getSliderBackgroundRoundedCorner-D9Ej5fM, reason: not valid java name */
    public final float m2146getSliderBackgroundRoundedCornerD9Ej5fM() {
        return SliderBackgroundRoundedCorner;
    }

    /* JADX INFO: renamed from: getSliderTrackRoundedCorner-D9Ej5fM, reason: not valid java name */
    public final float m2147getSliderTrackRoundedCornerD9Ej5fM() {
        return SliderTrackRoundedCorner;
    }

    /* JADX INFO: renamed from: getThumbTrackGapSize-D9Ej5fM, reason: not valid java name */
    public final float m2148getThumbTrackGapSizeD9Ej5fM() {
        return ThumbTrackGapSize;
    }
}
