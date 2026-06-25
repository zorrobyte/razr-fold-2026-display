package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationInterruptLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public NotificationInterruptLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static NotificationInterruptLogger_Factory create(Provider provider) {
        return new NotificationInterruptLogger_Factory(provider);
    }

    public static NotificationInterruptLogger newInstance(LogBuffer logBuffer) {
        return new NotificationInterruptLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public NotificationInterruptLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
