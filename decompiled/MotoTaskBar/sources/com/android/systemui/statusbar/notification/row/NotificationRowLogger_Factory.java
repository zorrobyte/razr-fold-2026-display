package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationRowLogger_Factory implements Factory {
    private final Provider bufferProvider;
    private final Provider notificationRenderBufferProvider;

    public NotificationRowLogger_Factory(Provider provider, Provider provider2) {
        this.bufferProvider = provider;
        this.notificationRenderBufferProvider = provider2;
    }

    public static NotificationRowLogger_Factory create(Provider provider, Provider provider2) {
        return new NotificationRowLogger_Factory(provider, provider2);
    }

    public static NotificationRowLogger newInstance(LogBuffer logBuffer, LogBuffer logBuffer2) {
        return new NotificationRowLogger(logBuffer, logBuffer2);
    }

    @Override // javax.inject.Provider
    public NotificationRowLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get(), (LogBuffer) this.notificationRenderBufferProvider.get());
    }
}
