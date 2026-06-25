package com.android.systemui.statusbar.notification.dagger;

import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Optional;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory implements Factory {
    private final Provider providerProvider;

    public NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory(Provider provider) {
        this.providerProvider = provider;
    }

    public static NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory create(Provider provider) {
        return new NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory(provider);
    }

    public static Optional provideStatsLogger(javax.inject.Provider provider) {
        Optional optionalProvideStatsLogger = NotificationStatsLoggerModule.Companion.provideStatsLogger(provider);
        optionalProvideStatsLogger.getClass();
        return optionalProvideStatsLogger;
    }

    @Override // javax.inject.Provider
    public Optional get() {
        return provideStatsLogger(this.providerProvider);
    }
}
