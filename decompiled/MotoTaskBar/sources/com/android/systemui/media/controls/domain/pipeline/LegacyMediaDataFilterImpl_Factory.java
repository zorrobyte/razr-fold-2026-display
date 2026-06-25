package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class LegacyMediaDataFilterImpl_Factory implements Factory {
    private final Provider broadcastSenderProvider;
    private final Provider contextProvider;
    private final Provider executorProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider loggerProvider;
    private final Provider mediaFlagsProvider;
    private final Provider systemClockProvider;
    private final Provider userTrackerProvider;

    public LegacyMediaDataFilterImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.broadcastSenderProvider = provider3;
        this.lockscreenUserManagerProvider = provider4;
        this.executorProvider = provider5;
        this.systemClockProvider = provider6;
        this.loggerProvider = provider7;
        this.mediaFlagsProvider = provider8;
    }

    public static LegacyMediaDataFilterImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new LegacyMediaDataFilterImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static LegacyMediaDataFilterImpl newInstance(Context context, UserTracker userTracker, BroadcastSender broadcastSender, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, MediaFlags mediaFlags) {
        return new LegacyMediaDataFilterImpl(context, userTracker, broadcastSender, notificationLockscreenUserManager, executor, systemClock, mediaUiEventLogger, mediaFlags);
    }

    @Override // javax.inject.Provider
    public LegacyMediaDataFilterImpl get() {
        return newInstance((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (Executor) this.executorProvider.get(), (SystemClock) this.systemClockProvider.get(), (MediaUiEventLogger) this.loggerProvider.get(), (MediaFlags) this.mediaFlagsProvider.get());
    }
}
