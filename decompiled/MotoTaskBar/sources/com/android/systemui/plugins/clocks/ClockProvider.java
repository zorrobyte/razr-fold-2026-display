package com.android.systemui.plugins.clocks;

import java.util.List;

/* JADX INFO: compiled from: ClockProviderPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ClockProvider {
    ClockController createClock(ClockSettings clockSettings);

    ClockPickerConfig getClockPickerConfig(ClockSettings clockSettings);

    List getClocks();

    void initialize(ClockMessageBuffers clockMessageBuffers);
}
