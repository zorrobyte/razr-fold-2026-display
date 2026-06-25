package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideShadeLogBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideShadeLogBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideShadeLogBufferFactory create(Provider provider) {
        return new LogModule_ProvideShadeLogBufferFactory(provider);
    }

    public static LogBuffer provideShadeLogBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideShadeLogBuffer = LogModule.provideShadeLogBuffer(logBufferFactory);
        logBufferProvideShadeLogBuffer.getClass();
        return logBufferProvideShadeLogBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideShadeLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
