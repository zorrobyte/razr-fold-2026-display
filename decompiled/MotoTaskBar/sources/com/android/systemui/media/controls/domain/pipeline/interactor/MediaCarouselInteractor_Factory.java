package com.android.systemui.media.controls.domain.pipeline.interactor;

import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataCombineLatest;
import com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener;
import com.android.systemui.media.controls.util.MediaFlags;
import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes.dex */
public final class MediaCarouselInteractor_Factory implements Factory {
    private final Provider applicationScopeProvider;
    private final Provider mediaDataCombineLatestProvider;
    private final Provider mediaDataFilterProvider;
    private final Provider mediaDataProcessorProvider;
    private final Provider mediaDataRepositoryProvider;
    private final Provider mediaDeviceManagerProvider;
    private final Provider mediaFilterRepositoryProvider;
    private final Provider mediaFlagsProvider;
    private final Provider mediaResumeListenerProvider;
    private final Provider mediaSessionBasedFilterProvider;
    private final Provider mediaTimeoutListenerProvider;

    public MediaCarouselInteractor_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.applicationScopeProvider = provider;
        this.mediaDataRepositoryProvider = provider2;
        this.mediaDataProcessorProvider = provider3;
        this.mediaTimeoutListenerProvider = provider4;
        this.mediaResumeListenerProvider = provider5;
        this.mediaSessionBasedFilterProvider = provider6;
        this.mediaDeviceManagerProvider = provider7;
        this.mediaDataCombineLatestProvider = provider8;
        this.mediaDataFilterProvider = provider9;
        this.mediaFilterRepositoryProvider = provider10;
        this.mediaFlagsProvider = provider11;
    }

    public static MediaCarouselInteractor_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        return new MediaCarouselInteractor_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static MediaCarouselInteractor newInstance(CoroutineScope coroutineScope, MediaDataRepository mediaDataRepository, MediaDataProcessor mediaDataProcessor, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilterImpl mediaDataFilterImpl, MediaFilterRepository mediaFilterRepository, MediaFlags mediaFlags) {
        return new MediaCarouselInteractor(coroutineScope, mediaDataRepository, mediaDataProcessor, mediaTimeoutListener, mediaResumeListener, mediaSessionBasedFilter, mediaDeviceManager, mediaDataCombineLatest, mediaDataFilterImpl, mediaFilterRepository, mediaFlags);
    }

    @Override // javax.inject.Provider
    public MediaCarouselInteractor get() {
        return newInstance((CoroutineScope) this.applicationScopeProvider.get(), (MediaDataRepository) this.mediaDataRepositoryProvider.get(), (MediaDataProcessor) this.mediaDataProcessorProvider.get(), (MediaTimeoutListener) this.mediaTimeoutListenerProvider.get(), (MediaResumeListener) this.mediaResumeListenerProvider.get(), (MediaSessionBasedFilter) this.mediaSessionBasedFilterProvider.get(), (MediaDeviceManager) this.mediaDeviceManagerProvider.get(), (MediaDataCombineLatest) this.mediaDataCombineLatestProvider.get(), (MediaDataFilterImpl) this.mediaDataFilterProvider.get(), (MediaFilterRepository) this.mediaFilterRepositoryProvider.get(), (MediaFlags) this.mediaFlagsProvider.get());
    }
}
