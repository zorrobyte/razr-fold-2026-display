package com.motorola.mobiledesktop.core;

import X.Q0;
import X.v0;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import com.motorola.android.provider.MotorolaSettings;

/* JADX INFO: loaded from: classes.dex */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null || !"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            return;
        }
        v0.a("BootCompletedReceiver", "receive ACTION_BOOT_COMPLETED");
        boolean z2 = Q0.f288a;
        try {
            int i2 = MotorolaSettings.Global.getInt(context.getContentResolver(), "extra_display_mode", 1);
            boolean zEquals = "1".equals(SystemProperties.get("ro.product.readyfor.lite"));
            v0.a("Utils", "setDefaultDisplayMode isLiteProduct:" + zEquals + ", display mode:" + i2);
            if (zEquals && i2 == 1) {
                MotorolaSettings.Global.putInt(context.getContentResolver(), "extra_display_mode", 0);
            }
        } catch (Exception e2) {
            v0.c("Utils", "setDefaultDisplayMode failed", e2);
        }
        Q0.c(context);
    }
}
