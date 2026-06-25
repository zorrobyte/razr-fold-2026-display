package com.android.systemui.statusbar.notification.init;

import com.android.systemui.statusbar.NotificationListener;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationsControllerStub_Factory implements Factory {
    private final Provider notificationListenerProvider;

    public NotificationsControllerStub_Factory(Provider provider) {
        this.notificationListenerProvider = provider;
    }

    public static NotificationsControllerStub_Factory create(Provider provider) {
        return new NotificationsControllerStub_Factory(provider);
    }

    public static NotificationsControllerStub newInstance(NotificationListener notificationListener) {
        return new NotificationsControllerStub(notificationListener);
    }

    @Override // javax.inject.Provider
    public NotificationsControllerStub get() {
        return newInstance((NotificationListener) this.notificationListenerProvider.get());
    }
}
