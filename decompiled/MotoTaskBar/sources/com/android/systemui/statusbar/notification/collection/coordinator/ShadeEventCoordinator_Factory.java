package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class ShadeEventCoordinator_Factory implements Factory {
    private final Provider mLoggerProvider;
    private final Provider mMainExecutorProvider;

    public ShadeEventCoordinator_Factory(Provider provider, Provider provider2) {
        this.mMainExecutorProvider = provider;
        this.mLoggerProvider = provider2;
    }

    public static ShadeEventCoordinator_Factory create(Provider provider, Provider provider2) {
        return new ShadeEventCoordinator_Factory(provider, provider2);
    }

    public static ShadeEventCoordinator newInstance(Executor executor, ShadeEventCoordinatorLogger shadeEventCoordinatorLogger) {
        return new ShadeEventCoordinator(executor, shadeEventCoordinatorLogger);
    }

    @Override // javax.inject.Provider
    public ShadeEventCoordinator get() {
        return newInstance((Executor) this.mMainExecutorProvider.get(), (ShadeEventCoordinatorLogger) this.mLoggerProvider.get());
    }
}
