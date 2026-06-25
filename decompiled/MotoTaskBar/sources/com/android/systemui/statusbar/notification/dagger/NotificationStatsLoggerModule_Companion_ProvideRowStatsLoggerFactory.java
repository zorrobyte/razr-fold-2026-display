package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Optional;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStatsLoggerModule_Companion_ProvideRowStatsLoggerFactory implements Factory {
    private final Provider legacyLoggerOptionalProvider;
    private final Provider newProvider;

    public NotificationStatsLoggerModule_Companion_ProvideRowStatsLoggerFactory(Provider provider, Provider provider2) {
        this.newProvider = provider;
        this.legacyLoggerOptionalProvider = provider2;
    }

    public static NotificationStatsLoggerModule_Companion_ProvideRowStatsLoggerFactory create(Provider provider, Provider provider2) {
        return new NotificationStatsLoggerModule_Companion_ProvideRowStatsLoggerFactory(provider, provider2);
    }

    public static NotificationRowStatsLogger provideRowStatsLogger(javax.inject.Provider provider, Optional optional) {
        NotificationRowStatsLogger notificationRowStatsLoggerProvideRowStatsLogger = NotificationStatsLoggerModule.Companion.provideRowStatsLogger(provider, optional);
        notificationRowStatsLoggerProvideRowStatsLogger.getClass();
        return notificationRowStatsLoggerProvideRowStatsLogger;
    }

    @Override // javax.inject.Provider
    public NotificationRowStatsLogger get() {
        return provideRowStatsLogger(this.newProvider, (Optional) this.legacyLoggerOptionalProvider.get());
    }
}
