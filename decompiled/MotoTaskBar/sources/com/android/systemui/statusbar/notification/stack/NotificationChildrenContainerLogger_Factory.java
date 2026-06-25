package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationChildrenContainerLogger_Factory implements Factory {
    private final Provider notificationRenderBufferProvider;

    public NotificationChildrenContainerLogger_Factory(Provider provider) {
        this.notificationRenderBufferProvider = provider;
    }

    public static NotificationChildrenContainerLogger_Factory create(Provider provider) {
        return new NotificationChildrenContainerLogger_Factory(provider);
    }

    public static NotificationChildrenContainerLogger newInstance(LogBuffer logBuffer) {
        return new NotificationChildrenContainerLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public NotificationChildrenContainerLogger get() {
        return newInstance((LogBuffer) this.notificationRenderBufferProvider.get());
    }
}
