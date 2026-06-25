package com.android.systemui.statusbar;

import com.android.internal.statusbar.IStatusBarService;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationClickNotifier_Factory implements Factory {
    private final Provider backgroundExecutorProvider;
    private final Provider barServiceProvider;
    private final Provider mainExecutorProvider;

    public NotificationClickNotifier_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.barServiceProvider = provider;
        this.mainExecutorProvider = provider2;
        this.backgroundExecutorProvider = provider3;
    }

    public static NotificationClickNotifier_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new NotificationClickNotifier_Factory(provider, provider2, provider3);
    }

    public static NotificationClickNotifier newInstance(IStatusBarService iStatusBarService, Executor executor, Executor executor2) {
        return new NotificationClickNotifier(iStatusBarService, executor, executor2);
    }

    @Override // javax.inject.Provider
    public NotificationClickNotifier get() {
        return newInstance((IStatusBarService) this.barServiceProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.backgroundExecutorProvider.get());
    }
}
