package com.motorola.taskbar.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import com.motorola.taskbar.R$string;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public abstract class NotificationChannelsIds {
    public static String ALERTS = "ALR";
    public static String GENERALS = "GENERALS";

    public static void createAll(Context context) {
        ((NotificationManager) context.getSystemService(NotificationManager.class)).createNotificationChannels(Arrays.asList(new NotificationChannel(ALERTS, context.getString(R$string.notification_channel_alerts), 4), new NotificationChannel(GENERALS, context.getString(R$string.notification_channel_general), 3)));
    }
}
