package com.android.systemui.statusbar.policy;

import android.net.Uri;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;

/* JADX INFO: loaded from: classes.dex */
public class RemoteInputUriController {
    private final NotifCollectionListener mInlineUriListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputUriController.1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            try {
                RemoteInputUriController.this.mStatusBarManagerService.clearInlineReplyUriPermissions(notificationEntry.getKey());
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    };
    private final IStatusBarService mStatusBarManagerService;

    public RemoteInputUriController(IStatusBarService iStatusBarService) {
        this.mStatusBarManagerService = iStatusBarService;
    }

    public void grantInlineReplyUriPermission(StatusBarNotification statusBarNotification, Uri uri) {
        try {
            this.mStatusBarManagerService.grantInlineReplyUriPermission(statusBarNotification.getKey(), uri, statusBarNotification.getUser(), statusBarNotification.getPackageName());
        } catch (Exception e) {
            Log.e("RemoteInputUriController", "Failed to grant URI permissions:" + e.getMessage(), e);
        }
    }
}
