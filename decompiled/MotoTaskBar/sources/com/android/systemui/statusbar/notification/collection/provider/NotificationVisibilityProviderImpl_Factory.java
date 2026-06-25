package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationVisibilityProviderImpl_Factory implements Factory {
    private final Provider activeNotificationsInteractorProvider;
    private final Provider notifCollectionProvider;
    private final Provider notifDataStoreProvider;

    public NotificationVisibilityProviderImpl_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.activeNotificationsInteractorProvider = provider;
        this.notifDataStoreProvider = provider2;
        this.notifCollectionProvider = provider3;
    }

    public static NotificationVisibilityProviderImpl_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new NotificationVisibilityProviderImpl_Factory(provider, provider2, provider3);
    }

    public static NotificationVisibilityProviderImpl newInstance(ActiveNotificationsInteractor activeNotificationsInteractor, NotifLiveDataStore notifLiveDataStore, CommonNotifCollection commonNotifCollection) {
        return new NotificationVisibilityProviderImpl(activeNotificationsInteractor, notifLiveDataStore, commonNotifCollection);
    }

    @Override // javax.inject.Provider
    public NotificationVisibilityProviderImpl get() {
        return newInstance((ActiveNotificationsInteractor) this.activeNotificationsInteractorProvider.get(), (NotifLiveDataStore) this.notifDataStoreProvider.get(), (CommonNotifCollection) this.notifCollectionProvider.get());
    }
}
