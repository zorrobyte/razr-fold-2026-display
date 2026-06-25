package com.android.systemui.media.dagger;

import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaModule_ProvidesQSMediaHostFactory implements Factory {
    private final Provider carouselControllerProvider;
    private final Provider dataManagerProvider;
    private final Provider hierarchyManagerProvider;
    private final Provider stateHolderProvider;
    private final Provider statesManagerProvider;

    public MediaModule_ProvidesQSMediaHostFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.stateHolderProvider = provider;
        this.hierarchyManagerProvider = provider2;
        this.dataManagerProvider = provider3;
        this.statesManagerProvider = provider4;
        this.carouselControllerProvider = provider5;
    }

    public static MediaModule_ProvidesQSMediaHostFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new MediaModule_ProvidesQSMediaHostFactory(provider, provider2, provider3, provider4, provider5);
    }

    public static MediaHost providesQSMediaHost(MediaHost.MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager, MediaCarouselController mediaCarouselController) {
        MediaHost mediaHostProvidesQSMediaHost = MediaModule.providesQSMediaHost(mediaHostStateHolder, mediaHierarchyManager, mediaDataManager, mediaHostStatesManager, mediaCarouselController);
        mediaHostProvidesQSMediaHost.getClass();
        return mediaHostProvidesQSMediaHost;
    }

    @Override // javax.inject.Provider
    public MediaHost get() {
        return providesQSMediaHost((MediaHost.MediaHostStateHolder) this.stateHolderProvider.get(), (MediaHierarchyManager) this.hierarchyManagerProvider.get(), (MediaDataManager) this.dataManagerProvider.get(), (MediaHostStatesManager) this.statesManagerProvider.get(), (MediaCarouselController) this.carouselControllerProvider.get());
    }
}
