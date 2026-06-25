package com.motorola.taskbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.Dependency;
import com.motorola.taskbar.checkin.CheckinDailyUsageStats;
import com.motorola.taskbar.model.HardwareDisplayController;

/* JADX INFO: loaded from: classes2.dex */
public class ModeChooserActivityReceiver extends BroadcastReceiver {
    public static void requestShowDesktopSplashScreen(Context context, int i) {
        HardwareDisplayController hardwareDisplayController = (HardwareDisplayController) Dependency.get(HardwareDisplayController.class);
        if (hardwareDisplayController == null) {
            return;
        }
        Log.d("ModeChooserActivityReceiver", "requestShowDesktopSplashScreen: " + i + " | " + hardwareDisplayController.getDisplayId());
        if (i == hardwareDisplayController.getDisplayId()) {
            ((TaskBarServiceProxy) Dependency.get(TaskBarServiceProxy.class)).requestShowDesktopSplashScreen();
        } else {
            Log.w("ModeChooserActivityReceiver", "requestShowDesktopSplashScreen failed splashScreenManager: ");
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        action.hashCode();
        if (action.equals("com.motorola.systemui.desk.ACTION_START_MODE_CHOOSER_ACTIVITY")) {
            int intExtra = intent.getIntExtra("DISPLAY_ID", 0);
            Log.d("ModeChooserActivityReceiver", "onReceive: " + intent.getAction() + " | " + intExtra);
            requestShowDesktopSplashScreen(context, intExtra);
            CheckinDailyUsageStats.update(context, "b_chooser");
        }
    }
}
