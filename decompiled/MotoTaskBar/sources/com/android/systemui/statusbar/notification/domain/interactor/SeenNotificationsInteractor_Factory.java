package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SeenNotificationsInteractor_Factory implements Factory {
    private final Provider notificationListRepositoryProvider;

    public SeenNotificationsInteractor_Factory(Provider provider) {
        this.notificationListRepositoryProvider = provider;
    }

    public static SeenNotificationsInteractor_Factory create(Provider provider) {
        return new SeenNotificationsInteractor_Factory(provider);
    }

    public static SeenNotificationsInteractor newInstance(ActiveNotificationListRepository activeNotificationListRepository) {
        return new SeenNotificationsInteractor(activeNotificationListRepository);
    }

    @Override // javax.inject.Provider
    public SeenNotificationsInteractor get() {
        return newInstance((ActiveNotificationListRepository) this.notificationListRepositoryProvider.get());
    }
}
