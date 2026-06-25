package com.android.systemui.statusbar.notification.ui.viewbinder;

import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpNotificationViewBinder_Factory implements Factory {
    private final Provider viewModelProvider;

    public HeadsUpNotificationViewBinder_Factory(Provider provider) {
        this.viewModelProvider = provider;
    }

    public static HeadsUpNotificationViewBinder_Factory create(Provider provider) {
        return new HeadsUpNotificationViewBinder_Factory(provider);
    }

    public static HeadsUpNotificationViewBinder newInstance(NotificationListViewModel notificationListViewModel) {
        return new HeadsUpNotificationViewBinder(notificationListViewModel);
    }

    @Override // javax.inject.Provider
    public HeadsUpNotificationViewBinder get() {
        return newInstance((NotificationListViewModel) this.viewModelProvider.get());
    }
}
