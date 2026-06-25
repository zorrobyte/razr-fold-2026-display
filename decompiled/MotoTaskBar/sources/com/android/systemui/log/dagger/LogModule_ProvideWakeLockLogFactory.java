package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class LogModule_ProvideWakeLockLogFactory implements Factory {
    private final Provider factoryProvider;

    public LogModule_ProvideWakeLockLogFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideWakeLockLogFactory create(Provider provider) {
        return new LogModule_ProvideWakeLockLogFactory(provider);
    }

    public static LogBuffer provideWakeLockLog(LogBufferFactory logBufferFactory) {
        LogBuffer logBufferProvideWakeLockLog = LogModule.provideWakeLockLog(logBufferFactory);
        logBufferProvideWakeLockLog.getClass();
        return logBufferProvideWakeLockLog;
    }

    @Override // javax.inject.Provider
    public LogBuffer get() {
        return provideWakeLockLog((LogBufferFactory) this.factoryProvider.get());
    }
}
