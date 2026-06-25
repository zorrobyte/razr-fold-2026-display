package com.android.systemui.statusbar.policy;

import android.content.res.Configuration;

/* JADX INFO: loaded from: classes.dex */
public interface ConfigurationController extends CallbackController {

    public interface ConfigurationListener {
        default void onConfigChanged(Configuration configuration) {
        }

        default void onDensityOrFontScaleChanged() {
        }

        default void onLayoutDirectionChanged(boolean z) {
        }

        default void onLocaleListChanged() {
        }

        default void onMaxBoundsChanged() {
        }

        default void onOrientationChanged(int i) {
        }

        default void onSmallestScreenWidthChanged() {
        }

        default void onThemeChanged() {
        }

        default void onUiModeChanged() {
        }
    }

    String getNightModeName();

    void onConfigurationChanged(Configuration configuration);
}
