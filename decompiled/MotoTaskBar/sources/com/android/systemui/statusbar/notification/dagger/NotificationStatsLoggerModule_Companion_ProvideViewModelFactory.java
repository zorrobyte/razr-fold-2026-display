package com.android.systemui.statusbar.notification.dagger;

import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Optional;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStatsLoggerModule_Companion_ProvideViewModelFactory implements Factory {
    private final Provider providerProvider;

    public NotificationStatsLoggerModule_Companion_ProvideViewModelFactory(Provider provider) {
        this.providerProvider = provider;
    }

    public static NotificationStatsLoggerModule_Companion_ProvideViewModelFactory create(Provider provider) {
        return new NotificationStatsLoggerModule_Companion_ProvideViewModelFactory(provider);
    }

    public static Optional provideViewModel(javax.inject.Provider provider) {
        Optional optionalProvideViewModel = NotificationStatsLoggerModule.Companion.provideViewModel(provider);
        optionalProvideViewModel.getClass();
        return optionalProvideViewModel;
    }

    @Override // javax.inject.Provider
    public Optional get() {
        return provideViewModel(this.providerProvider);
    }
}
