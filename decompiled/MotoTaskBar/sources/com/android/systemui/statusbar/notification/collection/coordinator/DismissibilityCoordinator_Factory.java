package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class DismissibilityCoordinator_Factory implements Factory {
    private final Provider providerProvider;

    public DismissibilityCoordinator_Factory(Provider provider) {
        this.providerProvider = provider;
    }

    public static DismissibilityCoordinator_Factory create(Provider provider) {
        return new DismissibilityCoordinator_Factory(provider);
    }

    public static DismissibilityCoordinator newInstance(NotificationDismissibilityProviderImpl notificationDismissibilityProviderImpl) {
        return new DismissibilityCoordinator(notificationDismissibilityProviderImpl);
    }

    @Override // javax.inject.Provider
    public DismissibilityCoordinator get() {
        return newInstance((NotificationDismissibilityProviderImpl) this.providerProvider.get());
    }
}
