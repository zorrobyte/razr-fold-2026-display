package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import com.android.systemui.DejankUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationClicker implements View.OnClickListener {
    private final NotificationClickerLogger mLogger;
    private final NotificationActivityStarter mNotificationActivityStarter;
    private ExpandableNotificationRow.OnDragSuccessListener mOnDragSuccessListener;

    public class Builder {
        private final NotificationClickerLogger mLogger;

        public Builder(NotificationClickerLogger notificationClickerLogger) {
            this.mLogger = notificationClickerLogger;
        }

        public NotificationClicker build(NotificationActivityStarter notificationActivityStarter) {
            return new NotificationClicker(this.mLogger, notificationActivityStarter);
        }
    }

    private NotificationClicker(NotificationClickerLogger notificationClickerLogger, NotificationActivityStarter notificationActivityStarter) {
        this.mOnDragSuccessListener = new ExpandableNotificationRow.OnDragSuccessListener() { // from class: com.android.systemui.statusbar.notification.NotificationClicker.1
            @Override // com.android.systemui.statusbar.notification.row.ExpandableNotificationRow.OnDragSuccessListener
            public void onDragSuccess(NotificationEntry notificationEntry) {
                NotificationClicker.this.mNotificationActivityStarter.onDragSuccess(notificationEntry);
            }
        };
        this.mLogger = notificationClickerLogger;
        this.mNotificationActivityStarter = notificationActivityStarter;
    }

    private boolean isMenuVisible(ExpandableNotificationRow expandableNotificationRow) {
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            Log.e("NotificationClicker", "NotificationClicker called on a view that is not a notification row.");
            return;
        }
        final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        NotificationEntry entry = expandableNotificationRow.getEntry();
        this.mLogger.logOnClick(entry);
        if (isMenuVisible(expandableNotificationRow)) {
            this.mLogger.logMenuVisible(entry);
            expandableNotificationRow.animateResetTranslation();
            return;
        }
        if (expandableNotificationRow.isChildInGroup() && isMenuVisible(expandableNotificationRow.getNotificationParent())) {
            this.mLogger.logParentMenuVisible(entry);
            expandableNotificationRow.getNotificationParent().animateResetTranslation();
        } else {
            if (expandableNotificationRow.isSummaryWithChildren() && expandableNotificationRow.areChildrenExpanded()) {
                this.mLogger.logChildrenExpanded(entry);
                return;
            }
            expandableNotificationRow.setJustClicked(true);
            DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    expandableNotificationRow.setJustClicked(false);
                }
            });
            this.mNotificationActivityStarter.onNotificationClicked(entry, expandableNotificationRow);
        }
    }

    public void register(ExpandableNotificationRow expandableNotificationRow, StatusBarNotification statusBarNotification) {
        Notification notification = statusBarNotification.getNotification();
        if (notification.contentIntent == null && notification.fullScreenIntent == null && !expandableNotificationRow.getEntry().isBubble()) {
            expandableNotificationRow.setOnClickListener(null);
            expandableNotificationRow.setOnDragSuccessListener(null);
        } else {
            expandableNotificationRow.setOnClickListener(this);
            expandableNotificationRow.setOnDragSuccessListener(this.mOnDragSuccessListener);
        }
    }
}
