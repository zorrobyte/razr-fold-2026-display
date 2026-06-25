package com.android.systemui.statusbar.notification.row;

import androidx.collection.ArraySet;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class NotifInflationErrorManager {
    Set mErroredNotifs = new ArraySet();
    List mListeners = new ArrayList();

    public interface NotifInflationErrorListener {
        void onNotifInflationError(NotificationEntry notificationEntry, Exception exc);

        void onNotifInflationErrorCleared(NotificationEntry notificationEntry);
    }

    public void addInflationErrorListener(NotifInflationErrorListener notifInflationErrorListener) {
        this.mListeners.add(notifInflationErrorListener);
    }

    public void clearInflationError(NotificationEntry notificationEntry) {
        if (this.mErroredNotifs.contains(notificationEntry.getKey())) {
            this.mErroredNotifs.remove(notificationEntry.getKey());
            for (int i = 0; i < this.mListeners.size(); i++) {
                ((NotifInflationErrorListener) this.mListeners.get(i)).onNotifInflationErrorCleared(notificationEntry);
            }
        }
    }

    public void setInflationError(NotificationEntry notificationEntry, Exception exc) {
        this.mErroredNotifs.add(notificationEntry.getKey());
        for (int i = 0; i < this.mListeners.size(); i++) {
            ((NotifInflationErrorListener) this.mListeners.get(i)).onNotifInflationError(notificationEntry, exc);
        }
    }
}
