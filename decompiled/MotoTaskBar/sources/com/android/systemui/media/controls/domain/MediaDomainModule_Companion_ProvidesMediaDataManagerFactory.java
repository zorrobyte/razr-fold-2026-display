package com.android.systemui.media.controls.domain;

import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.util.MediaFlags;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaDomainModule_Companion_ProvidesMediaDataManagerFactory implements Factory {
    private final Provider legacyProvider;
    private final Provider mediaFlagsProvider;
    private final Provider newProvider;

    public MediaDomainModule_Companion_ProvidesMediaDataManagerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.legacyProvider = provider;
        this.newProvider = provider2;
        this.mediaFlagsProvider = provider3;
    }

    public static MediaDomainModule_Companion_ProvidesMediaDataManagerFactory create(Provider provider, Provider provider2, Provider provider3) {
        return new MediaDomainModule_Companion_ProvidesMediaDataManagerFactory(provider, provider2, provider3);
    }

    public static MediaDataManager providesMediaDataManager(javax.inject.Provider provider, javax.inject.Provider provider2, MediaFlags mediaFlags) {
        MediaDataManager mediaDataManagerProvidesMediaDataManager = MediaDomainModule.Companion.providesMediaDataManager(provider, provider2, mediaFlags);
        mediaDataManagerProvidesMediaDataManager.getClass();
        return mediaDataManagerProvidesMediaDataManager;
    }

    @Override // javax.inject.Provider
    public MediaDataManager get() {
        return providesMediaDataManager(this.legacyProvider, this.newProvider, (MediaFlags) this.mediaFlagsProvider.get());
    }
}
