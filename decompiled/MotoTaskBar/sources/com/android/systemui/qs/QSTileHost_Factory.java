package com.android.systemui.qs;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class QSTileHost_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider defaultFactoryProvider;
    private final Provider mainExecutorProvider;
    private final Provider userTrackerProvider;

    public QSTileHost_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.defaultFactoryProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.userTrackerProvider = provider4;
    }

    public static QSTileHost_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new QSTileHost_Factory(provider, provider2, provider3, provider4);
    }

    public static QSTileHost newInstance(Context context, QSFactory qSFactory, Executor executor, UserTracker userTracker) {
        return new QSTileHost(context, qSFactory, executor, userTracker);
    }

    @Override // javax.inject.Provider
    public QSTileHost get() {
        return newInstance((Context) this.contextProvider.get(), (QSFactory) this.defaultFactoryProvider.get(), (Executor) this.mainExecutorProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
