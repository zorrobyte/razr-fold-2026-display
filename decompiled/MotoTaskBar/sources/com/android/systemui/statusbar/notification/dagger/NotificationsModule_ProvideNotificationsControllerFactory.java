package com.android.systemui.statusbar.notification.dagger;

import android.content.Context;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationsModule_ProvideNotificationsControllerFactory implements Factory {
    private final Provider contextProvider;
    private final Provider realControllerProvider;
    private final Provider stubControllerProvider;

    public NotificationsModule_ProvideNotificationsControllerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.realControllerProvider = provider2;
        this.stubControllerProvider = provider3;
    }

    public static NotificationsModule_ProvideNotificationsControllerFactory create(Provider provider, Provider provider2, Provider provider3) {
        return new NotificationsModule_ProvideNotificationsControllerFactory(provider, provider2, provider3);
    }

    public static NotificationsController provideNotificationsController(Context context, javax.inject.Provider provider, javax.inject.Provider provider2) {
        NotificationsController notificationsControllerProvideNotificationsController = NotificationsModule.provideNotificationsController(context, provider, provider2);
        notificationsControllerProvideNotificationsController.getClass();
        return notificationsControllerProvideNotificationsController;
    }

    @Override // javax.inject.Provider
    public NotificationsController get() {
        return provideNotificationsController((Context) this.contextProvider.get(), this.realControllerProvider, this.stubControllerProvider);
    }
}
