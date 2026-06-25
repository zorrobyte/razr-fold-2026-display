package com.android.systemui.statusbar.notification.collection.provider;

import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;

/* JADX INFO: compiled from: NotificationVisibilityProviderImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationVisibilityProviderImpl implements NotificationVisibilityProvider {
    private final ActiveNotificationsInteractor activeNotificationsInteractor;
    private final CommonNotifCollection notifCollection;
    private final NotifLiveDataStore notifDataStore;

    public NotificationVisibilityProviderImpl(ActiveNotificationsInteractor activeNotificationsInteractor, NotifLiveDataStore notifLiveDataStore, CommonNotifCollection commonNotifCollection) {
        activeNotificationsInteractor.getClass();
        notifLiveDataStore.getClass();
        commonNotifCollection.getClass();
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.notifDataStore = notifLiveDataStore;
        this.notifCollection = commonNotifCollection;
    }

    private final int getCount() {
        return Flags.notificationsLiveDataStoreRefactor() ? this.activeNotificationsInteractor.getAllNotificationsCountValue() : ((Number) this.notifDataStore.getActiveNotifCount().getValue()).intValue();
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider
    public NotificationVisibility.NotificationLocation getLocation(String str) {
        str.getClass();
        NotificationVisibility.NotificationLocation notificationLocation = NotificationLogger.getNotificationLocation(this.notifCollection.getEntry(str));
        notificationLocation.getClass();
        return notificationLocation;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider
    public NotificationVisibility obtain(NotificationEntry notificationEntry, boolean z) {
        notificationEntry.getClass();
        int count = getCount();
        int rank = notificationEntry.getRanking().getRank();
        boolean z2 = false;
        boolean z3 = notificationEntry.getRow() != null;
        NotificationVisibility.NotificationLocation notificationLocation = NotificationLogger.getNotificationLocation(notificationEntry);
        String key = notificationEntry.getKey();
        if (z && z3) {
            z2 = true;
        }
        NotificationVisibility notificationVisibilityObtain = NotificationVisibility.obtain(key, rank, count, z2, notificationLocation);
        notificationVisibilityObtain.getClass();
        return notificationVisibilityObtain;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider
    public NotificationVisibility obtain(String str, boolean z) {
        str.getClass();
        NotificationEntry entry = this.notifCollection.getEntry(str);
        if (entry != null) {
            return obtain(entry, z);
        }
        NotificationVisibility notificationVisibilityObtain = NotificationVisibility.obtain(str, -1, getCount(), false);
        notificationVisibilityObtain.getClass();
        return notificationVisibilityObtain;
    }
}
