package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Optional;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStatsLoggerCoordinator_Factory implements Factory {
    private final Provider loggerOptionalProvider;

    public NotificationStatsLoggerCoordinator_Factory(Provider provider) {
        this.loggerOptionalProvider = provider;
    }

    public static NotificationStatsLoggerCoordinator_Factory create(Provider provider) {
        return new NotificationStatsLoggerCoordinator_Factory(provider);
    }

    public static NotificationStatsLoggerCoordinator newInstance(Optional optional) {
        return new NotificationStatsLoggerCoordinator(optional);
    }

    @Override // javax.inject.Provider
    public NotificationStatsLoggerCoordinator get() {
        return newInstance((Optional) this.loggerOptionalProvider.get());
    }
}
