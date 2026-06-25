package com.android.systemui.haptics.qs;

import android.os.VibrationEffect;

/* JADX INFO: compiled from: LongPressHapticBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LongPressHapticBuilder {
    public static final LongPressHapticBuilder INSTANCE = new LongPressHapticBuilder();

    private LongPressHapticBuilder() {
    }

    public final VibrationEffect createSnapEffect() {
        return VibrationEffect.startComposition().addPrimitive(1, 0.5f, 0).compose();
    }
}
