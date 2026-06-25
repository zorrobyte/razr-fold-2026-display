package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideNotifInflationLogBufferFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideNotifInflationLogBufferFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotifInflationLogBufferFactory create(Provider provider) {
        return new LogModule_ProvideNotifInflationLogBufferFactory(provider);
    }

    public static LogBuffer provideNotifInflationLogBuffer(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideNotifInflationLogBuffer = LogModule.provideNotifInflationLogBuffer(logBufferFactory);
        logBufferProvideNotifInflationLogBuffer.getClass();
        return logBufferProvideNotifInflationLogBuffer;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideNotifInflationLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}
