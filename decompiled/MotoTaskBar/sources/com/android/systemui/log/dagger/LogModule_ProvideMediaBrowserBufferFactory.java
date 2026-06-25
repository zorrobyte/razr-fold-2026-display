package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideMediaBrowserBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideMediaBrowserBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideMediaBrowserBufferFactory create(Provider provider) {
        return new LogModule_ProvideMediaBrowserBufferFactory(provider);
    }

    public static LogBuffer provideMediaBrowserBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideMediaBrowserBuffer = LogModule.provideMediaBrowserBuffer(logBufferFactory);
        logBufferProvideMediaBrowserBuffer.getClass();
        return logBufferProvideMediaBrowserBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideMediaBrowserBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
