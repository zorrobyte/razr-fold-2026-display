package com.android.systemui.plugins.statusbar;

import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* JADX INFO: loaded from: classes.dex */
@ProvidesInterface(version = 1)
@DependsOn(target = StateListener.class)
public interface StatusBarStateController {
    public static final int VERSION = 1;

    @ProvidesInterface(version = 1)
    public interface StateListener {
        public static final int VERSION = 1;

        default void onDozeAmountChanged(float f, float f2) {
        }

        default void onDozingChanged(boolean z) {
        }

        default void onDreamingChanged(boolean z) {
        }

        default void onExpandedChanged(boolean z) {
        }

        default void onPulsingAmountChanged(float f, float f2) {
        }

        default void onPulsingChanged(boolean z) {
        }

        default void onStateChanged(int i) {
        }

        default void onStatePostChange() {
        }

        default void onStatePreChange(int i, int i2) {
        }

        default void onUpcomingStateChanged(int i) {
        }
    }

    void addCallback(StateListener stateListener);

    float getDozeAmount();

    float getPulsingAmount();

    int getState();

    boolean isDozing();

    boolean isDreaming();

    boolean isExpanded();

    boolean isPulsing();

    void removeCallback(StateListener stateListener);
}
