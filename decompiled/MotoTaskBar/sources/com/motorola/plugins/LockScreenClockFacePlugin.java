package com.motorola.plugins;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.motorola.plugin.sdk.annotations.ProvidesInterface;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
@ProvidesInterface(action = LockScreenClockFacePlugin.ACTION, version = 5)
public interface LockScreenClockFacePlugin extends ViewTypePlugin {
    public static final String ACTION = "com.motorola.plugin.action.LOCK_SCREEN_CLOCK_FACE";
    public static final String ATTR_CLOCK_COLOR = "clock_color";
    public static final int STYLE_LOCKED = 1;
    public static final int STYLE_LOCKED_LOCK_SCREEN = 2;
    public static final int VERSION = 5;

    public interface LockScreenClockFaceCallback {
        void onAttributesChanged(Bundle bundle);
    }

    Bundle addExtraViews(Map map, Bundle bundle);

    void bindExtras(Bundle bundle);

    Bundle onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, ViewGroup viewGroup2, ViewGroup viewGroup3, Bundle bundle);

    void onTimeTick();

    Bundle removeExtraViews(List list, Bundle bundle);

    void setCallback(LockScreenClockFaceCallback lockScreenClockFaceCallback);

    void setCurrentClockFaceStyle(int i);
}
