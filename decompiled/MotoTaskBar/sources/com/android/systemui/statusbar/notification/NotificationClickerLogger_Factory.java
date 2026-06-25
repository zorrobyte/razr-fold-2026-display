package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationClickerLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public NotificationClickerLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static NotificationClickerLogger_Factory create(Provider provider) {
        return new NotificationClickerLogger_Factory(provider);
    }

    public static NotificationClickerLogger newInstance(LogBuffer logBuffer) {
        return new NotificationClickerLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public NotificationClickerLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
