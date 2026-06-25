package com.android.systemui.statusbar.notification.icon;

import android.app.Notification;
import android.content.Context;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.NotificationContentDescription;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: IconBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class IconBuilder {
    private final Context context;

    public IconBuilder(Context context) {
        context.getClass();
        this.context = context;
    }

    public final StatusBarIconView createIconView(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return new StatusBarIconView(this.context, notificationEntry.getSbn().getPackageName() + "/0x" + Integer.toHexString(notificationEntry.getSbn().getId()), notificationEntry.getSbn());
    }

    public final CharSequence getIconContentDescription(Notification notification) {
        notification.getClass();
        return NotificationContentDescription.contentDescForNotification(this.context, notification);
    }
}
