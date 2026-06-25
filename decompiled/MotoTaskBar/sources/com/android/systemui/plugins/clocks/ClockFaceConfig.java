package com.android.systemui.plugins.clocks;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ClockConfig.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ClockFaceConfig {
    public static final int $stable = 0;
    private final boolean hasCustomPositionUpdatedAnimation;
    private final boolean hasCustomWeatherDataDisplay;
    private final boolean showStatusBarArea;
    private final ClockTickRate tickRate;
    private final boolean useCustomClockScene;

    public ClockFaceConfig() {
        this(null, false, false, false, false, 31, null);
    }

    public ClockFaceConfig(ClockTickRate clockTickRate, boolean z, boolean z2, boolean z3, boolean z4) {
        clockTickRate.getClass();
        this.tickRate = clockTickRate;
        this.hasCustomWeatherDataDisplay = z;
        this.hasCustomPositionUpdatedAnimation = z2;
        this.useCustomClockScene = z3;
        this.showStatusBarArea = z4;
    }

    public /* synthetic */ ClockFaceConfig(ClockTickRate clockTickRate, boolean z, boolean z2, boolean z3, boolean z4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? ClockTickRate.PER_MINUTE : clockTickRate, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? false : z3, (i & 16) != 0 ? true : z4);
    }

    public static /* synthetic */ ClockFaceConfig copy$default(ClockFaceConfig clockFaceConfig, ClockTickRate clockTickRate, boolean z, boolean z2, boolean z3, boolean z4, int i, Object obj) {
        if ((i & 1) != 0) {
            clockTickRate = clockFaceConfig.tickRate;
        }
        if ((i & 2) != 0) {
            z = clockFaceConfig.hasCustomWeatherDataDisplay;
        }
        if ((i & 4) != 0) {
            z2 = clockFaceConfig.hasCustomPositionUpdatedAnimation;
        }
        if ((i & 8) != 0) {
            z3 = clockFaceConfig.useCustomClockScene;
        }
        if ((i & 16) != 0) {
            z4 = clockFaceConfig.showStatusBarArea;
        }
        boolean z5 = z4;
        boolean z6 = z2;
        return clockFaceConfig.copy(clockTickRate, z, z6, z3, z5);
    }

    public final ClockTickRate component1() {
        return this.tickRate;
    }

    public final boolean component2() {
        return this.hasCustomWeatherDataDisplay;
    }

    public final boolean component3() {
        return this.hasCustomPositionUpdatedAnimation;
    }

    public final boolean component4() {
        return this.useCustomClockScene;
    }

    public final boolean component5() {
        return this.showStatusBarArea;
    }

    public final ClockFaceConfig copy(ClockTickRate clockTickRate, boolean z, boolean z2, boolean z3, boolean z4) {
        clockTickRate.getClass();
        return new ClockFaceConfig(clockTickRate, z, z2, z3, z4);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockFaceConfig)) {
            return false;
        }
        ClockFaceConfig clockFaceConfig = (ClockFaceConfig) obj;
        return this.tickRate == clockFaceConfig.tickRate && this.hasCustomWeatherDataDisplay == clockFaceConfig.hasCustomWeatherDataDisplay && this.hasCustomPositionUpdatedAnimation == clockFaceConfig.hasCustomPositionUpdatedAnimation && this.useCustomClockScene == clockFaceConfig.useCustomClockScene && this.showStatusBarArea == clockFaceConfig.showStatusBarArea;
    }

    public final boolean getHasCustomPositionUpdatedAnimation() {
        return this.hasCustomPositionUpdatedAnimation;
    }

    public final boolean getHasCustomWeatherDataDisplay() {
        return this.hasCustomWeatherDataDisplay;
    }

    public final boolean getShowStatusBarArea() {
        return this.showStatusBarArea;
    }

    public final ClockTickRate getTickRate() {
        return this.tickRate;
    }

    public final boolean getUseCustomClockScene() {
        return this.useCustomClockScene;
    }

    public int hashCode() {
        return (((((((this.tickRate.hashCode() * 31) + Boolean.hashCode(this.hasCustomWeatherDataDisplay)) * 31) + Boolean.hashCode(this.hasCustomPositionUpdatedAnimation)) * 31) + Boolean.hashCode(this.useCustomClockScene)) * 31) + Boolean.hashCode(this.showStatusBarArea);
    }

    public String toString() {
        return "ClockFaceConfig(tickRate=" + this.tickRate + ", hasCustomWeatherDataDisplay=" + this.hasCustomWeatherDataDisplay + ", hasCustomPositionUpdatedAnimation=" + this.hasCustomPositionUpdatedAnimation + ", useCustomClockScene=" + this.useCustomClockScene + ", showStatusBarArea=" + this.showStatusBarArea + ")";
    }
}
