package com.android.systemui.media.controls.domain.resume;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class MediaResumeListener_Factory implements Factory {
    private final Provider backgroundExecutorProvider;
    private final Provider broadcastDispatcherProvider;
    private final Provider contextProvider;
    private final Provider dumpManagerProvider;
    private final Provider mainExecutorProvider;
    private final Provider mediaBrowserFactoryProvider;
    private final Provider mediaFlagsProvider;
    private final Provider systemClockProvider;
    private final Provider userTrackerProvider;

    public MediaResumeListener_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.userTrackerProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.backgroundExecutorProvider = provider5;
        this.mediaBrowserFactoryProvider = provider6;
        this.dumpManagerProvider = provider7;
        this.systemClockProvider = provider8;
        this.mediaFlagsProvider = provider9;
    }

    public static MediaResumeListener_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new MediaResumeListener_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static MediaResumeListener newInstance(Context context, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, Executor executor, Executor executor2, ResumeMediaBrowserFactory resumeMediaBrowserFactory, DumpManager dumpManager, SystemClock systemClock, MediaFlags mediaFlags) {
        return new MediaResumeListener(context, broadcastDispatcher, userTracker, executor, executor2, resumeMediaBrowserFactory, dumpManager, systemClock, mediaFlags);
    }

    @Override // javax.inject.Provider
    public MediaResumeListener get() {
        return newInstance((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (ResumeMediaBrowserFactory) this.mediaBrowserFactoryProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (SystemClock) this.systemClockProvider.get(), (MediaFlags) this.mediaFlagsProvider.get());
    }
}
