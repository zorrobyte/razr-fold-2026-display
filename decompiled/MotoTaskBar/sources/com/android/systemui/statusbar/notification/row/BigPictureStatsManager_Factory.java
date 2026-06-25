package com.android.systemui.statusbar.notification.row;

import com.android.internal.util.LatencyTracker;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* JADX INFO: loaded from: classes.dex */
public final class BigPictureStatsManager_Factory implements Factory {
    private final Provider dumpManagerProvider;
    private final Provider latencyTrackerProvider;
    private final Provider mainDispatcherProvider;

    public BigPictureStatsManager_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.latencyTrackerProvider = provider;
        this.mainDispatcherProvider = provider2;
        this.dumpManagerProvider = provider3;
    }

    public static BigPictureStatsManager_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new BigPictureStatsManager_Factory(provider, provider2, provider3);
    }

    public static BigPictureStatsManager newInstance(LatencyTracker latencyTracker, CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager) {
        return new BigPictureStatsManager(latencyTracker, coroutineDispatcher, dumpManager);
    }

    @Override // javax.inject.Provider
    public BigPictureStatsManager get() {
        return newInstance((LatencyTracker) this.latencyTrackerProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
