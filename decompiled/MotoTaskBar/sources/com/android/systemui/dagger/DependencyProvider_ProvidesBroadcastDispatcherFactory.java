package com.android.systemui.dagger;

import android.content.Context;
import android.os.Looper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class DependencyProvider_ProvidesBroadcastDispatcherFactory implements Factory {
    private final Provider backgroundExecutorProvider;
    private final Provider backgroundLooperProvider;
    private final Provider contextProvider;
    private final DependencyProvider module;

    public DependencyProvider_ProvidesBroadcastDispatcherFactory(DependencyProvider dependencyProvider, Provider provider, Provider provider2, Provider provider3) {
        this.module = dependencyProvider;
        this.contextProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.backgroundExecutorProvider = provider3;
    }

    public static DependencyProvider_ProvidesBroadcastDispatcherFactory create(DependencyProvider dependencyProvider, Provider provider, Provider provider2, Provider provider3) {
        return new DependencyProvider_ProvidesBroadcastDispatcherFactory(dependencyProvider, provider, provider2, provider3);
    }

    public static BroadcastDispatcher providesBroadcastDispatcher(DependencyProvider dependencyProvider, Context context, Looper looper, Executor executor) {
        BroadcastDispatcher broadcastDispatcherProvidesBroadcastDispatcher = dependencyProvider.providesBroadcastDispatcher(context, looper, executor);
        broadcastDispatcherProvidesBroadcastDispatcher.getClass();
        return broadcastDispatcherProvidesBroadcastDispatcher;
    }

    @Override // javax.inject.Provider
    public BroadcastDispatcher get() {
        return providesBroadcastDispatcher(this.module, (Context) this.contextProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Executor) this.backgroundExecutorProvider.get());
    }
}
