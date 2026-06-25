package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineDispatcher;

/* JADX INFO: loaded from: classes.dex */
public final class MediaCarouselController_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider backgroundDispatcherProvider;
    private final Provider bgExecutorProvider;
    private final Provider configurationControllerProvider;
    private final Provider contextProvider;
    private final Provider debugLoggerProvider;
    private final Provider dumpManagerProvider;
    private final Provider executorProvider;
    private final Provider globalSettingsProvider;
    private final Provider loggerProvider;
    private final Provider mainDispatcherProvider;
    private final Provider mediaCarouselViewModelProvider;
    private final Provider mediaControlPanelFactoryProvider;
    private final Provider mediaFlagsProvider;
    private final Provider mediaHostStatesManagerProvider;
    private final Provider mediaManagerProvider;
    private final Provider mediaViewControllerFactoryProvider;
    private final Provider systemClockProvider;
    private final Provider visualStabilityProvider;

    public MediaCarouselController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19) {
        this.contextProvider = provider;
        this.mediaControlPanelFactoryProvider = provider2;
        this.visualStabilityProvider = provider3;
        this.mediaHostStatesManagerProvider = provider4;
        this.activityStarterProvider = provider5;
        this.systemClockProvider = provider6;
        this.mainDispatcherProvider = provider7;
        this.executorProvider = provider8;
        this.bgExecutorProvider = provider9;
        this.backgroundDispatcherProvider = provider10;
        this.mediaManagerProvider = provider11;
        this.configurationControllerProvider = provider12;
        this.dumpManagerProvider = provider13;
        this.loggerProvider = provider14;
        this.debugLoggerProvider = provider15;
        this.mediaFlagsProvider = provider16;
        this.globalSettingsProvider = provider17;
        this.mediaCarouselViewModelProvider = provider18;
        this.mediaViewControllerFactoryProvider = provider19;
    }

    public static MediaCarouselController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19) {
        return new MediaCarouselController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19);
    }

    public static MediaCarouselController newInstance(Context context, javax.inject.Provider provider, VisualStabilityProvider visualStabilityProvider, MediaHostStatesManager mediaHostStatesManager, ActivityStarter activityStarter, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, DelayableExecutor delayableExecutor, Executor executor, CoroutineDispatcher coroutineDispatcher2, MediaDataManager mediaDataManager, ConfigurationController configurationController, DumpManager dumpManager, MediaUiEventLogger mediaUiEventLogger, MediaCarouselControllerLogger mediaCarouselControllerLogger, MediaFlags mediaFlags, GlobalSettings globalSettings, MediaCarouselViewModel mediaCarouselViewModel, javax.inject.Provider provider2) {
        return new MediaCarouselController(context, provider, visualStabilityProvider, mediaHostStatesManager, activityStarter, systemClock, coroutineDispatcher, delayableExecutor, executor, coroutineDispatcher2, mediaDataManager, configurationController, dumpManager, mediaUiEventLogger, mediaCarouselControllerLogger, mediaFlags, globalSettings, mediaCarouselViewModel, provider2);
    }

    @Override // javax.inject.Provider
    public MediaCarouselController get() {
        return newInstance((Context) this.contextProvider.get(), this.mediaControlPanelFactoryProvider, (VisualStabilityProvider) this.visualStabilityProvider.get(), (MediaHostStatesManager) this.mediaHostStatesManagerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SystemClock) this.systemClockProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (DelayableExecutor) this.executorProvider.get(), (Executor) this.bgExecutorProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (MediaDataManager) this.mediaManagerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (MediaUiEventLogger) this.loggerProvider.get(), (MediaCarouselControllerLogger) this.debugLoggerProvider.get(), (MediaFlags) this.mediaFlagsProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (MediaCarouselViewModel) this.mediaCarouselViewModelProvider.get(), this.mediaViewControllerFactoryProvider);
    }
}
