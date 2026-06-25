package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideMediaCarouselControllerBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideMediaCarouselControllerBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideMediaCarouselControllerBufferFactory create(Provider provider) {
        return new LogModule_ProvideMediaCarouselControllerBufferFactory(provider);
    }

    public static LogBuffer provideMediaCarouselControllerBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideMediaCarouselControllerBuffer = LogModule.provideMediaCarouselControllerBuffer(logBufferFactory);
        logBufferProvideMediaCarouselControllerBuffer.getClass();
        return logBufferProvideMediaCarouselControllerBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideMediaCarouselControllerBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
