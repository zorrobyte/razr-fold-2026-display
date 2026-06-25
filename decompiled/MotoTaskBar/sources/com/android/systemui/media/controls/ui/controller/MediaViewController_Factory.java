package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaViewController_Factory implements Factory {
    private final Provider configurationControllerProvider;
    private final Provider contextProvider;
    private final Provider globalSettingsProvider;
    private final Provider loggerProvider;
    private final Provider mainExecutorProvider;
    private final Provider mediaFlagsProvider;
    private final Provider mediaHostStatesManagerProvider;
    private final Provider seekBarViewModelProvider;

    public MediaViewController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.configurationControllerProvider = provider2;
        this.mediaHostStatesManagerProvider = provider3;
        this.loggerProvider = provider4;
        this.seekBarViewModelProvider = provider5;
        this.mainExecutorProvider = provider6;
        this.mediaFlagsProvider = provider7;
        this.globalSettingsProvider = provider8;
    }

    public static MediaViewController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new MediaViewController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static MediaViewController newInstance(Context context, ConfigurationController configurationController, MediaHostStatesManager mediaHostStatesManager, MediaViewLogger mediaViewLogger, SeekBarViewModel seekBarViewModel, DelayableExecutor delayableExecutor, MediaFlags mediaFlags, GlobalSettings globalSettings) {
        return new MediaViewController(context, configurationController, mediaHostStatesManager, mediaViewLogger, seekBarViewModel, delayableExecutor, mediaFlags, globalSettings);
    }

    @Override // javax.inject.Provider
    public MediaViewController get() {
        return newInstance((Context) this.contextProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (MediaHostStatesManager) this.mediaHostStatesManagerProvider.get(), (MediaViewLogger) this.loggerProvider.get(), (SeekBarViewModel) this.seekBarViewModelProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (MediaFlags) this.mediaFlagsProvider.get(), (GlobalSettings) this.globalSettingsProvider.get());
    }
}
