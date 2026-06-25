package com.android.systemui.media.controls.util;

import com.android.internal.logging.UiEventLogger;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaUiEventLogger_Factory implements Factory {
    private final Provider loggerProvider;

    public MediaUiEventLogger_Factory(Provider provider) {
        this.loggerProvider = provider;
    }

    public static MediaUiEventLogger_Factory create(Provider provider) {
        return new MediaUiEventLogger_Factory(provider);
    }

    public static MediaUiEventLogger newInstance(UiEventLogger uiEventLogger) {
        return new MediaUiEventLogger(uiEventLogger);
    }

    @Override // javax.inject.Provider
    public MediaUiEventLogger get() {
        return newInstance((UiEventLogger) this.loggerProvider.get());
    }
}
