package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class AvalancheProvider_Factory implements Factory {
    private final Provider broadcastDispatcherProvider;
    private final Provider loggerProvider;

    public AvalancheProvider_Factory(Provider provider, Provider provider2) {
        this.broadcastDispatcherProvider = provider;
        this.loggerProvider = provider2;
    }

    public static AvalancheProvider_Factory create(Provider provider, Provider provider2) {
        return new AvalancheProvider_Factory(provider, provider2);
    }

    public static AvalancheProvider newInstance(BroadcastDispatcher broadcastDispatcher, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger) {
        return new AvalancheProvider(broadcastDispatcher, visualInterruptionDecisionLogger);
    }

    @Override // javax.inject.Provider
    public AvalancheProvider get() {
        return newInstance((BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (VisualInterruptionDecisionLogger) this.loggerProvider.get());
    }
}
