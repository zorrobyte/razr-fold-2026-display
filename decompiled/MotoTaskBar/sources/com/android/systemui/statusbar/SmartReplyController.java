package com.android.systemui.statusbar;

import android.app.Notification;
import android.os.RemoteException;
import android.util.ArraySet;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class SmartReplyController implements Dumpable {
    private final IStatusBarService mBarService;
    private Callback mCallback;
    private final NotificationClickNotifier mClickNotifier;
    private final Set mSendingKeys = new ArraySet();
    private final NotificationVisibilityProvider mVisibilityProvider;

    public interface Callback {
        void onSmartReplySent(NotificationEntry notificationEntry, CharSequence charSequence);
    }

    public SmartReplyController(DumpManager dumpManager, NotificationVisibilityProvider notificationVisibilityProvider, IStatusBarService iStatusBarService, NotificationClickNotifier notificationClickNotifier) {
        this.mBarService = iStatusBarService;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mClickNotifier = notificationClickNotifier;
        dumpManager.registerDumpable(this);
    }

    public boolean isSendingSmartReply(String str) {
        return this.mSendingKeys.contains(str);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void smartActionClicked(NotificationEntry notificationEntry, int i, Notification.Action action, boolean z) {
        this.mClickNotifier.onNotificationActionClick(notificationEntry.getKey(), i, action, this.mVisibilityProvider.obtain(notificationEntry, true), z);
    }

    public void smartReplySent(NotificationEntry notificationEntry, int i, CharSequence charSequence, int i2, boolean z) {
        this.mCallback.onSmartReplySent(notificationEntry, charSequence);
        this.mSendingKeys.add(notificationEntry.getKey());
        try {
            this.mBarService.onNotificationSmartReplySent(notificationEntry.getSbn().getKey(), i, charSequence, i2, z);
        } catch (RemoteException unused) {
        }
    }

    public void smartSuggestionsAdded(NotificationEntry notificationEntry, int i, int i2, boolean z, boolean z2) {
        try {
            this.mBarService.onNotificationSmartSuggestionsAdded(notificationEntry.getSbn().getKey(), i, i2, z, z2);
        } catch (RemoteException unused) {
        }
    }

    public void stopSending(NotificationEntry notificationEntry) {
        if (notificationEntry != null) {
            this.mSendingKeys.remove(notificationEntry.getSbn().getKey());
        }
    }
}
