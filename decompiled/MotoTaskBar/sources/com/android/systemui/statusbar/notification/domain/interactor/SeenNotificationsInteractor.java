package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: SeenNotificationsInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SeenNotificationsInteractor {
    private final StateFlow hasFilteredOutSeenNotifications;
    private final ActiveNotificationListRepository notificationListRepository;

    public SeenNotificationsInteractor(ActiveNotificationListRepository activeNotificationListRepository) {
        activeNotificationListRepository.getClass();
        this.notificationListRepository = activeNotificationListRepository;
        this.hasFilteredOutSeenNotifications = activeNotificationListRepository.getHasFilteredOutSeenNotifications();
    }

    public final StateFlow getHasFilteredOutSeenNotifications() {
        return this.hasFilteredOutSeenNotifications;
    }
}
