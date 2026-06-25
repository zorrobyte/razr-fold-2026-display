package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideNotificationRenderLogBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideNotificationRenderLogBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotificationRenderLogBufferFactory create(Provider provider) {
        return new LogModule_ProvideNotificationRenderLogBufferFactory(provider);
    }

    public static LogBuffer provideNotificationRenderLogBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideNotificationRenderLogBuffer = LogModule.provideNotificationRenderLogBuffer(logBufferFactory);
        logBufferProvideNotificationRenderLogBuffer.getClass();
        return logBufferProvideNotificationRenderLogBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideNotificationRenderLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
