package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.SparseArray;
import android.widget.RemoteViews;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NotifRemoteViewCacheImpl implements NotifRemoteViewCache {
    private final NotifCollectionListener mCollectionListener;
    private final Map mNotifCachedContentViews = new ArrayMap();

    NotifRemoteViewCacheImpl(CommonNotifCollection commonNotifCollection) {
        NotifCollectionListener notifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryCleanUp(NotificationEntry notificationEntry) {
                NotifRemoteViewCacheImpl.this.mNotifCachedContentViews.remove(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryInit(NotificationEntry notificationEntry) {
                NotifRemoteViewCacheImpl.this.mNotifCachedContentViews.put(notificationEntry, new SparseArray());
            }
        };
        this.mCollectionListener = notifCollectionListener;
        commonNotifCollection.addCollectionListener(notifCollectionListener);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewCache
    public void clearCache(NotificationEntry notificationEntry) {
        SparseArray sparseArray = (SparseArray) this.mNotifCachedContentViews.get(notificationEntry);
        if (sparseArray == null) {
            return;
        }
        sparseArray.clear();
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewCache
    public RemoteViews getCachedView(NotificationEntry notificationEntry, int i) {
        SparseArray sparseArray = (SparseArray) this.mNotifCachedContentViews.get(notificationEntry);
        if (sparseArray == null) {
            return null;
        }
        return (RemoteViews) sparseArray.get(i);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewCache
    public boolean hasCachedView(NotificationEntry notificationEntry, int i) {
        return getCachedView(notificationEntry, i) != null;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewCache
    public void putCachedView(NotificationEntry notificationEntry, int i, RemoteViews remoteViews) {
        SparseArray sparseArray = (SparseArray) this.mNotifCachedContentViews.get(notificationEntry);
        if (sparseArray == null) {
            return;
        }
        sparseArray.put(i, remoteViews);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewCache
    public void removeCachedView(NotificationEntry notificationEntry, int i) {
        SparseArray sparseArray = (SparseArray) this.mNotifCachedContentViews.get(notificationEntry);
        if (sparseArray == null) {
            return;
        }
        sparseArray.remove(i);
    }
}
