package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ViewConfigCoordinator_Factory implements Factory {
    private final Provider colorUpdateLoggerProvider;
    private final Provider mConfigurationControllerProvider;
    private final Provider mLockscreenUserManagerProvider;

    public ViewConfigCoordinator_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.mConfigurationControllerProvider = provider;
        this.mLockscreenUserManagerProvider = provider2;
        this.colorUpdateLoggerProvider = provider3;
    }

    public static ViewConfigCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ViewConfigCoordinator_Factory(provider, provider2, provider3);
    }

    public static ViewConfigCoordinator newInstance(ConfigurationController configurationController, NotificationLockscreenUserManager notificationLockscreenUserManager, ColorUpdateLogger colorUpdateLogger) {
        return new ViewConfigCoordinator(configurationController, notificationLockscreenUserManager, colorUpdateLogger);
    }

    @Override // javax.inject.Provider
    public ViewConfigCoordinator get() {
        return newInstance((ConfigurationController) this.mConfigurationControllerProvider.get(), (NotificationLockscreenUserManager) this.mLockscreenUserManagerProvider.get(), (ColorUpdateLogger) this.colorUpdateLoggerProvider.get());
    }
}
