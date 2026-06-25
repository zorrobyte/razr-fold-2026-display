package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaCarouselControllerLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public MediaCarouselControllerLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static MediaCarouselControllerLogger_Factory create(Provider provider) {
        return new MediaCarouselControllerLogger_Factory(provider);
    }

    public static MediaCarouselControllerLogger newInstance(LogBuffer logBuffer) {
        return new MediaCarouselControllerLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public MediaCarouselControllerLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
