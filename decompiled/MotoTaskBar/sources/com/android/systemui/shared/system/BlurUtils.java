package com.android.systemui.shared.system;

import android.app.ActivityManager;
import android.os.SystemProperties;
import android.view.CrossWindowBlurListeners;

/* JADX INFO: loaded from: classes.dex */
public abstract class BlurUtils {
    public static boolean supportsBlursOnWindows() {
        return CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED && ActivityManager.isHighEndGfx() && !SystemProperties.getBoolean("persist.sysui.disableBlur", false);
    }
}
