package com.android.systemui.plugins.clocks;

/* JADX INFO: compiled from: ClockAnimations.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ClockAnimations {

    /* JADX INFO: compiled from: ClockAnimations.kt */
    public final class DefaultImpls {
        public static void aod(ClockAnimations clockAnimations, boolean z, boolean z2) {
        }
    }

    void aod(boolean z, boolean z2);

    void charge();

    void doze(float f);

    void enter();

    void fold(float f);

    void onFidgetTap(float f, float f2);

    void onFontAxesChanged(ClockAxisStyle clockAxisStyle);

    void onPickerCarouselSwiping(float f);

    void onPositionUpdated(float f, float f2);

    void onPositionUpdated(int i, int i2, float f);
}
