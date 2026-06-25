package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaViewLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public MediaViewLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static MediaViewLogger_Factory create(Provider provider) {
        return new MediaViewLogger_Factory(provider);
    }

    public static MediaViewLogger newInstance(LogBuffer logBuffer) {
        return new MediaViewLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public MediaViewLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
