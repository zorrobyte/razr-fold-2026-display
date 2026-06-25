package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationsModule_ProvideVisualInterruptionDecisionProviderFactory implements Factory {
    private final Provider newImplProvider;
    private final Provider oldImplProvider;

    public NotificationsModule_ProvideVisualInterruptionDecisionProviderFactory(Provider provider, Provider provider2) {
        this.oldImplProvider = provider;
        this.newImplProvider = provider2;
    }

    public static NotificationsModule_ProvideVisualInterruptionDecisionProviderFactory create(Provider provider, Provider provider2) {
        return new NotificationsModule_ProvideVisualInterruptionDecisionProviderFactory(provider, provider2);
    }

    public static VisualInterruptionDecisionProvider provideVisualInterruptionDecisionProvider(javax.inject.Provider provider, javax.inject.Provider provider2) {
        VisualInterruptionDecisionProvider visualInterruptionDecisionProviderProvideVisualInterruptionDecisionProvider = NotificationsModule.provideVisualInterruptionDecisionProvider(provider, provider2);
        visualInterruptionDecisionProviderProvideVisualInterruptionDecisionProvider.getClass();
        return visualInterruptionDecisionProviderProvideVisualInterruptionDecisionProvider;
    }

    @Override // javax.inject.Provider
    public VisualInterruptionDecisionProvider get() {
        return provideVisualInterruptionDecisionProvider(this.oldImplProvider, this.newImplProvider);
    }
}
