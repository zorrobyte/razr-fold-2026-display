package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* JADX INFO: loaded from: classes.dex */
public final class ActiveNotificationsInteractor_Factory implements Factory {
    private final Provider backgroundDispatcherProvider;
    private final Provider repositoryProvider;

    public ActiveNotificationsInteractor_Factory(Provider provider, Provider provider2) {
        this.repositoryProvider = provider;
        this.backgroundDispatcherProvider = provider2;
    }

    public static ActiveNotificationsInteractor_Factory create(Provider provider, Provider provider2) {
        return new ActiveNotificationsInteractor_Factory(provider, provider2);
    }

    public static ActiveNotificationsInteractor newInstance(ActiveNotificationListRepository activeNotificationListRepository, CoroutineDispatcher coroutineDispatcher) {
        return new ActiveNotificationsInteractor(activeNotificationListRepository, coroutineDispatcher);
    }

    @Override // javax.inject.Provider
    public ActiveNotificationsInteractor get() {
        return newInstance((ActiveNotificationListRepository) this.repositoryProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get());
    }
}
