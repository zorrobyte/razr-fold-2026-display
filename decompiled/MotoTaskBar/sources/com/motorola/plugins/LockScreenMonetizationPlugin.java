package com.motorola.plugins;

import android.os.Bundle;
import com.motorola.plugin.sdk.annotations.ProvidesInterface;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes2.dex */
@ProvidesInterface(action = LockScreenMonetizationPlugin.ACTION, version = 2)
public interface LockScreenMonetizationPlugin extends ViewTypePlugin {
    public static final String ACTION = "com.motorola.plugin.action.LOCK_SCREEN_MONETIZATION";
    public static final int EVENT_SWIPE_LEFT = 1;
    public static final int EVENT_SWIPE_RIGHT = 2;
    public static final int EVENT_TAP = 0;
    public static final int STYLE_AOD = 2;
    public static final int STYLE_DOZE = 1;
    public static final int STYLE_LOCKED = 0;
    public static final int STYLE_SUPPRESSED = 3;
    public static final int VERSION = 2;

    @FunctionalInterface
    public interface MonetizationAttributesChangedCallback {
        void onMonetizationAttributesChanged(MonetizationAttributes monetizationAttributes);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MonetizationEvent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MonetizationStyle {
    }

    void bindExtras(Bundle bundle);

    MonetizationAttributes getAttributes();

    void notifyEvent(int i);

    void onLSWallpaperChanged(boolean z);

    void setBlur(boolean z);

    void setCurrentMonetizationStyle(int i);

    void setOnAttributesChangedCallback(MonetizationAttributesChangedCallback monetizationAttributesChangedCallback);

    void setUnlocked(boolean z);
}
