package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes.dex */
public final class MediaDataProcessor_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider applicationScopeProvider;
    private final Provider backgroundDispatcherProvider;
    private final Provider broadcastDispatcherProvider;
    private final Provider clockProvider;
    private final Provider contextProvider;
    private final Provider dumpManagerProvider;
    private final Provider foregroundExecutorProvider;
    private final Provider handlerProvider;
    private final Provider loggerProvider;
    private final Provider mediaControllerFactoryProvider;
    private final Provider mediaDataRepositoryProvider;
    private final Provider mediaFlagsProvider;
    private final Provider threadFactoryProvider;
    private final Provider uiExecutorProvider;

    public MediaDataProcessor_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15) {
        this.contextProvider = provider;
        this.applicationScopeProvider = provider2;
        this.backgroundDispatcherProvider = provider3;
        this.threadFactoryProvider = provider4;
        this.uiExecutorProvider = provider5;
        this.foregroundExecutorProvider = provider6;
        this.handlerProvider = provider7;
        this.mediaControllerFactoryProvider = provider8;
        this.dumpManagerProvider = provider9;
        this.broadcastDispatcherProvider = provider10;
        this.activityStarterProvider = provider11;
        this.clockProvider = provider12;
        this.mediaFlagsProvider = provider13;
        this.loggerProvider = provider14;
        this.mediaDataRepositoryProvider = provider15;
    }

    public static MediaDataProcessor_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15) {
        return new MediaDataProcessor_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15);
    }

    public static MediaDataProcessor newInstance(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, ThreadFactory threadFactory, Executor executor, DelayableExecutor delayableExecutor, Handler handler, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, ActivityStarter activityStarter, SystemClock systemClock, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, MediaDataRepository mediaDataRepository) {
        return new MediaDataProcessor(context, coroutineScope, coroutineDispatcher, threadFactory, executor, delayableExecutor, handler, mediaControllerFactory, dumpManager, broadcastDispatcher, activityStarter, systemClock, mediaFlags, mediaUiEventLogger, mediaDataRepository);
    }

    @Override // javax.inject.Provider
    public MediaDataProcessor get() {
        return newInstance((Context) this.contextProvider.get(), (CoroutineScope) this.applicationScopeProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (ThreadFactory) this.threadFactoryProvider.get(), (Executor) this.uiExecutorProvider.get(), (DelayableExecutor) this.foregroundExecutorProvider.get(), (Handler) this.handlerProvider.get(), (MediaControllerFactory) this.mediaControllerFactoryProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SystemClock) this.clockProvider.get(), (MediaFlags) this.mediaFlagsProvider.get(), (MediaUiEventLogger) this.loggerProvider.get(), (MediaDataRepository) this.mediaDataRepositoryProvider.get());
    }
}
