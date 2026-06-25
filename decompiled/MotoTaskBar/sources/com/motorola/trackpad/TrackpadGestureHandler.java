package com.motorola.trackpad;

import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Dependency;
import com.motorola.extendscreenshot.ScreenshotController;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.model.HardwareDisplayController;
import com.motorola.taskbar.recent.RecentsController;
import com.motorola.taskbar.shortcut.ShortcutKeyDispatcher;
import com.motorola.taskbar.util.DebugConfig;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class TrackpadGestureHandler {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private static final Uri SECOND_DISPLAY_URI = Uri.parse("content://com.motorola.launcher3.secondarydisplay.settings");
    private final Context mContext;
    private final MotoFeature mMotoFeature;
    private final Vibrator mVibrator;

    public TrackpadGestureHandler(Context context, MotoFeature motoFeature) {
        this.mContext = context;
        this.mMotoFeature = motoFeature;
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
    }

    private boolean isInLauncher() {
        Bundle bundleCall = this.mContext.createContextAsUser(UserHandle.of(ActivityManager.getCurrentUser()), 0).getContentResolver().call(SECOND_DISPLAY_URI, "is_sdl_focused", (String) null, (Bundle) null);
        if (bundleCall != null) {
            return bundleCall.getBoolean("result");
        }
        return false;
    }

    public void handleGesture(int i) {
        int displayId = ((HardwareDisplayController) Dependency.get(HardwareDisplayController.class)).getDisplayId();
        boolean z = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 0;
        boolean z2 = DEBUG;
        if (z2) {
            Log.d("TrackpadGestureHandle", "handleGesture: " + i + " DisplayId :" + displayId + "isLTR :" + z);
        }
        if (i == 0 || !this.mMotoFeature.isValidHDMIOrWfdDisplay(displayId)) {
            return;
        }
        String displayChooserMode = ((TaskBarController) Dependency.get(TaskBarController.class)).getDisplayChooserMode(displayId);
        if (i == 1) {
            if (z) {
                ((QSNotificationPanelController) Dependency.get(QSNotificationPanelController.class)).requestShowNotificationPanel(displayId);
                return;
            } else {
                if ("normal".equals(displayChooserMode)) {
                    return;
                }
                ((ShortcutKeyDispatcher) Dependency.get(ShortcutKeyDispatcher.class)).handleAirRemoteMenu(displayId);
                return;
            }
        }
        if (i == 2) {
            if (!"normal".equals(displayChooserMode)) {
                ((ShortcutKeyDispatcher) Dependency.get(ShortcutKeyDispatcher.class)).handleAirRemoteHome(displayId);
                return;
            }
            boolean zIsInLauncher = isInLauncher();
            if (z2) {
                Log.d("TrackpadGestureHandle", "isInLauncher: " + zIsInLauncher);
            }
            if (zIsInLauncher) {
                ((ShortcutKeyDispatcher) Dependency.get(ShortcutKeyDispatcher.class)).handleAirRemoteMenu(displayId);
                return;
            } else {
                ((ShortcutKeyDispatcher) Dependency.get(ShortcutKeyDispatcher.class)).handleAirRemoteHome(displayId);
                return;
            }
        }
        if (i == 3) {
            if (!z) {
                ((QSNotificationPanelController) Dependency.get(QSNotificationPanelController.class)).requestShowNotificationPanel(displayId);
                return;
            } else {
                if ("normal".equals(displayChooserMode)) {
                    return;
                }
                ((ShortcutKeyDispatcher) Dependency.get(ShortcutKeyDispatcher.class)).handleAirRemoteMenu(displayId);
                return;
            }
        }
        if (i == 5) {
            this.mVibrator.vibrate(5L);
            ((ScreenshotController) Dependency.get(ScreenshotController.class)).takeScreenshot(4, displayId);
        } else {
            if (i != 6) {
                return;
            }
            this.mVibrator.vibrate(5L);
            ((RecentsController) Dependency.get(RecentsController.class)).toggleRecents(displayId, false);
        }
    }
}
