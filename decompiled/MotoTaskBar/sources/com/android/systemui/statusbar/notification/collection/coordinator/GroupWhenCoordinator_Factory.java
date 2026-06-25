package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class GroupWhenCoordinator_Factory implements Factory {
    private final Provider delayableExecutorProvider;
    private final Provider systemClockProvider;

    public GroupWhenCoordinator_Factory(Provider provider, Provider provider2) {
        this.delayableExecutorProvider = provider;
        this.systemClockProvider = provider2;
    }

    public static GroupWhenCoordinator_Factory create(Provider provider, Provider provider2) {
        return new GroupWhenCoordinator_Factory(provider, provider2);
    }

    public static GroupWhenCoordinator newInstance(DelayableExecutor delayableExecutor, SystemClock systemClock) {
        return new GroupWhenCoordinator(delayableExecutor, systemClock);
    }

    @Override // javax.inject.Provider
    public GroupWhenCoordinator get() {
        return newInstance((DelayableExecutor) this.delayableExecutorProvider.get(), (SystemClock) this.systemClockProvider.get());
    }
}
