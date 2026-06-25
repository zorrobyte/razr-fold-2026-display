package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideNotifInteractionLogBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideNotifInteractionLogBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotifInteractionLogBufferFactory create(Provider provider) {
        return new LogModule_ProvideNotifInteractionLogBufferFactory(provider);
    }

    public static LogBuffer provideNotifInteractionLogBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideNotifInteractionLogBuffer = LogModule.provideNotifInteractionLogBuffer(logBufferFactory);
        logBufferProvideNotifInteractionLogBuffer.getClass();
        return logBufferProvideNotifInteractionLogBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideNotifInteractionLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
