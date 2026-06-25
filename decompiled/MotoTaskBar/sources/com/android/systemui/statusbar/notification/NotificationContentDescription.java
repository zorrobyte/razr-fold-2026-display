package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.Context;
import com.android.systemui.res.R$string;

/* JADX INFO: compiled from: NotificationContentDescription.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class NotificationContentDescription {
    public static final CharSequence contentDescForNotification(Context context, Notification notification) {
        context.getClass();
        notification.getClass();
        String strLoadHeaderAppName = notification.loadHeaderAppName(context);
        if (strLoadHeaderAppName == null) {
            strLoadHeaderAppName = "";
        }
        String string = context.getString(R$string.accessibility_desc_notification_icon, strLoadHeaderAppName, "");
        string.getClass();
        return string;
    }
}
