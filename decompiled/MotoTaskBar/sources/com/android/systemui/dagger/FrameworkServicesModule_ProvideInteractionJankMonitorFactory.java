package com.android.systemui.dagger;

import com.android.internal.jank.InteractionJankMonitor;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideInteractionJankMonitorFactory implements Factory {

    abstract class InstanceHolder {
        static final FrameworkServicesModule_ProvideInteractionJankMonitorFactory INSTANCE = new FrameworkServicesModule_ProvideInteractionJankMonitorFactory();
    }

    public static FrameworkServicesModule_ProvideInteractionJankMonitorFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static InteractionJankMonitor provideInteractionJankMonitor() {
        InteractionJankMonitor interactionJankMonitorProvideInteractionJankMonitor = FrameworkServicesModule.provideInteractionJankMonitor();
        interactionJankMonitorProvideInteractionJankMonitor.getClass();
        return interactionJankMonitorProvideInteractionJankMonitor;
    }

    @Override // javax.inject.Provider
    public InteractionJankMonitor get() {
        return provideInteractionJankMonitor();
    }
}
