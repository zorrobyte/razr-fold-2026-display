package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideNotificationHeadsUpLogBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideNotificationHeadsUpLogBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotificationHeadsUpLogBufferFactory create(Provider provider) {
        return new LogModule_ProvideNotificationHeadsUpLogBufferFactory(provider);
    }

    public static LogBuffer provideNotificationHeadsUpLogBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideNotificationHeadsUpLogBuffer = LogModule.provideNotificationHeadsUpLogBuffer(logBufferFactory);
        logBufferProvideNotificationHeadsUpLogBuffer.getClass();
        return logBufferProvideNotificationHeadsUpLogBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideNotificationHeadsUpLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
