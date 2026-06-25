package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;

/* JADX INFO: loaded from: classes.dex */
public class ColorizedFgsCoordinator implements Coordinator {
    private final NotifSectioner mNotifSectioner = new NotifSectioner(this, "ColorizedSectioner", 3) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ColorizedFgsCoordinator.1
        private boolean isCall(NotificationEntry notificationEntry) {
            return notificationEntry.getImportance() > 1 && notificationEntry.getSbn().getNotification().isStyle(Notification.CallStyle.class);
        }

        private boolean isColorizedForegroundService(NotificationEntry notificationEntry) {
            Notification notification = notificationEntry.getSbn().getNotification();
            return notification.isForegroundService() && notification.isColorized() && notificationEntry.getImportance() > 1;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry != null) {
                return isColorizedForegroundService(representativeEntry) || isCall(representativeEntry);
            }
            return false;
        }
    };

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
    }

    public NotifSectioner getSectioner() {
        return this.mNotifSectioner;
    }
}
