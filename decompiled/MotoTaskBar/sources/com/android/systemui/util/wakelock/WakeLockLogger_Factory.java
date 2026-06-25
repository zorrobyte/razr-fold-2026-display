package com.android.systemui.util.wakelock;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class WakeLockLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public WakeLockLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static WakeLockLogger_Factory create(Provider provider) {
        return new WakeLockLogger_Factory(provider);
    }

    public static WakeLockLogger newInstance(LogBuffer logBuffer) {
        return new WakeLockLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public WakeLockLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
