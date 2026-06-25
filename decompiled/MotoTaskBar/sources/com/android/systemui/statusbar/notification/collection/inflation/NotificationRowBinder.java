package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;

/* JADX INFO: loaded from: classes.dex */
public interface NotificationRowBinder {
    void inflateViews(NotificationEntry notificationEntry, NotifInflater.Params params, NotificationRowContentBinder.InflationCallback inflationCallback);
}
