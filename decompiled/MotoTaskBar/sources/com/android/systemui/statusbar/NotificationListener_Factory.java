package com.android.systemui.statusbar;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationListener_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider mainExecutorProvider;
    private final Provider notificationManagerProvider;
    private final Provider systemClockProvider;

    public NotificationListener_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.notificationManagerProvider = provider2;
        this.systemClockProvider = provider3;
        this.mainExecutorProvider = provider4;
    }

    public static NotificationListener_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new NotificationListener_Factory(provider, provider2, provider3, provider4);
    }

    public static NotificationListener newInstance(Context context, NotificationManager notificationManager, SystemClock systemClock, Executor executor) {
        return new NotificationListener(context, notificationManager, systemClock, executor);
    }

    @Override // javax.inject.Provider
    public NotificationListener get() {
        return newInstance((Context) this.contextProvider.get(), (NotificationManager) this.notificationManagerProvider.get(), (SystemClock) this.systemClockProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}
