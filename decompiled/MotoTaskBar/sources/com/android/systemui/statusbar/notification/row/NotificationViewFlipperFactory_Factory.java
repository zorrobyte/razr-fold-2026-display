package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationViewFlipperFactory_Factory implements Factory {
    private final Provider viewModelProvider;

    public NotificationViewFlipperFactory_Factory(Provider provider) {
        this.viewModelProvider = provider;
    }

    public static NotificationViewFlipperFactory_Factory create(Provider provider) {
        return new NotificationViewFlipperFactory_Factory(provider);
    }

    public static NotificationViewFlipperFactory newInstance(NotificationViewFlipperViewModel notificationViewFlipperViewModel) {
        return new NotificationViewFlipperFactory(notificationViewFlipperViewModel);
    }

    @Override // javax.inject.Provider
    public NotificationViewFlipperFactory get() {
        return newInstance((NotificationViewFlipperViewModel) this.viewModelProvider.get());
    }
}
