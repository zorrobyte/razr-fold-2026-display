package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class HideNotifsForOtherUsersCoordinator_Factory implements Factory {
    private final Provider lockscreenUserManagerProvider;

    public HideNotifsForOtherUsersCoordinator_Factory(Provider provider) {
        this.lockscreenUserManagerProvider = provider;
    }

    public static HideNotifsForOtherUsersCoordinator_Factory create(Provider provider) {
        return new HideNotifsForOtherUsersCoordinator_Factory(provider);
    }

    public static HideNotifsForOtherUsersCoordinator newInstance(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        return new HideNotifsForOtherUsersCoordinator(notificationLockscreenUserManager);
    }

    @Override // javax.inject.Provider
    public HideNotifsForOtherUsersCoordinator get() {
        return newInstance((NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get());
    }
}
