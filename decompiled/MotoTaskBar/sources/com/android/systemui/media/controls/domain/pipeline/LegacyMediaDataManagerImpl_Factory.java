package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class LegacyMediaDataManagerImpl_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider broadcastDispatcherProvider;
    private final Provider clockProvider;
    private final Provider contextProvider;
    private final Provider dumpManagerProvider;
    private final Provider foregroundExecutorProvider;
    private final Provider loggerProvider;
    private final Provider mediaControllerFactoryProvider;
    private final Provider mediaDataCombineLatestProvider;
    private final Provider mediaDataFilterProvider;
    private final Provider mediaDeviceManagerProvider;
    private final Provider mediaFlagsProvider;
    private final Provider mediaResumeListenerProvider;
    private final Provider mediaSessionBasedFilterProvider;
    private final Provider mediaTimeoutListenerProvider;
    private final Provider threadFactoryProvider;
    private final Provider uiExecutorProvider;

    public LegacyMediaDataManagerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
        this.contextProvider = provider;
        this.threadFactoryProvider = provider2;
        this.uiExecutorProvider = provider3;
        this.foregroundExecutorProvider = provider4;
        this.mediaControllerFactoryProvider = provider5;
        this.dumpManagerProvider = provider6;
        this.broadcastDispatcherProvider = provider7;
        this.mediaTimeoutListenerProvider = provider8;
        this.mediaResumeListenerProvider = provider9;
        this.mediaSessionBasedFilterProvider = provider10;
        this.mediaDeviceManagerProvider = provider11;
        this.mediaDataCombineLatestProvider = provider12;
        this.mediaDataFilterProvider = provider13;
        this.activityStarterProvider = provider14;
        this.clockProvider = provider15;
        this.mediaFlagsProvider = provider16;
        this.loggerProvider = provider17;
    }

    public static LegacyMediaDataManagerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
        return new LegacyMediaDataManagerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    public static LegacyMediaDataManagerImpl newInstance(Context context, ThreadFactory threadFactory, Executor executor, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, LegacyMediaDataFilterImpl legacyMediaDataFilterImpl, ActivityStarter activityStarter, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger) {
        return new LegacyMediaDataManagerImpl(context, threadFactory, executor, delayableExecutor, mediaControllerFactory, dumpManager, broadcastDispatcher, mediaTimeoutListener, mediaResumeListener, mediaSessionBasedFilter, mediaDeviceManager, mediaDataCombineLatest, legacyMediaDataFilterImpl, activityStarter, systemClock, mediaFlags, mediaUiEventLogger);
    }

    @Override // javax.inject.Provider
    public LegacyMediaDataManagerImpl get() {
        return newInstance((Context) this.contextProvider.get(), (ThreadFactory) this.threadFactoryProvider.get(), (Executor) this.uiExecutorProvider.get(), (DelayableExecutor) this.foregroundExecutorProvider.get(), (MediaControllerFactory) this.mediaControllerFactoryProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (MediaTimeoutListener) this.mediaTimeoutListenerProvider.get(), (MediaResumeListener) this.mediaResumeListenerProvider.get(), (MediaSessionBasedFilter) this.mediaSessionBasedFilterProvider.get(), (MediaDeviceManager) this.mediaDeviceManagerProvider.get(), (MediaDataCombineLatest) this.mediaDataCombineLatestProvider.get(), (LegacyMediaDataFilterImpl) this.mediaDataFilterProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SystemClock) this.clockProvider.get(), (MediaFlags) this.mediaFlagsProvider.get(), (MediaUiEventLogger) this.loggerProvider.get());
    }
}
