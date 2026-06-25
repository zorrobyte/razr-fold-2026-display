package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class VisualStabilityCoordinator_Factory implements Factory {
    private final Provider delayableExecutorProvider;
    private final Provider dumpManagerProvider;
    private final Provider headsUpManagerProvider;
    private final Provider javaAdapterProvider;
    private final Provider visibilityLocationProvider;
    private final Provider visualStabilityProvider;

    public VisualStabilityCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.delayableExecutorProvider = provider;
        this.dumpManagerProvider = provider2;
        this.headsUpManagerProvider = provider3;
        this.javaAdapterProvider = provider4;
        this.visibilityLocationProvider = provider5;
        this.visualStabilityProvider = provider6;
    }

    public static VisualStabilityCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new VisualStabilityCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static VisualStabilityCoordinator newInstance(DelayableExecutor delayableExecutor, DumpManager dumpManager, HeadsUpManager headsUpManager, JavaAdapter javaAdapter, VisibilityLocationProvider visibilityLocationProvider, VisualStabilityProvider visualStabilityProvider) {
        return new VisualStabilityCoordinator(delayableExecutor, dumpManager, headsUpManager, javaAdapter, visibilityLocationProvider, visualStabilityProvider);
    }

    @Override // javax.inject.Provider
    public VisualStabilityCoordinator get() {
        return newInstance((DelayableExecutor) this.delayableExecutorProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (JavaAdapter) this.javaAdapterProvider.get(), (VisibilityLocationProvider) this.visibilityLocationProvider.get(), (VisualStabilityProvider) this.visualStabilityProvider.get());
    }
}
