package com.android.systemui.plugins.clocks;

import android.graphics.Rect;
import kotlin.Deprecated;

/* JADX INFO: compiled from: ClockFaceEvents.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ClockFaceEvents {
    void onFontSettingChanged(float f);

    void onSecondaryDisplayChanged(boolean z);

    @Deprecated
    void onTargetRegionChanged(Rect rect);

    void onThemeChanged(ThemeConfig themeConfig);

    void onTimeTick();
}
