package com.android.systemui.statusbar.notification.row.dagger;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.BigPictureIconManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;

/* JADX INFO: loaded from: classes.dex */
public interface ExpandableNotificationRowComponent {

    public interface Builder {
        ExpandableNotificationRowComponent build();

        Builder expandableNotificationRow(ExpandableNotificationRow expandableNotificationRow);

        Builder notificationEntry(NotificationEntry notificationEntry);

        Builder onExpandClickListener(ExpandableNotificationRow.OnExpandClickListener onExpandClickListener);
    }

    public abstract class ExpandableNotificationRowModule {
        static String provideAppName(Context context, StatusBarNotification statusBarNotification) {
            PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(context, statusBarNotification.getUser().getIdentifier());
            String packageName = statusBarNotification.getPackageName();
            try {
                ApplicationInfo applicationInfo = packageManagerForUser.getApplicationInfo(packageName, 8704);
                if (applicationInfo != null) {
                    return String.valueOf(packageManagerForUser.getApplicationLabel(applicationInfo));
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
            return packageName;
        }

        static String provideNotificationKey(StatusBarNotification statusBarNotification) {
            return statusBarNotification.getKey();
        }

        static StatusBarNotification provideStatusBarNotification(NotificationEntry notificationEntry) {
            return notificationEntry.getSbn();
        }
    }

    BigPictureIconManager getBigPictureIconManager();

    ExpandableNotificationRowController getExpandableNotificationRowController();
}
