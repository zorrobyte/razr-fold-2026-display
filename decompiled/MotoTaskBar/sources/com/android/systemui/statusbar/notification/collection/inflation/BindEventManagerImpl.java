package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import java.util.Iterator;

/* JADX INFO: compiled from: BindEventManagerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BindEventManagerImpl extends BindEventManager {
    public final void notifyViewBound(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        Iterator<E> it = getListeners().iterator();
        while (it.hasNext()) {
            ((BindEventManager.Listener) it.next()).onViewBound(notificationEntry);
        }
    }
}
