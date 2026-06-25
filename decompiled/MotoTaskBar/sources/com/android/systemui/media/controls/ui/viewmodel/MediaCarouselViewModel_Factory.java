package com.android.systemui.media.controls.ui.viewmodel;

import android.content.Context;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes.dex */
public final class MediaCarouselViewModel_Factory implements Factory {
    private final Provider applicationContextProvider;
    private final Provider applicationScopeProvider;
    private final Provider backgroundDispatcherProvider;
    private final Provider backgroundExecutorProvider;
    private final Provider controlInteractorFactoryProvider;
    private final Provider interactorProvider;
    private final Provider loggerProvider;
    private final Provider mediaFlagsProvider;
    private final Provider visualStabilityProvider;

    public MediaCarouselViewModel_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.applicationScopeProvider = provider;
        this.applicationContextProvider = provider2;
        this.backgroundDispatcherProvider = provider3;
        this.backgroundExecutorProvider = provider4;
        this.visualStabilityProvider = provider5;
        this.interactorProvider = provider6;
        this.controlInteractorFactoryProvider = provider7;
        this.loggerProvider = provider8;
        this.mediaFlagsProvider = provider9;
    }

    public static MediaCarouselViewModel_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new MediaCarouselViewModel_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static MediaCarouselViewModel newInstance(CoroutineScope coroutineScope, Context context, CoroutineDispatcher coroutineDispatcher, Executor executor, VisualStabilityProvider visualStabilityProvider, MediaCarouselInteractor mediaCarouselInteractor, MediaControlInteractorFactory mediaControlInteractorFactory, MediaUiEventLogger mediaUiEventLogger, MediaFlags mediaFlags) {
        return new MediaCarouselViewModel(coroutineScope, context, coroutineDispatcher, executor, visualStabilityProvider, mediaCarouselInteractor, mediaControlInteractorFactory, mediaUiEventLogger, mediaFlags);
    }

    @Override // javax.inject.Provider
    public MediaCarouselViewModel get() {
        return newInstance((CoroutineScope) this.applicationScopeProvider.get(), (Context) this.applicationContextProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (VisualStabilityProvider) this.visualStabilityProvider.get(), (MediaCarouselInteractor) this.interactorProvider.get(), (MediaControlInteractorFactory) this.controlInteractorFactoryProvider.get(), (MediaUiEventLogger) this.loggerProvider.get(), (MediaFlags) this.mediaFlagsProvider.get());
    }
}
