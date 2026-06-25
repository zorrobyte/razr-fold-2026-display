package com.motorola.plugins;

import android.os.Bundle;
import com.motorola.plugin.sdk.annotations.ProvidesInterface;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
@ProvidesInterface(action = ClockFacePlugin.ACTION, version = 6)
public interface ClockFacePlugin extends ViewTypePlugin {
    public static final String ACTION = "com.motorola.plugin.action.PLUGIN_CLOCK_FACE";
    public static final int STYLE_LOCKED = 1;
    public static final int STYLE_LOCKED_LOCK_SCREEN = 2;
    public static final int STYLE_UNLOCKED = 3;
    public static final int VERSION = 6;

    @FunctionalInterface
    public interface ClockFaceAttributesChangedCallback {
        void onClockFaceAttributesChanged(ClockFaceAttributes clockFaceAttributes);
    }

    void bindExtras(Bundle bundle);

    void bindNotification(List list);

    ClockFaceAttributes getAttributes();

    void onTimeTickUpdated();

    void setCurrentClockFaceStyle(int i);

    void setOnAttributesChangedCallback(ClockFaceAttributesChangedCallback clockFaceAttributesChangedCallback);
}
