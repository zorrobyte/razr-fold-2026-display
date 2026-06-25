package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationRowBinderLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public NotificationRowBinderLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static NotificationRowBinderLogger_Factory create(Provider provider) {
        return new NotificationRowBinderLogger_Factory(provider);
    }

    public static NotificationRowBinderLogger newInstance(LogBuffer logBuffer) {
        return new NotificationRowBinderLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public NotificationRowBinderLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
