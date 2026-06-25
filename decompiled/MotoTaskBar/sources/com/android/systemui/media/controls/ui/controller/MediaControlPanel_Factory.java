package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class MediaControlPanel_Factory implements Factory {
    private final Provider activityIntentHelperProvider;
    private final Provider activityStarterProvider;
    private final Provider backgroundExecutorProvider;
    private final Provider broadcastSenderProvider;
    private final Provider contextProvider;
    private final Provider globalSettingsProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider loggerProvider;
    private final Provider mainExecutorProvider;
    private final Provider mediaCarouselControllerProvider;
    private final Provider mediaDataManagerProvider;
    private final Provider mediaFlagsProvider;
    private final Provider mediaViewControllerProvider;
    private final Provider seekBarViewModelProvider;
    private final Provider systemClockProvider;

    public MediaControlPanel_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15) {
        this.contextProvider = provider;
        this.backgroundExecutorProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.activityStarterProvider = provider4;
        this.broadcastSenderProvider = provider5;
        this.mediaViewControllerProvider = provider6;
        this.seekBarViewModelProvider = provider7;
        this.mediaDataManagerProvider = provider8;
        this.mediaCarouselControllerProvider = provider9;
        this.systemClockProvider = provider10;
        this.loggerProvider = provider11;
        this.activityIntentHelperProvider = provider12;
        this.lockscreenUserManagerProvider = provider13;
        this.globalSettingsProvider = provider14;
        this.mediaFlagsProvider = provider15;
    }

    public static MediaControlPanel_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15) {
        return new MediaControlPanel_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15);
    }

    public static MediaControlPanel newInstance(Context context, Executor executor, DelayableExecutor delayableExecutor, ActivityStarter activityStarter, BroadcastSender broadcastSender, MediaViewController mediaViewController, SeekBarViewModel seekBarViewModel, Lazy lazy, MediaCarouselController mediaCarouselController, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger, ActivityIntentHelper activityIntentHelper, NotificationLockscreenUserManager notificationLockscreenUserManager, GlobalSettings globalSettings, MediaFlags mediaFlags) {
        return new MediaControlPanel(context, executor, delayableExecutor, activityStarter, broadcastSender, mediaViewController, seekBarViewModel, lazy, mediaCarouselController, systemClock, mediaUiEventLogger, activityIntentHelper, notificationLockscreenUserManager, globalSettings, mediaFlags);
    }

    @Override // javax.inject.Provider
    public MediaControlPanel get() {
        return newInstance((Context) this.contextProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get(), (MediaViewController) this.mediaViewControllerProvider.get(), (SeekBarViewModel) this.seekBarViewModelProvider.get(), DoubleCheck.lazy(this.mediaDataManagerProvider), (MediaCarouselController) this.mediaCarouselControllerProvider.get(), (SystemClock) this.systemClockProvider.get(), (MediaUiEventLogger) this.loggerProvider.get(), (ActivityIntentHelper) this.activityIntentHelperProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (MediaFlags) this.mediaFlagsProvider.get());
    }
}
