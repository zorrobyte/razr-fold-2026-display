package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideNotificationInterruptLogBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideNotificationInterruptLogBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotificationInterruptLogBufferFactory create(Provider provider) {
        return new LogModule_ProvideNotificationInterruptLogBufferFactory(provider);
    }

    public static LogBuffer provideNotificationInterruptLogBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideNotificationInterruptLogBuffer = LogModule.provideNotificationInterruptLogBuffer(logBufferFactory);
        logBufferProvideNotificationInterruptLogBuffer.getClass();
        return logBufferProvideNotificationInterruptLogBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideNotificationInterruptLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
