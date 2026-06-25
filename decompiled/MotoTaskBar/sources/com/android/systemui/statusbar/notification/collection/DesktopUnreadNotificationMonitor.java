package com.android.systemui.statusbar.notification.collection;

import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class DesktopUnreadNotificationMonitor {
    private final Handler mBgHandler;
    private final NotificationListener mNotificationListener;
    private final ShadeListBuilder mShadeListBuilder;
    private final Set mOldNotifications = new HashSet();
    private final Set mNewNotifications = new HashSet();
    private final Set mVisibleNewNotifications = new HashSet();
    private UnReadNotificationListener mUnReadNotificationListener = null;
    private NotificationListener.NotificationHandler mNotificationHandler = new NotificationListener.NotificationHandler() { // from class: com.android.systemui.statusbar.notification.collection.DesktopUnreadNotificationMonitor.1
        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            if (DesktopUnreadNotificationMonitor.this.mOldNotifications.remove(statusBarNotification.getKey())) {
                return;
            }
            DesktopUnreadNotificationMonitor.this.mNewNotifications.add(statusBarNotification.getKey());
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
            DesktopUnreadNotificationMonitor.this.mOldNotifications.remove(statusBarNotification.getKey());
            DesktopUnreadNotificationMonitor.this.mNewNotifications.remove(statusBarNotification.getKey());
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public void onNotificationsInitialized() {
        }
    };
    private OnBeforeRenderListListener mOnBeforeRenderListListener = new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.DesktopUnreadNotificationMonitor.2
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
        public void onBeforeRenderList(List list) {
            DesktopUnreadNotificationMonitor.this.mVisibleNewNotifications.clear();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ListEntry listEntry = (ListEntry) it.next();
                if (listEntry instanceof GroupEntry) {
                    Iterator it2 = ((GroupEntry) listEntry).getChildren().iterator();
                    while (it2.hasNext()) {
                        DesktopUnreadNotificationMonitor.this.onNotificationEntryBeforeRender((NotificationEntry) it2.next());
                    }
                } else if (listEntry instanceof NotificationEntry) {
                    DesktopUnreadNotificationMonitor.this.onNotificationEntryBeforeRender((NotificationEntry) listEntry);
                }
            }
            DesktopUnreadNotificationMonitor.this.notifyUnReadNotificationSizeChanged();
        }
    };

    public interface UnReadNotificationListener {
        void onUnReadNotificationSizeChanged(int i);
    }

    public DesktopUnreadNotificationMonitor(ShadeListBuilder shadeListBuilder, NotificationListener notificationListener, Handler handler) {
        this.mShadeListBuilder = shadeListBuilder;
        this.mNotificationListener = notificationListener;
        this.mBgHandler = handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyUnReadNotificationSizeChanged$0() {
        UnReadNotificationListener unReadNotificationListener = this.mUnReadNotificationListener;
        if (unReadNotificationListener != null) {
            unReadNotificationListener.onUnReadNotificationSizeChanged(this.mVisibleNewNotifications.size());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyUnReadNotificationSizeChanged() {
        this.mBgHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.DesktopUnreadNotificationMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyUnReadNotificationSizeChanged$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotificationEntryBeforeRender(NotificationEntry notificationEntry) {
        if (this.mNewNotifications.contains(notificationEntry.getKey())) {
            this.mVisibleNewNotifications.add(notificationEntry.getKey());
        }
    }

    public void attach() {
        Log.d("DesktopUnreadNotificationMonitor", "attach: ");
        for (StatusBarNotification statusBarNotification : this.mNotificationListener.getActiveNotifications()) {
            this.mOldNotifications.add(statusBarNotification.getKey());
        }
        this.mNotificationListener.addNotificationHandler(this.mNotificationHandler);
        this.mShadeListBuilder.addOnBeforeRenderListListener(this.mOnBeforeRenderListListener);
    }

    public void detach() {
        Log.d("DesktopUnreadNotificationMonitor", "detach: ");
        this.mNotificationListener.removeNotificationHandle(this.mNotificationHandler);
    }

    public int getUnReadNotificationSize() {
        return this.mVisibleNewNotifications.size();
    }

    public void resetUnReadNotificationSize() {
        this.mVisibleNewNotifications.clear();
        this.mNewNotifications.clear();
        notifyUnReadNotificationSizeChanged();
    }

    public void setUnReadNotificationListener(UnReadNotificationListener unReadNotificationListener) {
        this.mUnReadNotificationListener = unReadNotificationListener;
    }
}
