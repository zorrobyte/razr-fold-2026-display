package com.android.systemui.media.controls.domain.pipeline.interactor.factory;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor_Factory;
import dagger.internal.InstanceFactory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaControlInteractorFactory_Impl implements MediaControlInteractorFactory {
    private final MediaControlInteractor_Factory delegateFactory;

    MediaControlInteractorFactory_Impl(MediaControlInteractor_Factory mediaControlInteractor_Factory) {
        this.delegateFactory = mediaControlInteractor_Factory;
    }

    public static Provider createFactoryProvider(MediaControlInteractor_Factory mediaControlInteractor_Factory) {
        return InstanceFactory.create(new MediaControlInteractorFactory_Impl(mediaControlInteractor_Factory));
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory
    public MediaControlInteractor create(InstanceId instanceId) {
        return this.delegateFactory.get(instanceId);
    }
}
