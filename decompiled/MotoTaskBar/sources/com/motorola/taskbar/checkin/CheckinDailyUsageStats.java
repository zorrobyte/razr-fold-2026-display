package com.motorola.taskbar.checkin;

import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes2.dex */
public abstract class CheckinDailyUsageStats {
    public static void update(Context context, String str) {
        Intent intent = new Intent("com.motorola.motocare.trigger.TASK_BAR");
        intent.putExtra("type", str);
        intent.setPackage("com.motorola.motocare");
        context.sendBroadcast(intent);
    }
}
