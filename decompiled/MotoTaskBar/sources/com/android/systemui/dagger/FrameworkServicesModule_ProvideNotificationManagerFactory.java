package com.android.systemui.dagger;

import android.app.NotificationManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideNotificationManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideNotificationManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideNotificationManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideNotificationManagerFactory(provider);
    }

    public static NotificationManager provideNotificationManager(Context context) {
        NotificationManager notificationManagerProvideNotificationManager = FrameworkServicesModule.provideNotificationManager(context);
        notificationManagerProvideNotificationManager.getClass();
        return notificationManagerProvideNotificationManager;
    }

    @Override // javax.inject.Provider
    public NotificationManager get() {
        return provideNotificationManager((Context) this.contextProvider.get());
    }
}
