package com.motorola.taskbar.bar;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.Dependency;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.trackpad.ReadyForProxy;

/* JADX INFO: loaded from: classes2.dex */
public class ExternalDisplayModeManager {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private static final String TAG = ExternalDisplayModeManager.class.getSimpleName();
    private final Context mContext;

    public ExternalDisplayModeManager(Context context) {
        this.mContext = context;
    }

    public static boolean readIsDesktopEnabled(Context context) {
        int i = MotorolaSettings.Global.getInt(context.getContentResolver(), "extra_display_mode", 1);
        if (DEBUG) {
            Log.d(TAG, "readIsDesktopEnabled: " + i);
        }
        return i == 1 || i == 2;
    }

    public void setDesktopEnable(boolean z) {
        if (DEBUG) {
            Log.d(TAG, "setDesktopEnable: " + z);
        }
        MotorolaSettings.Global.putInt(this.mContext.getContentResolver(), "extra_display_mode", z ? 1 : 0);
    }

    public boolean startGameModeLauncher(Context context, String str, int i) {
        Intent intent;
        try {
            boolean zIsSupportMllOnSc = ((ReadyForProxy) Dependency.get(ReadyForProxy.class)).isSupportMllOnSc();
            PackageManager packageManager = context.getPackageManager();
            String str2 = zIsSupportMllOnSc ? "com.motorola.mobiledesktop" : "com.motorola.leanbacklauncher";
            packageManager.getPackageInfo(str2, 0);
            intent = new Intent("com.motorola.leanbacklauncher.desktop.LAUNCH_DASHBOARD");
            intent.setComponent(new ComponentName(str2, "com.motorola.leanbacklauncher.ui.DesktopModeLauncherActivity"));
            intent.putExtra("com.motorola.leanbacklauncher.desktopLaunchMode", str);
            if (DEBUG) {
                Log.d(TAG, "startGameModeLauncher leanbacklauncher: " + i);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            intent = new Intent("com.motorola.gamemode.desktop.LAUNCH_DASHBOARD");
            intent.setComponent(new ComponentName("com.motorola.gamemode", "com.motorola.gamemode.ui.desktopmode.DesktopModeLauncherActivity"));
            intent.putExtra("com.motorola.gamemode.desktopLaunchMode", str);
            if (DEBUG) {
                Log.d(TAG, "startGameModeLauncher gamemode: " + i);
            }
        }
        try {
            intent.addFlags(268435456);
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setLaunchDisplayId(i);
            this.mContext.startActivityAsUser(intent, activityOptionsMakeBasic.toBundle(), UserHandle.CURRENT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
