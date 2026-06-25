package com.android.systemui.statusbar.notification.data.repository;

import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: ActiveNotificationListRepository.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ActiveNotificationListRepository {
    private final MutableStateFlow activeNotifications = StateFlowKt.MutableStateFlow(new ActiveNotificationsStore(null, null, null, null, 15, null));
    private final MutableStateFlow hasFilteredOutSeenNotifications = StateFlowKt.MutableStateFlow(Boolean.FALSE);
    private final MutableStateFlow notifStats = StateFlowKt.MutableStateFlow(NotifStats.Companion.getEmpty());

    public final MutableStateFlow getActiveNotifications() {
        return this.activeNotifications;
    }

    public final MutableStateFlow getHasFilteredOutSeenNotifications() {
        return this.hasFilteredOutSeenNotifications;
    }

    public final MutableStateFlow getNotifStats() {
        return this.notifStats;
    }
}
