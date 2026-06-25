package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class StackStateLogger_Factory implements Factory {
    private final Provider bufferProvider;
    private final Provider notificationRenderBufferProvider;

    public StackStateLogger_Factory(Provider provider, Provider provider2) {
        this.bufferProvider = provider;
        this.notificationRenderBufferProvider = provider2;
    }

    public static StackStateLogger_Factory create(Provider provider, Provider provider2) {
        return new StackStateLogger_Factory(provider, provider2);
    }

    public static StackStateLogger newInstance(LogBuffer logBuffer, LogBuffer logBuffer2) {
        return new StackStateLogger(logBuffer, logBuffer2);
    }

    @Override // javax.inject.Provider
    public StackStateLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get(), (LogBuffer) this.notificationRenderBufferProvider.get());
    }
}
