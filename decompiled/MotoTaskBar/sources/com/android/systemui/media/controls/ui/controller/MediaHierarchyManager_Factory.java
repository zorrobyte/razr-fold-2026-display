package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaHierarchyManager_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider mediaCarouselControllerProvider;
    private final Provider mediaManagerProvider;

    public MediaHierarchyManager_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.mediaCarouselControllerProvider = provider2;
        this.mediaManagerProvider = provider3;
    }

    public static MediaHierarchyManager_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new MediaHierarchyManager_Factory(provider, provider2, provider3);
    }

    public static MediaHierarchyManager newInstance(Context context, MediaCarouselController mediaCarouselController, MediaDataManager mediaDataManager) {
        return new MediaHierarchyManager(context, mediaCarouselController, mediaDataManager);
    }

    @Override // javax.inject.Provider
    public MediaHierarchyManager get() {
        return newInstance((Context) this.contextProvider.get(), (MediaCarouselController) this.mediaCarouselControllerProvider.get(), (MediaDataManager) this.mediaManagerProvider.get());
    }
}
