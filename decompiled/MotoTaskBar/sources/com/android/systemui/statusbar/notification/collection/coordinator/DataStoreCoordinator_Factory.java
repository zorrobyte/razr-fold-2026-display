package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class DataStoreCoordinator_Factory implements Factory {
    private final Provider notifLiveDataStoreImplProvider;

    public DataStoreCoordinator_Factory(Provider provider) {
        this.notifLiveDataStoreImplProvider = provider;
    }

    public static DataStoreCoordinator_Factory create(Provider provider) {
        return new DataStoreCoordinator_Factory(provider);
    }

    public static DataStoreCoordinator newInstance(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        return new DataStoreCoordinator(notifLiveDataStoreImpl);
    }

    @Override // javax.inject.Provider
    public DataStoreCoordinator get() {
        return newInstance((NotifLiveDataStoreImpl) this.notifLiveDataStoreImplProvider.get());
    }
}
