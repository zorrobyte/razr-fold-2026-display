package com.android.systemui.statusbar.data.repository;

import com.android.systemui.statusbar.NotificationRemoteInputManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputRepositoryImpl_Factory implements Factory {
    private final Provider notificationRemoteInputManagerProvider;

    public RemoteInputRepositoryImpl_Factory(Provider provider) {
        this.notificationRemoteInputManagerProvider = provider;
    }

    public static RemoteInputRepositoryImpl_Factory create(Provider provider) {
        return new RemoteInputRepositoryImpl_Factory(provider);
    }

    public static RemoteInputRepositoryImpl newInstance(NotificationRemoteInputManager notificationRemoteInputManager) {
        return new RemoteInputRepositoryImpl(notificationRemoteInputManager);
    }

    @Override // javax.inject.Provider
    public RemoteInputRepositoryImpl get() {
        return newInstance((NotificationRemoteInputManager) this.notificationRemoteInputManagerProvider.get());
    }
}
