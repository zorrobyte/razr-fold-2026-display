package com.motorola.taskbar;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.systemui.Dependency;
import com.motorola.taskbar.bar.ExternalModeChooserActivity;
import com.motorola.taskbar.onboard.OnBoardActivity;

/* JADX INFO: loaded from: classes2.dex */
public class BootCompletedReceiver extends BroadcastReceiver {
    public static void setComponentEnabled(Context context, ComponentName componentName, boolean z) {
        Log.d("TaskBar.BootCompletedReceiver", "setComponentEnabled: " + componentName + ", enabled: " + z);
        context.getPackageManager().setComponentEnabledSetting(componentName, z ? 1 : 2, 1);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            return;
        }
        Log.d("TaskBar.BootCompletedReceiver", "Receive ACTION_BOOT_COMPLETED in TaskBar");
        ((TaskBarServiceProxy) Dependency.get(TaskBarServiceProxy.class)).onBootComplete();
        if (ActivityManager.isLowRamDeviceStatic()) {
            setComponentEnabled(context, new ComponentName(context, (Class<?>) ExternalModeChooserActivity.class), false);
            setComponentEnabled(context, new ComponentName(context, (Class<?>) OnBoardActivity.class), false);
        }
    }
}
