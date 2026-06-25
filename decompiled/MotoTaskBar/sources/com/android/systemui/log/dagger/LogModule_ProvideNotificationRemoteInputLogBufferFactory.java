package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideNotificationRemoteInputLogBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideNotificationRemoteInputLogBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotificationRemoteInputLogBufferFactory create(Provider provider) {
        return new LogModule_ProvideNotificationRemoteInputLogBufferFactory(provider);
    }

    public static LogBuffer provideNotificationRemoteInputLogBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideNotificationRemoteInputLogBuffer = LogModule.provideNotificationRemoteInputLogBuffer(logBufferFactory);
        logBufferProvideNotificationRemoteInputLogBuffer.getClass();
        return logBufferProvideNotificationRemoteInputLogBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideNotificationRemoteInputLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
