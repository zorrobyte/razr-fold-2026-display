package com.android.systemui.statusbar.notification.collection;

import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.phone.CentralSurfaces;

/* JADX INFO: compiled from: TargetSdkResolver.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TargetSdkResolver {
    private final String TAG;
    private final Context context;

    public TargetSdkResolver(Context context) {
        context.getClass();
        this.context = context;
        this.TAG = "TargetSdkResolver";
    }

    private final ApplicationInfo getApplicationInfoFromExtras(Notification notification) {
        return (ApplicationInfo) notification.extras.getParcelable("android.appInfo", ApplicationInfo.class);
    }

    private final ApplicationInfo getApplicationInfoFromPackageManager(StatusBarNotification statusBarNotification) {
        try {
            return CentralSurfaces.getPackageManagerForUser(this.context, statusBarNotification.getUser().getIdentifier()).getApplicationInfo(statusBarNotification.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(this.TAG, "Failed looking up ApplicationInfo for " + statusBarNotification.getPackageName(), e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int resolveNotificationSdk(StatusBarNotification statusBarNotification) {
        Notification notification = statusBarNotification.getNotification();
        notification.getClass();
        ApplicationInfo applicationInfoFromExtras = getApplicationInfoFromExtras(notification);
        if (applicationInfoFromExtras == null) {
            applicationInfoFromExtras = getApplicationInfoFromPackageManager(statusBarNotification);
        }
        if (applicationInfoFromExtras != null) {
            return applicationInfoFromExtras.targetSdkVersion;
        }
        return 0;
    }

    public final void initialize(CommonNotifCollection commonNotifCollection) {
        commonNotifCollection.getClass();
        commonNotifCollection.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.TargetSdkResolver.initialize.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
                notificationEntry.getClass();
                statusBarNotification.getClass();
                notificationEntry.targetSdk = TargetSdkResolver.this.resolveNotificationSdk(statusBarNotification);
            }
        });
    }
}
