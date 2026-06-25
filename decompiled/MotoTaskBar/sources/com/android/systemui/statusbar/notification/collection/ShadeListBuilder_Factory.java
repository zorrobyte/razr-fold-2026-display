package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationInteractionTracker;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ShadeListBuilder_Factory implements Factory {
    private final Provider dumpManagerProvider;
    private final Provider flagsProvider;
    private final Provider interactionTrackerProvider;
    private final Provider loggerProvider;
    private final Provider pipelineChoreographerProvider;
    private final Provider systemClockProvider;

    public ShadeListBuilder_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.dumpManagerProvider = provider;
        this.pipelineChoreographerProvider = provider2;
        this.flagsProvider = provider3;
        this.interactionTrackerProvider = provider4;
        this.loggerProvider = provider5;
        this.systemClockProvider = provider6;
    }

    public static ShadeListBuilder_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new ShadeListBuilder_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static ShadeListBuilder newInstance(DumpManager dumpManager, NotifPipelineChoreographer notifPipelineChoreographer, NotifPipelineFlags notifPipelineFlags, NotificationInteractionTracker notificationInteractionTracker, ShadeListBuilderLogger shadeListBuilderLogger, SystemClock systemClock) {
        return new ShadeListBuilder(dumpManager, notifPipelineChoreographer, notifPipelineFlags, notificationInteractionTracker, shadeListBuilderLogger, systemClock);
    }

    @Override // javax.inject.Provider
    public ShadeListBuilder get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (NotifPipelineChoreographer) this.pipelineChoreographerProvider.get(), (NotifPipelineFlags) this.flagsProvider.get(), (NotificationInteractionTracker) this.interactionTrackerProvider.get(), (ShadeListBuilderLogger) this.loggerProvider.get(), (SystemClock) this.systemClockProvider.get());
    }
}
