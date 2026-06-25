package com.android.systemui.broadcast;

import android.content.Context;
import com.android.systemui.util.wakelock.WakeLock$Builder;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class BroadcastSender_Factory implements Factory {
    private final Provider bgExecutorProvider;
    private final Provider contextProvider;
    private final Provider wakeLockBuilderProvider;

    public BroadcastSender_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.wakeLockBuilderProvider = provider2;
        this.bgExecutorProvider = provider3;
    }

    public static BroadcastSender_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new BroadcastSender_Factory(provider, provider2, provider3);
    }

    public static BroadcastSender newInstance(Context context, WakeLock$Builder wakeLock$Builder, Executor executor) {
        return new BroadcastSender(context, wakeLock$Builder, executor);
    }

    @Override // javax.inject.Provider
    public BroadcastSender get() {
        return newInstance((Context) this.contextProvider.get(), (WakeLock$Builder) this.wakeLockBuilderProvider.get(), (Executor) this.bgExecutorProvider.get());
    }
}
