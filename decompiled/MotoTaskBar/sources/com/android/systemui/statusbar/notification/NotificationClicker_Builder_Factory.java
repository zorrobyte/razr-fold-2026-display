package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.NotificationClicker;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationClicker_Builder_Factory implements Factory {
    private final Provider loggerProvider;

    public NotificationClicker_Builder_Factory(Provider provider) {
        this.loggerProvider = provider;
    }

    public static NotificationClicker_Builder_Factory create(Provider provider) {
        return new NotificationClicker_Builder_Factory(provider);
    }

    public static NotificationClicker.Builder newInstance(NotificationClickerLogger notificationClickerLogger) {
        return new NotificationClicker.Builder(notificationClickerLogger);
    }

    @Override // javax.inject.Provider
    public NotificationClicker.Builder get() {
        return newInstance((NotificationClickerLogger) this.loggerProvider.get());
    }
}
