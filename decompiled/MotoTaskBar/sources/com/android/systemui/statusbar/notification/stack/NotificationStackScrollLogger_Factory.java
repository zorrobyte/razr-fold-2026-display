package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStackScrollLogger_Factory implements Factory {
    private final Provider bufferProvider;
    private final Provider notificationRenderBufferProvider;
    private final Provider shadeLogBufferProvider;

    public NotificationStackScrollLogger_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.bufferProvider = provider;
        this.notificationRenderBufferProvider = provider2;
        this.shadeLogBufferProvider = provider3;
    }

    public static NotificationStackScrollLogger_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new NotificationStackScrollLogger_Factory(provider, provider2, provider3);
    }

    public static NotificationStackScrollLogger newInstance(LogBuffer logBuffer, LogBuffer logBuffer2, LogBuffer logBuffer3) {
        return new NotificationStackScrollLogger(logBuffer, logBuffer2, logBuffer3);
    }

    @Override // javax.inject.Provider
    public NotificationStackScrollLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get(), (LogBuffer) this.notificationRenderBufferProvider.get(), (LogBuffer) this.shadeLogBufferProvider.get());
    }
}
