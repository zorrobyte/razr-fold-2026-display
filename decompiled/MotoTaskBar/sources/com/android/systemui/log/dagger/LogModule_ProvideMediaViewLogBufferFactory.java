package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideMediaViewLogBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideMediaViewLogBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideMediaViewLogBufferFactory create(Provider provider) {
        return new LogModule_ProvideMediaViewLogBufferFactory(provider);
    }

    public static LogBuffer provideMediaViewLogBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideMediaViewLogBuffer = LogModule.provideMediaViewLogBuffer(logBufferFactory);
        logBufferProvideMediaViewLogBuffer.getClass();
        return logBufferProvideMediaViewLogBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideMediaViewLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
