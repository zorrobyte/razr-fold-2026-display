package com.motorola.taskbar;

import android.app.ActivityManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserManager;
import android.view.WindowManagerGlobal;
import com.android.internal.widget.LockPatternUtils;

/* JADX INFO: loaded from: classes2.dex */
public class LockScreenReceiver extends BroadcastReceiver {
    private void lockDevice(Context context, boolean z) {
        try {
            if (!new LockPatternUtils(context).isLockScreenDisabled(ActivityManager.getCurrentUser()) && !z) {
                WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                lockProfiles(context);
                return;
            }
            ((PowerManager) context.getSystemService("power")).goToSleep(SystemClock.uptimeMillis(), 0, 256);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lockProfiles(Context context) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        TrustManager trustManager = (TrustManager) context.getSystemService("trust");
        int currentUser = ActivityManager.getCurrentUser();
        for (int i : userManager.getEnabledProfileIds(currentUser)) {
            if (i != currentUser) {
                trustManager.setDeviceLockedForUser(i, true);
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        lockDevice(context, intent.getIntExtra("display_id", 0) == 0);
    }
}
