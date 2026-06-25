package com.android.systemui.dagger;

import android.content.Context;
import com.android.internal.util.LatencyTracker;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideLatencyTrackerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideLatencyTrackerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideLatencyTrackerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideLatencyTrackerFactory(provider);
    }

    public static LatencyTracker provideLatencyTracker(Context context) {
        LatencyTracker latencyTrackerProvideLatencyTracker = FrameworkServicesModule.provideLatencyTracker(context);
        latencyTrackerProvideLatencyTracker.getClass();
        return latencyTrackerProvideLatencyTracker;
    }

    @Override // javax.inject.Provider
    public LatencyTracker get() {
        return provideLatencyTracker((Context) this.contextProvider.get());
    }
}
