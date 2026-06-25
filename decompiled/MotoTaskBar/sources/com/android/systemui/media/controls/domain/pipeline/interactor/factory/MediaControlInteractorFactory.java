package com.android.systemui.media.controls.domain.pipeline.interactor.factory;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor;

/* JADX INFO: compiled from: MediaControlInteractorFactory.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MediaControlInteractorFactory {
    MediaControlInteractor create(InstanceId instanceId);
}
